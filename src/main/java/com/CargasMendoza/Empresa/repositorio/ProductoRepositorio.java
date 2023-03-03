
package com.CargasMendoza.Empresa.repositorio;

import com.CargasMendoza.Empresa.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductoRepositorio extends JpaRepository<Producto, String>{
    
}
