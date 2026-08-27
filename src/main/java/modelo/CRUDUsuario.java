package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Archivo: CRUDUsuario.java
 * Justificación metodológica: Como estudiante, implemento el patrón DAO (Data Access Object)
 * para separar la lógica de base de datos de la lógica de negocio.
 * ¡MUY IMPORTANTE!: Se utiliza PreparedStatement en lugar de Statement simple. Esto evita
 * la inyección SQL, ya que los parámetros se envían de forma segura y separada de la consulta
 * SQL original, evitando que un atacante inyecte comandos maliciosos a través de las entradas de texto.
 */
public class CRUDUsuario {

    public boolean agregar(Usuario usuario) {
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) {
            System.err.println("Error: No se pudo obtener la conexión a la base de datos.");
            return false;
        }
        String sql = "INSERT INTO usuarios (id, clave, nombre, email, rol) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
            
            declaracion.setString(1, usuario.getId());
            declaracion.setString(2, usuario.getClave());
            declaracion.setString(3, usuario.getNombre());
            declaracion.setString(4, usuario.getEmail());
            declaracion.setString(5, usuario.getRol());
            
            int filasAfectadas = declaracion.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modificar(Usuario usuario) {
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return false;
        String sql = "UPDATE usuarios SET clave = ?, nombre = ?, email = ?, rol = ? WHERE id = ?";
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
            
            declaracion.setString(1, usuario.getClave());
            declaracion.setString(2, usuario.getNombre());
            declaracion.setString(3, usuario.getEmail());
            declaracion.setString(4, usuario.getRol());
            declaracion.setString(5, usuario.getId());
            
            int filasAfectadas = declaracion.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(String id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return false;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
            
            declaracion.setString(1, id);
            
            int filasAfectadas = declaracion.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario buscar(String id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        Usuario usuarioEncontrado = null;
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return null;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
            
            declaracion.setString(1, id);
            ResultSet resultados = declaracion.executeQuery();
            
            if (resultados.next()) {
                usuarioEncontrado = new Usuario();
                usuarioEncontrado.setId(resultados.getString("id"));
                usuarioEncontrado.setClave(resultados.getString("clave"));
                usuarioEncontrado.setNombre(resultados.getString("nombre"));
                usuarioEncontrado.setEmail(resultados.getString("email"));
                usuarioEncontrado.setRol(resultados.getString("rol"));
            }
            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioEncontrado;
    }

    public List<Usuario> listar() {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> lista = new ArrayList<>();
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return lista;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql);
             ResultSet resultados = declaracion.executeQuery()) {
            
            while (resultados.next()) {
                Usuario u = new Usuario();
                u.setId(resultados.getString("id"));
                u.setClave(resultados.getString("clave"));
                u.setNombre(resultados.getString("nombre"));
                u.setEmail(resultados.getString("email"));
                u.setRol(resultados.getString("rol"));
                lista.add(u);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Usuario iniciarSesion(String id, String clave) {
        String sql = "SELECT * FROM usuarios WHERE id = ? AND clave = ?";
        Usuario usuarioEncontrado = null;
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return null;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
            
            declaracion.setString(1, id);
            declaracion.setString(2, clave);
            ResultSet resultados = declaracion.executeQuery();
            
            if (resultados.next()) {
                usuarioEncontrado = new Usuario();
                usuarioEncontrado.setId(resultados.getString("id"));
                usuarioEncontrado.setClave(resultados.getString("clave"));
                usuarioEncontrado.setNombre(resultados.getString("nombre"));
                usuarioEncontrado.setEmail(resultados.getString("email"));
                usuarioEncontrado.setRol(resultados.getString("rol"));
            }
            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioEncontrado;
    }

    // Reporte: Listar por rol
    public List<Usuario> listarPorRol(String rol) {
        String sql = "SELECT * FROM usuarios WHERE rol = ?";
        List<Usuario> lista = new ArrayList<>();
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return lista;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
             
            declaracion.setString(1, rol);
            ResultSet resultados = declaracion.executeQuery();
            
            while (resultados.next()) {
                Usuario u = new Usuario();
                u.setId(resultados.getString("id"));
                u.setClave(resultados.getString("clave"));
                u.setNombre(resultados.getString("nombre"));
                u.setEmail(resultados.getString("email"));
                u.setRol(resultados.getString("rol"));
                lista.add(u);
            }
            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Reporte: Buscar por nombre usando LIKE
    public List<Usuario> buscarPorNombre(String parteNombre) {
        String sql = "SELECT * FROM usuarios WHERE nombre LIKE ?";
        List<Usuario> lista = new ArrayList<>();
        Connection conexion = ConexionBaseDatos.obtenerConexion();
        if (conexion == null) return lista;
        try (PreparedStatement declaracion = conexion.prepareStatement(sql)) {
             
            // Se le añaden comodines para buscar en cualquier parte del nombre
            declaracion.setString(1, "%" + parteNombre + "%");
            ResultSet resultados = declaracion.executeQuery();
            
            while (resultados.next()) {
                Usuario u = new Usuario();
                u.setId(resultados.getString("id"));
                u.setClave(resultados.getString("clave"));
                u.setNombre(resultados.getString("nombre"));
                u.setEmail(resultados.getString("email"));
                u.setRol(resultados.getString("rol"));
                lista.add(u);
            }
            resultados.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
