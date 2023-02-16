package com.example.hitoindividualad.servicios;
import com.example.hitoindividualad.jpa.Tarea;
import com.example.hitoindividualad.jpa.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
@Service
@ApplicationScope
public class TareaService {
    TareaRepositorio tareas;
    public TareaService(TareaRepositorio tareas) {
        this.tareas = tareas;
    }
    public List<Tarea> listaTareas() {
        return tareas.findAll();
    }
    public Integer tareasFinalizadas() {
        return tareas.finalizadas();
    }
    public Integer tareasPendientes() {
        return tareas.pendientes();
    }
    public Integer tareasEnProceso() {
        return tareas.enproceso();
    }
    public Integer cuentaTareas(Integer estado) {
        return tareas.findByEstado(estado);
    }
    public void guardarTarea(Tarea tarea) {
        tareas.save(tarea);
    }
    public List<Tarea> borrarTarea(Usuario t) {
        tareas.deleteByUsuario(t.setTareas(borrarTarea(t)));
        return null;
    }
    public void eliminarTareasUsuario(Usuario u) {
        String nif = u.getNif();
        tareas.deleteByUsuario(u);
    }
    public void eliminarTareaById(int id) {
        tareas.deleteById(id);
    }
}
