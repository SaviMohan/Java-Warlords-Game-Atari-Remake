package uoa.compsys302.project;

import java.nio.file.Paths;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * The game is started by initialising the MasterViewsController class which
 * will control the displaying and switching of views
 * @author Savi Mohan, Ira Syamira
 */
public class MainApp extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Out Of Your Element"); //the name of the game
		
		MasterViewsController masterViewsController = new MasterViewsController(primaryStage);
		
		masterViewsController.goToMainMenu(masterViewsController);//on startup go to the main menu
		music();
	}
	MediaPlayer mediaPlayer;
	public void music(){//game music that loops indefinitely
	    String bip = "src/Sounds/EpicMusic_lowVol.mp3";
	    Media hit = new Media(Paths.get(bip).toUri().toString());
	    mediaPlayer = new MediaPlayer(hit);
	    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	    mediaPlayer.play();
		
		 
	}
	
	

	
	public static void main(String[] args) {
		launch(args);
	}
	

}
