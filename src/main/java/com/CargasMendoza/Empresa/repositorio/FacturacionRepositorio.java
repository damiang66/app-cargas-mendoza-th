
package com.CargasMendoza.Empresa.repositorio;

import com.CargasMendoza.Empresa.entidades.Facturacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturacionRepositorio extends JpaRepository<Facturacion,String> {
    @Query("select f from Facturacion f where f.mes=:mes and f.anio=:anio")
    public List<Facturacion> listarPorMes(@Param("mes")Integer mes,@Param("anio")Integer anio);
}
