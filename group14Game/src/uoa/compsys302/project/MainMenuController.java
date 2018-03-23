package uoa.compsys302.project;

import java.util.ArrayList;

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
/**
 * This is the class which creates main menu buttons and packages them into the main menu scene.
 * @author Ira Syamira, Savi Mohan
 *
 */
public class MainMenuController {
	private MasterViewsController masterViewsController;
	private Group root;
	private Scene menuScene;
	private int buttonIndex;//keeps track of the current button highlighted by the keyboard keys
	private ArrayList<Button> buttonList;//stores all the buttons created
	/**
	 * constructor for the MainMenuController
	 * @param masterViewsController Takes in an instance of MasterViewsController so that the MainMenuController can switch scenes
	 */
	public MainMenuController(MasterViewsController masterViewsController){
		this.masterViewsController = masterViewsController;
		buttonIndex = 0;
		buttonList = new ArrayList<Button>();
	}
	/**
	 * Instantiates buttons (and their functionality) for the main menu and adds them to a root node, then returns a scene container
	 * that is made from the root node.
	 * @param width
	 * @param height
	 * @return the menuScene container that contains all the button objects
	 */
	public Scene initialise(int width, int height) {
		root = new Group();
		Image img = new Image("file:src/Images/start_screen.png");
		
		menuScene = new Scene(root, width, height, new ImagePattern(img));
		
		//creates single player button
		Button singlePlayerButton = new Button();
		singlePlayerButton.setText("Single Player");
		singlePlayerButton.setLayoutX(362);
		singlePlayerButton.setLayoutY(340);
		singlePlayerButton.setMinWidth(300);
		singlePlayerButton.setMinHeight(50);
		singlePlayerButton.setFont(Font.font("Verdana", 20));
				
        singlePlayerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//go to single player mode start screen when pressed
            	masterViewsController.goToLevelsProgressScene(masterViewsController, 1);
            }
        });
        singlePlayerButton.setTextFill(Color.BLUE);
        buttonList.add(singlePlayerButton);
        root.getChildren().add(singlePlayerButton);
        
        //creates multiplayer button
        Button multiPlayerButton = new Button();
        multiPlayerButton.setText("Multiplayer");
        multiPlayerButton.setLayoutX(362);
        multiPlayerButton.setLayoutY(400);
        multiPlayerButton.setMinWidth(300);
        multiPlayerButton.setMinHeight(50);
        multiPlayerButton.setFont(Font.font("Verdana", 20));
		
        multiPlayerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//go to multiplayer setup screen when pressed
            	masterViewsController.goToMultiplayerSetup(masterViewsController);
            }
        });
        buttonList.add(multiPlayerButton);
        root.getChildren().add(multiPlayerButton);
        //creates instructions button
        Button instructionsButton = new Button();
        instructionsButton.setText("Instructions");
        instructionsButton.setLayoutX(362);
        instructionsButton.setLayoutY(460);
        instructionsButton.setMinWidth(300);
        instructionsButton.setMinHeight(50);
        instructionsButton.setFont(Font.font("Verdana", 20));
		
        instructionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//go to instructions screen when pressed
            	masterViewsController.goToInformationScreen(masterViewsController, 1);
            }
        });
        buttonList.add(instructionsButton);
        root.getChildren().add(instructionsButton);
        //creates credits button
        Button creditsButton = new Button();
        creditsButton.setText("Credits");
        creditsButton.setLayoutX(362);
        creditsButton.setLayoutY(520);
        creditsButton.setMinWidth(300);
        creditsButton.setMinHeight(50);
        creditsButton.setFont(Font.font("Verdana", 20));
		
        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//go to credits screen when pressed
            	masterViewsController.goToInformationScreen(masterViewsController, 2);
            }
        });
        buttonList.add(creditsButton);
        root.getChildren().add(creditsButton);
        //creates ai demo mode button
        Button aiButton = new Button();
        aiButton.setText("AI DEMO");
        aiButton.setLayoutX(362);
        aiButton.setLayoutY(580);
        aiButton.setMinWidth(300);
        aiButton.setMinHeight(50);
        aiButton.setFont(Font.font("Verdana", 20));
		
        aiButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	ArrayList<String> playerNames = new ArrayList<String>();//list of player names for ai demo
            	playerNames.add("Player 1");
            	playerNames.add("Player 2");
            	playerNames.add("Player 3");
            	playerNames.add("Player 4");
            	//go to ai demo mode when pressed
            	masterViewsController.goToGameScene(masterViewsController, 5, 1, 0, false, playerNames);
            }
        });
        buttonList.add(aiButton);
        root.getChildren().add(aiButton);
        //on top of having all the buttons being mouse clickable, their functionality can also be accessed via keyboard
        menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent ke) {
		        if (ke.getCode() == KeyCode.UP) {//if up key is pressed the highlighted button becomes the next one above on the screen		        	
		        	if(buttonIndex > 0){
		        		buttonIndex = buttonIndex - 1;
		        	}
		        	buttonList.get(buttonIndex).setTextFill(Color.BLUE);
		        	for (int i=0;i<buttonList.size();i++){
		        		if(i!=buttonIndex){
		        			buttonList.get(i).setTextFill(Color.BLACK);
		        		}
		        	}
		        } else if (ke.getCode() == KeyCode.DOWN) {//if down key is pressed the highlighted button becomes the next one below on the screen			        	
		        	if(buttonIndex < (buttonList.size()-1)){
		        		buttonIndex = buttonIndex + 1;
		        	}
		        	buttonList.get(buttonIndex).setTextFill(Color.BLUE);
		        	for (int i=0;i<buttonList.size();i++){
		        		if(i!=buttonIndex){
		        			buttonList.get(i).setTextFill(Color.BLACK);
		        		}
		        	}							
		        } else if (ke.getCode() == KeyCode.ENTER) {//if the enter key is pressed, the functionality of the current highlighted button is carried out
		        	switch(buttonIndex){
		        	case 0:
		        		//SINGLE PLAYER
		        		masterViewsController.goToLevelsProgressScene(masterViewsController, 1);	
		    			break;
		    		case 1:
		    			//MULTIPLAYER
		    			masterViewsController.goToMultiplayerSetup(masterViewsController);		
		    			break;
		    		
		    		case 2:
		    			//INSTRUCTIONS
		    			masterViewsController.goToInformationScreen(masterViewsController, 1);		
		    			break;
		    		case 3:
		    			//CREDITS
		    			masterViewsController.goToInformationScreen(masterViewsController, 2);	
		    			break;
		    		case 4:
		    			//AI DEMO
		    			ArrayList<String> playerNames = new ArrayList<String>();
		            	playerNames.add("AI 1");
		            	playerNames.add("AI 2");
		            	playerNames.add("AI 3");
		            	playerNames.add("AI 4");
		            	masterViewsController.goToGameScene(masterViewsController, 5, 1, 0, false, playerNames);
		    			break;	
		    		
		    		default:
		    			break;
		    			
		        	}
		        } 
		    }
		});
		
		return menuScene;
	}

}
