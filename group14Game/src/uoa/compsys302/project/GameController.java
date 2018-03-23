package uoa.compsys302.project;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import uoa.compsys302.project.model.Ball;
import uoa.compsys302.project.model.FieldSpell;
import uoa.compsys302.project.model.Iceberg;
import uoa.compsys302.project.model.Paddle;
import uoa.compsys302.project.model.Player;
import uoa.compsys302.project.model.SinkHole;
import uoa.compsys302.project.model.Tornado;
import uoa.compsys302.project.model.Volcano;
import uoa.compsys302.project.model.Wall;
import warlordstest.IGame;

/**
 * This is the class which initialises the game model objects and packages the data into a Scene 
 * container for the GameView to plot. It controls the game flow via the tick() function.
 * 
 * @author Savi Mohan, Ira Syamira
 */
public class GameController implements IGame {
	
	protected Group root;
	private MasterViewsController masterViewsController;
	private int level;
	private int gameMode;
	private int humanPlayerCount;	
	private Text timerText;
	private Text fieldSpellText;
	private String stringTimer;
	private boolean pauseMenuEnabled = false;
	private boolean spawnBalls;
	private boolean isSinglePlayer;
	private Player singlePlayer; //this variable keeps track of the human player in single player mode
	private Player winner;
	Random random;		
	private Scene gameScene;	
	private boolean obstaclesFrozen, paddlesLocked, wallsInvisible;
	private boolean escapeButtonPressed;
	private boolean enterButtonPressed;
	private boolean isGameFinished;	
	private boolean gameIsTied;		
	private double timeRemaining;
	private final int frameRate = 48;
    private final double durationMilliseconds = 1000 / frameRate; //the delay between each call of the tick() function in milliseconds
        
	//ArrayLists that store instances of game Model objects
	ArrayList<Paddle> paddleList = new ArrayList<Paddle>();
	ArrayList<Ball> ballList = new ArrayList<Ball>();
	ArrayList<Player> playerList = new ArrayList<Player>();
	ArrayList<Wall> player1Walls = new ArrayList<Wall>();
	ArrayList<Wall> player2Walls = new ArrayList<Wall>();
	ArrayList<Wall> player3Walls = new ArrayList<Wall>();
	ArrayList<Wall> player4Walls = new ArrayList<Wall>();
	ArrayList<Iceberg> icebergList = new ArrayList<Iceberg>();
	ArrayList<Volcano> volcanoList = new ArrayList<Volcano>();
	ArrayList<Tornado> tornadoList = new ArrayList<Tornado>();
	ArrayList<SinkHole> sinkHoleList = new ArrayList<SinkHole>();	
	ArrayList<String> playerNames = new ArrayList<String>();	
	ArrayList<FieldSpell> fieldSpellsList = new ArrayList<FieldSpell>();
	
	/**
	 * Constructor for the GameController class
	 * @param masterViewsController Takes in an instance of MasterViewsController so that the GameController can switch scenes
	 * @param level Used to select the appropriate map and obstacles pertaining to a certain level  
	 * @param gameMode Determines whether game will be normal mode, no walls mode or 3 ball mode
	 * @param humanPlayerCount Used to determine how many players will be human
	 * @param isSinglePlayer Used to determine whether game will be single player or multiplayer
	 * @param playerNames list of player names to be displayed on the screen
	 */
	 	
	public GameController(MasterViewsController masterViewsController,int level, int gameMode,int humanPlayerCount,boolean isSinglePlayer, ArrayList<String> playerNames){	
		timeRemaining = 123.0;
		spawnBalls = true;
		obstaclesFrozen = false;
		paddlesLocked = false;
		wallsInvisible = false;
		escapeButtonPressed = false;
		enterButtonPressed = false;
		this.masterViewsController = masterViewsController;
		this.level = level;
		this.isSinglePlayer= isSinglePlayer;
		this.playerNames = playerNames;		
		gameIsTied = false;
		this.gameMode = gameMode;
		this.humanPlayerCount=humanPlayerCount;
	}
		
	/***
	 * Instantiates game model objects and adds them to a root node, then returns a scene container
	 * that is made from the root node.
	 * @param width The scene's width 
	 * @param height The scene's height 
     * @return The gameScene container that contains all the instantiated game model objects
     */	
	public Scene initialise(int width, int height) {
		
		root = new Group();
		gameScene = new Scene(root, width, height, Color.WHITE);
		
		Rectangle background = new Rectangle(0,0,1024,768);
		Image img=  new Image("file:src/Images/final_level-bg.jpg");	;
		switch(level){//chooses the appropriate background depending on what the level is
    	case 1:
    		//Fire Level
    		img = new Image("file:src/Images/fire-bg.jpg");	
			break;
		case 2:
			//Air Level
			img = new Image("file:src/Images/air-bg.jpg");		
			break;
		
		case 3:
			//Water Level
			img = new Image("file:src/Images/water-bg.jpg");		
			break;
		case 4:
			//Earth level
			img = new Image("file:src/Images/earth-bg.jpg");	
			break;
		case 5:
			//Aether Level
			img = new Image("file:src/Images/final_level-bg.jpg");
			break;	
		default:
			break;
			
    	}	
		
		background.setFill(new ImagePattern(img));
		root.getChildren().add(background);
		
		//game model objects are instantiated
		//Field Spells are created and added to fieldSpellsList
		FieldSpell speedUp = new FieldSpell(50,384,1);
		fieldSpellsList.add(speedUp);
		FieldSpell disableObstacles = new FieldSpell(512,668,2);
		fieldSpellsList.add(disableObstacles);
		FieldSpell lockPaddles = new FieldSpell(512,100,3);
		fieldSpellsList.add(lockPaddles);
		if(gameMode != 2){
			FieldSpell makeWallsDisappear = new FieldSpell(974,384,4);
			fieldSpellsList.add(makeWallsDisappear);
		} else { //if the game mode is the 'no walls' mode then there is no need for the wall disabling field spell and instead another speedup spell is created instead
			FieldSpell speedUp2 = new FieldSpell(974,384,1);
			fieldSpellsList.add(speedUp2);
		}		
		
		//players are added
		Player player1 = new Player(30,30);
		player1.getBounds().setFill(Color.RED);
		if(humanPlayerCount<1){
			player1.setIsHuman(false);
		}
		playerList.add(player1);
		
		
		
		Player player2 = new Player(30,738);
		player2.getBounds().setFill(Color.RED);
		if(humanPlayerCount<2){
			player2.setIsHuman(false);
		}
		
		playerList.add(player2);
		
		Player player3 = new Player(994,738);
		player3.getBounds().setFill(Color.RED);
		if(humanPlayerCount<3){
			player3.setIsHuman(false);
		}
		playerList.add(player3);
		
		Player player4 = new Player(994,30);
		player4.getBounds().setFill(Color.RED);
		if(humanPlayerCount<4){
			player4.setIsHuman(false);
		}
		playerList.add(player4);
		
		for(int i=0;i<playerList.size();i++){
			playerList.get(i).setPlayerName(playerNames.get(i));//sets the names of the players
		}
		
		//Add element images to player bases
		if(!isSinglePlayer){
			playerList.get(0).getBounds().setFill(new ImagePattern(new Image("file:src/Images/fire.png")));
			playerList.get(1).getBounds().setFill(new ImagePattern(new Image("file:src/Images/air.png")));
			playerList.get(2).getBounds().setFill(new ImagePattern(new Image("file:src/Images/water.png")));
			playerList.get(3).getBounds().setFill(new ImagePattern(new Image("file:src/Images/earth.png")));
		}		
		if(isSinglePlayer){
			if(level == 1){
				playerList.get(1).getBounds().setFill(new ImagePattern(new Image("file:src/Images/fire.png")));
				playerList.get(2).getBounds().setFill(new ImagePattern(new Image("file:src/Images/fire.png")));
				playerList.get(3).getBounds().setFill(new ImagePattern(new Image("file:src/Images/fire.png")));
			} else if(level == 2){
				playerList.get(1).getBounds().setFill(new ImagePattern(new Image("file:src/Images/air.png")));
				playerList.get(2).getBounds().setFill(new ImagePattern(new Image("file:src/Images/air.png")));
				playerList.get(3).getBounds().setFill(new ImagePattern(new Image("file:src/Images/air.png")));
			} else if(level == 3){
				playerList.get(1).getBounds().setFill(new ImagePattern(new Image("file:src/Images/water.png")));
				playerList.get(2).getBounds().setFill(new ImagePattern(new Image("file:src/Images/water.png")));
				playerList.get(3).getBounds().setFill(new ImagePattern(new Image("file:src/Images/water.png")));
			} else if(level == 4){
				playerList.get(1).getBounds().setFill(new ImagePattern(new Image("file:src/Images/earth.png")));
				playerList.get(2).getBounds().setFill(new ImagePattern(new Image("file:src/Images/earth.png")));
				playerList.get(3).getBounds().setFill(new ImagePattern(new Image("file:src/Images/earth.png")));
			} else if(level == 5){
				playerList.get(1).getBounds().setFill(new ImagePattern(new Image("file:src/Images/air.png")));
				playerList.get(2).getBounds().setFill(new ImagePattern(new Image("file:src/Images/water.png")));
				playerList.get(3).getBounds().setFill(new ImagePattern(new Image("file:src/Images/earth.png")));
			}
						
			singlePlayer = playerList.get(0);
			singlePlayer.getBounds().setFill(new ImagePattern(new Image("file:src/Images/fire.png")));
		}
		
		
		if(gameMode != 2){//checks if the game mode is not the 'no walls' mode
			//create walls for each player
			int[][] player1WallsCoords = { {120,0},{120,60},{120,120},{60,120},{0,120},{150,30},{150,90},{90,90},{90,150},{30,150} };
			for( int i = 0; i < player1WallsCoords.length; i++)
			{
			    player1Walls.add(new Wall(player1WallsCoords[i][0],player1WallsCoords[i][1]));
			    root.getChildren().add(player1Walls.get(i).getBounds());
			}
			
			int[][] player2WallsCoords = { {120,738},{120,678},{120,618},{60,618},{0,618},{150,708},{150,648},{90,648},{90,588},{30,588} };
			for( int i = 0; i < player2WallsCoords.length; i++)
			{
			    player2Walls.add(new Wall(player2WallsCoords[i][0],player2WallsCoords[i][1]));
			    root.getChildren().add(player2Walls.get(i).getBounds());
			}
			
			int[][] player3WallsCoords = { {874,738},{874,678},{874,618},{934,618},{994,618},{844,708},{844,648},{904,648},{904,588},{964,588} };
			for( int i = 0; i < player3WallsCoords.length; i++)
			{
			    player3Walls.add(new Wall(player3WallsCoords[i][0],player3WallsCoords[i][1]));
			    root.getChildren().add(player3Walls.get(i).getBounds());
			}
			
			int[][] player4WallsCoords = { {874,0},{874,60},{874,120},{934,120},{994,120},{844,30},{844,90},{904,90},{904,150},{964,150} };
			for( int i = 0; i < player4WallsCoords.length; i++)
			{
			    player4Walls.add(new Wall(player4WallsCoords[i][0],player4WallsCoords[i][1]));
			    root.getChildren().add(player4Walls.get(i).getBounds());
			}
		}
		
		//create ball
		Ball ball = new Ball();
		ball.getBounds().setFill(Color.RED);
		ball.setXPos(512);
		ball.setYPos(384);
		
		ArrayList<Integer> numbers=new ArrayList<Integer>();
		numbers.add(5);
		numbers.add(-5);
		//randomly determine the starting velocity direction of the ball
		Collections.shuffle(numbers);		
		ball.setXVelocity(numbers.get(0));
		Collections.shuffle(numbers);
        ball.setYVelocity(numbers.get(0));
        ballList.add(ball);
        if(gameMode == 3){//if the game mode is the '3 ball' mode then create two more balls
        	Ball secondBall = new Ball();
        	secondBall.getBounds().setFill(Color.RED);
        	secondBall.setXPos(512);
        	secondBall.setYPos(384);
        	secondBall.setXVelocity(numbers.get(1));
        	secondBall.setYVelocity(numbers.get(1));
        	ballList.add(secondBall);
        	Ball thirdBall = new Ball();
        	thirdBall.getBounds().setFill(Color.RED);
        	thirdBall.setXPos(512);
        	thirdBall.setYPos(384);
        	thirdBall.setXVelocity(numbers.get(0));
        	thirdBall.setYVelocity(-1*numbers.get(0));
        	ballList.add(thirdBall);
        }
		
        //create paddles
		Paddle paddle1 = new Paddle(10,30);
		paddle1.getBounds().setFill(Color.RED);
		paddle1.getBounds().getTransforms().add(new Rotate(45,25,5));
		paddle1.setXPos(250);
		paddle1.setYPos(5);
		paddleList.add(paddle1);
		
		Paddle paddle2 = new Paddle(10,30);
		paddle2.getBounds().setFill(Color.RED);
		paddle2.getBounds().getTransforms().add(new Rotate(-45,25,763));
		paddle2.setXPos(250);
		paddle2.setYPos(738);
		paddleList.add(paddle2);
		
		Paddle paddle3 = new Paddle(10,30);
		paddle3.getBounds().setFill(Color.RED);
		paddle3.getBounds().getTransforms().add(new Rotate(45,999,763));
		paddle3.setXPos(764);
		paddle3.setYPos(738);
		
		paddleList.add(paddle3);
		
		Paddle paddle4 = new Paddle(10,30);
		paddle4.getBounds().setFill(Color.RED);
		paddle4.getBounds().getTransforms().add(new Rotate(-45,999,5));
		paddle4.setXPos(764);
		paddle4.setYPos(5);
		
		paddleList.add(paddle4);
		
		//create obstacles depending on whether they're supposed to be in a certain game level or not
		if((level ==1)||(level ==5)){
			//volcano obstacle that will shoot fireballs at players
			Volcano volcano = new Volcano (512,384);
			volcanoList.add(volcano);
			root.getChildren().add(volcanoList.get(0).getBounds());
		}
		
		if((level ==2)||(level ==5)){
			//tornado obstacles that will randomly deviate balls from their paths
			Tornado tornado1 = new Tornado(512,236);
		    tornadoList.add(tornado1);
		    Tornado tornado2 = new Tornado(512,532);
		    tornadoList.add(tornado2);
		    root.getChildren().add(tornadoList.get(0).getBounds());
		    root.getChildren().add(tornadoList.get(1).getBounds());
		}
		
		if((level ==3)||(level ==5)){
			//moving iceberg obstacles that will deflect balls
			Iceberg iceberg1 = new Iceberg(260,260,2);
		    icebergList.add(iceberg1);
		    Iceberg iceberg2 = new Iceberg(764,260,2);
		    icebergList.add(iceberg2);
		    root.getChildren().add(icebergList.get(0).getBounds());
		    root.getChildren().add(icebergList.get(1).getBounds());
		}
	    
		if((level ==4)||(level ==5)){
			//sinkhole obstacles that will transport balls to other sinkholes on the map
			SinkHole sinkHole1 = new SinkHole(125,320);
		    sinkHoleList.add(sinkHole1);
		    SinkHole sinkHole2 = new SinkHole(590,125);
		    sinkHoleList.add(sinkHole2);
		    SinkHole sinkHole3 = new SinkHole(899,438);
		    sinkHoleList.add(sinkHole3);
		    SinkHole sinkHole4 = new SinkHole(434,643);
		    sinkHoleList.add(sinkHole4);
		    for (int i=0;i<4;i++){
		    	root.getChildren().add(sinkHoleList.get(i).getBounds());
		    }
		}	    
	    
		//used to display time left and initial countdown
	    timerText = new Text(420, 25, "Starting in 3");
	    timerText.setFill(Color.WHITE);
	    timerText.setFont(Font.font ("Verdana", 20));
	    
	    //used to display whether a certain field spell has been activated
	    fieldSpellText = new Text(420, 50, "");
	    fieldSpellText.setFill(Color.WHITE);
	    if(level == 2){
	    	fieldSpellText.setFill(Color.BLACK);
	    }
	    fieldSpellText.setFont(Font.font ("Verdana", 20));
	    
	    //used to display player names next to their respective bases
	    Text player1NameText = new Text(0, 85, player1.getPlayerName());
	    player1NameText.setFill(Color.WHITE);
	    player1NameText.setFont(Font.font ("Verdana", 20));
	    
	    Text player2NameText = new Text(0, 700, player2.getPlayerName());
	    player2NameText.setFill(Color.WHITE);
	    player2NameText.setFont(Font.font ("Verdana", 20));
	    
	    Text player3NameText = new Text(910, 700, player3.getPlayerName());
	    player3NameText.setFill(Color.WHITE);
	    player3NameText.setFont(Font.font ("Verdana", 20));
	    
	    Text player4NameText = new Text(910, 85, player4.getPlayerName());
	    player4NameText.setFill(Color.WHITE);
	    player4NameText.setFont(Font.font ("Verdana", 20));
	    
	    
	    //instantiated objects are added to the root node
	        
	    for (FieldSpell fieldSpell : fieldSpellsList){
	    	root.getChildren().add(fieldSpell.getBounds());
	    }
	    	
	    root.getChildren().add(timerText);
	    root.getChildren().add(fieldSpellText);
	    root.getChildren().add(player1NameText);
	    root.getChildren().add(player2NameText);
	    root.getChildren().add(player3NameText);
	    root.getChildren().add(player4NameText);
		
		root.getChildren().add(playerList.get(0).getBounds());
		root.getChildren().add(playerList.get(1).getBounds());
		root.getChildren().add(playerList.get(2).getBounds());
		root.getChildren().add(playerList.get(3).getBounds());
		root.getChildren().add(paddleList.get(0).getBounds());
		root.getChildren().add(paddleList.get(1).getBounds());
		root.getChildren().add(paddleList.get(2).getBounds());
		root.getChildren().add(paddleList.get(3).getBounds());
			
		// Keyboard handlers that carry out certain tasks depending on which keys have been pressed or released
		//we have both an OnKeyPressed Handler and an OnKeyReleased Handler so that we can move multiple paddles at the same time using the keyboard
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent ke) {
		        if (ke.getCode() == KeyCode.Q) {		        	
					paddleList.get(0).setMoveLeft(true);						
		        } else if (ke.getCode() == KeyCode.W) {		        	
					paddleList.get(0).setMoveRight(true);							
		        } else if (ke.getCode() == KeyCode.V) {
		        	paddleList.get(1).setMoveLeft(true);						
		        } else if (ke.getCode() == KeyCode.B) {
		        	paddleList.get(1).setMoveRight(true);						
		        } else if (ke.getCode() == KeyCode.LEFT) {
		        	paddleList.get(2).setMoveLeft(true);						
		        } else if (ke.getCode() == KeyCode.RIGHT) {
		        	paddleList.get(2).setMoveRight(true);						
		        } else if (ke.getCode() == KeyCode.MULTIPLY) {
		        	paddleList.get(3).setMoveLeft(true);						
		        } else if (ke.getCode() == KeyCode.SUBTRACT) {
		        	paddleList.get(3).setMoveRight(true);						
		        } else if (ke.getCode() == KeyCode.P) {
		        	pauseMenuEnabled = !pauseMenuEnabled;					
		        } else if (ke.getCode() == KeyCode.PAGE_DOWN) {
		        	timeRemaining = 0.0;					
		        } else if (ke.getCode() == KeyCode.ESCAPE){
		        	escapeButtonPressed = true;
		        } else if (ke.getCode() == KeyCode.ENTER){
		        	enterButtonPressed = true;
		        } 
		    }
		});

		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent ke) {
		    	if (ke.getCode() == KeyCode.Q) {
		        	paddleList.get(0).setMoveLeft(false);					
		        } else if (ke.getCode() == KeyCode.W) {		        	
					paddleList.get(0).setMoveRight(false);							
		        } else if (ke.getCode() == KeyCode.V) {
		        	paddleList.get(1).setMoveLeft(false);						
		        } else if (ke.getCode() == KeyCode.B) {
		        	paddleList.get(1).setMoveRight(false);						
		        } else if (ke.getCode() == KeyCode.LEFT) {
		        	paddleList.get(2).setMoveLeft(false);						
		        } else if (ke.getCode() == KeyCode.RIGHT) {
		        	paddleList.get(2).setMoveRight(false);						
		        } else if (ke.getCode() == KeyCode.MULTIPLY) {
		        	paddleList.get(3).setMoveLeft(false);						
		        } else if (ke.getCode() == KeyCode.SUBTRACT) {
		        	paddleList.get(3).setMoveRight(false);						
		        } else if (ke.getCode() == KeyCode.ESCAPE){
		        	escapeButtonPressed = false;
		        } else if (ke.getCode() == KeyCode.ENTER){
		        	enterButtonPressed = false;
		        }      
		    }
		});
		
		return gameScene;
	}	
	
	
	
	
	/***
     * This method advances the state of the game world by one frame. The ball (and all other
     *  objects) move according to their velocity and collisions are detected and processed
     */
    public void tick(){
    	
    	if(isGameFinished){//checks if the game is finished
    		if(isSinglePlayer){
    			if((singlePlayer.isDead())||(!singlePlayer.hasWon())){//if the game is finished in the single player mode and the player has not won, then display a 'DEFEAT' screen
    				timerText.setText("         DEFEAT \nPress Esc to exit game \nPress 'Enter' to restart level");
    	    		timerText.setX(380);
    	    		timerText.setY(250);
    	    		if(enterButtonPressed){//if the enter button is pressed, the player can retry the level    	    			
    	    			masterViewsController.goToGameScene(masterViewsController, level, 1, 1, true,playerNames);
    	    		} else if (escapeButtonPressed){//go back to the main menu if the Esc key is pressed
    	    			masterViewsController.goToMainMenu(masterViewsController);    	    			
    	    		}
    			} else if (singlePlayer.hasWon()){
    				//if the player has won then they proceed to the next level
    				masterViewsController.goToLevelsProgressScene(masterViewsController, level+1);
    				
    			}
    		}else{//if the game is in the multiplayer mode then the scene is switched to a separate exit screen
    			String gameMessage = "";
    			if(gameIsTied){
    				gameMessage = "The Game is Tied!";
    			}else{
    				gameMessage = "The Winner is "+ winner.getPlayerName();
    			}
    			
    			
	    		masterViewsController.goToMultiplayerExitScreen(masterViewsController, gameMessage);
    		}
    		return;
    	}
    	
    	isGameFinished = isFinished();
    	
    	if (pauseMenuEnabled){ //displays pause menu if pause button is pressed, and stops gameplay 
    		timerText.setText("         PAUSED \nPress Esc to exit game \nPress 'p' to resume");
    		timerText.setX(380);
    		timerText.setY(250);
    		if (escapeButtonPressed){//if the pause menu is being displayed and the Esc button is pressed then return to the main menu
    			masterViewsController.goToMainMenu(masterViewsController);    	    			
    		}
    		return;
    	}
    	
    	if(wallsInvisible){//if the 'disable walls' field spell is active then display the below message
    		fieldSpellText.setText("WALLS ARE DISABLED!");
    	}
    	
    	
    	if (timeRemaining>120.0){//displays 3,2,1 countdown timer at start of game 
    		stringTimer = Integer.toString(((int)timeRemaining)-119);
    		stringTimer = "Starting in "+ stringTimer;
    		timerText.setText(stringTimer);	
    		timerText.setX(420);
    		timerText.setY(25);
    		timeRemaining = timeRemaining - (durationMilliseconds/1000);
    		return;
    	}
    	if(spawnBalls){//spawns the balls once the game starts
    		root.getChildren().add(ballList.get(0).getBounds());
    		if(gameMode == 3){
    			root.getChildren().add(ballList.get(1).getBounds());
    			root.getChildren().add(ballList.get(2).getBounds());
    		}
    		spawnBalls = false;
    	}
    	//displays time left until end of level
    	stringTimer = Integer.toString(((int)timeRemaining));
    	stringTimer = "Time Remaining: "+stringTimer;
    	timerText.setText(stringTimer);
    	timerText.setX(420);
		timerText.setY(25);
    	timeRemaining = timeRemaining - (durationMilliseconds/1000);
    	
    	//carries out the obstacle movement functions as long as the 'obstacle freezing' field spell is not active 
    	if(!obstaclesFrozen){
	    	for(Iceberg iceberg: icebergList){//moves the icebergs up and down the screen
				iceberg.getBounds().setY(iceberg.getBounds().getY()+iceberg.getYVelocity());
				if((iceberg.getBounds().getY()<260)||(iceberg.getBounds().getY()>508)){
					iceberg.setYVelocity(-1*iceberg.getYVelocity());
				}
			}
			
			for(Tornado tornado: tornadoList){//rotates each tornado to create 'spinning' effect
				Rotate rotate = new Rotate(2, tornado.getBounds().getCenterX()+tornado.getBounds().getRadius(),tornado.getBounds().getCenterY()+tornado.getBounds().getRadius());
				tornado.getBounds().getTransforms().add(rotate);
			}
			for(Volcano volcano: volcanoList){//makes the volcano release fireballs every 10 seconds
				if (((int)timeRemaining<120)&&((int)timeRemaining>0)&&(volcano.getCoolDownTime()<=0)){
					if ((int)timeRemaining % 20 == 0 ){
						volcano.setCoolDownTime(10);//volcano can't release fireballs for another 10 seconds
						Ball fireBall = new Ball (512,384);//fireballs are spawned 
						fireBall.setIsFireBall(true);
						fireBall.setXVelocity(5);
						fireBall.setYVelocity(5);
						fireBall.getBounds().setFill(Color.ORANGE);
						ballList.add(fireBall);
						root.getChildren().add(fireBall.getBounds());
						Ball fireBall2 = new Ball (512,384);
						fireBall2.setIsFireBall(true);
						fireBall2.setXVelocity(-5);
						fireBall2.setYVelocity(-5);
						fireBall2.getBounds().setFill(Color.ORANGE);
						ballList.add(fireBall2);
						root.getChildren().add(fireBall2.getBounds());
					} else if((int)timeRemaining % 10 == 0 ){
						
						volcano.setCoolDownTime(10);
						Ball fireBall = new Ball (512,384);
						fireBall.setIsFireBall(true);
						fireBall.setXVelocity(5);
						fireBall.setYVelocity(-5);
						fireBall.getBounds().setFill(Color.ORANGE);
						ballList.add(fireBall);
						root.getChildren().add(fireBall.getBounds());
						Ball fireBall2 = new Ball (512,384);
						fireBall2.setIsFireBall(true);
						fireBall2.setXVelocity(-5);
						fireBall2.setYVelocity(5);
						fireBall2.getBounds().setFill(Color.ORANGE);
						ballList.add(fireBall2);
						root.getChildren().add(fireBall2.getBounds());
					}
				}
				volcano.setCoolDownTime(volcano.getCoolDownTime()-durationMilliseconds/1000);
			}
    	}else{
    		fieldSpellText.setText("OBSTACLES ARE FROZEN!");
    	}
    	
    	
    	//checks each player and if its an AI, determines the closest ball to the player and then sets the paddle to move to intercept that ball
    	if(!(playerList.get(0).getIsHuman())){
			Ball closestBall = findClosestBall(paddleList.get(0).getBounds());
			if(paddleList.get(0).getBounds().getX()>closestBall.getBounds().getCenterX()){
				paddleList.get(0).setMoveRight(false);
				paddleList.get(0).setMoveLeft(true);
			} else{
				paddleList.get(0).setMoveRight(true);
				paddleList.get(0).setMoveLeft(false);
			}    			
		}
		if(!(playerList.get(1).getIsHuman())){
			Ball closestBall = findClosestBall(paddleList.get(1).getBounds());
			if(paddleList.get(1).getBounds().getX()>closestBall.getBounds().getCenterX()){
				paddleList.get(1).setMoveRight(false);
				paddleList.get(1).setMoveLeft(true);
			} else{
				paddleList.get(1).setMoveRight(true);
				paddleList.get(1).setMoveLeft(false);
			}    			
		}
		if(!(playerList.get(2).getIsHuman())){
			Ball closestBall = findClosestBall(paddleList.get(2).getBounds());
			if(paddleList.get(2).getBounds().getX()<closestBall.getBounds().getCenterX()){
				paddleList.get(2).setMoveRight(true);
				paddleList.get(2).setMoveLeft(false);
			} else{
				paddleList.get(2).setMoveRight(false);
				paddleList.get(2).setMoveLeft(true);
			}    			
		}
		if(!(playerList.get(3).getIsHuman())){
			Ball closestBall = findClosestBall(paddleList.get(3).getBounds());
			if(paddleList.get(3).getBounds().getX()<closestBall.getBounds().getCenterX()){
				paddleList.get(3).setMoveRight(true);
				paddleList.get(3).setMoveLeft(false);
			} else{
				paddleList.get(3).setMoveRight(false);
				paddleList.get(3).setMoveLeft(true);
			}    			
		}					
    		
    	//if the 'paddle lock' field spell is not active, checks each paddle to see whether it needs to be moved left or right
		//does not allow a paddle to move if it would exceed the bounds of the game
		if(!paddlesLocked){
	    	if(paddleList.get(0).getMoveLeft()&&(paddleList.get(0).getRotate()>-45)){			
				paddleList.get(0).getBounds().getTransforms().add(new Rotate(3, 25, 5));//rotates the paddle so that it moves along a circular path
	    		paddleList.get(0).setRotate(paddleList.get(0).getRotate()-3);
	    		
			} else if(paddleList.get(0).getMoveRight()&&(paddleList.get(0).getRotate()<45)){			
				paddleList.get(0).getBounds().getTransforms().add(new Rotate(-3, 25, 5));
				paddleList.get(0).setRotate(paddleList.get(0).getRotate()+3);
				
			}
	    	if(paddleList.get(1).getMoveLeft()&&(paddleList.get(1).getRotate()>-45)){    			
	    		paddleList.get(1).getBounds().getTransforms().add(new Rotate(-3, 25, 763));
	    		paddleList.get(1).setRotate(paddleList.get(1).getRotate()-3);    		
	    	} else if(paddleList.get(1).getMoveRight()&&(paddleList.get(1).getRotate()<45)){    		
	    		paddleList.get(1).getBounds().getTransforms().add(new Rotate(3, 25, 763));
	    		paddleList.get(1).setRotate(paddleList.get(1).getRotate()+3);    		
	    	}
	    	if(paddleList.get(2).getMoveLeft()&&(paddleList.get(2).getRotate()>-45)){			
				paddleList.get(2).getBounds().getTransforms().add(new Rotate(-3, 999, 763));
				paddleList.get(2).setRotate(paddleList.get(2).getRotate()-3);			
			} else if(paddleList.get(2).getMoveRight()&&(paddleList.get(2).getRotate()<45)){			
				paddleList.get(2).getBounds().getTransforms().add(new Rotate(3, 999, 763));
				paddleList.get(2).setRotate(paddleList.get(2).getRotate()+3);			
			}
	    	if(paddleList.get(3).getMoveLeft()&&(paddleList.get(3).getRotate()>-45)){			
				paddleList.get(3).getBounds().getTransforms().add(new Rotate(3, 999, 5));
				paddleList.get(3).setRotate(paddleList.get(3).getRotate()-3);			
			} else if(paddleList.get(3).getMoveRight()&&(paddleList.get(3).getRotate()<45)){			
				paddleList.get(3).getBounds().getTransforms().add(new Rotate(-3, 999, 5));
				paddleList.get(3).setRotate(paddleList.get(3).getRotate()+3);			
			}
		}else{
			fieldSpellText.setText("PADDLES ARE LOCKED!");
		}
    	
    	
    	Iterator<Ball> iter = ballList.iterator();//loops through every ball in the game	
    	//we're using an iterator instead of a for-loop so that we can remove objects from the list while looping through it
    	while (iter.hasNext()) {
    		Ball ball = iter.next();
    		//determines the number of positions that need to checked from the current position to the position at the end of the tick
    		int iterations = Math.min(Math.abs(ball.getXVelocity()), Math.abs(ball.getYVelocity()));
    	
    		for (int i=0;i< iterations;i++) {
    			
    			
    			if(ball.getIterationsSinceLastCollision()>=1){
    				ball.setIterationsSinceLastCollision(ball.getIterationsSinceLastCollision()-1);
    			} 
    			//move the ball by a y-increment, then if a collision occurs we know that the object was 
    			//hit on the top or the bottom(depending on if yVelocity is positive or negative)
    			ball.setYPos(ball.getYPos()+(ball.getYVelocity()/iterations));
	    		boolean yDirectionCollision = checkBallCollision(ball);
	    		if ((ball.getYPos()<=0)||(ball.getYPos()>=768)||(yDirectionCollision)){
	    			//if the ball exceeds the y-boundaries or collides in the y-direction, reverse the ball's y-Velocity
	    			ball.setYVelocity(-1*ball.getYVelocity());
	    			ball.setYPos(ball.getYPos()+(ball.getYVelocity()));
	    			if(ball.getIsFireBall()&&(yDirectionCollision)){//if a volcanic fireball collides with anything other than the bounds of the game window then it disappears
	    				root.getChildren().remove(ball.getBounds());
	    				iter.remove();
	    			}
	    			break;
	    			
	    		}
    			
	    		//move the ball by a x-increment, then if a collision occurs we know that the object was 
    			//hit on the left or the right(depending on if xVelocity is positive or negative)
    			ball.setXPos(ball.getXPos()+(ball.getXVelocity()/iterations));
    			boolean xDirectionCollision = checkBallCollision(ball);
    			if ((ball.getXPos()<=0)||(ball.getXPos()>=1024)||(xDirectionCollision)){
    				//if the ball exceeds the x-boundaries or collides in the x-direction, reverse the ball's x-Velocity
    				ball.setXVelocity(-1*ball.getXVelocity());
    				ball.setXPos(ball.getXPos()+(ball.getXVelocity()));
    				if(ball.getIsFireBall()&&(xDirectionCollision)){
	    				root.getChildren().remove(ball.getBounds());
	    				iter.remove();
	    			}
    				break;
    				
    			}    		
	    		
    		}
    		
	    		
    	}
    }
    /**
     * Takes in a paddle object and determines the closest ball to it
     * @param paddle
     * @return the Ball object that is closest to the given paddle
     */
    public Ball findClosestBall(Rectangle paddle){
    	Ball closestBall=new Ball();
    	double distanceToClosestBall=10000000;
		for (Ball ball : ballList) {
			double distance = Math.sqrt((Math.pow(ball.getXPos()-paddle.getX(), 2)+Math.pow(ball.getYPos()-paddle.getY(), 2)));
			if (distance < distanceToClosestBall){
				distanceToClosestBall = distance;
				closestBall = ball; 
			}
		}
    	return closestBall;
    }
    
    /***
     * This method is called by the tick() function and is used to determine whether a ball has collided 
     * with any other objects in the game.
     * @param ball
     * @return true if a collision has occurred, false otherwise.
     */
    public boolean checkBallCollision(Ball ball){
    	   	
    	for (Paddle paddle : paddleList){			
			if (ball.getBounds().getBoundsInParent().intersects(paddle.getBounds().getBoundsInParent()))
			{
				if(ball.getIterationsSinceLastCollision()<=0){
					ball.setIterationsSinceLastCollision(60);//ensures that the ball will not collide with this paddle again for a set number of ticks to avoid double reflections
    				AudioClip laser = new AudioClip("file:src/Sounds/laser.mp3");//plays appropriate sound for paddle collision
    				laser.play();
					
					return true;
				} 				
			}
		}
    	for (FieldSpell fieldSpell : fieldSpellsList){
    		if(fieldSpell.getCoolDownTime()>=0){
    			fieldSpell.setCoolDownTime(fieldSpell.getCoolDownTime()-(durationMilliseconds/1000));
    			if((fieldSpell.getCoolDownTime()<=150.0)&&((fieldSpell.getCoolDownTime()>=140.0))){//determines if a field spell's effect is currently active and needs to have its effects ended
    				fieldSpellText.setText("");
    				switch (fieldSpell.getType()) {
        			case 1:// return any sped up balls back to their original velocity
        				for(Ball speedBall:ballList){
        					if(Math.abs(speedBall.getXVelocity())>=10){
        						speedBall.setXVelocity(speedBall.getXVelocity()/2);
        						speedBall.setYVelocity(speedBall.getYVelocity()/2);
        					}
        				}
        				break;
        			case 2://disable obstacles
        				obstaclesFrozen = false;		
        				break;
        			
        			case 3://unlock paddles
        				paddlesLocked = false;	
        				break;
        			case 4://makes walls reappear
        				wallsInvisible = false;	
        				for(Wall wall: player1Walls){
        					wall.getBounds().setFill(Color.GREEN);
        				}
        				for(Wall wall: player2Walls){
        					wall.getBounds().setFill(Color.GREEN);
        				}
        				for(Wall wall: player3Walls){
        					wall.getBounds().setFill(Color.GREEN);
        				}
        				for(Wall wall: player4Walls){
        					wall.getBounds().setFill(Color.GREEN);
        				}
        				break;		
        			default:
        				break;
        			}	
    				
    			}else if(fieldSpell.getCoolDownTime()<=0){
    				fieldSpell.initialise();//makes a field spell reappear after a set cooldown period
    			}
    		}		
    		
    		//checks if the ball intersects a field spell, if it does then that field spell's effect can be activated given that its cool down time is 0
    		if ((ball.getBounds().getBoundsInParent().intersects(fieldSpell.getBounds().getBoundsInParent()))&&(fieldSpell.getCoolDownTime()<=0)){
    			AudioClip laser = new AudioClip("file:src/Sounds/power_up.mp3");
				laser.play();
				
    			fieldSpell.getBounds().setFill(Color.TRANSPARENT);
    			fieldSpell.setCoolDownTime(200.0);//sets cooldown period before this field spell can be activated again
    			switch (fieldSpell.getType()) {
    			case 1:// speedup spell
    				ball.setXVelocity(ball.getXVelocity()*2);
    				ball.setYVelocity(ball.getYVelocity()*2);
    				fieldSpellText.setText("BALL SPEED INCREASED!");
    				break;
    			case 2://disable obstacles
    				obstaclesFrozen = true;	
    				fieldSpellText.setText("OBSTACLES ARE FROZEN");
    				break;
    			
    			case 3://paddles lock
    				paddlesLocked = true;	
    				fieldSpellText.setText("PADDLES ARE LOCKED!");
    				break;
    			case 4://makes walls temporarily disappear
    				fieldSpellText.setText("WALLS ARE DISABLED!");
    				wallsInvisible = true;	
    				for(Wall wall: player1Walls){
    					wall.getBounds().setFill(Color.TRANSPARENT);
    				}
    				for(Wall wall: player2Walls){
    					wall.getBounds().setFill(Color.TRANSPARENT);
    				}
    				for(Wall wall: player3Walls){
    					wall.getBounds().setFill(Color.TRANSPARENT);
    				}
    				for(Wall wall: player4Walls){
    					wall.getBounds().setFill(Color.TRANSPARENT);
    				}
    				break;		
    			default:
    				break;
    			}	
    		}
	    }
		for (Player player : playerList){
			//checks if the ball intersects any player's base
			if ((ball.getBounds().getBoundsInParent().intersects(player.getBounds().getBoundsInParent()))&&(!player.isDead()))
			{
				
				player.kill();//destroy the player if a collision occurs with the ball
				//the player's base's image is changed to a 'skull and crossbones' to show that the player has died(but can still continue to use powerups)
				player.getBounds().setFill(new ImagePattern(new Image("file:src/Images/skull.png")));
				
				AudioClip laser = new AudioClip("file:src/Sounds/explosion.mp3");
				laser.play();
				return true;
			}	    			
		}
		
		//loops through player one's walls
		Iterator<Wall> iter = player1Walls.iterator();	
    	while (iter.hasNext()) {
    		Wall wall = iter.next();
    		//if the ball collides with a wall and the wall disabling field spell is not active then the wall is 'destroyed' by being removed from the root and the wallList
			if ((ball.getBounds().getBoundsInParent().intersects(wall.getBounds().getBoundsInParent()))&&(!wallsInvisible))
			{				
				wall.destroyWall();
				root.getChildren().remove(wall.getBounds());
				iter.remove();
				AudioClip laser = new AudioClip("file:src/Sounds/laser.mp3");
				laser.play();
				return true;
			}	    			
		}
    	//loops through player two's walls
    	Iterator<Wall> iter2 = player2Walls.iterator();	
    	while (iter2.hasNext()) {
    		Wall wall = iter2.next();	
			if ((ball.getBounds().getBoundsInParent().intersects(wall.getBounds().getBoundsInParent()))&&(!wallsInvisible))
			{
				
				wall.destroyWall();
				root.getChildren().remove(wall.getBounds());
				iter2.remove();
				AudioClip laser = new AudioClip("file:src/Sounds/laser.mp3");
				laser.play();
				return true;
			}	    			
		}
    	//loops through player three's walls
    	Iterator<Wall> iter3 = player3Walls.iterator();	
    	while (iter3.hasNext()) {
    		Wall wall = iter3.next();
			if ((ball.getBounds().getBoundsInParent().intersects(wall.getBounds().getBoundsInParent()))&&(!wallsInvisible))
			{
				
				wall.destroyWall();
				root.getChildren().remove(wall.getBounds());
				iter3.remove();
				AudioClip laser = new AudioClip("file:src/Sounds/laser.mp3");
				laser.play();
				return true;
			}	    			
		}
    	//loops through player four's walls
    	Iterator<Wall> iter4 = player4Walls.iterator();	
    	while (iter4.hasNext()) {
    		Wall wall = iter4.next();
			if ((ball.getBounds().getBoundsInParent().intersects(wall.getBounds().getBoundsInParent()))&&(!wallsInvisible))
			{
				
				wall.destroyWall();
				root.getChildren().remove(wall.getBounds());
				iter4.remove();
				AudioClip laser = new AudioClip("file:src/Sounds/laser.mp3");
				laser.play();
				return true;
			}	    			
		}
    	
    	//checks if the ball collides with any of the obstacles
		for(Iceberg iceberg: icebergList){
			if (ball.getBounds().getBoundsInParent().intersects(iceberg.getBounds().getBoundsInParent()))
			{				
				AudioClip laser = new AudioClip("file:src/Sounds/ice_thud.mp3");
				laser.play();

				return true;
			}				
		}
		int sinkHoleIndex = 0;
		if(sinkHoleList.size()>2){
			sinkHoleIndex = 1;
		}
		
		for(SinkHole sinkHole :sinkHoleList){
			
			sinkHoleIndex = sinkHoleIndex + 1;
						
			if(sinkHoleIndex==(sinkHoleList.size())){
				sinkHoleIndex = 0;
			}
			if (ball.getBounds().getBoundsInParent().intersects(sinkHole.getBounds().getBoundsInParent()))
			{//if the ball hits a sinkhole then transport the ball to the sinkhole opposite this current sinkhole				
				if(ball.getIterationsSinceLastCollision()<=0){
					ball.setXPos((int)sinkHoleList.get(sinkHoleIndex).getBounds().getX());
					ball.setYPos((int)sinkHoleList.get(sinkHoleIndex).getBounds().getY());
					ball.setIterationsSinceLastCollision(50);
    				AudioClip laser = new AudioClip("file:src/Sounds/cave.mp3");
    				laser.play();

					return true;
				}
				
			}	  
		}
		for(Tornado tornado: tornadoList){
			if ((ball.getBounds().getBoundsInParent().intersects(tornado.getBounds().getBoundsInParent()))&&(ball.getCollidedWithTornado())){
				
			} else{
				ball.setCollidedWithTornado(false);
				if (ball.getBounds().getBoundsInParent().intersects(tornado.getBounds().getBoundsInParent()))
				{				
					if(ball.getIterationsSinceLastCollision()<=0){
						ArrayList<Double> numbers=new ArrayList<Double>();
						numbers.add(1.0);
						numbers.add(-1.0);
						numbers.add(1.05);
						numbers.add(-1.05);
						//ball direction is determined randomly
						Collections.shuffle(numbers);		
						ball.setXVelocity((int)(numbers.get(0)*ball.getXVelocity()));
						Collections.shuffle(numbers);
				        ball.setYVelocity((int)(numbers.get(0)*ball.getYVelocity()));
						
						ball.setIterationsSinceLastCollision(50);
						
						ball.setCollidedWithTornado(true);//ensures that the ball does not get stuck inside the tornado object
	    				AudioClip laser = new AudioClip("file:src/Sounds/wind.mp3");
	    				laser.play();

						return true;
					}
					
				}	  
			}
				
				
			
		}
		
		return false;
    }
    
    
	
	/***
     * add a paddle to the paddleList
     * @param paddle 
     */
    public void addPaddle(Paddle paddle){
    	paddleList.add(paddle);
    }
	
    /***
     * add a ball to the ballList
     * @param ball 
     */
    public void addBall(Ball ball){
    	ballList.add(ball);
    }
    
    /***
     * add a player to the playerList
     * @param player 
     */
    public void addPlayer(Player player){
    	playerList.add(player);
    }
    
    /***
     * add a wall to the player1Walls list
     * @param wall
     */
    public void addPlayer1Wall(Wall wall){
    	player1Walls.add(wall);
    }
    
    /***
     * add a wall to the player2Walls list
     * @param wall
     */
    public void addPlayer2Wall(Wall wall){
    	player2Walls.add(wall);
    }
    /***
     * add a wall to the player3Walls list
     * @param wall
     */
    public void addPlayer3Wall(Wall wall){
    	player3Walls.add(wall);
    }
    /***
     * add a wall to the player4Walls list
     * @param wall
     */
    public void addPlayer4Wall(Wall wall){
    	player4Walls.add(wall);
    }
    
	

    /***
     * Determine if the game has finished. Results are only be valid before the start and after the end of a game tick.
     *
     * @return true if no more than one player remains alive or if the human player in single player mode dies
     * or if the time remaining is less than or equal to zero. Otherwise, return false.
     */
    public boolean isFinished(){
    	int aliveCount = 0;
    	for (Player gamePlayer : playerList){
    		if (!gamePlayer.isDead()){
    			aliveCount +=1;
    		}    		 
    	}
    	if(aliveCount <= 1){//if only one player remains alive, make that player the winner
    		for (Player gamePlayer : playerList){
        		if (!gamePlayer.isDead()){
        			gamePlayer.makeWinner();
        			winner = gamePlayer;
        		}    		 
        	}
    		return true;
    	}
    	
    	if(isSinglePlayer){
    		if (singlePlayer.isDead()){
    			return true; //In single player mode, level ends once human player dies
    		}
    	}
    	
    	
    	if (timeRemaining <= 0.0){//if time runs out, check which player has the most walls remaining and make that player the winner
    		//if a player is dead, then their walls are not considered in the win calculation
    		if(playerList.get(0).isDead()){
    			player1Walls = new ArrayList<Wall>();
    		} 
    		if(playerList.get(1).isDead()){
    			player2Walls = new ArrayList<Wall>();
    		}
    		if(playerList.get(2).isDead()){
    			player3Walls = new ArrayList<Wall>();
    		}
    		if(playerList.get(3).isDead()){
    			player4Walls = new ArrayList<Wall>();
    		}
    		
    		//check which alive player has the most walls
    		if((!playerList.get(0).isDead())&&(player1Walls.size()>Math.max(player2Walls.size(),Math.max(player3Walls.size(),player4Walls.size())))){
    			playerList.get(0).makeWinner();
    			winner = playerList.get(0);
    			
    		} else if ((!playerList.get(1).isDead())&&(player2Walls.size()>Math.max(player2Walls.size(),Math.max(player3Walls.size(),player4Walls.size())))){
    			playerList.get(1).makeWinner();
    			winner = playerList.get(1);
    			
    		} else if ((!playerList.get(2).isDead())&&(player3Walls.size()>Math.max(player1Walls.size(),Math.max(player2Walls.size(),player4Walls.size())))){
    			playerList.get(2).makeWinner();
    			winner = playerList.get(2);
    			
    		} else if ((!playerList.get(3).isDead())&&(player4Walls.size()>Math.max(player2Walls.size(),Math.max(player3Walls.size(),player1Walls.size())))){
    			playerList.get(3).makeWinner();
    			winner = playerList.get(3);
    			
    		}
    		if(winner == null){//if there is no winner, then the game is tied
    			
    			gameIsTied = true;
    		}
    			
    		return true;
    	}
    	
    	return false;
    		
    }

    /***
     * Set the time remaining in the game to the given value in seconds.
     *
     * @param seconds
     */
    public void setTimeRemaining(double seconds){
    	timeRemaining = seconds;
    }
    
    /***
     * Get the time remaining in the game in seconds.
     *
     * 
     */
    public double getTimeRemaining(){
    	return timeRemaining;
    }
    
    
	
    
}
