package model;

import java.util.ArrayList;
import java.util.HashSet;

public class Cliente extends Usuario{
private Carro carro;
private int puntosAcumulados;
private int ContadorInicioSesion;

    public Cliente(String nombre, String contraseña, String correo, String usuario) {
        super(nombre, contraseña, correo, usuario);
        this.carro = new Carro();
        this.puntosAcumulados = 0;
        this.ContadorInicioSesion = 0;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(int puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    @Override
    public String toString() {
        return super.toString() +
                "carro:" + carro +
                "puntosAcumulados:" + puntosAcumulados;
    }
}
