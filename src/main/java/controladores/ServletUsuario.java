package controladores;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.CRUDUsuario;
import modelo.CRUDGasto;
import modelo.Gasto;
import modelo.Usuario;

/*
 * Archivo: ServletUsuario.java
 * JustificaciÃ³n metodolÃ³gica: Como estudiante, centralizo todas las operaciones
 * CRUD de la entidad Usuario en este Servlet usando un switch-case en doPost y doGet.
 * Esto mantiene el cÃ³digo ordenado y facilita el mantenimiento, siguiendo el patrÃ³n MVC
 * como un Controlador frontal bÃ¡sico para la entidad.
 */
@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CRUDUsuario crudUsuario;
    private CRUDGasto crudGasto;

    public void init() {
        crudUsuario = new CRUDUsuario();
        crudGasto = new CRUDGasto();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if (request.getSession().getAttribute("usuarioLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
            return;
        }
        
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar":
                listarUsuarios(request, response);
                break;
            case "eliminar":
                eliminarUsuario(request, response);
                break;
            case "editar":
                mostrarFormularioEdicion(request, response);
                break;
            case "listarPorRol":
                listarUsuariosPorRol(request, response);
                break;
            default:
                listarUsuarios(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if (request.getSession().getAttribute("usuarioLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/vistas/login.jsp");
            return;
        }
        
        String accion = request.getParameter("accion");

        switch (accion) {
            case "agregar":
                agregarUsuario(request, response);
                break;
            case "modificar":
                modificarUsuario(request, response);
                break;
            case "resumenGastos":
                resumenGastosUsuario(request, response);
                break;
            default:
                listarUsuarios(request, response);
                break;
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> lista = crudUsuario.listar();
        request.setAttribute("listaUsuarios", lista);
        request.getRequestDispatcher("/vistas/usuarios_lista.jsp").forward(request, response);
    }

    private void agregarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        String clave = request.getParameter("clave");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String rol = request.getParameter("rol");

        Usuario u = new Usuario(id, clave, nombre, email, rol);
        crudUsuario.agregar(u);
        response.sendRedirect("ServletUsuario?accion=listar");
    }

    private void modificarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        String clave = request.getParameter("clave");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String rol = request.getParameter("rol");

        Usuario u = new Usuario(id, clave, nombre, email, rol);
        crudUsuario.modificar(u);
        response.sendRedirect("ServletUsuario?accion=listar");
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        crudUsuario.eliminar(id);
        response.sendRedirect("ServletUsuario?accion=listar");
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Usuario usuarioEncontrado = crudUsuario.buscar(id);
        request.setAttribute("usuario", usuarioEncontrado);
        request.getRequestDispatcher("/vistas/usuario_form.jsp").forward(request, response);
    }

    private void resumenGastosUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idUsuario = request.getParameter("idUsuario");
        Usuario u = crudUsuario.buscar(idUsuario);
        
        if (u != null) {
            List<Gasto> gastosUsuario = crudGasto.listarPorUsuario(idUsuario);
            double totalGastado = 0;
            for (Gasto g : gastosUsuario) {
                totalGastado += g.getValorTotalConIVA();
            }
            
            request.setAttribute("usuarioResumen", u);
            request.setAttribute("listaGastos", gastosUsuario);
            request.setAttribute("totalGastosUsuario", totalGastado);
            request.setAttribute("cantidadGastos", gastosUsuario.size());
            
            // Reutilizamos la vista de gastos para mostrar su lista personal
            request.getRequestDispatcher("/vistas/gastos_lista.jsp").forward(request, response);
        } else {
            // Usuario no encontrado
            response.sendRedirect(request.getContextPath() + "/ServletUsuario?accion=listar");
        }
    }

    private void listarUsuariosPorRol(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rol = request.getParameter("rol");
        List<Usuario> lista = crudUsuario.listarPorRol(rol);
        
        request.setAttribute("listaUsuarios", lista);
        request.setAttribute("totalUsuariosRol", lista.size());
        request.setAttribute("rolBuscado", rol);
        
        request.getRequestDispatcher("/vistas/usuarios_lista.jsp").forward(request, response);
    }
}
