package FxmlController;

import controller.UsuarioActivoController;
import controller.UsuariosController;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;
import utils.Utilidades;

import java.awt.*;
import java.io.IOException;

public class InicioSesionController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private javafx.scene.shape.Rectangle btnIniciarSesion;

    @FXML
    private javafx.scene.shape.Rectangle btnRegistrar;

    @FXML
    private javafx.scene.shape.Rectangle btnInvitado;


    UsuariosController usuariosController = UsuariosController.getInstancia();
    UsuarioActivoController usuarioActivoController = UsuarioActivoController.getInstancia();

    @FXML
    private void iniciarSesion (javafx.scene.input.MouseEvent event) throws IOException {

            String usuario = txtUsuario.getText();
            String contraseña = txtContraseña.getText();

             if(usuariosController.getListaUsuarios().isEmpty()) {
                 Alert alerta = new Alert(Alert.AlertType.ERROR);
                 alerta.setTitle("Error inicio de sesión");
                 alerta.setHeaderText("Usuario no registrado");
                 alerta.setContentText("Registra un usuario para poder acceder al sistema");
                 alerta.showAndWait();
                 return;

        }

        for (Usuario u : usuariosController.getListaUsuarios()) {
            if (!u.getUsuario().equals(usuario) || !u.getContraseña().equals(contraseña)) {
                Alert alerta2 = new Alert(Alert.AlertType.ERROR);
                alerta2.setTitle("Error inicio de sesión");
                alerta2.setHeaderText("Usuario no registrado");
                alerta2.setContentText("Registra un usuario para poder acceder al sistema");
                alerta2.showAndWait();
            }
        }

        boolean encontrado = false;
        for (Usuario u : usuariosController.getListaUsuarios()) {
            if (u.getUsuario().equals(usuario) && u.getContraseña().equals(contraseña)) {
                usuarioActivoController.setUsuarioActivo(u);
                cambiarEscena(event, "/Fxml/Inicio.fxml");
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error inicio de sesión");
            alerta.setHeaderText("Usuario o contraseña incorrectos");
            alerta.setContentText("Por favor, verifica tus credenciales.");
            alerta.showAndWait();
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
    private void registrar (javafx.scene.input.MouseEvent event) throws IOException {
        cambiarEscena(event, "/Fxml/Registro.fxml");
    }

    @FXML
    private void invitado (javafx.scene.input.MouseEvent event) throws IOException {
        usuarioActivoController.usarInvitado();
        cambiarEscena(event, "/Fxml/Inicio.fxml");
    }

}
