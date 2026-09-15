<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
 Archivo: registro.jsp
 Justificación metodológica: Formulario público para que nuevos usuarios puedan
 crearse una cuenta sin necesidad de un administrador. Se comunica con ServletAuth.
-->
<html lang="es">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <title>Registro de Usuario - Gastos</title>
    <link href="<%= request.getContextPath() %>/vistas/css/estilos.css?v=4" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2>Registro de Usuario</h2>
        <% 
            String error = (String) request.getAttribute("error");
            String mensaje = (String) request.getAttribute("mensaje");
            if (error != null) { 
        %>
            <div class="error-msg"><%= error %></div>
        <% } else if (mensaje != null) { %>
            <div style="background-color: #10b981; color: white; padding: 1rem; border-radius: 8px; margin-bottom: 1rem; text-align: center;"><%= mensaje %></div>
        <% } %>
        <form action="../ServletAuth" method="POST">
            <input type="hidden" name="accion" value="registrar">
            <div class="form-group">
                <label for="id">ID de Usuario</label>
                <input type="text" id="id" name="id" required>
            </div>
            <div class="form-group">
                <label for="nombre">Nombre Completo</label>
                <input type="text" id="nombre" name="nombre" required>
            </div>
            <div class="form-group">
                <label for="email">Correo Electrónico</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="clave">Clave</label>
                <input type="password" id="clave" name="clave" required>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn-primary">Registrarse</button>
            </div>
            <div class="form-links">
                <a href="<%= request.getContextPath() %>/vistas/login.jsp">Volver al Login</a>
            </div>
        </form>
    </div>
</body>
</html>
