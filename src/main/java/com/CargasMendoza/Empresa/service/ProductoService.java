package com.CargasMendoza.Empresa.service;

import com.CargasMendoza.Empresa.entidades.Producto;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.ProductoRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    ProductoRepositorio productoRepositorio;

    /**
     *
     * @param nombre
     *
     * @throws Excepcion
     */
    public void crearProducto(String nombre) throws Excepcion {
        validar(nombre);
        Producto producto = new Producto();
        producto.setNombre(nombre);
        productoRepositorio.save(producto);

    }

    /**
     *
     * @param id
     * @param nombre
     * @throws Excepcion
     */
    public void ModificarProducto(String id, String nombre) throws Excepcion {
        validar(nombre);
        Optional<Producto> respuesta = productoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Producto producto = respuesta.get();
            producto.setNombre(nombre);
            productoRepositorio.save(producto);
        }
    }
    /**
     * 
     * @return 
     */
    public List<Producto> listarProducto(){
        List<Producto> lista = new ArrayList();
        lista= productoRepositorio.findAll();
        return lista;
    }
    /**
     * 
     * @param id
     * @return 
     */
public Producto mostrarProducto(String id){
    return productoRepositorio.getOne(id);
}
    /**
     *
     * @param nombre
     * @throws Excepcion
     */
    private void validar(String nombre) throws Excepcion {
        if (nombre == null || nombre.isEmpty()) {
            throw new Excepcion("El nombre del Producto no puede estar vacio");
        }
    }
}
