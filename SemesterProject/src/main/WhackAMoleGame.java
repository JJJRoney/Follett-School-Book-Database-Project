import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The WhackAMoleGame class implements a simple JavaFX-based Whack-a-Mole game.
 * The player's objective is to click on moles as they appear within a grid
 * to earn points within a limited time frame.
 *
 * The game features a grid layout where moles randomly appear and disappear.
 * The player can start the game by clicking the "Start" button and earn points
 * by clicking on the visible moles. The game ends after a fixed duration,
 * and the player's score is displayed. The player can restart the game by
 * clicking the "Try Again" button.
 *
 * This game provides an engaging user experience and demonstrates the
 * usage of JavaFX features such as animations, event handling, and layouts.
 *
 * The game parameters such as grid size, mole radius, spawn rate, game duration,
 * and mole display time can be adjusted as constants within the program.
 * Additionally, the number of moles spawned simultaneously can be modified
 * in the spawnMoles method.
 *
 * This program also utilizes the SwitchScene class to facilitate
 * navigation between different scenes within the JavaFX application.
 *
 * @author [Justin Ross]
 * @date [4/19/24]
 * @class [CPS 240]
 */

public class WhackAMoleGame extends Application {

    // Constants for game parameters
    private static final int GRID_SIZE = 8;
    private static final int MOLE_RADIUS = 30;
    private static final int SPAWN_RATE = 1000; // Milliseconds between mole spawns
    private static final int GAME_DURATION = 20000; // Game duration in milliseconds
    private static final int MOLE_DISPLAY_TIME = 1000; // Milliseconds mole is displayed before disappearing

    // Game variables
    private int score = 0;
    private int timeLeftInSeconds = 40; // Initial time left
    private GridPane gridPane;
    private Label scoreLabel;
    private Label timeLeftLabel; // Label to display time left
    private Button startButton;
    private Timeline gameTimeline;
    private Timeline updateTimeLeftTimeline; // Timeline to update time left label
    private boolean gameRunning;

    /**
     * The main entry point for the JavaFX application.
     * Initializes and launches the Whack-a-Mole game.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application by initializing the game UI.
     *
     * @param primaryStage The primary stage for the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create the root layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: lightgrey;");

        // Top Section - Contains buttons for starting and going back
        BorderPane topPane = new BorderPane();
        startButton = new Button("Start");
        startButton.setOnAction(e -> startGame()); // Start button event handler
        startButton.setStyle("-fx-font-size: 18px; -fx-padding: 15px;");
        BorderPane.setAlignment(startButton, Pos.CENTER);
        topPane.setRight(startButton);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            SwitchScene.switchScene("Games", primaryStage);
        });
        backButton.setStyle("-fx-font-size: 18px; -fx-padding: 15px;");
        BorderPane.setAlignment(backButton, Pos.CENTER);
        topPane.setLeft(backButton);

        root.setTop(topPane);

        // Center Section - Contains the grid of moles
        gridPane = createGridPane();
        root.setCenter(gridPane);

        // Bottom Section - Contains the score label, time left label, and instructions label
        BorderPane bottomPane = new BorderPane();
        bottomPane.setPadding(new Insets(10, 0, 10, 0));

        scoreLabel = new Label("Score: 0");
        scoreLabel.setTextFill(Color.DARKBLUE);
        scoreLabel.setStyle("-fx-font-size: 20px;");
        BorderPane.setAlignment(scoreLabel, Pos.CENTER_LEFT);
        bottomPane.setLeft(scoreLabel);

        // Label to display time left
        timeLeftLabel = new Label("Time Left: " + timeLeftInSeconds + "s");
        timeLeftLabel.setTextFill(Color.DARKBLUE);
        timeLeftLabel.setStyle("-fx-font-size: 20px;");
        BorderPane.setAlignment(timeLeftLabel, Pos.CENTER_RIGHT);
        bottomPane.setRight(timeLeftLabel);

        Label instructionsLabel = new Label("Click on moles before they disappear!");
        instructionsLabel.setTextFill(Color.DARKBLUE);
        instructionsLabel.setStyle("-fx-font-size: 20px;");
        BorderPane.setAlignment(instructionsLabel, Pos.CENTER);
        bottomPane.setCenter(instructionsLabel);

        root.setBottom(bottomPane);

        // Create the scene and set it to the stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Whack-a-Mole Game");
        primaryStage.setMaximized(true); // Set stage to maximize
        primaryStage.show();
    }

    /**
     * Creates the grid pane for moles with the specified grid size.
     *
     * @return The GridPane containing the moles.
     */
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Populate the grid with moles
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Circle mole = new Circle(MOLE_RADIUS);
                mole.setFill(Color.DARKBLUE);
                mole.setVisible(false);
                gridPane.add(mole, i, j);
            }
        }

        return gridPane;
    }

    /**
     * Starts the game by initializing game parameters and timelines.
     * Allows the player to interact with the game by clicking moles.
     */
    private void startGame() {
        if (!gameRunning) {
            gameRunning = true;
            score = 0;
            scoreLabel.setText("Score: " + score);
            startButton.setVisible(false);

            // Reset the time left to 40 seconds
            timeLeftInSeconds = 40;
            timeLeftLabel.setText("Time Left: " + timeLeftInSeconds + "s");

            // Stop and reset the update time left timeline if it's running
            if (updateTimeLeftTimeline != null) {
                updateTimeLeftTimeline.stop();
            }

            // Set up timeline for spawning moles at regular intervals
            gameTimeline = new Timeline(new KeyFrame(Duration.millis(SPAWN_RATE), e -> spawnMoles()));
            gameTimeline.setCycleCount(Timeline.INDEFINITE);
            gameTimeline.play();

            // Set up timeline for game duration
            Timeline gameDurationTimeline = new Timeline(new KeyFrame(Duration.millis(GAME_DURATION), e -> endGame()));
            gameDurationTimeline.setDelay(Duration.millis(GAME_DURATION));
            gameDurationTimeline.play();

            // Update time left label every second
            updateTimeLeftTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                if (timeLeftInSeconds > 0) {
                    timeLeftInSeconds--;
                    timeLeftLabel.setText("Time Left: " + timeLeftInSeconds + "s");
                }
            }));
            updateTimeLeftTimeline.setCycleCount(Timeline.INDEFINITE);
            updateTimeLeftTimeline.play();
        }
    }

    /**
     * Ends the game and stops all game-related timelines.
     */
    private void endGame() {
        gameRunning = false;
        startButton.setText("Try Again");
        startButton.setVisible(true);
        gameTimeline.stop();
    }

    /**
     * Spawns moles at random locations within the grid.
     * Moles are displayed for a fixed duration before disappearing.
     * Players earn points by clicking on visible moles.
     */
    private void spawnMoles() {
        Random random = new Random();
        List<Circle> moles = new ArrayList<>();

        // Randomly choose multiple locations to spawn moles
        for (int i = 0; i < 3; i++) { // You can adjust the number of moles spawned simultaneously here
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);
            Circle mole = (Circle) gridPane.getChildren().get(row * GRID_SIZE + col);
            moles.add(mole);
        }

        // Display moles at the chosen locations
        for (Circle mole : moles) {
            mole.setVisible(true);

            // Set up timeline for mole disappearance
            Timeline moleDisplayTimeline = new Timeline(new KeyFrame(Duration.millis(MOLE_DISPLAY_TIME), e -> {
                if (mole.isVisible()) {
                    mole.setVisible(false);
                }
            }));
            moleDisplayTimeline.setDelay(Duration.millis(MOLE_DISPLAY_TIME));
            moleDisplayTimeline.play();

            // Add mouse click event for scoring
            mole.setOnMouseClicked(e -> {
                if (gameRunning && mole.isVisible()) {
                    score++;
                    scoreLabel.setText("Score: " + score);
                    mole.setVisible(false);
                }
            });
        }
    }
}
