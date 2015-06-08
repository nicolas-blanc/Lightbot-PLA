package lightbot.graphics;

import java.util.ArrayList;

import lightbot.system.CardinalDirection;
import lightbot.system.Robot;
import lightbot.system.world.Grid;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import org.jsfml.window.event.MouseButtonEvent;

public class Game implements Display{
	
	private Grid grid;
	private Robot robot;
	
	private int line;
	private int column;
	
	private int originX;
	private int originY;
	
	private GridDisplay gridDisplay;
	
	public ArrayList<Sprite> toDisplay;
	
	private Animation anim;
	
	public Game(int[][] mat, int originX, int originY){
		this.toDisplay = new ArrayList<Sprite>();
		
		this.line = mat.length;
		this.column = mat[0].length;
		this.originX = originX;
		this.originY = originY;
		
		this.gridDisplay = new GridDisplay(this.line, this.column, this.originX, this.originY);
		gridDisplay.initGridFromMatrix(mat);
	}
	
	public Game(Grid grid, Robot robot, int originX, int originY){
		this.toDisplay = new ArrayList<Sprite>();
		this.robot = robot;
		
		this.grid = grid;
		this.line = grid.getSize();
		this.column = grid.getSize();
		this.originX = originX;
		this.originY = originY;
		
		//this.gridDisplay = new GridDisplay(this.line, this.column, this.originX, this.originY);
		//grid.initGridFromMatrix(mat);
		this.gridDisplay = new GridDisplay(this.grid, this.robot, this.originX, this.originY);
		this.gridDisplay.initGrid();
		this.gridDisplay.initRobot();
	}
	
	/**
	 * Initialize the constant display for a game 
	 */
	public void initConstantDisplay(){
		// TODO
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
		gridDisplay.printCubeList();		
	}
	
	public void printGrid(){
		anim = new Animation(this.gridDisplay.getGrid(), this.gridDisplay.getRobot(), this.robot);
		Sprite[][][] grid = this.gridDisplay.getGrid();
		for(int l = 0; l < grid.length; l++)
			for(int c = 0; c < grid[0].length; c++)
				for(int level = 0; level < 50; level++)
					if(l == robot.getPositionX() && c == robot.getPositionY() && level == GridDisplay.levelMax[l][c]){
						anim.addRemoveCube(l, c, level, true, true);
						anim.displayRobot(l, c, level, true);
					}
					else{
						if(grid[l][c][level] != null)
							anim.addRemoveCube(l, c, level, true, true);
					}
	}
	
	public void deleteGrid(){
		Sprite[][][] grid = this.gridDisplay.getGrid();
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

	@Override
	public void eventManager(Event event) {
		// TODO Auto-generated method stub
		if(event.type == Event.Type.KEY_PRESSED){
			switch(event.asKeyEvent().key){
				case UP:
					anim.moveRobot(CardinalDirection.NORTH, 0);
	    	 		this.robot.setPositionX(this.robot.getPositionX()-1);
					break;
					
				case DOWN:
					anim.moveRobot(CardinalDirection.SOUTH, 0);
	    	 		this.robot.setPositionX(this.robot.getPositionX()+1);
					break;
					
				case LEFT:
					anim.moveRobot(CardinalDirection.WEST, 0);
	       	 		this.robot.setPositionY(this.robot.getPositionY()-1);
					break;
					
				case RIGHT:
					
		       	 	anim.moveRobot(CardinalDirection.EAST, 0);
	       	 		this.robot.setPositionY(this.robot.getPositionY()+1);
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
			
			System.out.println("Robot --> l : "+robot.getPositionX()+", c : "+robot.getPositionY());
		}
		if(event.type == Event.Type.MOUSE_BUTTON_PRESSED){
			MouseButtonEvent mouse = event.asMouseButtonEvent();
       	 	/*if(mouse.button == Mouse.Button.LEFT){
       	 		anim.moveRobot(Direction.EAST);
       	 		this.robot.setPositionY(this.robot.getPositionY()+1);
       	 	}
       	 	else if(mouse.button == Mouse.Button.RIGHT){
       	 		anim.moveRobot(Direction.WEST);
       	 		this.robot.setPositionY(this.robot.getPositionY()-1);
       	 		//deleteGrid();
       	 	}*/
       	 	/*if(mouse.button == Mouse.Button.LEFT){
    	 		anim.moveRobot(Direction.NORTH);
    	 		this.robot.setPositionX(this.robot.getPositionX()-1);
    	 	}
       	 	else if(mouse.button == Mouse.Button.RIGHT){
    	 		anim.moveRobot(Direction.SOUTH);
    	 		this.robot.setPositionX(this.robot.getPositionX()+1);
    	 		//deleteGrid();
    	 	}*/
		}
	}
}
