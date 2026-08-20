package modelo;

import java.sql.Date;

/*
 * Archivo: Gasto.java
 * Justificación metodológica: Como estudiante, implemento esta clase POJO para representar 
 * la entidad Gasto (Ejercicio 6) de la base de datos. Utilizo los tipos de datos apropiados 
 * (java.sql.Date para fechas SQL, double para valores monetarios).
 */
public class Gasto {
    private int idGasto;
    private Date fecha;
    private double valorTotalSinIVA;
    private double ivaTotal;
    private double valorTotalConIVA;
    private String nombreUsuario; // Llave foránea a Usuario.id
    private String lugar;
    private String descripcion;

    public Gasto() {
    }

    public Gasto(int idGasto, Date fecha, double valorTotalSinIVA, double ivaTotal, double valorTotalConIVA, String nombreUsuario, String lugar, String descripcion) {
        this.idGasto = idGasto;
        this.fecha = fecha;
        this.valorTotalSinIVA = valorTotalSinIVA;
        this.ivaTotal = ivaTotal;
        this.valorTotalConIVA = valorTotalConIVA;
        this.nombreUsuario = nombreUsuario;
        this.lugar = lugar;
        this.descripcion = descripcion;
    }

    public int getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(int idGasto) {
        this.idGasto = idGasto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getValorTotalSinIVA() {
        return valorTotalSinIVA;
    }

    public void setValorTotalSinIVA(double valorTotalSinIVA) {
        this.valorTotalSinIVA = valorTotalSinIVA;
    }

    public double getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(double ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    public double getValorTotalConIVA() {
        return valorTotalConIVA;
    }

    public void setValorTotalConIVA(double valorTotalConIVA) {
        this.valorTotalConIVA = valorTotalConIVA;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
