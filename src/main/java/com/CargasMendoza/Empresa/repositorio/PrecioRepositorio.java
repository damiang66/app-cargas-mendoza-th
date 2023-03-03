
package com.CargasMendoza.Empresa.repositorio;

import com.CargasMendoza.Empresa.entidades.Precio;
import com.CargasMendoza.Empresa.enumeraciones.Zonas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface PrecioRepositorio extends JpaRepository<Precio, String> {
    @Query("select p from Precio p where p.zona=:zona")
    public Precio precioFacturacion(@Param("zona")Zonas zona);
}
