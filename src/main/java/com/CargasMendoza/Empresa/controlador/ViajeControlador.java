
package com.CargasMendoza.Empresa.controlador;

import com.CargasMendoza.Empresa.entidades.Viaje;
import com.CargasMendoza.Empresa.enumeraciones.Zonas;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.service.ViajeServicio;
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
@RequestMapping("/viaje")
public class ViajeControlador {
    @Autowired
    ViajeServicio viajeService;
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        modelo.put("zonas", Zonas.values());
        return "viaje_formulario";
    }
    @PostMapping("/registro")
    public String registro(@RequestParam String zona,@RequestParam Double importe,
            @RequestParam Integer mes,@RequestParam Integer anio,ModelMap modelo){
        try {
            viajeService.cargar(zona, importe, mes, anio);
            modelo.put("exito", "El Viaje se cargo correctamente");
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
        }
        return "viaje_formulario";
    }
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        modelo.put("zonas", Zonas.values());
        List<Viaje> lista= viajeService.listarTodosLosViajes();
        modelo.addAttribute("viajes", lista);
        double total=0;
        for (Viaje viaje : lista) {
        total+=viaje.getImporte();
        }
        modelo.put("total", total);
        return "viaje_lista";
    }
    
    @PostMapping("/listar")
    public String listar(ModelMap modelo,@RequestParam Integer mes,@RequestParam Integer anio,@RequestParam String zona){
        List<Viaje> lista=viajeService.listaPorZona(zona, mes, anio);
        modelo.addAttribute("viajes", lista);
         double total=0;
        for (Viaje viaje : lista) {
        total+=viaje.getImporte();
        }
        modelo.put("total", total);
        return "viaje_lista";
    }
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,ModelMap modelo){
        modelo.put("viaje", viajeService.unViaje(id));
        return "viaje_modificar";
    }
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,Double importe,
            Integer mes,Integer anio,String z,ModelMap modelo){
        try {
            System.out.println(z);
            System.out.println(id);
            System.out.println(importe);
            System.out.println(mes);
            System.out.println(anio);
           
            
            viajeService.Modificar(id, z, importe, mes, anio);
            return "redirect:../lista";
           
        } catch (Excepcion ex) {
           modelo.put("error", ex.getMessage());
           return "viaje_modficar";
        }
        
    }
}
