<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
 Archivo: recuperar_clave.jsp
 Justificación metodológica: Formulario simple para enviar el ID del usuario al ServletAuth 
 y pedir que se imprima la clave en la consola del servidor (requerimiento académico).
-->
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Recuperar Clave</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%= request.getContextPath() %>/vistas/css/estilos.css?v=4" rel="stylesheet">
</head>
<body>
    <div class="container" style="max-width: 400px; margin-top: 5rem;">
        <h2 style="text-align: center; margin-bottom: 1.5rem;">Recuperar Contraseña</h2>
        
        <% 
            String error = (String) request.getAttribute("error");
            String mensaje = (String) request.getAttribute("mensaje");
            if (error != null) { 
        %>
            <div style="background-color: #ef4444; color: white; padding: 1rem; border-radius: 8px; margin-bottom: 1rem; text-align: center;"><%= error %></div>
        <% } else if (mensaje != null) { %>
            <div style="background-color: #10b981; color: white; padding: 1rem; border-radius: 8px; margin-bottom: 1rem; text-align: center;"><%= mensaje %></div>
        <% } %>
        
        <form action="<%= request.getContextPath() %>/ServletAuth" method="POST">
            <input type="hidden" name="accion" value="recuperar">
            <div class="form-group">
                <label for="id">Digite su ID de Usuario</label>
                <input type="text" id="id" name="id" required>
            </div>
            <div class="form-actions" style="margin-top: 1.5rem; display: flex; flex-direction: column; gap: 1rem;">
                <button type="submit" class="btn-primary" style="width: 100%;">Enviar Correo</button>
                <a href="<%= request.getContextPath() %>/vistas/login.jsp" style="text-align: center; color: #3b82f6; text-decoration: none; padding: 0.5rem;">Volver al Login</a>
            </div>
        </form>
    </div>
</body>
</html>
