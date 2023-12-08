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

    public UsuarioDAO() {
        this.listaUsuarios = new ArrayList();
        this.miConexion = new Conexion();
    }
    
    /**
     * Función que obtiene todos los usuarios de la base de datos y los guarda en un 
     * arraylist
     * @return la lista de usuarios.
     */
    public ArrayList<Usuario> obtenerUsuarios(){
        
        miConexion.conectar();
        
        try{
            String query = "SELECT * FROM usuarios";
            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()){
                Usuario usuario = new Usuario(
                resultSet.getInt("id_usuario"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getBoolean("es_administrador"),
                resultSet.getDouble("saldo")
                );
                
                listaUsuarios.add(usuario);
            }
            
        } catch (SQLException ex) {
            System.out.println("Error al obtener todos los usuarios: " + ex.getMessage());
        } finally {
            miConexion.desconectar();
        }
        
        return listaUsuarios;
    }
    
    /**
     * Función que recibe un username y una password y busca si existe en el arraylist de usuarios 
     * @param username
     * @param password
     * @return true si ha encontrado, false si no lo ha encontrado
     */
    public boolean validarCredenciales(String username, String password){
        
        for(Usuario usuario : listaUsuarios){
            if(usuario.getUsername().equals(username) && usuario.getPassword().equals(password)){
                return true;
            }
        }
        
        return false;
       
    }
    
    public Usuario obtenerUsuarioPorNombre(String username) {
    for (Usuario usuario : listaUsuarios) {
        if (usuario.getUsername().equals(username)) {
            return usuario;
        }
    }
    return null;
}
    
}
