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
import Entity.Usuario;
import Handlers.CarritoDAO;
import Handlers.ConsolaDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "GestionarCarrito", urlPatterns = {"/GestionarCarrito"})
public class GestionarCarrito extends HttpServlet {

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
// Accede al carrito desde la sesión
        HttpSession session = request.getSession();
        String accion = request.getParameter("accion");

        if ("Eliminar seleccionados".equals(accion)) {
            // Recupera los identificadores de consolas y juegos seleccionados
            String[] consolaIds = request.getParameterValues("consolaIds");
            String[] juegoIds = request.getParameterValues("juegoIds");

            Carrito carrito = (Carrito) session.getAttribute("carrito");

            if (carrito != null) {
                // Eliminar consolas seleccionadas
                if (consolaIds != null) {
                    for (String consolaId : consolaIds) {
                        int idConsola = Integer.parseInt(consolaId);
                        ItemCarritoConsola consolaItem = new ItemCarritoConsola(new Consola(idConsola, "", "", "", "", 0.0, 0), 0);
                        carrito.eliminarConsola(consolaItem);
                    }
                }

                // Eliminar juegos seleccionados
                if (juegoIds != null) {
                    for (String juegoId : juegoIds) {
                        int idJuego = Integer.parseInt(juegoId);
                        ItemCarritoJuego juegoItem = new ItemCarritoJuego(new Juego(idJuego, "", "", "", 0, 0.0, 0), 0);
                        carrito.eliminarJuego(juegoItem);
                    }
                }

                // Actualizar el carrito en la sesión
                session.setAttribute("carrito", carrito);
            }

            // Redirige de vuelta a la página mostrando el carrito actualizado
            response.sendRedirect("mostrarCarrito.jsp");

        } else if ("Procesar Compra".equals(accion)) {
            Conexion miConexion = new Conexion();
            miConexion.conectar();
            CarritoDAO carritoDAO = new CarritoDAO(miConexion);

            // Obtener el Carrito de la sesión
            Carrito carrito = (Carrito) session.getAttribute("carrito");

            // Obtener el ID del usuario de la sesión
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            int idUsuario = usuario.getId_usuario();

            // Procesar la compra y verificar si fue exitosa
            boolean compraExitosa = carritoDAO.procesarCompra(idUsuario, carrito);

            

            if (compraExitosa) {
                int idTicket = CarritoDAO.ticket;
                // La compra se procesó con éxito
                // Redirigir a una página de confirmación
                response.sendRedirect("confirmacionCompra.jsp");
            } else {
                // La compra no se procesó con éxito
                // Redirigir a una página de error
                response.sendRedirect(request.getContextPath() + "/errorCompra.jsp");
            }
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
