# Sistema de Gestión de Gastos - Ejercicio 6

## 1. Descripción del Proyecto
Este proyecto es una aplicación web Java EE (Servlets y JSP) desarrollada bajo el patrón de arquitectura **Modelo-Vista-Controlador (MVC)**. Su objetivo principal es gestionar los gastos de usuarios, implementar distintos niveles de permisos (Administrador y Operador) y proveer reportes sobre la información.

**Fechas de Desarrollo:** 10 de Agosto al 17 de Agosto.

## 2. Desarrollo y Arquitectura

### 2.1 Modelo-Vista-Controlador
La aplicación se estructuró dividiendo las responsabilidades para facilitar su escalabilidad:
- **Modelo:** Contiene las clases **POJO** (`Usuario`, `Gasto`, `ConfiguracionSMTP`) que representan las entidades del negocio, y las clases **DAO** (`CRUDUsuario`, `CRUDGasto`) encargadas de interactuar de forma segura con la base de datos usando `PreparedStatement` para prevenir inyecciones SQL.
- **Vista:** Todas las interfaces gráficas se construyeron en **JSP** y **CSS nativo** sin utilizar frameworks como Bootstrap (cumpliendo estrictamente el requerimiento). Se diseñó una interfaz responsiva y estética (colores limpios, tarjetas, y grillas nativas CSS).
- **Controlador:** Se implementaron **Servlets** (`ServletAuth`, `ServletUsuario`, `ServletGasto`) que reciben las peticiones HTTP (GET/POST), interactúan con la capa DAO, y redirigen a las Vistas correspondientes con la información extraída.

### 2.2 Funcionalidades Añadidas
- **CRUD Completo:** Gestión total de Gastos y Usuarios.
- **Roles y Permisos:** Los usuarios registrados públicamente asumen el rol `Operador` por defecto, mientras que un `Administrador` puede eliminar, editar, o ver cualquier información.
- **Campo Email:** Se actualizó la estructura de la tabla `usuarios` y todos los flujos de la aplicación para guardar y administrar correos electrónicos, útiles para la funcionalidad de recuperación de contraseñas.
- **Configuración SMTP Dinámica:** Se creó un instalador (`InstaladorBD`) y un formulario SMTP exclusivo para administradores, evitando "quemar" (hardcodear) claves de base de datos o correos en el código fuente.

## 3. Configuración

### 3.1 Base de Datos (MySQL)
La base de datos fue diseñada mediante un archivo central `schema.sql` que crea las tres tablas principales:
- `usuarios`
- `gastos`
- `configuracion_smtp`

La conexión a la base de datos no está quemada en código; se implementó una clase `GestorConfiguracion` que crea y lee el archivo `db_config.properties` alojado en el entorno local del servidor.

### 3.2 Herramientas Utilizadas
- **Lenguaje:** Java 17
- **Construcción:** Apache Maven (empaquetado `.war`)
- **Servidor Web:** Apache Tomcat 9
- **Base de Datos:** MySQL 8

## 4. Pruebas Realizadas

Se llevaron a cabo exhaustivas pruebas manuales que confirmaron:
1. **Registro e Inicio de Sesión:** El sistema no permite el acceso a usuarios no registrados. Se valida el cifrado y correcta transferencia de la sesión mediante el objeto `HttpSession`.
2. **Validación de Roles:** Se verificó que los botones de eliminar o configurar parámetros de servidor SMTP solo estuviesen visibles para el usuario `admin`.
3. **Manejo de Formularios CSS Nativo:** Se probó la correcta distribución geométrica y el contraste de colores de los elementos (texto blanco sobre fondo secundario/oscuro) simulando una grilla Bootstrap mediante reglas propias en `estilos.css`.
4. **Modificación Estructural:** La adición del campo `email` se probó desde el backend hasta el frontend (modificando la base de datos remotamente usando `ALTER TABLE` sin perder los datos previamente registrados).

## 5. Despliegue

### ¿Dónde se desplegó?
El proyecto está alojado en el entorno de Hosting Platform-as-a-Service (PaaS) **Alwaysdata**.

### ¿Cómo se desplegó?
1. **Empaquetado:** Se utilizó el comando `mvn clean package` para compilar el código `.java` a bytecode y empaquetar la aplicación junto a sus vistas en un archivo `app-gastos-ejercicio6-1.0-SNAPSHOT.war`.
2. **WebDAV Automático:** Se creó un script en PowerShell (`upload_webdav.ps1`) para conectarse directamente a las carpetas remotas de Alwaysdata vía el protocolo WebDAV.
3. **Alojamiento:** El archivo `.war` fue subido al directorio `/www` y renombrado como `ROOT.war` para que Tomcat lo tomara como aplicación principal.
4. **Despliegue Dinámico:** Alwaysdata cuenta con la propiedad `unpackWARs="true"`. Al reiniciar el servicio desde el panel de control, Tomcat desempaquetó automáticamente el `.war` y publicó los cambios en tiempo real.
