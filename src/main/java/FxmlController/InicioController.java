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
    private Text pedidosbtn;
    @FXML
    private ImageView carrobtn;
    @FXML
    private Rectangle añadirProducto;
    @FXML
    private TableView <Producto> tablaProductos;

    @FXML
    private void inicio() {
    mostrarDatosCliente();
    tablaProductos.setItems((ObservableList)(productosDAO.obtenerTodos()));

    }

    private void mostrarDatosCliente() {
        bienvenidaLabel.setText("Bienvenido, " + usuarioActivoController.getUsuarioActivo().getNombre() + "!");
        if (cliente != null) {
            puntoslbl.setText("Puntos: " + cliente.getPuntos());
        }
    }

    // Método para añadir un producto al carrito HAY QUE TERMINARLO
    private void añadirProducto(MouseEvent event) {
        añadirProducto.setOnMouseClicked(mouseEvent -> {;
            Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        });
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
}
