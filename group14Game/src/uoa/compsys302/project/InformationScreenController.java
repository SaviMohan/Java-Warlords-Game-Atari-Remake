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
/**
 * This is the class that creates the scenes for the instructions and credits screens
 * @author Ira Syamira, Savi Mohan
 *
 */
public class InformationScreenController {
	private MasterViewsController masterViewsController;
	private Group root;
	private Scene informationScene;
	private int displayInfo;
	/**
	 * Constructor for the InformationScreenController class
	 * @param masterViewsController Takes in an instance of MasterViewsController so that the InformationScreenController class can switch scenes back to the main menu
	 * @param displayInfo Determines whether the instructions or credits need to be displayed
	 */
	public InformationScreenController(MasterViewsController masterViewsController, int displayInfo){
		this.masterViewsController = masterViewsController;
		this.displayInfo = displayInfo;
	}
	/**
	 * Instantiates scene containing either instructions or credits and a return to menu button and adds them to a root node, then returns a scene container
	 * that is made from the root node.
	 * @param width
	 * @param height
	 * @return the informationScene container that contains the either the instructions or credits
	 */
	public Scene initialise(int width, int height) {
		root = new Group();
			
		informationScene = new Scene(root, width, height);
			
		Button backButton = new Button();
		backButton.setText("Press Enter to Return to Menu");
		
		switch (displayInfo) {
		case 1:
			informationScene.setFill(new ImagePattern(new Image("file:src/Images/instruction_screen.png")));	
			backButton.setLayoutX(612);
			backButton.setLayoutY(650);
			break;
		case 2:
			informationScene.setFill(new ImagePattern(new Image("file:src/Images/Credits.png")));		
			backButton.setLayoutX(20);
			backButton.setLayoutY(10);
			break;				
		default:
			break;
		}			
		
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
        //you can also return to the main menu by pressing enter
        informationScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent ke) {
		        if (ke.getCode() == KeyCode.ENTER) {
		        	masterViewsController.goToMainMenu(masterViewsController);
		        } 
		    }
		});
		
		return informationScene;
	}

}
