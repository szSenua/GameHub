<%@page import="Handlers.UsuarioDAO"%>
<%@page import="Handlers.JuegoDAO"%>
<%@page import="Handlers.ConsolaDAO"%>
<%@page import="Conexion.Conexion"%>
<%@ page import="java.util.List" %>
<%@ page import="Handlers.ProductoDAO" %>
<%@ page import="Entity.Consola" %>
<%@ page import="Entity.Juego" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>

<%
// Verificar si hay una sesión activa
    session = request.getSession(false);

    if (session == null || session.getAttribute("usuario") == null) {

        response.sendRedirect("login.jsp");
    } else {
        // Obtener el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Verificar si el usuario tiene el rol de Administrador
        if (usuario.getTipodeusuario() != Usuario.RolUsuario.Administrador) {
            // El usuario no tiene permisos de Administrador, redirigir al índice
            response.sendRedirect("index.jsp");
        }
        // Si llega aquí, el usuario está autenticado y tiene el rol de Administrador, continuar con el contenido de la página
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración</title>

        <style>
            body {
                font-family: 'Arial', sans-serif;
            }

            .table-container {
                padding: 20px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            th, td {
                border: 1px solid #ddd;
                padding: 8px;
                text-align: left;
            }

            th {
                background-color: #f2f2f2;
            }

            tfoot {
                font-weight: bold;
            }

            input[type="submit"] {
                padding: 10px;
                margin-top: 10px;
                cursor: pointer;
            }
            .error-message {
                color: red;
                font-weight: bold;
            }

            .action-link, .edit-link, .delete-link {
                display: inline-block;
                padding: 5px 10px;
                text-decoration: none;
                color: #fff;
                background-color: #007bff;
                border: 1px solid #007bff;
                border-radius: 5px;
            }

            .edit-link {
                background-color: #28a745;
                border: 1px solid #28a745;
            }

            .delete-link {
                background-color: #dc3545;
                border: 1px solid #dc3545;
            }

            .action-link{
                margin: 1em;
            }
        </style>
    </head>
    <body>
        <%        Conexion miConexion = new Conexion();
            miConexion.conectar();
            ConsolaDAO consolaDAO = new ConsolaDAO(miConexion);
            JuegoDAO juegoDAO = new JuegoDAO(miConexion);
            UsuarioDAO usuarioDAO = new UsuarioDAO(miConexion);
            ArrayList<Consola> consolas = consolaDAO.obtenerConsolas();
            ArrayList<Juego> juegos = juegoDAO.obtenerJuegos();
            ArrayList<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
            miConexion.desconectar();
        %>

        <%
            // Verificar si hay un mensaje de error y mostrarlo
            String error = (String) request.getAttribute("error");
            if (error != null && !error.isEmpty()) {
        %>
        <div class="error-message"><%= error%></div>
        <%
            }
        %>
        <h2>Consolas</h2>
        <div class="table-container">
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Potencia CPU</th>
                        <th>Potencia GPU</th>
                        <th>Compañía Desarrolladora</th>
                        <th>Precio</th>
                        <th>Unidades Disponibles</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterar sobre la lista de consolas y mostrar los datos -->
                    <% for (Consola consola : consolas) {%>
                    <tr>
                        <td><%= consola.getId_consola()%></td>
                        <td><%= consola.getNombre()%></td>
                        <td><%= consola.getPotencia_cpu()%></td>
                        <td><%= consola.getPotencia_gpu()%></td>
                        <td><%= consola.getCompania_desarrolladora()%></td>
                        <td><%= consola.getPrecio()%></td>
                        <td><%= consola.getUnidadesDisponibles()%></td>
                        <td>
                            <a class="edit-link" href="Administracion?action=editarConsola&id=<%= consola.getId_consola()%>">Editar</a>
                            <a class="delete-link" href="Administracion?action=eliminarConsola&id=<%= consola.getId_consola()%>">Eliminar</a>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <!-- Agregar consola -->
        <a class="action-link" href="insertaConsolaForm.jsp">Agregar Consola</a>

        <h2>Juegos</h2>
        <div class="table-container">
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Género</th>
                        <th>Puntuación Metacritic</th>
                        <th>Precio</th>
                        <th>Unidades Disponibles</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterar sobre la lista de juegos y mostrar los datos -->
                    <% for (Juego juego : juegos) {%>
                    <tr>
                        <td><%= juego.getId_juego()%></td>
                        <td><%= juego.getNombre()%></td>
                        <td><%= juego.getGenero()%></td>
                        <td><%= juego.getPuntuacion_metacritic()%></td>
                        <td><%= juego.getPrecio()%></td>
                        <td><%= juego.getUnidadesDisponibles()%></td>

                        <td>
                            <a class="edit-link" href="Administracion?action=editarJuego&id=<%= juego.getId_juego()%>">Editar</a>
                            <a class="delete-link" href="Administracion?action=eliminarJuego&id=<%= juego.getId_juego()%>">Eliminar</a>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        <!-- Agregar juego -->
        <a class="action-link" href="insertaJuegoForm.jsp">Agregar Juego</a>


        <h2>Usuarios</h2>
        <div class="table-container">
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Tipo</th>
                        <th>Saldo</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterar sobre la lista de usuarios y mostrar los datos -->
                    <% for (Usuario usuario : usuarios) {%>
                    <tr>
                        <td><%= usuario.getId_usuario()%></td>
                        <td><%= usuario.getUsername()%></td>
                        <td><%= usuario.getTipodeusuario()%></td>
                        <td><%= usuario.getSaldo()%></td>

                        <td>
                            <a class="edit-link" href="Administracion?action=editarUsuario&id=<%= usuario.getId_usuario()%>">Editar</a>
                            <a class="delete-link" href="Administracion?action=eliminarUsuario&id=<%= usuario.getId_usuario()%>">Eliminar</a>
                        </td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>

        <!-- Agregar usuario -->
        <a class="action-link" href="insertaUsuarioForm.jsp">Agregar Usuario</a>
    </body>
</html>
