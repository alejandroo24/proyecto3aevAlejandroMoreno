package FxmlController;

import DAO.ClienteDAO;
import DAO.DetallesPedidoDAO;
import DAO.PedidoDAO;
import DAO.ProductoDAO;
import controller.UsuarioActivoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import DataBase.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class inicioController {

    UsuarioActivoController usuarioActivoController = UsuarioActivoController.getInstancia();
    ClienteDAO clienteDAO = new ClienteDAO(ConnectionBD.getConnection());
    ProductoDAO productosDAO = new ProductoDAO(ConnectionBD.getConnection());
    Cliente cliente = clienteDAO.obtenerPorId(usuarioActivoController.getUsuarioActivo().getId());


    @FXML
    private Label bienvenidaLabel;
    @FXML
    private Label contadorCarro;
    @FXML
    private Text pedidosbtn;
    @FXML
    private ImageView carrobtn;
    @FXML
    private Rectangle añadirProducto;
    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, String> descripcionColumna;
    @FXML
    private TableColumn<Producto, String> categoriaColumna;
    @FXML
    private TableColumn<Producto, Integer> stockColumna;
    @FXML
    private TableColumn<Producto, Float> precioColumna;


    @FXML
    private Text cerrarSesionBtn;


    PedidoDAO pedidoDAO = new PedidoDAO(ConnectionBD.getConnection());
    DetallesPedidoDAO detallesPedidoDAO = new DetallesPedidoDAO(ConnectionBD.getConnection());
    ObservableList listaProductos = FXCollections.observableArrayList(productosDAO.obtenerTodos());

    private List<DetallesPedido> carro = new ArrayList<>();

    @FXML
    private void initialize() {
        mostrarDatosCliente();
        mostrarProductos();
        tablaProductos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        añadirProducto.setOnMouseClicked(mouseEvent -> añadeProducto());
        pedidosbtn.setOnMouseClicked(event -> irAPedidos(event));
        carrobtn.setOnMouseClicked(event -> irACarro(event));
        cerrarSesionBtn.setOnMouseClicked(event -> cerrarSesion(event));

    }

    private void mostrarDatosCliente() {
        bienvenidaLabel.setText("Bienvenido, " + usuarioActivoController.getUsuarioActivo().getNombre() + "!");
    }

    private void mostrarProductos() {
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        categoriaColumna.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        stockColumna.setCellValueFactory(new PropertyValueFactory<>("stock"));
        precioColumna.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tablaProductos.setItems(listaProductos);
    }


    public void añadeProducto() {
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null && productoSeleccionado.getStock() > 0) {
            DetallesPedido existente = null;
            for (DetallesPedido detalle : carro) {
                if (detalle.getProducto().getId() == productoSeleccionado.getId()) {
                    existente = detalle;
                    break;
                }
            }
            if (existente != null) {
                // Si el producto ya está en el carro, solo incrementamos la cantidad
                existente.setCantidad(existente.getCantidad() + 1);
            } else {
                // Si no está, creamos un nuevo detalle de pedido
                DetallesPedido detallePedido = new DetallesPedido();
                detallePedido.setProducto(productoSeleccionado);
                detallePedido.setCantidad(1);
                detallePedido.setPrecio(productoSeleccionado.getPrecio());
                carro.add(detallePedido);
            }
            contadorCarro.setText(String.valueOf(carro.size()));
            productosDAO.bajarStockProducto(productoSeleccionado);
            // Actualizar la tabla de productos
            listaProductos.setAll(productosDAO.obtenerTodos());
            tablaProductos.setItems(listaProductos);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Producto añadido");
            alerta.setHeaderText(null);
            alerta.setContentText("Has añadido " + productoSeleccionado.getDescripcion() + " al carrito.");
            alerta.showAndWait();
            // Actualizar el stock del producto

        } else if (productoSeleccionado.getStock() == 0) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Producto no disponible");
            alerta.setHeaderText(null);
            alerta.setContentText("El producto seleccionado no está disponible en stock.");
            alerta.showAndWait();
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Selección de producto");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecciona un producto de la lista.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void cambiarEscena(MouseEvent event, String rutaFXML) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1280, 720));
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error al cambiar de escena");
            alerta.setHeaderText("No se pudo cargar la nueva escena");
            alerta.setContentText("Verifica que el archivo FXML exista y esté configurado correctamente.");
            alerta.showAndWait();
        }


    }

    @FXML
    private void irAPedidos(MouseEvent event) {

        cambiarEscena(event, "/Fxml/PedidosMenu.fxml");
    }

    @FXML
    private void irACarro(MouseEvent event) {
        Pedido pedido = new Pedido(cliente, new ArrayList<>(carro));
        pedido.setFechaCreacion(LocalDate.now());
        pedido.setEstadoPedido(EstadoPedido.POR_REALIZAR);
        pedidoDAO.insertar(pedido);
        for (DetallesPedido detalle : carro) {
            detalle.setPedido(pedido);
            detallesPedidoDAO.insertar(detalle);
        }
        cambiarEscena(event, "/Fxml/carroMenu.fxml");
        carro.clear();
    }

    private void cerrarSesion(MouseEvent event) {
        usuarioActivoController.setUsuarioActivo(null);
        carro.clear();
        cambiarEscena(event, "/Fxml/InicioSesion.fxml");
    }

    public List<DetallesPedido> getCarro() {
        return carro;
    }

    public void setCarro(List<DetallesPedido> carro) {
        this.carro = carro;
    }
}
