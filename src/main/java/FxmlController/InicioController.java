package FxmlController;

import DAO.ClienteDAO;
import controller.UsuarioActivoController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import DataBase.*;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.*;

public class InicioController {

    UsuarioActivoController usuarioActivoController = UsuarioActivoController.getInstancia();
    ClienteDAO clienteDAO = new ClienteDAO(ConnectionBD.getConnection());
    Cliente cliente = clienteDAO.obtenerPorId(usuarioActivoController.getUsuarioActivo().getId());


    @FXML
    private Label bienvenidaLabel;
    @FXML
    private Text saldobtn;
    @FXML
    private Text premiobtn;
    @FXML
    private Text pedidosbtn;
    @FXML
    private ImageView carrobtn;
    @FXML
    private Label contadorCarro;

    @FXML
    private MenuButton colorcamisetamnbtn;
    @FXML
    private MenuItem colornegroitemc;
    @FXML
    private MenuItem colormarronitemc;

    @FXML
    private MenuButton tallacamisetamnbtn;
    @FXML
    private MenuItem tallaScamisetaitem;
    @FXML
    private MenuItem tallaMcamisetaitem;
    @FXML
    private MenuItem tallaLcamisetaitem;
    @FXML
    private MenuItem tallaXLcamisetaitem;


    @FXML
    private MenuButton colorsudaderamnbtn;
    @FXML
    private MenuItem colorblancosudaderaitem;
    @FXML
    private MenuItem colornegrosudaderaitem;


    @FXML
    private MenuButton tallasudaderamnbtn;
    @FXML
    private MenuItem tallaSsudaderaitem;
    @FXML
    private MenuItem tallaMsudaderaitem;
    @FXML
    private MenuItem tallaLsudaderaitem;
    @FXML
    private MenuItem tallaXLsudaderaitem;


    @FXML
    private Rectangle añadircamisetabtn;
    @FXML
    private Rectangle añadirsudaderabtn;

    private ColorProducto colorSeleccionadoc = null;
    private TallasProducto tallaSeleccionadac = null;
    private ColorProducto colorSeleccionadoS = null;
    private TallasProducto tallaSeleccionadaS = null;



    @FXML
    private void initialize() {
        inicio();
        añadirCamisetaCarro();
        añadirSudaderaCarro();
    }


    private void inicio() {
        bienvenidaLabel.setText("Bienvenido, " + usuarioActivoController.getUsuarioActivo().getNombre() + "!");
        int cantidadProductos = (clienteDAO.obtenerPorId(usuarioActivoController.getUsuarioActivo().getId()).getCarro().cantidadProductos());
        contadorCarro.setText(String.valueOf(cantidadProductos));
    }

    @FXML
    private void cambiarEscena(javafx.scene.input.MouseEvent event, String rutaFXML) {
        try {
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource(rutaFXML));
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
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

    private void añadirCamisetaCarro() {
        colornegroitemc.setOnAction(event -> colorSeleccionadoc = ColorProducto.BLANCO);
        colormarronitemc.setOnAction(event ->  colorSeleccionadoc = ColorProducto.MARRON);

        // Configurar acciones para las tallas
        tallaScamisetaitem.setOnAction(event -> tallaSeleccionadac= TallasProducto.S);
        tallaMcamisetaitem.setOnAction(event -> tallaSeleccionadac = TallasProducto.M);
        tallaLcamisetaitem.setOnAction(event -> tallaSeleccionadac = TallasProducto.L);
        tallaXLcamisetaitem.setOnAction(event -> tallaSeleccionadac = TallasProducto.XL);

        if (colorSeleccionadoc != null && tallaSeleccionadac != null) {

            Producto camiseta = new Producto("Camiseta majesty",tallaSeleccionadac,colorSeleccionadoc, TipoProducto.CAMISETA);
            cliente.getCarro().agregarProducto(camiseta,1);
        }

        clienteDAO.actualizar(cliente);

        int cantidadProductos = (clienteDAO.obtenerPorId(usuarioActivoController.getUsuarioActivo().getId()).getCarro().cantidadProductos());
        contadorCarro.setText(String.valueOf(cantidadProductos));

    }

    private void añadirSudaderaCarro(){
        colorblancosudaderaitem.setOnAction(event -> colorSeleccionadoS = ColorProducto.BLANCO);
        colornegrosudaderaitem.setOnAction(event ->  colorSeleccionadoS = ColorProducto.NEGRO);

        // Configurar acciones para las tallas
        tallaScamisetaitem.setOnAction(event -> tallaSeleccionadaS = TallasProducto.S);
        tallaMcamisetaitem.setOnAction(event -> tallaSeleccionadaS = TallasProducto.M);
        tallaLcamisetaitem.setOnAction(event -> tallaSeleccionadaS = TallasProducto.L);
        tallaXLcamisetaitem.setOnAction(event -> tallaSeleccionadaS = TallasProducto.XL);

        if (colorSeleccionadoS != null && tallaSeleccionadaS != null) {

            Producto sudadera = new Producto("Sudadera forever damned",tallaSeleccionadaS,colorSeleccionadoS, TipoProducto.SUDADERA);
            cliente.getCarro().agregarProducto(sudadera,1);
        }
        clienteDAO.actualizar(cliente);

        int cantidadProductos = (clienteDAO.obtenerPorId(usuarioActivoController.getUsuarioActivo().getId()).getCarro().cantidadProductos());
        contadorCarro.setText(String.valueOf(cantidadProductos));
    }

    @FXML
    private void irASaldo(javafx.scene.input.MouseEvent event) {
        // Lógica para ir a la pantalla de saldo
        cambiarEscena(event, "/Fxml/SaldoMenu.fxml");
    }
    @FXML
    private void irAPremios(javafx.scene.input.MouseEvent event) {
        // Lógica para ir a la pantalla de saldo
        cambiarEscena(event, "/Fxml/PremiosMenu.fxml");
    }

    @FXML
    private void irAPedidos(javafx.scene.input.MouseEvent event) {
        // Lógica para ir a la pantalla de saldo
        cambiarEscena(event, "/Fxml/PedidosMenu.fxml");
    }

    @FXML
    private void irACarro(javafx.scene.input.MouseEvent event) {
        // Lógica para ir a la pantalla de saldo
        cambiarEscena(event, "/Fxml/carroMenu.fxml");
    }
}
