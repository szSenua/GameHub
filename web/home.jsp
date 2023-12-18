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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <%
            // Obtengo la sesión
            session = request.getSession();

            // Verificar si hay un usuario logueado
            if (session.getAttribute("usuarioLogueado") != null && (boolean) session.getAttribute("usuarioLogueado")) {
                // Obtener el objeto Usuario de la sesión
                Usuario usuario = (Usuario) session.getAttribute("usuario");

                // Utilizar los atributos del objeto Usuario
                String nombreUsuario = usuario.getUsername();
                Usuario.RolUsuario rolUsuario = usuario.getTipodeusuario();

                %>
                <h1>Bienvenido, <%= nombreUsuario %></h1>
                <p>Tu rol es: <%= rolUsuario %></p>
                <%
            } else {
                %>
                <h1>Eres un invitado</h1>
                <%
            }
        %>
    </body>
</html>
