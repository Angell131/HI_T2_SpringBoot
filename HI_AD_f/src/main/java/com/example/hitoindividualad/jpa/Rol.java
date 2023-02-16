package com.example.hitoindividualad.jpa;
import java.io.Serializable;

import com.example.hitoindividualad.jpa.Usuario;
import jakarta.persistence.*;
@Entity
@Table(name="roles")
public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    private String rol;

    //bi-directional many-to-one association to Usuario
    @ManyToOne
    @JoinColumn(name="nif")
    private Usuario usuario;
    public Rol() {}
    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}
    public String getRol() {return this.rol;}
    public void setRol(String rol) {this.rol = rol;}
    public Usuario getUsuario() {return this.usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}
}
