/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers;

import Conexion.Conexion;
import Entity.Juego;
import Entity.Producto;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author SzBel
 */
public class JuegoDAO {

    private ArrayList<Juego> listaJuegos;
    private Conexion miConexion;

    public JuegoDAO(Conexion miConexion) {
        this.listaJuegos = new ArrayList();
        this.miConexion = miConexion;
    }

    /**
     * Función para guardar los juegos de la base de datos en un ArrayList
     *
     * @return
     */
    public ArrayList<Juego> obtenerJuegos() {
        try {
            String query = "SELECT * FROM juegos";
            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idJuego = resultSet.getInt("id_juego");
                String nombre = resultSet.getString("nombre");
                String compania = resultSet.getString("compania_desarrolladora");
                String genero = resultSet.getString("genero");
                int metacritic = resultSet.getInt("puntuacion_metacritic");
                double precio = resultSet.getDouble("precio");
                int unidadesDisponibles = resultSet.getInt("unidades_disponibles");

                Juego juego = new Juego(idJuego, nombre, compania, genero, metacritic, precio, unidadesDisponibles);

                listaJuegos.add(juego);
            }

        } catch (SQLException ex) {
            System.out.println("Error al obtener todos los juegos: " + ex.getMessage());
        } finally {
            miConexion.desconectar();
        }

        return listaJuegos;
    }

    /**
     * Función que inserta un juego nuevo en la base de datos
     *
     * @param nuevoJuego
     * @return
     */
    public boolean insertaJuego(Juego nuevoJuego) {
        try {
            // Preparar la consulta SQL para la inserción
            String query = "INSERT INTO juegos (nombre, compania_desarrolladora, genero, puntuacion_metacritic, "
                    + "precio, unidades_disponibles) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);

            // Establecer los valores de los parámetros
            preparedStatement.setString(1, nuevoJuego.getNombre());
            preparedStatement.setString(2, nuevoJuego.getCompania_desarrolladora());
            preparedStatement.setString(3, nuevoJuego.getGenero());
            preparedStatement.setInt(4, nuevoJuego.getPuntuacion_metacritic());
            preparedStatement.setDouble(5, nuevoJuego.getPrecio());
            preparedStatement.setInt(6, nuevoJuego.getUnidadesDisponibles());

            // Ejecutar la consulta de inserción
            int filasAfectadas = preparedStatement.executeUpdate();

            // Verificar si la inserción fue exitosa
            if (filasAfectadas > 0) {
                System.out.println("Juego insertado con éxito");
                return true;
            } else {
                System.out.println("No se pudo insertar el juego");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Error al insertar el juego: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Función que modifica la información de un juego en la base de datos
     *
     * @param juegoModificado
     * @return
     */
    public boolean modificaJuego(int id, Juego juegoModificado) {
        try {
            // Preparar la consulta SQL para la actualización
            String query = "UPDATE juegos SET nombre=?, compania_desarrolladora=?, genero=?, puntuacion_metacritic=?, precio=?, unidades_disponibles=? "
                    + "WHERE " + id + " = ?";

            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);

            // Establecer los valores de los parámetros
            preparedStatement.setString(1, juegoModificado.getNombre());
            preparedStatement.setString(2, juegoModificado.getCompania_desarrolladora());
            preparedStatement.setString(3, juegoModificado.getGenero());
            preparedStatement.setInt(4, juegoModificado.getPuntuacion_metacritic());
            preparedStatement.setDouble(5, juegoModificado.getPrecio());
            preparedStatement.setInt(6, juegoModificado.getUnidadesDisponibles());
            preparedStatement.setInt(7, juegoModificado.getId_juego());

            // Ejecutar la consulta de actualización
            int filasAfectadas = preparedStatement.executeUpdate();

            // Verificar si la actualización fue exitosa
            if (filasAfectadas > 0) {
                System.out.println("Juego modificado con éxito");
                return true;
            } else {
                System.out.println("No se pudo modificar el juego");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Error al modificar el juego: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Función para borrar un juego
     *
     * @param idJuego
     * @return
     */
    public boolean borrarJuego(int idJuego) {
        try {
            // Preparar la consulta SQL para la eliminación
            String query = "DELETE FROM juegos WHERE id_juego=?";

            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);

            // Establecer el valor del parámetro
            preparedStatement.setInt(1, idJuego);

            // Ejecutar la consulta de eliminación
            int filasAfectadas = preparedStatement.executeUpdate();

            // Verificar si la eliminación fue exitosa
            if (filasAfectadas > 0) {
                System.out.println("Juego eliminado con éxito");
                return true;
            } else {
                System.out.println("No se pudo eliminar el juego");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Error al eliminar el juego: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Función que obtiene los juegos por consola
     *
     * @param idConsola obtenido de los values de un select
     * @return
     */
    public ArrayList<Juego> obtenerJuegosPorConsola(int idConsola) {
        ArrayList<Juego> juegosPorConsola = new ArrayList<>();
        try {
            // Preparar la consulta SQL para obtener juegos por consola
            String query = "SELECT juegos.* FROM juegos "
                    + "INNER JOIN juegos_plataformas ON juegos.id_juego = juegos_plataformas.id_juego "
                    + "WHERE juegos_plataformas.id_consola = ?";
            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);

            // Establecer el valor del parámetro
            preparedStatement.setInt(1, idConsola);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idJuego = resultSet.getInt("id_juego");
                String nombre = resultSet.getString("nombre");
                String compania = resultSet.getString("compania_desarrolladora");
                String genero = resultSet.getString("genero");
                int metacritic = resultSet.getInt("puntuacion_metacritic");
                double precio = resultSet.getDouble("precio");
                int unidadesDisponibles = resultSet.getInt("unidades_disponibles");

                Juego juego = new Juego(idJuego, nombre, compania, genero, metacritic, precio, unidadesDisponibles);
                juegosPorConsola.add(juego);
            }

        } catch (SQLException ex) {
            System.out.println("Error al obtener juegos por consola: " + ex.getMessage());
        } finally {
            miConexion.desconectar();
        }

        return juegosPorConsola;
    }

}
