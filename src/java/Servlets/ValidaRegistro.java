/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Conexion.Conexion;
import Entity.Usuario;
import Handlers.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author SzBel
 */
@WebServlet(name = "ValidaRegistro", urlPatterns = {"/ValidaRegistro"})
public class ValidaRegistro extends HttpServlet {

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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String saldoStr = request.getParameter("saldo");

        // Validar que el saldo sea un número
        double saldo = 0;
        try {
            saldo = Double.parseDouble(saldoStr);
        } catch (NumberFormatException e) {
            // Setear el mensaje de error en el request
            request.setAttribute("errorMessage", "Error: El saldo debe ser un número válido.");
            // Redirigir de vuelta al formulario de registro
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
            return;
        }

        // Crear una nueva instancia de Usuario con los datos proporcionados
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setSaldo(saldo);
        nuevoUsuario.setTipodeusuario(Usuario.RolUsuario.Usuario);

        //Verificar si el usuario ya existe
        Conexion miConexion = new Conexion();
        miConexion.conectar();
        UsuarioDAO usuarioDAO = new UsuarioDAO(miConexion);

        if (usuarioDAO.existeUsuario(username)) {
            // Setear el mensaje de error en el request
            request.setAttribute("errorMessage", "Error: El nombre de usuario ya existe. Por favor, elige otro.");
            // Redirigir de vuelta al formulario de registro
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
        } else {
            // Insertar el nuevo usuario en la base de datos
            boolean insercionExitosa = usuarioDAO.insertarUsuario(nuevoUsuario);

            if (insercionExitosa) {
                // Redirigir al login
                response.sendRedirect("login.jsp");
            } else {
                // Setear el mensaje de error en el request
                request.setAttribute("errorMessage", "Error al registrar el usuario. Por favor, intenta nuevamente.");
                // Redirigir de vuelta al formulario de registro
                request.getRequestDispatcher("/registro.jsp").forward(request, response);
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
