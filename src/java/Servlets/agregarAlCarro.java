/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Conexion.Conexion;
import Entity.Consola;
import Entity.Juego;
import Entity.Producto;
import Handlers.ConsolaDAO;
import Handlers.JuegoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author SzBel
 */
@WebServlet(name = "agregarAlCarro", urlPatterns = {"/agregarAlCarro"})
public class agregarAlCarro extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        // Obtengo los parámetros de los input hidden
        String tipoProducto = request.getParameter("tipoProducto");
        String idProducto = request.getParameter("idProducto");

        // Extraigo la letra y el id del producto
        char tipo = idProducto.toUpperCase().charAt(0);
        int id = Integer.parseInt(idProducto.substring(1));

        Conexion miConexion = new Conexion();
        miConexion.conectar();

        // Clasifica el producto como juego o consola
        Producto producto = null;
        if (tipo == 'J') {

            JuegoDAO juegoDAO = new JuegoDAO(miConexion);
            ArrayList<Juego> listaJuegos = juegoDAO.obtenerJuegos();

            for (int i = 0; i < listaJuegos.size(); i++) {
                if (listaJuegos.get(i).getId_juego() == id) {
                    //encuentro el juego
                    producto = listaJuegos.get(i);
                    break;
                }
            }

        } else if (tipo == 'C') {

            ConsolaDAO consolaDAO = new ConsolaDAO(miConexion);
            ArrayList<Consola> listaConsolas = consolaDAO.obtenerConsolas();

            for (int i = 0; i < listaConsolas.size(); i++) {
                if (listaConsolas.get(i).getId_consola() == id) {
                    //encuentro la consola
                    producto = listaConsolas.get(i);
                    break;
                }
            }

        } else {

            out.println("<title>No se pudo realizar la compra</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>El artículo que desea no se encuentra disponible</h1>");
        }
        //Cerramos la sesión
        miConexion.desconectar();

        // -------------- MANEJAR EL CARRITO AQUÍ -------------------
        // Verifica si el producto es un juego o una consola
        if (producto instanceof Juego) {
            Juego juego = (Juego) producto;
            out.println("<title>Compra Exitosa</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Compra Exitosa</h1>");
            out.println("<p>" + juego.getTipoDeProducto() + "</p>");
            out.println("<p>ID de Juego: " + juego.getId_juego() + "</p>");
            out.println("<p>Nombre: " + juego.getNombre() + "</p>");

        } else if (producto instanceof Consola) {
            Consola consola = (Consola) producto;
            out.println("<title>Compra Exitosa</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Compra Exitosa</h1>");
            out.println("<p>" + consola.getTipoDeProducto() + "</p>");
            out.println("<p>ID de Consola: " + consola.getId_consola() + "</p>");
            out.println("<p>Nombre: " + consola.getNombre() + "</p>");

        }

        out.println("<p>ID de Producto: " + idProducto + "</p>");

        out.println("</body>");
        out.println("</html>");

    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
