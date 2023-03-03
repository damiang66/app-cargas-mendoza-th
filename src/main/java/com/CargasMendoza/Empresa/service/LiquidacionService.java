package com.CargasMendoza.Empresa.service;

import com.CargasMendoza.Empresa.entidades.Adelanto;
import com.CargasMendoza.Empresa.entidades.Chofer;
import com.CargasMendoza.Empresa.entidades.Liquidacion;
import com.CargasMendoza.Empresa.entidades.Precio;
import com.CargasMendoza.Empresa.entidades.Reparto;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.LiquidacionRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiquidacionService {

    @Autowired
    private LiquidacionRepositorio liquidacionRepositorio;
    @Autowired
    private RepartoService repartoServicio;
    @Autowired
    private AdelantoService adelantoServicio;
    @Autowired
    private PrecioServicio precioServicio;
    @Autowired
    private ChoferService choferServicio;
/**
 * validacion de datos
 * @param idChofer
 * @param idAdelanto
 * @param idReparto
 * @param mes
 * @param anio
 * @throws Excepcion 
 */
    private void validar(String idChofer,String idPrecio,  Integer mes, Integer anio) throws Excepcion {
        
        
                
                
        if (idChofer == null || idChofer.isEmpty()) {
            throw new Excepcion("El chofer no puede estar vacio");
        }
          if (idPrecio == null || idPrecio.isEmpty()) {
            throw new Excepcion("El chofer no puede estar vacio");
        }
      
        if (mes == null) {
            throw new Excepcion("El mes no puede estar vacio");

        }
        if (anio == null) {
            throw new Excepcion("El año no puede estar vacio");

        }
        

    }
    /**
     * cargar chofer
     * @param idChofer
     * @param mes
     * @param anio
     * @param idPrecio
     * @throws Excepcion 
     */
    @Transactional
    public void cargar(String idChofer, Integer mes, Integer anio,String idPrecio) throws Excepcion {
       
        validar(idChofer,idPrecio, mes, anio);
        List<Liquidacion> lista = liquidacionRepositorio.findAll();
       
        for (int i = 0; i < lista.size(); i++) {
            /*
            System.out.println(lista.get(i).getAnio());
            System.out.println(lista.get(i).getMes());
            System.out.println(lista.get(i).getChofer().getId());
            */
         
        }
        Liquidacion liquidacion = new Liquidacion();
        Chofer chofer =  choferServicio.mostarUnChofer(idChofer);
        List<Adelanto> listaAdelanto = adelantoServicio.listarPorMesYChofer(idChofer, anio, mes);
        List<Reparto> listaReparto =  repartoServicio.listarPorMes(mes, anio, idChofer);
        Precio precio = new Precio();
        precio= precioServicio.mostarUnPrecio(idPrecio);
        Integer cantidad=0;
        for (Reparto reparto : listaReparto) {
            cantidad+=reparto.getCantidad();
        }
        Double importe= precio.getPrecio()*cantidad;
        double adelanto=0;
        for (Adelanto aux : listaAdelanto) {
            adelanto+=aux.getImporte();
        }
        liquidacion.setAdelanto(adelanto);
        liquidacion.setAnio(anio);
        liquidacion.setCantidad(cantidad);
        liquidacion.setChofer(chofer);
        liquidacion.setImporte(importe);
        liquidacion.setMes(mes);
        liquidacion.setTotal(importe-adelanto);
        liquidacionRepositorio.save(liquidacion);
    }
    /**
     * modificar
     * @param id
     * @param idChofer
     * @param mes
     * @param anio
     * @param idPrecio
     * @throws Excepcion 
     */
    @Transactional
    public void modificar(String id,String idChofer, Integer mes, Integer anio,String idPrecio) throws Excepcion {
         List<Liquidacion> lista = liquidacionRepositorio.findAll();
        for (Liquidacion aux : lista) {
            if(aux.getMes()==mes){
            if(aux.getAnio()==anio){
                if(aux.getChofer().getId().equals(idChofer)){
                    throw new Excepcion("Esta liquidacion ya esta cargada");
                }
                
            }
        }
        }
        validar(idChofer, idPrecio, mes, anio);
        Optional<Liquidacion>respuesta=liquidacionRepositorio.findById(id);
        if (respuesta.isPresent()){
            Liquidacion liquidacion = respuesta.get();
            Chofer chofer =  choferServicio.mostarUnChofer(idChofer);
        List<Adelanto> listaAdelanto = adelantoServicio.listarPorMesYChofer(idChofer, anio, mes);
        List<Reparto> listaReparto =  repartoServicio.listarPorMes(mes, anio, idChofer);
        Precio precio = new Precio();
        precio= precioServicio.mostarUnPrecio(idPrecio);
        Integer cantidad=0;
        for (Reparto reparto : listaReparto) {
            cantidad+=reparto.getCantidad();
        }
        Double importe= precio.getPrecio();
        double adelanto=0;
        for (Adelanto aux : listaAdelanto) {
            adelanto+=aux.getImporte();
        }
        liquidacion.setAdelanto(adelanto);
        liquidacion.setAnio(anio);
        liquidacion.setCantidad(cantidad);
        liquidacion.setChofer(chofer);
        liquidacion.setImporte(importe);
        liquidacion.setMes(mes);
        liquidacion.setTotal(importe-adelanto);
        liquidacionRepositorio.save(liquidacion);
        }
        
}
    /**
     * retornamos una liquidacion
     * @param id
     * @return 
     */
    public Liquidacion buscarUno(String id){
        return liquidacionRepositorio.getOne(id);
    }
    /**
     *  todas las liquidaciones
     * @return 
     */
    public List<Liquidacion> todas(){
        return liquidacionRepositorio.findAll();
    }
    /**
     * retornamos liquidaciones del mes y año
     * @param mes
     * @param anio
     * @return 
     */
    public List<Liquidacion> buscarPorMesYChofer(Integer mes,Integer anio,String idChofer){
        return liquidacionRepositorio.listarPorMesYChofer(mes, anio,idChofer);
    }
}