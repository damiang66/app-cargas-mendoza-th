
package com.CargasMendoza.Empresa.repositorio;

import com.CargasMendoza.Empresa.entidades.Gastos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GastosRepositorio extends JpaRepository<Gastos, String> {
    @Query("select g from Gastos g where g.mes=:mes and g.anio=:anio")
    public List<Gastos> buscarGastos(@Param("mes")Integer mes,@Param("anio")Integer anio);
}
