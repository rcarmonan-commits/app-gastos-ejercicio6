<%@ page import="java.sql.*" %>
<%@ page import="modelo.ConexionBaseDatos" %>
<%
    try {
        Connection con = ConexionBaseDatos.obtenerConexion();
        if (con != null) {
            Statement stmt = con.createStatement();
            try {
                stmt.executeUpdate("ALTER TABLE usuarios ADD COLUMN email VARCHAR(100)");
                out.println("Columna agregada exitosamente.");
            } catch (Exception e) {
                out.println("Error o columna ya existe: " + e.getMessage());
            }
            try {
                stmt.executeUpdate("UPDATE usuarios SET email = 'admin@example.com' WHERE id = 'admin' AND email IS NULL");
                out.println("Email admin actualizado.");
            } catch (Exception e) {}
            stmt.close();
            con.close();
        } else {
            out.println("No se pudo conectar a la BD.");
        }
    } catch (Exception e) {
        out.println("Error general: " + e.getMessage());
    }
%>
