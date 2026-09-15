<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
 Archivo: login.jsp
 Justificación metodológica: Interfaz de autenticación con Bootstrap. 
 En un entorno de aprendizaje, es importante tener un punto de entrada seguro
 antes de acceder a las funcionalidades del sistema (index.jsp).
-->
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Gastos</title>
    
    <link href="<%= request.getContextPath() %>/vistas/css/estilos.css?v=4" rel="stylesheet">
</head>
<body>
    <div class="login-container">
        <h2>Ingreso al Sistema</h2>
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
            <input type="hidden" name="accion" value="login">
            <div class="form-group">
                <label for="id">ID de Usuario</label>
                <input type="text" id="id" name="id" required>
            </div>
            <div class="form-group">
                <label for="clave">Clave</label>
                <input type="password" id="clave" name="clave" required>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn-primary">Iniciar Sesión</button>
            </div>
            <div class="form-links">
                <a href="<%= request.getContextPath() %>/vistas/registro.jsp">Registrar Usuario</a>
                <a href="<%= request.getContextPath() %>/vistas/recuperar_clave.jsp">¿Olvidó su clave?</a>
            </div>
        </form>
    </div>
</body>
</html>
