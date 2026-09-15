<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modelo.ConfiguracionSMTP" %>
<%@ page import="modelo.Usuario" %>
<%
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuarioLogueado == null || !"Administrador".equals(usuarioLogueado.getRol())) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        return;
    }
    ConfiguracionSMTP configSMTP = (ConfiguracionSMTP) request.getAttribute("configSMTP");
%>
<!DOCTYPE html>
<!--
 Archivo: smtp_form.jsp
 Justificación metodológica: Formulario exclusivo para Administradores 
 para configurar las credenciales del servidor de correos sión quemarlas en el código.
-->
<html lang="es">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <title>Configuración SMTP</title>
    <link href="<%= request.getContextPath() %>/vistas/css/estilos.css?v=4" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    
    <div>
        <h2>Configuración del Servidor de Correos (SMTP)</h2>
        
        <% 
            String error = (String) request.getAttribute("error");
            String mensaje = (String) request.getAttribute("mensaje");
            if (error != null) { 
        %>
            <div><%= error %></div>
        <% } else if (mensaje != null) { %>
            <div><%= mensaje %></div>
        <% } %>

        <div>
            <form action="<%= request.getContextPath() %>/ServletSMTP" method="POST">
                <input type="hidden" name="accion" value="guardar">
                <input type="hidden" name="id" value="<%= configSMTP != null ? configSMTP.getId() : "" %>">
                
                <label for="host">Host SMTP</label>
                <input type="text" id="host" name="host" value="<%= configSMTP != null ? configSMTP.getHost() : "" %>" required>
                
                <label for="puerto">Puerto</label>
                <input type="text" id="puerto" name="puerto" value="<%= configSMTP != null ? configSMTP.getPuerto() : "" %>" required>
                
                <label for="usuario">Usuario (Correo)</label>
                <input type="text" id="usuario" name="usuario" value="<%= configSMTP != null ? configSMTP.getUsuario() : "" %>" required>
                
                <label for="clave">Clave</label>
                <input type="password" id="clave" name="clave" value="<%= configSMTP != null ? configSMTP.getClave() : "" %>" required>
                
                <button type="submit">Guardar Configuración</button>
            </form>
        </div>
    </div>
</body>
</html>
