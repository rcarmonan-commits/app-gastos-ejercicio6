package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import utilidades.GestorConfiguracion;

/*
 * Archivo: ConexionBaseDatos.java
 * Justificación metodológica: Como estudiante, centralizo la conexión a la base de datos 
 * usando el patrón Singleton básico para evitar múltiples instancias de conexión 
 * descontroladas. Además, leo dinámicamente las propiedades usando GestorConfiguracion, 
 * por lo que no necesito quemar contraseñas.
 */
public class ConexionBaseDatos {
    private static Connection conexion = null;

    public static Connection obtenerConexion() {
        if (conexion == null) {
            conectar();
        } else {
            try {
                if (conexion.isClosed()) {
                    conectar();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conexion;
    }

    private static void conectar() {
        try {
            Properties propiedades = GestorConfiguracion.leerConfiguracion();
            String motor = propiedades.getProperty("motor");
            String host = propiedades.getProperty("host");
            String puerto = propiedades.getProperty("puerto");
            String nombreBD = propiedades.getProperty("nombre_bd");
            String usuario = propiedades.getProperty("usuario");
            String clave = propiedades.getProperty("clave");

            String url = "";
            String driver = "";

            if ("MySQL".equals(motor)) {
                driver = "com.mysql.cj.jdbc.Driver";
                url = "jdbc:mysql://" + host + ":" + puerto + "/" + nombreBD + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            } else if ("PostgreSQL".equals(motor)) {
                driver = "org.postgresql.Driver";
                url = "jdbc:postgresql://" + host + ":" + puerto + "/" + nombreBD;
            }

            Class.forName(driver);
            conexion = DriverManager.getConnection(url, usuario, clave);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
