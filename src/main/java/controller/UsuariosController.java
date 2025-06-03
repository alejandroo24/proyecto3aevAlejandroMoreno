package controller;

import DataBase.ConnectionBD;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuariosController {

    private final String ficheroUsuarios = "Usuarios.xml";
    private static UsuariosController instancia;
    private List<Usuario> listaUsuarios = new ArrayList<>();
    UsuarioDAO usuarioDAO = new UsuarioDAO(ConnectionBD.getConnection());
    public static UsuariosController getInstancia() {
        if (instancia == null) {
            instancia = new UsuariosController();
            UsuarioDAO usuarioDAO = new UsuarioDAO(ConnectionBD.getConnection());
        }
        return instancia;
    }

    public UsuariosController(){
        this.listaUsuarios = usuarioDAO.obtenerTodos();
    }
    public UsuariosController(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Usuario> getListaUsuarios() {
        if (listaUsuarios == null || listaUsuarios.isEmpty()) {
            listaUsuarios = new ArrayList<>();
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
        boolean añadido = true;
        usuarioDAO.insertar(usuario);
        return añadido;

    }

    public boolean removeUsuario(Usuario usuario) {
        listaUsuarios.remove(usuario);
        boolean eliminado = true;
        usuarioDAO.eliminar(usuario.getId());
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
                    usuarioDAO.actualizar(u);
                }
            }
        }while (!usuarioEncontrado);
        return usuarioEncontrado;
    }

}
