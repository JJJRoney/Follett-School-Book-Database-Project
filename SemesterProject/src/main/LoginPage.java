import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoginPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create top menu buttons
        Button homeButton = createStyledButton("Home");
        Button collectionsButton = createStyledButton("Collections");

        // Lock buttons to the top left and top right respectively
        HBox leftMenu = new HBox(10, homeButton);
        HBox rightMenu = new HBox(10, collectionsButton);
        leftMenu.setAlignment(Pos.TOP_LEFT);
        rightMenu.setAlignment(Pos.TOP_RIGHT);

        // Set padding for the right menu to create a fixed distance from the top right corner
        leftMenu.setPadding(new Insets(10, 0, 0, 10));
        rightMenu.setPadding(new Insets(10, 10, 0, 0));

        // Create an ImageView with the specified image file
        //Image logo = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
        //ImageView ivLogo = new ImageView(logo);
        //ivLogo.setPickOnBounds(true); // Make ImageView contain entire image, not just the geometrical shape

        // Create EventHandler for clicking on image
        Image logo = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
        ImageView imageView = null;
            imageView = new ImageView(logo);
            imageView.setPickOnBounds(true);
            imageView.setFitWidth(200); // Set desired width
            imageView.setFitHeight(100); // Set desired height
            imageView.setOnMouseClicked(e -> {
                getHostServices().showDocument("https://renaissancepsa.com/");
            });

        // Create centered label for login
        Label loginLabel = createStyledLabel("Login");

        // Create a VBox to stack the image and the label vertically
        VBox vbox = new VBox(10, imageView, loginLabel);
        vbox.setAlignment(Pos.TOP_CENTER);

        // Create labels and text fields for username and password
        Label usernameLabel = createStyledLabel("Username:");
        TextField usernameField = createStyledTextField();

        Label passwordLabel = createStyledLabel("Password:");
        TextField passwordField = createStyledTextField();

        // Create login button
        Button loginButton = createStyledButton("Login");

        // Create layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Center form
        GridPane centerForm = new GridPane();
        centerForm.addRow(0, usernameLabel, usernameField);
        centerForm.addRow(1, passwordLabel, passwordField);
        centerForm.add(loginButton, 1, 2);
        centerForm.setVgap(10);
        centerForm.setHgap(10);
        centerForm.setAlignment(Pos.CENTER);

        StackPane topPane = new StackPane();
        topPane.getChildren().addAll(leftMenu, rightMenu, vbox);
        root.setTop(topPane);
        root.setCenter(centerForm);

        // Create scene and set it to the stage
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Form");
        primaryStage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold;");
        return button;
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold;");
        return label;
    }

    private TextField createStyledTextField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-font-size: 14pt;");
        return textField;
    }

    public static void main(String[] args) {
        launch(args);
    }
}