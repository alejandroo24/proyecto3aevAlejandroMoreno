package controller;

import model.*;
import utils.Utilidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ClienteController {
    private static ClienteController instancia;
    private Cliente clienteActivo;
    private PremiosController premiosController = PremiosController.getInstance();
    private PedidoController pedidoController = PedidoController.getInstancia();
    private ProductoController productoController = ProductoController.getInstancia();
    private TrabajadorController trabajadorController = TrabajadorController.getInstancia();
    private final Random random = new Random();
    private ClienteController() {
        // Constructor privado para evitar instanciación externa
    }

    public static ClienteController getInstancia() {
        if (instancia == null) {
            instancia = new ClienteController();
        }
        return instancia;
    }

    public Cliente getClienteActivo() {
        return clienteActivo;
    }

    public void setClienteActivo(Cliente clienteActivo) {
        this.clienteActivo = clienteActivo;
    }

    public boolean canjearPremio(Cliente cliente, Premio premio) {
        if (cliente != null && cliente.getPuntosAcumulados() >= premio.getPuntosNecesarios()) { // Suponiendo que se necesitan 100 puntos para canjear un premio
            cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() - premio.getPuntosNecesarios());
            aplicarRecompensa(premio);
            return true;
        }
        return false;
    }

    public boolean aplicarRecompensa(Premio premio) {
        if (clienteActivo == null || premio == null) {
            return false;
        }

        switch (premio.getDescripcion()) {
            case "Descuento del 50% en la próxima compra":
                clienteActivo.getCarro().SetPrecioTotal(Utilidades.hacerPorcentaje(20,clienteActivo.getCarro().getPrecioTotal()));
                break;

            case "Descuento del 20% en la próxima compra":
                // Aplica un descuento al cliente
                clienteActivo.getCarro().SetPrecioTotal(Utilidades.hacerPorcentaje(20,clienteActivo.getCarro().getPrecioTotal()));
                break;

            case "Envío gratuito":
                // Marca el envío como gratuito
                for (Pedido pedido : pedidoController.getListaPedidos()) {
                    if (pedido.getCliente().equals(clienteActivo)) {
                        pedido.restarPrecioEnvío();
                        break;
                    }
                }
                break;

            case "Producto sorpresa gratis":
                // Agrega un producto gratis al carro
                Producto productoGratis = productoController.getListaProductos().get(random.nextInt(productoController.getListaProductos().size()));
                clienteActivo.getCarro().agregarProducto(productoGratis, 1);
                break;

            case "Duplicar puntos acumulados":
                // Duplica los puntos acumulados
                clienteActivo.setPuntosAcumulados(clienteActivo.getPuntosAcumulados() * 2);
                break;


            case "Tarjeta de regalo valorada en 10€":
                premiosController.generarCodigo(10);
                break;
            case "Tarjeta de regalo valorada en 20€":
                premiosController.generarCodigo(20);
                break;

            case "Tarjeta de regalo valorada en 50€":
                premiosController.generarCodigo(50);
                break;
        }

        return true;
    }

    public void addProductoCarro(Producto producto, int cantidad) {
        if (clienteActivo != null && producto != null) {
            clienteActivo.getCarro().agregarProducto(producto, cantidad);
        } else {
            Utilidades.muestraMensaje("No se puede añadir el producto al carro");
        }
    }

    public boolean removeProductoCarro(Producto producto) {
        if (clienteActivo != null && producto != null) {
            clienteActivo.getCarro().eliminarProducto(producto);
            return true;
        } else {
            Utilidades.muestraMensaje("No se puede eliminar el producto del carro");
        }
        return false;
    }

    public boolean vaciarCarro(){
        if (clienteActivo != null) {
            clienteActivo.getCarro().vaciarCarro();
            return true;
        } else {
            Utilidades.muestraMensaje("No se puede vaciar el carro");
        }
        return false;
    }

    public boolean crearSolicitudPedido(){
        boolean agregado = false;
        if (clienteActivo.getCarro() !=null && !clienteActivo.getCarro().getProductosCarro().isEmpty()){

            List<DetallesPedido>productosDetallePedido = new ArrayList<>();
            Pedido nuevoPedido = new Pedido();

            for (Producto producto : clienteActivo.getCarro().getProductosCarro().keySet()) {
                if (productoController.getListaProductos().contains(producto)) {
                    DetallesPedido detallesPedido = new DetallesPedido(producto, clienteActivo.getCarro().getProductosCarro().get(producto));
                    productosDetallePedido.add(detallesPedido);
                }
                nuevoPedido = new Pedido(clienteActivo,EstadoPedido.PENDIENTE,productosDetallePedido);
            }

            agregado = pedidoController.agregarPedido(nuevoPedido);
        }
        return agregado;
    }

    public boolean cancelarPedido(Pedido pedido) {
        if (pedido != null && pedido.getEstadoPedido() == EstadoPedido.PENDIENTE) {
            pedido.cancelarPedido();
            return true;
        } else {
            Utilidades.muestraMensaje("El pedido no se puede cancelar porque ya ha sido entregado o está en proceso de entrega.");
        }
        return false;
    }

    public boolean modificarDetallesPedido(Pedido pedido, DetallesPedido nuevosDetalles) {
        if (pedido != null && pedido.getEstadoPedido() == EstadoPedido.PENDIENTE) {
            return pedido.modificarPedido(nuevosDetalles);
        } else {
            Utilidades.muestraMensaje("El pedido no se puede modificar porque ya ha sido entregado o está en proceso de entrega.");
        }
        return false;
    }

    public boolean añadirSaldo (int cantidad){
        if (clienteActivo != null && cantidad > 0) {
            clienteActivo.setSaldo(clienteActivo.getSaldo() + cantidad);
            return true;
        } else {
            Utilidades.muestraMensaje("No se puede añadir saldo");
        }
        return false;
    }

    public boolean canjearCodigo(String codigo) {
        if(premiosController.getCodigosValidos().containsKey(codigo) && !premiosController.getCodigosUsados().contains(codigo)) {

            int valor = premiosController.getCodigosValidos().get(codigo);
            premiosController.canjearCodigo(codigo);
            añadirSaldo(valor);
            return true;
        } else if (premiosController.getCodigosUsados().contains(codigo)) {
            Utilidades.muestraMensaje("El código ya ha sido canjeado");
        } else {
            Utilidades.muestraMensaje("El código no es válido");
        }
        return false;
    }

    public void verCarro() {
        if (clienteActivo != null) {
            clienteActivo.getCarro().toString();
        } else {
            Utilidades.muestraMensaje("No hay carro disponible");
        }
    }

    public void consultarPuntos() {
        if (clienteActivo != null) {
            Utilidades.muestraMensaje("Puntos acumulados: " + clienteActivo.getPuntosAcumulados());
        } else {
            Utilidades.muestraMensaje("No hay cliente activo");
        }
    }




}
