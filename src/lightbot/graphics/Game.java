package lightbot.graphics;

import java.util.ArrayList;

import lightbot.system.CardinalDirection;
import lightbot.system.Robot;
import lightbot.system.world.Grid;
import lightbot.tests.Main;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;

public class Game implements DisplayMode{
	
	public ArrayList<Sprite> toDisplay;
	
	private Button turnLeftButton;
	private Button turnRightButton;
	
	private Display display;
	
	public Game(Grid grid, int originX, int originY){
		this.toDisplay = new ArrayList<Sprite>();
	
		initConstantDisplay();
		
		this.display = new Display(grid, originX, originY);
	}
	
	/**
	 * Initialize the constant display for a game 
	 */
	public void initConstantDisplay(){
		Sprite turnLeftSprite = new Sprite(Textures.turnLeftTexture);
		//turnLeftSprite.scale(new Vector2f((float)0.2, (float)0.2));
		turnLeftSprite.setOrigin(Vector2f.div(new Vector2f(Textures.turnLeftTexture.getSize()), 2));
		turnLeftSprite.setPosition((int)(100*Main.scaleRatio), 425);
		
		Sprite turnRightSprite = new Sprite(Textures.turnRightTexture);
		//turnLeftSprite.scale(new Vector2f((float)0.2, (float)0.2));
		turnRightSprite.setOrigin(Vector2f.div(new Vector2f(Textures.turnLeftTexture.getSize()), 2));
		turnRightSprite.setPosition((int)(960*Main.scaleRatio-(100*Main.scaleRatio)), 425);
		
		turnLeftButton = new Button(turnLeftSprite);
		turnRightButton = new Button(turnRightSprite);
		
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
		this.display.print();
	}
	
	public void printGrid(){
		this.display.printAnim();
	}
	
	public void deleteGrid(){
		this.display.removeAnim();
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
		if(event.type == Event.Type.KEY_PRESSED){
			switch(event.asKeyEvent().key){
				case UP:
					this.display.anim.moveRobot(CardinalDirection.NORTH, 0);
					Robot.wheatley.setLine(Robot.wheatley.getLine()-1);
					break;
					
				case DOWN:
					this.display.anim.moveRobot(CardinalDirection.SOUTH, 0);
					Robot.wheatley.setLine(Robot.wheatley.getLine()+1);
					break;
					
				case LEFT:
					this.display.anim.moveRobot(CardinalDirection.WEST, 0);
					Robot.wheatley.setColumn(Robot.wheatley.getColumn()-1);
					break;
					
				case RIGHT:
					
					this.display.anim.moveRobot(CardinalDirection.EAST, 0);
		       	 Robot.wheatley.setColumn(Robot.wheatley.getColumn()+1);
					break;
					
				case PAGEUP:
					
					this.display.anim.moveRobot(CardinalDirection.EAST, 2);
					break;
					
				case PAGEDOWN:
					
					this.display.anim.moveRobot(CardinalDirection.EAST, 1);
					break;
					
				case NUMPAD6:
					//this.gridDisplay.turnRobotRight();
					break;
					
				default:
					//this.gridDisplay.turnRobotLeft();
					break;
				
			}
			//anim.updateRobot(gridDisplay.getRobot());
			System.out.println("Robot --> l : "+Robot.wheatley.getLine()+", c : "+Robot.wheatley.getColumn());
		}
		if(event.type == Event.Type.MOUSE_BUTTON_PRESSED){
			MouseButtonEvent mouse = event.asMouseButtonEvent();
			if(mouse.button == Mouse.Button.LEFT){
				if(turnLeftButton.isInside(mouse.position)){
					this.display.rotate(0);
				}
				else if(turnRightButton.isInside(mouse.position)){
					this.display.rotate(1);
				}
			}
		}
	}
}
