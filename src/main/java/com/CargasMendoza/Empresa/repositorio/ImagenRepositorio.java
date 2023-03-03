
package com.CargasMendoza.Empresa.repositorio;

import com.CargasMendoza.Empresa.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{
    
}
