package com.CargasMendoza.Empresa.controlador;

import com.CargasMendoza.Empresa.entidades.IngresoCaja;
import com.CargasMendoza.Empresa.entidades.Producto;
import com.CargasMendoza.Empresa.enumeraciones.Zonas;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.IngresoCajaRepositorio;
import com.CargasMendoza.Empresa.service.IngresoCajaService;
import com.CargasMendoza.Empresa.service.ProductoService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping("/ingreso")

public class IngresoCajaControlador {

    @Autowired
    IngresoCajaService ingresoServicio;
    @Autowired
    ProductoService productoServicio;

    @GetMapping("/registrar")
    public String ingresar(ModelMap modelo) {
        List<Producto> listaProducto = productoServicio.listarProducto();
        modelo.addAttribute("productos", listaProducto);
        modelo.addAttribute("zonas", Zonas.values());

        return "ingresar_formulario";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam Integer cantidad, @RequestParam Integer mes,
            @RequestParam Integer anio, @RequestParam String idProducto,
            @RequestParam String zona, ModelMap modelo) {
        System.out.println(zona);
        try {

            ingresoServicio.ingresarCajas(cantidad, mes, anio, zona, idProducto);
            return "inicio";
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            return "ingresar_formulario";

        }

    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Producto> listaProducto = productoServicio.listarProducto();
        List<IngresoCaja> lista = ingresoServicio.buscarTodas();
        modelo.put("zonas", Zonas.values());
        modelo.addAttribute("productos", listaProducto);
        modelo.addAttribute("ingresos", lista);
        return "ingresar_lista";
    }

    /**
     *
     * @param zona
     * @param idProducto
     * @param mes
     * @param anio
     * @param modelo
     * @return
     */
    @PostMapping("/listar")
    public String listar(@RequestParam String zona,
            @RequestParam String idProducto, @RequestParam Integer mes, @RequestParam Integer anio, ModelMap modelo) {
        List<IngresoCaja> lista = ingresoServicio.buscarPorMesZonaYProducto(zona, idProducto, mes, anio);
        int cantidad = 0;
        for (IngresoCaja aux : lista) {
            cantidad += aux.getCantidad();
        }
        List<Producto> listaProducto = productoServicio.listarProducto();
        modelo.put("total", cantidad);
        modelo.put("zonas", Zonas.values());
        modelo.addAttribute("productos", listaProducto);
        modelo.addAttribute("ingresos", lista);
        return "ingresar_lista";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("ingreso", ingresoServicio.buscarUnIngreso(id));
        return "ingreso_modificar";
    }

    @PostMapping("/modificar/{id}")
    public String modficar(@PathVariable String id, Integer cantidad, Integer mes, Integer anio, String zona, String idProducto, ModelMap modelo) {
        try {

            ingresoServicio.modificar(id, cantidad, mes, anio, zona, idProducto);
            return "redirect:../lista";

        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            return "ingreso_modificar";
        }
    }

}
