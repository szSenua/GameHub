/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers;

import java.sql.*;
import Conexion.Conexion;
import Entity.Consola;
import java.util.ArrayList;

/**
 *
 * @author SzBel
 */
public class ConsolaDAO {

    private ArrayList<Consola> listaConsolas;
    private Conexion miConexion;

    public ConsolaDAO(Conexion miConexion) {
        this.listaConsolas = new ArrayList();
        this.miConexion = miConexion;
    }

    /**
     * Función que obtiene todas las consolas de la base de datos y los guarda
     * en un arraylist
     *
     * @return la lista de consolas.
     */
    public ArrayList<Consola> obtenerConsolas() {
        try {
            String query = "SELECT * FROM consolas";
            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idConsola = resultSet.getInt("id_consola");
                String nombre = resultSet.getString("nombre");
                String potenciaCPU = resultSet.getString("potencia_cpu");
                String potenciaGPU = resultSet.getString("potencia_gpu");
                String compania = resultSet.getString("compania");
                double precio = resultSet.getDouble("precio");
                int unidadesDisponibles = resultSet.getInt("unidades_disponibles");

                Consola consola = new Consola(idConsola, nombre, potenciaCPU, potenciaGPU, compania,
                        precio, unidadesDisponibles);

                listaConsolas.add(consola);
            }

        } catch (SQLException ex) {
            System.out.println("Error al obtener todos las consolas: " + ex.getMessage());
        } 

        return listaConsolas;
    }

    /**
     * Función que inserta una nueva consola en la base de datos
     *
     * @param nuevaConsola
     * @return
     */
    public boolean insertaConsola(Consola nuevaConsola) {
        try {
            // Preparar la consulta SQL para la inserción
            String query = "INSERT INTO consolas (nombre, potencia_cpu, potencia_gpu, compania, precio, unidades_disponibles) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);

            // Establecer los valores de los parámetros
            preparedStatement.setString(1, nuevaConsola.getNombre());
            preparedStatement.setString(2, nuevaConsola.getPotencia_cpu());
            preparedStatement.setString(3, nuevaConsola.getPotencia_gpu());
            preparedStatement.setString(4, nuevaConsola.getCompania_desarrolladora());
            preparedStatement.setDouble(5, nuevaConsola.getPrecio());
            preparedStatement.setInt(6, nuevaConsola.getUnidadesDisponibles());

            // Ejecutar la consulta de inserción
            int filasAfectadas = preparedStatement.executeUpdate();

            // Verificar si la inserción fue exitosa
            if (filasAfectadas > 0) {
                System.out.println("Consola insertada con éxito");
                return true;
            } else {
                System.out.println("No se pudo insertar la consola");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Error al insertar la consola: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Función que modifica la información de una consola
     *
     * @param consolaModificada
     * @return
     */
    public boolean modificaConsola(int id, Consola consolaModificada) {
        try {
            // Preparar la consulta SQL para la actualización
            String query = "UPDATE consolas SET nombre=?, potencia_cpu=?, potencia_gpu=?, compania=?, precio=?, unidades_disponibles=? "
                    + "WHERE " + id +" = ?";

            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);

            // Establecer los valores de los parámetros
            preparedStatement.setString(1, consolaModificada.getNombre());
            preparedStatement.setString(2, consolaModificada.getPotencia_cpu());
            preparedStatement.setString(3, consolaModificada.getPotencia_cpu());
            preparedStatement.setString(4, consolaModificada.getCompania_desarrolladora());
            preparedStatement.setDouble(5, consolaModificada.getPrecio());
            preparedStatement.setInt(6, consolaModificada.getUnidadesDisponibles());
            preparedStatement.setInt(7, consolaModificada.getId_consola());

            // Ejecutar la consulta de actualización
            int filasAfectadas = preparedStatement.executeUpdate();

            // Verificar si la actualización fue exitosa
            if (filasAfectadas > 0) {
                System.out.println("Consola modificada con éxito");
                return true;
            } else {
                System.out.println("No se pudo modificar la consola");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Error al modificar la consola: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Función que borra una consola
     *
     * @param idConsola
     * @return
     */
    public boolean borrarConsola(int idConsola) {
        try {
            // Preparar la consulta SQL para la eliminación
            String query = "DELETE FROM consolas WHERE id_consola=?";

            PreparedStatement preparedStatement = miConexion.getMiConexion().prepareStatement(query);

            // Establecer el valor del parámetro
            preparedStatement.setInt(1, idConsola);

            // Ejecutar la consulta de eliminación
            int filasAfectadas = preparedStatement.executeUpdate();

            // Verificar si la eliminación fue exitosa
            if (filasAfectadas > 0) {
                System.out.println("Consola eliminada con éxito");
                return true;
            } else {
                System.out.println("No se pudo eliminar la consola");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Error al eliminar la consola: " + ex.getMessage());
            return false;
        }
    }

}
