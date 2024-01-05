<%-- 
    Document   : insertaUsuarioForm
    Created on : 4 ene 2024, 18:45:24
    Author     : SzBel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo Usuario</title>
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
        <h2>Nuevo Usuario</h2>

        <form action="Administracion" method="get">
            <input type="hidden" name="action" value="insertaUsuario">

            <label for="username">Nombre:</label>
            <input type="text" name="username" required><br>

            <label for="password">Contraseña:</label>
            <input type="text" name="password" required><br>

            <label for="tipodeusuario">Tipo de Usuario:</label>
            <select name="tipodeusuario" required>
                <option value="Administrador">Administrador</option>
                <option value="Usuario">Usuario</option>
            </select><br>

            <label for="saldo">Saldo:</label>
            <input type="text" name="saldo" required><br>

            <input type="submit" value="Crear Usuario">
        </form>
    </body>
</html>
