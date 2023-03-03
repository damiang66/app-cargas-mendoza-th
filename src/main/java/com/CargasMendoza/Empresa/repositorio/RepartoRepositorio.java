package com.CargasMendoza.Empresa.repositorio;

import com.CargasMendoza.Empresa.entidades.Reparto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepartoRepositorio extends JpaRepository<Reparto, String> {

    /**
     *
     * @param mes
     * @param anio
     * @param idChofer
     * @return
     */
    @Query("select r from Reparto r where r.mes=:mes and r.anio=:anio and r.chofer.id=:idChofer")
    public List<Reparto> buscarPorMes(@Param("mes") Integer mes, @Param("anio") Integer anio, @Param("idChofer") String idChofer);

    /**
     *
     * @param nombre
     * @return
     */
    @Query("select r from Reparto r where r.producto.nombre=:nombre")
    public List<Reparto> buscarPorProducto(@Param("nombre") String nombre);
}
