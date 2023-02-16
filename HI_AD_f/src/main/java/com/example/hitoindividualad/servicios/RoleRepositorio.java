package com.example.hitoindividualad.servicios;

import com.example.hitoindividualad.jpa.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoleRepositorio extends JpaRepository<Rol, Integer> {
}
