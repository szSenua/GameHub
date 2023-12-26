<%-- 
    Document   : mostrarConsolas
    Created on : 26 dic 2023, 13:28:42
    Author     : SzBel
--%>

<%@page import="java.util.Iterator"%>
<%@page import="Entity.Consola"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Consolas</title>
        <style>
            .card-container {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
            }

            .card {
                border: 1px solid #ddd;
                padding: 10px;
                margin: 10px;
                width: 300px;
                display: inline-block;
                vertical-align: top;
                text-align: center;
            }

            .card h3, .card p {
                margin-bottom: 10px;
            }
            .comprar-button {
                background-color: #4CAF50;
                color: white;
                padding: 10px;
                border: none;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 4px 2px;
                cursor: pointer;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <h2>Lista de Consolas</h2>

        <div class="card-container">
            <%                ArrayList<Consola> listaConsolas = (ArrayList<Consola>) request.getAttribute("listaConsolas");

                if (listaConsolas != null) {
                    Iterator<Consola> iterator = listaConsolas.iterator();

                    while (iterator.hasNext()) {
                        Consola consola = iterator.next();
            %>
            <div class="card">
                <h3><%= consola.getNombre()%></h3>
                <p>ID: <%= consola.getId_consola()%></p>
                <p>Compañía Desarrolladora: <%= consola.getCompania_desarrolladora()%></p>
                <p>Precio: <%= consola.getPrecio()%></p>
                <p>Potencia CPU: <%= consola.getPotencia_cpu()%></p>
                <p>Potencia GPU: <%= consola.getPotencia_gpu()%></p>
                <p>Unidades Disponibles <%= consola.getUnidadesDisponibles()%></p>
                <form action="procesarCompra.jsp" method="post">
                    <input type="hidden" name="idConsola" value="<%= consola.getId_consola()%>">
                    <input type="submit" class="comprar-button" name="comprar" value="Comprar">
                </form>
            </div>
            <%
                    }
                }
            %>
        </div>
    </body>
</html>
