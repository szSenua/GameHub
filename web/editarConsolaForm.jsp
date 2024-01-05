<%-- 
    Document   : editarConsola
    Created on : 4 ene 2024, 2:05:49
    Author     : SzBel
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="Entity.Consola"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Consola</title>
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

        <%// Obtener la consola desde el atributo de solicitud
            Consola consola = (Consola) request.getAttribute("consola");
            String error = (String) request.getAttribute("error");

        %>

        <h2>Editar Consola</h2>

        <% if (error != null && !error.isEmpty()) {%>
        <p style="color: red;"><%= error%></p>
        <% } %>

        <% if (consola != null) {%>
        <form action="Administracion" method="get">
            <input type="hidden" name="action" value="guardarEdicionConsola">
            <input type="hidden" name="idConsola" value="<%= consola.getId_consola()%>">

            <label for="nombre">Nombre:</label>
            <input type="text" name="nombre" value="<%= consola.getNombre()%>" required><br>

            <label for="potenciaCpu">Potencia CPU:</label>
            <input type="text" name="potenciaCpu" value="<%= consola.getPotencia_cpu()%>" required><br>

            <label for="potenciaGpu">Potencia GPU:</label>
            <input type="text" name="potenciaGpu" value="<%= consola.getPotencia_gpu()%>" required><br>

            <label for="compania">Compañía:</label>
            <input type="text" name="compania" value="<%= consola.getCompania_desarrolladora()%>" required><br>

            <label for="precio">Precio</label>
            <input type="text" name="precio" value="<%= consola.getPrecio()%>" required><br>

            <label for="unidades">Unidades Disponibles:</label>
            <input type="text" name="unidades" value="<%= consola.getUnidadesDisponibles()%>" required><br>

            <input type="submit" value="Guardar cambios">
        </form>
        <% } else { %>
        <p>Consola no encontrada.</p>
        <% }%>

    </body>
</html>
