<%-- 
    Document   : menu.jsp
    Created on : 11 dic 2023, 19:27:33
    Author     : SzBel
--%>

<%@page import="Entity.Usuario"%>
<%@page import="Entity.Carrito"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Header</title>
        <style>
            * {
                padding: 0;
                margin: 0;
                outline: none;
                border: none;
                box-sizing: border-box;
            }

            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
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
                position: sticky;
                top: 0;
                z-index: 100;
                width: 100%;
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

                // Obtener el carrito de la sesión
                Carrito carrito = (Carrito) session.getAttribute("carrito");

                // Verificar si el usuario no es nulo antes de acceder a sus atributos
                if (usuario != null) {
                    // Utilizar los atributos del objeto Usuario
                    Usuario.RolUsuario rolUsuario = usuario.getTipodeusuario();
        %>
        <ul>
            <%-- Menú para Admin o Usuario --%>
            <li><a href="<%= request.getContextPath()%>/MostrarConsolas">Consultar consolas</a></li>
            <li><a href="<%= request.getContextPath()%>/MostrarJuegos">Consultar juegos</a></li>
            <li><a href="<%= request.getContextPath()%>/MostrarProductos">Consultar productos</a></li>

            <%-- Enlace del carrito con la cantidad de productos --%>
            <li><a href="mostrarCarrito.jsp">Carrito <%= (carrito != null && carrito.getCantidadTotal() > 0) ? "(" + carrito.getCantidadTotal() + ")" : ""%></a></li>

            <%-- Opciones específicas para Admin --%>
            <% if (rolUsuario == Usuario.RolUsuario.Administrador) { %>
            <li><a href="administracion.jsp">Panel de Administración</a></li>
                <% }%>

            <li style="margin-left: auto;"><a href="<%= request.getContextPath()%>/CerrarSesion">Cerrar sesión</a></li>
        </ul>
        <%
            }
        } else {
            // Menú para Invitado
        %>
        <ul>
            <li><a href="<%= request.getContextPath()%>/MostrarConsolas">Consultar consolas</a></li>
            <li><a href="<%= request.getContextPath()%>/MostrarJuegos">Consultar juegos</a></li>
            <li><a href="<%= request.getContextPath()%>/MostrarProductos">Consultar productos</a></li>

            <li><a href="login.jsp">Iniciar Sesión</a></li>
        </ul>
        <%
            }
        %>

    </body>
</html>
