<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modelo.Usuario" %>
<%
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuarioLogueado == null) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        return;
    }
    
    Usuario usuario = (Usuario) request.getAttribute("usuario");
    boolean esEdicion = (usuario != null);
%>
<!DOCTYPE html>
<!--
 Archivo: usuario_form.jsp
 Justificación metodológica: Formulario reutilizable para crear y editar usuarios.
 Si el Servlet envía un objeto "usuario", los campos se prellenan y la acción
 del formulario cambia a "modificar". Si es nulo, la acción es "agregar".
-->
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title><%= esEdicion ? "Editar" : "Nuevo" %> Usuario</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%= request.getContextPath() %>/vistas/css/estilos.css?v=4" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />

    <div class="container">
        <h2><%= esEdicion ? "Editar Usuario" : "Crear Nuevo Usuario" %></h2>
        <form action="<%= request.getContextPath() %>/ServletUsuario" method="POST">
            <input type="hidden" name="accion" value="<%= esEdicion ? "modificar" : "agregar" %>">
            
            <div class="form-group">
                <label>ID de Usuario</label>
                <input type="text" name="id" value="<%= esEdicion ? usuario.getId() : "" %>" <%= esEdicion ? "readonly" : "required" %>>
            </div>
            
            <div class="form-group">
                <label>Correo Electrónico</label>
                <input type="email" name="email" value="<%= esEdicion ? usuario.getEmail() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label>Clave</label>
                <input type="password" name="clave" value="<%= esEdicion ? usuario.getClave() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label>Nombre Completo</label>
                <input type="text" name="nombre" value="<%= esEdicion ? usuario.getNombre() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label>Rol</label>
                <select name="rol" required>
                    <option value="Administrador" <%= (esEdicion && "Administrador".equals(usuario.getRol())) ? "selected" : "" %>>Administrador</option>
                    <option value="Operador" <%= (esEdicion && "Operador".equals(usuario.getRol())) ? "selected" : "" %>>Operador</option>
                </select>
            </div>
            
            <div class="form-actions" style="margin-top: 1rem; display: flex; gap: 1rem;">
                <button type="submit" class="btn-primary">Guardar</button>
                <a href="<%= request.getContextPath() %>/ServletUsuario?accion=listar" class="btn-secondary" style="display:inline-flex; align-items:center; justify-content:center; padding:0.75rem 1.5rem; text-decoration:none; background-color:#64748b; color:white; border-radius:8px;">Cancelar</a>
            </div>
        </form>
    </div>
</body>
</html>
