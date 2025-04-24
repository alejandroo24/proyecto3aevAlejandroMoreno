package model;

import java.util.Objects;

public class Usuario {
    private String nombre;
    private String contraseña;
    private String correo;
    private String usuario;

    public Usuario(String nombre, String contraseña, String correo, String usuario) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.usuario = usuario;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario1 = (Usuario) o;
        return Objects.equals(correo, usuario1.correo) && Objects.equals(usuario, usuario1.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(correo, usuario);
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
