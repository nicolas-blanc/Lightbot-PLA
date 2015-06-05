package lightbot.graphics;

import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

public class Game implements Display{
	private int line;
	private int column;
	
	private int originX;
	private int originY;
	
	private GridDisplay grid;
	
	public Game(int[][] mat, int originX, int originY){
		this.line = mat.length;
		this.column = mat[0].length;
		this.originX = originX;
		this.originY = originY;
		
		this.grid = new GridDisplay(this.line, this.column, this.originX, this.originY);
		grid.initGridFromMatrix(mat);
	}
	
	public void display(){
		grid.printCubeList();		
	}
	
	public GridDisplay getGrid(){
		return this.grid;
	}
	
	public CellPosition isInside(Vector2i coord){
		return new CellPosition(0, 0, 0, false);
	}

	@Override
	public void eventManager(Event event) {
		// TODO Auto-generated method stub
		
	}
}
