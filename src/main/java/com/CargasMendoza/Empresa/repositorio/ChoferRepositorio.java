
package com.CargasMendoza.Empresa.repositorio;

import com.CargasMendoza.Empresa.entidades.Chofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ChoferRepositorio extends JpaRepository<Chofer, String> {
    
}
