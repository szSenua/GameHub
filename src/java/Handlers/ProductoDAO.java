/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers;

import Conexion.Conexion;
import Entity.Consola;
import Entity.Juego;
import Entity.Producto;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author SzBel
 */
public class ProductoDAO {

    private ConsolaDAO consolaDAO;
    private JuegoDAO juegoDAO;
    

    public ProductoDAO(Conexion miConexion) {
        this.consolaDAO = new ConsolaDAO(miConexion);
        this.juegoDAO = new JuegoDAO(miConexion);

    }

    /**
     * Función que lista todos los productos (clase padre)
     *
     * @return
     */
    public ArrayList<Producto> obtenerProductos() {
        ArrayList<Producto> listaProductos = new ArrayList<>();

        // Obtener consolas
        ArrayList<Consola> consolas = consolaDAO.obtenerConsolas();
        listaProductos.addAll(consolas);

        // Obtener juegos
        ArrayList<Juego> juegos = juegoDAO.obtenerJuegos();
        listaProductos.addAll(juegos);

        return listaProductos;
    }

    /**
     * Función que inserta un producto dependiendo de si es Consola o Juego
     * @param nuevoProducto
     * @return devuelve true or false llamando a los métodos de cada clase
     */
    public boolean insertaProducto(Producto nuevoProducto) {

        if (nuevoProducto instanceof Consola) {
            // Insertar una nueva consola
            Consola consola = (Consola) nuevoProducto;
            return consolaDAO.insertaConsola(consola);
        } 
        
        if (nuevoProducto instanceof Juego) {
            // Insertar un nuevo juego
            Juego juego = (Juego) nuevoProducto;
            return juegoDAO.insertaJuego(juego);
        }
        
        // Tipo de producto no admitido
        return false;

    }

    /**
     * Función que modifica un producto dependiendo de si es una Consola o Juego
     * @param productoID
     * @param nuevoProducto
     * @return 
     */
    public boolean modificaProducto(int productoID, Producto nuevoProducto) {
        // Verificar si el ID del producto existe
        Producto productoExistente = obtenerProductoPorID(productoID);

        // Producto no encontrado o modificación no admitida
        if (productoExistente == null) {
            return false;
        }

        // Realizar la modificación según el tipo de producto
        if (productoExistente instanceof Consola && nuevoProducto instanceof Consola) {
            // Modificar Consola
            consolaDAO.modificaConsola((Consola) nuevoProducto);
            return true;
        }

        if (productoExistente instanceof Juego && nuevoProducto instanceof Juego) {
            // Modificar Juego
            juegoDAO.modificaJuego((Juego) nuevoProducto);
            return true;
        }

        // Cualquier otra condicion
        return false;
    }

    /**
     * Función que borra un producto dependiendo de si es una Consola o un Juego
     * @param productoID
     * @return 
     */
    public boolean borraProducto(int productoID) {

        Producto productoExistente = obtenerProductoPorID(productoID);

        if (productoExistente == null) {
            return false;
        }

        if (productoExistente instanceof Consola) {
            // Borrar una consola
            return consolaDAO.borrarConsola(((Consola) productoExistente).getId_consola());
            
        }

        if (productoExistente instanceof Juego) {
            // Borrar un juego
            return juegoDAO.borrarJuego(((Juego) productoExistente).getId_juego());
        }

        // Cualquier otra condición
        return false;
    }

    public Producto obtenerProductoPorID(int productoID) {
        //Obtengo la lista de todos los productos
        ArrayList<Producto> listaProductos = obtenerProductos();

        for (Producto producto : listaProductos) {
            if (producto instanceof Consola && ((Consola) producto).getId_consola() == productoID) {
                return producto; // Encuentra la consola
            } else if (producto instanceof Juego && ((Juego) producto).getId_juego() == productoID) {
                return producto; // Encuentra el juego
            }
        }

        return null; // No encontró nada
    }




    
    
}
