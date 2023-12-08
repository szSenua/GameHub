/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que se va a encargar de la conexión a la base de datos
 *
 * @author SzBel
 */
public class Conexion {

    private String usuario;
    private String contrasena;
    private String url;
    private String db;
    private Connection miConexion;

    public Conexion() {
        this.usuario = "gamehub";
        this.contrasena = "1234";
        this.url = "jdbc:mysql://localhost:3306/gamehub";

    }

    public void conectar() {

        try {
            if (miConexion == null || miConexion.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Conectando con la base de datos: " + url);
                miConexion = DriverManager.getConnection(url, usuario, contrasena);
                System.out.println("Conectado con éxito");
            } else {
                System.out.println("Ya hay una conexión iniciada");
            }

        } catch (ClassNotFoundException ex) {
            System.out.println("Error con el controlador: " + ex.getStackTrace());
        } catch (SQLException ex) {
            System.out.println("Error al conectar con la base de datos: " + ex.getStackTrace());
        }

    }

    public void desconectar() {

        try {
            if (!miConexion.isClosed() && miConexion != null) {
                miConexion.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la conexión a la base de datos "
                    + ex.getStackTrace());
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public Connection getMiConexion() {
        return miConexion;
    }

    public void setMiConexion(Connection miConexion) {
        this.miConexion = miConexion;
    }

}
