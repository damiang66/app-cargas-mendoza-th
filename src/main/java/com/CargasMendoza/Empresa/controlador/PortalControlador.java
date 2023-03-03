
package com.CargasMendoza.Empresa.controlador;


import com.CargasMendoza.Empresa.entidades.Usuario;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.service.GastosService;


import com.CargasMendoza.Empresa.service.UsuarioService;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
     @Autowired
     GastosService prueba;
     
    /*
    
    */
      @Autowired
        UsuarioService usuarioService;
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    @GetMapping("/login")
     public String login(@RequestParam(required=false)String error,ModelMap modelo){
      if(error!=null){
          modelo.put("error","usuario o contraseña invalido");
      }  
        return "login.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session){
        Usuario logueado=(Usuario) session.getAttribute("usuariosession");
        System.out.println(logueado.getRol().toString());
        if (logueado.getRol().toString().equals("ADMIN")){
            return "redirect:/admin/dasboard";
            
        }
        return "inicio.html";
    }
 
    @GetMapping("/registrar")
    public String registrar(){
        return "registro.html";
    }
    @PostMapping("registro")
    public String registro(@RequestParam String nombre,@RequestParam String email,@RequestParam String password,@RequestParam String password2,ModelMap modelo,MultipartFile archivo ){
          try {
              usuarioService.regitrar(archivo, nombre, email, password, password2);
          modelo.put("exito", "EL usuario se cargo correctamente");
          return "index.html";
          } catch (Excepcion ex) {
             modelo.put("error", ex.getMessage());
               modelo.put("nombre", nombre);
           modelo.put("email", email);
             return "registro.html";
          }
    
        
    }
   // @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo,HttpSession session){
        Usuario usuario =(Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);
        return "usuario_modificar.html";
    }
   // @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/usuario/{id}")
    public String actualizar(MultipartFile archivo, @PathVariable String id,@PathVariable String nombre,@PathVariable String email,@PathVariable String password,
    @PathVariable String password2,ModelMap modelo){
        try {
            usuarioService.modificar(archivo, id, nombre, email, password, password2);
            modelo.put("exito", "usuario actualizado correctamente");
            return "inicio.html";
        } catch (Excepcion ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email",email);
            return "usuario_modificar.html";
        }
        
    }
      
    }

