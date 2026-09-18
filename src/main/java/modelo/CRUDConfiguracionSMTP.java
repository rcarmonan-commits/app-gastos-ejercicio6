package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CRUDConfiguracionSMTP {

    public ConfiguracionSMTP obtenerConfiguracion() {
        ConfiguracionSMTP config = null;
        String sql = "SELECT * FROM configuracion_smtp LIMIT 1";
        try (Connection con = ConexionBaseDatos.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                config = new ConfiguracionSMTP(
                    rs.getInt("id"),
                    rs.getString("host"),
                    rs.getString("puerto"),
                    rs.getString("usuario"),
                    rs.getString("clave")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return config;
    }

    public boolean actualizarConfiguracion(ConfiguracionSMTP config) {
        String sql = "UPDATE configuracion_smtp SET host=?, puerto=?, usuario=?, clave=? WHERE id=?";
        try (Connection con = ConexionBaseDatos.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, config.getHost());
            ps.setString(2, config.getPuerto());
            ps.setString(3, config.getUsuario());
            ps.setString(4, config.getClave());
            ps.setInt(5, config.getId());
            
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
