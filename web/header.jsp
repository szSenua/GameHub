<%-- 
    Document   : menu.jsp
    Created on : 11 dic 2023, 19:27:33
    Author     : SzBel
--%>

<%@page import="Entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Header</title>
    <style>
        * {
            padding: 0;
            margin: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
        }

        h1 {
            color: #333;
        }

        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #333;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        li {
            float: left;
        }

        li a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }

        li a:hover {
            background-color: #111;
        }
    </style>
</head>
<body>

<%
    // Obtengo la sesión
    session = request.getSession();

    // Verificar si hay un usuario logueado
    if (session.getAttribute("usuarioLogueado") != null && (boolean) session.getAttribute("usuarioLogueado")) {
        // Obtener el objeto Usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Verificar si el usuario no es nulo antes de acceder a sus atributos
        if (usuario != null) {
            // Utilizar los atributos del objeto Usuario
            String nombreUsuario = usuario.getUsername();
            Usuario.RolUsuario rolUsuario = usuario.getTipodeusuario();
%>
            <ul>
                <%-- Menú para Admin o Usuario --%>
                <li><a href="#">Consultar consolas</a></li>
                <li><a href="#">Consultar juegos de cada consola</a></li>
                <li><a href="<%= request.getContextPath() %>/MostrarJuegos">Consultar juegos</a></li>
                <li><a href="#">Consultar productos</a></li>

                <%-- Opciones específicas para Admin --%>
                <% if (rolUsuario == Usuario.RolUsuario.Administrador) { %>
                    <li><a href="#">Panel de Administración</a></li>
                <% } %>

                <li style="margin-left: auto;"><a href="cerrarSesion.jsp">Cerrar sesión</a></li>
            </ul>
<%
        }
    } else {
        // Menú para Invitado
%>
        <ul>
            <li><a href="#">Consultar consolas</a></li>
            <li><a href="#">Consultar juegos de cada consola</a></li>
            <li><a href="<%= request.getContextPath() %>/MostrarJuegos">Consultar juegos</a></li>
            <li><a href="#">Consultar productos</a></li>
            <li style="margin-left: auto;"><a href="#">Iniciar sesión</a></li>
        </ul>
<%
    }
%>

</body>
</html>

