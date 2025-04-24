package model;

import java.util.ArrayList;

public class Trabajador extends Usuario {

    private int salario;
    private ArrayList<Almacen> almacenesGestionados;
    private ArrayList<Descuento> descuentosCreados;
    private ArrayList<Pedido> pedidosCompletados;

    public Trabajador(String nombre, String contraseña, String correo, String usuario) {
        super(nombre, contraseña, correo, usuario);
        almacenesGestionados = null;
        descuentosCreados = null;
        pedidosCompletados = null;
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

    @Override
    public String toString() {
        return super.toString() +
                "salario:" + salario +
                "almacenesGestionados: " + almacenesGestionados +
                "descuentosCreados: " + descuentosCreados +
                "pedidosCompletados: " + pedidosCompletados;
    }
}
