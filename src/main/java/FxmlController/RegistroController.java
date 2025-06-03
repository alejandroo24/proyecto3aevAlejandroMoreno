// En RegistroController.java
package FxmlController;

import DAO.ClienteDAO;
import DataBase.ConnectionBD;
import controller.ClienteController;
import controller.TrabajadorController;
import controller.UsuarioActivoController;
import controller.UsuariosController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.shape.Rectangle;
import model.Cliente;
import model.Trabajador;
import model.Usuario;

import java.io.IOException;

public class RegistroController {
    @FXML private TextField txtNombre;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContraseña;
    @FXML private TextField txtCorreo;
    @FXML private Rectangle btnRegistrarse;
    @FXML private Rectangle btnAtras;
    @FXML private Rectangle btnInvitado;
    @FXML private CheckBox chboxTrabajador;


    UsuariosController usuariosController = UsuariosController.getInstancia();
    ClienteController clienteController = ClienteController.getInstancia();
    TrabajadorController trabajadorController = TrabajadorController.getInstancia();
    UsuarioActivoController usuarioActivoController= UsuarioActivoController.getInstancia();
    UsuarioDAO usuarioDAO = new UsuarioDAO(ConnectionBD.getConnection());


    @FXML
    private void registrarUsuario (javafx.scene.input.MouseEvent event) throws IOException {

        String usuario = txtUsuario.getText();
        String contraseña = txtContraseña.getText();
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        boolean esTrabajador = chboxTrabajador.isSelected();

        boolean encontrado = false;
        for (Usuario u : usuariosController.getListaUsuarios()) {
            if (u.getUsuario().equals(usuario) && u.getContraseña().equals(contraseña)) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error al registrar");
                alerta.setHeaderText("Usuario ya existe");
                alerta.setContentText("Este usuario ya está registrado. Inicia sesión o elige otro nombre de usuario.");
                alerta.showAndWait();
                encontrado = true; // <-- Añade esto
                break;
            }
        }
        if (!encontrado) {
            Usuario nuevoUsuario = new Usuario(nombre, contraseña, correo, usuario,esTrabajador);
            if (usuarioDAO.existeCorreo(correo)) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error al registrar");
                alerta.setHeaderText("Correo ya existe");
                alerta.setContentText("Este correo ya está registrado. Inicia sesión o elige otro correo.");
                alerta.showAndWait();
            } else {
                usuarioDAO.insertar(nuevoUsuario);
                if (!nuevoUsuario.isTrabajador()) {
                    Cliente cliente = new Cliente(nuevoUsuario);
                    ClienteDAO clienteDAO = new ClienteDAO(ConnectionBD.getConnection());
                    clienteDAO.insertar(cliente);
                }else{
                    Trabajador trabajador = new Trabajador(nuevoUsuario);

                }

                usuariosController.addUsuario(nuevoUsuario);
            usuarioActivoController.setUsuarioActivo(nuevoUsuario);

            }
            if (esTrabajador) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Registro exitoso");
                alerta.setHeaderText("Bienvenido trabajador");
                alerta.setContentText("Has sido registrado como trabajador.");
                alerta.showAndWait();
                cambiarEscena(event, "/Fxml/Inicio.fxml");
            } else {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Registro exitoso");
                alerta.setHeaderText("Bienvenido cliente");
                alerta.setContentText("Has sido registrado como cliente.");
                alerta.showAndWait();
                cambiarEscena(event, "/Fxml/Inicio.fxml");
            }
        }
    }

    @FXML
    private void atras(javafx.scene.input.MouseEvent event) throws IOException {
        cambiarEscena(event, "/Fxml/Login.fxml");
    }

    @FXML
    private void invitado(javafx.scene.input.MouseEvent event) throws IOException {
        usuarioActivoController.usarInvitado();
        cambiarEscena(event, "/Fxml/Inicio.fxml");
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
}
