<%-- 
    Document   : index.jsp
    Created on : 20 dic 2023, 21:40:51
    Author     : SzBel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        
        <title>índice</title>
        
        <style>
            
            * {
                padding: 0;
                margin: 0;
                outline: none;
                border: none;
                box-sizing: border-box;
            }
            
            .container{
              
                position: relative;
            }

            .container .slide-container .slide{
                min-height: 95.2vh;
                position: relative;
                overflow: hidden;
                z-index: 1;
            }

            .container .slide-container .slide video{
                position: absolute;
                top: 0;
                left: 0;
                z-index: -1;
                height: 95.2vh;
                width: 100%;
                object-fit: cover;
                animation:fadeIn .4s linear;
            }
            
            @keyframes fadeIn{
                0%{
                    transform: scale(1.5);
                }
            }

            .container .slide-container .slide .content{
                min-height: 95.2vh;
                width: 100%;
                display: flex;
                align-items: center;
                justify-content: center;
                flex-flow: column;
                background: rgba(0,0, 0, 0.7);
                text-align: center;
                z-index: 2;
            }

            .container .slide-container .slide .content h3{
                font-size: 60px;
                color: #fff;
                text-shadow: 0 5px 10px rgba(0, 0, 0, .3);
                animation:animate .4s linear .4s backwards;
            }

            .container .slide-container .slide .content p{
                font-size: 15px;
                color: #eee;
                padding: 5px 0;
                font-weight: lighter;
                text-shadow: 0 5px 10px rgba(0, 0, 0, .3);
                max-width: 700px;
                animation:animate .4s linear .6s backwards;
            }

            .container .slide-container .slide .content .btn {
                display: inline-block;
                padding: 9px 30px;
                background: #fff;
                box-shadow: 0 5px 10px rgba(0, 0, 0, .3);
                color: #333;
                font-weight: bold;
                font-size: 17px;
                margin-top: 10px;
                letter-spacing: 0; 
                transition: letter-spacing 0.2s linear;
                text-decoration: none;
                text-transform: uppercase;
                animation:animate .4s linear 1s backwards;
            }

            .container .slide-container .slide .content .btn:hover {
                letter-spacing: 0.2em;
            }
            
            @keyframes animate{
                0% {
                    opacity: 0;
                    transform:scale(.5) translateY(-50px); 
                }
            }
           
        </style>
    </head>

    <body>
        <%            session = request.getSession();
        %>


        <div class="container">
            <div class="slide-container active">
                <div class="slide">
                    <div class="content">
                        <h3>Próximos Lanzamientos</h3>
                        <a href="<%= request.getContextPath()%>/MostrarJuegos" class="btn">Ir a la Tienda</a>
                    </div>
                    <video src="videos/Próximos JUEGOS de PS5 en 2024 PlayStation España.mp4" muted autoplay loop></video> 
                </div>
            </div>
        </div>

        
    </body>
</html>
