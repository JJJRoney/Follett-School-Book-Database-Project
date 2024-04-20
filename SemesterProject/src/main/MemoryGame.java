import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemoryGame extends Application {

    private static final int GRID_SIZE = 3;
    private static final int TILE_SIZE = 100;
    private static final int MAX_LEVEL = 20;
    private static final int FLASH_DELAY = 1000; // milliseconds

    private List<Integer> sequence;
    private int currentLevel;
    private int currentIndex;
    private boolean playerTurn;

    private Rectangle[][] tiles;
    private Button startRestartButton;
    private Button returnToMenuButton;

    @Override
    public void start(Stage primaryStage) {
        sequence = new ArrayList<>();
        currentLevel = 1;
        currentIndex = 0;
        playerTurn = false;

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        tiles = new Rectangle[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE, Color.WHITE);
                tile.setStroke(Color.BLACK);
                final int x = i;
                final int y = j;
                tile.setOnMouseClicked(event -> {
                    if (playerTurn) {
                        handleTileClick(x, y);
                    }
                });
                tiles[i][j] = tile;
                gridPane.add(tile, i, j);
            }
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        startRestartButton = new Button("Start Game");
        startRestartButton.setOnAction(event -> {
            if (startRestartButton.getText().equals("Start Game")) {
                startGame();
            } else {
                restartGame();
            }
        });

        returnToMenuButton = new Button("Return to Menu");
        returnToMenuButton.setOnAction(event -> {
            // Implement your logic to return to the menu
        });

        BorderPane.setAlignment(startRestartButton, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(returnToMenuButton, Pos.TOP_CENTER);
        borderPane.setBottom(startRestartButton);
        borderPane.setTop(returnToMenuButton);

        Scene scene = new Scene(borderPane, TILE_SIZE * GRID_SIZE, TILE_SIZE * GRID_SIZE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Memory Game");
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    private void startGame() {
        sequence.clear();
        currentLevel = 1;
        currentIndex = 0;
        playerTurn = false;
        startRestartButton.setText("Restart Game");
        showSequence(); // Show the sequence to the player before starting the game
    }

    private void restartGame() {
        startGame();
    }

    private void showSequence() {
        playerTurn = false;
        sequence.clear();

        // Generate the sequence
        for (int i = 0; i < currentLevel; i++) {
            int x = new Random().nextInt(GRID_SIZE);
            int y = new Random().nextInt(GRID_SIZE);
            sequence.add(x * GRID_SIZE + y);
        }

        // Flash each tile sequentially
        flashSequence(0);
    }

    private void flashSequence(int index) {
        if (index < sequence.size()) {
            int sequenceIndex = sequence.get(index);
            int x = sequenceIndex / GRID_SIZE;
            int y = sequenceIndex % GRID_SIZE;

            // Flash the tile
            flashTile(x, y, FLASH_DELAY, () -> flashSequence(index + 1));
        } else {
            // Sequence flashing complete, allow player's turn
            playerTurn = true;
        }
    }

    private void nextLevel() {
        if (currentLevel > MAX_LEVEL) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Congratulations");
            alert.setHeaderText(null);
            alert.setContentText("You've completed all levels!");
            alert.showAndWait();
            return;
        }

        playerTurn = false;
        currentIndex = 0;

        // Add one additional tile for the new level
        int x = new Random().nextInt(GRID_SIZE);
        int y = new Random().nextInt(GRID_SIZE);
        sequence.add(x * GRID_SIZE + y);
        flashTile(x, y, 0, () -> playerTurn = true); // No delay for the new tile
    }

    private void flashTile(int x, int y, int delay, Runnable callback) {
        Rectangle tile = tiles[x][y];
        new Thread(() -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tile.setFill(Color.web("#345073"));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update the UI on the JavaFX Application Thread
            Platform.runLater(() -> {
                tile.setFill(Color.WHITE);
                // Execute the callback
                callback.run();
            });
        }).start();
    }

    private void handleTileClick(int x, int y) {
        if (!playerTurn) return; // Ignore clicks when it's not player's turn

        int index = x * GRID_SIZE + y;
        if (index == sequence.get(currentIndex)) {
            currentIndex++;
            if (currentIndex == sequence.size()) {
                currentLevel++;
                nextLevel();
                currentIndex = 0; // Reset current index for the new level
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Wrong tile clicked! Game Over.");
            alert.showAndWait();
            startGame(); // Restart the game after showing the message
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}