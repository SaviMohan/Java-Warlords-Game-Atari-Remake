package uoa.compsys302.project.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is a view class which plots the scene data from the LevelsProgressController class.
 * @author Savi Mohan, Ira Syamira
 */
public class LevelsProgressView {
	/**
	 * Constructor that takes in the stage and the scene to be used on that stage
	 * @param levelsProgressScene
	 * @param stage
	 */
	public LevelsProgressView(Scene levelsProgressScene,Stage stage){
		stage.setScene(levelsProgressScene);
	}

}
