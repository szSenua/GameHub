/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers;

import Conexion.Conexion;
import Entity.Carrito;
import Entity.Consola;
import Entity.ItemCarritoConsola;
import Entity.ItemCarritoJuego;
import Entity.Juego;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author SzBel
 */
public class CarritoDAO {

    public static int ticket;

    private Carrito carrito;
    private Conexion miConexion;

    public CarritoDAO(Conexion miConexion) {
        this.carrito = carrito;
        this.miConexion = miConexion;
    }

    /**
     * Función que gestiona la compra
     * @param idUsuario
     * @param carrito
     * @return 
     */
    public boolean procesarCompra(int idUsuario, Carrito carrito) {
        Connection connection = miConexion.getMiConexion();

        try {
            // Verificar existencias y saldo antes de procesar la compra
            if (!verificarExistencias(carrito) || !verificarSaldoUsuario(idUsuario, carrito)) {
                return false; // La compra no se puede procesar debido a falta de existencias o saldo insuficiente
            }

            // Crear un nuevo ticket
            int idTicket = insertarNuevoTicket(idUsuario);

            // Asociar elementos del carrito al ticket y actualizar stock
            for (ItemCarritoConsola item : carrito.getConsolas()) {
                insertarItemTicket(idTicket, item);
                actualizarStockConsola(item.getConsola().getId_consola(), item.getCantidad());
            }

            for (ItemCarritoJuego item : carrito.getJuegos()) {
                insertarItemTicket(idTicket, item);
                actualizarStockJuego(item.getJuego().getId_juego(), item.getCantidad());
            }

            // Actualizar saldo del usuario
            double totalCompra = carrito.calcularTotal();
            actualizarSaldoUsuario(idUsuario, totalCompra);

            // Guardo el id del ticket en una variable estática
            ticket = idTicket;

            // Vaciar el carrito
            carrito.vaciarCarrito();

            return true; // La compra se procesó con éxito

        } catch (SQLException e) {
            e.printStackTrace();

            return false; // La compra no se procesó con éxito
        }
    }

    private boolean verificarSaldoUsuario(int idUsuario, Carrito carrito) throws SQLException {
        double saldoUsuario = obtenerSaldoUsuario(idUsuario);
        double totalCompra = carrito.calcularTotal();

        return saldoUsuario >= totalCompra;
    }

    private double obtenerSaldoUsuario(int idUsuario) throws SQLException {
        String sql = "SELECT saldo FROM usuarios WHERE id_usuario = ?";
        try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(sql)) {
            preparedStatement.setInt(1, idUsuario);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getDouble("saldo");
            }

            return 0; // Si no se encuentra el usuario, se asume saldo cero
        }
    }

    private boolean verificarExistencias(Carrito carrito) throws SQLException {
        for (ItemCarritoConsola item : carrito.getConsolas()) {
            if (!verificarExistenciasConsola(item.getConsola().getId_consola(), item.getCantidad())) {
                return false; // No hay suficientes existencias de la consola
            }
        }

        for (ItemCarritoJuego item : carrito.getJuegos()) {
            if (!verificarExistenciasJuego(item.getJuego().getId_juego(), item.getCantidad())) {
                return false; // No hay suficientes existencias del juego
            }
        }

        return true; // Hay suficientes existencias de todos los productos en el carrito
    }

    private boolean verificarExistenciasConsola(int idConsola, int cantidad) throws SQLException {
        String sql = "SELECT unidades_disponibles FROM consolas WHERE id_consola = ?";
        try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(sql)) {
            preparedStatement.setInt(1, idConsola);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int existencias = rs.getInt("unidades_disponibles");
                return existencias >= cantidad;
            }

            return false;
        }
    }

    private boolean verificarExistenciasJuego(int idJuego, int cantidad) throws SQLException {
        String sql = "SELECT unidades_disponibles FROM juegos WHERE id_juego = ?";
        try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(sql)) {
            preparedStatement.setInt(1, idJuego);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int existencias = rs.getInt("unidades_disponibles");
                return existencias >= cantidad;
            }

            return false;
        }
    }

    public int insertarNuevoTicket(int idUsuario) throws SQLException {
        String sql = "INSERT INTO tickets (id_usuario) VALUES (?)";
        try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.executeUpdate();

            //Obtengo las claves generadas ya automáticamente en el id del ticket
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID del ticket");
            }
        }
    }

    public void insertarItemTicket(int idTicket, Object item) throws SQLException {
        String tipoProducto = (item instanceof ItemCarritoConsola) ? "Consola" : "Juego";
        String sql = "INSERT INTO ticket_items (ticket_id, tipo_producto, id_consola, id_juego, cantidad, precio_unitario) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(sql)) {
            preparedStatement.setInt(1, idTicket);
            preparedStatement.setString(2, tipoProducto);

            if (item instanceof ItemCarritoConsola) {
                Consola consola = ((ItemCarritoConsola) item).getConsola();
                preparedStatement.setInt(3, consola.getId_consola());
                preparedStatement.setNull(4, Types.INTEGER); // id_juego es NULL para consolas
            } else if (item instanceof ItemCarritoJuego) {
                Juego juego = ((ItemCarritoJuego) item).getJuego();
                preparedStatement.setNull(3, Types.INTEGER); // id_consola es NULL para juegos
                preparedStatement.setInt(4, juego.getId_juego());
            }

            // Adaptación al método específico
            if (item instanceof ItemCarritoConsola) {
                preparedStatement.setInt(5, ((ItemCarritoConsola) item).getCantidad());
                preparedStatement.setDouble(6, ((ItemCarritoConsola) item).calcularSubtotal());
            } else if (item instanceof ItemCarritoJuego) {
                preparedStatement.setInt(5, ((ItemCarritoJuego) item).getCantidad());
                preparedStatement.setDouble(6, ((ItemCarritoJuego) item).calcularSubtotal());
            }

            preparedStatement.executeUpdate();
        }
    }

    private void actualizarStockConsola(int idConsola, int cantidad) throws SQLException {
        String sql = "UPDATE consolas SET unidades_disponibles = unidades_disponibles - ? WHERE id_consola = ?";
        try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(sql)) {
            preparedStatement.setInt(1, cantidad);
            preparedStatement.setInt(2, idConsola);
            preparedStatement.executeUpdate();
        }
    }

    private void actualizarStockJuego(int idJuego, int cantidad) throws SQLException {
        String sql = "UPDATE juegos SET unidades_disponibles = unidades_disponibles - ? WHERE id_juego = ?";
        try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(sql)) {
            preparedStatement.setInt(1, cantidad);
            preparedStatement.setInt(2, idJuego);
            preparedStatement.executeUpdate();
        }
    }

    private void actualizarSaldoUsuario(int idUsuario, double totalCompra) throws SQLException {
        String sql = "UPDATE usuarios SET saldo = saldo - ? WHERE id_usuario = ?";
        try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(sql)) {
            preparedStatement.setDouble(1, totalCompra);
            preparedStatement.setInt(2, idUsuario);
            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<ItemCarritoConsola> obtenerConsolasPorTicket(int idTicket) {
        ArrayList<ItemCarritoConsola> consolas = new ArrayList<>();

        String sql = "SELECT * FROM ticket_items WHERE ticket_id = ? AND tipo_producto = 'Consola'";

        try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(sql)) {
            preparedStatement.setInt(1, idTicket);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int idConsola = rs.getInt("id_consola");
                int cantidad = rs.getInt("cantidad");

                // Obtener más detalles de la consola si es necesario
                Consola consola = obtenerDetallesConsola(idConsola);

                ItemCarritoConsola item = new ItemCarritoConsola(consola, cantidad);
                consolas.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consolas;
    }

    public ArrayList<ItemCarritoJuego> obtenerJuegosPorTicket(int idTicket) {
        ArrayList<ItemCarritoJuego> juegos = new ArrayList<>();

        String sql = "SELECT * FROM ticket_items WHERE ticket_id = ? AND tipo_producto = 'Juego'";

        try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(sql)) {
            preparedStatement.setInt(1, idTicket);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int idJuego = rs.getInt("id_juego");
                int cantidad = rs.getInt("cantidad");

                // Obtener más detalles del juego si es necesario
                Juego juego = obtenerDetallesJuego(idJuego);

                ItemCarritoJuego item = new ItemCarritoJuego(juego, cantidad);
                juegos.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return juegos;
    }

    public Consola obtenerDetallesConsola(int idConsola) throws SQLException {
        String sql = "SELECT * FROM consolas WHERE id_consola = ?";
        try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(sql)) {
            preparedStatement.setInt(1, idConsola);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    // Aquí creo una instancia de Consola con los detalles del ResultSet
                    String nombre = rs.getString("nombre");
                    String potenciaCpu = rs.getString("potencia_cpu");
                    String potenciaGpu = rs.getString("potencia_gpu");
                    String compania = rs.getString("compania");
                    double precio = rs.getDouble("precio");
                    int unidadesDisponibles = rs.getInt("unidades_disponibles");

                    return new Consola(idConsola, nombre, potenciaCpu, potenciaGpu, compania, precio, unidadesDisponibles);
                }
            }
        }

        return null; // Retorna null si no se encuentra la consola
    }

    public Juego obtenerDetallesJuego(int idJuego) throws SQLException {
        String sql = "SELECT * FROM juegos WHERE id_juego = ?";
        try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(sql)) {
            preparedStatement.setInt(1, idJuego);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    // Aquí creo una instancia de Juego con los detalles del ResultSet
                    String nombre = rs.getString("nombre");
                    String companiaDesarrolladora = rs.getString("compania_desarrolladora");
                    String genero = rs.getString("genero");
                    int puntuacionMetacritic = rs.getInt("puntuacion_metacritic");
                    double precio = rs.getDouble("precio");
                    int unidadesDisponibles = rs.getInt("unidades_disponibles");

                    return new Juego(idJuego, nombre, companiaDesarrolladora, genero, puntuacionMetacritic, precio, unidadesDisponibles);
                }
            }
        }

        return null; // Retorna null si no se encuentra el juego
    }
}
