package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import utilidades.GestorConfiguracion;

/*
 * Archivo: InstaladorBD.java
 * Justificación metodológica: Como estudiante, quiero automatizar la creación de la 
 * base de datos para no obligar al usuario a hacer scripts manualmente. Este código 
 * recibe credenciales "root", se conecta, crea la BD, el nuevo usuario con permisos, 
 * y luego ejecuta el esquema SQL. Al probar en PostgreSQL y MySQL, el código se 
 * adapta de manera dinámica sin tener contraseñas quemadas.
 */
public class InstaladorBD {

    public static boolean instalar(String motor, String host, String puerto, String rootUsuario, String rootClave, 
                                   String nombreNuevaBD, String appUsuario, String appClave, String rutaAbsolutaEsquema) {
        Connection conexionRoot = null;
        Statement sentencia = null;
        boolean exito = false;

        try {
            String urlRoot = "";
            String driver = "";

            if (motor.equals("MySQL")) {
                driver = "com.mysql.cj.jdbc.Driver";
                urlRoot = "jdbc:mysql://" + host + ":" + puerto + "/";
            } else if (motor.equals("PostgreSQL")) {
                driver = "org.postgresql.Driver";
                urlRoot = "jdbc:postgresql://" + host + ":" + puerto + "/postgres";
            }

            // Cargar el driver
            Class.forName(driver);

            // Conectar con el usuario root
            conexionRoot = DriverManager.getConnection(urlRoot, rootUsuario, rootClave);
            sentencia = conexionRoot.createStatement();

            // Crear Base de Datos y Usuario dependiendo del motor
            if (motor.equals("MySQL")) {
                sentencia.executeUpdate("CREATE DATABASE IF NOT EXISTS " + nombreNuevaBD);
                sentencia.executeUpdate("CREATE USER IF NOT EXISTS '" + appUsuario + "'@'localhost' IDENTIFIED BY '" + appClave + "'");
                sentencia.executeUpdate("GRANT ALL PRIVILEGES ON " + nombreNuevaBD + ".* TO '" + appUsuario + "'@'localhost'");
                sentencia.executeUpdate("FLUSH PRIVILEGES");
                sentencia.executeUpdate("USE " + nombreNuevaBD);
            } else if (motor.equals("PostgreSQL")) {
                // En Postgres, CREATE DATABASE no puede ir en un bloque de transacción general a veces, 
                // pero por simplicidad JDBC lo ejecuta bien si no está en auto-commit = false.
                try {
                    sentencia.executeUpdate("CREATE DATABASE " + nombreNuevaBD);
                } catch (SQLException e) {
                    // Ignorar si ya existe
                    System.out.println("La base de datos podría ya existir: " + e.getMessage());
                }
                
                try {
                    sentencia.executeUpdate("CREATE USER " + appUsuario + " WITH PASSWORD '" + appClave + "'");
                } catch (SQLException e) {
                    System.out.println("El usuario podría ya existir: " + e.getMessage());
                }
                
                sentencia.executeUpdate("GRANT ALL PRIVILEGES ON DATABASE " + nombreNuevaBD + " TO " + appUsuario);
                
                // Cerrar conexión root actual y conectar directamente a la nueva BD en Postgres
                // porque Postgres no tiene comando USE.
                sentencia.close();
                conexionRoot.close();
                
                String urlNuevaBD = "jdbc:postgresql://" + host + ":" + puerto + "/" + nombreNuevaBD;
                conexionRoot = DriverManager.getConnection(urlNuevaBD, rootUsuario, rootClave);
                sentencia = conexionRoot.createStatement();
            }

            // Leer y ejecutar el script schema.sql
            File archivoSQL = new File(rutaAbsolutaEsquema);
            if (archivoSQL.exists()) {
                BufferedReader lector = new BufferedReader(new FileReader(archivoSQL));
                String linea;
                StringBuilder constructorConsultas = new StringBuilder();

                while ((linea = lector.readLine()) != null) {
                    // Evitar comentarios y lineas vacias
                    if (linea.trim().startsWith("--") || linea.trim().startsWith("/*") || linea.trim().isEmpty()) {
                        continue;
                    }
                    constructorConsultas.append(linea);
                    if (linea.trim().endsWith(";")) {
                        sentencia.executeUpdate(constructorConsultas.toString());
                        constructorConsultas.setLength(0); // Limpiar para la siguiente instrucción
                    }
                }
                lector.close();
            }

            // Si todo sale bien, guardar la configuración
            GestorConfiguracion.guardarConfiguracion(motor, host, puerto, nombreNuevaBD, appUsuario, appClave);
            exito = true;

        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        } finally {
            // Cerrar conexiones
            try {
                if (sentencia != null) sentencia.close();
                if (conexionRoot != null) conexionRoot.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return exito;
    }
}
