import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HomePage extends Application {

	public void start(Stage stage) {
		// Innit parts
		Text text = new Text("Renaissane Public School Academy\n      5th & 6th Grade Hall Library");
		Button login, bookshelf;
		
		// Book image for collections button
		Image book = new Image("https://png.pngtree.com/png-vector/20230318/ourmid/pngtree-book-clipart-vector-png-image_6653535.png");
		ImageView ivBook = new ImageView(book);
		ivBook.setFitWidth(50);
		ivBook.setFitHeight(50);
		ivBook.setPreserveRatio(true);
		
		// Renaissance clickable logo
		Image logo = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
		ImageView ivLogo = new ImageView(logo);
		ivLogo.setPickOnBounds(true); // Make ImageView contain entire image, not just the geometrical shape

		// Create EventHandler for clicking on image
		ivLogo.setOnMouseClicked(e -> {
			getHostServices().showDocument("https://renaissancepsa.com/");
		});

		// Adjust text
		text.setFont(Font.font("Arial Black", FontWeight.BOLD, 60));
		text.setFill(Color.DARKBLUE);

		// Adjust buttons
		login = new Button("Login");
		login.setFont(Font.font("Arial Black", FontWeight.BOLD, 60));
		login.setOnAction(e -> {
			System.out.println("Login Button Clicked!");
		});
		
		bookshelf = new Button(); // Create a button without text
        bookshelf.setOnAction(e -> {
            System.out.println("Bookshelf Button Clicked!");
        });
		
		// Create HBox & Button for Bookshelf button to have text & image
		Text bookshelfText = new Text("Collections");
		bookshelfText.setFont(Font.font("Arial Black", FontWeight.BOLD, 30));
		
		HBox bookshelfContent = new HBox(10);
		bookshelfContent.getChildren().addAll(bookshelfText, ivBook);
		
		bookshelf.setGraphic(bookshelfContent);

		// Create Panes
		VBox vbTop = new VBox(10);
		vbTop.setAlignment(Pos.CENTER);
		vbTop.getChildren().addAll(text, ivLogo);
		
		BorderPane root = new BorderPane();
		root.setTop(vbTop);
		BorderPane.setAlignment(vbTop, Pos.TOP_CENTER);
		root.setCenter(login);
		BorderPane.setMargin(login, new Insets(0,0,0,-300));
		root.setLeft(bookshelf);
		BorderPane.setMargin(bookshelf, new Insets(20,20,0,20));
		
		// Get primary screen & bounds of the screen for size of scene
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		
		
		// Create a scene and place it in the stage
		Scene scene = new Scene(root);
		stage.setTitle("Renaissance Public School Academy Library Home Page"); // Set the stage title
		stage.setScene(scene); // Place the scene in the stage
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
		stage.show(); // Display the stage
		
		// Add easter eggs 
		// Button that just increases counter of button 
		// Click a button too many times, close the page
	}

	public static void main(String[] args) {
		launch(args);

	}

}
