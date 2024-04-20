/**
 * The BrickBreaker class is the main entry point for the Brick Breaker game application.
 * It sets up the primary stage and loads the main game interface from an FXML file.
 * This class extends Application and overrides the start method to set up the JavaFX stage.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BrickBreaker extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Brick Breaker!");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);  // Maximize the window on startup
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
