
package com.CargasMendoza.Empresa.controlador;

import com.CargasMendoza.Empresa.entidades.Chofer;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.service.ChoferService;
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
@RequestMapping("/chofer")

public class ChoferControlador {
    @Autowired
    ChoferService choferServicio;
    @GetMapping("/registrar")
    public String cargar(){
        return "chofer_form.html";
    }
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido,ModelMap modelo){
        try {
            choferServicio.crearChofer(nombre, apellido);
            modelo.put("exito", "el chofer se cargo correctamente");
            return "inicio.html";
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            return "chofer_form.html";
        }
        
    }
   @GetMapping("/lista")
   public String listar(ModelMap modelo){
       List<Chofer> lista= choferServicio.listarChofer();
       modelo.addAttribute("choferes", lista);
       modelo.getAttribute("id");
       return "chofer_list.html";
       
   }
   @GetMapping("/modificar/{id}")
public String modificar(@PathVariable String id, ModelMap modelo ){
    modelo.put("chofer", choferServicio.mostarUnChofer(id));
    return "chofer_modificar.html";
}
@PostMapping("/modificar/{id}")
public String modificar(@PathVariable String id,String nombre,String apellido,ModelMap modelo){
        try {
            
          choferServicio.modificarChofer(id, nombre,apellido);
            return "redirect:../lista";
        } catch (Excepcion ex) {
           // Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
           modelo.put("error", ex.getMessage());
           return "chofer_modificar.html";
        }
}
}
