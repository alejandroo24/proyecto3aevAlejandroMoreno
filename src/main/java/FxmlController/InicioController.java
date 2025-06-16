package FxmlController;

import DAO.ClienteDAO;
import DAO.ProductoDAO;
import controller.UsuarioActivoController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import DataBase.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import utils.Utilidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InicioController {

    UsuarioActivoController usuarioActivoController = UsuarioActivoController.getInstancia();
    ClienteDAO clienteDAO = new ClienteDAO(ConnectionBD.getConnection());
    ProductoDAO productosDAO = new ProductoDAO(ConnectionBD.getConnection());
    Cliente cliente = clienteDAO.obtenerPorId(usuarioActivoController.getUsuarioActivo().getId());


    @FXML
    private Label bienvenidaLabel;
    @FXML
    private Label puntoslbl;
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
    private Text cerrarSesionBtn;



    private List<DetallesPedido> carro = new ArrayList<>();

    @FXML
    private void inicio() {
        mostrarDatosCliente();
        tablaProductos.setItems((ObservableList) (productosDAO.obtenerTodos()));
        tablaProductos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        añadirProducto.setOnMouseClicked(mouseEvent -> añadeProducto());
        pedidosbtn.setOnMouseClicked(event -> irAPedidos(event));
        carrobtn.setOnMouseClicked(event -> irACarro(event));
        cerrarSesionBtn.setOnMouseClicked(event -> cerrarSesion(event));

    }

    private void mostrarDatosCliente() {
        bienvenidaLabel.setText("Bienvenido, " + usuarioActivoController.getUsuarioActivo().getNombre() + "!");
        if (cliente != null) {
            puntoslbl.setText("Puntos: " + cliente.getPuntos());
        }
    }


    public void añadeProducto() {
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null && productoSeleccionado.getStock() > 0) {

            DetallesPedido detallePedido = new DetallesPedido();
            detallePedido.setProducto(productoSeleccionado);
            carro.add(detallePedido);
            contadorCarro.setText(String.valueOf(carro.size()));
            productosDAO.bajarStockProducto(productoSeleccionado);

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
            stage.setScene(new Scene(root));
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
        // Lógica para ir a la pantalla de saldo
        cambiarEscena(event, "/Fxml/PedidosMenu.fxml");
    }

    @FXML
    private void irACarro(MouseEvent event) {
        // Lógica para ir a la pantalla de saldo
        cambiarEscena(event, "/Fxml/carroMenu.fxml");
    }

    private void cerrarSesion(MouseEvent event) {
        usuarioActivoController.setUsuarioActivo(null);
        carro.clear();
        cambiarEscena(event, "/Fxml/InicioSesion.fxml");
    }
}
