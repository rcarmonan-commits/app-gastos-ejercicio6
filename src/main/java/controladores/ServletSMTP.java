package controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.CRUDConfiguracionSMTP;
import modelo.ConfiguracionSMTP;
import modelo.Usuario;

@WebServlet("/ServletSMTP")
public class ServletSMTP extends HttpServlet {

    private CRUDConfiguracionSMTP crudSMTP;

    @Override
    public void init() {
        crudSMTP = new CRUDConfiguracionSMTP();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null || !"Administrador".equals(usuarioLogueado.getRol())) {
            response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        if ("ver".equals(accion)) {
            ConfiguracionSMTP config = crudSMTP.obtenerConfiguracion();
            request.setAttribute("configSMTP", config);
            request.getRequestDispatcher("/vistas/smtp_form.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/vistas/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null || !"Administrador".equals(usuarioLogueado.getRol())) {
            response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        if ("guardar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String host = request.getParameter("host");
            String puerto = request.getParameter("puerto");
            String usuario = request.getParameter("usuario");
            String clave = request.getParameter("clave");
            
            ConfiguracionSMTP config = new ConfiguracionSMTP(id, host, puerto, usuario, clave);
            boolean exito = crudSMTP.actualizarConfiguracion(config);
            
            if (exito) {
                request.setAttribute("mensaje", "Configuracion SMTP actualizada con exito.");
            } else {
                request.setAttribute("error", "Error al actualizar la configuracion SMTP.");
            }
            request.setAttribute("configSMTP", config);
            request.getRequestDispatcher("/vistas/smtp_form.jsp").forward(request, response);
        }
    }
}
