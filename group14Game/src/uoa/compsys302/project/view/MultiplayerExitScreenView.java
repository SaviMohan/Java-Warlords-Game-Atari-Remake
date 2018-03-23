package uoa.compsys302.project.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * This is a view class which plots the scene data from the MultiplayerExitScreenController class.
 * @author Ira Syamira, Savi Mohan
 *
 */
public class MultiplayerExitScreenView {
	/**
	 * Constructor that takes in the stage and the scene to be used on that stage
	 * @param multiplayerExitScreenScene
	 * @param stage
	 */
	public MultiplayerExitScreenView(Scene multiplayerExitScreenScene,Stage stage){
		stage.setScene(multiplayerExitScreenScene);
	}
}
