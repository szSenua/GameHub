/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author SzBel
 */
public class Usuario {
    
   private int id_usuario;
   private String username;
   private String password;
   private boolean es_administrador;
   private double saldo;

    public Usuario(int id_usuario, String username, String password, boolean es_administrador, double saldo) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.password = password;
        this.es_administrador = es_administrador;
        this.saldo = saldo;
    }
    
    public Usuario() {
        this.id_usuario = id_usuario;
        this.username = username;
        this.password = password;
        this.es_administrador = es_administrador;
        this.saldo = saldo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEs_administrador() {
        return es_administrador;
    }

    public void setEs_administrador(boolean es_admin) {
        this.es_administrador = es_administrador;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
   
   
}
