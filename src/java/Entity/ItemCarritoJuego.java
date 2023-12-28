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
public class ItemCarritoJuego {
    private Juego juego;
    private int cantidad;
    
    

    public ItemCarritoJuego(Juego juego, int cantidad) {
        this.juego = juego;
        this.cantidad = cantidad;
        
    }
    
   
    
    public void incrementarCantidad() {
        cantidad++;
    }
    
    
    /**
     * Función que sobreescritura el método equals
     * @param objeto
     * @return 
     */
    @Override
    public boolean equals(Object objeto) {
        
        //Verifico que el objeto que se pasa es una referencia a sí mismo
        if (this == objeto) {
            return true;
        }
        
        //Verifico que el objeto no sea nulo ni que pertenezca a una clase distinta
        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }
        
        //Verifico que los dos objetos pertenezcan a la misma clase
        ItemCarritoJuego item = (ItemCarritoJuego) objeto;
        
        //Devuelvo el resultado la comparación de ids
        return juego.getId_juego() == item.juego.getId_juego();
    }

    @Override
    public int hashCode() {
        return Objects.hash(juego.getId_juego());
    }

    public Juego getJuego() {
        return juego;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double calcularSubtotal() {
        return juego.getPrecio() * cantidad;
    }
}

