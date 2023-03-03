
package com.CargasMendoza.Empresa.controlador;

import com.CargasMendoza.Empresa.entidades.Usuario;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.UsuarioRepositorio;
import com.CargasMendoza.Empresa.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    @Autowired
    UsuarioService usuarioServicio;
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @GetMapping("/dasboard")
    public String panelAdministrativo(){
        return "inicio.html";
    }
    /*
    @GetMapping("/autor.html")
    public String autor(){
        return "autor.html";
    }
    */
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios",usuarios);
        return "usuario_list.html";
    }
    @GetMapping("modificarRol/{id}")
    public String cambiarRol(@PathVariable String id){
        usuarioServicio.cambiarRol(id);
        return "redirect:/admin/lista";
        
    }
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,ModelMap modelo){
        modelo.put("usuario",usuarioServicio.BuscarUnUsuario(id));
        return "usuario_modificar";
    }
     @PostMapping("/modificar/{id}")
    public String actualizar(MultipartFile archivo, @PathVariable String id,@PathVariable String nombre,@PathVariable String email,@PathVariable String password,
    @PathVariable String password2,ModelMap modelo){
        try {
            usuarioServicio.modificar(archivo, id, nombre, email, password, password2);
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
