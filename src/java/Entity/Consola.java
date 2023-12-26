/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author SzBel
 */
public class Consola extends Producto {

    private int id_consola;
    private String potencia_cpu;
    private String potencia_gpu;
    private String compania_desarrolladora;

    public Consola(int id_consola, String nombre, String potencia_cpu, String potencia_gpu, String compania_desarrolladora,
            double precio, int unidadesDisponibles) {
        super(nombre, precio, unidadesDisponibles);

        this.id_consola = id_consola;
        this.potencia_cpu = potencia_cpu;
        this.potencia_gpu = potencia_gpu;
        this.compania_desarrolladora = compania_desarrolladora;
        this.setTipoDeProducto("Consola");

    }

    public int getId_consola() {
        return id_consola;
    }

    public void setId_consola(int id_consola) {
        this.id_consola = id_consola;
    }

    public String getPotencia_cpu() {
        return potencia_cpu;
    }

    public void setPotencia_cpu(String potencia_cpu) {
        this.potencia_cpu = potencia_cpu;
    }

    public String getPotencia_gpu() {
        return potencia_gpu;
    }

    public void setPotencia_gpu(String potencia_gpu) {
        this.potencia_gpu = potencia_gpu;
    }

    public String getCompania_desarrolladora() {
        return compania_desarrolladora;
    }

    public void setCompania_desarrolladora(String compania_desarrolladora) {
        this.compania_desarrolladora = compania_desarrolladora;
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
