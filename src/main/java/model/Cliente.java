package model;

import exceptions.TipoUsuarioException;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;


public class Cliente extends Usuario{
    private Carro carro;

    private int puntosAcumulados;

    private float saldo;


    public Cliente(){

    }

    public Cliente(Usuario usuario){
        super(usuario.getNombre(), usuario.getContraseña(), usuario.getCorreo(), usuario.getUsuario(), false);
        this.carro = null;
        this.puntosAcumulados = 0;
        this.saldo = 0;
    }

    public Cliente(String nombre, String contraseña, String correo, String usuario) throws TipoUsuarioException {
        super(nombre, contraseña, correo, usuario, false);
        try {
            if (!isTrabajador()) {
                this.carro = null;
                this.puntosAcumulados = 0;
                this.saldo = 0;
            }
        } catch (TipoUsuarioException e) {
            throw new TipoUsuarioException("El usuario no es un cliente");
        }
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
