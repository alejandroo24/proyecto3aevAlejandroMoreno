package FxmlController;

import DAO.ClienteDAO;
import DAO.TrabajadorDAO;
import DataBase.ConnectionBD;
import controller.UsuarioActivoController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Cliente;
import model.Trabajador;

import java.io.IOException;

public class inicioSesionController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private javafx.scene.shape.Rectangle btnIniciarSesion;

    @FXML
    private javafx.scene.shape.Rectangle btnRegistrar;

    ClienteDAO clienteDAO = new ClienteDAO(ConnectionBD.getConnection());
    TrabajadorDAO trabajadorDAO = new TrabajadorDAO(ConnectionBD.getConnection());
    UsuarioActivoController usuarioActivoController = UsuarioActivoController.getInstancia();


    @FXML
    private void iniciarSesion (javafx.scene.input.MouseEvent event)  {

            String nickname = txtUsuario.getText();
            String contraseña = txtContraseña.getText();
                btnIniciarSesion.setOnMouseClicked(mouseEvent -> {
                    try {
                        compruebaUsuario(nickname,event);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });






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


    private void compruebaUsuario(String nickname,javafx.scene.input.MouseEvent event) throws IOException {
        if (!clienteDAO.existeCliente(nickname) && !trabajadorDAO.existeTrabajador(nickname)) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error inicio de sesión");
            alerta.setHeaderText("Usuario no registrado");
            alerta.setContentText("Registra un usuario para poder acceder al sistema");
            alerta.showAndWait();

        }else {
            String nicknameusuario = txtUsuario.getText();
            String contraseña = txtContraseña.getText();
                inicioCorrecto(nicknameusuario, contraseña, event);
        }
    }

    private boolean inicioCorrecto(String nickname, String contraseña, javafx.scene.input.MouseEvent event) {
        boolean encontrado = false;
        for (Cliente cliente : clienteDAO.obtenerTodos()) {
            if (cliente.getNickname().equals(nickname) && cliente.getContraseña().equals(contraseña)) {
                usuarioActivoController.setUsuarioActivo(cliente);
                cambiarEscena(event, "/Fxml/Inicio.fxml");
                encontrado = true;
                return encontrado;
            }
        }
        for (Trabajador trabajador : trabajadorDAO.obtenerTodos()) {
            if (trabajador.getNickname().equals(nickname) && trabajador.getContraseña().equals(contraseña)) {
                usuarioActivoController.setUsuarioActivo(trabajador);
                cambiarEscena(event, "/Fxml/InicioTrabajador.fxml");
                encontrado = true;
                return encontrado;
            }
        }

        if (!encontrado) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error inicio de sesión");
            alerta.setHeaderText("Usuario o contraseña incorrectos");
            alerta.setContentText("Por favor, verifica tus credenciales e intenta nuevamente.");
            alerta.showAndWait();
        }
        return false;
    }
}
