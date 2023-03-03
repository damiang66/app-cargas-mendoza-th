
package com.CargasMendoza.Empresa.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Chofer {
 @Id
 @GeneratedValue(generator="uuid")
 @GenericGenerator(name="uuid",strategy="uuid2")
 private String id;
 private String nombre;
 private String apellido;

    public Chofer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
 
}
