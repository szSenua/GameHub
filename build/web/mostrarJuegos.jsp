<%@page import="Entity.Usuario"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Entity.Juego"%>
<%@page import="Entity.Consola"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Mostrar Juegos</title>
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

            label {
                margin-right: 5px;
            }

            input[type="checkbox"] {
                margin-right: 5px;
            }

            input[type="submit"] {
                padding: 8px;
                margin-top: 10px;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            
            .listaConsolas{
                margin-top: .5em;
            }

        </style>
    </head>
    <body>


        <div class="container">
            <!-- Filtros -->
            <div class="filters">
                <form action="MostrarJuegos" method="post">
                    <label>Filtrar por consolas:</label><br>
                    <%                        ArrayList<Consola> listaConsolas = (ArrayList<Consola>) request.getAttribute("listaConsolas");

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
                    session = request.getSession();
                    ArrayList<Juego> listaJuegos = (ArrayList<Juego>) request.getAttribute("listaJuegos");

                    
                    // Ordenar la lista de juegos alfabéticamente por el nombre
                    Collections.sort(listaJuegos, ( j1,     j2) -> j1.getNombre().compareTo(j2.getNombre()));

                    Usuario usuario = (Usuario) session.getAttribute("usuario");
                    if (listaJuegos != null) {
                        Iterator<Juego> iterator = listaJuegos.iterator();

                        while (iterator.hasNext()) {
                            Juego juego = iterator.next();
                            ArrayList<Consola> consolasPorJuego = (ArrayList<Consola>) request.getAttribute("consolasPorJuego_" + juego.getId_juego());

                            String tipoProducto = "juego";
                            String idProducto = "J" + juego.getId_juego();

                            
                %>
                <div class="card">
                    <h3><%= juego.getNombre()%></h3>
                    <p>ID: <%= juego.getId_juego()%></p>
                    <p>Compañía Desarrolladora: <%= juego.getCompania_desarrolladora()%></p>
                    <p>Género: <%= juego.getGenero()%></p>
                    <p>Puntuación Metacritic: <%= juego.getPuntuacion_metacritic()%></p>
                    <p>Precio: <%= juego.getPrecio()%></p>
                    <p>Unidades Disponibles: <%= juego.getUnidadesDisponibles()%></p>
                    
                    <!-- Mostrar las consolas asociadas al juego -->
                    <h4>Consolas:</h4>
                    <div class="listaConsolas">
                        <% if (consolasPorJuego != null && !consolasPorJuego.isEmpty()) { %>
                            <% for (Consola consola : consolasPorJuego) { %>
                                <p><%= consola.getNombre() %></p>
                            <% } %>
                        <% } else { %>
                            <p>No hay consolas asociadas a este juego.</p>
                        <% } %>
                    </div>
                    
                    <form action="AgregarAlCarro" method="post">
                        <input type="hidden" name="tipoProducto" value="<%= tipoProducto%>" />
                        <input type="hidden" name="idProducto" value="<%= idProducto%>" />
                        <%
                            if (usuario != null) {
                                Usuario.RolUsuario rolUsuario = usuario.getTipodeusuario();

                                if (usuario != null && (rolUsuario == Usuario.RolUsuario.Administrador
                                        || rolUsuario == Usuario.RolUsuario.Usuario)) { %>
                        <input type="submit" class="comprar-button" name="comprar" value="Comprar">
                        <% }
                            }%>
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
