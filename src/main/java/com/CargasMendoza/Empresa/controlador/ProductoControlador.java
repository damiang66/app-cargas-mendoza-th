
package com.CargasMendoza.Empresa.controlador;

import com.CargasMendoza.Empresa.entidades.Producto;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.ProductoRepositorio;
import com.CargasMendoza.Empresa.service.ProductoService;
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
@RequestMapping("/producto")
public class ProductoControlador {
    
    @Autowired
    ProductoRepositorio productoRepositorio;
    @Autowired
    ProductoService productoService;
    @GetMapping("/registrar")
    public String registrar(){
        return "producto_formulario.html";
    }
    @PostMapping("/registro")

    public String registro(@RequestParam String nombre,ModelMap modelo){
        try {
            productoService.crearProducto(nombre);
            modelo.put("exito", "Producto cargadado con exito");
            return "inicio.html";
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            return "producto_formulario.html";
        }
        
    }
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        List<Producto> lista = productoService.listarProducto();
        modelo.addAttribute("productos",lista);
        return "producto_lista";
    }
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,ModelMap modelo){
        modelo.put("producto", productoService.mostrarProducto(id));
        return "producto_modificar";
    }
    @PostMapping("/modificar/{id}")
    public String modificadar(@PathVariable String id,String nombre,ModelMap modelo){
        try {
            productoService.ModificarProducto(id, nombre);
            // modelo.put("exito", "producto modificado correctamente");
              return "redirect:../lista";
           
          
        } catch (Excepcion ex) {
           modelo.put("error", ex.getMessage());
           return "producto_modificar";
        }
    }
}
