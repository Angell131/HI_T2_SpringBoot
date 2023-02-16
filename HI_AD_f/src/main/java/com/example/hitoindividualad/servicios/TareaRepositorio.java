package com.example.hitoindividualad.servicios;
import com.example.hitoindividualad.jpa.Tarea;

import com.example.hitoindividualad.jpa.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface TareaRepositorio extends JpaRepository<Tarea, Integer> {
    @Query("select count(t) as pendientes from Tarea t where t.estado =1")
    public Integer pendientes();
    @Query("select count(t) as enproceso from Tarea t where t.estado =2")
    public Integer enproceso();
    @Query("select count(t) as finalizadas from Tarea t where t.estado =3")
    public Integer finalizadas();
    @Query("select count(t) as cuenta from Tarea t where t.estado =?1")
    public Integer findByEstado(Integer estado);
    @Query("delete from Tarea t where t.usuario =?1")
    void deleteByUsuario(Usuario user);
}
