package com.CargasMendoza.Empresa.service;

import com.CargasMendoza.Empresa.entidades.Adelanto;
import com.CargasMendoza.Empresa.entidades.Chofer;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.AdelantoRepositorio;
import com.CargasMendoza.Empresa.repositorio.ChoferRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdelantoService {

    @Autowired
    AdelantoRepositorio adelantoRepositorio;
    @Autowired
    ChoferRepositorio choferReposiotorio;

    /**
     * validar datos
     *
     * @param idChofer
     * @param descripcion
     * @param mes
     * @param anio
     * @param importe
     */
    public void validar(String idChofer, String descripcion, Integer mes, Integer anio, Double importe) throws Excepcion {
        if (idChofer == null || idChofer.isEmpty()) {
            throw new Excepcion("El chofer no puede estar vacio");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new Excepcion("La descripcion no puede estar vacio");
        }
        if (mes == null) {
            throw new Excepcion("El mes no puede estar vacio");
        }
        if (anio == null) {
            throw new Excepcion("El año no puede estar vacio");
        }
        if (importe == null) {
            throw new Excepcion("El importe no puede estar vacio");
        }

    }

    /**
     * ingresar
     *
     * @param idChofer
     * @param descripcion
     * @param mes
     * @param anio
     * @param importe
     */
    public void ingresarAdelanto(String idChofer, String descripcion, Integer mes, Integer anio, Double importe) throws Excepcion {
        Chofer chofer = choferReposiotorio.findById(idChofer).get();
        validar(idChofer, descripcion, mes, anio, importe);

        Adelanto adelanto = new Adelanto();
        adelanto.setAnio(anio);
        adelanto.setChofer(chofer);
        adelanto.setDescripcion(descripcion);
        adelanto.setMes(mes);
        adelanto.setImporte(importe);
        adelantoRepositorio.save(adelanto);

    }

    /**
     * Modificar
     *
     * @param id
     * @param idChofer
     * @param descripcion
     * @param mes
     * @param anio
     * @param importe
     * @throws Excepcion
     */
    public void modificar(String id, String idChofer, String descripcion, Integer mes, Integer anio, Double importe) throws Excepcion {
        validar(idChofer, descripcion, mes, anio, importe);
        
        Optional<Chofer> respuestaChofer = choferReposiotorio.findById(idChofer);
        Optional<Adelanto> respuesta = adelantoRepositorio.findById(id);
        Chofer chofer = new Chofer();
        if (respuestaChofer.isPresent()) {
            chofer = respuestaChofer.get();
        }
        if (respuesta.isPresent()) {
            Adelanto adelanto = respuesta.get();
            adelanto.setAnio(anio);
            adelanto.setChofer(adelanto.getChofer());
            adelanto.setDescripcion(descripcion);
            adelanto.setMes(mes);
            adelanto.setImporte(importe);
            adelantoRepositorio.save(adelanto);
        }
    }
    /**
     * listar por mes y chofer
     * @param idChofer
     * @param anio
     * @param mes
     * @return 
     */
    public List<Adelanto> listarPorMesYChofer(String idChofer,Integer anio,Integer mes){
        List<Adelanto> lista = new ArrayList();
        lista=adelantoRepositorio.buscarPorMesYChofer(mes, anio, idChofer);
        return lista;
    }
    /**
     * todos los adelantos
     * @return 
     */
    public List<Adelanto> listarTodos(){
        List<Adelanto>lista = new ArrayList();
        lista=adelantoRepositorio.findAll();
        return lista;
    }
    /**
     *  traer un adelanto
     * @param id
     * @return 
     */
    public Adelanto unAdelanto(String id){
        return adelantoRepositorio.getOne(id);
    }
}
