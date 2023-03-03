
package com.CargasMendoza.Empresa.controlador;

import com.CargasMendoza.Empresa.enumeraciones.Zonas;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.service.PrecioServicio;
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
@RequestMapping("/precio")
public class PrecioControlador {
    @Autowired
    PrecioServicio precioService;
    @GetMapping("/registrar")
public String registrar(ModelMap modelo){
    modelo.addAttribute("zonas", Zonas.values());
    return "precio_formulario";
}   
@PostMapping("/registro")
public String registro(ModelMap modelo,@RequestParam String zona,@RequestParam double precio){
        try {
            precioService.crear(zona, precio);
            modelo.put("exito", "el precio fue cargado correctamente");
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
        }
        modelo.addAttribute("zonas", Zonas.values());
        return "precio_formulario";
}
@GetMapping("/lista")
public String lista(ModelMap modelo){
    modelo.addAttribute("precios", precioService.listar());
    return "precio_lista";
}
@GetMapping("/modificar/{id}")
public String modificar(@PathVariable String id,ModelMap modelo){
    modelo.put("precios", precioService.mostarUnPrecio(id));
    return "precio_modificar";
}
@PostMapping("modificar/{id}")
public String modificar(@PathVariable String id,String zona,Double importe,ModelMap modelo){
        try {
            System.out.println(zona);
            System.out.println(id);
            System.out.println(importe);
            precioService.modificar(id, zona, importe);
            modelo.put("exito","precio modificado");
            return "redirect:../lista";
        } catch (Excepcion ex) {
          modelo.put("error", ex.getMessage());
           return "precio_modificar";
        }
       
}
}
