package com.CargasMendoza.Empresa.service;

import com.CargasMendoza.Empresa.entidades.Chofer;
import com.CargasMendoza.Empresa.entidades.Producto;
import com.CargasMendoza.Empresa.entidades.Reparto;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.ChoferRepositorio;
import com.CargasMendoza.Empresa.repositorio.ProductoRepositorio;
import com.CargasMendoza.Empresa.repositorio.RepartoRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class RepartoService {

    @Autowired
    RepartoRepositorio repartoRepositorio;
    @Autowired
    ChoferRepositorio choferRepositorio;
    @Autowired
    ProductoRepositorio productoRepositorio;

    /**
     *
     * @param cantidad
     * @param fecha
     * @param precio
     * @param mes
     * @param anio
     * @param idChofer
     * @param idProducto
     * @throws Excepcion
     */
    public void crearReparto(Integer cantidad, Date fecha, Double precio, Integer mes, Integer anio, String idChofer, String idProducto) throws Excepcion {
        validar(cantidad, fecha, precio, mes, anio, idChofer, idProducto);
        Producto producto = productoRepositorio.findById(idProducto).get();
        Chofer chofer = choferRepositorio.findById(idChofer).get();
        Reparto reparto = new Reparto();
        reparto.setAnio(anio);
        reparto.setChofer(chofer);
        reparto.setFecha(fecha);
        reparto.setProducto(producto);
        reparto.setPrecio(precio);
        reparto.setMes(mes);
        reparto.setCantidad(cantidad);
        repartoRepositorio.save(reparto);
    }

    /**
     *
     * @param cantidad
     * @param fecha
     * @param precio
     * @param mes
     * @param anio
     * @param idChofer
     * @param idProducto
     * @param id
     * @throws Excepcion
     */
    public void modificarReparto(Integer cantidad, Date fecha, Double precio, Integer mes, Integer anio, String idChofer, String idProducto, String id) throws Excepcion {
        validar(cantidad, fecha, precio, mes, anio, idChofer, idProducto);
        Chofer chofer = new Chofer();
        Producto producto = new Producto();
        Optional<Chofer> respuestaChofer = choferRepositorio.findById(idChofer);
        Optional<Producto> respuestaProducto = productoRepositorio.findById(idProducto);
        Optional<Reparto> respuesta = repartoRepositorio.findById(id);
        if (respuestaChofer.isPresent()) {
            chofer = respuestaChofer.get();
        }
        if (respuestaProducto.isPresent()) {
            producto = respuestaProducto.get();
        }
        if (respuesta.isPresent()) {
            Reparto reparto = respuesta.get();
            reparto.setAnio(anio);
            reparto.setChofer(chofer);
           
            reparto.setProducto(producto);
            reparto.setPrecio(precio);
            reparto.setMes(mes);
            reparto.setCantidad(cantidad);
            repartoRepositorio.save(reparto);
        }
    }

    /**
     *
     * @return
     */
    public List<Reparto> listar() {
        List<Reparto> lista = new ArrayList();
        lista = repartoRepositorio.findAll();
        return lista;
    }
/**
 * 
 * @param nombre
 * @return 
 */
    public List<Reparto> listarPorProducto(String nombre) {
        List<Reparto> lista = new ArrayList();
        lista = repartoRepositorio.buscarPorProducto(nombre);
        return lista;
    }

    /**
     *
     * @param mes
     * @param anio
     * @return
     */

    public List<Reparto> listarPorMes(Integer mes, Integer anio,String idChofer) {
        List<Reparto> lista = new ArrayList();
        lista = repartoRepositorio.buscarPorMes(mes, anio,idChofer);
        return lista;
    }
    /**
     * 
     * @param id
     * @return 
     */
    public Reparto buscarUnReparto(String id){
        return repartoRepositorio.getOne(id);
    }

    /**
     *
     * @param cantidad
     * @param fecha
     * @param precio
     * @param mes
     * @param anio
     * @param idChofer
     * @param idProducto
     * @throws Excepcion
     */
    private void validar(Integer cantidad, Date fecha, Double precio, Integer mes, Integer anio, String idChofer, String idProducto) throws Excepcion {
        if (cantidad == null) {
            throw new Excepcion("La cantidad de cajas del Reparto no puede estar vacia");
        }
        if (fecha == null) {
            throw new Excepcion("La fecha no puede estar vacia");

        }
        if (precio == null) {
            throw new Excepcion("El precio no puede estar vacio");

        }
        if (mes == null) {
            throw new Excepcion("El mes no puede estar vacio");

        }
        if (anio == null) {
            throw new Excepcion("El año no puede estar vacio");

        }
        if (idChofer == null || idChofer.isEmpty()) {
            throw new Excepcion("El chofer no puede estar vacio");
        }
        if (idProducto == null || idProducto.isEmpty()) {
            throw new Excepcion("El producto no puede estar vacio");
        }
    }

}
