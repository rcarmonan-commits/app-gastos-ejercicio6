# Proyecto Ejercicio 6 - Gestión de Gastos

Aplicación web monolítica en Java 17 construida desde cero con Servlets y JDBC (sin frameworks). Cumple con los requisitos del patrón MVC y el uso del patrón DAO para acceso a datos.

## Características Principales
- **Puro Java:** Servlets, JSP y JDBC. No se usan ORMs como Hibernate.
- **Patrón DAO:** Todo el acceso a datos está separado de la lógica de negocio.
- **Seguridad:** Prevención de Inyección SQL utilizando `PreparedStatement`.
- **Instalador Web Multi-motor:** Permite instalar y configurar la base de datos (MySQL o PostgreSQL) directamente desde una interfaz web dinámica. No hay contraseñas hardcodeadas.
- **Vistas en Bootstrap:** Interfaz agradable y responsiva.

## Estructura del Proyecto
- `src/main/java/modelo/`: Entidades POJO y lógica DAO (CRUDUsuario, CRUDGasto).
- `src/main/java/controladores/`: Servlets que actúan como controladores e interceptores de la aplicación.
- `src/main/java/utilidades/`: Clases de soporte (ej. GestorConfiguracion).
- `src/main/webapp/vistas/`: Archivos JSP para la interfaz gráfica.
- `docs/`: Manuales de usuario.

Para iniciar, lea `docs/MANUAL_USO.md`.
