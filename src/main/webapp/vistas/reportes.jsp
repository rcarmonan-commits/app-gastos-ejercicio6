<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modelo.Usuario" %>
<%
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuarioLogueado == null) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reportes del Sistema</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%= request.getContextPath() %>/vistas/css/estilos.css?v=4" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    
    <div class="container" style="max-width: 800px;">
        <h2 style="margin-bottom: 2rem;">Centro de Reportes</h2>
        
        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 1.5rem;">
            
            <!-- Reporte: Sumar Gastos por Lugar -->
            <div style="background: #1e293b; padding: 1.5rem; border-radius: 8px; border: 1px solid #334155;">
                <h3 style="font-size: 1.25rem; margin-bottom: 1rem; color: #3b82f6;">Sumar Gastos por Lugar</h3>
                <form action="<%= request.getContextPath() %>/ServletGasto" method="GET" style="display: flex; flex-direction: column; gap: 1rem;">
                    <input type="hidden" name="accion" value="sumarPorLugar">
                    <div class="form-group" style="margin-bottom: 0;">
                        <label>Nombre del Lugar</label>
                        <input type="text" name="lugar" placeholder="Ej: Supermercado" required>
                    </div>
                    <button type="submit" class="btn-primary">Calcular Suma</button>
                </form>
            </div>
            
            <!-- Reporte: Gastos por Rango de Fechas -->
            <div style="background: #1e293b; padding: 1.5rem; border-radius: 8px; border: 1px solid #334155;">
                <h3 style="font-size: 1.25rem; margin-bottom: 1rem; color: #3b82f6;">Gastos por Rango de Fechas</h3>
                <form action="<%= request.getContextPath() %>/ServletGasto" method="GET" style="display: flex; flex-direction: column; gap: 1rem;">
                    <input type="hidden" name="accion" value="listarRangoFechas">
                    <div class="form-group" style="margin-bottom: 0;">
                        <label>Fecha de Inicio</label>
                        <input type="date" name="fechaInicio" required>
                    </div>
                    <div class="form-group" style="margin-bottom: 0;">
                        <label>Fecha de Fin</label>
                        <input type="date" name="fechaFin" required>
                    </div>
                    <button type="submit" class="btn-primary">Generar Reporte</button>
                </form>
            </div>
            <!-- Reporte: Usuarios por Rol -->
            <div style="background: #1e293b; padding: 1.5rem; border-radius: 8px; border: 1px solid #334155;">
                <h3 style="font-size: 1.25rem; margin-bottom: 1rem; color: #3b82f6;">Usuarios por Rol</h3>
                <form action="<%= request.getContextPath() %>/ServletUsuario" method="GET" style="display: flex; flex-direction: column; gap: 1rem;">
                    <input type="hidden" name="accion" value="listarPorRol">
                    <div class="form-group" style="margin-bottom: 0;">
                        <label>Seleccione el Rol</label>
                        <select name="rol" required style="width: 100%; padding: 0.75rem; border-radius: 4px; border: 1px solid #475569; background: #0f172a; color: white;">
                            <option value="Admin">Admin</option>
                            <option value="Usuario">Usuario</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-primary">Filtrar por Rol</button>
                </form>
            </div>
            
            <!-- Reporte: Resumen de Gastos del Usuario -->
            <div style="background: #1e293b; padding: 1.5rem; border-radius: 8px; border: 1px solid #334155;">
                <h3 style="font-size: 1.25rem; margin-bottom: 1rem; color: #3b82f6;">Resumen de Gastos del Usuario</h3>
                <form action="<%= request.getContextPath() %>/ServletUsuario" method="POST" style="display: flex; flex-direction: column; gap: 1rem;">
                    <input type="hidden" name="accion" value="resumenGastos">
                    <div class="form-group" style="margin-bottom: 0;">
                        <label>ID del Usuario</label>
                        <input type="text" name="idUsuario" placeholder="Ej: 1010" required>
                        <small style="color: #94a3b8; font-size: 0.85rem; display: block; margin-top: 0.25rem;">Consulta todos los gastos y totales de un usuario.</small>
                    </div>
                    <button type="submit" class="btn-primary">Generar Resumen</button>
                </form>
            </div>

        </div>
    </div>
</body>
</html>
