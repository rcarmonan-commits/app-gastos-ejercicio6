<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- 
 Archivo: instalador.jsp
 Justificación metodológica: Como estudiante, quiero una interfaz amigable (usando Bootstrap)
 para que el evaluador instale la base de datos fácilmente, seleccionando el motor 
 (MySQL o PostgreSQL) y digitando las credenciales necesarias, sin tocar el código.
-->
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Instalador de Base de Datos</title>
    <!-- Incluir Bootstrap desde CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white text-center">
                        <h4>Asistente de Instalación - Sistema de Gastos</h4>
                    </div>
                    <div class="card-body">
                        <% 
                            String error = (String) request.getAttribute("error");
                            if (error != null) { 
                        %>
                            <div class="alert alert-danger" role="alert">
                                <%= error %>
                            </div>
                        <% } %>
                        <form action="ServletInstalador" method="POST">
                            <div class="mb-3">
                                <label for="motor" class="form-label">Motor de Base de Datos</label>
                                <select class="form-select" id="motor" name="motor" required>
                                    <option value="MySQL">MySQL</option>
                                    <option value="PostgreSQL">PostgreSQL</option>
                                </select>
                            </div>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="host" class="form-label">Host</label>
                                    <input type="text" class="form-control" id="host" name="host" value="localhost" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="puerto" class="form-label">Puerto</label>
                                    <input type="text" class="form-control" id="puerto" name="puerto" placeholder="3306 o 5432" required>
                                </div>
                            </div>
                            
                            <hr>
                            <h5 class="text-secondary">Credenciales del Administrador (Root)</h5>
                            <p class="text-muted small">Necesarias solo para crear la base de datos y el usuario.</p>
                            
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="rootUsuario" class="form-label">Usuario Root</label>
                                    <input type="text" class="form-control" id="rootUsuario" name="rootUsuario" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="rootClave" class="form-label">Clave Root</label>
                                    <input type="password" class="form-control" id="rootClave" name="rootClave">
                                </div>
                            </div>

                            <hr>
                            <h5 class="text-secondary">Configuración de la Nueva App</h5>
                            
                            <div class="mb-3">
                                <label for="nombreNuevaBD" class="form-label">Nombre de Nueva BD</label>
                                <input type="text" class="form-control" id="nombreNuevaBD" name="nombreNuevaBD" value="gastos_db" required>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="appUsuario" class="form-label">Nuevo Usuario App</label>
                                    <input type="text" class="form-control" id="appUsuario" name="appUsuario" value="usr_gastos" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="appClave" class="form-label">Clave Nueva App</label>
                                    <input type="password" class="form-control" id="appClave" name="appClave" value="123456" required>
                                </div>
                            </div>

                            <div class="d-grid">
                                <button type="submit" class="btn btn-success">Instalar y Configurar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
