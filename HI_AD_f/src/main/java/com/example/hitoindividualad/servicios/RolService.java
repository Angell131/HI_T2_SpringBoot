package com.example.hitoindividualad.servicios;

import com.example.hitoindividualad.jpa.Rol;

import com.example.hitoindividualad.jpa.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
@Service
@ApplicationScope
public class RolService {
    RoleRepositorio roles;
    public RolService(RoleRepositorio roles) {
        this.roles = roles;
    }
    public void guardarRol(Rol rol) {
        roles.save(rol);
    }
    public void eliminarRolById(int id) {
        roles.deleteById(id);
    }
}
