package com.CargasMendoza.Empresa.controlador;

import com.CargasMendoza.Empresa.entidades.Gastos;
import com.CargasMendoza.Empresa.enumeraciones.GastosEnum;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.service.GastosService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/gasto")
public class GastoControlador {

    @Autowired
    GastosService gastoServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        modelo.put("enums", GastosEnum.values());
        return "gastos_formulario";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String gasto, @RequestParam double importe,
            @RequestParam Integer mes, @RequestParam Integer anio, ModelMap modelo) {
        
      
        try {
            gastoServicio.crearGasto(importe, mes, anio, gasto);
            modelo.put("exito","gasto cargado correctamente");
        } catch (Excepcion ex) {
           modelo.put("error",ex.getMessage());
        }
        return "gastos_formulario";
    }
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Gastos> lista=gastoServicio.gastosTotos();
        modelo.addAttribute("gastos", lista);
        double total=0;
        for (Gastos gastos : lista) {
            total+=gastos.getImporte();
        }
        modelo.put("total", total);
        return "gastos_lista";
    }
    @PostMapping("/listar")
    public String listar(@RequestParam Integer mes,@RequestParam Integer anio,ModelMap modelo){
        List<Gastos> lista = gastoServicio.gastosMes(mes, anio);
        modelo.addAttribute("gastos", lista);
        double total=0;
        for (Gastos gastos : lista) {
            total+=gastos.getImporte();
        }
        modelo.put("total", total);
         return "gastos_lista";
    }
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,ModelMap modelo){
        modelo.put("gastos", gastoServicio.unGasto(id));
        return "gastos_modificar";
    }
    @PostMapping("modificar/{id}")
    public String modificar(@PathVariable String id,Integer mes,Integer anio,Double importe,String nombre,ModelMap modelo){
        try {
            gastoServicio.modificar(id, importe, mes, anio, nombre);
            return "redirect:../lista";
            
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            return "gastos_modificar";
        }
    }

}
