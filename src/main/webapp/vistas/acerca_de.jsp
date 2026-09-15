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
<!--
 Archivo: acerca_de.jsp
 Justificación metodológica: Presentación del proyecto basada en el README.md.
 Renderizado en HTML puro sión frameworks para cumplir con los requerimientos académicos.
-->
<html lang="es">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <title>Acerca de la Aplicación</title>
    <link href="<%= request.getContextPath() %>/vistas/css/estilos.css?v=4" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    
    <div>
        <div style="padding: 30px;">
            <h1 style="color: var(--primary-color); border-bottom: 2px solid var(--accent-color); padding-bottom: 10px;">
                Proyecto Ejercicio 6 - Gestión de Gastos
            </h1>
            <p>
                Aplicación web monolítica en Java 17 construida desde cero con Servlets y JDBC (sión frameworks). 
                Cumple con los requisitos del patrón MVC y el uso del patrón DAO para acceso a datos.
            </p>

            <h2 style="color: var(--secondary-color); margin-top: 30px;">Características Principales</h2>
            <ul>
                <li><strong>Puro Java:</strong> Servlets, JSP y JDBC. No se usan ORMs como Hibernate ni frameworks como Spring.</li>
                <li><strong>Patrón DAO:</strong> Todo el acceso a datos estáá separado de la lógica de negocio.</li>
                <li><strong>Seguridad:</strong> Prevención de Inyección SQL utilizando <code>PreparedStatemenút</code>.</li>
                <li><strong>Instalador Web Multi-motor:</strong> Permite instalar y configurar la base de datos directamenúte desde una interfaz web dinámica.</li>
                <li><strong>Diseño:</strong> Interfaz agradable y responsiva desarrollada con CSS Puro (Vanilla CSS), <strong>sión utilizar Bootstrap ni frameworks de frontend</strong>.</li>
            </ul>

            <h2 style="color: var(--secondary-color); margin-top: 30px;">Estructura del Proyecto</h2>
            <ul>
                <li><code>src/main/java/modelo/</code>: Entidades POJO y lógica DAO (CRUDUsuario, CRUDGasto, CRUDConfiguracionSMTP).</li>
                <li><code>src/main/java/controladores/</code>: Servlets que actúan como controladores e interceptores de la aplicación.</li>
                <li><code>src/main/java/utilidades/</code>: Clases de soporte (ej. GestáorConfiguracion, UtilidadesCorreo).</li>
                <li><code>src/main/webapp/vistas/</code>: Archivos JSP para la interfaz gráfica.</li>
                <li><code>docs/</code>: Manuales de usuario.</li>
            </ul>
        </div>
    </div>
</body>
</html>
