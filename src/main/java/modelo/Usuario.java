package modelo;

/*
 * Archivo: Usuario.java
 * Justificación metodológica: Como estudiante, implemento esta clase POJO (Plain Old Java Object)
 * para representar la entidad Usuario de la base de datos. Utilizo encapsulamiento con atributos
 * privados y métodos getter/setter para proteger la información.
 */
public class Usuario {
    private String id;
    private String clave;
    private String nombre;
    private String email;
    private String rol;

    public Usuario() {
    }

    public Usuario(String id, String clave, String nombre, String email, String rol) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
