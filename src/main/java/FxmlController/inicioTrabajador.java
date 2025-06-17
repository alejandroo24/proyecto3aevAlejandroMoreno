package FxmlController;

import DAO.AlmacenDAO;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDate;

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
    private TableColumn<Pedido, Integer> idColumna;
    @FXML
    private TableColumn<Pedido, LocalDate> fechaColumna;
    @FXML
    private TableColumn<Pedido, EstadoPedido> estadoColumna;
    @FXML
    private TableColumn<Pedido, Float> precioColumna;

    @FXML
    private TableView<Producto> tablaAlmacen;
    @FXML
    private TableColumn<Producto, String> productoColumna;
    @FXML
    private TableColumn<Producto, Categoria> categoriaColumna;
    @FXML
    private TableColumn<Producto, Integer> stockColumna;
    @FXML
    private TableColumn<Producto, Float> precioPrColumna;




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
    private MenuItem pantalon;
    @FXML
    private MenuItem gorra;



    UsuarioActivoController usuarioActivoController = UsuarioActivoController.getInstancia();
    Trabajador trabajadorActivo = (Trabajador) usuarioActivoController.getUsuarioActivo();
    PedidoDAO pedidoDAO = new PedidoDAO(DataBase.ConnectionBD.getConnection());
    AlmacenDAO almacenDAO = new AlmacenDAO(DataBase.ConnectionBD.getConnection());
    ProductoDAO productoDAO = new ProductoDAO(DataBase.ConnectionBD.getConnection());
    ObservableList pedidosPendientes = FXCollections.observableArrayList(pedidoDAO.obtenerPedidosPorEstado(EstadoPedido.PENDIENTE));


    @FXML
    private void initialize() {
        if (trabajadorActivo.getAlmacen() == null) {
            Almacen almacen = almacenDAO.obtenerAlmacenPorTrabajadores();
            trabajadorActivo.setAlmacen(almacen);
        }
        ObservableList productosAlmacen = FXCollections.observableArrayList(productoDAO.obtenerProductosAlmacen(trabajadorActivo.getAlmacen()));

        mostrarDatosTrabajador();
        mostrarTablaPedidos();
        mostrarTablaAlmacen(productosAlmacen);

        camiseta.setOnAction(e -> categoriaMenu.setText("Camiseta"));
        sudadera.setOnAction(e -> categoriaMenu.setText("Sudadera"));
        pantalon.setOnAction(e -> categoriaMenu.setText("Pantalón"));
        gorra.setOnAction(e -> categoriaMenu.setText("Gorra"));

        confirmarPedidoBtn.setOnMouseClicked(mouseEvent -> confirmarPedido());
        cancelarPedidoBtn.setOnMouseClicked(mouseEvent -> cancelarPedido());
        añadirProductoBtn.setOnMouseClicked(mouseEvent -> añadirProducto());
        actualizarProductoBtn.setOnMouseClicked(mouseEvent -> actualizarProducto(productosAlmacen));
        eliminarProductoBtn.setOnMouseClicked(mouseEvent -> eliminarProducto(productosAlmacen));
        cerrarSesionBtn.setOnMouseClicked(event -> cerrarSesion(event));
    }

    private void mostrarTablaPedidos() {
        idColumna.setCellValueFactory(new PropertyValueFactory<>("id"));
        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        estadoColumna.setCellValueFactory(new PropertyValueFactory<>("estadoPedido"));
        precioColumna.setCellValueFactory((cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getPrecioTotal())
        ));
        tablaPedidos.setItems(pedidosPendientes);
        tablaPedidos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void mostrarTablaAlmacen(ObservableList productosAlmacen) {
        productoColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        categoriaColumna.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        stockColumna.setCellValueFactory(new PropertyValueFactory<>("stock"));
        precioPrColumna.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tablaAlmacen.setItems(productosAlmacen);
        tablaAlmacen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void mostrarDatosTrabajador() {
        bienvenidaLabel.setText("Bienvenido, " + trabajadorActivo.getNombre());
        salarioLabel.setText("" + trabajadorActivo.getSalario() + " €");
        almacenLabel.setText( ""+ trabajadorActivo.getAlmacen().getNombre());
    }


    private boolean confirmarPedido() {
        Pedido pedidoSeleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (pedidoSeleccionado != null && pedidoSeleccionado.getEstadoPedido() == EstadoPedido.PENDIENTE) {
            pedidoDAO.confirmarPedido(pedidoSeleccionado);
            pedidosPendientes.setAll(pedidoDAO.obtenerPedidosPorEstado(EstadoPedido.PENDIENTE));
            mostrarTablaPedidos();
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Pedido Confirmado");
            alerta.setHeaderText(null);
            alerta.setContentText("El pedido ha sido confirmado exitosamente.");
            alerta.showAndWait();
            trabajadorActivo.setSalario(trabajadorActivo.getSalario() + 20);
            mostrarDatosTrabajador();
            return true;
        }
        return false;
    }

    private boolean cancelarPedido() {
        Pedido pedidoSeleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (pedidoSeleccionado != null && pedidoSeleccionado.getEstadoPedido() == EstadoPedido.PENDIENTE) {
            pedidoDAO.cancelarPedido(pedidoSeleccionado);
            pedidosPendientes.setAll(pedidoDAO.obtenerPedidosPorEstado(EstadoPedido.PENDIENTE));
            mostrarTablaPedidos();
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

    private boolean actualizarProducto(ObservableList productosAlmacen) {
        Producto productoSeleccionado = tablaAlmacen.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            Producto productoActualizado = datosActualizados();
            if (productoActualizado == null) {
                return false;
            } else {
                productoActualizado.setId(productoSeleccionado.getId());
                productoDAO.actualizar(productoActualizado);
                productosAlmacen.setAll(productoDAO.obtenerProductosAlmacen(trabajadorActivo.getAlmacen()));
                mostrarTablaAlmacen(productosAlmacen);
                return true;
            }
        }
        return false;
    }

    private boolean eliminarProducto(ObservableList productosAlmacen) {
        Producto productoSeleccionado = tablaAlmacen.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            productoDAO.eliminar(productoSeleccionado);
            productosAlmacen.setAll(productoDAO.obtenerProductosAlmacen(trabajadorActivo.getAlmacen()));
            mostrarTablaAlmacen(productosAlmacen);
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

        // Asignar la categoría según el texto seleccionado en el MenuButton
        switch (categoriaMenu.getText()) {
            case "Camiseta":
                producto.setCategoria(Categoria.CAMISETA);
                break;
            case "Sudadera":
                producto.setCategoria(Categoria.SUDADERA);
                break;
            case "Gorra":
                producto.setCategoria(Categoria.GORRA);
                break;
            case "Pantalón":
                producto.setCategoria(Categoria.PANTALON);
                break;
            default:
                producto.setCategoria(null);
        }

        // Validar que la categoría no sea null
        if (producto.getCategoria() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Categoría no seleccionada");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecciona una categoría válida.");
            alerta.showAndWait();
            return null;
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
            String precioTexto = precioField.getText().replace(",", ".");
            producto.setPrecio(Float.parseFloat(precioTexto));
        }

        String categoriaTexto = categoriaMenu.getText();
        if (categoriaTexto.isEmpty()) {
            producto.setCategoria(productoSeleccionado.getCategoria());
        } else if (categoriaTexto.equals("Camiseta")) {
            producto.setCategoria(Categoria.CAMISETA);
        } else if (categoriaTexto.equals("Sudadera")) {
            producto.setCategoria(Categoria.SUDADERA);
        } else if (categoriaTexto.equals("Gorra")) {
            producto.setCategoria(Categoria.GORRA);
        } else if (categoriaTexto.equals("Pantalón") || categoriaTexto.equals("Pantalon")) {
            producto.setCategoria(Categoria.PANTALON);
        } else {
            producto.setCategoria(null);
        }

        if (producto.getCategoria() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Categoría no seleccionada");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecciona una categoría válida.");
            alerta.showAndWait();
            return null;
        }

        producto.setAlmacen(trabajadorActivo.getAlmacen());
        return producto;
    }







}
