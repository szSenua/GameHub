<%@ page import="Entity.Carrito"%>
<%@ page import="Entity.ItemCarritoConsola"%>
<%@ page import="Entity.ItemCarritoJuego"%>
<%@ page import="java.util.List"%>
<%@ page import="Entity.Consola"%>
<%@ page import="Entity.Juego"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mostrar Carrito</title>

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
    </style>
</head>
<body>
    <h2>Productos en el Carrito</h2>

    <%
        // Verificar si hay una sesión activa
        session = request.getSession(false);

        if (session == null || session.getAttribute("usuario") == null) {
            // No hay sesión activa o no se ha autenticado, redirigir a la página de login
            response.sendRedirect("login.jsp");
        } else {
            // El usuario está autenticado, continuar con el contenido de la página

            Carrito carrito = (Carrito) session.getAttribute("carrito");

            if (carrito != null) {
                List<ItemCarritoConsola> consolas = carrito.getConsolas();
                List<ItemCarritoJuego> juegos = carrito.getJuegos();

                if (!consolas.isEmpty() || !juegos.isEmpty()) {
    %>
                <form action="GestionarCarrito" method="post">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Tipo</th>
                                <th>Precio</th>
                                <th>Cantidad</th>
                                <th>Subtotal</th>
                                <th>Eliminar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                double totalCarrito = 0;
                                for (ItemCarritoConsola item : consolas) {
                                    Consola consola = item.getConsola();
                            %>
                                    <tr>
                                        <td><%= consola.getId_consola()%></td>
                                        <td><%= consola.getNombre()%></td>
                                        <td><%= consola.getTipoDeProducto()%></td>
                                        <td><%= consola.getPrecio()%></td>
                                        <td><%= item.getCantidad()%></td>
                                        <td><%= item.calcularSubtotal()%></td>
                                        <td>
                                            <input type="checkbox" name="consolaIds" value="<%= consola.getId_consola()%>">
                                        </td>
                                    </tr>
                            <%
                                    totalCarrito += item.calcularSubtotal();
                                }
                                for (ItemCarritoJuego item : juegos) {
                                    Juego juego = item.getJuego();
                            %>
                                    <tr>
                                        <td><%= juego.getId_juego()%></td>
                                        <td><%= juego.getNombre()%></td>
                                        <td><%= juego.getTipoDeProducto()%></td>
                                        <td><%= juego.getPrecio()%></td>
                                        <td><%= item.getCantidad()%></td>
                                        <td><%= item.calcularSubtotal()%></td>
                                        <td>
                                            <input type="checkbox" name="juegoIds" value="<%= juego.getId_juego()%>">
                                        </td>
                                    </tr>
                            <%
                                    totalCarrito += item.calcularSubtotal();
                                }
                            %>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="5">Total</th>
                                <th><%= totalCarrito%></th>
                                <td>
                                    <input type="submit" name="accion" value="Eliminar seleccionados">
                                </td>
                            </tr>
                        </tfoot>
                    </table>

                    <input type="submit" name="accion" value="Procesar Compra">
                </form>
    <%
            } else {
    %>
                <p>No hay productos en el carrito.</p>
    <%
            }
        } else {
    %>
            <p>El carrito está vacío.</p>
    <%
        }
}
    %>
</body>
</html>
