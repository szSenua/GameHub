<%-- 
    Document   : editarJuegoForm
    Created on : 4 ene 2024, 17:00:02
    Author     : SzBel
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="Entity.Juego"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Juego</title>
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
        
        <%            // Obtener el juego desde el atributo de solicitud
            Juego juego = (Juego) request.getAttribute("juego");
            String error = (String) request.getAttribute("error");
            


        %>
        <h2>Editar Juego</h2>

        <% if (error != null && !error.isEmpty()) {%>
        <p style="color: red;"><%= error%></p>
        <% } %>

        <% if (juego != null) {%>
        <form action="Administracion" method="get">
            <input type="hidden" name="action" value="guardarEdicionJuego">
            <input type="hidden" name="idJuego" value="<%= juego.getId_juego() %>">

            <label for="nombre">Nombre:</label>
            <input type="text" name="nombre" value="<%= juego.getNombre()%>" required><br>

            <label for="compania">Compañía:</label>
            <input type="text" name="compania" value="<%= juego.getCompania_desarrolladora()%>" required><br>

            <label for="genero">Género</label>
            <input type="text" name="genero" value="<%= juego.getGenero() %>" required><br>
            
            <label for="puntuacion_metacritic">Puntuación Metacritic:</label>
            <input type="text" name="puntuacion_metacritic" value="<%= juego.getPuntuacion_metacritic() %>" required><br>
            
            <label for="precio">Precio</label>
            <input type="text" name="precio" value="<%= juego.getPrecio()%>" required><br>

            <label for="unidades">Unidades Disponibles:</label>
            <input type="text" name="unidades" value="<%= juego.getUnidadesDisponibles()%>" required><br>

            <input type="submit" value="Guardar cambios">
        </form>
        <% } else { %>
        <p>Juego no encontrado.</p>
        <% }%>

    </body>
</html>
