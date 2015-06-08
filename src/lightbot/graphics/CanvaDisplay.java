package lightbot.graphics;

import java.util.ArrayList;

import lightbot.tests.Main;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;

public class CanvaDisplay {
	
	private Sprite[][] canva;
	private ClickableCell[][] canvaClick;
	
	private int line;
	private int column;
	
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
	
	public void addCel(int line, int column){
		Sprite toAdd = CellDisplay.createCell(line, column);
		toAdd.setPosition(originX, originY);
		
		canva[line][column] = toAdd;
		canvaClick[line][column] = new ClickableCell(toAdd, Textures.cellTexture);
	}
	
	public void initCanva(){
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				addCel(l, c);
	}
	
	public void printCanva(){
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				Main.window.draw(canva[l][c]);
	}
	
	public ArrayList<Sprite> getCanva(){
		ArrayList<Sprite> out = new ArrayList<Sprite>();
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				out.add(canva[l][c]);
		return out;
	}
	
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
