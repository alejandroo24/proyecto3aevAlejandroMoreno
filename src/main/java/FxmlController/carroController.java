package FxmlController;

import DAO.DetallesPedidoDAO;
import DAO.PedidoDAO;
import controller.UsuarioActivoController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Cliente;
import model.DetallesPedido;
import model.EstadoPedido;
import model.Pedido;

import java.util.List;

public class carroController {

    @FXML
    private Label bienvenidaLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Label puntosAcumuladosLabel;
    @FXML
    private TableView<DetallesPedido> tablaCarro;
    @FXML
    private CheckBox descuentoCheckBox;
    @FXML
    private Rectangle realizarPedidoBtn;
    @FXML
    private Rectangle eliminarProductoBtn;
    @FXML
    private Rectangle vaciarCarroBtn;




    UsuarioActivoController usuarioActivoController = UsuarioActivoController.getInstancia();;
    PedidoDAO pedidoDAO = new PedidoDAO(DataBase.ConnectionBD.getConnection());
    DetallesPedidoDAO detallesPedidoDAO = new DetallesPedidoDAO(DataBase.ConnectionBD.getConnection());

    Cliente clienteActivo = (Cliente) usuarioActivoController.getUsuarioActivo();
    Pedido pedidoPorRealizar = pedidoDAO.obtenerPedidoPorRealizar(clienteActivo);

    @FXML
    private void initialize() {
        mostrarDatosCliente();
        mostrarDatosCarro();
        tablaCarro.setItems((ObservableList<DetallesPedido>) pedidoPorRealizar.getDetallesPedido());
        realizarPedidoBtn.setOnMouseClicked(mouseEvent -> realizarPedido());
        eliminarProductoBtn.setOnMouseClicked(mouseEvent -> eliminarProducto());
        vaciarCarroBtn.setOnMouseClicked(mouseEvent -> vaciarCarro());
        descuentoCheckBox.setOnAction(event -> aplicarDescuento());
    }


    @FXML
    private void mostrarDatosCliente() {
        bienvenidaLabel.setText("Bienvenido, " + clienteActivo.getNombre());
        puntosAcumuladosLabel.setText("Puntos acumulados: " + clienteActivo.getPuntos());

    }



    @FXML
    private void mostrarDatosCarro() {

        totalLabel.setText("Total: " + pedidoPorRealizar.getPrecioTotal() + " €");
    }

    @FXML
    private boolean realizarPedido() {
        if (pedidoPorRealizar.getDetallesPedido().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Carro vacío");
            alerta.setHeaderText("No hay productos en el carro");
            alerta.setContentText("Agrega productos al carro antes de realizar un pedido.");
            alerta.showAndWait();
            return false;
        }else {

            pedidoPorRealizar.setEstadoPedido(EstadoPedido.PENDIENTE);
            pedidoDAO.actualizar(pedidoPorRealizar);
        }

        clienteActivo.setPuntos(clienteActivo.getPuntos() + (int) pedidoPorRealizar.getPrecioTotal() / 2);
        mostrarDatosCliente();
        vaciarCarro();
        return true;
    }

    @FXML
    private boolean eliminarProducto() {
        DetallesPedido detalleSeleccionado = tablaCarro.getSelectionModel().getSelectedItem();
        if (detalleSeleccionado != null) {
            pedidoPorRealizar.getDetallesPedido().remove(detalleSeleccionado);
            detallesPedidoDAO.eliminar(detalleSeleccionado);
            if (descuentoCheckBox.isSelected()) {
                aplicarDescuento();
            }
            pedidoDAO.actualizar(pedidoPorRealizar);
            mostrarDatosCarro();

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Producto eliminado");
            alerta.setHeaderText(null);
            alerta.setContentText("El producto ha sido eliminado del carro.");
            alerta.showAndWait();
            return true;
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Selección de producto");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecciona un producto para eliminar.");
            alerta.showAndWait();
            return false;
        }
    }

    @FXML
    private boolean vaciarCarro(){
        if (pedidoPorRealizar.getDetallesPedido().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Carro vacío");
            alerta.setHeaderText(null);
            alerta.setContentText("El carro ya está vacío.");
            alerta.showAndWait();
            return false;
        } else {
            pedidoPorRealizar.getDetallesPedido().clear();
            detallesPedidoDAO.eliminarPorPedido(pedidoPorRealizar);
            pedidoDAO.actualizar(pedidoPorRealizar);
            mostrarDatosCarro();
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Carro vaciado");
            alerta.setHeaderText(null);
            alerta.setContentText("El carro ha sido vaciado.");
            alerta.showAndWait();
            return true;
        }
    }

    private void aplicarDescuento() {
        if (descuentoCheckBox.isSelected()) {
            float descuento = pedidoPorRealizar.getPrecioTotal() * 0.1f; // 10% de descuento
            float nuevoTotal = pedidoPorRealizar.getPrecioTotal() - descuento;
            pedidoPorRealizar.setPrecioTotal(nuevoTotal);
            totalLabel.setText("Total con descuento: " + nuevoTotal + " €");
        } else {
            totalLabel.setText("Total: " + pedidoPorRealizar.getPrecioTotal() + " €");
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
}
