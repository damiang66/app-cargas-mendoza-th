package com.CargasMendoza.Empresa.repositorio;

import com.CargasMendoza.Empresa.entidades.Viaje;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ViajeRepositorio extends JpaRepository<Viaje, String> {

    /**
     *
     * @param mes
     * @param anio
     * @return
     */
    @Query("select v from Viaje v where v.mes=:mes and v.anio=:anio")
    public List<Viaje> buscarPorMes(@Param("mes") Integer mes, @Param("anio") Integer anio);

    /**
     *
     * @param mes
     * @param anio
     * @param zona
     * @return
     */
    @Query("select v from Viaje v where v.mes=:mes and v.anio=:anio and v.zona=:zona")
    public List<Viaje> buscarPorMesYZona(@Param("mes") Integer mes, @Param("anio") Integer anio, @Param("zona") String zona);
}
