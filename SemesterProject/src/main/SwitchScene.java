
import javafx.stage.Stage;

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
			// Add cases for other scenes as needed
			default:
				System.out.println("Invalid scene name");
				break;
		}
	}
}

