/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import Conexion.Conexion;
import Entity.Consola;
import Entity.Juego;
import Entity.Usuario;
import Handlers.ConsolaDAO;
import Handlers.JuegoDAO;
import Handlers.UsuarioDAO;
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
@WebServlet(name = "Administracion", urlPatterns = {"/Administracion"})
public class Administracion extends HttpServlet {

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

        String action = request.getParameter("action");

        Conexion miConexion = new Conexion();
        miConexion.conectar();
        ConsolaDAO consolaDAO = new ConsolaDAO(miConexion);
        JuegoDAO juegoDAO = new JuegoDAO(miConexion);
        UsuarioDAO usuarioDAO = new UsuarioDAO(miConexion);
        boolean insercionExitosa;

        if (action != null) {
            switch (action) {
                case "editarConsola":
                    String consolaIdParam = request.getParameter("id");

                    if (consolaIdParam != null && !consolaIdParam.isEmpty()) {
                        try {
                            int consolaId = Integer.parseInt(consolaIdParam);

                            Consola consola = consolaDAO.obtenerConsolaPorId(consolaId);

                            if (consola != null) {
                                request.setAttribute("consola", consola);
                                request.getRequestDispatcher("editarConsolaForm.jsp").forward(request, response);
                                return;
                            } else {
                                request.setAttribute("error", "Consola no encontrada");
                            }
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "ID de consola inválido");
                        }
                    } else {
                        request.setAttribute("error", "ID de consola no proporcionado");
                    }
                    break;

                case "guardarEdicionConsola":
                    int idConsolaEditar = 0;
                    String nuevoNombre = request.getParameter("nombre");
                    String nuevaPotenciaCpu = request.getParameter("potenciaCpu");
                    String nuevaPotenciaGpu = request.getParameter("potenciaGpu");
                    String compania = request.getParameter("compania");
                    String precio = request.getParameter("precio");
                    String unidades = request.getParameter("unidades");

                    if (validarFormatoDouble(precio) && validarFormatoInt(unidades)) {
                        // Precio y unidades son números válidos
                        double precioDouble = Double.parseDouble(precio);
                        int unidadesInt = Integer.parseInt(unidades);

                        String idConsolaEditarStr = request.getParameter("idConsola");
                        if (idConsolaEditarStr != null && !idConsolaEditarStr.isEmpty()) {
                            try {
                                idConsolaEditar = Integer.parseInt(idConsolaEditarStr);

                                Consola consolaActualizada = new Consola();
                                consolaActualizada.setId_consola(idConsolaEditar);
                                consolaActualizada.setNombre(nuevoNombre);
                                consolaActualizada.setPotencia_cpu(nuevaPotenciaCpu);
                                consolaActualizada.setPotencia_gpu(nuevaPotenciaGpu);
                                consolaActualizada.setCompania_desarrolladora(compania);
                                consolaActualizada.setPrecio(precioDouble);
                                consolaActualizada.setUnidadesDisponibles(unidadesInt);

                                boolean actualizacionExitosa = consolaDAO.modificaConsola(consolaActualizada);

                                if (actualizacionExitosa) {
                                    response.sendRedirect("administracion.jsp?success=Consola actualizada con éxito");
                                    return;
                                } else {
                                    request.setAttribute("error", "No se pudo actualizar la consola");
                                }
                            } catch (NumberFormatException e) {
                                request.setAttribute("error", "Error al procesar el ID de consola");
                            }
                        } else {
                            request.setAttribute("error", "ID de consola no proporcionado");
                        }
                    } else {
                        request.setAttribute("error", "Precio y unidades deben ser números válidos");
                    }
                    break;

                case "insertaConsola":
                    // Recupera datos del formulario de inserción
                    nuevoNombre = request.getParameter("nombre");
                    nuevaPotenciaCpu = request.getParameter("potenciaCpu");
                    nuevaPotenciaGpu = request.getParameter("potenciaGpu");
                    compania = request.getParameter("compania");
                    precio = request.getParameter("precio");
                    unidades = request.getParameter("unidades");

                    if (validarFormatoDouble(precio) && validarFormatoInt(unidades)) {
                        // Precio y unidades son números válidos
                        double precioDouble = Double.parseDouble(precio);
                        int unidadesInt = Integer.parseInt(unidades);

                        // Crea una nueva instancia de Consola con los datos proporcionados
                        Consola nuevaConsola = new Consola();
                        nuevaConsola.setNombre(nuevoNombre);
                        nuevaConsola.setPotencia_cpu(nuevaPotenciaCpu);
                        nuevaConsola.setPotencia_gpu(nuevaPotenciaGpu);
                        nuevaConsola.setCompania_desarrolladora(compania);
                        nuevaConsola.setPrecio(precioDouble);
                        nuevaConsola.setUnidadesDisponibles(unidadesInt);

                        // Inserta la nueva consola en la base de datos
                        insercionExitosa = consolaDAO.insertaConsola(nuevaConsola);

                        if (insercionExitosa) {
                            // Establece un atributo para indicar la inserción exitosa
                            request.setAttribute("insercionExitosa", true);

                            response.sendRedirect("administracion.jsp?success=Consola insertada con éxito");
                            return;
                        } else {
                            request.setAttribute("error", "No se pudo insertar la consola");
                        }
                    } else {
                        request.setAttribute("error", "Precio y unidades deben ser números válidos");
                    }
                    break;

                case "eliminarConsola":
                    String idConsolaEliminarStr = request.getParameter("id");

                    if (idConsolaEliminarStr != null && !idConsolaEliminarStr.isEmpty()) {
                        try {
                            int idConsolaEliminar = Integer.parseInt(idConsolaEliminarStr);

                            boolean eliminacionExitosa = consolaDAO.borrarConsola(idConsolaEliminar);

                            if (eliminacionExitosa) {
                                response.sendRedirect("administracion.jsp?success=Consola eliminada con éxito");
                                return;
                            } else {
                                request.setAttribute("error", "No se pudo eliminar la consola");
                            }
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "ID de consola a eliminar inválido");
                        }
                    } else {
                        request.setAttribute("error", "ID de consola a eliminar no proporcionado");
                    }
                    break;

                case "editarJuego":
                    String juegoIdParam = request.getParameter("id");

                    if (juegoIdParam != null && !juegoIdParam.isEmpty()) {
                        try {
                            int juegoId = Integer.parseInt(juegoIdParam);

                            Juego juego = juegoDAO.obtenerJuegoPorId(juegoId);

                            if (juego != null) {
                                request.setAttribute("juego", juego);
                                request.getRequestDispatcher("editarJuegoForm.jsp").forward(request, response);
                                return;
                            } else {
                                request.setAttribute("error", "Juego no encontrado");
                            }
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "ID de juego inválido");
                        }
                    } else {
                        request.setAttribute("error", "ID de juego no proporcionado");
                    }
                    break;

                case "guardarEdicionJuego":
                    int idJuegoEditar = 0;

                    nuevoNombre = request.getParameter("nombre");
                    compania = request.getParameter("compania");
                    String genero = request.getParameter("genero");
                    String puntuacion = request.getParameter("puntuacion_metacritic");
                    precio = request.getParameter("precio");
                    unidades = request.getParameter("unidades");

                    String idJuegoEditarStr = request.getParameter("idJuego");

                    if (idJuegoEditarStr != null && !idJuegoEditarStr.isEmpty()) {
                        try {
                            idJuegoEditar = Integer.parseInt(idJuegoEditarStr);

                            // Validar formato de campos numéricos
                            if (validarFormatoInt(puntuacion) && validarFormatoDouble(precio) && validarFormatoInt(unidades)) {
                                // Puntuación, precio y unidades son enteros válidos
                                int puntuacionInt = Integer.parseInt(puntuacion);
                                double precioDouble = Double.parseDouble(precio);
                                int unidadesInt = Integer.parseInt(unidades);

                                Juego juegoActualizado = new Juego();
                                juegoActualizado.setId_juego(idJuegoEditar);
                                juegoActualizado.setNombre(nuevoNombre);
                                juegoActualizado.setCompania_desarrolladora(compania);
                                juegoActualizado.setGenero(genero);
                                juegoActualizado.setPuntuacion_metacritic(puntuacionInt);
                                juegoActualizado.setPrecio(precioDouble);
                                juegoActualizado.setUnidadesDisponibles(unidadesInt);

                                boolean actualizacionExitosa = juegoDAO.modificaJuego(juegoActualizado);

                                if (actualizacionExitosa) {
                                    response.sendRedirect("administracion.jsp?success=Juego actualizado con éxito");
                                    return;
                                } else {
                                    request.setAttribute("error", "No se pudo actualizar el juego");
                                }
                            } else {
                                request.setAttribute("error", "Puntuación, precio y unidades deben ser números válidos");
                            }

                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "Error al procesar el ID del juego");
                        }
                    } else {
                        request.setAttribute("error", "ID de juego no proporcionado");
                    }

                    break;

                case "insertaJuego":
                    // Recupera datos del formulario de inserción
                    nuevoNombre = request.getParameter("nombre");
                    compania = request.getParameter("compania");
                    genero = request.getParameter("genero");
                    puntuacion = request.getParameter("puntuacion_metacritic");
                    precio = request.getParameter("precio");
                    unidades = request.getParameter("unidades");

                    if (validarFormatoInt(puntuacion) && validarFormatoDouble(precio) && validarFormatoInt(unidades)) {
                        // Puntuación, precio y unidades son números válidos
                        int puntuacionInt = Integer.parseInt(puntuacion);
                        double precioDouble = Double.parseDouble(precio);
                        int unidadesInt = Integer.parseInt(unidades);

                        // Crea una nueva instancia de Juegos con los datos proporcionados
                        Juego nuevoJuego = new Juego();
                        nuevoJuego.setNombre(nuevoNombre);
                        nuevoJuego.setCompania_desarrolladora(compania);
                        nuevoJuego.setGenero(genero);
                        nuevoJuego.setPuntuacion_metacritic(puntuacionInt);
                        nuevoJuego.setPrecio(precioDouble);
                        nuevoJuego.setUnidadesDisponibles(unidadesInt);

                        // Inserta el nuevo juego en la base de datos
                        insercionExitosa = juegoDAO.insertaJuego(nuevoJuego);

                        if (insercionExitosa) {
                            // Establece un atributo para indicar la inserción exitosa
                            request.setAttribute("insercionExitosa", true);
                            response.sendRedirect("administracion.jsp?success=Juego insertado con éxito");
                            return;
                        } else {
                            request.setAttribute("error", "No se pudo insertar el juego");
                        }
                    } else {
                        request.setAttribute("error", "Puntuación, precio y unidades deben ser números válidos");
                    }
                    break;

                case "eliminarJuego":
                    String idJuegoEliminarStr = request.getParameter("id");

                    if (idJuegoEliminarStr != null && !idJuegoEliminarStr.isEmpty()) {
                        try {
                            int idJuegoEliminar = Integer.parseInt(idJuegoEliminarStr);

                            boolean eliminacionExitosa = juegoDAO.borrarJuego(idJuegoEliminar);

                            if (eliminacionExitosa) {
                                response.sendRedirect("administracion.jsp?success=Juego eliminado con éxito");
                                return;
                            } else {
                                request.setAttribute("error", "No se pudo eliminar el juego");
                            }
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "ID de juego a eliminar inválido");
                        }
                    } else {
                        request.setAttribute("error", "ID de juego a eliminar no proporcionado");
                    }
                    break;

                case "editarUsuario":
                    String usuarioIdParam = request.getParameter("id");

                    if (usuarioIdParam != null && !usuarioIdParam.isEmpty()) {
                        try {
                            int usuarioId = Integer.parseInt(usuarioIdParam);

                            Usuario usuario = usuarioDAO.obtenerUsuarioPorId(usuarioId);

                            if (usuario != null) {
                                request.setAttribute("usuario", usuario);
                                request.getRequestDispatcher("editarUsuarioForm.jsp").forward(request, response);
                                return;
                            } else {
                                request.setAttribute("error", "Usuario no encontrado");
                            }
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "ID de usuario inválido");
                        }
                    } else {
                        request.setAttribute("error", "ID de usuario no proporcionado");
                    }
                    break;

                case "guardarEdicionUsuario":
                    int idUsuarioEditar = 0;

                    String nuevoUsername = request.getParameter("username");
                    String newPassword = request.getParameter("password");
                    String nuevoTipoUsuario = request.getParameter("tipodeusuario");
                    String nuevoSaldo = request.getParameter("saldo");

                    String idUsuarioEditarStr = request.getParameter("idUsuario");

                    if (idUsuarioEditarStr != null && !idUsuarioEditarStr.isEmpty()) {
                        try {
                            idUsuarioEditar = Integer.parseInt(idUsuarioEditarStr);

                            // Validar formato de campos numéricos
                            if (validarFormatoDouble(nuevoSaldo)) {
                                // Saldo es un número decimal válido
                                double saldoDouble = Double.parseDouble(nuevoSaldo);

                                // Crear una nueva instancia de Usuario con los datos proporcionados
                                Usuario usuarioActualizado = new Usuario();
                                usuarioActualizado.setId_usuario(idUsuarioEditar);
                                usuarioActualizado.setUsername(nuevoUsername);
                                usuarioActualizado.setPassword(newPassword);
                                usuarioActualizado.setTipodeusuario(Usuario.RolUsuario.valueOf(nuevoTipoUsuario));
                                usuarioActualizado.setSaldo(saldoDouble);

                                // Actualizar el usuario en la base de datos
                                boolean actualizacionExitosa = usuarioDAO.actualizarUsuario(usuarioActualizado);

                                if (actualizacionExitosa) {
                                    response.sendRedirect("administracion.jsp?success=Usuario actualizado con éxito");
                                    return;
                                } else {
                                    request.setAttribute("error", "No se pudo actualizar el usuario");
                                }
                            } else {
                                request.setAttribute("error", "El saldo debe ser un número decimal");
                            }
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "Error al procesar el ID del usuario");
                        }
                    } else {
                        request.setAttribute("error", "ID de usuario no proporcionado");
                    }
                    break;

                case "insertaUsuario":
                    // Recupera datos del formulario de inserción

                    nuevoUsername = request.getParameter("username");
                    newPassword = request.getParameter("password");
                    nuevoTipoUsuario = request.getParameter("tipodeusuario");
                    nuevoSaldo = request.getParameter("saldo");

                    // Validar formato de campos numéricos
                    if (validarFormatoDouble(nuevoSaldo)) {
                        // Saldo es un número decimal válido
                        double saldoDouble = Double.parseDouble(nuevoSaldo);

                        // Crea una nueva instancia de Usuario con los datos proporcionados
                        Usuario nuevoUsuario = new Usuario();
                        nuevoUsuario.setUsername(nuevoUsername);
                        nuevoUsuario.setPassword(newPassword);
                        nuevoUsuario.setTipodeusuario(Usuario.RolUsuario.valueOf(nuevoTipoUsuario));
                        nuevoUsuario.setSaldo(saldoDouble);

                        // Inserta el nuevo usuario en la base de datos
                        insercionExitosa = usuarioDAO.insertarUsuario(nuevoUsuario);

                        if (insercionExitosa) {
                            // Establece un atributo para indicar la inserción exitosa
                            request.setAttribute("insercionExitosa", true);

                            response.sendRedirect("administracion.jsp?success=Usuario insertado con éxito");
                            return;
                        } else {
                            request.setAttribute("error", "No se pudo insertar el usuario");
                        }
                    } else {
                        request.setAttribute("error", "El saldo debe ser un número decimal");
                    }
                    break;

                case "eliminarUsuario":
                    String idUsuarioEliminarStr = request.getParameter("id");

                    if (idUsuarioEliminarStr != null && !idUsuarioEliminarStr.isEmpty()) {
                        try {
                            int idUsuarioEliminar = Integer.parseInt(idUsuarioEliminarStr);

                            boolean eliminacionExitosa = usuarioDAO.eliminarUsuario(idUsuarioEliminar);

                            if (eliminacionExitosa) {
                                response.sendRedirect("administracion.jsp?success=Usuario eliminado con éxito");
                                return;
                            } else {
                                request.setAttribute("error", "No se pudo eliminar el usuario");
                            }
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "ID de usuario a eliminar inválido");
                        }
                    } else {
                        request.setAttribute("error", "ID de usuario a eliminar no proporcionado");
                    }
                    break;
            }
        }

        ArrayList<Consola> listaConsolas = consolaDAO.obtenerConsolas();
        ArrayList<Juego> listaJuegos = juegoDAO.obtenerJuegos();
        ArrayList<Usuario> listaUsuarios = usuarioDAO.obtenerUsuarios();
        request.setAttribute("usuarios", listaUsuarios);
        request.setAttribute("juegos", listaJuegos);
        request.setAttribute("consolas", listaConsolas);
        //Cierro la conexión
        miConexion.desconectar();
        request.getRequestDispatcher("administracion.jsp").forward(request, response);

    }

    private boolean validarFormatoInt(String str) {
        if (str != null && !str.isEmpty()) {
            try {
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    private boolean validarFormatoDouble(String str) {
        if (str != null && !str.isEmpty()) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
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
