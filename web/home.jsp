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
            //Obtengo la sesión

            session = request.getSession();

            //Verificar si hay un usuario logueado
            if (session.getAttribute("usuarioLogueado") != null && (boolean) session.getAttribute("usuarioLogueado")) {
                //Obtener el tipo de usuario
                Usuario.RolUsuario rolUsuario = (Usuario.RolUsuario) session.getAttribute("tipodeusuario");

                if (rolUsuario == Usuario.RolUsuario.Administrador) {

        %>
        <h1>Eres admin</h1>
        <%                } else if (rolUsuario == Usuario.RolUsuario.Usuario) {

        %>
        <h1>Eres usuario</h1>

        <%            }

        } else {
        %>
        <h1>Eres un invitado</h1>
        <%
            }


        %>




    </body>
</html>
