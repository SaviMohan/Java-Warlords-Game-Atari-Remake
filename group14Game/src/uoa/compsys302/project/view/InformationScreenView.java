package uoa.compsys302.project.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is a view class which plots the scene data from the InformationScreenController class.
 * @author Savi Mohan, Ira Syamira
 */
public class InformationScreenView {
	
	 
	/**
	 * Constructor that takes in the stage and the scene to be used on that stage
	 * @param informationScene
	 * @param stage
	 */
	public InformationScreenView(Scene informationScene,Stage stage){
		stage.setScene(informationScene);
	}
}
