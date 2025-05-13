package model;

import javax.xml.bind.annotation.*;
import java.util.Objects;

public class Usuario {
    private int id;
    private String nombre;
    private String contraseña;
    private String correo;
    private String usuario;
    private boolean esTrabajador;

    public Usuario() {
    }
    public Usuario(String nombre, String contraseña, String correo, String usuario, boolean esTrabajador) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.usuario = usuario;
        this.esTrabajador = esTrabajador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isTrabajador() {
        return esTrabajador;
    }

    public void setEsTrabajador(boolean esTrabajador) {
        this.esTrabajador = esTrabajador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario1 = (Usuario) o;
        return esTrabajador == usuario1.esTrabajador && Objects.equals(id, usuario1.id) && Objects.equals(nombre, usuario1.nombre) && Objects.equals(contraseña, usuario1.contraseña) && Objects.equals(correo, usuario1.correo) && Objects.equals(usuario, usuario1.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, contraseña, correo, usuario, esTrabajador);
    }

    @Override
    public String toString() {
        return "Usuario:\'" +
                "nombre:" + nombre + '\'' +
                "contraseña:" + contraseña + '\'' +
                "correo:" + correo + '\'' +
                "usuario:" + usuario + '\'';
    }
}
