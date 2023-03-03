
package com.CargasMendoza.Empresa.repositorio;

import com.CargasMendoza.Empresa.entidades.Adelanto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdelantoRepositorio extends JpaRepository<Adelanto, String> {
    @Query("select a from Adelanto a where a.mes=:mes and a.anio=:anio and a.chofer.id=:id")
    public List<Adelanto> buscarPorMesYChofer(@Param("mes")Integer mes,@Param("anio")Integer anio,@Param("id")String chofer);
}
