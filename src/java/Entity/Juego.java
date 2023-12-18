/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author SzBel
 */
public class Juego extends Producto {
    private int id_juego;
    private String compania_desarrolladora;
    private String genero;
    private int puntuacion_metacritic;

    public Juego(int id_juego, String nombre, String compania_desarrolladora, String genero, int puntuacion_metacritic, double precio, int unidades_disponibles) {
        // Llama al constructor de la clase base (Producto)
        super(nombre, precio, unidades_disponibles);
        
        this.id_juego = id_juego;
        this.compania_desarrolladora = compania_desarrolladora;
        this.genero = genero;
        this.puntuacion_metacritic = puntuacion_metacritic;
    }

    public int getId_juego() {
        return id_juego;
    }

    public void setId_juego(int id_juego) {
        this.id_juego = id_juego;
    }

    public String getCompania_desarrolladora() {
        return compania_desarrolladora;
    }

    public void setCompania_desarrolladora(String compania_desarrolladora) {
        this.compania_desarrolladora = compania_desarrolladora;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getPuntuacion_metacritic() {
        return puntuacion_metacritic;
    }

    public void setPuntuacion_metacritic(int puntuacion_metacritic) {
        this.puntuacion_metacritic = puntuacion_metacritic;
    }
    
    //Métodos de la clase padre
public int getUnidadesDisponibles() {
        return super.getUnidadesDisponibles();
    }
    
    public String getNombre() {
        return super.getNombre();
    }
    
    public double getPrecio() {
        return super.getPrecio();
    }
    
}

