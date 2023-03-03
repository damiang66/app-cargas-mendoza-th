package com.CargasMendoza.Empresa.controlador;

import com.CargasMendoza.Empresa.entidades.Liquidacion;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.service.ChoferService;
import com.CargasMendoza.Empresa.service.LiquidacionService;
import com.CargasMendoza.Empresa.service.PrecioServicio;
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.util.List;
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
@RequestMapping("/liquidacion")
public class LiquidacionControlador {

    @Autowired
    private LiquidacionService liquidacionServicio;
    @Autowired
    private ChoferService choferServicio;
    @Autowired
    private PrecioServicio precioServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        modelo.addAttribute("choferes", choferServicio.listarChofer());
        modelo.addAttribute("precios", precioServicio.listar());
        return "liquidacion_formulario";
    }

    @PostMapping("/registro")
    public String registro(ModelMap modelo, @RequestParam Integer mes, @RequestParam Integer anio,
            @RequestParam String idChofer, @RequestParam String idPrecio) {
        try {
            liquidacionServicio.cargar(idChofer, mes, anio, idPrecio);
            modelo.put("exito", "Liquidacion cargada Correctamente");
            return "inicio";
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            return "liquidacion_formulario";
        }

    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        modelo.addAttribute("liquidaciones", liquidacionServicio.todas());
        modelo.addAttribute("choferes", choferServicio.listarChofer());
        List<Liquidacion> lista = liquidacionServicio.todas();
        double total = 0;
        for (Liquidacion aux : lista) {
            total += aux.getTotal();
        }
        modelo.put("total", total);
        return "liquidacion_lista";
    }

    @PostMapping("/listar")
    public String listar(ModelMap modelo, @RequestParam Integer mes, @RequestParam Integer anio,
            @RequestParam String idChofer) {
        modelo.addAttribute("liquidaciones", liquidacionServicio.buscarPorMesYChofer(mes, anio, idChofer));
        modelo.addAttribute("choferes", choferServicio.listarChofer());
        List<Liquidacion> lista = liquidacionServicio.todas();
        double total = 0;
        for (Liquidacion aux : lista) {
            total += aux.getTotal();
        }
        modelo.put("total", total);

        return "liquidacion_lista";
    }

    @GetMapping("/reporte/{id}")
    public String reporte(@PathVariable String id, ModelMap modelo) {
        modelo.put("liquidacion", liquidacionServicio.buscarUno(id));

        return "liquidacion_reporte";

    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("liquidacion", liquidacionServicio.buscarUno(id));
        modelo.addAttribute("precios", precioServicio.listar());

        return "liquidacion_modificar";

    }

    @PostMapping("/modificar/{id}")
    public String modificado(@PathVariable String id, Integer mes, Integer anio,
            String idPrecio, ModelMap modelo, String idChofer) {
        modelo.addAttribute("precios", precioServicio.listar());
        try {
            liquidacionServicio.modificar(id, idChofer, mes, anio, idPrecio);
            return "redirect:../lista";
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            return "liquidacion_modficar";
        }
    }
}
