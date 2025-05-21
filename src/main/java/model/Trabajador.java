package model;

import controller.AlmacenController;
import controller.DescuentoController;
import controller.PedidoController;
import controller.ProductoController;
import exceptions.EstadoPedidoInvalidException;
import exceptions.TipoUsuarioException;
import java.util.ArrayList;
import java.util.Map;

public class Trabajador extends Usuario {
    // Atributos
    private int id;
    private float salario;
    private ArrayList<Almacen> almacenesGestionados;
    private ArrayList<Descuento> descuentosCreados;
    private ArrayList<Pedido> pedidosCompletados;
    private ArrayList<Pedido> solicitudesPendientes = new ArrayList<>();



    AlmacenController almacenController = AlmacenController.getInstancia();
    ProductoController productoController = ProductoController.getInstancia();
    PedidoController pedidoController = PedidoController.getInstancia();
    DescuentoController descuentoController = DescuentoController.getInstancia();

    public Trabajador(){

    }

    public Trabajador(Usuario usuario){
        super(usuario.getNombre(), usuario.getContraseña(), usuario.getCorreo(), usuario.getUsuario(), true);
        this.solicitudesPendientes = new ArrayList<>();
        this.almacenesGestionados = new ArrayList<>();
        this.descuentosCreados = new ArrayList<>();
        this.pedidosCompletados = new ArrayList<>();
        this.salario = 0;
        this.id = usuario.getId();
    }

    public Trabajador(String nombre, String contraseña, String correo, String usuario,boolean isTrabajador) throws TipoUsuarioException {
            super(nombre, contraseña, correo, usuario, true);
            try {
                if (isTrabajador) {
                    almacenesGestionados = new ArrayList<>();
                    descuentosCreados = new ArrayList<>();
                    pedidosCompletados = new ArrayList<>();
                    this.salario = 0;
                    for (Pedido p : pedidoController.getListaPedidos()) {
                        if (p.getEstadoPedido() == EstadoPedido.PENDIENTE) {
                            this.solicitudesPendientes.add(p);

                        }
                    }
                }

            } catch (TipoUsuarioException e) {
            throw new TipoUsuarioException("El usuario no es un trabajador");
        }

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public ArrayList<Almacen> getAlmacenesGestionados() {
        return almacenesGestionados;
    }

    public void setAlmacenesGestionados(ArrayList<Almacen> almacenesGestionados) {
        this.almacenesGestionados = almacenesGestionados;
    }

    public ArrayList<Descuento> getDescuentosCreados() {
        return descuentosCreados;
    }

    public void setDescuentosCreados(ArrayList<Descuento> descuentosCreados) {
        this.descuentosCreados = descuentosCreados;
    }

    public ArrayList<Pedido> getPedidosCompletados() {
        return pedidosCompletados;
    }

    public void setPedidosCompletados(ArrayList<Pedido> pedidosCompletados) {
        this.pedidosCompletados = pedidosCompletados;
    }

    public ArrayList<Pedido> getSolicitudesPendientes() {
        return solicitudesPendientes;
    }

    public void setSolicitudesPendientes(ArrayList<Pedido> solicitudesPendientes) {
        this.solicitudesPendientes = solicitudesPendientes;
    }

    @Override
    public String toString() {
        return super.toString() +
                "salario:" + salario +
                "almacenesGestionados: " + almacenesGestionados +
                "descuentosCreados: " + descuentosCreados +
                "pedidosCompletados: " + pedidosCompletados;
    }

}
