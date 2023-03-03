package com.CargasMendoza.Empresa.service;

import com.CargasMendoza.Empresa.entidades.Viaje;
import com.CargasMendoza.Empresa.enumeraciones.Zonas;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.ViajeRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViajeServicio {

    @Autowired
    ViajeRepositorio viajeRepositorio;

    /**
     * validar datos
     *
     * @param zona
     * @param importe
     * @param mes
     * @param anio
     * @throws Excepcion
     */
    private void validar(String zona, Double importe, Integer mes, Integer anio) throws Excepcion {
        if (zona.isEmpty() || zona == null) {
            throw new Excepcion("la zona no puede estar vacia");
        }
        if (importe == null) {
            throw new Excepcion("El importe no puede estar vacio");
        }
        if (mes == null) {
            throw new Excepcion("El mes no puede estar vacio");
        }
        if (anio == null) {
            throw new Excepcion("El año no puede estar vacio");
        }
    }

    /**
     * crear viaje
     *
     * @param zona
     * @param importe
     * @param mes
     * @param anio
     * @throws Excepcion
     */
    public void cargar(String zona, Double importe, Integer mes, Integer anio) throws Excepcion {
        validar(zona, importe, mes, anio);
        Viaje viaje = new Viaje();
        viaje.setAnio(anio);
        viaje.setImporte(importe);
        viaje.setMes(mes);
        /*
         if (zona.equals("MZA")) {
            viaje.setZona(Zonas.MZA);
        }
        if (zona.equals("ESTE")) {
            viaje.setZona(Zonas.ESTE);
        }
        if (zona.equals("UCO")) {
            viaje.setZona(Zonas.UCO);
        }
        if (zona.equals("SAN_JUAN")) {
            viaje.setZona(Zonas.SAN_JUAN);
        }
        if (zona.equals("SAN_RAFAEL")) {
            viaje.setZona(Zonas.SAN_RAFAEL);
        }
        */
        viaje.setZona(zona);
        viajeRepositorio.save(viaje);

    }

    /**
     * modificar viaje
     *
     * @param id
     * @param zona
     * @param importe
     * @param mes
     * @param anio
     * @throws Excepcion
     */
    public void Modificar(String id, String zona, Double importe, Integer mes, Integer anio) throws Excepcion {
        validar(zona, importe, mes, anio);
        Optional<Viaje> respuesta = viajeRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Viaje viaje = respuesta.get();
            viaje.setAnio(anio);
            viaje.setImporte(importe);
            viaje.setMes(mes);
            viaje.setZona(zona);
            /*
            if (zona.equals("MZA")) {
                viaje.setZona(Zonas.MZA);
            }S
            if (zona.equals("ESTE")) {
                viaje.setZona(Zonas.ESTE);
            }
            if (zona.equals("UCO")) {
                viaje.setZona(Zonas.UCO);
            }
            if (zona.equals("SAN_JUAN")) {
                viaje.setZona(Zonas.SAN_JUAN);
            }
            if (zona.equals("SAN_RAFAEL")) {
                viaje.setZona(Zonas.SAN_RAFAEL);
            }
        */
       
            viajeRepositorio.save(viaje);
        }
    }
    /**
     * listar todos los viajes
     * @return 
     */
    public List<Viaje> listarTodosLosViajes(){
        List<Viaje> lista= new ArrayList();
        lista=viajeRepositorio.findAll();
        
        return lista;
    }
    /**
     *  mostrar un solo viaje
     * @param id
     * @return 
     */
    public Viaje unViaje(String id){
        return viajeRepositorio.getOne(id);
    }
    /**
     * buscar un viaje por Zona mes y año
     * @param zona
     * @param mes
     * @param anio
     * @return 
     */
    public List<Viaje> listaPorZona(String zona,Integer mes,Integer anio){
        List<Viaje> lista = new ArrayList();
        lista=viajeRepositorio.buscarPorMesYZona(mes, anio, zona);
       
        return lista;
    }
}
