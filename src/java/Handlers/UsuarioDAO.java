/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers;

import Conexion.Conexion;
import Entity.Consola;
import Entity.Juego;
import Entity.Producto;
import Entity.Usuario;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author SzBel
 */
public class UsuarioDAO {

    private ArrayList<Usuario> listaUsuarios;
    private Conexion miConexion;

    public UsuarioDAO(Conexion miConexion) {
        this.listaUsuarios = new ArrayList();
        this.miConexion = miConexion;
    }

    /**
     * Función que obtiene todos los usuarios de la base de datos y los guarda
     * en un arraylist
     *
     * @return la lista de usuarios.
     */
    public ArrayList<Usuario> obtenerUsuarios() {

        try {
            String query = "SELECT * FROM usuarios";
            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idUsuario = resultSet.getInt("id_usuario");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                // Obtener string del tipo de usuario desde la bd
                String tipoUsuarioString = resultSet.getString("tipodeusuario");

                // Convertir el String a un valor del enum RolUsuario
                Usuario.RolUsuario tipodeusuario = Usuario.RolUsuario.valueOf(tipoUsuarioString);

                double saldo = resultSet.getDouble("saldo");

                Usuario usuario = new Usuario(idUsuario, username, password, tipodeusuario, saldo);

                listaUsuarios.add(usuario);
            }

        } catch (SQLException ex) {
            System.out.println("Error al obtener todos los usuarios: " + ex.getMessage());
        } finally {
            miConexion.desconectar();
        }

        return listaUsuarios;
    }

    //Función provisional
    public boolean comprarProducto(Usuario usuario, Producto producto, int cantidad) {
        try {

            // Verificar si hay suficiente stock
            if (producto.getUnidadesDisponibles() < cantidad) {
                System.out.println("No hay suficiente stock para realizar la compra.");
                return false;
            }

            // Verificar si el usuario tiene saldo suficiente
            double costoTotal = producto.getPrecio() * cantidad;
            if (usuario.getSaldo() < costoTotal) {
                System.out.println("Saldo insuficiente para realizar la compra.");
                return false;
            }

            // Actualizar el saldo del usuario y el stock del producto en una transacción
            String compraQuery = "";

            if (producto instanceof Consola) {
                compraQuery = "UPDATE usuarios SET saldo = saldo - ? WHERE id_usuario = ?;"
                        + "UPDATE consolas SET unidades_disponibles = unidades_disponibles - ? WHERE id_consola = ?";
            } else if (producto instanceof Juego) {
                compraQuery = "UPDATE usuarios SET saldo = saldo - ? WHERE id_usuario = ?;"
                        + "UPDATE juegos SET unidades_disponibles = unidades_disponibles - ? WHERE id_juego = ?";
            }

            try (PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(compraQuery)) {
                preparedStatement.setDouble(1, costoTotal);
                preparedStatement.setInt(2, usuario.getId_usuario());
                preparedStatement.setInt(3, cantidad);

                // Acceder al atributo id directamente
                int idProducto = (producto instanceof Consola) ? ((Consola) producto).getId_consola() : ((Juego) producto).getId_juego();
                preparedStatement.setInt(4, idProducto);

                // Ejecutar la transacción
                preparedStatement.executeUpdate();

                System.out.println("Compra realizada con éxito.");
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error al realizar la compra: " + ex.getMessage());
            return false;
        } finally {
            miConexion.desconectar();
        }
    }

}
