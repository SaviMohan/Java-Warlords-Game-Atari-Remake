package uoa.compsys302.project.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
/**
 * This is the model class which contains the data relevant to an iceberg in the game, as well
 * as getter and setter methods which allow this data to be accessed and modified.
 * @author Savi Mohan, Ira Syamira
 */
public class Iceberg {
	private Rectangle bounds;
	private int yVelocity=0;
	
	/**
     * overridden constructor that takes in the coordinates and y-velocity of the Iceberg
     * @param xPos
     * @param yPos
     */
    public Iceberg(int xPos, int yPos, int yVelocity){
		
    	bounds = new Rectangle(xPos,yPos,50,30);
    	Image img = new Image("file:src/Images/iceberg.png");
		bounds.setFill(new ImagePattern(img));
		bounds.setArcHeight(15);
		bounds.setArcWidth(15);
    	this.yVelocity = yVelocity;
	}
    
    /***
     * @return the bounds of the Iceberg.
     */
    public Rectangle getBounds(){
    	return bounds;
    }
    
    /***
     *  Set the vertical velocity of the iceberg to the given value.
     * @param dY
     */
    public void setYVelocity(int dY){
    	yVelocity = dY;
    }

    /***
     * @return the vertical velocity of the iceberg.
     */
    public int getYVelocity(){
    	return yVelocity;
    }
}
