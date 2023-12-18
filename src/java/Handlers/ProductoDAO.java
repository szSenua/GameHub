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
}
