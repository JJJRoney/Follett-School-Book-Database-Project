import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class game extends Application {

    private static final int GRID_SIZE = 3;
    private static final int TILE_SIZE = 100;
    private static final int MAX_LEVEL = 20;
    private static final int FLASH_DELAY = 1000; // milliseconds

    private List<Integer> sequence;
    private int currentLevel;
    private int currentIndex;
    private boolean playerTurn;

    private Rectangle[][] tiles;

    @Override
    public void start(Stage primaryStage) {
        sequence = new ArrayList<>();
        currentLevel = 1;
        currentIndex = 0;
        playerTurn = false;

        GridPane gridPane = new GridPane();
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

        Scene scene = new Scene(gridPane, TILE_SIZE * GRID_SIZE, TILE_SIZE * GRID_SIZE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Memory Game");
        primaryStage.show();

        showSequence(); // Show the sequence to the player before starting the game
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
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/*V3import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class game extends Application {

    private static final int GRID_SIZE = 3;
    private static final int TILE_SIZE = 100;
    private static final int MAX_LEVEL = 5;
    private static final int FLASH_DELAY = 1000; // milliseconds

    private List<Integer> sequence;
    private int currentLevel;
    private int currentIndex;
    private boolean playerTurn;

    private Rectangle[][] tiles;

    @Override
    public void start(Stage primaryStage) {
        sequence = new ArrayList<>();
        currentLevel = 1;
        currentIndex = 0;
        playerTurn = false;

        GridPane gridPane = new GridPane();
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

        Scene scene = new Scene(gridPane, TILE_SIZE * GRID_SIZE, TILE_SIZE * GRID_SIZE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Memory Game");
        primaryStage.show();

        showSequence(); // Show the sequence to the player before starting the game
    }

    private void showSequence() {
        playerTurn = false;
        sequence.clear();

        for (int i = 0; i < currentLevel; i++) {
            int x = new Random().nextInt(GRID_SIZE);
            int y = new Random().nextInt(GRID_SIZE);
            sequence.add(x * GRID_SIZE + y);
            flashTile(x, y, i * FLASH_DELAY); // Delay the flashing of each tile
        }

        playerTurn = true;
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

        for (int i = 0; i < currentLevel; i++) {
            int x = new Random().nextInt(GRID_SIZE);
            int y = new Random().nextInt(GRID_SIZE);
            sequence.add(x * GRID_SIZE + y);
            flashTile(x, y, i * FLASH_DELAY);
        }

        playerTurn = true;
    }

    private void flashTile(int x, int y, int delay) {
        Rectangle tile = tiles[x][y];
        new Thread(() -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tile.setFill(Color.YELLOW);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tile.setFill(Color.WHITE);
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
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/


/*V2import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class game extends Application {

    private static final int GRID_SIZE = 3;
    private static final int TILE_SIZE = 100;
    private static final int MAX_LEVEL = 5;
    private static final int FLASH_DELAY = 1000; // milliseconds

    private List<Integer> sequence;
    private int currentLevel;
    private int currentIndex;
    private boolean playerTurn;

    private Rectangle[][] tiles;

    @Override
    public void start(Stage primaryStage) {
        sequence = new ArrayList<>();
        currentLevel = 1;
        currentIndex = 0;
        playerTurn = false;

        GridPane gridPane = new GridPane();
        tiles = new Rectangle[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE, Color.WHITE);
                tile.setStroke(Color.BLACK);
                final int x = i;
                final int y = j;
                tiles[i][j] = tile;
                gridPane.add(tile, i, j);
            }
        }

        Scene scene = new Scene(gridPane, TILE_SIZE * GRID_SIZE, TILE_SIZE * GRID_SIZE);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Memory Game");
        primaryStage.show();

        showSequence(); // Show the sequence to the player before starting the game
    }

    private void showSequence() {
        playerTurn = false;
        sequence.clear();

        for (int i = 0; i < currentLevel; i++) {
            int x = new Random().nextInt(GRID_SIZE);
            int y = new Random().nextInt(GRID_SIZE);
            sequence.add(x * GRID_SIZE + y);
            flashTile(x, y, i * FLASH_DELAY); // Delay the flashing of each tile
        }

        playerTurn = true;
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

        for (int i = 0; i < currentLevel; i++) {
            int x = new Random().nextInt(GRID_SIZE);
            int y = new Random().nextInt(GRID_SIZE);
            sequence.add(x * GRID_SIZE + y);
            flashTile(x, y, i * FLASH_DELAY);
        }

        playerTurn = true;
    }

    private void flashTile(int x, int y, int delay) {
        Rectangle tile = tiles[x][y];
        new Thread(() -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tile.setFill(Color.YELLOW);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tile.setFill(Color.WHITE);
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
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Wrong tile clicked! Game Over.");
            alert.showAndWait();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/


/*import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
i[mport javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.ArrayList;
import java.util.Random;

public class game extends Application {
    private int gridSize = 3;
    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList<Integer> sequence = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Button button = new Button();
                button.setMinSize(100, 100);
                final int x = i;
                final int y = j;
                button.setOnAction(event -> onButtonClick(x, y));
                buttons.add(button);
                gridPane.add(button, j, i);
            }
        }

        Scene scene = new Scene(gridPane, 350, 350);
        primaryStage.setTitle("Memory Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        startGame();
    }

    private void startGame() {
        generateSequence();
        displaySequence();
    }

    private void generateSequence() {
        Random random = new Random();
        sequence.add(random.nextInt(gridSize * gridSize));
    }

    private void displaySequence() {
        for (int i = 0; i < sequence.size(); i++) {
            int index = sequence.get(i);
            Button button = buttons.get(index);
            button.setStyle("-fx-background-color: red;");
            try {
                Thread.sleep(1000); // Display each button for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            button.setStyle("-fx-background-color: white;");
            try {
                Thread.sleep(500); // Pause for 0.5 seconds between buttons
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void onButtonClick(int x, int y) {
        int index = x * gridSize + y;
        if (index == sequence.get(currentIndex)) {
            currentIndex++;
            if (currentIndex == sequence.size()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Congratulations!");
                alert.setHeaderText(null);
                alert.setContentText("You have successfully completed the sequence!");
                alert.showAndWait();
                currentIndex = 0;
                sequence.clear();
                startGame();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect sequence! Game Over!");
            alert.showAndWait();
            currentIndex = 0;
            sequence.clear();
            startGame();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/