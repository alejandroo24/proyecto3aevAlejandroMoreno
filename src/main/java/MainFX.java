import javafx.application.Application;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {
        // Your JavaFX application code here
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/InicioSesion.fxml"));
        primaryStage.setTitle("Forever Damned");
        primaryStage.setScene(new javafx.scene.Scene(root));
        primaryStage.setMaximized(true);

        primaryStage.show();
    }
}
