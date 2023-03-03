package com.CargasMendoza.Empresa.controlador;

import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.service.FacturacionService;
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
@RequestMapping("/factura")
public class FacturaControlador {

    @Autowired
    private FacturacionService facturaService;

    @GetMapping("/registrar")
    public String registrar() {
        return "facturacion_formulario";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam Integer mes, @RequestParam Integer anio, ModelMap modelo) {
        try {
            facturaService.crear(mes, anio);
            modelo.put("exito", "Facturacion creada correctamente");
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            return "facturacion_formulario";
        }
        return "facturacion_formulario";
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        modelo.addAttribute("facturas", facturaService.listar());
        return "facturacion_lista";
    }

    @PostMapping("/listar")
    public String listar(@RequestParam Integer mes, @RequestParam Integer anio, ModelMap modelo) {
        modelo.addAttribute("facturas", facturaService.buscarPorMes(mes, anio));
        return "facturacion_lista";
    }

    @GetMapping("/reporte/{id}")
    public String reporte(@PathVariable String id, ModelMap modelo) {
        modelo.put("factura", facturaService.unaFactura(id));
        return "facturacion_reporte";
    }

    @GetMapping("/modificar/{id}")
    public String modifcar(@PathVariable String id, ModelMap modelo) {
        modelo.put("factura", facturaService.unaFactura(id));
        return "facturacion_modificar";
    }
    @PostMapping("modificar/{id}")
    public String modificado(@PathVariable String id,ModelMap modelo,Integer mes, Integer anio){
        try {
            facturaService.modificar(id, mes, anio);
            modelo.put("exito","Usuario actualizado");
            return "redirect:../lista";
        } catch (Excepcion ex) {
            modelo.put("error",ex.getMessage());
            return "facturacion_modificar";
        }
    }

}
