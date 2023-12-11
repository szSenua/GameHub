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
     public enum RolUsuario {
        Administrador,
        Usuario
    }
    
    
   private int id_usuario;
   private String username;
   private String password;
   private RolUsuario tipodeusuario;
   private double saldo;

    public Usuario(int id_usuario, String username, String password, RolUsuario tipodeusuario, double saldo) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.password = password;
        this.tipodeusuario = tipodeusuario;
        this.saldo = saldo;
    }
    
    public Usuario() {
        this.id_usuario = id_usuario;
        this.username = username;
        this.password = password;
        this.tipodeusuario = tipodeusuario;
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

    public RolUsuario getTipodeusuario() {
        return tipodeusuario;
    }

    public void setTipodeusuario(RolUsuario tipodeusuario) {
        this.tipodeusuario = tipodeusuario;
    }

  
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
   
   
}
