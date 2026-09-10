package controladores;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.CRUDGasto;
import modelo.Gasto;

/*
 * Archivo: ServletGasto.java
 * JustificaciÃ³n metodolÃ³gica: Como estudiante, centralizo todas las operaciones
 * CRUD y reportes de la entidad Gasto en este Servlet usando un switch-case.
 * Convierto las fechas y nÃºmeros recibidos del JSP (como Strings) a los tipos nativos de Java
 * antes de pasarlos al modelo.
 */
@WebServlet("/ServletGasto")
public class ServletGasto extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CRUDGasto crudGasto;

    public void init() {
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
                listarGastos(request, response);
                break;
            case "eliminar":
                eliminarGasto(request, response);
                break;
            case "editar":
                mostrarFormularioEdicion(request, response);
                break;
            case "sumarPorLugar":
                sumarGastosLugar(request, response);
                break;
            case "listarRangoFechas":
                listarGastosRangoFechas(request, response);
                break;
            default:
                listarGastos(request, response);
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
                agregarGasto(request, response);
                break;
            case "modificar":
                modificarGasto(request, response);
                break;
            default:
                listarGastos(request, response);
                break;
        }
    }

    private void listarGastos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Gasto> lista = crudGasto.listar();
        request.setAttribute("listaGastos", lista);
        request.getRequestDispatcher("/vistas/gastos_lista.jsp").forward(request, response);
    }

    private void agregarGasto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Date fecha = Date.valueOf(request.getParameter("fecha"));
        double valorTotalSinIVA = Double.parseDouble(request.getParameter("valorTotalSinIVA"));
        double porcentajeIva = Double.parseDouble(request.getParameter("porcentajeIva"));
        
        // Cálculo automático de los totales basados en el porcentaje dado
        double ivaTotal = valorTotalSinIVA * (porcentajeIva / 100.0);
        double valorTotalConIVA = valorTotalSinIVA + ivaTotal;
        
        String nombreUsuario = request.getParameter("nombreUsuario");
        String lugar = request.getParameter("lugar");
        String descripcion = request.getParameter("descripcion");

        Gasto g = new Gasto(0, fecha, valorTotalSinIVA, ivaTotal, valorTotalConIVA, nombreUsuario, lugar, descripcion);
        crudGasto.agregar(g);
        response.sendRedirect("ServletGasto?accion=listar");
    }

    private void modificarGasto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idGasto = Integer.parseInt(request.getParameter("idGasto"));
        Date fecha = Date.valueOf(request.getParameter("fecha"));
        double valorTotalSinIVA = Double.parseDouble(request.getParameter("valorTotalSinIVA"));
        double porcentajeIva = Double.parseDouble(request.getParameter("porcentajeIva"));
        
        // Cálculo automático de los totales basados en el porcentaje dado
        double ivaTotal = valorTotalSinIVA * (porcentajeIva / 100.0);
        double valorTotalConIVA = valorTotalSinIVA + ivaTotal;
        
        String nombreUsuario = request.getParameter("nombreUsuario");
        String lugar = request.getParameter("lugar");
        String descripcion = request.getParameter("descripcion");

        Gasto g = new Gasto(idGasto, fecha, valorTotalSinIVA, ivaTotal, valorTotalConIVA, nombreUsuario, lugar, descripcion);
        crudGasto.modificar(g);
        response.sendRedirect("ServletGasto?accion=listar");
    }

    private void eliminarGasto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idGasto = Integer.parseInt(request.getParameter("idGasto"));
        crudGasto.eliminar(idGasto);
        response.sendRedirect("ServletGasto?accion=listar");
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idGasto = Integer.parseInt(request.getParameter("idGasto"));
        Gasto gastoEncontrado = crudGasto.buscar(idGasto);
        request.setAttribute("gasto", gastoEncontrado);
        request.getRequestDispatcher("/vistas/gasto_form.jsp").forward(request, response);
    }

    private void sumarGastosLugar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lugar = request.getParameter("lugar");
        double total = crudGasto.sumarGastosConIVAPorLugar(lugar);
        request.setAttribute("totalSuma", total);
        request.setAttribute("lugarSuma", lugar);
        
        // Volver a listar para mostrar en la misma pÃ¡gina
        List<Gasto> lista = crudGasto.listar();
        request.setAttribute("listaGastos", lista);
        request.getRequestDispatcher("/vistas/gastos_lista.jsp").forward(request, response);
    }

    private void listarGastosRangoFechas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date fechaInicio = Date.valueOf(request.getParameter("fechaInicio"));
        Date fechaFin = Date.valueOf(request.getParameter("fechaFin"));
        List<Gasto> lista = crudGasto.listarPorRangoFechas(fechaInicio, fechaFin);
        
        // Calcular el gran total gastado en este rango para hacerlo verdaderamente analítico
        double totalGastadoRango = 0;
        for (Gasto g : lista) {
            totalGastadoRango += g.getValorTotalConIVA();
        }
        
        request.setAttribute("listaGastos", lista);
        request.setAttribute("totalRango", totalGastadoRango);
        request.setAttribute("fechaInicioRango", fechaInicio);
        request.setAttribute("fechaFinRango", fechaFin);
        
        request.getRequestDispatcher("/vistas/gastos_lista.jsp").forward(request, response);
    }
}
