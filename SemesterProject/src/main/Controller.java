/**
 * The Controller class handles the game dynamics for the Brick Breaker game.
 * It includes initialization logic, game state management, and response to user interactions.
 * The class utilizes JavaFX Animation and Event Handling to manage game elements and interactions.
 */


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Application; // Ensures that Application can be referenced
import javafx.stage.Stage;             // For handling the stage
import javafx.event.ActionEvent;       // For handling the action event
import javafx.fxml.FXML;                // For FXML annotations




public class Controller implements Initializable {

	@FXML
	private AnchorPane scene;

	@FXML
	private Circle circle;

	@FXML
	private Rectangle paddle;

	@FXML
	private Rectangle bottomZone;

	@FXML
	private Button startButton;
	
	@FXML
	private Button GameLaunchPageButton;

	private int paddleStartSize = 600;

	Robot robot = new Robot();

	private ArrayList<Rectangle> bricks = new ArrayList<>();

	double deltaX = -1;
	double deltaY = -3;

	@FXML
	private Button gameLaunchPageButton; // This is your "Back" button

	public void gameOver() {
	    // Logic that handles what happens when the game is over
	    System.out.println("Game over!");

	    // Make the "Back" button visible again for the user to navigate away
	    gameLaunchPageButton.setVisible(true);

	    // Optionally reset the game environment or provide options to restart
	    resetGameEnvironment();
	}

	public void resetGameEnvironment() {
	    // Reset game elements, positions, scores, etc.
	    // Ensure the start button is visible if the game can be restarted
	    startButton.setVisible(true);
	}

	
	// 1 Frame evey 10 millis, which means 100 FPS
	Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent actionEvent) {
	        movePaddle();
	        checkCollisionPaddle(paddle);
	        circle.setLayoutX(circle.getLayoutX() + deltaX);
	        circle.setLayoutY(circle.getLayoutY() + deltaY);

	        if (!bricks.isEmpty()) {
	            bricks.removeIf(brick -> checkCollisionBrick(brick));
	            if (bricks.isEmpty()) { // Check if all bricks are gone
	                resetAndNavigateToStart(); // Call the method to reset the game and navigate
	            }
	        } else {
	            resetAndNavigateToStart(); // Call the method to reset the game and navigate
	        }

	        checkCollisionScene(scene);
	        checkCollisionBottomZone();
	    }
	}));

	
	@FXML
    void backToGameLaunchPage(ActionEvent event) {
        try {
            // Load the GameLaunchPage
            Application gameLaunchPage = new GameLaunchPage();
            Stage stage = (Stage) GameLaunchPageButton.getScene().getWindow();  // Get the current stage
            gameLaunchPage.start(stage);  // Start the GameLaunchPage application on the current stage
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		paddle.setWidth(paddleStartSize);
		timeline.setCycleCount(Animation.INDEFINITE);
	}

	@FXML
	void startGameButtonAction(ActionEvent event) {
		startButton.setVisible(false);
		startGame();
		GameLaunchPageButton.setVisible(false);
		
	}
	@FXML
	void endGameButtonAction(ActionEvent event) {
		GameLaunchPageButton.setVisible(false);
		
	}

	public void startGame() {
		createBricks();
		timeline.play();
	}

	public void checkCollisionScene(Node node) {
	    Bounds bounds = node.getBoundsInLocal();
	    double maxX = bounds.getMaxX();
	    double minX = bounds.getMinX();
	    double maxY = bounds.getMaxY();
	    double minY = bounds.getMinY();

	    // Right boundary
	    if (circle.getLayoutX() >= (maxX - circle.getRadius())) {
	        circle.setLayoutX(maxX - circle.getRadius());
	        deltaX *= -1;
	    }

	    // Left boundary
	    if (circle.getLayoutX() <= (minX + circle.getRadius())) {
	        circle.setLayoutX(minX + circle.getRadius());
	        deltaX *= -1;
	    }

	    // Top boundary
	    if (circle.getLayoutY() <= (minY + circle.getRadius())) {
	        circle.setLayoutY(minY + circle.getRadius());
	        deltaY *= -1;
	    }

	    // Bottom boundary (very bottom of the window)
	    if (circle.getLayoutY() >= (maxY - circle.getRadius())) {
	        // Instead of bouncing the ball, you might want to handle game over or similar logic here
	        gameOver();  // Game over if the ball hits the bottom of the scene
	    }
	}



	public boolean checkCollisionBrick(Rectangle brick) {

		if (circle.getBoundsInParent().intersects(brick.getBoundsInParent())) {
			boolean rightBorder = circle.getLayoutX() >= ((brick.getX() + brick.getWidth()) - circle.getRadius());
			boolean leftBorder = circle.getLayoutX() <= (brick.getX() + circle.getRadius());
			boolean bottomBorder = circle.getLayoutY() >= ((brick.getY() + brick.getHeight()) - circle.getRadius());
			boolean topBorder = circle.getLayoutY() <= (brick.getY() + circle.getRadius());

			if (rightBorder || leftBorder) {
				deltaX *= -1;
			}
			if (bottomBorder || topBorder) {
				deltaY *= -1;
			}

			paddle.setWidth(paddle.getWidth() - (0.10 * paddle.getWidth()));
			scene.getChildren().remove(brick);

			return true;
		}
		return false;
	}

	public void createBricks() {
	    double width = 1250;   // Total width where bricks will be created
	    double height = 200;  // Total height where bricks will be created
	    String[] colors = { "RED", "BLUE", "ORANGE", "GREEN" };  // Corrected typo in "ORANGE"
	    int rowCounter = 0;   // Counter to keep track of the current row

	    // Iterate over each row
	    for (double y = 0; y < height; y += 50) {
	        // Select color from the array based on the current row
	        Color fill = Color.valueOf(colors[rowCounter % colors.length]);

	        // Iterate over each column in the current row
	        for (double x = 0; x < width; x += 55) {  // Adjusted space between bricks
	            Rectangle rectangle = new Rectangle(x, y, 50, 30);  // Adjusted brick size for uniformity
	            rectangle.setFill(fill);
	            scene.getChildren().add(rectangle);
	            bricks.add(rectangle);
	        }

	        // Increment the row counter after finishing each row
	        rowCounter++;
	    }
	}


	public void movePaddle() {
	    double mouseX = robot.getMouseX();
	    double newPaddleX = mouseX - scene.localToScene(scene.getBoundsInLocal()).getMinX() - paddle.getWidth() / 2;

	    newPaddleX = Math.max(newPaddleX, 0); // Ensure paddle doesn't move beyond the left edge
	    newPaddleX = Math.min(newPaddleX, scene.getWidth() - paddle.getWidth()); // Ensure paddle doesn't move beyond the right edge

	    paddle.setLayoutX(newPaddleX);
	}


	public void checkCollisionPaddle(Rectangle paddle) {

		if (circle.getBoundsInParent().intersects(paddle.getBoundsInParent())) {

			boolean rightBorder = circle
					.getLayoutX() >= ((paddle.getLayoutX() + paddle.getWidth()) - circle.getRadius());
			boolean leftBorder = circle.getLayoutX() <= (paddle.getLayoutX() + circle.getRadius());
			boolean bottomBorder = circle
					.getLayoutY() >= ((paddle.getLayoutY() + paddle.getHeight()) - circle.getRadius());
			boolean topBorder = circle.getLayoutY() <= (paddle.getLayoutY() + circle.getRadius());

			if (rightBorder || leftBorder) {
				deltaX *= -1;
			}
			if (bottomBorder || topBorder) {
				deltaY *= -1;
			}
		}
	}

	public void checkCollisionBottomZone() {
		if (circle.getBoundsInParent().intersects(bottomZone.getBoundsInParent())) {
			timeline.stop();
			bricks.forEach(brick -> scene.getChildren().remove(brick));
			bricks.clear();
			startButton.setVisible(true);
			GameLaunchPageButton.setVisible(true);

			paddle.setWidth(paddleStartSize);

			deltaX = -1;
			deltaY = -3;

			circle.setLayoutX(300);
			circle.setLayoutY(300);

			System.out.println("Game over!");
		}
	}
	
	public void resetAndNavigateToStart() {
	    timeline.stop(); // Stop the game animation
	    bricks.forEach(brick -> scene.getChildren().remove(brick)); // Remove all bricks
	    bricks.clear(); // Clear the bricks list
	    startButton.setVisible(true); // Show the start button
	    gameLaunchPageButton.setVisible(true);
	    GameLaunchPageButton.setVisible(true); // Show the game launch button if hidden

	    // Reset the ball and paddle positions
	    paddle.setWidth(paddleStartSize);
	    circle.setLayoutX(300);
	    circle.setLayoutY(300);

	    // Reset movement deltas
	    deltaX = -1;
	    deltaY = -3;

	    // Call the navigation method here
	    // navigateToStartScreen(); // Uncomment this line if you have a method to handle screen changes
	    System.out.println("Game reset, navigate to start!");
	}

}
