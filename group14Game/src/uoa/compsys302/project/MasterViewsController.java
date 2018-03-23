package uoa.compsys302.project;


import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import uoa.compsys302.project.view.GameView;
import uoa.compsys302.project.view.InformationScreenView;
import uoa.compsys302.project.view.LevelsProgressView;
import uoa.compsys302.project.view.MainMenuView;
import uoa.compsys302.project.view.MultiplayerExitScreenView;
import uoa.compsys302.project.view.MultiplayerSetupView;

/**
 * This is the master controller class that initialises a controller class,then extracts and plots 
 * it's model class data by calling the view class that corresponds to that controller. This class 
 * controls switching between different views (ie. from game menu to a game level)
 * @author Savi Mohan, Ira Syamira
 */
public class MasterViewsController {
	
	private final int frameRate = 48;
    private final int durationMilliseconds = 1000 / frameRate; //the delay between each call of the tick() function in milliseconds
       
	private Stage stage;
	public Timeline animation;
	GameController gameController;
	Scene gameScene;
	
	GameView gameView;
	
	/**
	 * Constructor for MasterViewsController class
	 * @param primaryStage the Stage that this class uses
	 */
	public MasterViewsController(Stage primaryStage) {
		this.stage = primaryStage;
		this.animation = new Timeline();
		stage.show();
		
		
	}	
	
	
	/**
	 * Initialises the GameController Class and takes its model class information 
	 * and passes it to the GameView class (which it also initialises) to be plotted 
	 * @param masterViewsController
	 * @param level
	 * @param gameMode
	 * @param humanPlayerCount
	 * @param isSinglePlayer
	 * @param playerNames
	 */
	public void goToGameScene(MasterViewsController masterViewsController,int level, int gameMode, int humanPlayerCount, boolean isSinglePlayer, ArrayList<String> playerNames){
		animation.stop();
		
		gameController = new GameController(masterViewsController,level,gameMode,humanPlayerCount, isSinglePlayer,playerNames);
		gameScene = gameController.initialise(1024, 768);
		animation.getKeyFrames().clear();
		
		KeyFrame frame = new KeyFrame(Duration.millis(durationMilliseconds), e -> gameController.tick());
		animation = new Timeline();
		gameView = new GameView(gameScene, animation, stage,frame);
				
	}
	
	/**
	 * Initialises the MainMenuController Class and takes its data 
	 * and passes it to the MainMenuView class (which it also initialises) to be plotted
	 * @param masterViewsController
	 */
	public void goToMainMenu(MasterViewsController masterViewsController) {
		animation.stop();
		MainMenuController menuController = new MainMenuController(masterViewsController);
		Scene menuScene = menuController.initialise(1024, 768);
		MainMenuView mainMenuView = new MainMenuView(menuScene,stage);
		
	}
	/**
	 * Initialises the MultiplayerSetupController Class and takes its data 
	 * and passes it to the MultiplayerSetupView class (which it also initialises) to be plotted
	 * @param masterViewsController
	 */
	public void goToMultiplayerSetup(MasterViewsController masterViewsController) {
		animation.stop();
		MultiplayerSetupController multiplayerSetupController = new MultiplayerSetupController(masterViewsController);
		Scene multiplayerSetupScene = multiplayerSetupController.initialise(1024, 768,1);
		MultiplayerSetupView multiplayerSetupView = new MultiplayerSetupView(multiplayerSetupScene,stage);
		
	}
	/**
	 * Initialises the LevelsProgressController Class and takes its data 
	 * and passes it to the LevelsProgressView class (which it also initialises) to be plotted
	 * @param masterViewsController
	 * @param level
	 */
	public void goToLevelsProgressScene(MasterViewsController masterViewsController, int level){
		animation.stop();
		LevelsProgressController levelsProgressController = new LevelsProgressController(masterViewsController,level);
		Scene levelsProgressScene = levelsProgressController.initialise(1024, 768);
		LevelsProgressView levelsProgressView = new LevelsProgressView(levelsProgressScene,stage);
		
	}
	/**
	 * Initialises the InformationScreenController Class and takes its data 
	 * and passes it to the InformationScreenView class (which it also initialises) to be plotted
	 * @param masterViewsController
	 * @param displayInfo
	 */
	public void goToInformationScreen(MasterViewsController masterViewsController, int displayInfo) {
		animation.stop();
		InformationScreenController informationScreenController = new InformationScreenController(masterViewsController,displayInfo);
		Scene informationScene = informationScreenController.initialise(1024, 768);
		InformationScreenView informationScreenView = new InformationScreenView(informationScene,stage);
		
	}
	/**
	 * Initialises the MultiplayerExitScreenController Class and takes its data 
	 * and passes it to the MultiplayerExitScreenView class (which it also initialises) to be plotted
	 * @param masterViewsController
	 * @param gameMessage
	 */
	public void goToMultiplayerExitScreen(MasterViewsController masterViewsController, String gameMessage) {
		animation.stop();
		MultiplayerExitScreenController multiplayerExitScreenController = new MultiplayerExitScreenController(masterViewsController,gameMessage);
		Scene multiplayerExitScreenScene = multiplayerExitScreenController.initialise(1024, 768);
		MultiplayerExitScreenView multiplayerExitScreenView = new MultiplayerExitScreenView(multiplayerExitScreenScene,stage);
		
	}
}
