<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Gasto" %>
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
 Archivo: gastos_lista.jsp
 Justificación metodológica: Muestra la lista de gastos. Incluye formularios GET
 para ejecutar los reportes exigidos en el ejercicio 6 (filtrar por fechas, sumar IVA por lugar).
-->
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Lista de Gastos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%= request.getContextPath() %>/vistas/css/estilos.css?v=5" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    
    <div class="container" style="max-width: 1100px;">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem;">
            <h2>Gestión de Gastos</h2>
            <a href="vistas/gasto_form.jsp" class="btn-primary" style="display:inline-block; padding:0.5rem 1rem; border-radius:8px; text-decoration:none; color:white; background-color:#3b82f6;">Nuevo Gasto</a>
        </div>

        <!-- Sección de Reportes -->
        <div style="display: flex; justify-content: space-between; gap: 1rem; margin-bottom: 1rem; background: #1e293b; padding: 1rem; border-radius: 8px;">
            <div style="display: flex; flex-direction: column; width: 100%; gap: 1rem;">
                
                <div style="display: flex; justify-content: space-between; gap: 1rem; flex-wrap: wrap;">
                    <!-- Reporte 1: Filtrar entre fechas -->
                    <form action="<%= request.getContextPath() %>/ServletGasto" method="GET" style="display: flex; gap: 0.5rem; flex-direction: row; align-items: center;">
                        <input type="hidden" name="accion" value="listarRangoFechas">
                        <label style="margin-bottom: 0;">Fechas:</label>
                        <input type="date" name="fechaInicio" required style="width: auto; padding: 0.5rem;">
                        <span style="color: #cbd5e1;">-</span>
                        <input type="date" name="fechaFin" required style="width: auto; padding: 0.5rem;">
                        <button type="submit" style="padding: 0.5rem 1rem; width: auto;">Buscar</button>
                    </form>
                    
                    <!-- Reporte 2: Sumar IVA por lugar -->
                    <form action="<%= request.getContextPath() %>/ServletGasto" method="GET" style="display: flex; gap: 0.5rem; flex-direction: row; align-items: center;">
                        <input type="hidden" name="accion" value="sumarPorLugar">
                        <label style="margin-bottom: 0;">Lugar:</label>
                        <input type="text" name="lugar" placeholder="Ej. Bogotá" required style="width: 200px; padding: 0.5rem;">
                        <button type="submit" style="padding: 0.5rem 1rem; width: auto;">Sumar Total</button>
                        <a href="<%= request.getContextPath() %>/ServletGasto?accion=listar" style="padding: 0.5rem; text-decoration: none; color: #cbd5e1;">Limpiar</a>
                    </form>
                </div>
                
                <% 
                    Double totalSuma = (Double) request.getAttribute("totalSuma");
                    String lugarSuma = (String) request.getAttribute("lugarSuma");
                    if (totalSuma != null) {
                %>
                    <div style="background: rgba(59, 130, 246, 0.1); padding: 1rem; border-radius: 8px; border: 1px solid #3b82f6; color: #60a5fa; text-align: right;">
                        <strong>Total de gastos con IVA en <%= lugarSuma %>:</strong> $<%= String.format("%.2f", totalSuma) %>
                    </div>
                <% } %>
                
                <% 
                    Double totalRango = (Double) request.getAttribute("totalRango");
                    java.sql.Date fechaInicioRango = (java.sql.Date) request.getAttribute("fechaInicioRango");
                    java.sql.Date fechaFinRango = (java.sql.Date) request.getAttribute("fechaFinRango");
                    if (totalRango != null) {
                %>
                    <div style="background: rgba(16, 185, 129, 0.1); padding: 1rem; border-radius: 8px; border: 1px solid #10b981; color: #34d399; text-align: right; margin-top: 0.5rem;">
                        <strong>Gran Total Gastado (<%= fechaInicioRango %> al <%= fechaFinRango %>):</strong> $<%= String.format("%.2f", totalRango) %>
                    </div>
                <% } %>
                
                <% 
                    Usuario usuarioResumen = (Usuario) request.getAttribute("usuarioResumen");
                    Double totalGastosUsuario = (Double) request.getAttribute("totalGastosUsuario");
                    Integer cantidadGastos = (Integer) request.getAttribute("cantidadGastos");
                    if (usuarioResumen != null) {
                %>
                    <div style="background: rgba(245, 158, 11, 0.1); padding: 1rem; border-radius: 8px; border: 1px solid #f59e0b; color: #fbbf24; margin-top: 0.5rem;">
                        <h3 style="margin: 0 0 0.5rem 0;">Resumen Analítico de Gastos</h3>
                        <p style="margin: 0.25rem 0;"><strong>Usuario:</strong> <%= usuarioResumen.getNombre() %> (ID: <%= usuarioResumen.getId() %>)</p>
                        <p style="margin: 0.25rem 0;"><strong>Total de compras:</strong> <%= cantidadGastos %></p>
                        <p style="margin: 0.25rem 0; font-size: 1.1rem;"><strong>Gran Total Gastado:</strong> $<%= String.format("%.2f", totalGastosUsuario) %></p>
                    </div>
                <% } %>
            </div>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Fecha</th>
                    <th>Valor Sin IVA</th>
                    <th>IVA</th>
                    <th>Valor Con IVA</th>
                    <th>Usuario</th>
                    <th>Lugar</th>
                    <th>Descripción</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Gasto> lista = (List<Gasto>) request.getAttribute("listaGastos");
                    if (lista != null) {
                        for (Gasto g : lista) {
                %>
                <tr>
                    <td><%= g.getIdGasto() %></td>
                    <td><%= g.getFecha() %></td>
                    <td>$<%= g.getValorTotalSinIVA() %></td>
                    <td>$<%= g.getIvaTotal() %></td>
                    <td>$<%= g.getValorTotalConIVA() %></td>
                    <td><%= g.getNombreUsuario() %></td>
                    <td><%= g.getLugar() %></td>
                    <td><%= g.getDescripcion() %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/ServletGasto?accion=editar&idGasto=<%= g.getIdGasto() %>" style="color: #f59e0b; margin-right: 0.5rem;">Editar</a>
                        <a href="<%= request.getContextPath() %>/ServletGasto?accion=eliminar&idGasto=<%= g.getIdGasto() %>" onclick="return confirm('¿Seguro que desea eliminar?');" style="color: #ef4444;">Eliminar</a>
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
