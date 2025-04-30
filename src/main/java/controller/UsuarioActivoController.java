package controller;

import model.Cliente;
import model.Trabajador;
import model.Usuario;

public class UsuarioActivoController {

    private static UsuarioActivoController instancia;
    private Usuario usuarioActivo;
    private static Trabajador trabajadorInstancia;
    private static Cliente ClienteInstancia;

    private UsuarioActivoController(){

    }

    public static UsuarioActivoController getInstancia(){
        if(instancia == null){
            instancia = new UsuarioActivoController();
        }
        return instancia;
    }

    public static Trabajador getTrabajadorInstancia() {
        return trabajadorInstancia;
    }

    public static Cliente getClienteInstancia() {
        return ClienteInstancia;
    }

    public Trabajador getTrabajadorActivo(){
        if(usuarioActivo instanceof Trabajador){
            trabajadorInstancia = (Trabajador) usuarioActivo;
            return trabajadorInstancia;
        }
        return null;
    }

    public Cliente getClienteActivo(){
        if(usuarioActivo instanceof Cliente){
            ClienteInstancia = (Cliente) usuarioActivo;
            return ClienteInstancia;
        }
        return null;
    }

    public void setUsuarioActivo(Usuario usuario){
        this.usuarioActivo = usuario;
    }

    public Usuario getUsuarioActivo(){
        return usuarioActivo;
    }

    public void cerrarSesion(){
        usuarioActivo = null;
        trabajadorInstancia = null;
        ClienteInstancia = null;
    }
}
