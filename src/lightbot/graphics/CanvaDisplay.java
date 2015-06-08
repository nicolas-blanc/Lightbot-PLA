package lightbot.graphics;

import java.util.ArrayList;

import lightbot.tests.Main;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;

public class CanvaDisplay {
	
	private Sprite[][] canva;
	private ClickableCell[][] canvaClick;
	
	
	/**
	 * Number of lines and columns of the canvas
	 */
	private int line;
	private int column;
	
	/**
	 * The position of the first cell to be placed
	 */
	private int originX;
	private int originY;
	
	public CanvaDisplay(int line, int column, int originX, int originY){
		this.line = line;
		this.column = column;
		this.originX = originX;
		this.originY = originY;
		
		canva = new Sprite[this.line][this.column];
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
					canva[l][c]= null;
		
		canvaClick = new ClickableCell[this.line][this.column];
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
					canvaClick[l][c] = null;
	}
	
	/**
	 * Add a cell to the canvas
	 * @param line The cell's line
	 * @param column The cell's column
	 */
	public void addCel(int line, int column){
		Sprite toAdd = CellDisplay.createCell(line, column);
		toAdd.setPosition(originX, originY);
		
		canva[line][column] = toAdd;
		canvaClick[line][column] = new ClickableCell(toAdd, Textures.cellTexture);
	}
	
	/**
	 * Initialize a canvas of the size defined at the instantiation
	 */
	public void initCanva(){
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				addCel(l, c);
	}
	
	/**
	 * Print a canvas in the global window
	 */
	public void printCanva(){
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				Main.window.draw(canva[l][c]);
	}
	
	/**
	 * Get the list of sprite created by all the cells in the canvas
	 * @return A list of sprites
	 */
	public ArrayList<Sprite> getCanva(){
		ArrayList<Sprite> out = new ArrayList<Sprite>();
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				out.add(canva[l][c]);
		return out;
	}
	
	/**
	 * Checks if a coordinate is inside a cell
	 * @param coord
	 * @return
	 */
	public CellPosition isInside(Vector2i coord){
		CellPosition pos = new CellPosition(0, 0, 0, false);
		for(int l = this.line-1; l>=0; l--)
			for(int c = this.column-1; c>=0; c--)
				if(canvaClick[l][c].isInside(coord) && GridDisplay.levelMax[l][c] == -1){
					pos.line = l;
					pos.column = c;
					pos.level = -1;
					pos.isFound = true;
					pos.isMaxCell = true;
					return pos;
				}
		return pos;
	}
}
