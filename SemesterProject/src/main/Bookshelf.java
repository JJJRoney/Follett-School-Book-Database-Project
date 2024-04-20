import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class Bookshelf extends Application {
    private Button searchButton = new Button("Search");

    @Override
    public void start(Stage stage) {
        GridPane gridpane = new GridPane();

        TextField searchField = new TextField();
        searchField.setMaxSize(Double.MAX_VALUE, 42);
        searchField.setPrefHeight(30);
        GridPane.setMargin(searchField, new Insets(0));
        GridPane.setColumnSpan(searchField, 4);

        Image logo = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
        ImageView ivLogo = new ImageView(logo);
        ivLogo.setPickOnBounds(true);

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

	        buttonGeneration(bookTitles, gridpane, stage);

	        gridpane.setAlignment(Pos.CENTER);
	        gridpane.add(searchField, 2, 4);

	        GridPane.setFillHeight(searchButton, true);
	        GridPane.setFillWidth(searchButton, true);
	        searchButton.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-font-family: Arial;");

	        String[] options = { "Home", "Bookshelf", "Games" };
	        ComboBox<String> pageSelection = new ComboBox<String>();
	        pageSelection.getItems().addAll(options);
	        pageSelection.setValue(options[1]);
	        pageSelection.setStyle("-fx-font-size: 24px; -fx-text-fill: #00008B;");

	        pageSelection.setOnAction(e -> {
	            String selectedOption = pageSelection.getValue();
	            System.out.println("Selected Option: " + selectedOption);
	            SwitchScene.switchScene(selectedOption, stage);
	        });

	        gridpane.add(pageSelection, 0, 4);
	        gridpane.add(searchButton, 6, 4);

	        for (int i = 0; i < 7; i++) {
	            ColumnConstraints columnConstraints = new ColumnConstraints();
	            columnConstraints.setHgrow(Priority.ALWAYS);
	            gridpane.getColumnConstraints().add(columnConstraints);
	        }

	        for (int i = 0; i < bookTitles.length / 6; i++) {
	            RowConstraints rowConstraints = new RowConstraints();
	            rowConstraints.setVgrow(Priority.ALWAYS);
	            gridpane.getRowConstraints().add(rowConstraints);
	        }

	        ScrollPane scrollPane = new ScrollPane(gridpane);
	        scrollPane.setFitToWidth(true);
	        scrollPane.setFitToHeight(true);
	        gridpane.setStyle("-fx-background-color: rgba(0, 0, 0, 0)");

	        Screen screen = Screen.getPrimary();
	        Rectangle2D bounds = screen.getVisualBounds();

	        Scene scene = new Scene(scrollPane);
	        stage.setScene(scene);
	        stage.setTitle("Renaissance Public School Academy Library: Book Collection");
	        stage.setWidth(bounds.getWidth());
	        stage.setHeight(bounds.getHeight());
	        stage.show();
	        stage.centerOnScreen();
	    }

	    public static void buttonGeneration(String[] bookTitles, GridPane gridpane, Stage stage) {
	        int col = 1;
	        int row = 5;
	        int colNext = 1;

	        for (int i = 0; i < bookTitles.length; i++) {
	            int temp = i;
	            Button button = new Button(bookTitles[i]);
	            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	            button.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: Arial;");
	            button.wrapTextProperty().setValue(true);
	            button.setPrefWidth(100);
	            button.setMinSize(100, 100);
	            button.setMaxSize(100, 100);
	            button.setPrefSize(400, 400);

	            button.setOnAction(e -> {
	                openMoreDetail(stage, bookTitles[temp]);
	            });

	            if (i == 1 + ((bookTitles.length / 6) * colNext)) {
	                col++;
	                row = 5;
	                colNext++;
	            }
	            gridpane.add(button, col, row);
	            GridPane.setMargin(button, new Insets(20));
	            GridPane.setFillHeight(button, true);
	            GridPane.setFillWidth(button, true);
	            i = temp;
	            row++;
	        }
	    }

	    public static void openMoreDetail(Stage stage, String bookTitle) {
	        MoreDetail moreDetail = new MoreDetail(bookTitle);
	        moreDetail.start(stage);
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
	}
