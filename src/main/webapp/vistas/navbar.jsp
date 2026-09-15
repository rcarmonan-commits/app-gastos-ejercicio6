<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modelo.Usuario" %>
<%
    Usuario usuarioLogueadoNav = (Usuario) session.getAttribute("usuarioLogueado");
%>
<nav>
    <a href="<%= request.getContextPath() %>/vistas/index.jsp">App Gastos</a>
    
    <div>
        <a href="<%= request.getContextPath() %>/vistas/acerca_de.jsp">Acerca de</a>
        <a href="<%= request.getContextPath() %>/ServletUsuario?accion=listar">Gestión Usuarios</a>
        <a href="<%= request.getContextPath() %>/ServletGasto?accion=listar">Gestión Gastos</a>
        <a href="<%= request.getContextPath() %>/vistas/reportes.jsp">Reportes</a>
        
        <% if (usuarioLogueadoNav != null && "Administrador".equals(usuarioLogueadoNav.getRol())) { %>
            <a href="<%= request.getContextPath() %>/ServletSMTP?accion=ver">Config. SMTP</a>
        <% } %>
    </div>
    
    <div>
        <span>Hola, <strong><%= (usuarioLogueadoNav != null) ? usuarioLogueadoNav.getNombre() : "" %></strong></span>
        <a href="<%= request.getContextPath() %>/ServletAuth?accion=logout">Cerrar Sesión</a>
    </div>
</nav>
