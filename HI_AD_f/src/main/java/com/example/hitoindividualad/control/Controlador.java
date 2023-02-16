package com.example.hitoindividualad.control;
import com.example.hitoindividualad.jpa.Rol;
import com.example.hitoindividualad.jpa.Tarea;
import com.example.hitoindividualad.jpa.Usuario;
import com.example.hitoindividualad.servicios.RolService;
import com.example.hitoindividualad.servicios.TareaService;
import com.example.hitoindividualad.servicios.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
@Controller
public class Controlador {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UsuarioService usuarios;

    @Autowired
    TareaService tareas;

    @Autowired
    RolService roles;
    //atiende a la petición localhost:8089/
    @RequestMapping("/")
    public ModelAndView peticionRaiz(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        if(auth==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", auth.getName());
        mv.setViewName("index");
        String texto = "123";
        String encriptado = encoder.encode(texto);
        System.out.println("Texto original: "+texto);
        System.out.println("Texto emcriptado: "+encriptado);
        return mv;
    }
    //login para iniciar sesión
    @RequestMapping("login")
    public ModelAndView peticionSesion(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        if(auth==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", auth.getName());
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping("/denegado")
    public ModelAndView peticionDenegado(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        if(auth==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", auth.getName());
        mv.setViewName("denegado");
        return mv;
    }


    @RequestMapping("/user")
    public ModelAndView peticionUsuario(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        Usuario user=null;
        if(auth==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else {
            mv.addObject("user", auth.getName());
            Optional<Usuario> userOpcional = usuarios.buscarUsuario(auth.getName());

            if (userOpcional.isPresent())
                user = userOpcional.get();
        }

        mv.addObject("usuario", user);

        mv.setViewName("user");
        return mv;
    }

    @RequestMapping("/user/perfil")
    public ModelAndView peticionPerfil(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        if(auth==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", auth.getName());
        Optional<Usuario> uOpcional = usuarios.buscarUsuario(auth.getName());
        Usuario u = uOpcional.get();
        mv.addObject("usuario", u);
        mv.setViewName("perfil");
        return mv;
    }

    @RequestMapping("/user/tareas/nueva")
    public ModelAndView peticionNuevaTarea(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        if(auth==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", auth.getName());
        Tarea t = new Tarea();
        mv.addObject("tarea", t);
        mv.setViewName("nuevatarea");
        return mv;
    }

    @RequestMapping("/admin/usuario/nuevo")
    public ModelAndView registro() {
        ModelAndView mv = new ModelAndView();
        Usuario c = new Usuario();
        mv.addObject("usuario", c);
        mv.setViewName("nuevousuario");
        return mv;
    }

    @RequestMapping("/user/tareas/listado")
    public ModelAndView peticionListadoTareas(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        if(auth==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", auth.getName());
        List<Usuario> listaUsuarios = usuarios.listaUsuarios();
        mv.addObject("listaUsuarios", listaUsuarios);

        mv.setViewName("listadotareas");
        return mv;
    }

    

    @RequestMapping("/admin")
    public ModelAndView peticionAdministrador(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        if(auth==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", auth.getName());

        List<Usuario> listaUsuarios = usuarios.listaUsuarios();
         mv.addObject("listaUsuarios", listaUsuarios);

        mv.setViewName("admin");
        return mv;
    }

    @RequestMapping("/admin/dashboard")
    public ModelAndView peticionDashboard(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        if(auth==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", auth.getName());
        mv.setViewName("dashboard");
        return mv;
    }


    @RequestMapping("/guardar")
    public ModelAndView peticionGuardar(Usuario u, Authentication auth) {
        ModelAndView mv = new ModelAndView();
        System.out.println(u);
        String sincifrar = u.getPw();
        String cifrado = encoder.encode(sincifrar);
        u.setPw(cifrado);
        if(auth==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", auth.getName());
        Optional<Usuario> usuarioBuscado = usuarios.buscarUsuario(u.getNif());
        if (usuarioBuscado.isPresent()) {
            mv.addObject("sms", "El nif " + u.getNif() + " ya está utilizado");
        } else {
            usuarios.guardarUsuario(u);
            Rol rol = new Rol();
            rol.setUsuario(u);
            rol.setRol("USUARIO");
            roles.guardarRol(rol);
            mv.addObject("sms", "Usuario " + u.getNombre() + " registrado con éxito");
        }
        mv.setViewName("informa");
        return mv;
    }


    @RequestMapping("/admin/usuario/mostar")
    public ModelAndView peticionUsuariosMostrar(Authentication auth) {
        ModelAndView mv = new ModelAndView();
        if(auth==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", auth.getName());
        mv.setViewName("mostrarusuarios");
        return mv;
    }

    @RequestMapping("/admin/usuario/editar")
    public ModelAndView peticioUsuariosEditar(Authentication auth, HttpServletRequest request) {
        String nif = request.getParameter("nif");
        Optional<Usuario> usuarioOpt = usuarios.buscarUsuario(nif);
        Usuario user = usuarioOpt.get();
        ModelAndView mv = new ModelAndView();if(auth==null)
            mv.addObject("user", "No se ha iniciado sesión");
        else
            mv.addObject("user", auth.getName());
        mv.addObject("usuario", user);
        mv.setViewName("editarusuarios");
        return mv;
    }

    @RequestMapping("/actualizar")
    public String peticionActualizar(Usuario u, Authentication auth) {
        usuarios.guardarUsuario(u);
        return "redirect:/admin";
    }
    @RequestMapping("/actualizarTarea")
    public String peticionActualizarTarea(Tarea t, Authentication auth) {
        tareas.guardarTarea(t);
        return "redirect:/user/tareas/listado";
    }

    @RequestMapping("/eliminar/{nif}")
    public String peticionEliminar(@PathVariable("nif") String nif, Authentication auth) {
        Usuario u = usuarios.buscarUsuario(nif).get();

        if (!u.getRoles().isEmpty()){
            List<Rol> lr = u.getRoles();
            lr.forEach(rol -> roles.eliminarRolById(rol.getId()));
        }
        if (!u.getTareas().isEmpty()){
            List<Tarea> lt = u.getTareas();
            lt.forEach(tarea -> tareas.eliminarTareaById(tarea.getId()));
        }
        usuarios.eliminarUsuarioById(u.getNif());

        return "redirect:/admin";

    }
}
