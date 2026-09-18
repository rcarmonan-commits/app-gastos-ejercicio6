package controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.CRUDUsuario;
import modelo.Usuario;
import utilidades.UtilidadesCorreo;

/*
 * Archivo: ServletAuth.java
 * JustificaciÃ³n metodolÃ³gica: Como estudiante, agrupo las funciones de inicio de sesiÃ³n, 
 * cierre de sesiÃ³n y recuperaciÃ³n de clave en un controlador dedicado. Uso HttpSession 
 * para mantener al usuario autenticado. La recuperaciÃ³n de clave imprime la clave en 
 * consola por simplicidad en este ejercicio acadÃ©mico.
 */
@WebServlet("/ServletAuth")
public class ServletAuth extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CRUDUsuario crudUsuario;

    public void init() {
        crudUsuario = new CRUDUsuario();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if ("logout".equals(accion)) {
            HttpSession sesion = request.getSession();
            sesion.invalidate(); // Cerrar sesiÃ³n
            response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        if ("login".equals(accion)) {
            String id = request.getParameter("id");
            String clave = request.getParameter("clave");
            
            Usuario usuario = crudUsuario.iniciarSesion(id, clave);
            
            if (usuario != null) {
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuarioLogueado", usuario);
                response.sendRedirect(request.getContextPath() + "/vistas/index.jsp");
            } else {
                request.setAttribute("error", "Credenciales incorrectas.");
                request.getRequestDispatcher("/vistas/login.jsp").forward(request, response);
            }
        } else if ("recuperar".equals(accion)) {
            String id = request.getParameter("id");
            Usuario usuario = crudUsuario.buscar(id);
            
            if (usuario != null) {
                try {
                    String asunto = "Recuperacion de Clave - Sistema de Gastos";
                    String cuerpo = "<h3>Hola " + usuario.getNombre() + ",</h3>"
                            + "<p>Has solicitado recuperar tu clave de acceso al Sistema de Gastos.</p>"
                            + "<p>Tu clave es: <strong>" + usuario.getClave() + "</strong></p>"
                            + "<p>Te recomendamos iniciar sesion y guardarla en un lugar seguro.</p>";
                    
                    UtilidadesCorreo.enviarCorreo(usuario.getEmail(), asunto, cuerpo);
                    request.setAttribute("mensaje", "La clave ha sido enviada a tu correo electronico (" + usuario.getEmail() + ").");
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Hubo un problema al enviar el correo: " + e.getMessage());
                }
            } else {
                request.setAttribute("error", "Usuario no encontrado.");
            }
            request.getRequestDispatcher("/vistas/recuperar_clave.jsp").forward(request, response);
        } else if ("registrar".equals(accion)) {
            String id = request.getParameter("id");
            String clave = request.getParameter("clave");
            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            
            // Por defecto, un usuario que se registra pÃºblicamente es "Operador"
            Usuario nuevoUsuario = new Usuario(id, clave, nombre, email, "Operador");
            boolean exito = crudUsuario.agregar(nuevoUsuario);
            
            if (exito) {
                request.setAttribute("mensaje", "Registro exitoso. Ahora puede ir a iniciar sesión.");
                request.getRequestDispatcher("/vistas/registro.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Error al registrar. El ID podrÃ­a ya estar en uso.");
                request.getRequestDispatcher("/vistas/registro.jsp").forward(request, response);
            }
        }
    }
}
