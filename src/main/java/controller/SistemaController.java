package controller;

import exceptions.DatoIncorrectoException;
import exceptions.UsuarioNoEncontradoException;
import model.Usuario;
import utils.Utilidades;

import java.util.List;


//HAY QUE MODIFICAR MAS ADELANTE LA CLASE ENTERA PARA VINCULAR LOS MÉTODOS CON EL FUNCIONAMIENTO DE JAVAFX
public class SistemaController {
    UsuarioActivoController usuarioActivo = UsuarioActivoController.getInstancia();
    UsuariosController usuariosController = UsuariosController.getInstancia();


    public boolean registrarUsuario() throws DatoIncorrectoException {
        Boolean registrado = false;
        try {
            String nombre = "nombre";
            String contraseña = utils.Utilidades.hashContraseña("Contraseña");
            String correo = "correo";
            String usuario = "usuario";
            //INDICAR CON UN CHECKBOX SI ES TRABAJADOR O NO
            boolean esTrabajador = false;

            Usuario nuevoUsuario = new Usuario(nombre, contraseña, correo, usuario, false);
            registrado = usuariosController.addUsuario(nuevoUsuario);
        } catch (DatoIncorrectoException e) {
            Utilidades.muestraMensaje(e.getMessage());
        }
    return registrado;
    }

    public boolean iniciarSesion(){
        boolean sesionIniciada = false;
        try {
            String usuario = "usuario";
            String contraseña = "contraseña";
            for (Usuario u : usuariosController.getListaUsuarios()){
                if (u.getUsuario().equals(usuario) && u.getContraseña().equals(Utilidades.hashContraseña(contraseña))){
                    usuarioActivo.setUsuarioActivo(u);
                    sesionIniciada = true;
                }
            }
            return sesionIniciada;
        }catch (UsuarioNoEncontradoException e){
            Utilidades.muestraMensaje(e.getMessage());
        }
     return sesionIniciada;
    }

    public boolean cerrarSesion(){
        boolean cerrada = false;
        usuarioActivo.cerrarSesion();
        if (usuarioActivo.getUsuarioActivo() == null){
            cerrada = true;
        }
        return cerrada;
    }
}
