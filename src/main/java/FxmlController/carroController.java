package FxmlController;

import DAO.DetallesPedidoDAO;
import DAO.PedidoDAO;
import DAO.ProductoDAO;
import controller.UsuarioActivoController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

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
    private TableColumn<DetallesPedido, Integer> nombreProductoColumna;
    @FXML
    private TableColumn<DetallesPedido, Integer> cantidadColumna;
    @FXML
    private TableColumn<DetallesPedido, Float> precioTotalColumna;
    @FXML
    private CheckBox descuentoCheckBox;
    @FXML
    private Rectangle realizarPedidoBtn;
    @FXML
    private Rectangle eliminarProductoBtn;
    @FXML
    private Rectangle vaciarCarroBtn;

    @FXML
    private Text inicioBtn;




    UsuarioActivoController usuarioActivoController = UsuarioActivoController.getInstancia();;
    PedidoDAO pedidoDAO = new PedidoDAO(DataBase.ConnectionBD.getConnection());
    ProductoDAO productoDAO = new ProductoDAO(DataBase.ConnectionBD.getConnection());
    DetallesPedidoDAO detallesPedidoDAO = new DetallesPedidoDAO(DataBase.ConnectionBD.getConnection());

    Cliente clienteActivo = (Cliente) usuarioActivoController.getUsuarioActivo();
    Pedido pedidoPorRealizar = pedidoDAO.obtenerUltimoPedidoPorCliente(clienteActivo.getId());


    ObservableList listaDetalles = FXCollections.observableArrayList(detallesPedidoDAO.obtenerPorPedido(pedidoPorRealizar));
    ObservableList<Producto> listaProductos = FXCollections.observableArrayList(pedidoDAO.obtenerProductosPorPedido(pedidoPorRealizar));

    @FXML
    private void initialize() {
        pedidoPorRealizar.setDetallesPedido(detallesPedidoDAO.obtenerPorPedido(pedidoPorRealizar));
        mostrarDatosCliente();
        mostrarDatosCarro();

        realizarPedidoBtn.setOnMouseClicked(mouseEvent -> realizarPedido(pedidoPorRealizar));
        eliminarProductoBtn.setOnMouseClicked(mouseEvent -> eliminarProducto());
        vaciarCarroBtn.setOnMouseClicked(mouseEvent -> vaciarCarro());
        descuentoCheckBox.setOnAction(event -> aplicarDescuento());
        inicioBtn.setOnMouseClicked(event -> irAInicio(event));
    }


    @FXML
    private void mostrarDatosCliente() {
        bienvenidaLabel.setText("Bienvenido, " + clienteActivo.getNombre());
        puntosAcumuladosLabel.setText(clienteActivo.getPuntos() + " puntos acumulados");

    }



    @FXML
    private void mostrarDatosCarro() {
         nombreProductoColumna.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
            cantidadColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            precioTotalColumna.setCellValueFactory(cellData -> {;
                DetallesPedido detalle = cellData.getValue();
                float total = detalle.getCantidad() * detalle.getPrecio();
                return new ReadOnlyObjectWrapper<>(detalle.getCantidad() * detalle.getPrecio());
            });
            tablaCarro.setItems(listaDetalles);
            totalLabel.setText("Total: " + detallesPedidoDAO.obtenerTotalPorPedido(pedidoPorRealizar) + " €");

    }



    @FXML
    private boolean realizarPedido(Pedido pedido) {
        if (pedidoPorRealizar.getDetallesPedido().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Carro vacío");
            alerta.setHeaderText("No hay productos en el carro");
            alerta.setContentText("Agrega productos al carro antes de realizar un pedido.");
            alerta.showAndWait();
            return false;
        }else {
            pedidoPorRealizar.setDetallesPedido(detallesPedidoDAO.obtenerPorPedido(pedidoPorRealizar));
            pedidoPorRealizar.setEstadoPedido(EstadoPedido.PENDIENTE);
            pedidoPorRealizar.setCliente(clienteActivo);
            pedidoPorRealizar.setPrecioTotal(detallesPedidoDAO.obtenerTotalPorPedido(pedidoPorRealizar));
            pedidoDAO.actualizar(pedidoPorRealizar);
        }

        clienteActivo.setPuntos(clienteActivo.getPuntos() + (int) pedidoPorRealizar.getPrecioTotal() / 2);
        mostrarDatosCliente();
        mostrarDatosCarro();
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
            listaProductos.setAll(productoDAO.obtenerTodos());
            tablaCarro.setItems(listaDetalles);
            mostrarDatosCarro();

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Carro vaciado");
            alerta.setHeaderText(null);
            alerta.setContentText("El carro ha sido vaciado.");
            alerta.showAndWait();
            return true;
        }
    }

    private void irAInicio(MouseEvent event) {
        cambiarEscena(event, "/Fxml/inicio.fxml");
    }

    private void aplicarDescuento() {
        if (descuentoCheckBox.isSelected() && pedidoPorRealizar.getPrecioTotal() > 0 && clienteActivo.getPuntos() >= 100) {
            int bloques = clienteActivo.getPuntos() / 100;
            float descuentoTotal = 1.0f - (0.1f * bloques);
            if (descuentoTotal < 0) descuentoTotal = 0; // No permitir descuento mayor al 100%
            float nuevoTotal = pedidoPorRealizar.getPrecioTotal() * descuentoTotal;
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
