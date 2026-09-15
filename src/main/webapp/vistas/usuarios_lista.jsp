<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Usuario" %>
<%
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuarioLogueado == null) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<!--
 Archivo: usuarios_lista.jsp
 Justificación metodológica: Muestra la lista de usuarios. Uso un scriptlet básico para 
 iterar la lista obtenida del Servlet. Uso JSTL (c:forEach) en un entorno real, pero 
 aquí uso código básico de Java para demostrar comprensión del flujo Servlet -> JSP.
-->
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Usuarios</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%= request.getContextPath() %>/vistas/css/estilos.css?v=4" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    
    <div class="container" style="max-width: 900px;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem;">
            <h2>Gestión de Usuarios</h2>
            <a href="vistas/usuario_form.jsp" class="btn-primary" style="display:inline-block; padding:0.5rem 1rem; border-radius:8px; text-decoration:none; color:white; background-color:#3b82f6;">Nuevo Usuario</a>
        </div>

        <div style="display: flex; justify-content: space-between; gap: 1rem; margin-bottom: 1rem; background: #1e293b; padding: 1rem; border-radius: 8px;">
            <form action="<%= request.getContextPath() %>/ServletUsuario" method="GET" style="display: flex; gap: 0.5rem; flex-direction: row; align-items: center;">
                <input type="hidden" name="accion" value="buscar">
                <input type="text" name="parteNombre" placeholder="Buscar por nombre..." style="width: 200px; padding: 0.5rem;">
                <button type="submit" style="padding: 0.5rem 1rem; width: auto;">Buscar</button>
            </form>
            
            <form action="<%= request.getContextPath() %>/ServletUsuario" method="GET" style="display: flex; gap: 0.5rem; flex-direction: row; align-items: center;">
                <input type="hidden" name="accion" value="listarPorRol">
                <select name="rol" style="width:auto; padding: 0.5rem;">
                    <option value="Administrador">Administrador</option>
                    <option value="Operador">Operador</option>
                </select>
                <button type="submit" style="padding: 0.5rem 1rem; width: auto;">Filtrar Rol</button>
                <a href="<%= request.getContextPath() %>/ServletUsuario?accion=listar" style="padding: 0.5rem; text-decoration: none; color: #cbd5e1;">Limpiar</a>
            </form>
        </div>
        
        <% 
            Integer totalUsuariosRol = (Integer) request.getAttribute("totalUsuariosRol");
            String rolBuscado = (String) request.getAttribute("rolBuscado");
            if (totalUsuariosRol != null) {
        %>
            <div style="background: rgba(139, 92, 246, 0.1); padding: 1rem; border-radius: 8px; border: 1px solid #8b5cf6; color: #a78bfa; margin-bottom: 1rem;">
                <strong>Total de usuarios con rol '<%= rolBuscado %>':</strong> <%= totalUsuariosRol %>
            </div>
        <% } %>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Rol</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Usuario> lista = (List<Usuario>) request.getAttribute("listaUsuarios");
                    if (lista != null) {
                        for (Usuario u : lista) {
                %>
                <tr>
                    <td><%= u.getId() %></td>
                    <td><%= u.getNombre() %></td>
                    <td><%= u.getEmail() %></td>
                    <td><%= u.getRol() %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/ServletUsuario?accion=editar&id=<%= u.getId() %>" style="color: #f59e0b; margin-right: 0.5rem;">Editar</a>
                        <a href="<%= request.getContextPath() %>/ServletUsuario?accion=eliminar&id=<%= u.getId() %>" onclick="return confirm('¿Seguro que desea eliminar?');" style="color: #ef4444;">Eliminar</a>
                    </td>
                </tr>
                <% 
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
