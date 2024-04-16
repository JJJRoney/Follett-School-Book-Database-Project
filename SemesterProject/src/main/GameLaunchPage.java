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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameLaunchPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Creating game images
        Image game1Image = new Image("https://www.coolmathgames.com/sites/default/files/Snake_OG-logo.jpg");
        ImageView game1ImageView = new ImageView(game1Image);
        game1ImageView.setFitWidth(200);
        game1ImageView.setFitHeight(200);
        game1ImageView.setOnMouseClicked(event -> {
            // Handle click event for game 1
            System.out.println("Game 1 clicked");
            // Add code to navigate to game 1 scene
        });

        Image game2Image = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
        ImageView game2ImageView = new ImageView(game2Image);
        game2ImageView.setFitWidth(200);
        game2ImageView.setFitHeight(200);
        game2ImageView.setOnMouseClicked(event -> {
            // Handle click event for game 2
            System.out.println("Game 2 clicked");
            // Add code to navigate to game 2 scene
        });

        Image game3Image = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
        ImageView game3ImageView = new ImageView(game3Image);
        game3ImageView.setFitWidth(200);
        game3ImageView.setFitHeight(200);
        game3ImageView.setOnMouseClicked(event -> {
            // Handle click event for game 3
            System.out.println("Game 3 clicked");
            // Add code to navigate to game 3 scene
        });

        Image game4Image = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
        ImageView game4ImageView = new ImageView(game4Image);
        game4ImageView.setFitWidth(200);
        game4ImageView.setFitHeight(200);
        game4ImageView.setOnMouseClicked(event -> {
            // Handle click event for game 4
            System.out.println("Game 4 clicked");
            // Add code to navigate to game 4 scene
        });

        // Creating game titles
        Label game1Label = new Label("Game 1");
        Label game2Label = new Label("Game 2");
        Label game3Label = new Label("Game 3");
        Label game4Label = new Label("Game 4");

        // Centering label text
        game1Label.setAlignment(Pos.CENTER);
        game2Label.setAlignment(Pos.CENTER);
        game3Label.setAlignment(Pos.CENTER);
        game4Label.setAlignment(Pos.CENTER);

        // Creating layout
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));

        // VBox for each game
        VBox game1Box = new VBox(5);
        game1Box.getChildren().addAll(game1ImageView, game1Label);
        game1Box.setAlignment(Pos.CENTER); // Center alignment for VBox

        VBox game2Box = new VBox(5);
        game2Box.getChildren().addAll(game2ImageView, game2Label);
        game2Box.setAlignment(Pos.CENTER); // Center alignment for VBox

        VBox game3Box = new VBox(5);
        game3Box.getChildren().addAll(game3ImageView, game3Label);
        game3Box.setAlignment(Pos.CENTER); // Center alignment for VBox

        VBox game4Box = new VBox(5);
        game4Box.getChildren().addAll(game4ImageView, game4Label);
        game4Box.setAlignment(Pos.CENTER); // Center alignment for VBox

        FlowPane imagePane = new FlowPane(20, 20);
        imagePane.getChildren().addAll(game1Box, game2Box, game3Box, game4Box);
        imagePane.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(imagePane);
        
        Button homeButton = createStyledButton("Home");
        Button collectionsButton = createStyledButton("Collections");

        // Lock buttons to the top left and top right respectively
        VBox leftMenu = new VBox(10, homeButton);
        VBox rightMenu = new VBox(10, collectionsButton);
        leftMenu.setAlignment(Pos.TOP_LEFT);
        rightMenu.setAlignment(Pos.TOP_RIGHT);

        // Set padding for the right menu to create a fixed distance from the top right corner
        leftMenu.setPadding(new Insets(10, 10, 10, 10));
        rightMenu.setPadding(new Insets(10, 10, 10, 10));

        // Create an ImageView with the specified image file
        Image logo = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
        ImageView imageView = new ImageView(logo);
        imageView.setPickOnBounds(true);
        imageView.setFitWidth(200); // Set desired width
        imageView.setFitHeight(100); // Set desired height
        imageView.setOnMouseClicked(e -> {
            getHostServices().showDocument("https://renaissancepsa.com/");
        });


        VBox logoBox = new VBox(10, imageView);
        logoBox.setAlignment(Pos.TOP_CENTER);

        
        GridPane topGridPane = new GridPane();
        topGridPane.setHgap(10);
        topGridPane.add(leftMenu, 0, 0);
        topGridPane.add(logoBox, 1, 0);
        topGridPane.add(rightMenu, 2, 0);
        topGridPane.setAlignment(Pos.CENTER);

        // Setting up the scene
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vbox);
        borderPane.setTop(topGridPane);
        Scene scene = new Scene(borderPane, 800, 600);

        primaryStage.setTitle("Game Home Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
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
}