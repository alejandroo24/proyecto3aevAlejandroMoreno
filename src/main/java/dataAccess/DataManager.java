package dataAccess;

import model.Pedido;
import model.Premio;
import model.Producto;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instancia;
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Producto> productos = new ArrayList<>();

    private DataManager(){}

    public static DataManager getInstancia() {
        if (instancia == null) {
            instancia = new DataManager();
        }
        return instancia;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
