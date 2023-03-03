package com.CargasMendoza.Empresa.service;

import com.CargasMendoza.Empresa.entidades.Facturacion;
import com.CargasMendoza.Empresa.entidades.Gastos;
import com.CargasMendoza.Empresa.entidades.IngresoCaja;
import com.CargasMendoza.Empresa.entidades.Precio;
import com.CargasMendoza.Empresa.entidades.Viaje;
import com.CargasMendoza.Empresa.enumeraciones.Zonas;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.FacturacionRepositorio;
import com.CargasMendoza.Empresa.repositorio.GastosRepositorio;
import com.CargasMendoza.Empresa.repositorio.IngresoCajaRepositorio;
import com.CargasMendoza.Empresa.repositorio.PrecioRepositorio;
import com.CargasMendoza.Empresa.repositorio.ViajeRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturacionService {

    @Autowired
    private FacturacionRepositorio facturacionRepositorio;
    @Autowired
    private IngresoCajaRepositorio ingresoCajaRepositorio;
    @Autowired
    private PrecioRepositorio precioRepositorio;
    @Autowired
    private GastosRepositorio gastosRepositorio;
    @Autowired
    private ViajeRepositorio viajeRepositorio;

    /**
     * crear Facturacion
     *
     * @param mes
     * @param anio
     * @throws Excepcion
     */
    @Transactional
    public void crear(Integer mes, Integer anio) throws Excepcion {
        validar(mes, anio);
        Facturacion facturacion = new Facturacion();
        List<IngresoCaja> listaCajas = ingresoCajaRepositorio.buscarPorMes(mes, anio);
        Integer cantidad = 0;
        for (IngresoCaja aux : listaCajas) {
            cantidad += aux.getCantidad();
        }
      
      
         Precio precio = precioRepositorio.precioFacturacion(Zonas.DAMIAN);
            System.out.println(precio.getPrecio());
            double importe = precio.getPrecio();
        importe = importe * cantidad;
        double gastos = 0;
        List<Gastos> listaGastos = gastosRepositorio.buscarGastos(mes, anio);
        for (Gastos listaGasto : listaGastos) {
            gastos += listaGasto.getImporte();
        }
        List<Viaje> listaViajes = viajeRepositorio.buscarPorMes(mes, anio);
        double importeViaje = 0;
        for (Viaje listaViaje : listaViajes) {
            importeViaje += listaViaje.getImporte();
        }
        double total = importe + importeViaje - gastos;
        facturacion.setAnio(anio);
        facturacion.setCantidad(cantidad);
        facturacion.setGastos(gastos);
        facturacion.setImporte(importe);
        facturacion.setMes(mes);
        facturacion.setTotal(total);
        facturacion.setViajes(importeViaje);
        facturacionRepositorio.save(facturacion);
    }

    /**
     * modficar facturacion
     *
     * @param id
     * @param mes
     * @param anio
     * @throws Excepcion
     */
    public void modificar(String id, Integer mes, Integer anio) throws Excepcion {
        validar(mes, anio);
        Optional<Facturacion> respuesta = facturacionRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Facturacion facturacion = respuesta.get();
            List<IngresoCaja> listaCajas = ingresoCajaRepositorio.buscarPorMes(mes, anio);
            Integer cantidad = 0;
            for (IngresoCaja aux : listaCajas) {
                cantidad += aux.getCantidad();
            }
          
            Precio precio = precioRepositorio.precioFacturacion(Zonas.DAMIAN);
            System.out.println(precio.getPrecio());
            double importe = precio.getPrecio();
            
            /*
            for (Precio precio : listaPrecio) {
                if(precio.getZona().equals("DAMIAN")){
                    System.out.println("hola");
                    importe+=precio.getPrecio();
                }
            }
            */
            importe = importe * cantidad;
            double gastos = 0;
            List<Gastos> listaGastos = gastosRepositorio.buscarGastos(mes, anio);
            for (Gastos listaGasto : listaGastos) {
                gastos += listaGasto.getImporte();
            }
            List<Viaje> listaViajes = viajeRepositorio.buscarPorMes(mes, anio);
            double importeViaje = 0;
            for (Viaje listaViaje : listaViajes) {
                importeViaje += listaViaje.getImporte();
            }
            double total = importe + importeViaje - gastos;
            facturacion.setAnio(anio);
            facturacion.setCantidad(cantidad);
            facturacion.setGastos(gastos);
            facturacion.setImporte(importe);
            facturacion.setMes(mes);
            facturacion.setTotal(total);
            facturacion.setViajes(importeViaje);
            facturacionRepositorio.save(facturacion);
        }
    }
    /**
     * lista todas las facturas
     * @return 
     */
    public List<Facturacion> listar(){
        return facturacionRepositorio.findAll();
    }
    /**
     * una sola factura
     * @param id
     * @return 
     */
    public Facturacion unaFactura(String id){
        return facturacionRepositorio.getOne(id);
    }
    /**
     * busca una factura por mes y año
     * @param mes
     * @param anio
     * @return 
     */
    public List<Facturacion> buscarPorMes(Integer mes,Integer anio){
        return facturacionRepositorio.listarPorMes(mes, anio);
    }

    /**
     * validar datos
     *
     * @param mes
     * @param anio
     * @throws Excepcion
     */
    private void validar(Integer mes, Integer anio) throws Excepcion {
        if (mes == null) {
            throw new Excepcion("El mes no puede ser nulo");
        }
        if (anio == null) {
            throw new Excepcion("El año no puede ser nulo");
        }
    }
}
