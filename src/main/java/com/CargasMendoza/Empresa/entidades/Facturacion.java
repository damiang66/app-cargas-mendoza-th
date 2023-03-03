
package com.CargasMendoza.Empresa.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Facturacion {
   @Id
   @GeneratedValue(generator="uuid")
   @GenericGenerator(name="uuid",strategy="uuid2")
   private String id;
   private Integer cantidad;
   private Double importe;
   private Double gastos;
   private Double viajes;
   private double total;
   private Integer mes;
   private Integer anio;

    public Facturacion() {
    }

    public String getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getGastos() {
        return gastos;
    }

    public void setGastos(Double gastos) {
        this.gastos = gastos;
    }

    public Double getViajes() {
        return viajes;
    }

    public void setViajes(Double viajes) {
        this.viajes = viajes;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }
   
   
}
