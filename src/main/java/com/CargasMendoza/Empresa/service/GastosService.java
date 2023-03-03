package com.CargasMendoza.Empresa.service;

import com.CargasMendoza.Empresa.entidades.Gastos;

import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.GastosRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GastosService {

    @Autowired
    GastosRepositorio gastosRepositorio;

    /**
     *
     * @param importe
     * @param mes
     * @param anio
     */
    @Transactional
    public void crearGasto(Double importe, Integer mes, Integer anio, String detalle) throws Excepcion {
        validar(importe, mes, anio, detalle);
        Gastos gasto = new Gastos();
        gasto.setNombre(detalle);
        gasto.setImporte(importe);
        gasto.setMes(mes);
        gasto.setAnio(anio);
        gastosRepositorio.save(gasto);

    }

    /**
     * gastos del mes
     *
     * @param mes
     * @param anio
     * @return
     */
    public List<Gastos> gastosMes(Integer mes, Integer anio) {
        List<Gastos> lista = new ArrayList();
        lista = gastosRepositorio.buscarGastos(mes, anio);
        return lista;
    }

    /**
     * todos los gastos
     *
     * @return
     */
    public List<Gastos> gastosTotos() {
        List<Gastos> lista = new ArrayList();
        lista = gastosRepositorio.findAll();
        return lista;
    }

    /**
     *
     * @param id
     * @param importe
     * @param mes
     * @param anio
     * @param detalle
     * @throws Excepcion
     */
    @Transactional
    public void modificar(String id, Double importe, Integer mes, Integer anio, String detalle) throws Excepcion {
        validar(importe, mes, anio, detalle);
        Optional<Gastos> respuesta = gastosRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Gastos gasto = respuesta.get();
           gasto.setNombre(detalle);
            gasto.setImporte(importe);
            gasto.setMes(mes);
            gasto.setAnio(anio);
            gastosRepositorio.save(gasto);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public Gastos unGasto(String id) {
        return gastosRepositorio.getOne(id);
    }

    /**
     *
     * @param importe
     * @param mes
     * @param anio
     * @param detalle
     * @throws Excepcion
     */
    private void validar(Double importe, Integer mes, Integer anio, String detalle) throws Excepcion {
        if (importe == null) {
            throw new Excepcion("El importe no puede estar vacio");
        }
        if (mes == null) {
            throw new Excepcion("El mes no puede estar vacio");
        }
        if (anio == null) {
            throw new Excepcion("El año no puede estar vacio");
        }
        if (detalle == null || detalle.isEmpty()) {
            throw new Excepcion("El detalle del Gasto no puede estar vacio");
        }
    }
}
