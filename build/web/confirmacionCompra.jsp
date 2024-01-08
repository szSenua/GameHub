<%@page import="Conexion.Conexion"%>
<%@ page import="Entity.Usuario"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Entity.Carrito" %>
<%@ page import="Entity.Consola" %>
<%@ page import="Entity.ItemCarritoConsola" %>
<%@ page import="Entity.ItemCarritoJuego" %>
<%@ page import="Entity.Juego" %>
<%@ page import="Handlers.CarritoDAO" %>
<%@ page import="Handlers.UsuarioDAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirmación de Compra</title>
        
    <style>
        body {
            font-family: 'Arial', sans-serif;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tfoot {
            font-weight: bold;
        }

        input[type="submit"] {
            padding: 10px;
            margin-top: 10px;
            cursor: pointer;
        }
        .error-message {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h1>¡Compra Exitosa!</h1>

    <%-- Recupera el ID del ticket desde la clase CarritoDAO --%>
    <% int idTicket = CarritoDAO.ticket; %>
    
    <h2>ID del Ticket: <%= idTicket %></h2>

    <%-- Muestra los detalles del ticket y actualiza el saldo del usuario --%>
    <% 
        Conexion miConexion = new Conexion();
        miConexion.conectar();
        CarritoDAO carritoDAO = new CarritoDAO(miConexion);
        UsuarioDAO usuarioDAO = new UsuarioDAO(miConexion);
        
        // Obtener datos de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        // Obtener detalles del ticket
        ArrayList<ItemCarritoConsola> consolas = carritoDAO.obtenerConsolasPorTicket(idTicket);
        ArrayList<ItemCarritoJuego> juegos = carritoDAO.obtenerJuegosPorTicket(idTicket);
        
        // Calcular el total de la compra
        double totalCarrito = 0;
        for (ItemCarritoConsola consola : consolas) {
            totalCarrito += consola.calcularSubtotal();
        }
        for (ItemCarritoJuego juego : juegos) {
            totalCarrito += juego.calcularSubtotal();
        }
        
        // Actualizar el saldo del usuario
        double nuevoSaldo = usuario.getSaldo() - totalCarrito;
        usuario.setSaldo(nuevoSaldo);
        usuarioDAO.actualizarSaldo(usuario.getId_usuario(), nuevoSaldo);
    %>

    <h3>Productos en el Carrito:</h3>
    <table border="1">
        <thead>
            <tr>
                <th>Nombre</th>
                <th>Tipo</th>
                <th>Precio</th>
                <th>Cantidad</th>
                <th>Subtotal</th>
            </tr>
        </thead>
        <tbody>
            <% 
                for (ItemCarritoConsola consola : consolas) {
            %>
                <tr>
                    <td><%= consola.getConsola().getNombre() %></td>
                    <td>Consola</td>
                    <td><%= consola.getConsola().getPrecio() %></td>
                    <td><%= consola.getCantidad() %></td>
                    <td><%= consola.calcularSubtotal() %></td>
                </tr>
            <%
                }
                for (ItemCarritoJuego juego : juegos) {
            %>
                <tr>
                    <td><%= juego.getJuego().getNombre() %></td>
                    <td>Juego</td>
                    <td><%= juego.getJuego().getPrecio() %></td>
                    <td><%= juego.getCantidad() %></td>
                    <td><%= juego.calcularSubtotal() %></td>
                </tr>
            <%
                }
            %>
        </tbody>
        <tfoot>
            <tr>
                <th colspan="4">Total</th>
                <th><%= totalCarrito %></th>
            </tr>
        </tfoot>
    </table>
    
    <h3>Saldo Actual:</h3>
    <table border="1">
        <thead>
            <tr>
                <th>Saldo Anterior</th>
                <th>Total de la Compra</th>
                <th>Saldo Restante</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><%= usuario.getSaldo() + totalCarrito %></td>
                <td><%= totalCarrito %></td>
                <td><%= usuario.getSaldo() %></td>
            </tr>
        </tbody>
    </table>

    <p>Gracias por tu compra. Recuerda revisar tu correo electrónico para recibir información adicional.</p>

    <%-- Cierro la conexión --%>
    <% miConexion.desconectar(); %>
</body>
</html>
