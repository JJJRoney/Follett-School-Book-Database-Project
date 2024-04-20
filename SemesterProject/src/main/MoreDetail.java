import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoreDetail extends Application {
    private Label titleLabel;
    private Label authorLabel;
    private Label authorNameLabel;
    private Label pageLabel;
    private Label pageAmountLabel;
    private Label genreLabel;
    private Label genreOfBookLabel;
    private Label copiesAmountLabel;
    private Label copiesAmountValueLabel;
    private Button bookshelfButton;
    private StackPane imagePanel;

    private String bookTitle;
    private String authorName;
    private String pageAmount;
    private String genreOfBook;
    private String copiesAmount;
	private Stage primaryStage;
    
    public MoreDetail() {
        // Default values
        this.bookTitle = "Unknown";
        this.authorName = "Unknown Author";
        this.pageAmount = "0";
        this.genreOfBook = "Unknown";
        this.copiesAmount = "0";
    }

    

    public MoreDetail(String bookTitle, Stage primaryStage) {
        this.bookTitle = bookTitle;
        this.primaryStage = primaryStage;
        fetchBookDetails(bookTitle);
    }
    private void fetchBookDetails(String title) {
    	
        String sql = "SELECT Title, FirstName, LastName, Pages, Genre, Copies FROM books WHERE Title = ?";
        try (Connection conn = Connect.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    authorName = rs.getString("FirstName") + " " + rs.getString("LastName");
                    pageAmount = rs.getString("Pages");
                    genreOfBook = rs.getString("Genre");
                    copiesAmount = rs.getString("Copies");
                    System.out.println("Data loaded successfully.");
                } else {
                    System.out.println("No data found for title: " + title);
                    authorName = "Data not found";
                    pageAmount = "N/A";
                    genreOfBook = "N/A";
                    copiesAmount = "N/A";
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching book details: " + e.getMessage());
            authorName = "Error fetching data";
            pageAmount = "Error";
            genreOfBook = "Error";
            copiesAmount = "Error";
        }
    }




    @Override
    public void start(Stage primaryStage) {
    	
    	
        // Initialize components
        titleLabel = new Label("Book Title: " + bookTitle);
        authorLabel = new Label("Author: ");
        authorNameLabel = new Label(authorName);
        pageLabel = new Label("Page #: ");
        pageAmountLabel = new Label(pageAmount);
        genreLabel = new Label("Genre: ");
        genreOfBookLabel = new Label(genreOfBook);
        copiesAmountLabel = new Label("Copies Amount: ");
        copiesAmountValueLabel = new Label(copiesAmount);
        bookshelfButton = new Button("Bookshelf");
        imagePanel = new StackPane();

        // Initialize ComboBox
        String[] options = { "Home", "Bookshelf", "Games" };
        ComboBox<String> pageSelection = new ComboBox<String>();
        pageSelection.getItems().addAll(options);
        pageSelection.setValue("More Details");
        pageSelection.setStyle("-fx-font-size: 24px; -fx-text-fill: #00008B;");

        pageSelection.setOnAction(e -> {
            String selectedOption = pageSelection.getValue();
            System.out.println("Selected Option: " + selectedOption);
            SwitchScene.switchScene(selectedOption, primaryStage);
        });

        // Set font size and color for labels and buttons
        String font = "Arial";
        int fontSize = 36;
        titleLabel.setFont(new javafx.scene.text.Font(font, fontSize));
        authorLabel.setFont(new javafx.scene.text.Font(font, fontSize));
        authorNameLabel.setFont(new javafx.scene.text.Font(font, fontSize));
        pageLabel.setFont(new javafx.scene.text.Font(font, fontSize));
        pageAmountLabel.setFont(new javafx.scene.text.Font(font, fontSize));
        genreLabel.setFont(new javafx.scene.text.Font(font, fontSize));
        genreOfBookLabel.setFont(new javafx.scene.text.Font(font, fontSize));
        copiesAmountLabel.setFont(new javafx.scene.text.Font(font, fontSize));
        copiesAmountValueLabel.setFont(new javafx.scene.text.Font(font, fontSize));
        bookshelfButton.setFont(new javafx.scene.text.Font(font, fontSize));

        // Set text color to dark blue for all components
        String darkBlueColor = "-fx-text-fill: #00008B;";
        titleLabel.setStyle(darkBlueColor);
        authorLabel.setStyle(darkBlueColor);
        authorNameLabel.setStyle(darkBlueColor);
        pageLabel.setStyle(darkBlueColor);
        pageAmountLabel.setStyle(darkBlueColor);
        genreLabel.setStyle(darkBlueColor);
        genreOfBookLabel.setStyle(darkBlueColor);
        copiesAmountLabel.setStyle(darkBlueColor);
        copiesAmountValueLabel.setStyle(darkBlueColor);
        bookshelfButton.setStyle(darkBlueColor);

        // Add components to the top section
        VBox topPanel = new VBox(20);
        topPanel.setAlignment(Pos.CENTER);
        topPanel.getChildren().addAll(imagePanel);

        // Add Bookshelf button to the top left
        HBox leftSection = new HBox(pageSelection);
        leftSection.setAlignment(Pos.TOP_LEFT);
        leftSection.setPadding(new Insets(10));

        // Add sections to the top panel
        topPanel.getChildren().addAll(leftSection);

        // Load and add logo image
        try {
            Image logoImage = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
            ImageView logoView = new ImageView(logoImage);
            logoView.setPreserveRatio(true);
            logoView.setPickOnBounds(true);
            logoView.setFitWidth(200);
            imagePanel.getChildren().add(logoView);
            logoView.setOnMouseClicked(this::openLink);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add components to the center section
        VBox centerPanel = new VBox(20);
        centerPanel.setAlignment(Pos.CENTER);
        centerPanel.getChildren().addAll(
                titleLabel,
                new HBox(20, authorLabel, authorNameLabel),
                new HBox(20, pageLabel, pageAmountLabel),
                new HBox(20, genreLabel, genreOfBookLabel),
                new HBox(20, copiesAmountLabel, copiesAmountValueLabel)
        );

        // Add a delete button
        Button deleteButton = new Button("Delete");
        deleteButton.setFont(new javafx.scene.text.Font("Arial", 36));
        deleteButton.setStyle("-fx-text-fill: #FF0000;"); // Red text to highlight it's a delete button
        deleteButton.setOnAction(e -> confirmAndDeleteBook());

        HBox bottomRightBox = new HBox();
        bottomRightBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomRightBox.setPadding(new Insets(15));
        bottomRightBox.getChildren().add(deleteButton);
        centerPanel.getChildren().add(bottomRightBox);


        // Set background color to light gray for the center panel
        centerPanel.setStyle("-fx-background-color: #F0F0F0;");
        leftSection.setStyle("-fx-background-color: #F0F0F0;");
        topPanel.setStyle("-fx-background-color: #F0F0F0;");

        // Create layout
        BorderPane root = new BorderPane();
        root.setTop(topPanel);
        root.setCenter(centerPanel);
        Scene scene = new Scene(root, 800, 600);

        // Set stage properties
        primaryStage.setScene(scene);
        primaryStage.setTitle("MoreDetail");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void confirmAndDeleteBook() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this book: " + bookTitle + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                deleteBook();
            }
        });
    }

    private void deleteBook() {
        String sql = "DELETE FROM books WHERE Title = ?";
        try (Connection conn = Connect.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookTitle);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Book deleted successfully.");
                switchToBookshelf(); // Switch back to the bookshelf view
            } else {
                System.out.println("No book was deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }

    private void switchToBookshelf() {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.start(primaryStage); // Assuming Bookshelf has a start method that can be called to initialize it
    }
    private void openLink(MouseEvent event) {
        getHostServices().showDocument("https://renaissancepsa.com/");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
