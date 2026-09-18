package utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/*
 * Archivo: GestorConfiguracion.java
 * Justificación metodológica: Como estudiante, es importante no "quemar" (hardcodear) 
 * la configuración de la base de datos dentro del código. Usar un archivo de propiedades 
 * nos permite cambiar el motor (MySQL o PostgreSQL) y las credenciales sin tener que 
 * recompilar la aplicación. Usamos métodos básicos para leer y escribir el archivo.
 */
public class GestorConfiguracion {

    // Ruta relativa o absoluta donde guardaremos la configuración.
    // Para entornos web, usaremos una ruta fija en el home del usuario o variable temporal.
    private static final String RUTA_ARCHIVO = System.getProperty("user.home") + File.separator + "db_config.properties";

    public static void guardarConfiguracion(String motor, String host, String puerto, String nombreBD, String usuario, String clave) throws IOException {
        Properties propiedades = new Properties();
        propiedades.setProperty("motor", motor);
        propiedades.setProperty("host", host);
        propiedades.setProperty("puerto", puerto);
        propiedades.setProperty("nombre_bd", nombreBD);
        propiedades.setProperty("usuario", usuario);
        propiedades.setProperty("clave", clave);

        // Se usa FileOutputStream para escribir el archivo
        FileOutputStream salida = new FileOutputStream(RUTA_ARCHIVO);
        propiedades.store(salida, "Configuracion de Base de Datos - Ejercicio 6");
        salida.close();
    }

    public static Properties leerConfiguracion() {
        Properties propiedades = new Properties();
        try {
            File archivo = new File(RUTA_ARCHIVO);
            if (archivo.exists()) {
                FileInputStream entrada = new FileInputStream(archivo);
                propiedades.load(entrada);
                entrada.close();
            }
        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
        return propiedades;
    }

    public static boolean estaConfigurado() {
        File archivo = new File(RUTA_ARCHIVO);
        return archivo.exists();
    }
}
