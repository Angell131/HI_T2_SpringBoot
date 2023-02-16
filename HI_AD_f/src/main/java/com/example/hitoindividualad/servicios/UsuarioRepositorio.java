package com.example.hitoindividualad.servicios;
import com.example.hitoindividualad.jpa.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    List<Usuario> findByNombre(String nombre);
}
