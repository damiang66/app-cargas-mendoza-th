package com.CargasMendoza.Empresa.controlador;

import com.CargasMendoza.Empresa.entidades.Adelanto;
import com.CargasMendoza.Empresa.entidades.Chofer;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.service.AdelantoService;
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
@RequestMapping("/adelanto")
public class AdelantoControlador {

    @Autowired
    ChoferService choferService;
    @Autowired
    AdelantoService adelantoService;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Chofer> listaChoferes = choferService.listarChofer();
        modelo.addAttribute("choferes", listaChoferes);
        return "adelanto_formulario.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam Integer mes,
             @RequestParam Integer anio, @RequestParam Double importe,
            @RequestParam String idAdelanto, @RequestParam String descripcion, ModelMap modelo) {
        try {
            adelantoService.ingresarAdelanto(idAdelanto, descripcion, mes, anio, importe);
            modelo.put("exito", "adelanto cargado correctamente");
            return "adelanto_formulario";
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            return "adelanto_formulario";

        }

    }
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Adelanto> lista = adelantoService.listarTodos();
        List<Chofer> listaChofer=choferService.listarChofer();
        modelo.addAttribute("choferes", listaChofer);
        modelo.addAttribute("adelantos",lista);
        return "adelanto_lista";
    }

    @PostMapping("/listar")
     public String listaChoferes(@RequestParam String idChofer, @RequestParam Integer mes,
             @RequestParam Integer anio, 
             ModelMap modelo){
        List<Adelanto> lista = adelantoService.listarPorMesYChofer(idChofer, anio, mes);
        List<Chofer> listaChofer=choferService.listarChofer();
        double total=0;
        for (int i = 0; i < lista.size(); i++) {
            total+=lista.get(i).getImporte();
        }
        modelo.addAttribute("total", total);
        modelo.addAttribute("choferes", listaChofer);
        modelo.addAttribute("adelantos",lista);
        return "adelanto_lista";
    }
     @GetMapping("/modificar/{id}")
     public String modificar(@PathVariable String id,ModelMap modelo){
         List<Chofer> listaChoferes = choferService.listarChofer();
        modelo.addAttribute("choferes", listaChoferes);
       modelo.addAttribute("adelantos", adelantoService.unAdelanto(id));
         return "adelanto_modificar.html";
     }
     @PostMapping("/modificar/{id}")
     public String modificar(@PathVariable String id,String idChofer,String descripcion,Integer mes,Integer anio,Double importe,ModelMap modelo){
        try {
          List<Chofer> listaChoferes = choferService.listarChofer();
         System.out.println(id+"/****"+idChofer+"-"+mes+"-"+anio+"-"+importe);
           modelo.addAttribute("choferes", listaChoferes);
            adelantoService.modificar(id, idChofer, descripcion, mes, anio, importe);
           // modelo.put("exito", "adelanto Actualizado");
              return "redirect:../lista";
        } catch (Excepcion ex) {
          modelo.put("error", ex.getMessage());
            return "adelanto_modificar.html";
        }
         
     }
}
