package controller;

import dataAccess.XMLManager;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuariosController {

    private final String ficheroUsuarios = "Usuarios.xml";
    private static UsuariosController instancia;
    private List<Usuario> listaUsuarios = new ArrayList<>();

    public static UsuariosController getInstancia() {
        if (instancia == null) {
            instancia = new UsuariosController();
        }
        return instancia;
    }

    public UsuariosController(){
        cargarUsuarios();
        if(listaUsuarios == null){
            listaUsuarios = new ArrayList<>();
        }
    }
    public UsuariosController(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Usuario> getListaUsuarios() {
        cargarUsuarios();
        if (listaUsuarios == null || listaUsuarios.isEmpty()) {
            listaUsuarios = new ArrayList<>();
            guardarUsuarios();
        }
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public boolean addUsuario(Usuario usuario) {
        if (usuario == null) {
            return false;
        }
        listaUsuarios.add(usuario);
        guardarUsuarios();
        boolean añadido = true;
        return añadido;
    }

    public boolean removeUsuario(Usuario usuario) {
        listaUsuarios.remove(usuario);
        guardarUsuarios();
        boolean eliminado = true;
        return eliminado;
    }

    public boolean updateUsuario(Usuario usuario) {
        boolean usuarioEncontrado = false;
        do {
            for (Usuario u : listaUsuarios) {
                if (u.equals(usuario)) {
                    usuarioEncontrado = true;
                    u.setNombre(usuario.getNombre());
                    u.setUsuario(usuario.getUsuario());
                    u.setContraseña(usuario.getContraseña());
                    u.setCorreo(usuario.getCorreo());
                }
            }
        }while (!usuarioEncontrado);
        guardarUsuarios();
        return usuarioEncontrado;
    }


    public void cargarUsuarios() {
        List<Usuario> usuarioLeidos = XMLManager.readXML(new ArrayList<Usuario>(), ficheroUsuarios);
        if (usuarioLeidos != null) {
            listaUsuarios = usuarioLeidos;
        }else{
            listaUsuarios = new ArrayList<>();
            guardarUsuarios();
        }
    }

    public void guardarUsuarios() {
        XMLManager.writeXML(listaUsuarios, ficheroUsuarios);
    }
}
