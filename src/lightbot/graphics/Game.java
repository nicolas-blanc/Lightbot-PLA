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

public class Game implements Display{
	
	private Grid grid;
	
	private int line;
	private int column;
	
	private int originX;
	private int originY;
	
	private GridDisplay gridDisplay;
	
	public ArrayList<Sprite> toDisplay;
	
	private Animation anim;
	
	private Button turnLeftButton;
	private Button turnRightButton;
	
	public Game(int[][] mat, int originX, int originY){
		this.toDisplay = new ArrayList<Sprite>();
		
		this.line = mat.length;
		this.column = mat[0].length;
		this.originX = originX;
		this.originY = originY;
		
		this.gridDisplay = new GridDisplay(this.line, this.column, this.originX, this.originY);
		gridDisplay.initGridFromMatrix(mat);
	}
	
	public Game(Grid grid, int originX, int originY){
		this.toDisplay = new ArrayList<Sprite>();
		
		this.grid = grid;
		this.line = grid.getSize();
		this.column = grid.getSize();
		this.originX = originX;
		this.originY = originY;
		
		this.gridDisplay = new GridDisplay(this.grid, this.originX, this.originY);
		this.gridDisplay.initGrid();
		this.gridDisplay.initRobot();
		
		initConstantDisplay();
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
		gridDisplay.printCubeList();		
	}
	
	public void printGrid(){
		anim = new Animation(this.gridDisplay.getGridSprites(), this.gridDisplay.getRobot());
		Sprite[][][] grid = this.gridDisplay.getGridSprites();
		for(int l = 0; l < grid.length; l++)
			for(int c = 0; c < grid[0].length; c++)
				for(int level = 0; level < 50; level++)
					if(l == Robot.wheatley.getPositionX() && c == Robot.wheatley.getPositionY() && level == GridDisplay.levelMax[l][c]){
						anim.addRemoveCube(l, c, level, true, true);
						anim.displayRobot(l, c, level, true);
					}
					else{
						if(grid[l][c][level] != null)
							anim.addRemoveCube(l, c, level, true, true);
					}
	}
	
	public void deleteGrid(){
		Sprite[][][] grid = this.gridDisplay.getGridSprites();
		for(int l = grid.length-1; l >= 0; l--)
			for(int c = grid[0].length-1; c >= 0; c--)
				for(int level = 49; level >= 0; level--)
					if(grid[l][c][level] != null)
						anim.addRemoveCube(l, c, level, false, true);
	}
	
	/**
	 * Get the grid from the game
	 */
	public GridDisplay getGrid(){
		return this.gridDisplay;
	}
	
	public CellPosition isInside(Vector2i coord){
		return new CellPosition(0, 0, 0, false);
	}

	public void eventManager(Event event) {
		if(event.type == Event.Type.KEY_PRESSED){
			switch(event.asKeyEvent().key){
				case UP:
					anim.moveRobot(CardinalDirection.NORTH, 0);
					Robot.wheatley.setPositionX(Robot.wheatley.getPositionX()-1);
					break;
					
				case DOWN:
					anim.moveRobot(CardinalDirection.SOUTH, 0);
					Robot.wheatley.setPositionX(Robot.wheatley.getPositionX()+1);
					break;
					
				case LEFT:
					anim.moveRobot(CardinalDirection.WEST, 0);
					Robot.wheatley.setPositionY(Robot.wheatley.getPositionY()-1);
					break;
					
				case RIGHT:
					
		       	 	anim.moveRobot(CardinalDirection.EAST, 0);
		       	 Robot.wheatley.setPositionY(Robot.wheatley.getPositionY()+1);
					break;
					
				case PAGEUP:
					
					anim.moveRobot(CardinalDirection.EAST, 2);
					break;
					
				case PAGEDOWN:
					
					anim.moveRobot(CardinalDirection.EAST, 1);
					break;
					
				case NUMPAD6:
					this.gridDisplay.turnRobotRight();
					break;
					
				default:
					this.gridDisplay.turnRobotLeft();
					break;
				
			}
			anim.updateRobot(gridDisplay.getRobot());
			System.out.println("Robot --> l : "+Robot.wheatley.getPositionX()+", c : "+Robot.wheatley.getPositionY());
		}
		if(event.type == Event.Type.MOUSE_BUTTON_PRESSED){
			MouseButtonEvent mouse = event.asMouseButtonEvent();
			if(mouse.button == Mouse.Button.LEFT){
				if(turnLeftButton.isInside(mouse.position)){
					gridDisplay.rotateLeft();
					anim.updateSprite(gridDisplay.getGridSprites(), gridDisplay.getRobot());
				}
				else if(turnRightButton.isInside(mouse.position)){
					gridDisplay.rotateRight();
					anim.updateSprite(gridDisplay.getGridSprites(), gridDisplay.getRobot());
				}
			}
		}
	}
}
