import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class Bookshelf extends Application {
	private final Button[] finalButtons = { new Button("Home"), new Button("Collections"), new Button("Search") };

	@Override
	public void start(Stage stage) {

		Scanner sc = new Scanner(System.in);
		GridPane gridpane = new GridPane();

		TextField searchField = new TextField();
		searchField.setMaxSize(Double.MAX_VALUE, 42); // Allow textfield to resize
		searchField.setPrefHeight(30); // Set preferred height
		GridPane.setMargin(searchField, new Insets(0)); // Remove space between the textfield and buttons
		GridPane.setColumnSpan(searchField, 4); // Span over four columns

		Image logo = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
        ImageView ivLogo = new ImageView(logo);
        ivLogo.setPickOnBounds(true); // Make ImageView contain entire image, not just the geometrical shape

        // Create EventHandler for clicking on image
        ivLogo.setOnMouseClicked(e -> {
            getHostServices().showDocument("https://renaissancepsa.com/");
        });

		gridpane.add(ivLogo, 3, 0);
		
		String[] bookTitles = { "To Kill a Mockingbird", "1984", "The Great Gatsby", "Pride and Prejudice",
				"The Catcher in the Rye", "The Hobbit", "Fahrenheit 451", "The Lord of the Rings", "Jane Eyre",
				"Animal Farm", "Brave New World", "The Diary of a Young Girl", "The Grapes of Wrath", "Moby-Dick",
				"The Odyssey", "Wuthering Heights", "Frankenstein", "The Adventures of Huckleberry Finn",
				"The Count of Monte Cristo", "Crime and Punishment", "One Hundred Years of Solitude",
				"The Picture of Dorian Gray", "Gone with the Wind", "Les Misérables", "The Brothers Karamazov",
				"Anna Karenina", "Great Expectations", "War and Peace", "The Scarlet Letter", "Dracula", "Don Quixote",
				"The Divine Comedy", "The Iliad", "The Bell Jar", "A Tale of Two Cities",
				"The Adventures of Sherlock Holmes", "The Canterbury Tales", "Alice's Adventures in Wonderland",
				"The Old Man and the Sea", "A Clockwork Orange", "The Sun Also Rises", "East of Eden",
				"The Sound and the Fury", "The Stranger", "The Metamorphosis", "Heart of Darkness",
				"Slaughterhouse-Five", "Beloved", "The Wind-Up Bird Chronicle", "The Handmaid's Tale", "The Road",
				"The Name of the Rose", "Maus", "The Martian Chronicles", "The Stand", "The Grapes of Wrath",
				"One Flew Over the Cuckoo's Nest", "The Color Purple", "Catch-22", "Lord of the Flies",
				"The Hitchhiker's Guide to the Galaxy", "The Joy Luck Club", "A Thousand Splendid Suns",
				"The Alchemist", "The Kite Runner", "The Help", "Life of Pi", "The Hunger Games",
				"The Girl with the Dragon Tattoo", "The Girl on the Train", "The Da Vinci Code",
				"Harry Potter and the Philosopher's Stone", "The Twilight Saga", "The Fault in Our Stars", "Divergent",
				"The Chronicles of Narnia", "The Maze Runner", "The Lord of the Rings", "The Hobbit",
				"A Song of Ice and Fire", "The Hunger Games", "The Wheel of Time", "The Witcher",
				"Harry Potter and the Sorcerer's Stone", "The Twilight Saga", "The Chronicles of Narnia",
				"The Inheritance Cycle", "Percy Jackson & the Olympians", "The Chronicles of Prydain",
				"The Earthsea Cycle", "The Dark Tower", "His Dark Materials", "The Hunger Games", "The Kane Chronicles",
				"The Chronicles of Narnia", "The Grisha Trilogy", "The Hitchhiker's Guide to the Galaxy",
				"The Bartimaeus Trilogy", "The Chronicles of Prydain", "The Witcher", "The Inheritance Cycle",
				"The Chronicles of Narnia", "The Earthsea Cycle", "Percy Jackson & the Olympians",
				"The Lord of the Rings", "The Maze Runner", "The Chronicles of Prydain", "The Kane Chronicles",
				"The Dark Tower" };

		// int totalBooks = sc.nextInt();
		int totalBooks = 60;

		buttonGeneration(bookTitles, gridpane);

		gridpane.setAlignment(Pos.CENTER);
		// gridpane.getChildren().add(searchField);
		gridpane.add(searchField, 2, 4); // Add to column 2

		// Apply resizing properties to final buttons
		for (Button button : finalButtons) {
			// button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			GridPane.setFillHeight(button, true); // Allow button to fill height
			GridPane.setFillWidth(button, true); // Allow button to fill width
			button.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-font-family: Arial;");
		}

		// [array], column, row

		gridpane.add(finalButtons[0], 0, 4); // homeButton
		gridpane.add(finalButtons[1], 7, 4); // collectionsButton
		gridpane.add(finalButtons[2], 6, 4); // searchButton

		// Define column constraints
		for (int i = 0; i < 7; i++) { // allows movement of i < " " columns
			ColumnConstraints columnConstraints = new ColumnConstraints();
			columnConstraints.setHgrow(Priority.ALWAYS);
			gridpane.getColumnConstraints().add(columnConstraints);
		}

		// Define row constraints
		for (int i = 0; i < bookTitles.length / 6; i++) {
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setVgrow(Priority.ALWAYS);
			gridpane.getRowConstraints().add(rowConstraints);
		}

		// allows user to scroll throughout book choices
		ScrollPane scrollPane = new ScrollPane(gridpane);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		gridpane.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");

		Scene scene = new Scene(scrollPane, 1000, 1000);
		stage.setScene(scene);
		stage.setTitle("Renaissance Public School Academy Library: Book Collection");
		stage.show();
		stage.centerOnScreen();
	}

	public static void buttonGeneration(String[] bookTitles, GridPane gridpane) {
		int col = 1;
		int row = 5;
		int colNext = 1; // Moves the cycle ahead to the next column

		for (int i = 0; i < bookTitles.length; i++) {
			int temp = i;
			Button button = new Button(bookTitles[i]);
			button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Allow button to resize
			button.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: Arial;"); // Set font
			button.wrapTextProperty().setValue(true);
			button.setPrefWidth(100);

			button.setMinSize(100, 100);
			button.setMaxSize(100, 100);
			button.setPrefSize(400, 400);// specifications
			if (i == 1 + ((bookTitles.length / 6) * colNext)) {
				col++;
				row = 5;
				colNext++;
			}
			gridpane.add(button, col, row);
			GridPane.setMargin(button, new Insets(20));
			GridPane.setFillHeight(button, true); // Allow button to fill height
			GridPane.setFillWidth(button, true); // Allow button to fill width
			i = temp;
			row++;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
