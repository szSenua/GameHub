<%-- 
    Document   : editarUsuarioForm
    Created on : 4 ene 2024, 19:12:44
    Author     : SzBel
--%>

<%@page import="Entity.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Usuario</title>
        <style>
            h2 {
                color: #333;
            }

            form {
                max-width: 400px;
                margin: 0 auto;
            }

            label {
                display: block;
                margin-bottom: 5px;
                color: #555;
            }

            input[type="text"] {
                width: 100%;
                padding: 8px;
                margin-bottom: 10px;
                box-sizing: border-box;
            }

            select {
                width: 100%;
                padding: 8px;
                margin-bottom: 10px;
                box-sizing: border-box;
            }

            input[type="submit"] {
                background-color: #4CAF50;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            input[type="submit"]:hover {
                background-color: #45a049;
            }

            p.error {
                color: red;
            }
        </style>
    </head>
    <body>
        <%            // Obtener el usuario desde el atributo de solicitud
            Usuario usuario = (Usuario) request.getAttribute("usuario");
            String error = (String) request.getAttribute("error");


        %>
        <h2>Editar Usuario</h2>

        <% if (usuario != null) {%>
        <form action="Administracion" method="get">
            <input type="hidden" name="action" value="guardarEdicionUsuario">
            <input type="hidden" name="idUsuario" value="<%= usuario.getId_usuario()%>">

            <label for="username">Nombre:</label>
            <input type="text" name="username" value="<%= usuario.getUsername()%>" required><br>

            <label for="password">Contraseña:</label>
            <input type="text" name="password" value="<%= usuario.getPassword()%>" required><br>

            <label for="tipodeusuario">Tipo de Usuario:</label>
            <select name="tipodeusuario" required>
                <option value="Administrador" <%= (usuario.getTipodeusuario() != null && usuario.getTipodeusuario().equals(Usuario.RolUsuario.Administrador)) ? "selected" : ""%>>Administrador</option>
                <option value="Usuario" <%= (usuario.getTipodeusuario() != null && usuario.getTipodeusuario().equals(Usuario.RolUsuario.Usuario)) ? "selected" : ""%>>Usuario</option>
            </select><br>

            <label for="saldo">Saldo:</label>
            <input type="text" name="saldo" value="<%= usuario.getSaldo()%>" required><br>

            <input type="submit" value="Guardar Cambios">
        </form>
            <% } else { %>
        <p>Usuario no encontrado.</p>
        <% }%>
    </body>
</html>
