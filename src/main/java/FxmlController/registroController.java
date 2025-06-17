// En RegistroController.java
package FxmlController;

import DAO.ClienteDAO;
import DAO.TrabajadorDAO;
import DataBase.ConnectionBD;

import controller.UsuarioActivoController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.shape.Rectangle;
import model.Cliente;
import model.Trabajador;
import utils.Utilidades;

import java.io.IOException;

public class registroController {
    @FXML private TextField txtNombre;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContraseña;
    @FXML private TextField txtCorreo;
    @FXML private Rectangle btnRegistrarse;
    @FXML private Rectangle btnAtras;
    @FXML private Rectangle btnInvitado;
    @FXML private CheckBox chboxTrabajador;



    UsuarioActivoController usuarioActivoController= UsuarioActivoController.getInstancia();
    ClienteDAO clienteDAO = new ClienteDAO(ConnectionBD.getConnection());
    TrabajadorDAO trabajadorDAO = new TrabajadorDAO(ConnectionBD.getConnection());


    @FXML
    private void registrarUsuario (javafx.scene.input.MouseEvent event) throws IOException {

        String usuario = txtUsuario.getText();
        String contraseña = txtContraseña.getText();
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        boolean esTrabajador = chboxTrabajador.isSelected();



        btnRegistrarse.setOnMouseClicked(MouseEvent -> {
            try {
                registraUsuario(nombre, usuario, contraseña, correo, esTrabajador, event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }


    private boolean camposValidos() {
        String nombre = txtNombre.getText();
        String usuario = txtUsuario.getText();
        String contraseña = txtContraseña.getText();
        String correo = txtCorreo.getText();

        if (nombre.isEmpty() || usuario.isEmpty() || contraseña.isEmpty() || correo.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error al registrar");
            alerta.setHeaderText("Campos incompletos");
            alerta.setContentText("Por favor, completa todos los campos.");
            alerta.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    private boolean usuarioRegistrado(String nickname, String contraseña) {
        for (Cliente cliente : clienteDAO.obtenerTodos()) {
            if (cliente.getNickname().equals(nickname) && cliente.getContraseña().equals(contraseña)) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error al iniciar sesión");
                alerta.setHeaderText("Usuario ya registrado");
                alerta.setContentText("Este usuario ya está registrado. Inicia sesión o elige otro nombre de usuario.");
                return true;
            }
        }
        for (Trabajador trabajador : trabajadorDAO.obtenerTodos()) {
            if (trabajador.getNickname().equals(nickname) && trabajador.getContraseña().equals(contraseña)) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error al iniciar sesión");
                alerta.setHeaderText("Usuario ya registrado");
                alerta.setContentText("Este usuario ya está registrado. Inicia sesión o elige otro nombre de usuario.");
                return true;
            }
        }

        return false;
    }

    private boolean verificaCorreo(String correo) {

        if (Utilidades.validarEmail(correo)){
            return true;
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error al registrar");
            alerta.setHeaderText("Correo inválido");
            alerta.setContentText("Por favor, introduce un correo electrónico válido.");
            alerta.showAndWait();
            return false;
        }
    }

    private boolean registraUsuario(String nombre, String usuario, String contraseña, String correo, boolean esTrabajador, javafx.scene.input.MouseEvent event) throws IOException {
        if (camposValidos() && !usuarioRegistrado(usuario, contraseña) && verificaCorreo(correo)) {
        if (esTrabajador) {
                Trabajador trabajador = new Trabajador(nombre, contraseña, correo, usuario);
                trabajadorDAO.insertar(trabajador);
                usuarioActivoController.setUsuarioActivo(trabajador);
                cambiarEscena(event, "/Fxml/InicioTrabajador.fxml");
            } else {
                Cliente cliente = new Cliente(nombre, contraseña, correo, usuario);
                clienteDAO.insertar(cliente);
                usuarioActivoController.setUsuarioActivo(cliente);
                cambiarEscena(event, "/Fxml/Inicio.fxml");
            }
            return true;
        } else {
            return false;
        }
    }

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

    @FXML
    private void atras(javafx.scene.input.MouseEvent event) throws IOException {
        cambiarEscena(event, "/Fxml/inicioSesion.fxml");
    }


}
