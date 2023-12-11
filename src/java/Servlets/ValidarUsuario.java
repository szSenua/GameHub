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
@WebServlet(name = "ValidarUsuario", urlPatterns = {"/ValidarUsuario"})
public class ValidarUsuario extends HttpServlet {

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

        //Obtengo los parámetros del login
        String nombre = request.getParameter("usuario");
        String contrasenia = request.getParameter("password");
        boolean encontrado;

        //Llamo al método de obtener usuarios
        Conexion miConexion = new Conexion();
        miConexion.conectar();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        ArrayList<Usuario> misUsuarios = usuarioDAO.obtenerUsuarios();

        encontrado = usuarioDAO.validarCredenciales(nombre, contrasenia);

        if (encontrado) {
            // Establezco los atributos de sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", true);
            session.setAttribute("username", nombre);

            // Busco el usuario en el ArrayList utilizando las credenciales
            Usuario usuarioEncontrado = null;
            for (Usuario usuario : misUsuarios) {
                if (usuario.getUsername().equals(nombre) && usuario.getPassword().equals(contrasenia)) {
                    usuarioEncontrado = usuario;
                    break;
                }
            }

            // Consulto los demás datos del usuario y los añado a la sesión
            if (usuarioEncontrado != null) {
                session.setAttribute("id_usuario", usuarioEncontrado.getId_usuario());
                session.setAttribute("tipodeusuario", usuarioEncontrado.getTipodeusuario());
                session.setAttribute("saldo", usuarioEncontrado.getSaldo());

                //Redirijo a home
                response.sendRedirect("home.jsp");

            }
        } else {
            out.println("El usuario no existe");
        }

        miConexion.desconectar();

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
        //processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //out.println("Método no permitido");
        response.sendRedirect("login.jsp");
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
