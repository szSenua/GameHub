/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers;

import Conexion.Conexion;
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
        } 

        return listaUsuarios;
    }
    
    public boolean existeUsuario(String username) {
    try {
        String query = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
        PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);

        return count > 0;
    } catch (SQLException ex) {
        System.out.println("Error al verificar la existencia del usuario: " + ex.getMessage());
        return false;
    }
}


    public boolean insertarUsuario(Usuario usuario) {
        try {
            String query = "INSERT INTO usuarios (username, password, tipodeusuario, saldo) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);

            preparedStatement.setString(1, usuario.getUsername());
            preparedStatement.setString(2, usuario.getPassword());
            preparedStatement.setString(3, usuario.getTipodeusuario().name());
            preparedStatement.setDouble(4, usuario.getSaldo());

            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            System.out.println("Error al insertar el usuario: " + ex.getMessage());
            return false;
        }
    }

    public Usuario obtenerUsuarioPorId(int idUsuario) {
        try {
            String query = "SELECT * FROM usuarios WHERE id_usuario = ?";
            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);
            preparedStatement.setInt(1, idUsuario);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String tipoUsuarioString = resultSet.getString("tipodeusuario");
                Usuario.RolUsuario tipodeusuario = Usuario.RolUsuario.valueOf(tipoUsuarioString);
                double saldo = resultSet.getDouble("saldo");

                return new Usuario(idUsuario, username, password, tipodeusuario, saldo);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener el usuario por ID: " + ex.getMessage());
        }

        return null;
    }

    public boolean actualizarUsuario(Usuario usuario) {
        try {
            String query = "UPDATE usuarios SET username = ?, password = ?, tipodeusuario = ?, saldo = ? WHERE id_usuario = ?";
            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);

            preparedStatement.setString(1, usuario.getUsername());
            preparedStatement.setString(2, usuario.getPassword());
            preparedStatement.setString(3, usuario.getTipodeusuario().name());
            preparedStatement.setDouble(4, usuario.getSaldo());
            preparedStatement.setInt(5, usuario.getId_usuario());

            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el usuario: " + ex.getMessage());
            return false;
        }
    }

    public boolean eliminarUsuario(int idUsuario) {
        try {
            String query = "DELETE FROM usuarios WHERE id_usuario = ?";
            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);
            preparedStatement.setInt(1, idUsuario);

            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            System.out.println("Error al eliminar el usuario: " + ex.getMessage());
            return false;
        }
    }
    
    public boolean actualizarSaldo(int idUsuario, double nuevoSaldo) {
    try {
        String query = "UPDATE usuarios SET saldo = ? WHERE id_usuario = ?";
        PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);

        preparedStatement.setDouble(1, nuevoSaldo);
        preparedStatement.setInt(2, idUsuario);

        int filasAfectadas = preparedStatement.executeUpdate();
        return filasAfectadas > 0;
    } catch (SQLException ex) {
        System.out.println("Error al actualizar el saldo del usuario: " + ex.getMessage());
        return false;
    }
}


}
