
package com.CargasMendoza.Empresa.service;

import com.CargasMendoza.Empresa.entidades.Imagen;
import com.CargasMendoza.Empresa.entidades.Usuario;
import com.CargasMendoza.Empresa.enumeraciones.Rol;
import com.CargasMendoza.Empresa.excepciones.Excepcion;
import com.CargasMendoza.Empresa.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
@Service
public class UsuarioService implements UserDetailsService{
  @Autowired
   private UsuarioRepositorio usuarioRepositorio;
   @Autowired
   private ImagenService imagenServicio;
   @Transactional
   public void regitrar(MultipartFile archivo, String nombre,String email, String password,String password2) throws Excepcion{
       validar(nombre, email, password, password2);
       Usuario usuario = new Usuario();
       usuario.setNombre(nombre);
       usuario.setEmail(email);
       usuario.setPassword(new BCryptPasswordEncoder().encode(password));
       usuario.setRol(Rol.USER);
       Imagen imagen = imagenServicio.guardar(archivo);
       usuario.setImagen(imagen);
       usuarioRepositorio.save(usuario);
   }
   public void modificar(MultipartFile archivo ,String idUsuario,String nombre,String email,String password,String password2 ) throws Excepcion{
       validar(nombre, email, password, password2);
       Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
       if(respuesta.isPresent()){
           Usuario usuario= respuesta.get();
           usuario.setNombre(nombre);
       usuario.setEmail(email);
       usuario.setPassword(new BCryptPasswordEncoder().encode(password));
       usuario.setRol(Rol.USER);
       String idImagen = null;
       if(usuario.getImagen()!=null){
           idImagen=usuario.getImagen().getId();
       }
       Imagen imagen = imagenServicio.actualizar(archivo,idImagen);
       
       usuario.setImagen(imagen);
       
       usuarioRepositorio.save(usuario);
           
       }
   }
   private void validar(String nombre,String email, String password,String password2) throws Excepcion{
       if (nombre.isEmpty()|| nombre==null){
           throw new Excepcion("el nombre del usuario no puede estar vacio");
       }
       if (email.isEmpty()|| email==null){
           throw new Excepcion("el mail del usuario no puede estar vacio");
       }
       if (password.isEmpty()|| password==null||password.length()<=5){
           throw new Excepcion("la contraseña no puede estar vacia y debe tener mas de 5 digitos");
       }
       if(!password2.equals(password)){
           
            throw new Excepcion("la contraseñas ingresadas deben ser iguales");
       }
   }
    public Usuario BuscarUnUsuario(String id){
        return usuarioRepositorio.getOne(id);
    }

  @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario=usuarioRepositorio.buscarPorEmail(email);
        if(usuario!= null){
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
                     
            return new User(usuario.getEmail(), usuario.getPassword(),permisos );
        }else{
            return null;
        }
        
    }

    public List<Usuario> listarUsuarios() {
      List<Usuario> usuarios = usuarioRepositorio.findAll();
      return usuarios;
    }

    public void cambiarRol(String id) {
       Optional<Usuario> respuesta= usuarioRepositorio.findById(id);
       if (respuesta.isPresent()){
           Usuario usuario = respuesta.get();
           System.out.println(usuario.getRol());
       if (usuario.getRol().equals(Rol.USER)){
           System.out.println("entre como user");
       usuario.setRol(Rol.ADMIN);
       }else if(usuario.getRol().equals(Rol.ADMIN)){
         usuario.setRol(Rol.USER);
       }
       usuarioRepositorio.save(usuario);
       }
    }   
}
