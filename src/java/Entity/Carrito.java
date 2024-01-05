/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.ArrayList;

/**
 *
 * @author SzBel
 */
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<ItemCarritoConsola> consolas;
    private List<ItemCarritoJuego> juegos;

    public Carrito() {
        this.consolas = new ArrayList<>();
        this.juegos = new ArrayList<>();
    }

    public List<ItemCarritoConsola> getConsolas() {
        return consolas;
    }

    public List<ItemCarritoJuego> getJuegos() {
        return juegos;
    }
    
    /**
     * Función que obtiene la cantidad total de productos en el carrito
     * @return 
     */
    public int getCantidadTotal() {
        int cantidadTotal = 0;

        for (ItemCarritoConsola item : consolas) {
            cantidadTotal += item.getCantidad();
        }

        for (ItemCarritoJuego item : juegos) {
            cantidadTotal += item.getCantidad();
        }

        return cantidadTotal;
    }
    
     public boolean contieneJuego(ItemCarritoJuego item) {
        return juegos.contains(item);
    }

    public boolean contieneConsola(ItemCarritoConsola item) {
        return consolas.contains(item);
    }

    public void incrementarCantidadJuego(ItemCarritoJuego item) {
        if (juegos.contains(item)) {
            int index = juegos.indexOf(item);
            ItemCarritoJuego existingItem = juegos.get(index);
            existingItem.incrementarCantidad();
        }
    }

    public void incrementarCantidadConsola(ItemCarritoConsola item) {
        if (consolas.contains(item)) {
            int index = consolas.indexOf(item);
            ItemCarritoConsola existingItem = consolas.get(index);
            existingItem.incrementarCantidad();
        }
    }

    public void agregarConsola(ItemCarritoConsola consola) {
        consolas.add(consola);
    }

    public void agregarJuego(ItemCarritoJuego juego) {
        juegos.add(juego);
    }

    public void eliminarConsola(ItemCarritoConsola consola) {
        consolas.remove(consola);
    }

    public void eliminarJuego(ItemCarritoJuego juego) {
        juegos.remove(juego);
    }
    

    public void vaciarCarrito() {
        consolas.clear();
        juegos.clear();
    }

    public double calcularTotal() {
        double total = 0;

        for (ItemCarritoConsola item : consolas) {
            total += item.calcularSubtotal();
        }

        for (ItemCarritoJuego item : juegos) {
            total += item.calcularSubtotal();
        }

        return total;
    }
}
