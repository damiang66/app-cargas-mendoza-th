package com.CargasMendoza.Empresa.service;

import com.CargasMendoza.Empresa.entidades.Chofer;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.ChoferRepositorio;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChoferService {

    @Autowired
    private ChoferRepositorio choferRepositorio;

    /**
     * este metodo se utiliza para crear un chofer
     *
     * @param nombre
     * @param apellido
     * @throws Excepcion
     */
    public void crearChofer(String nombre, String apellido) throws Excepcion {
        validar(nombre, apellido);
        Chofer chofer = new Chofer();
        chofer.setNombre(nombre);
        chofer.setApellido(apellido);
        choferRepositorio.save(chofer);

    }

    /**
     * este metodo lo utilizo para modificar un chofer
     *
     * @param id
     * @param nombre
     * @param apellido
     * @throws Excepcion
     */
    public void modificarChofer(String id, String nombre, String apellido) throws Excepcion {
        validar(nombre, apellido);
        Optional<Chofer> respuesta = choferRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Chofer chofer = respuesta.get();
            chofer.setNombre(nombre);
            chofer.setApellido(apellido);
            choferRepositorio.save(chofer);
        }
    }

    /**
     * este metodo lo utilzo para retornar una lista de choferes
     *
     * @return
     */
    public List<Chofer> listarChofer() {
        List<Chofer> choferes = new ArrayList();
        choferes = choferRepositorio.findAll();
        return choferes;
    }

    /**
     * Metodo para traer un solo chofer
     *
     * @param id
     * @return
     */
    public Chofer mostarUnChofer(String id) {
        return choferRepositorio.getOne(id);
    }

    /**
     * metodo para validar los datos
     *
     * @param nombre
     * @param apellido
     * @throws Excepcion
     */
    private void validar(String nombre, String apellido) throws Excepcion {
        if (nombre == null || nombre.isEmpty()) {
            throw new Excepcion("El nombre no puede estar vacio");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new Excepcion("El apellido no puede estar vacio");
        }
    }

}
