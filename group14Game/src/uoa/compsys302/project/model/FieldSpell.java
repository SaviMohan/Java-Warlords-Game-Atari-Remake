package uoa.compsys302.project.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
/**
 * This is the model class which contains the data relevant to a field spell in the game, as well
 * as getter and setter methods which allow this data to be accessed and modified.
 * @author Savi Mohan, Ira Syamira
 */
public class FieldSpell {
	
	private int type;
	private Circle bounds;
	private double coolDownTime;
	/**
	 * Constructor for the FieldSpell class that takes in the coordinates and type of the field spell
	 * @param xPos
	 * @param yPos
	 * @param type
	 */
	public FieldSpell(int xPos, int yPos, int type){
		
		this.type = type;
		bounds = new Circle(xPos,yPos,15);
		initialise();
		coolDownTime = 0;
			
	}
	/***
     *  Sets the cool down time for the field spell before it can reappear on the game field again.
     * @param coolDownTime
     */
    public void setCoolDownTime(double coolDownTime){
    	this.coolDownTime=coolDownTime;
    	
    }
    
    /***
     * @return the cool down time of the field spell.
     * 
     */
    public double getCoolDownTime(){
    	return this.coolDownTime;
    }
	
	/**
	 * 
	 * @return the bounds of the field spell
	 */
	public Circle getBounds(){
		return bounds;
	}
	
	/**
	 * 
	 * @return the type of field spell this object is
	 */
	public int getType(){
		return this.type;
	}
	
	/**
	 * sets the type of field spell this object is
	 * @param type
	 */
	public void setType(int type){
		this.type = type;
	}
	
	/**
	 * initialise this object depending on what type of field spell its supposed to be
	 */
	public void initialise(){
		switch (type) {
		case 1:// speedup spell
			Image img = new Image("file:src/Images/speedup.png");
			bounds.setFill(new ImagePattern(img));		
			break;
		case 2://disable obstacles
			Image img2 = new Image("file:src/Images/obstaclefreeze.png");
			bounds.setFill(new ImagePattern(img2));			
			break;
		
		case 3://paddles lock
			Image img3 = new Image("file:src/Images/lockpaddle.png");
			bounds.setFill(new ImagePattern(img3));			
			break;
		case 4://makes walls temporarily disappear
			Image img4 = new Image("file:src/Images/disablewall.png");
			bounds.setFill(new ImagePattern(img4));			
			break;		
		default:
			break;
		}	
	}
	
	

}
