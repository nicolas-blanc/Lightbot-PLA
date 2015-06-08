package lightbot.graphics;

import java.util.ArrayList;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;

public class Game implements Display{
	private int line;
	private int column;
	
	private int originX;
	private int originY;
	
	private GridDisplay grid;
	
	public ArrayList<Sprite> toDisplay;
	
	private Animation anim;
	
	public Game(int[][] mat, int originX, int originY){
		this.toDisplay = new ArrayList<Sprite>();
		
		this.line = mat.length;
		this.column = mat[0].length;
		this.originX = originX;
		this.originY = originY;
		
		this.grid = new GridDisplay(this.line, this.column, this.originX, this.originY);
		grid.initGridFromMatrix(mat);
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
		grid.printCubeList();		
	}
	
	public void printGrid(){
		anim = new Animation(this.grid.getGrid());
		Sprite[][][] grid = this.grid.getGrid();
		for(int l = 0; l < grid.length; l++)
			for(int c = 0; c < grid[0].length; c++)
				for(int level = 0; level < 50; level++)
					if(grid[l][c][level] != null)
						anim.addRemoveCube(l, c, level, true, true);
	}
	
	public void deleteGrid(){
		Sprite[][][] grid = this.grid.getGrid();
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
		return this.grid;
	}
	
	public CellPosition isInside(Vector2i coord){
		return new CellPosition(0, 0, 0, false);
	}

	@Override
	public void eventManager(Event event) {
		// TODO Auto-generated method stub
		if(event.type == Event.Type.MOUSE_BUTTON_PRESSED){
			MouseButtonEvent mouse = event.asMouseButtonEvent();
       	 	if(mouse.button == Mouse.Button.LEFT){
       	 		deleteGrid();
       	 	}
		}
	}
}
