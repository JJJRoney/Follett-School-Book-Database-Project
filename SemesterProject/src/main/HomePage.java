 import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HomePage extends Application {
	private Text text;
	private Button login;
	private ComboBox<String> pageSelection;
	private Image book, logo;
	private ImageView ivBook, ivLogo;
	private VBox vbTop;
	private BorderPane root;
	private Screen screen;
	private Rectangle2D bounds;
	private Scene homeScene;

	public void start(Stage homeStage) {
		// Innitialize title
		text = new Text("Renaissane Public School Academy\n      5th & 6th Grade Hall Library");
		text.setFont(Font.font("Arial Black", FontWeight.BOLD, 60));
		text.setFill(Color.DARKBLUE);

		// Get primary screen & bounds of the screen for size of scene
		screen = Screen.getPrimary();
		bounds = screen.getVisualBounds();

		// Book image for collections button
		book = new Image(
				"https://png.pngtree.com/png-vector/20230318/ourmid/pngtree-book-clipart-vector-png-image_6653535.png");
		ivBook = new ImageView(book);
		ivBook.setFitWidth(200);
		ivBook.setFitHeight(100);
		ivBook.setPreserveRatio(true);

		// Renaissance clickable logo
		logo = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
		ivLogo = new ImageView(logo);
		ivLogo.setPickOnBounds(true); // Make ImageView contain entire image, not just the geometrical shape

		// Create EventHandler for clicking on image
		ivLogo.setOnMouseClicked(e -> {
			getHostServices().showDocument("https://renaissancepsa.com/");
		});

		// Adjust buttons
		login = new Button("Login");
		login.setFont(Font.font("Arial Black", FontWeight.BOLD, 60));
		login.setOnAction(e -> {
			System.out.println("Login pressed");
			Stage loginStage = new Stage();
			LoginPage loginPage = new LoginPage();
			loginPage.start(loginStage);
			homeStage.close();
		});

		// Innitialize ComboBox
		String[] options = { "Home", "Bookshelf", "Games" };
		pageSelection = new ComboBox<String>();
		pageSelection.getItems().addAll(options);
		pageSelection.setValue(options[0]);
		pageSelection.setStyle("-fx-font-size: 24px; -fx-text-fill: #00008B;");

		// Add event listener to ComboBox
		pageSelection.setOnAction(e -> {
			String selectedOption = pageSelection.getValue();
			System.out.println("Selected Option: " + selectedOption);
			Stage stage = (Stage) root.getScene().getWindow();
			SwitchScene.switchScene(selectedOption, stage);
		});

		// Create Panes
		vbTop = new VBox(10);
		vbTop.setAlignment(Pos.CENTER);
		vbTop.getChildren().addAll(text, ivLogo);

		root = new BorderPane();
		root.setTop(vbTop);
		BorderPane.setAlignment(vbTop, Pos.TOP_CENTER);
		root.setCenter(login);
		BorderPane.setMargin(login, new Insets(0, 0, 0, -300));
		root.setLeft(pageSelection);
		BorderPane.setMargin(pageSelection, new Insets(20, 20, 0, 20));

		// Create a scene and place it in the stage
		homeScene = new Scene(root);
		homeStage.setTitle("Renaissance Public School Academy Library Home Page"); // Set the stage title
		homeStage.setScene(homeScene); // Place the scene in the stage
		homeStage.setWidth(bounds.getWidth());
		homeStage.setHeight(bounds.getHeight());
		homeStage.show(); // Display the stage
		homeStage.centerOnScreen();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
