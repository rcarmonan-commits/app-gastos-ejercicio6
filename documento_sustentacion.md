# Documento de Sustentación - Aplicación de Gastos (Ejercicio 6)

## 1. Introducción
Este documento detalla la implementación, desarrollo y despliegue de la aplicación web de gestión de gastos, desarrollada en Java con Servlets y JSP. Se cumplen todos los requerimientos académicos, incluyendo autenticación, base de datos en la nube y reportes parametrizados.

## 2. Desarrollo de la Aplicación (Servlets y JSP)
La aplicación fue diseñada siguiendo el patrón arquitectónico MVC (Modelo-Vista-Controlador).
- **Vistas (JSP)**: Interfaces responsivas creadas con Bootstrap. Se incluye login, registro, listado de gastos y reportes.
- **Controladores (Servlets)**: `ServletUsuario`, `ServletGasto`, `ServletAuth` gestionan la lógica de negocio y aseguran que los usuarios sin sesión no puedan acceder.
- **Modelo (DAO)**: Clases como `CRUDUsuario` y `CRUDGasto` centralizan el acceso a la base de datos usando `PreparedStatement` para evitar inyección SQL.

![Login Page](/C:/Users/sarrieta/.gemini/antigravity-ide/brain/07b4218f-ce69-4fe8-8be1-330e134f9f9f/login_page_1786776635208.png)
*Figura 1: Interfaz de Inicio de Sesión responsiva.*

![Registro Page](/C:/Users/sarrieta/.gemini/antigravity-ide/brain/07b4218f-ce69-4fe8-8be1-330e134f9f9f/registration_page_1786776685190.png)
*Figura 2: Formulario público de registro de usuarios.*

## 3. Implementación Quirúrgica de Reportes Parametrizados
Se implementaron 4 reportes dinámicos que no solo listan, sino que analizan la información:
1. **Reporte por Rango de Fechas**: Sumariza el total de gastos realizados entre dos fechas dadas.
2. **Reporte por Lugar de Compra**: Filtra compras y suma el total y el IVA generado en ese comercio específico.
3. **Resumen de Usuario**: Analiza la participación de un usuario mostrando su cantidad total de tickets y dinero invertido.
4. **Listado Consolidado**: Tarjetas dinámicas resumen la cantidad de administradores y operadores del sistema.

Las validaciones en los formularios (HTML5 `required`, `min`, `max`) y el cálculo en backend aseguran la integridad, por ejemplo, el cálculo automático de IVA a partir de un porcentaje ingresado.

## 4. Despliegue en la Nube (Alwaysdata)
La aplicación fue empacada usando Maven (`mvn clean package`) generando un archivo `.war`. El proceso de despliegue consistió en:
1. **Servidor Java**: Configuración de Alwaysdata para usar un contenedor **Tomcat y Java 17**.
2. **Base de Datos**: Importación del esquema relacional en MariaDB.
3. **Conexión remota**: Modificación de `ConexionBaseDatos.java` para conectarse a `mysql-app-gastos-ejercicio6.alwaysdata.net` usando parámetros SSL corregidos (`useSSL=false`).
4. **Subida de WAR**: Archivo `ROOT.war` desplegado vía WebFTP.

![Despliegue y Pruebas](/C:/Users/sarrieta/.gemini/antigravity-ide/brain/07b4218f-ce69-4fe8-8be1-330e134f9f9f/media__1786825923384.png)
*Figura 3: Evidencia del proyecto ejecutándose exitosamente contra la base de datos remota.*

## 5. Control de Versiones (Git y GitHub)
El desarrollo siguió un control de versiones meticuloso, con mensajes informativos en español documentando los hitos desde la inicialización, la creación de modelos y DAO, hasta el refinamiento de la interfaz gráfica y los reportes avanzados (del 12 de agosto al 18 de septiembre).

---
*Este documento incluye capturas de la implementación. Se invita al tutor a revisar el código fuente completo versionado en GitHub.*
