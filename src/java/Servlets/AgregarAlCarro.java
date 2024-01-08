/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Conexion.Conexion;
import Entity.Carrito;
import Entity.Consola;
import Entity.ItemCarritoConsola;
import Entity.ItemCarritoJuego;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author SzBel
 */
@WebServlet(name = "AgregarAlCarro", urlPatterns = {"/AgregarAlCarro"})
public class AgregarAlCarro extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

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
                    producto = listaJuegos.get(i);
                    break;
                }
            }
        } else if (tipo == 'C') {
            ConsolaDAO consolaDAO = new ConsolaDAO(miConexion);
            ArrayList<Consola> listaConsolas = consolaDAO.obtenerConsolas();
            for (int i = 0; i < listaConsolas.size(); i++) {
                if (listaConsolas.get(i).getId_consola() == id) {
                    producto = listaConsolas.get(i);
                    break;
                }
            }
        }

        miConexion.desconectar();

        // Manejar el carrito aquí
        HttpSession session = request.getSession(true);
        Carrito carrito = (Carrito) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute("carrito", carrito);
        }

        if (producto != null) {
            // Verifica si el producto ya está en el carrito
            if (producto instanceof Juego) {
                Juego juego = (Juego) producto;
                ItemCarritoJuego itemJuego = new ItemCarritoJuego(juego, 1);

                if (carrito.contieneJuego(itemJuego)) {
                    // Si el juego ya está en el carrito, aumenta la cantidad
                    carrito.incrementarCantidadJuego(itemJuego);
                } else {
                    // Si el juego no está en el carrito, agrégalo
                    carrito.agregarJuego(itemJuego);
                }
            } else if (producto instanceof Consola) {
                Consola consola = (Consola) producto;
                ItemCarritoConsola itemConsola = new ItemCarritoConsola(consola, 1);

                if (carrito.contieneConsola(itemConsola)) {
                    // Si la consola ya está en el carrito, aumenta la cantidad
                    carrito.incrementarCantidadConsola(itemConsola);
                } else {
                    // Si la consola no está en el carrito, agrégala
                    carrito.agregarConsola(itemConsola);
                }
            }

            // Recarga la página actual
            // Código de estado de la respuesta a 302 Found y configura el Location
            // a la página de referencia, es decir, en la que esté el usuario.

            response.setStatus(HttpServletResponse.SC_FOUND);
            response.setHeader("Location", request.getHeader("Referer"));

        } else {
            // Redirecciona a una página de error o muestra un mensaje
            response.sendRedirect("error.jsp");
        }
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
