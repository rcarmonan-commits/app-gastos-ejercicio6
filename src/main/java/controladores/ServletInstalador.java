package controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.InstaladorBD;

/*
 * Archivo: ServletInstalador.java
 * JustificaciÃ³n metodolÃ³gica: Como estudiante, uso este Servlet para interceptar
 * el formulario de instalaciÃ³n web. Si la base de datos se instala correctamente,
 * redirige al login. Si falla, manda un mensaje de error a la misma vista.
 */
@WebServlet("/ServletInstalador")
public class ServletInstalador extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String motor = request.getParameter("motor");
        String host = request.getParameter("host");
        String puerto = request.getParameter("puerto");
        String rootUsuario = request.getParameter("rootUsuario");
        String rootClave = request.getParameter("rootClave");
        String nombreNuevaBD = request.getParameter("nombreNuevaBD");
        String appUsuario = request.getParameter("appUsuario");
        String appClave = request.getParameter("appClave");

        // Obtenemos la ruta real de schema.sql ubicado en la raÃ­z del proyecto web
        // NOTA: Para este ejercicio, asumimos que schema.sql estÃ¡ en un lugar accesible.
        // Si el WAR no expone la raÃ­z externa, usamos getServletContext().getRealPath("/")
        // Asumiendo que copiaremos schema.sql a la raÃ­z del webapp
        String rutaEsquema = getServletContext().getRealPath("/schema.sql");
        if (rutaEsquema == null || rutaEsquema.isEmpty()) {
            // Alternativa si se ejecuta desde IDE (ej: C:\ruta\proyecto\schema.sql)
            // Se puede pedir tambiÃ©n por parÃ¡metro o buscar en la ruta del proyecto.
            rutaEsquema = System.getProperty("user.dir") + "/schema.sql";
        }

        boolean exito = InstaladorBD.instalar(motor, host, puerto, rootUsuario, rootClave, nombreNuevaBD, appUsuario, appClave, rutaEsquema);

        if (exito) {
            // InstalaciÃ³n correcta
            response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        } else {
            // Falla de instalaciÃ³n
            request.setAttribute("error", "Verifique que el motor estÃ© instalado y corriendo. Credenciales root invÃ¡lidas o motor inalcanzable.");
            request.getRequestDispatcher("/instalador.jsp").forward(request, response);
        }
    }
}
