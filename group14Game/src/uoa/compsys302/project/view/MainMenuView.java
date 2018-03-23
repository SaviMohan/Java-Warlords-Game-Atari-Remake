package uoa.compsys302.project.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * This is a view class which plots the scene data from the MainMenuController class.
 * @author Ira Syamira,Savi Mohan
 *
 */
public class MainMenuView {
	/**
	 * Constructor that takes in the stage and the scene to be used on that stage
	 * @param menuScene
	 * @param stage
	 */
	public MainMenuView(Scene menuScene,Stage stage){
		stage.setScene(menuScene);
	}
}
