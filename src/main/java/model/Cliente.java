package model;

import exceptions.TipoUsuarioException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;

@XmlRootElement(name = "cliente")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cliente extends Usuario{
    @XmlElement
    private Carro carro;
    @XmlElement
    private int puntosAcumulados;
    @XmlElement
    private float saldo;
    @XmlElement
    private int ContadorInicioSesion;

    public Cliente(){

    }
    public Cliente(String nombre, String contraseña, String correo, String usuario) throws TipoUsuarioException {
        super(nombre, contraseña, correo, usuario, false);
        try {
            if (!isTrabajador()) {
                this.carro = null;
                this.puntosAcumulados = 0;
                this.ContadorInicioSesion = 0;
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
