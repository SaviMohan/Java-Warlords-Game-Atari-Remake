package uoa.compsys302.project.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * This is a view class which plots the scene data from the MultiplayerSetupViewController class.
 * @author Ira Syamira, Savi Mohan
 *
 */
public class MultiplayerSetupView {
	/**
	 * Constructor that takes in the stage and the scene to be used on that stage
	 * @param multiplayerSetupScene
	 * @param stage
	 */
	public MultiplayerSetupView(Scene multiplayerSetupScene, Stage stage){
		stage.setScene(multiplayerSetupScene);
	}
}
