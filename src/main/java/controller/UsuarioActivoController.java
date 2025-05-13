package controller;

import model.Cliente;
import model.Trabajador;
import model.Usuario;

public class UsuarioActivoController {

    private final static Usuario invitado = new Usuario("invitado","invitado","invitado@gmail.com","invitado",false);
    private static UsuarioActivoController instancia;
    private Usuario usuarioActivo;

    private UsuarioActivoController(){}

    public static UsuarioActivoController getInstancia(){
        if(instancia == null){
            instancia = new UsuarioActivoController();
        }
        return instancia;
    }

    public void setUsuarioActivo(Usuario usuario){
        this.usuarioActivo = usuario;
    }

    public Usuario getUsuarioActivo(){
        return usuarioActivo;
    }

    public void usarInvitado(){
        usuarioActivo = invitado;
    }

    public boolean esTrabajadorActivo() {
        return usuarioActivo != null && usuarioActivo.isTrabajador();
    }

    public void cerrarSesion(){
        usuarioActivo = null;
    }
}