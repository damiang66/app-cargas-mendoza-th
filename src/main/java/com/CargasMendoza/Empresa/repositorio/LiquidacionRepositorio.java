
package com.CargasMendoza.Empresa.repositorio;

import com.CargasMendoza.Empresa.entidades.Liquidacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquidacionRepositorio extends JpaRepository<Liquidacion,String> {
    /**
     * 
     * @param mes
     * @param anio
     * @return 
     */
    @Query("select l from Liquidacion l where l.mes= :mes and l.anio=:anio")
    public List<Liquidacion> listarPorMesYanio(@Param("mes")Integer mes,@Param("anio") Integer anio);
@Query("select l from Liquidacion l where l.mes=:mes and l.anio=:anio and l.chofer.id=:idChofer")
 public List<Liquidacion> listarPorMesYChofer(@Param("mes")Integer mes,@Param("anio") Integer anio,@Param("idChofer")String idChofer);
}
