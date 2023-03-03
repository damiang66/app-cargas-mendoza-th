
package com.CargasMendoza.Empresa.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/prueba")
public class PortalPrueba {
    @GetMapping("/a")
    public String prueba(){
        System.out.println("hola");
       return "index.html";
        
    }
}
