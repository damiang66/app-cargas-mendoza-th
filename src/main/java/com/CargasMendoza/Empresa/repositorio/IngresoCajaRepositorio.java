
package com.CargasMendoza.Empresa.repositorio;

import com.CargasMendoza.Empresa.entidades.IngresoCaja;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IngresoCajaRepositorio extends JpaRepository<IngresoCaja, String> {
    @Query("select i from IngresoCaja i where i.mes=:mes and i.anio=:anio")
    public List<IngresoCaja>buscarPorMes(@Param("mes") Integer mes,@Param("anio") Integer anio);
    @Query("select i from IngresoCaja i where i.zona=:zona")
     public List<IngresoCaja> buscarPorZona(@Param("zona") String zona);
    @Query("select i from IngresoCaja i where i.zona =:zona and  i.mes=:mes and i.anio=:anio and i.producto.id=:id")
    public List<IngresoCaja> buscarPorZonaMesProducto(@Param("mes") Integer mes,@Param("anio") Integer anio,@Param("zona")String zona,@Param("id")String id);
}
