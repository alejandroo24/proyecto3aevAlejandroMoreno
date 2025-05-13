package manager;

import controller.*;
import model.Usuario;
import utils.Utilidades;

public class AppManager {
        private static manager.AppManager instancia;

        private UsuariosController usuariosController;
        private ClienteController clienteController;
        private TrabajadorController trabajadorController;
        private ProductoController productoController;
        private AlmacenController almacenController;
        private PedidoController pedidoController;
        private DescuentoController descuentoController;

        private AppManager() {
            inicializarControladores();
            cargarDatos();
        }

        public static manager.AppManager getInstancia() {
            if (instancia == null) {
                instancia = new manager.AppManager();
            }
            return instancia;
        }

        private void inicializarControladores() {
            usuariosController = UsuariosController.getInstancia();
            clienteController = ClienteController.getInstancia();
            trabajadorController = new TrabajadorController();
            productoController = ProductoController.getInstancia();
            almacenController = AlmacenController.getInstancia();
            pedidoController = PedidoController.getInstancia();
            descuentoController = DescuentoController.getInstancia();
        }

        private void cargarDatos() {

        }

        public void start() {
            Utilidades.muestraMensaje("Bienvenido a la aplicación.");
            // Aquí puedes implementar la lógica para mostrar el menú principal o iniciar la vista.
        }

        public UsuariosController getUsuariosController() {
            return usuariosController;
        }

        public ClienteController getClienteController() {
            return clienteController;
        }

        public TrabajadorController getTrabajadorController() {
            return trabajadorController;
        }

        public ProductoController getProductoController() {
            return productoController;
        }

        public AlmacenController getAlmacenController() {
            return almacenController;
        }

        public PedidoController getPedidoController() {
            return pedidoController;
        }

        public DescuentoController getDescuentoController() {
            return descuentoController;
        }
}

