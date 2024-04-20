/*
 * Devon Burton
 * CPS240 final project
 *  This is the game launch page in which all of the games are listed and the user can click on the game they want to play's respective 
 *  image and the application will switch the scene to that game
 *  */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameLaunchPage extends Application {

	//main method where everything is created and put into the right place
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
            SwitchScene.switchScene("Snake", primaryStage);
        });

        Image game2Image = new Image("https://www.cokogames.com/wp-content/uploads/2022/11/sequence-memory-game.jpg");
        ImageView game2ImageView = new ImageView(game2Image);
        game2ImageView.setPickOnBounds(true); 
        game2ImageView.setFitWidth(200);
        game2ImageView.setFitHeight(200);
        game2ImageView.setOnMouseClicked(event -> {
            // Handle click event for game 2
            System.out.println("Game 2 clicked");
            SwitchScene.switchScene("Memory", primaryStage);
        });

        Image game3Image = new Image("https://play-lh.googleusercontent.com/muD11u7sK-i75V-zq7FAapJ5gOUjRt2CXxssGwz-FsjkjCVCHcrMquIUMroLPVEOAy0");
        ImageView game3ImageView = new ImageView(game3Image);
        game3ImageView.setPickOnBounds(true); 
        game3ImageView.setFitWidth(200);
        game3ImageView.setFitHeight(200);
        game3ImageView.setOnMouseClicked(event -> {
            // Handle click event for game 3
            System.out.println("Game 3 clicked");
            SwitchScene.switchScene("Brick Breaker", primaryStage);
        });

        Image game4Image = new Image("https://media.istockphoto.com/id/1153033854/vector/a-game-to-hit-the-mole.jpg?s=612x612&w=0&k=20&c=Zugmk3xO6JASmNN4sNwpaekrstBXjUA2fdIAJRi3v5Y=");
        ImageView game4ImageView = new ImageView(game4Image);
        game4ImageView.setPickOnBounds(true); 
        game4ImageView.setFitWidth(200);
        game4ImageView.setFitHeight(200);
        game4ImageView.setOnMouseClicked(event -> {
            // Handle click event for game 4
            System.out.println("Game 4 clicked");
            SwitchScene.switchScene("WhackAMole", primaryStage);
        });
        
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        String[] options = { "Home", "Bookshelf", "Games" };
        ComboBox<String> pageSelection = new ComboBox<String>();
        pageSelection.getItems().addAll(options);
        pageSelection.setValue(options[2]);
        pageSelection.setStyle("-fx-font-size: 24px; -fx-text-fill: #00008B;");

        pageSelection.setOnAction(e -> {
            String selectedOption = pageSelection.getValue();
            System.out.println("Selected Option: " + selectedOption);
            SwitchScene.switchScene(selectedOption, primaryStage);
        });
        
        // Creating game titles
        Label game1Label = new Label("Snake");
        Label game2Label = new Label("Memory Game");
        Label game3Label = new Label("Brick Breaker");
        Label game4Label = new Label("Wack-A-Mole");

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

        FlowPane gameImagePane = new FlowPane(20, 20);
        gameImagePane.getChildren().addAll(game1Box, game2Box, game3Box, game4Box);
        gameImagePane.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(gameImagePane);

        // Create an ImageView with the specified image file
        Image logo = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
        ImageView imageView = new ImageView(logo);
        imageView.setPickOnBounds(true);
        imageView.setFitWidth(400); // Set desired width
        imageView.setFitHeight(200); // Set desired height
        imageView.setOnMouseClicked(e -> {
            getHostServices().showDocument("https://renaissancepsa.com/");
        });


        VBox logoBox = new VBox(10, imageView);
        logoBox.setAlignment(Pos.TOP_CENTER);

        
        GridPane topGridPane = new GridPane();
        topGridPane.setHgap(10);
        topGridPane.add(logoBox, 1, 0);
        topGridPane.setAlignment(Pos.CENTER);

        // Setting up the scene
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(imageView);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);
        borderPane.setCenter(gameImagePane);
        BorderPane.setAlignment(gameImagePane, Pos.CENTER);
        BorderPane.setMargin(gameImagePane, new Insets(0,250,0,0));
        borderPane.setLeft(pageSelection);
        BorderPane.setMargin(pageSelection, new Insets(0,0,0,20));
        
        Scene scene = new Scene(borderPane);
        primaryStage.setTitle("Game Home Page");
        primaryStage.setScene(scene);
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    /*
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
    */
}