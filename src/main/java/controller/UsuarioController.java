package controller;

import dataAccess.XMLManager;
import model.Usuario;
import dataAccess.XMLManager;

import java.util.HashSet;
import java.util.List;

public class UsuarioController {

    private final String ficheroUsuarios = "Usuarios.xml";
    private List<Usuario> listaUsuarios;

    public UsuarioController(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public void addUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
        XMLManager.writeXML(listaUsuarios, ficheroUsuarios);
    }

    public void removeUsuario(Usuario usuario) {
        listaUsuarios.remove(usuario);
        XMLManager.writeXML(listaUsuarios, ficheroUsuarios);
    }

    public void updateUsuario(Usuario usuario) {
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
        XMLManager.writeXML(listaUsuarios, ficheroUsuarios);
    }


}
