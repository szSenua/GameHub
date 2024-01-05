<%-- 
    Document   : registro
    Created on : 4 ene 2024, 20:25:59
    Author     : SzBel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro</title>
        <style>
            body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }

        label {
            display: block;
            margin: 10px 0;
            font-weight: bold;
            color: #333;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
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
        
        .error-message {
            color: red;
            margin-bottom: 15px;
        }
        
        #index-link {
                margin-top: 15px;
                display: block;
                color: #333;
                text-decoration: none;
         }
        </style>
    </head>
    <body>
    <form action="ValidaRegistro" method="post">
        
         <%-- Mostrar el mensaje de error si existe --%>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null && !errorMessage.isEmpty()) {
        %>
                <div class="error-message">
                    <%= errorMessage %>
                </div>
        <%
            }
        %>
        
        <label for="username">Nombre de Usuario: </label>
        <input type="text" name="username" required><br>
        <label for="password">Contraseña: </label>
        <input type="password" name="password" required><br>
        <label for="saldo">Saldo: </label>
        <input type="text" name="saldo" required><br>
        <input type="submit" value="Registrarse" name="registrar"> 
        <a href="index.jsp" id="index-link">Volver al Índice</a>
    </form>
</body>
</html>
