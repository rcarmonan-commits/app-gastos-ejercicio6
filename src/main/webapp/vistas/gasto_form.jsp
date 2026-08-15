<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modelo.Gasto" %>
<%@ page import="modelo.Usuario" %>
<%
    Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuarioLogueado == null) {
        response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        return;
    }
    
    Gasto gasto = (Gasto) request.getAttribute("gasto");
    boolean esEdicion = (gasto != null);
%>
<!DOCTYPE html>
<!--
 Archivo: gasto_form.jsp
 Justificación metodológica: Formulario de creación/edición de Gastos.
 En un sistema real, el select de 'nombreUsuario' debería llenarse dinámicamente
 desde la base de datos, pero por simplicidad académica, el usuario debe digitar
 o seleccionar el ID. Aquí uso un campo de texto simple para el foreign key.
-->
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title><%= esEdicion ? "Editar" : "Nuevo" %> Gasto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%= request.getContextPath() %>/vistas/css/estilos.css?v=5" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />

    <div class="container" style="max-width: 600px;">
        <h2><%= esEdicion ? "Editar Gasto" : "Registrar Nuevo Gasto" %></h2>
        <form action="<%= request.getContextPath() %>/ServletGasto" method="POST">
            <input type="hidden" name="accion" value="<%= esEdicion ? "modificar" : "agregar" %>">
            <input type="hidden" name="idGasto" value="<%= esEdicion ? gasto.getIdGasto() : "0" %>">
            
            <div style="display: flex; gap: 1rem;">
                <div class="form-group" style="flex: 1;">
                    <label>Fecha del Gasto</label>
                    <input type="date" name="fecha" value="<%= esEdicion ? gasto.getFecha() : "" %>" required>
                    <small style="color: #94a3b8; font-size: 0.85rem; display: block; margin-top: 0.25rem;">Selecciona el día exacto de la compra.</small>
                </div>
                <div class="form-group" style="flex: 1;">
                    <label>Usuario (ID)</label>
                    <input type="text" name="nombreUsuario" value="<%= esEdicion ? gasto.getNombreUsuario() : usuarioLogueado.getId() %>" required readonly style="background-color: #334155; cursor: not-allowed;" title="El gasto se asignará a tu cuenta automáticamente">
                    <small style="color: #94a3b8; font-size: 0.85rem; display: block; margin-top: 0.25rem;">Usuario que registra el gasto.</small>
                </div>
            </div>
            
            <div style="display: flex; gap: 1rem;">
                <div class="form-group" style="flex: 1;">
                    <label>Valor de la Compra (Sin IVA)</label>
                    <input type="number" step="0.01" min="0.01" name="valorTotalSinIVA" value="<%= esEdicion ? gasto.getValorTotalSinIVA() : "" %>" placeholder="Ej: 50000" required>
                    <small style="color: #94a3b8; font-size: 0.85rem; display: block; margin-top: 0.25rem;">El costo neto del producto o servicio.</small>
                </div>
                <div class="form-group" style="flex: 1;">
                    <label>Porcentaje de IVA (%)</label>
                    <%
                        // Si es edición, calculamos el % de IVA basado en los valores que ya tiene
                        double porcentajeIva = 0;
                        if (esEdicion && gasto.getValorTotalSinIVA() > 0) {
                            porcentajeIva = (gasto.getIvaTotal() / gasto.getValorTotalSinIVA()) * 100;
                        }
                    %>
                    <input type="number" step="0.1" min="0" max="100" name="porcentajeIva" value="<%= esEdicion ? Math.round(porcentajeIva) : "19" %>" required>
                    <small style="color: #94a3b8; font-size: 0.85rem; display: block; margin-top: 0.25rem;">Digita solo el número (ej: 19 para 19%).</small>
                </div>
            </div>
            
            <div class="form-group" style="margin-top: 1rem;">
                <label>Valor Total (Con IVA)</label>
                <input type="text" id="valorTotalConIva" readonly style="background-color: #334155; font-weight: bold; cursor: not-allowed; font-size: 1.1rem; color: #10b981;" value="<%= esEdicion ? String.format(\"%.2f\", gasto.getValorTotalSinIVA() + gasto.getIvaTotal()) : \"0.00\" %>">
                <small style="color: #94a3b8; font-size: 0.85rem; display: block; margin-top: 0.25rem;">Se calcula automáticamente.</small>
            </div>
            
            <div class="form-group" style="margin-top: 1rem;">
                <label>Lugar de la Compra</label>
                <input type="text" name="lugar" value="<%= esEdicion ? gasto.getLugar() : "" %>" placeholder="Ej: Éxito, D1, Restaurante" required maxlength="100">
                <small style="color: #94a3b8; font-size: 0.85rem; display: block; margin-top: 0.25rem;">Establecimiento donde realizaste el gasto.</small>
            </div>

            <div class="form-group">
                <label>Descripción / Detalles</label>
                <textarea name="descripcion" rows="3" placeholder="Ej: Compra de mercado quincenal..." required maxlength="255"><%= esEdicion ? gasto.getDescripcion() : "" %></textarea>
                <small style="color: #94a3b8; font-size: 0.85rem; display: block; margin-top: 0.25rem;">Un resumen breve de lo que compraste.</small>
            </div>
            
            <div class="form-actions" style="margin-top: 1rem; display: flex; gap: 1rem;">
                <button type="submit" class="btn-primary">Guardar Gasto</button>
                <a href="<%= request.getContextPath() %>/ServletGasto?accion=listar" class="btn-secondary" style="display:inline-flex; align-items:center; justify-content:center; padding:0.75rem 1.5rem; text-decoration:none; background-color:#64748b; color:white; border-radius:8px;">Cancelar</a>
            </div>
        </form>
    </div>
    
    <script>
        // Cálculo dinámico del total con IVA
        const inputSinIva = document.querySelector('input[name="valorTotalSinIVA"]');
        const inputPorcentaje = document.querySelector('input[name="porcentajeIva"]');
        const inputTotalConIva = document.getElementById('valorTotalConIva');

        function calcularTotal() {
            const sinIva = parseFloat(inputSinIva.value) || 0;
            const porcentaje = parseFloat(inputPorcentaje.value) || 0;
            const total = sinIva + (sinIva * (porcentaje / 100));
            inputTotalConIva.value = total.toFixed(2);
        }

        inputSinIva.addEventListener('input', calcularTotal);
        inputPorcentaje.addEventListener('input', calcularTotal);
    </script>
</body>
</html>
