package FxmlController;

import DAO.ClienteDAO;
import DAO.ProductoDAO;
import DataBase.ConnectionBD;
import controller.UsuarioActivoController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import model.Cliente;

public class SaldoMenuController {

    UsuarioActivoController usuarioActivoController = UsuarioActivoController.getInstancia();
    ClienteDAO clienteDAO = new ClienteDAO(ConnectionBD.getConnection());
    ProductoDAO productosDAO = new ProductoDAO(ConnectionBD.getConnection());
    ClienteController clienteController = ClienteController.getInstancia();
    Cliente cliente = clienteDAO.obtenerPorId(usuarioActivoController.getUsuarioActivo().getId());
    PremiosController premiosController = PremiosController.getInstance();
    PremiosDAO premiosDAO = new PremiosDAO(ConnectionBD.getConnection());
    CodigoDAO codigoDAO = new CodigoDAO(ConnectionBD.getConnection());



    @FXML
    private javafx.scene.control.Label bienvenidaLabel;

    @FXML
    private javafx.scene.control.Label saldoLabel;

    @FXML
    private Rectangle añadirsaldobtn;

    @FXML
    private MenuButton valormenubtn;

    @FXML
    private javafx.scene.control.MenuItem diezemnit;

    @FXML
    private javafx.scene.control.MenuItem veinteemnit;

    @FXML
    private MenuItem cincuentaemnit;

    @FXML
    private javafx.scene.control.Label codigolabel;

    @FXML
    private javafx.scene.control.TextField codigocanjeartxt;

    @FXML
    private Rectangle canjearbtn;

    @FXML
    private Rectangle generarcodigobtn;

    private ImageView btnCopiar;







    @FXML
    private void initialize() {
        bienvenidaLabel.setText("bienvenido, "+ cliente.getNombre());
        saldoLabel.setText(cliente.getSaldo() + "€");
        añadirsaldobtn.setOnMouseClicked(event -> añadirSaldo());
        generarcodigobtn.setOnMouseClicked(mouseEvent -> generarCodigo());
    }

    @FXML
    private void canjearCodigo(){
        String codigo = codigocanjeartxt.getText();

        for ( Codigo buscaCodigo :codigoDAO.obtenerTodos()){
            if (buscaCodigo.getCodigo().equals(codigo)) {
                Codigo codigoCanjeado = buscaCodigo;
                añadirSaldo(codigoCanjeado.getValor());
                codigoCanjeado.setUsado(true);
                premiosController.getCodigosUsados().add(codigoCanjeado);
                codigoDAO.actualizar(codigoCanjeado);
            }
        }
    }

    @FXML
    private void añadirSaldo() {
    clienteController.añadirSaldo(cliente.getSaldo()+20);
    clienteDAO.actualizar(cliente);
    }

    @FXML
    private void añadirSaldo(float cantidad) {
        clienteController.añadirSaldo(cantidad);
        clienteDAO.actualizar(cliente);
        saldoLabel.setText(cliente.getSaldo() + "€");
    }

    @FXML
    private void generarCodigo(){
        diezemnit.setOnAction(event -> {
            // Generar código de 10€ y mostrarlo en codigolabel
            String codigo = clienteController.CrearTarjetaRegalo(10);
            codigolabel.setText(codigo);
            Codigo code = new Codigo();
            code.setCodigo(codigo);
            code.setValor(10);
            premiosController.getCodigosValidos().add(code);
            codigoDAO.insertar(code);

        });

        veinteemnit.setOnAction(event -> {
            // Generar código de 20€ y mostrarlo en codigolabel
            String codigo = clienteController.CrearTarjetaRegalo(20);
            codigolabel.setText(codigo);
            Codigo code = new Codigo();
            code.setCodigo(codigo);
            code.setValor(20);
            premiosController.getCodigosValidos().add(code);
            codigoDAO.insertar(code);


        });

        cincuentaemnit.setOnAction(event -> {
            // Generar código de 50€ y mostrarlo en codigolabel
            String codigo = clienteController.CrearTarjetaRegalo(50);
            codigolabel.setText(codigo);
            Codigo code = new Codigo();
            code.setCodigo(codigo);
            code.setValor(50);
            premiosController.getCodigosValidos().add(code);
            codigoDAO.insertar(code);
        });

    }

    @FXML
    private void cambiarEscena(javafx.scene.input.MouseEvent event, String rutaFXML) {
        try {
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource(rutaFXML));
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setScene(new javafx.scene.Scene(root, 1280, 720));
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




