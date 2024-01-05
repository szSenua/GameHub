/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Conexion.Conexion;
import Entity.Consola;
import Entity.Juego;
import Handlers.ConsolaDAO;
import Handlers.JuegoDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author SzBel
 */
@WebServlet(name = "MostrarJuegos", urlPatterns = {"/MostrarJuegos"})
public class MostrarJuegos extends HttpServlet {

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
        
    
        // Conectar a la base de datos
        Conexion miConexion = new Conexion();
        miConexion.conectar();
        JuegoDAO juegoDAO = new JuegoDAO(miConexion);
        ConsolaDAO consolaDAO = new ConsolaDAO(miConexion);

        // Obtener las consolas seleccionadas
        String[] consolasSeleccionadas = request.getParameterValues("consolas");

        if (request.getParameter("quitarFiltros") != null) {
            // Si se presionó el botón, redirige a MostrarJuegos sin parámetros de consolas
            response.sendRedirect(request.getContextPath() + "/MostrarJuegos");
            return;
        }

        // Colocar las consolas seleccionadas en el atributo de la solicitud
        request.setAttribute("consolasSeleccionadas", consolasSeleccionadas);

        ArrayList<Juego> listaJuegos;
        ArrayList<Consola> listaConsolas;

        if (consolasSeleccionadas != null) {
            // Convertir los strings a enteros
            List<Integer> idConsolas = Arrays.stream(consolasSeleccionadas)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            listaConsolas = consolaDAO.obtenerConsolas();
            // Llamar al método obtenerJuegosPorConsolas con la lista de IDs de consolas
            listaJuegos = juegoDAO.obtenerJuegosPorConsolas(idConsolas);
        } else {
            listaConsolas = consolaDAO.obtenerConsolas();
            // Si no se seleccionaron consolas específicas, obtener todos los juegos
            listaJuegos = juegoDAO.obtenerJuegos();
        }

        // Colocar la lista en el atributo de la solicitud para mostrarla en el JSP
        request.setAttribute("listaJuegos", listaJuegos);
        request.setAttribute("listaConsolas", listaConsolas);

        // Establece las consolas asociadas a cada juego
        if (listaJuegos != null) {
            for (Juego juego : listaJuegos) {
                ArrayList<Consola> consolasPorJuego = juegoDAO.obtenerConsolasPorJuego(juego.getId_juego());
                request.setAttribute("consolasPorJuego_" + juego.getId_juego(), consolasPorJuego);
            }
        }

        // Cerrar la conexión
        miConexion.desconectar();

        // Redirigir a la página que mostrará la lista de juegos
        request.getRequestDispatcher("/mostrarJuegos.jsp").forward(request, response);
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
