/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Objects;

/**
 *
 * @author SzBel
 */
public class ItemCarritoConsola {
    private Consola consola;
    private int cantidad;
   

    public ItemCarritoConsola(Consola consola, int cantidad) {
        this.consola = consola;
        this.cantidad = cantidad;
       
    }
    
    
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }
        ItemCarritoConsola item = (ItemCarritoConsola) objeto;
        return consola.getId_consola() == item.consola.getId_consola();
    }

    /**
     * Calcula que dos instancias de la misma clase (por el valor de sus ids) tengan el mismo valor hash
     * @return 
     */
    @Override
    public int hashCode() {
        return Objects.hash(consola.getId_consola());
    }
    
    
    public void incrementarCantidad() {
        cantidad++;
    }

    public Consola getConsola() {
        return consola;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double calcularSubtotal() {
        return consola.getPrecio() * cantidad;
    }
}

