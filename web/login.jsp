<%-- 
    Document   : login
    Created on : 8 dic 2023, 20:23:57
    Author     : SzBel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>JSP Page</title>
    </head>
    <body>
        <form action="ValidarUsuario" method="post">
            <label for="usuario">Usuario: </label>
            <input type="text" name="usuario"><br>
            <label for="password">Contraseña: </label>
            <input type="password" name="password"><br>
            <input type="submit" value="Enviar" name="enviar">
                  
        </form>
    </body>
</html>
