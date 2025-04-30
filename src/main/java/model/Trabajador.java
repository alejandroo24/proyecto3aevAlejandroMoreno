package model;

import controller.AlmacenController;
import controller.DescuentoController;
import controller.PedidoController;
import controller.ProductoController;
import exceptions.EstadoPedidoInvalidException;
import exceptions.TipoUsuarioException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Map;

@XmlRootElement(name = "trabajador")
@XmlAccessorType(XmlAccessType.FIELD)
public class Trabajador extends Usuario {
    @XmlElement
    private int salario;
    @XmlElement
    private ArrayList<Almacen> almacenesGestionados;
    @XmlElement
    private ArrayList<Descuento> descuentosCreados;
    @XmlElement
    private ArrayList<Pedido> pedidosCompletados;
    @XmlElement
    private ArrayList<Pedido> solicitudesPendientes = new ArrayList<>();

    AlmacenController almacenController = AlmacenController.getInstancia();
    ProductoController productoController = ProductoController.getInstancia();
    PedidoController pedidoController = PedidoController.getInstancia();
    DescuentoController descuentoController = DescuentoController.getInstancia();
    public Trabajador(){

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

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
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
