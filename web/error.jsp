<%-- 
    Document   : errorCompra
    Created on : 8 ene 2024, 14:24:27
    Author     : SzBel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Error en la compra</title>
</head>
<body>
     <h2>Error</h2>
    <p><%= request.getAttribute("errorMensaje") %></p>
    
</body>
</html>
