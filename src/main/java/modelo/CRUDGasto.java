package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Archivo: CRUDGasto.java
 * Justificación metodológica: Como estudiante, agrupo todas las operaciones de la entidad Gasto 
 * en esta clase para cumplir con el patrón DAO. Al igual que en CRUDUsuario, uso PreparedStatement
 * para prevenir inyecciones SQL y mantener la integridad y seguridad de la base de datos, 
 * evitando que entradas maliciosas modifiquen mis consultas.
 */
public class CRUDGasto {

    public boolean agregar(Gasto gasto) {
        String sql = "INSERT INTO gastos (fecha, valor_total_sin_iva, iva_total, valor_total_con_iva, nombre_usuario, lugar, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return false;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
            
            declaracion.setDate(1, gasto.getFecha());
            declaracion.setDouble(2, gasto.getValorTotalSinIVA());
            declaracion.setDouble(3, gasto.getIvaTotal());
            declaracion.setDouble(4, gasto.getValorTotalConIVA());
            declaracion.setString(5, gasto.getNombreUsuario());
            declaracion.setString(6, gasto.getLugar());
            declaracion.setString(7, gasto.getDescripcion());
            
            int filasAfectadas = declaracion.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificar(Gasto gasto) {
        String sql = "UPDATE gastos SET fecha = ?, valor_total_sin_iva = ?, iva_total = ?, valor_total_con_iva = ?, nombre_usuario = ?, lugar = ?, descripcion = ? WHERE id_gasto = ?";
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return false;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
            
            declaracion.setDate(1, gasto.getFecha());
            declaracion.setDouble(2, gasto.getValorTotalSinIVA());
            declaracion.setDouble(3, gasto.getIvaTotal());
            declaracion.setDouble(4, gasto.getValorTotalConIVA());
            declaracion.setString(5, gasto.getNombreUsuario());
            declaracion.setString(6, gasto.getLugar());
            declaracion.setString(7, gasto.getDescripcion());
            declaracion.setInt(8, gasto.getIdGasto());
            
            int filasAfectadas = declaracion.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idGasto) {
        String sql = "DELETE FROM gastos WHERE id_gasto = ?";
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return false;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
            
            declaracion.setInt(1, idGasto);
            
            int filasAfectadas = declaracion.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Gasto buscar(int idGasto) {
        String sql = "SELECT * FROM gastos WHERE id_gasto = ?";
        Gasto gastoEncontrado = null;
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return null;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
            
            declaracion.setInt(1, idGasto);
            ResultSet resultados = declaracion.executeQuery();
            
            if (resultados.next()) {
                gastoEncontrado = mapearGasto(resultados);
            }
            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gastoEncontrado;
    }

    public List<Gasto> listar() {
        String sql = "SELECT * FROM gastos";
        List<Gasto> lista = new ArrayList<>();
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return lista;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql);
             ResultSet resultados = declaracion.executeQuery()) {
            
            while (resultados.next()) {
                lista.add(mapearGasto(resultados));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Reporte: Sumar gastos con IVA filtrado por lugar
    public double sumarGastosConIVAPorLugar(String lugar) {
        String sql = "SELECT SUM(valor_total_con_iva) AS total FROM gastos WHERE lugar = ?";
        double total = 0.0;
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return total;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
             
            declaracion.setString(1, lugar);
            ResultSet resultados = declaracion.executeQuery();
            
            if (resultados.next()) {
                total = resultados.getDouble("total");
            }
            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    // Reporte: Listar gastos entre dos fechas
    public List<Gasto> listarPorRangoFechas(Date fechaInicio, Date fechaFin) {
        String sql = "SELECT * FROM gastos WHERE fecha BETWEEN ? AND ?";
        List<Gasto> lista = new ArrayList<>();
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return lista;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
             
            declaracion.setDate(1, fechaInicio);
            declaracion.setDate(2, fechaFin);
            ResultSet resultados = declaracion.executeQuery();
            
            while (resultados.next()) {
                lista.add(mapearGasto(resultados));
            }
            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método auxiliar para evitar repetir código al leer el ResultSet
    private Gasto mapearGasto(ResultSet resultados) throws SQLException {
        Gasto g = new Gasto();
        g.setIdGasto(resultados.getInt("id_gasto"));
        g.setFecha(resultados.getDate("fecha"));
        g.setValorTotalSinIVA(resultados.getDouble("valor_total_sin_iva"));
        g.setIvaTotal(resultados.getDouble("iva_total"));
        g.setValorTotalConIVA(resultados.getDouble("valor_total_con_iva"));
        g.setNombreUsuario(resultados.getString("nombre_usuario"));
        g.setLugar(resultados.getString("lugar"));
        g.setDescripcion(resultados.getString("descripcion"));
        return g;
    }

    // Reporte: Resumen de Gastos por Usuario (Cruzar entidades)
    public List<Gasto> listarPorUsuario(String idUsuario) {
        String sql = "SELECT * FROM gastos WHERE nombre_usuario = ?";
        List<Gasto> lista = new ArrayList<>();
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return lista;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
             
            declaracion.setString(1, idUsuario);
            ResultSet resultados = declaracion.executeQuery();
            
            while (resultados.next()) {
                lista.add(mapearGasto(resultados));
            }
            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
