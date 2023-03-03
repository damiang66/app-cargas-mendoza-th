
package com.CargasMendoza.Empresa.service;

import com.CargasMendoza.Empresa.entidades.Precio;
import com.CargasMendoza.Empresa.enumeraciones.Zonas;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.PrecioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrecioServicio {
    @Autowired
    public PrecioRepositorio precioRepositorio;
    /**
     * 
     * @param zona
     * @param precio
     * @throws Excepcion 
     */
    @Transactional
    public void crear(String zona, Double precio ) throws Excepcion{
        Precio precios = new Precio();
        validar(zona, precio);
        if (zona.equals("MZA")){
            precios.setZona(Zonas.MZA);
        }
         if (zona.equals("DAMIAN")){
            precios.setZona(Zonas.DAMIAN);
        }
        if (zona.equals("SAN_JUAN")){
            precios.setZona(Zonas.SAN_JUAN);
        }
        if (zona.equals("ESTE")){
            precios.setZona(Zonas.ESTE);
        }
        if (zona.equals("UCO")){
            precios.setZona(Zonas.UCO);
        }
        if (zona.equals("SAN_RAFAEL")){
            precios.setZona(Zonas.SAN_RAFAEL);
        }
        precios.setPrecio(precio);
        precioRepositorio.save(precios);
        
    }
    /**
     * 
     * @param id
     * @param zona
     * @param importe
     * @throws Excepcion 
     */
    @Transactional
    public void modificar(String id, String zona,Double importe) throws Excepcion{
        validar(zona, importe);
Optional<Precio> respuesta= precioRepositorio.findById(id);
if(respuesta.isPresent()){
    Precio precio= respuesta.get();
     if (zona.equals("MZA")){
            precio.setZona(Zonas.MZA);
        }
     if (zona.equals("DAMIAN")){
            precio.setZona(Zonas.DAMIAN);
        }
        if (zona.equals("SAN_JUAN")){
            precio.setZona(Zonas.SAN_JUAN);
        }
        if (zona.equals("ESTE")){
            precio.setZona(Zonas.ESTE);
        }
        if (zona.equals("UCO")){
            precio.setZona(Zonas.UCO);
        }
        if (zona.equals("SAN_RAFAEL")){
            precio.setZona(Zonas.SAN_RAFAEL);
        }
        precio.setPrecio(importe);
        precioRepositorio.save(precio);
}
    }
    /**
     * 
     * @return 
     */
    public List<Precio> listar(){
        List<Precio> lista = new ArrayList();
        lista= precioRepositorio.findAll();
        return lista;
    }
    /**
     * 
     * @param id
     * @return 
     */
    public Precio mostarUnPrecio(String id){
        return precioRepositorio.getOne(id);
    }
    /**
     * 
     * @param zona
     * @param precio
     * @throws Excepcion 
     */
    private void validar(String zona, Double precio) throws Excepcion{
        if(zona.isEmpty()|| zona==null){
            throw new Excepcion("la zona no puede estar vacia");
        }
        if (precio==null){
            throw new Excepcion("El precio no puede estar vacio");
        }
    }
}
