package FxmlController;

import DAO.AlmacenDAO;
import DAO.PedidoDAO;
import DAO.ProductoDAO;
import controller.UsuarioActivoController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

public class inicioTrabajador {

    @FXML
    private Label bienvenidaLabel;
    @FXML
    private Label salarioLabel;
    @FXML
    private Label almacenLabel;

    @FXML
    private TableView<Pedido> tablaPedidos;
    @FXML
    private TableView<Producto> tablaAlmacen;

    @FXML
    private Rectangle confirmarPedidoBtn;
    @FXML
    private Rectangle cancelarPedidoBtn;
    @FXML
    private Rectangle añadirProductoBtn;
    @FXML
    private Rectangle actualizarProductoBtn;
    @FXML
    private Rectangle eliminarProductoBtn;

    @FXML
    private Text cerrarSesionBtn;

    @FXML
    private TextField descripcionField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField precioField;

    @FXML
    private MenuButton categoriaMenu;
    @FXML
    private MenuItem camiseta;
    @FXML
    private MenuItem sudadera;
    @FXML
    private MenuItem gorra;
    @FXML
    private MenuItem calzado;



    UsuarioActivoController usuarioActivoController = UsuarioActivoController.getInstancia();
    Trabajador trabajadorActivo = (Trabajador) usuarioActivoController.getUsuarioActivo();
    PedidoDAO pedidoDAO = new PedidoDAO(DataBase.ConnectionBD.getConnection());
    AlmacenDAO almacenDAO = new AlmacenDAO(DataBase.ConnectionBD.getConnection());
    ProductoDAO productoDAO = new ProductoDAO(DataBase.ConnectionBD.getConnection());

    @FXML
    private void initialize() {
        mostrarDatosTrabajador();
        mostrarPedidos();
        mostrarAlmacen();

        confirmarPedidoBtn.setOnMouseClicked(mouseEvent -> confirmarPedido());
        cancelarPedidoBtn.setOnMouseClicked(mouseEvent -> cancelarPedido());
        añadirProductoBtn.setOnMouseClicked(mouseEvent -> añadirProducto());
        actualizarProductoBtn.setOnMouseClicked(mouseEvent -> actualizarProducto());
        eliminarProductoBtn.setOnMouseClicked(mouseEvent -> eliminarProducto());
        cerrarSesionBtn.setOnMouseClicked(event -> cerrarSesion(event));
    }

    private void mostrarDatosTrabajador() {
        bienvenidaLabel.setText("Bienvenido, " + trabajadorActivo.getNombre());
        salarioLabel.setText("Salario: " + trabajadorActivo.getSalario() + " €");
        almacenLabel.setText("Almacén: " + trabajadorActivo.getAlmacen().getNombre());
    }

    private void mostrarPedidos() {
       tablaPedidos.setItems((ObservableList<Pedido>) pedidoDAO.obtenerPedidosPorEstado(EstadoPedido.PENDIENTE));
    }

    private void mostrarAlmacen() {
        tablaAlmacen.setItems((ObservableList<Producto>) productoDAO.obtenerProductosAlmacen(trabajadorActivo.getAlmacen()));
    }

    private boolean confirmarPedido() {
        Pedido pedidoSeleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (pedidoSeleccionado != null && pedidoSeleccionado.getEstadoPedido() == EstadoPedido.PENDIENTE) {
            pedidoDAO.confirmarPedido(pedidoSeleccionado);
            mostrarPedidos();
            return true;
        }
        return false;
    }

    private boolean cancelarPedido() {
        Pedido pedidoSeleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (pedidoSeleccionado != null && pedidoSeleccionado.getEstadoPedido() == EstadoPedido.PENDIENTE) {
            pedidoDAO.cancelarPedido(pedidoSeleccionado);
            mostrarPedidos();
            return true;
        }
        return false;
    }

    private boolean añadirProducto() {
        Producto nuevoProducto = creaProducto();
        if (nuevoProducto == null) {
            return false; // Retornar false si no se crea el producto
        }else{
            productoDAO.insertar(nuevoProducto);
            return true;
        }
    }

    private boolean actualizarProducto(){
        Producto productoSeleccionado = tablaAlmacen.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            Producto productoActualizado = datosActualizados();
            if (productoActualizado == null) {
                return false;
            } else {
                productoActualizado.setId(productoSeleccionado.getId());
                productoDAO.actualizar(productoActualizado);
                mostrarAlmacen();
                return true;
            }
        }
        return false;
    }

    private boolean eliminarProducto(){
        Producto productoSeleccionado = tablaAlmacen.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            productoDAO.eliminar(productoSeleccionado);
            mostrarAlmacen();
            return true;
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Selección de Producto");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecciona un producto para eliminar.");
            alerta.showAndWait();
            return false;
        }
    }


    private void cerrarSesion(MouseEvent event) {
        usuarioActivoController.setUsuarioActivo(null);
        cambiarEscena(event, "/Fxml/InicioSesion.fxml");
    }

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

    private Producto creaProducto(){
        Producto producto = new Producto();
        if (descripcionField.getText().isEmpty() || stockField.getText().isEmpty() || precioField.getText().isEmpty() || categoriaMenu.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos Vacíos");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, completa todos los campos.");
            alerta.showAndWait();
            return null;
        }
        producto.setDescripcion(descripcionField.getText());
        producto.setStock(Integer.parseInt(stockField.getText()));
        producto.setPrecio(Float.parseFloat(precioField.getText()));
        producto.setAlmacen(trabajadorActivo.getAlmacen());

        if (categoriaMenu.getText().equals("Camiseta")) {
            producto.setCategoria(Categoria.CAMISETA);
        } else if (categoriaMenu.getText().equals("Sudadera")) {
            producto.setCategoria(Categoria.SUDADERA);
        } else if (categoriaMenu.getText().equals("Gorra")) {
            producto.setCategoria(Categoria.GORRA);
        } else if (categoriaMenu.getText().equals("Calzado")) {
            producto.setCategoria(Categoria.CALZADO);
        }

        return producto;
    }

    private Producto datosActualizados(){
        Producto producto = new Producto();
        Producto productoSeleccionado = tablaAlmacen.getSelectionModel().getSelectedItem();

        //Si los campos están vacíos, se mantienen los valores del producto seleccionado
        if (descripcionField.getText().isEmpty()) {
            producto.setDescripcion(productoSeleccionado.getDescripcion());
        }else {
            producto.setDescripcion(descripcionField.getText());
        }

        if (stockField.getText().isEmpty()) {
            producto.setStock(productoSeleccionado.getStock());
        } else {
            producto.setStock(Integer.parseInt(stockField.getText()));
        }

        if (precioField.getText().isEmpty()) {
            producto.setPrecio(productoSeleccionado.getPrecio());
        }
        else {
            producto.setPrecio(Float.parseFloat(precioField.getText()));
        }

        if (categoriaMenu.getText().isEmpty()) {
            producto.setCategoria(productoSeleccionado.getCategoria());
        } else if (categoriaMenu.getText().equals("Camiseta")) {
            producto.setCategoria(Categoria.CAMISETA);
        } else if (categoriaMenu.getText().equals("Sudadera")) {
            producto.setCategoria(Categoria.SUDADERA);
        } else if (categoriaMenu.getText().equals("Gorra")) {
            producto.setCategoria(Categoria.GORRA);
        } else if (categoriaMenu.getText().equals("Calzado")) {
            producto.setCategoria(Categoria.CALZADO);
        }

        producto.setAlmacen(trabajadorActivo.getAlmacen());
        return producto;
    }







}
