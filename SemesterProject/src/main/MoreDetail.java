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
    
    public MoreDetail() {
        // Default values
        this.bookTitle = "Unknown";
        this.authorName = "Unknown Author";
        this.pageAmount = "0";
        this.genreOfBook = "Unknown";
        this.copiesAmount = "0";
    }

    public MoreDetail(String bookTitle) {
        this.bookTitle = bookTitle;
        // You can set other book details based on the selected book title here.
        // For now, I'm setting some example values.
        this.authorName = "Unknown Author";
        this.pageAmount = "300";
        this.genreOfBook = "Fiction";
        this.copiesAmount = "5";
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

        // Add delete button to the bottom right
        Button deleteButton = new Button("Delete");
        deleteButton.setVisible(false);
        HBox bottomRightBox = new HBox();
        bottomRightBox.setAlignment(Pos.BOTTOM_RIGHT);
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

    private void openLink(MouseEvent event) {
        getHostServices().showDocument("https://renaissancepsa.com/");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
