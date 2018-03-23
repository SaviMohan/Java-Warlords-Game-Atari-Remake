package uoa.compsys302.project.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
/**
 * This is the model class which contains the data relevant to a volcano in the game, as well
 * as getter and setter methods which allow this data to be accessed and modified.
 * @author Savi Mohan, Ira Syamira
 */
public class Volcano {
	private Circle bounds;
	private double coolDownTime;
	
	/**
     * overridden constructor that takes in the coordinates of the Volcano
     * @param xPos
     * @param yPos
     */
    public Volcano(int xPos, int yPos){
		
    	//bounds = new Rectangle(xPos,yPos,30,30);
    	bounds = new Circle(xPos,yPos,50);
    	Image img = new Image("file:src/Images/oie_transparent.png");
		bounds.setFill(new ImagePattern(img));
		coolDownTime= 0;
	}
    
    /***
     * @return the bounds of the Volcano.
     */
    public Circle getBounds(){
    	return bounds;
    }
    
    /***
     * @return the cool down time of the volcano before it can release more fire balls.
     */
    public double getCoolDownTime(){
    	return this.coolDownTime;
    }

    /***
     * Sets the amount of time before the volcano can release fire balls again.
     * @param coolDownTime
     */
    public void setCoolDownTime(double coolDownTime){
    	this.coolDownTime = coolDownTime;
    }
}
