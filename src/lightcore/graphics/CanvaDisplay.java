package lightcore.graphics;

import java.util.ArrayList;

import lightcore.LightCore;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;

public class CanvaDisplay{
	
	private int size;
	private int originX;
	private int originY;
	
	private Sprite[][] canva;
	private ClickableCell[][] canvaClick;
	
	
	/********************************************************************************************/
	/*										Constructors										*/
	/********************************************************************************************/
	
	public CanvaDisplay(int size, int originX, int originY){
		this.size = size;
		this.originX = originX;
		this.originY = originY;
		
		canva = new Sprite[size][size];
		for(int l = 0; l<size; l++)
			for(int c = 0; c<size; c++)
					canva[l][c]= null;
		
		canvaClick = new ClickableCell[size][size];
		for(int l = 0; l<size; l++)
			for(int c = 0; c<size; c++)
					canvaClick[l][c] = null;
	}
	
	
	/********************************************************************************************/
	/*										Accessors											*/
	/********************************************************************************************/
	
	/**
	 * Get the list of sprite created by all the cells in the canvas
	 * @return A list of sprites
	 */
	public ArrayList<Sprite> getCanva(){
		ArrayList<Sprite> out = new ArrayList<Sprite>();
		for(int l = 0; l<size; l++)
			for(int c = 0; c<size; c++)
				out.add(canva[l][c]);
		return out;
	}
	
	
	/********************************************************************************************/
	/*								Initialization functions									*/
	/********************************************************************************************/
	
	/**
	 * Initialize a canvas of the size defined at the instantiation
	 */
	public void initCanva(){
		for(int l = 0; l<size; l++)
			for(int c = 0; c<size; c++)
				addCel(l, c);
	}
	
	
	/********************************************************************************************/
	/*									Display construction									*/
	/********************************************************************************************/
	
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
	
	
	/********************************************************************************************/
	/*									Printing procedures										*/
	/********************************************************************************************/
	
	/**
	 * Print a canvas in the global window
	 */
	public void print(){
		for(int l = 0; l<size; l++)
			for(int c = 0; c<size; c++)
				LightCore.window.draw(canva[l][c]);
	}
	
	
	/********************************************************************************************/
	/*										Tests functions										*/
	/********************************************************************************************/
	
	/**
	 * Checks if a coordinate is inside a cell
	 * @param coord
	 * @return
	 */
	public CellPosition isInside(Vector2i coord){
		CellPosition pos = new CellPosition(0, 0, 0, false);
		for(int l = size-1; l>=0; l--)
			for(int c = size-1; c>=0; c--)
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
