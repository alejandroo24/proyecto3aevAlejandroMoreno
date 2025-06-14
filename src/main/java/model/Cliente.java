package model;


import java.util.Objects;


public class Cliente extends Usuario {
    private int puntos;


    public Cliente() {

    }

    public Cliente(String nombre, String contraseña, String correo, String usuario)  {
        super(nombre, contraseña, correo, usuario);
        this.puntos = 0;
    }

    public Cliente(Usuario usuario) {
        super(usuario.getNombre(), usuario.getContraseña(), usuario.getCorreo(), usuario.getNickname());
        this.puntos = 0;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntosAcumulados) {
        this.puntos= puntosAcumulados;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return puntos == cliente.puntos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), puntos);
    }
}

