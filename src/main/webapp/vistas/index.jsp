<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<!--
 Archivo: index.jsp
 Justificación metodológica: Menú principal. Verifica sesión activa, de lo contrario
 redirige. Implemenúta la barra de navegación responsive con CSS nativo.
-->
<html lang="es">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <title>Panel Principal - App Gastos</title>
    <link href="<%= request.getContextPath() %>/vistas/css/estilos.css?v=4" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />

    <div>
        <div>
            <h1>Bienvenido al Sistema de Gastos</h1>
            <p style="font-size: 1.1rem; color: var(--text-muted); font-weight: normal; margin-top: 1rem;">
                Seleccione una opción del menú superior para continuar.
            </p>
        </div>
    </div>
</body>
</html>
