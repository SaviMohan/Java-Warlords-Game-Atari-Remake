package uoa.compsys302.project.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
/**
 * This is the model class which contains the data relevant to a tornado in the game, as well
 * as getter and setter methods which allow this data to be accessed and modified.
 * @author Savi Mohan, Ira Syamira
 */
public class Tornado {
	private Circle bounds;
	
	/**
     * overridden constructor that takes in the coordinate of the Tornado
     * @param xPos
     * @param yPos
     */
    public Tornado(int xPos, int yPos){
		
    	
    	bounds = new Circle(xPos,yPos,20);
    	Image img = new Image("file:src/Images/tornado.png");
		bounds.setFill(new ImagePattern(img));
    	
	}
    
    /***
     * @return the bounds of the Tornado.
     */
    public Circle getBounds(){
    	return bounds;
    }
}
