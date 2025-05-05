package controller;

import model.Cliente;
import model.Trabajador;
import model.Usuario;

public class UsuarioActivoController {

    private static UsuarioActivoController instancia;
    private Usuario usuarioActivo;
    private static Trabajador trabajadorInstancia;
    private static Cliente clienteInstancia;

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
        return clienteInstancia;
    }

    public static void setTrabajadorInstancia(Trabajador trabajadorInstancia) {
        UsuarioActivoController.trabajadorInstancia = trabajadorInstancia;
    }

    public void setUsuarioActivo(Usuario usuario){
        this.usuarioActivo = usuario;
        if (usuario.isTrabajador()){
            trabajadorInstancia = (Trabajador) usuario;
            clienteInstancia = null;
        } else {
            clienteInstancia = (Cliente) usuario;
            trabajadorInstancia = null;
        }
    }

    public Usuario getUsuarioActivo(){
        return usuarioActivo;
    }


    public void cerrarSesion(){
        usuarioActivo = null;
        trabajadorInstancia = null;
        clienteInstancia = null;
    }
}
