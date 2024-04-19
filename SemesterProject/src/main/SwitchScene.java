
import javafx.stage.Stage;
import sample.Main;

public class SwitchScene {
	
	public static void switchScene(String sceneName, Stage primaryStage) {
		
		switch (sceneName) {
			case "Home":
				HomePage homePage = new HomePage();
				homePage.start(primaryStage);
				break;
			case "Bookshelf":
				Bookshelf bookshelf = new Bookshelf();
				bookshelf.start(primaryStage);
				break;
			case "LoginPage":
				LoginPage loginPage = new LoginPage();
				loginPage.start(primaryStage);
				break;
			case "MoreDetail":
				MoreDetail moreDetail = new MoreDetail();
				moreDetail.start(primaryStage);
				break;
			case "Games":
				GameLaunchPage gamesPage = new GameLaunchPage();
				gamesPage.start(primaryStage);
				break;
			case "Snake":
				SnakeGame snakeGame = new SnakeGame();
				snakeGame.start(primaryStage);
				break;
			case "Memory":
				MemoryGame memoryGame = new MemoryGame();
				memoryGame.start(primaryStage);
				break;
			case "Brick Breaker":
				Main brickBreaker = new Main();
			try {
				brickBreaker.start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
				break;
			case "WhackAMole":
				WhackAMoleGame wamg = new WhackAMoleGame();
				wamg.start(primaryStage);
				break;
			default:
				System.out.println("Invalid scene name");
				break;
		}
	}
}

