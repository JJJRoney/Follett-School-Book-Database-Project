import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MoreDetail extends Application {
    // Declare GUI components
    private Label titleLabel;
    private Label authorLabel;
    private Label authorNameLabel;
    private Label pageLabel;
    private Label pageAmountLabel;
    private Label genreLabel;
    private Label genreOfBookLabel;
    private Label copiesAmountLabel; // Changed label name
    private Label copiesAmountValueLabel; // Changed variable name
    private Button bookshelfButton; // Changed button name
    private ComboBox<String> dropDownButton; // Drop-down button
    private StackPane imagePanel; // StackPane to center the image

    // Declare variables
    private String bookTitle;
    private String authorName;
    private String pageAmount;
    private String genreOfBook;
    private String copiesAmount; // Changed variable name

    @Override
    public void start(Stage primaryStage) {
        // Initialize components
        titleLabel = new Label("Book Title: ");
        authorLabel = new Label("Author: ");
        authorNameLabel = new Label();
        pageLabel = new Label("Page #: ");
        pageAmountLabel = new Label();
        genreLabel = new Label("Genre: ");
        genreOfBookLabel = new Label();
        copiesAmountLabel = new Label("Copies Amount: "); // Changed label name
        copiesAmountValueLabel = new Label(); // Changed variable name
        bookshelfButton = new Button("Bookshelf"); // Changed button name
        String[] options = {"Home", "Bookshelf", "Login", "Games"}; // Changed drop-down options order
        dropDownButton = new ComboBox<>();
        dropDownButton.getItems().addAll(options);
        dropDownButton.setValue(options[0]); // Set initial value
        dropDownButton.setStyle("-fx-font-size: 24px; -fx-text-fill: #00008B;"); // Set font size and color for drop-down button
        imagePanel = new StackPane(); // Use StackPane to center the image

        // Set default values
        bookTitle = "Unknown";
        authorName = "Unknown";
        pageAmount = "0";
        genreOfBook = "Unknown";
        copiesAmount = "0"; // Changed default value

        // Update the title label with the current book title
        updateTitleLabel();

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
        copiesAmountLabel.setFont(new javafx.scene.text.Font(font, fontSize)); // Changed label font
        copiesAmountValueLabel.setFont(new javafx.scene.text.Font(font, fontSize)); // Changed variable font
        bookshelfButton.setFont(new javafx.scene.text.Font(font, fontSize)); // Changed button font

        // Set text color to dark blue for all components
        String darkBlueColor = "-fx-text-fill: #00008B;";
        titleLabel.setStyle(darkBlueColor);
        authorLabel.setStyle(darkBlueColor);
        authorNameLabel.setStyle(darkBlueColor);
        pageLabel.setStyle(darkBlueColor);
        pageAmountLabel.setStyle(darkBlueColor);
        genreLabel.setStyle(darkBlueColor);
        genreOfBookLabel.setStyle(darkBlueColor);
        copiesAmountLabel.setStyle(darkBlueColor); // Changed label color
        copiesAmountValueLabel.setStyle(darkBlueColor); // Changed variable color
        bookshelfButton.setStyle(darkBlueColor); // Changed button color

        // Add components to the top section
        VBox topPanel = new VBox(20);
        topPanel.setAlignment(Pos.CENTER);
        topPanel.getChildren().addAll(imagePanel); // Changed button position

        // Add Drop-down button to the top right
        HBox rightSection = new HBox(dropDownButton);
        rightSection.setAlignment(Pos.TOP_RIGHT); // Align the drop-down button to the top
        rightSection.setPadding(new Insets(10));
        rightSection.setSpacing(20); // Add some spacing between components
        
     // Add Bookshelf button to the top left
        HBox leftSection = new HBox(bookshelfButton);
        leftSection.setAlignment(Pos.TOP_LEFT);
        leftSection.setPadding(new Insets(10));

        // Set the size of the drop-down button
        dropDownButton.setPrefWidth(300); // Adjust button width
        dropDownButton.setPrefHeight(60); // Adjust button height
        
        // Add sections to the top panel
        topPanel.getChildren().addAll(leftSection, rightSection);

        // Load and add logo image
        try {
            Image logoImage = new Image("https://renaissancepsa.com/wp-content/uploads/2017/08/rams_logo.png");
            ImageView logoView = new ImageView(logoImage);
            logoView.setPreserveRatio(true);
            logoView.setFitWidth(200); // Adjust image size as needed
            imagePanel.getChildren().add(logoView);
            logoView.setOnMouseClicked(this::openLink); // Set click event handler for the image
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Add components to the center section
        VBox centerPanel = new VBox(20); // Changed layout to VBox
        centerPanel.setAlignment(Pos.CENTER); // Set alignment for the whole VBox
        centerPanel.getChildren().addAll(
                // Labels aligned to the center
                titleLabel,
                // Labels aligned to the center left
                new HBox(20, authorLabel, authorNameLabel),
                new HBox(20, pageLabel, pageAmountLabel),
                new HBox(20, genreLabel, genreOfBookLabel),
                new HBox(20, copiesAmountLabel, copiesAmountValueLabel) // Changed label name
        );
        
        // Add delete button to the bottom right
        Button deleteButton = new Button("Delete");
        deleteButton.setVisible(false); // Initially hidden
        HBox bottomRightBox = new HBox();
        bottomRightBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomRightBox.getChildren().add(deleteButton);
        centerPanel.getChildren().add(bottomRightBox);

        // Set background color to light gray for the center panel
        centerPanel.setStyle("-fx-background-color: #F0F0F0;");
        rightSection.setStyle("-fx-background-color: #F0F0F0;");
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
        primaryStage.setMaximized(true); // Maximize the stage
        primaryStage.show();
    }

    // Method to update the title label with the current book title
    private void updateTitleLabel() {
        titleLabel.setText("Book Title: " + bookTitle);
        authorNameLabel.setText(authorName);
        pageAmountLabel.setText(pageAmount);
        genreOfBookLabel.setText(genreOfBook);
        copiesAmountValueLabel.setText(copiesAmount); // Changed variable name
    }

    // Method to handle opening the link
    private void openLink(MouseEvent event) {
        getHostServices().showDocument("https://renaissancepsa.com/");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
