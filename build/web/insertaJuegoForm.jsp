<%-- 
    Document   : insertaJuegoForm
    Created on : 4 ene 2024, 16:44:03
    Author     : SzBel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo Juego</title>
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
        <h2>Nuevo Juego</h2>
        
        <form action="Administracion" method="get">
            <input type="hidden" name="action" value="insertaJuego">
            
            <label for="nombre">Nombre:</label>
            <input type="text" name="nombre" required><br>
            
            <label for="compania">Compañía:</label>
            <input type="text" name="compania" required><br>
            
            <label for="genero">Género:</label>
            <input type="text" name="genero" required><br>
            
            <label for="puntuacion_metacritic">Puntuación Metacritic: </label>
            <input type="text" name="puntuacion_metacritic" required><br>
            
            <label for="precio">Precio:</label>
            <input type="text" name="precio" required><br>

            <label for="unidades">Unidades Disponibles:</label>
            <input type="text" name="unidades" required><br>
            
            <input type="submit" value="Insertar Juego">
        </form>
    </body>
</html>
