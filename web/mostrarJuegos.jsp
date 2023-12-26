<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entity.Juego"%>
<%@page import="Entity.Consola"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Juegos</title>
    <style>
        .container {
            display: flex;
            flex-wrap: wrap;
        }

        .filters {
            width: 15%;
            padding: 10px;
            box-sizing: border-box;
        }

        .card-container {
            width: 85%;
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
    

    <div class="container">
        <!-- Filtros -->
        <div class="filters">
            <form action="MostrarJuegos" method="post">
                <label>Filtrar por consolas:</label><br>
                <% 
                    ArrayList<Consola> listaConsolas = (ArrayList<Consola>) request.getAttribute("listaConsolas");

                    if (listaConsolas != null) {
                        for (Consola consola : listaConsolas) {
                            String idConsola = String.valueOf(consola.getId_consola());
                            String[] consolasSeleccionadas = (String[]) request.getAttribute("consolasSeleccionadas");

                            // Verificar si esta consola está seleccionada
                            String checked = (consolasSeleccionadas != null && Arrays.asList(consolasSeleccionadas).contains(idConsola)) ? "checked" : "";
                %>
                <input type="checkbox" name="consolas" value="<%= idConsola%>" <%= checked%>>
                <label><%= consola.getNombre()%></label><br>
                <%
                        }
                    }
                %>
                <input type="submit" value="Filtrar">
                <input type="submit" name="quitarFiltros" value="Quitar Filtros">
            </form>
        </div>

        <!-- Juegos -->
        <div class="card-container">
            <%
                ArrayList<Juego> listaJuegos = (ArrayList<Juego>) request.getAttribute("listaJuegos");

                if (listaJuegos != null) {
                    Iterator<Juego> iterator = listaJuegos.iterator();

                    while (iterator.hasNext()) {
                        Juego juego = iterator.next();
            %>
            <div class="card">
                <h3><%= juego.getNombre()%></h3>
                <p>ID: <%= juego.getId_juego()%></p>
                <p>Compañía Desarrolladora: <%= juego.getCompania_desarrolladora()%></p>
                <p>Género: <%= juego.getGenero()%></p>
                <p>Puntuación Metacritic: <%= juego.getPuntuacion_metacritic()%></p>
                <p>Precio: <%= juego.getPrecio()%></p>
                <p>Unidades Disponibles: <%= juego.getUnidadesDisponibles()%></p>
                <form action="procesarCompra.jsp" method="post">
                    <input type="hidden" name="idJuego" value="<%= juego.getId_juego()%>">
                    <input type="submit" class="comprar-button" name="comprar" value="Comprar">
                </form>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>
</body>
</html>
