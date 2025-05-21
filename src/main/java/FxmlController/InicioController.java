package FxmlController;

import DAO.ClienteDAO;
import DAO.ProductosDAO;
import controller.UsuarioActivoController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import DataBase.*;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.*;
import utils.Utilidades;

public class InicioController {

    UsuarioActivoController usuarioActivoController = UsuarioActivoController.getInstancia();
    ClienteDAO clienteDAO = new ClienteDAO(ConnectionBD.getConnection());
    ProductosDAO productosDAO = new ProductosDAO(ConnectionBD.getConnection());
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
    private MenuButton colorgorrabtn;
    @FXML
    private MenuItem colorRosagorra;

    @FXML
    private MenuButton colorcalzadobtn;
    @FXML
    private MenuItem colornegrocalzado;
    @FXML
    private MenuItem colorblancocalzado;

    @FXML
    private MenuButton tallacalzadobtn;
    @FXML
    private MenuItem tallactrescalzado;









    @FXML
    private Rectangle añadircamisetabtn;
    @FXML
    private Rectangle añadirsudaderabtn;
    @FXML
    private Rectangle añadirgorrabtn;
    @FXML
    private Rectangle añadircalzadobtn;

    private ColorProducto colorSeleccionadoc = null;
    private TallasProducto tallaSeleccionadac = null;
    private ColorProducto colorSeleccionadoS = null;
    private TallasProducto tallaSeleccionadaS = null;
    private ColorProducto colorSeleccionadoC = null;
    private TallasProducto tallaSeleccionadoC = null;
    private ColorProducto colorSeleccionadoG = null;
    private TallasProducto tallaSeleccionadoG = null;


    @FXML
    private void initialize() {
        inicio();
        if (cliente == null) {
            Utilidades.muestraMensaje("Error: el cliente no existe en la base de datos.");
            return;
        }
        if (cliente.getCarro() == null) {
            cliente.setCarro(new Carro());
            cliente.getCarro().setIdCliente(cliente.getId());
            clienteDAO.actualizar(cliente);
        }
        añadirCamisetaCarro();
        añadirSudaderaCarro();
    }


    private void inicio() {
        bienvenidaLabel.setText("Bienvenido, " + usuarioActivoController.getUsuarioActivo().getNombre() + "!");
        int cantidadProductos = 0;
        if (cliente != null && cliente.getCarro() != null){
            cantidadProductos = cliente.getCarro().cantidadProductos();
        }
        contadorCarro.setText(String.valueOf(cantidadProductos));
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

    private void añadirCamisetaCarro() {
        colornegroitemc.setOnAction(event -> {
            Utilidades.muestraMensaje("Camiseta negra seleccionada");
            colorSeleccionadoc = ColorProducto.NEGRO;
            intentarAñadirCamiseta();
        });
        colormarronitemc.setOnAction(event -> {
            colorSeleccionadoc = ColorProducto.MARRON;
            intentarAñadirCamiseta();
        });

        tallaScamisetaitem.setOnAction(event -> {
            tallaSeleccionadac = TallasProducto.S;
            intentarAñadirCamiseta();
        });
        tallaMcamisetaitem.setOnAction(event -> {
            tallaSeleccionadac = TallasProducto.M;
            intentarAñadirCamiseta();
        });
        tallaLcamisetaitem.setOnAction(event -> {
            tallaSeleccionadac = TallasProducto.L;
            intentarAñadirCamiseta();
        });
        tallaXLcamisetaitem.setOnAction(event -> {
            tallaSeleccionadac = TallasProducto.XL;
            intentarAñadirCamiseta();
        });
    }

    private void intentarAñadirCamiseta() {
        if (colorSeleccionadoc != null && tallaSeleccionadac != null && cliente != null) {
            Producto camiseta = productosDAO.obtenerPorAtributos(
                    "Camiseta majesty", tallaSeleccionadac, colorSeleccionadoc, TipoProducto.CAMISETA
            );
            if (camiseta != null) {
                cliente.getCarro().agregarProducto(camiseta, 1);
                clienteDAO.actualizar(cliente);
                cliente = clienteDAO.obtenerPorId(usuarioActivoController.getUsuarioActivo().getId());
                int cantidadProductos = cliente.getCarro().cantidadProductos();
                contadorCarro.setText(String.valueOf(cantidadProductos));
                Utilidades.muestraMensaje("producto añadido al carro");
            } else {
                Utilidades.muestraMensaje("No se encontró el producto en la base de datos");
            }
            colorSeleccionadoc = null;
            tallaSeleccionadac = null;
        }
    }

    private void añadirSudaderaCarro() {
        colorblancosudaderaitem.setOnAction(event -> {
            colorSeleccionadoS = ColorProducto.BLANCO;
            intentarAñadirSudadera();
        });
        colornegrosudaderaitem.setOnAction(event -> {
            colorSeleccionadoS = ColorProducto.NEGRO;
            intentarAñadirSudadera();
        });

        tallaSsudaderaitem.setOnAction(event -> {
            tallaSeleccionadaS = TallasProducto.S;
            intentarAñadirSudadera();
        });
        tallaMsudaderaitem.setOnAction(event -> {
            tallaSeleccionadaS = TallasProducto.M;
            intentarAñadirSudadera();
        });
        tallaLsudaderaitem.setOnAction(event -> {
            tallaSeleccionadaS = TallasProducto.L;
            intentarAñadirSudadera();
        });
        tallaXLsudaderaitem.setOnAction(event -> {
            tallaSeleccionadaS = TallasProducto.XL;
            intentarAñadirSudadera();
        });
    }

    private void intentarAñadirSudadera() {
        if (colorSeleccionadoS != null && tallaSeleccionadaS != null && cliente != null) {
            Producto sudadera = productosDAO.obtenerPorAtributos(
                    "Sudadera forever damned", tallaSeleccionadaS, colorSeleccionadoS, TipoProducto.SUDADERA
            );
            if (sudadera != null) {
                cliente.getCarro().agregarProducto(sudadera, 1);
                clienteDAO.actualizar(cliente);
                cliente = clienteDAO.obtenerPorId(usuarioActivoController.getUsuarioActivo().getId());
                int cantidadProductos = cliente.getCarro().cantidadProductos();
                contadorCarro.setText(String.valueOf(cantidadProductos));
                Utilidades.muestraMensaje("producto añadido al carro");
            } else {
                Utilidades.muestraMensaje("No se encontró el producto en la base de datos");
            }
            colorSeleccionadoS = null;
            tallaSeleccionadaS = null;
        }
    }

    private void añadirGorraCarro() {
        colorRosagorra.setOnAction(event -> {
            colorSeleccionadoG = ColorProducto.ROJO;
            intentarAñadirGorra();
        });
    }

    private void intentarAñadirGorra(){
        if (colorSeleccionadoG != null && cliente != null) {
            Producto gorra = productosDAO.obtenerPorAtributos(
                    "Gorra", TallasProducto.TALLA_UNICA, colorSeleccionadoG, TipoProducto.GORRA
            );
            if (gorra != null) {
                cliente.getCarro().agregarProducto(gorra, 1);
                clienteDAO.actualizar(cliente);
                cliente = clienteDAO.obtenerPorId(usuarioActivoController.getUsuarioActivo().getId());
                int cantidadProductos = cliente.getCarro().cantidadProductos();
                contadorCarro.setText(String.valueOf(cantidadProductos));
                Utilidades.muestraMensaje("producto añadido al carro");
            } else {
                Utilidades.muestraMensaje("No se encontró el producto en la base de datos");
            }
            colorSeleccionadoG = null;
        }
    }

    private void añadirCalzadoCarro() {
        colornegrocalzado.setOnAction(event -> {
            colorSeleccionadoC = ColorProducto.NEGRO;
            intentarAñadirCalzado();

        });


        colorblancocalzado.setOnAction(actionEvent -> {
                colorSeleccionadoC = ColorProducto.BLANCO;
            intentarAñadirCalzado();
        });

        tallactrescalzado.setOnAction(actionEvent -> {
        tallaSeleccionadoC = TallasProducto.CUARENTA_TRES;
        intentarAñadirCalzado();
        });
    };

    private void intentarAñadirCalzado(){
        if (colorSeleccionadoC != null && cliente != null) {
            Producto calzado = productosDAO.obtenerPorAtributos(
                    "chanclas", tallaSeleccionadoC, colorSeleccionadoG, TipoProducto.CALZADO
            );
            if (calzado != null) {
                cliente.getCarro().agregarProducto(calzado, 1);
                clienteDAO.actualizar(cliente);
                cliente = clienteDAO.obtenerPorId(usuarioActivoController.getUsuarioActivo().getId());
                int cantidadProductos = cliente.getCarro().cantidadProductos();
                contadorCarro.setText(String.valueOf(cantidadProductos));
                Utilidades.muestraMensaje("producto añadido al carro");
            } else {
                Utilidades.muestraMensaje("No se encontró el producto en la base de datos");
            }
            colorSeleccionadoG = null;
        }
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
