package controladores;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilidades.GestorConfiguracion;

/*
 * Archivo: FiltroInstalacion.java
 * JustificaciÃ³n metodolÃ³gica: Como estudiante, uso un Filtro (Filter) para asegurar
 * que la aplicaciÃ³n no pueda usarse si no se ha completado el asistente de instalaciÃ³n.
 * El filtro intercepta todas las peticiones (/*) y redirige a instalador.jsp si
 * no existe el archivo db_config.properties.
 */
@WebFilter("/*")
public class FiltroInstalacion implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {
        // InicializaciÃ³n del filtro si fuera necesario
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest peticionHttp = (HttpServletRequest) request;
        HttpServletResponse respuestaHttp = (HttpServletResponse) response;

        String ruta = peticionHttp.getRequestURI();

        // Evitar bucle infinito si ya estamos en el instalador o recursos estÃ¡ticos
        boolean esRutaInstalador = ruta.endsWith("instalador.jsp") || ruta.endsWith("ServletInstalador");
        
        if (!GestorConfiguracion.estaConfigurado() && !esRutaInstalador) {
            // Si no estÃ¡ configurado y no es el instalador, redirige al instalador
            respuestaHttp.sendRedirect(peticionHttp.getContextPath() + "/instalador.jsp");
            return;
        }

        // Si ya estÃ¡ configurado, o es el instalador, permite continuar
        chain.doFilter(request, response);
    }

    public void destroy() {
        // Liberar recursos si fuera necesario
    }
}
