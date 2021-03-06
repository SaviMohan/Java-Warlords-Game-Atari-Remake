package uoa.compsys302.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * This is the class that creates the multiplayer exit screen scene
 * @author Ira Syamira, Savi Mohan
 *
 */
public class MultiplayerExitScreenController {
	private MasterViewsController masterViewsController;
	private Group root;
	private Scene multiplayerExitScreenScene;
	private String gameMessage;
	/**
	 * Constructor for the MultiplayerExitScreenController class
	 * @param masterViewsController Takes in an instance of MasterViewsController so that the MultiplayerExitScreenController class can switch scenes back to the main menu
	 * @param gameMessage The message that needs to be displayed once the multiplayer game is finished
	 */
	public MultiplayerExitScreenController(MasterViewsController masterViewsController, String gameMessage){
		this.masterViewsController = masterViewsController;
		this.gameMessage = gameMessage;
	}
	/**
	 * Instantiates scene containing end of game message and a return to menu button and adds them to a root node, then returns a scene container
	 * that is made from the root node.
	 * @param width
	 * @param height
	 * @return the multiplayerExitScreenScene container that contains the exit screen message
	 */
	public Scene initialise(int width, int height) {
		root = new Group();
			
		multiplayerExitScreenScene = new Scene(root, width, height);
			
		Button backButton = new Button();
		backButton.setText("Press Enter to Return to Menu");
		backButton.setLayoutX(380);
		backButton.setLayoutY(350);
		backButton.setMinWidth(300);
		backButton.setMinHeight(50);
		backButton.setFont(Font.font("Verdana", 20));
		backButton.setTextFill(Color.BLUE);
		backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	masterViewsController.goToMainMenu(masterViewsController);//return to the main menu
            }
        });
		root.getChildren().add(backButton);
		
		multiplayerExitScreenScene.setFill(new ImagePattern(new Image("file:src/Images/Default_screen.png")));	
		
		//can also return to the main menu by pressing enter        
		multiplayerExitScreenScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent ke) {
		        if (ke.getCode() == KeyCode.ENTER) {
		        	masterViewsController.goToMainMenu(masterViewsController);
		        } 
		    }
		});
		
		Text resultsText = new Text(320,300,gameMessage);//display game results (ie, who won, or tie)
		resultsText.setFont(Font.font("Verdana", 45));
		resultsText.setFill(Color.WHITE);
        root.getChildren().add(resultsText);
		
		return multiplayerExitScreenScene;
	}
}
