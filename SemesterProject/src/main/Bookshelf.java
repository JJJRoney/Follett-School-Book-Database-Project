import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bookshelf extends Application {
    private Button searchButton = new Button("Search");
    private TextField searchField = new TextField();
    private List<Button> bookButtons = new ArrayList<>(); // List to keep track of all buttons
    private GridPane gridpane = new GridPane();
    private int maxCols = 6;

 // Add the Add Book button to the layout
    private void setupAddBookButton(Stage stage) {
        Button addBookButton = new Button("Add Book");
        addBookButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        addBookButton.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-font-family: Arial;");
        gridpane.add(addBookButton, 6, 1); // Adding it to the top right corner, adjust the indices as per your layout
        addBookButton.setOnAction(e -> openAddBookDialog(stage));
    }

    private void openAddBookDialog(Stage parentStage) {
        Stage dialog = new Stage();
        dialog.initOwner(parentStage);
        dialog.setTitle("Add New Book");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        TextField titleField = new TextField();
        titleField.setPromptText("Book Title");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Author's First Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Author's Last Name");
        TextField pagesField = new TextField();
        pagesField.setPromptText("Number of Pages");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            saveBook(titleField.getText(), firstNameField.getText(), lastNameField.getText(), pagesField.getText());
            dialog.close();
        });

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("First Name:"), 0, 1);
        grid.add(firstNameField, 1, 1);
        grid.add(new Label("Last Name:"), 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(new Label("Pages:"), 0, 3);
        grid.add(pagesField, 1, 3);
        grid.add(saveButton, 1, 4);

        Scene scene = new Scene(grid);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    
    public void saveBook(String title, String firstName, String lastName, String pages) {
        String sql = "INSERT INTO books (Title, First, Last) VALUES (?, ?, ?)";
        try (Connection conn = Connect.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            //pstmt.setInt(4, Integer.parseInt(pages));
            pstmt.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }


    @Override
    public void start(Stage stage) {
        //GridPane gridpane = new GridPane();
        List<String> bookTitles = null;
        setupAddBookButton(stage);

        try {
            bookTitles = Connect.selectAndFormatAllTitles(); // Fetch the titles
        } catch (Exception e) {
            System.out.println("Error fetching book titles: " + e.getMessage());
            return; // Stop further execution if titles can't be fetched
        }

       // TextField searchField = new TextField();
        searchField.setMaxSize(Double.MAX_VALUE, 42);
        searchField.setPrefHeight(30);
        GridPane.setMargin(searchField, new Insets(0));
        GridPane.setColumnSpan(searchField, 4);

        Image logo = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
        ImageView ivLogo = new ImageView(logo);
        ivLogo.setPickOnBounds(true);
        ivLogo.setOnMouseClicked(e -> getHostServices().showDocument("https://renaissancepsa.com/"));
        gridpane.add(ivLogo, 3, 0);

        if (bookTitles != null) {
            buttonGeneration(bookTitles.toArray(new String[0]), gridpane, stage);
        }

        gridpane.setAlignment(Pos.CENTER);
        gridpane.add(searchField, 2, 4);

        GridPane.setFillHeight(searchButton, true);
        GridPane.setFillWidth(searchButton, true);
        searchButton.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-font-family: Arial;");

        String[] options = { "Home", "Bookshelf", "Games" };
        ComboBox<String> pageSelection = new ComboBox<>();
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

        for (int i = 0; i < bookTitles.size() / 6; i++) {
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
        
        searchButton.setOnAction(e -> performSearch());

        Scene scene = new Scene(scrollPane);
        stage.setScene(scene);
        stage.setTitle("Renaissance Public School Academy Library: Book Collection");
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.show();
        stage.centerOnScreen();
    }

    private void performSearch() {
        String searchText = searchField.getText().toLowerCase();
        int col = 1;
        int row = 5;

        // If the search text is empty, reset positions for all buttons to be visible in their original order
        if (searchText.isBlank()) {
            for (Button button : bookButtons) {
                button.setVisible(true);
                gridpane.getChildren().remove(button);  // Remove and re-add to ensure order is maintained
                gridpane.add(button, col, row);
                row++;
                if (row > 10) {  // Adjust grid position dynamically
                    row = 5;
                    col++;
                    if (col > maxCols) col = 1;
                }
            }
        } else {
            for (Button button : bookButtons) {
                if (button.getText().toLowerCase().contains(searchText)) {
                    button.setVisible(true);
                    gridpane.getChildren().remove(button); // Remove the button from its current position
                    gridpane.add(button, col, row); // Add it at the next available position
                    row++;
                    if (row > 10) { // If the row limit per column is exceeded, move to the next column
                        col++;
                        row = 5;
                        if (col > maxCols) col = 1; // Reset columns if max is reached
                    }
                } else {
                    button.setVisible(false); // Hide buttons that do not match
                }
            }
        }
        ensureUIComponents(); // Ensure search field and other static components remain in place
    }

    private void ensureUIComponents() {
        if (!gridpane.getChildren().contains(searchField)) {
            gridpane.add(searchField, 2, 4);  // Re-add search field if not present
        }
        if (!gridpane.getChildren().contains(searchButton)) {
            gridpane.add(searchButton, 6, 4);  // Re-add search button if not present
        }
    }

    
    private void buttonGeneration(String[] bookTitles, GridPane gridpane, Stage stage) {
        int col = 1;
        int row = 5;

        for (int i = 0; i < bookTitles.length; i++) {
            Button button = new Button(bookTitles[i]);
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            button.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-font-family: Arial;");
            button.setPrefSize(400, 400);
            bookButtons.add(button); // Store button for later reference

            // Set an action on the button to open the MoreDetail window
            final String bookTitle = bookTitles[i]; // Capture book title in a final variable for use in lambda expression
            button.setOnAction(e -> {
                MoreDetail moreDetail = new MoreDetail(bookTitle, stage);
                moreDetail.start(stage); // Start MoreDetail stage
            });

            if ((i + 1) % maxCols == 0) {
                col = 1;
                row++;
            } else {
                col++;
            }

            gridpane.add(button, col, row);
            GridPane.setMargin(button, new Insets(20));
        }
    }

    
    public static void openMoreDetail(Stage stage, String bookTitle) {
        MoreDetail moreDetail = new MoreDetail(bookTitle, stage);
        moreDetail.start(stage);
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}

