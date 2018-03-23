package uoa.compsys302.project.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
/**
 * This is the model class which contains the data relevant to a sinkhole in the game, as well
 * as getter and setter methods which allow this data to be accessed and modified.
 * @author Savi Mohan, Ira Syamira
 */
public class SinkHole {
	private Rectangle bounds;
	
	/**
     * overridden constructor that takes in the coordinate of the Sink Hole
     * @param xPos
     * @param yPos
     */
    public SinkHole(int xPos, int yPos){
		
    	bounds = new Rectangle(xPos,yPos,30,30);
    	Image img = new Image("file:src/Images/cave.png");
		bounds.setFill(new ImagePattern(img));
    	
	}
    
    /***
     * @return the bounds of the Sink Hole.
     */
    public Rectangle getBounds(){
    	return bounds;
    }

}
