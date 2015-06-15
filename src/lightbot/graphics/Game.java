package lightbot.graphics;

import java.util.ArrayList;

import lightbot.system.CardinalDirection;
import lightbot.system.Colour;
import lightbot.system.RelativeDirection;
import lightbot.system.Robot;
import lightbot.system.action.Turn;
import lightbot.system.world.Grid;
import lightbot.tests.Main;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;

public class Game implements DisplayMode{
	
	public ArrayList<Sprite> toDisplay;
	
	private Button turnLeftButton;
	private Button turnRightButton;
	
	private Display display;
	
	private int originX;
	private int originY;
	
	/********************************************************************************************/
	/*										Constructors										*/
	/********************************************************************************************/
	
	public Game(Grid grid){
		originX = (750/2)+15;
		originY = (475-(grid.getSize()*Textures.cellTexture.getSize().y));
		
		toDisplay = new ArrayList<Sprite>();
	
		initConstantDisplay();
		
		display = new Display(grid, originX, originY);
	}
	
	/**
	 * Initialize the constant display for a game 
	 */
	public void initConstantDisplay(){
		Sprite turnLeftSprite = new Sprite(Textures.rotateLeft);
		turnLeftSprite.setPosition(50, (490-30-Textures.rotateLeft.getSize().y));
		
		Sprite turnRightSprite = new Sprite(Textures.rotateRight);
		turnRightSprite.setPosition((765-35-Textures.rotateRight.getSize().y), (490-30-Textures.rotateRight.getSize().y));
		
		turnLeftButton = new Button(turnLeftSprite, null, null);
		turnRightButton = new Button(turnRightSprite, null, null);
		
		toDisplay.add(turnLeftSprite);
		toDisplay.add(turnRightSprite);
	}
	
	/**
	 * Get the constant display
	 * @return A list of Sprite
	 */
	public ArrayList<Sprite> getConstantDisplay(){return this.toDisplay;}
	
	/**
	 * Display the game interface
	 */
	public void display(){
		for(Sprite s : toDisplay)
			Main.window.draw(s);
		display.print();
	}
	
	public void printGrid(){
		display.createGridAnim();
	}
	
	public void deleteGrid(){
		display.deleteGridAnim();
	}
	
	/**
	 * Get the grid from the game
	 */
	public GridDisplay getGrid(){
		return this.display.gridDisplay;
	}
	
	public CellPosition isInside(Vector2i coord){
		return new CellPosition(0, 0, 0, false);
	}

	public void eventManager(Event event) {
		Turn turn;
		if(event.type == Event.Type.KEY_PRESSED){
			switch(event.asKeyEvent().key){
				case UP:
					display.anim.moveRobot(CardinalDirection.NORTH, 0);
					Robot.wheatley.setLine(Robot.wheatley.getLine()-1);
					break;
					
				case DOWN:
					display.anim.moveRobot(CardinalDirection.SOUTH, 0);
					Robot.wheatley.setLine(Robot.wheatley.getLine()+1);
					break;
					
				case LEFT:
					display.anim.moveRobot(CardinalDirection.WEST, 0);
					Robot.wheatley.setColumn(Robot.wheatley.getColumn()-1);
					break;
					
				case RIGHT:
					
					display.anim.moveRobot(CardinalDirection.EAST, 0);
		       	 Robot.wheatley.setColumn(Robot.wheatley.getColumn()+1);
					break;
					
				case PAGEUP:
					
					display.anim.moveRobot(CardinalDirection.EAST, 2);
					break;
					
				case PAGEDOWN:
					
					display.anim.moveRobot(CardinalDirection.EAST, 1);
					break;
					
				case NUMPAD6:
					turn = new Turn(RelativeDirection.RIGHT, Colour.WHITE);
					turn.execute(null, Robot.wheatley);
					break;
					
				default:
					turn = new Turn(RelativeDirection.LEFT, Colour.WHITE);
					turn.execute(null, Robot.wheatley);
					break;
				
			}
			display.robotDisplay.updateRobot(Robot.wheatley, 255);
			display.anim.updateRobot(display.robotDisplay.robotSprite);
			System.out.println("Robot --> l : "+Robot.wheatley.getLine()+", c : "+Robot.wheatley.getColumn());
		}
		if(event.type == Event.Type.MOUSE_BUTTON_PRESSED){
			MouseButtonEvent mouse = event.asMouseButtonEvent();
			if(mouse.button == Mouse.Button.LEFT){
				if(turnLeftButton.isInside(mouse.position)){
					display.rotate(0);
				}
				else if(turnRightButton.isInside(mouse.position)){
					display.rotate(1);
				}
			}
		}
	}
}
