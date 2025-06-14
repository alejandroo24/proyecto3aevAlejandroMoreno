package controller;

import model.Cliente;
import model.Trabajador;
import model.Usuario;

public class UsuarioActivoController {

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



    public boolean esTrabajadorActivo() {
        return usuarioActivo != null && usuarioActivo.getClass() == Trabajador.class;
    }

    public void cerrarSesion(){
        usuarioActivo = null;
    }
}