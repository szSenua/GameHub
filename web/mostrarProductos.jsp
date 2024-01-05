<%-- 
    Document   : mostrarProductos
    Created on : 26 dic 2023, 14:58:28
    Author     : SzBel
--%>
<%@page import="Entity.Usuario"%>
<%@page import="java.util.Collections"%>
<%@page import="Entity.Juego"%>
<%@page import="Entity.Consola"%>
<%@page import="java.util.Iterator"%>
<%@page import="Entity.Producto"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mostrar Productos</title>
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
        display: flex; 
        flex-direction: column;
        align-items: center; 
        text-align: center;
        justify-content: center;
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
        <div class="card-container">
            <%            
                session = request.getSession();
                ArrayList<Producto> listaProductos = (ArrayList<Producto>) request.getAttribute("listaProductos");
                Usuario usuario = (Usuario) session.getAttribute("usuario");

                // Ordenar la lista de productos alfabéticamente por el nombre
                Collections.sort(listaProductos, ( p1,         p2) -> p1.getNombre().compareTo(p2.getNombre()));

                if (listaProductos != null) {
                    Iterator<Producto> iterator = listaProductos.iterator();

                    while (iterator.hasNext()) {
                        Producto producto = iterator.next();

                        // Evitar el operador ternario que tiene problemas con las comillas
                        String tipoProducto = "";
                        String idProducto = "";

                        // Verifica el tipo de producto y asigna valores específicos
                        if (producto instanceof Consola) {
                            tipoProducto = "consola";
                            idProducto = "C" + ((Consola) producto).getId_consola();
                        } else if (producto instanceof Juego) {
                            tipoProducto = "juego";
                            idProducto = "J" + ((Juego) producto).getId_juego();
                        }

                        //Testing
                        /*System.out.println("Nombre: " + producto.getNombre());
                            System.out.println("Precio: " + producto.getPrecio());
                            System.out.println("Tipo: " + tipoProducto);
                            System.out.println("ID: " + idProducto);*/
            %>
            <div class="card">
                <h3><%= producto.getNombre()%></h3>
                <p>Precio: <%= producto.getPrecio()%></p>
                <!-- Verifica el tipo de producto y muestra atributos específicos -->
                <% if (producto instanceof Consola) {%>
                <p>ID: <%= ((Consola) producto).getId_consola()%></p>
                <p>Potencia CPU: <%= ((Consola) producto).getPotencia_cpu()%></p>
                <p>Potencia GPU: <%= ((Consola) producto).getPotencia_gpu()%></p>
                <p>Compañía: <%= ((Consola) producto).getCompania_desarrolladora()%></p>
                <p>Unidades Disponibles <%= ((Consola) producto).getUnidadesDisponibles() %></p>
                <% } else if (producto instanceof Juego) {%>
                <p>ID: <%= ((Juego) producto).getId_juego()%></p>
                <p>Género: <%= ((Juego) producto).getGenero()%></p>
                <p>Puntuación Metacritic: <%= ((Juego) producto).getPuntuacion_metacritic()%></p>
                <p>Unidades Disponibles: <%= ((Juego) producto).getUnidadesDisponibles() %></p>
                <% }%>
                <form action="AgregarAlCarro" method="post">
                    <!-- Utiliza las variables intermedias -->
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
    </body>
</html>
