package FxmlController;

import DAO.DetallesPedidoDAO;
import DAO.PedidoDAO;
import DataBase.ConnectionBD;
import controller.UsuarioActivoController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Cliente;
import model.DetallesPedido;
import model.EstadoPedido;
import model.Pedido;

public class pedidoClienteController {

    @FXML
    private Label bienvenidaLabel;
    @FXML
    private Text inicioBtn;
    @FXML
    private TableView <Pedido> tablaPedido;
    @FXML
    private TableView <DetallesPedido> detallePedidoTable;
    @FXML
    private Rectangle cancelarPedidobtn;
    @FXML
    private Rectangle eliminarProductobtn;

    PedidoDAO pedidoDAO = new PedidoDAO(ConnectionBD.getConnection());
    UsuarioActivoController usuarioActivoController = UsuarioActivoController.getInstancia();
    DetallesPedidoDAO detallesPedidoDAO = new DetallesPedidoDAO(ConnectionBD.getConnection());

    @FXML
    private void initialize() {
        mostrarDatosCliente();
        mostrarPedidos();
        cancelarPedidobtn.setOnMouseClicked(mouseEvent -> cancelarPedido());
        eliminarProductobtn.setOnMouseClicked(mouseEvent -> eliminarProducto());
        inicioBtn.setOnMouseClicked(event -> volverInicio(event));
    }

    private void mostrarDatosCliente() {
        bienvenidaLabel.setText("Bienvenido, " + usuarioActivoController.getUsuarioActivo().getNombre());
    }

    private void mostrarPedidos() {
        ObservableList<Pedido> listaPedidos = (ObservableList<Pedido>) pedidoDAO.obtenerPedidosPorCliente((Cliente)usuarioActivoController.getUsuarioActivo());
        tablaPedido.setItems(listaPedidos);
        tablaPedido.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<DetallesPedido> listaDetalles = (ObservableList<DetallesPedido>) detallesPedidoDAO.obtenerPorPedido(tablaPedido.getSelectionModel().getSelectedItem());
    }

    private boolean cancelarPedido () {
        boolean cancelado = false;
        Pedido pedidoSeleccionado = tablaPedido.getSelectionModel().getSelectedItem();
        if (pedidoSeleccionado != null && pedidoSeleccionado.getEstadoPedido() == EstadoPedido.PENDIENTE) {
            pedidoDAO.eliminar(pedidoSeleccionado);
            detallesPedidoDAO.eliminarPorPedido(pedidoSeleccionado);
            mostrarPedidos();

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Pedido Cancelado");
            alerta.setHeaderText(null);
            alerta.setContentText("El pedido ha sido cancelado exitosamente.");
            alerta.showAndWait();
            cancelado = true;
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Selección de Pedido");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecciona un pedido para cancelar. Asegúrate de que el estado del pedido sea PENDIENTE.");
            alerta.showAndWait();
            cancelado = false;
        }
        return cancelado;
    }

    private boolean eliminarProducto() {
        boolean eliminado = false;
        DetallesPedido detalleSeleccionado = detallePedidoTable.getSelectionModel().getSelectedItem();
        if (detalleSeleccionado != null && detalleSeleccionado.getPedido().getEstadoPedido() == EstadoPedido.PENDIENTE) {
            detallesPedidoDAO.eliminar(detalleSeleccionado);
            Pedido pedido = tablaPedido.getSelectionModel().getSelectedItem();
            if (!pedido.getDetallesPedido().isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Producto Eliminado");
                alerta.setHeaderText(null);
                alerta.setContentText("El producto ha sido eliminado del pedido.");
                alerta.showAndWait();
                eliminado = true;
            }else {
                Alert alerta = new Alert (Alert.AlertType.INFORMATION);
                alerta.setTitle("Pedido Cancelado");
                alerta.setHeaderText(null);
                alerta.setContentText("El pedido ha sido cancelado porque no contiene productos. ");
                cancelarPedido();
                eliminado = true;
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Selección de Producto");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecciona un producto para eliminar. Asegúrate de que el estado del pedido sea PENDIENTE.");
            alerta.showAndWait();
            eliminado = false;
        }
        if (eliminado) {
            mostrarPedidos();
        }
        return eliminado;
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

    private void volverInicio(MouseEvent event) {
        cambiarEscena(event, "/Fxml/Inicio.fxml");
    }


}
