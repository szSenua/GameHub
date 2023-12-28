<%@page import="Conexion.Conexion"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Entity.Carrito" %>
<%@ page import="Entity.Consola" %>
<%@ page import="Entity.ItemCarritoConsola" %>
<%@ page import="Entity.ItemCarritoJuego" %>
<%@ page import="Entity.Juego" %>
<%@ page import="Handlers.CarritoDAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Confirmación de Compra</title>
</head>
<body>
    <h1>¡Compra Exitosa!</h1>

    <%-- Recupera el ID del ticket desde la clase CarritoDAO --%>
    <% int idTicket = CarritoDAO.ticket; %>
    
    <h2>ID del Ticket: <%= idTicket %></h2>

    <%-- Muestra los detalles del ticket --%>
    <% 
        Conexion miConexion = new Conexion();
        miConexion.conectar();
        CarritoDAO carritoDAO = new CarritoDAO(miConexion);
        
        // Obtener detalles del ticket
        ArrayList<ItemCarritoConsola> consolas = carritoDAO.obtenerConsolasPorTicket(idTicket);
        ArrayList<ItemCarritoJuego> juegos = carritoDAO.obtenerJuegosPorTicket(idTicket);
        
        // Mostrar consolas
        if (!consolas.isEmpty()) {
    %>
        <h3>Consolas:</h3>
        <ul>
            <% for (ItemCarritoConsola consola : consolas) { %>
                <li><%= consola.getConsola().getNombre() %>, Cantidad: <%= consola.getCantidad() %></li>
            <% } %>
        </ul>
    <% } %>

    <%-- Mostrar juegos --%>
    <% if (!juegos.isEmpty()) { %>
        <h3>Juegos:</h3>
        <ul>
            <% for (ItemCarritoJuego juego : juegos) { %>
                <li><%= juego.getJuego().getNombre() %>, Cantidad: <%= juego.getCantidad() %></li>
            <% } %>
        </ul>
    <% } %>

    <p>Gracias por tu compra. Recuerda revisar tu correo electrónico para recibir información adicional.</p>

    <%-- Cierra la conexión --%>
    <% miConexion.desconectar(); %>
</body>
</html>

