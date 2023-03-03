
package com.CargasMendoza.Empresa.controlador;

import com.CargasMendoza.Empresa.entidades.Chofer;
import com.CargasMendoza.Empresa.entidades.Reparto;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.service.ChoferService;
import com.CargasMendoza.Empresa.service.PrecioServicio;
import com.CargasMendoza.Empresa.service.ProductoService;
import com.CargasMendoza.Empresa.service.RepartoService;
import java.util.Date;
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
@RequestMapping("/reparto")
public class RepartoControlador {
    @Autowired
    RepartoService repartoServicio;
    @Autowired
    PrecioServicio precioServicio;
    @Autowired
    ChoferService choferServicio;
    @Autowired
    ProductoService productoServicio;
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        modelo.addAttribute("precios", precioServicio.listar());
        modelo.addAttribute("choferes", choferServicio.listarChofer());
        modelo.addAttribute("productos", productoServicio.listarProducto());
        return "reparto_formulario";
    }
    @PostMapping("/registro")
    public String registro(@RequestParam Integer cantidad,@RequestParam Integer mes,@RequestParam Integer anio,
            @RequestParam String idChofer,@RequestParam String idProducto,@RequestParam Double idPrecio,
            ModelMap modelo){
        Date fecha=new Date();
        try {
            repartoServicio.crearReparto(cantidad, fecha, idPrecio, mes, anio, idChofer, idProducto);
            modelo.put("exito", "Reparto cargado correctamente");
        } catch (Excepcion ex) {
           modelo.put("error", ex.getMessage());
        }
        return "reparto_formulario";
    }
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Reparto> listaReparto=repartoServicio.listar();
        List<Chofer> listaChoferes=choferServicio.listarChofer();
       int cantidad=0;
        for (Reparto reparto : listaReparto) {
            cantidad+=reparto.getCantidad();
        }
        modelo.put("total", cantidad);
        modelo.addAttribute("repartos", listaReparto);
       
        modelo.addAttribute("choferes",listaChoferes);
        
        return "reparto_lista";
    }
    @PostMapping("/listar")
    public String listar(ModelMap modelo,@RequestParam Integer mes,@RequestParam Integer anio,
            @RequestParam String idChofer){
        List<Reparto> lista= repartoServicio.listarPorMes(mes, anio, idChofer);
        int cantidad=0;
        for (Reparto reparto : lista) {
            cantidad+=reparto.getCantidad();
        }
        modelo.addAttribute("choferes", choferServicio.listarChofer());
        modelo.addAttribute("repartos", repartoServicio.listarPorMes(mes, anio, idChofer));
        modelo.put("total", cantidad);
        return "reparto_lista";
    }
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,ModelMap modelo){
        modelo.put("reparto", repartoServicio.buscarUnReparto(id));
        return "reparto_modificar";
        
    }
    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id,String idChofer,String idProducto,Integer cantidad,Integer mes,Integer anio,Double precio){
        Reparto reparto = repartoServicio.buscarUnReparto(id);
        try {
            repartoServicio.modificarReparto(cantidad, reparto.getFecha(), precio, mes, anio, idChofer, idProducto, id);
            return "redirect:../lista";
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
           return "reparto_modificar";
        }
    }
}
