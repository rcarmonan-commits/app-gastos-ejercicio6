# Manual de Uso - Sistema de Gastos

Este documento sirve como guía para la instalación y uso del aplicativo de gestión de gastos.

## 1. Instalación y Configuración (Asistente Web)
La aplicación cuenta con un instalador automatizado.

1. Despliega la aplicación en tu servidor (Tomcat, Glassfish, etc.).
2. Abre la URL en el navegador (Ej: `http://localhost:8080/app-gastos-ejercicio6/`).
3. El **Filtro de Seguridad** detectará que no hay configuración y te redirigirá a `instalador.jsp`.
4. Llena el formulario:
   - Selecciona el motor (MySQL o PostgreSQL).
   - Escribe el host (ej. `localhost`) y puerto.
   - Digita las **credenciales root**. Estas solo se usarán para crear la base de datos y el usuario; **no se guardan**.
   - Digita el nombre de la nueva base de datos y las credenciales del nuevo usuario de la app.
5. Haz clic en "Instalar y Configurar". Si todo es correcto, la aplicación creará la BD, las tablas, y te enviará al Login.

## 2. Iniciar Sesión
- Tras instalar, la BD no tiene usuarios. Debes insertar un usuario administrador manualmente en la BD para el primer uso, o si el script SQL lo provee, usar esas credenciales.
- Si olvidaste la clave, usa el enlace "**¿Olvidó su clave?**". Como requerimiento académico, la clave se imprimirá en la consola del servidor.

## 3. Gestión de Usuarios
- En el menú superior, ve a "Gestión Usuarios".
- Aquí puedes agregar, editar, o eliminar usuarios.
- **Reportes:**
  - Puedes buscar usuarios escribiendo parte de su nombre (ej. "Carlos" encontrará "Juan Carlos").
  - Puedes filtrar para ver únicamente Administradores u Operadores.

## 4. Gestión de Gastos
- En el menú superior, ve a "Gestión Gastos".
- Haz clic en "Nuevo Gasto" para agregar uno. El IVA se ingresa manualmente y el total se calcula automáticamente sumando el valor sin IVA más el IVA.
- **Reportes:**
  - Listar por rango de fechas: Selecciona una fecha de inicio y una de fin, presiona "Buscar".
  - Sumar IVA por lugar: Escribe el lugar (ej. "Bogotá") y presiona "Sumar" para ver el total gastado con IVA en ese lugar específico.
