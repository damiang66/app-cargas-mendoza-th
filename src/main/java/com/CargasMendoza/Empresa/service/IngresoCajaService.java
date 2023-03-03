package com.CargasMendoza.Empresa.service;

import com.CargasMendoza.Empresa.entidades.IngresoCaja;
import com.CargasMendoza.Empresa.entidades.Producto;
import com.CargasMendoza.Empresa.enumeraciones.Zonas;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.IngresoCajaRepositorio;
import com.CargasMendoza.Empresa.repositorio.ProductoRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngresoCajaService {

    @Autowired
    IngresoCajaRepositorio ingresoCajaRepositorio;
    @Autowired
    ProductoRepositorio productoRepositorio;

    /**
     *
     * 
     * @param cantidad
     * @param mes
     * @param anio
     * @param zona
     * @param IdProducto
     * @throws Excepcion
     */
    @Transactional
    public void ingresarCajas(Integer cantidad, Integer mes, Integer anio, String zona, String IdProducto) throws Excepcion {
        Producto producto = productoRepositorio.findById(IdProducto).get();

        IngresoCaja ingresoCaja = new IngresoCaja();
        validar(cantidad, mes, anio, zona, IdProducto);
        ingresoCaja.setFecha(new Date());
        ingresoCaja.setCantidad(cantidad);
        ingresoCaja.setMes(mes);
        ingresoCaja.setAnio(anio);
        ingresoCaja.setZona(zona);
     /*
      if (zona.equals("MZA")) {
            ingresoCaja.setZona(Zonas.MZA);
        }
        if (zona.equals("SAN_JUAN")) {
            ingresoCaja.setZona(Zonas.SAN_JUAN);
        }
        if (zona.equals("ESTE")) {
            ingresoCaja.setZona(Zonas.ESTE);
        }
        if (zona.equals("UCO")) {
            ingresoCaja.setZona(Zonas.UCO);
        }
        if (zona.equals("SAN_RAFAEL")) {
            ingresoCaja.setZona(Zonas.SAN_RAFAEL);
        }
      */

        ingresoCaja.setProducto(producto);
        ingresoCajaRepositorio.save(ingresoCaja);
    }

    /**
     *
     * @param id
     * 
     * @param cantidad
     * @param mes
     * @param anio
     * @param zona
     * @param IdProducto
     * @throws Excepcion
     */
    @Transactional
    public void modificar(String id, Integer cantidad, Integer mes, Integer anio, String zona, String IdProducto) throws Excepcion {
        validar(cantidad, mes, anio, zona, IdProducto);
        Optional<IngresoCaja> respuesta = ingresoCajaRepositorio.findById(id);
        Optional<Producto> respuestaProducto = productoRepositorio.findById(IdProducto);
       Producto producto= new Producto();
        if (respuestaProducto.isPresent()) {
           producto = respuestaProducto.get();
        }
// Producto producto = productoRepositorio.getById(id);
        if (respuesta.isPresent()) {
            IngresoCaja ingresoCaja = respuesta.get();
          //  ingresoCaja.setFecha(new Date());
            ingresoCaja.setCantidad(cantidad);
            ingresoCaja.setMes(mes);
            ingresoCaja.setAnio(anio);
            ingresoCaja.setZona(zona);
            /*
              if (zona.equals("MZA")) {
                ingresoCaja.setZona(Zonas.MZA);
            }
            if (zona.equals("SAN_JUAN")) {
                ingresoCaja.setZona(Zonas.SAN_JUAN);
            }
            if (zona.equals("ESTE")) {
                ingresoCaja.setZona(Zonas.ESTE);
            }
            if (zona.equals("UCO")) {
                ingresoCaja.setZona(Zonas.UCO);
            }
            if (zona.equals("SAN_RAFAEL")) {
                ingresoCaja.setZona(Zonas.SAN_RAFAEL);
            }
             */

            ingresoCaja.setProducto(producto);
            ingresoCajaRepositorio.save(ingresoCaja);
        }

    }

    /**
     * buscar por mes y año
     *
     * @param mes
     * @param anio
     * @return
     */
    public List<IngresoCaja> listarPorMes(Integer mes, Integer anio) {
        List<IngresoCaja> lista = ingresoCajaRepositorio.buscarPorMes(mes, anio);
        return lista;
    }

    /**
     * buscar por zona
     *
     * @param zona
     * @return
     */
    public List<IngresoCaja> listaPorZona(String zona) {
        List<IngresoCaja> lista = ingresoCajaRepositorio.buscarPorZona(zona);
        return lista;
    }

    /**
     * busca un ingreso
     *
     * @param id
     * @return
     */
    public IngresoCaja buscarUnIngreso(String id) {
        return ingresoCajaRepositorio.getOne(id);
    }

    /**
     * buscar todas los Ingresos de cajas
     *
     * @return
     */

    public List<IngresoCaja> buscarTodas() {
        List<IngresoCaja> lista = new ArrayList();
        lista = ingresoCajaRepositorio.findAll();
        return lista;
    }
    /**
     * 
     * @param zona
     * @param idProducto
     * @param mes
     * @param anio
     * @return 
     */
  public List<IngresoCaja> buscarPorMesZonaYProducto(String zona,String idProducto,Integer mes,Integer anio) {
        List<IngresoCaja> lista = new ArrayList();
        lista = ingresoCajaRepositorio.buscarPorZonaMesProducto(mes, anio, zona, idProducto);

        return lista;
    }
    /**
     *
     * 
     * @param cantidad
     * @param mes
     * @param anio
     * @param zona
     * @param IdProducto
     * @throws Excepcion
     */
    private void validar(Integer cantidad, Integer mes, Integer anio, String zona, String IdProducto) throws Excepcion {
      //  if (fecha == null || fecha.isEmpty()) {
         //   throw new Excepcion("La fecha no puede estar vacia");
      //  }
        if (cantidad == null) {
            throw new Excepcion("La cantidad no puede estar vacia");
        }
        if (mes == null) {
            throw new Excepcion("El mes no puede estar vacio");
        }
        if (anio == null) {
            throw new Excepcion("El año no puede estar vacio");
        }
        if (zona == null || zona.isEmpty()) {
            throw new Excepcion("la zona no puede estar vacia");
        }
        if (IdProducto == null || IdProducto.isEmpty()) {
            throw new Excepcion("El producto no puede estar vacio");
        }
    }
}
