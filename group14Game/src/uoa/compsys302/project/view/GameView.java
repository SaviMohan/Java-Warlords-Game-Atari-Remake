package uoa.compsys302.project.view;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is a view class which plots the model object data from the GameController class.
 * @author Savi Mohan, Ira Syamira
 */
public class GameView {
	
    
	/**
	 * Constructor for GameView class
	 * @param gameScene 
	 * @param animation
	 * @param stage
	 * @param frame
	 */
	public GameView(Scene gameScene, Timeline animation, Stage stage, KeyFrame frame){
			
		stage.show();
		stage.setScene(gameScene);
		
		animation.setCycleCount(Timeline.INDEFINITE); //the animation will loop indefinitely
		animation.getKeyFrames().add(frame); //adds the input KeyFrame into the animation Timeline
		animation.play();

		
	}
	
	
		
}
