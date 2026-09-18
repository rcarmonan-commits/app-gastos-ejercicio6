package modelo;

public class ConfiguracionSMTP {
    private int id;
    private String host;
    private String puerto;
    private String usuario;
    private String clave;

    public ConfiguracionSMTP() {
    }

    public ConfiguracionSMTP(int id, String host, String puerto, String usuario, String clave) {
        this.id = id;
        this.host = host;
        this.puerto = puerto;
        this.usuario = usuario;
        this.clave = clave;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public String getPuerto() { return puerto; }
    public void setPuerto(String puerto) { this.puerto = puerto; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
