package model;

import java.util.ArrayList;
import java.util.HashSet;

public class Cliente extends Usuario{
private Carro carro;
private int puntosAcumulados;
private float saldo;
private int ContadorInicioSesion;

    public Cliente(String nombre, String contraseña, String correo, String usuario) {
        super(nombre, contraseña, correo, usuario);
        this.carro = new Carro();
        this.puntosAcumulados = 0;
        this.ContadorInicioSesion = 0;
        this.saldo = 0;
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

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return super.toString() +
                "carro:" + carro +
                "puntosAcumulados:" + puntosAcumulados +
                "saldo:" + saldo ;
    }

}
