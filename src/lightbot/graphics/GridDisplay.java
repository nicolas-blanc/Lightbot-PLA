package lightbot.graphics;

import lightbot.system.Colour;
import lightbot.tests.Main;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;

public class GridDisplay {
	
	private Sprite[][][] cubes;
	private ClickableCell[][][] cellClick;
	
	public static int [][] levelMax;
	
	private int line;
	private int column;
	
	private int originX;
	private int originY;
	
	public GridDisplay(int line, int column, int originX, int originY){
		this.line = line;
		this.column = column;
		this.originX = originX;
		this.originY = originY;
		
		cubes = new Sprite[this.line][this.column][50];
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				for(int level = 0; level < 50; level++)
					cubes[l][c][level] = null;
		
		cellClick = new ClickableCell[this.line][this.column][50];
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				for(int level = 0; level < 50; level++)
					cellClick[l][c][level] = null;
		
		levelMax = new int[this.line][this.column];
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				levelMax[l][c] = -1;
	}
	
	public void initGridFromMatrix(int[][] mat){
		for(int l = 0; l<mat.length-1; l++)
			for(int c = 0; c<mat[c].length-1; c++)
				addLevel(l, c, mat[l][c]);
	}
	
	public void addCube(int line, int column, int level, Colour colour){
		CubeDisplay cube = new CubeDisplay(line, column, level, colour);
		Sprite toAdd = cube.createCube() ;
		toAdd.setPosition(originX, originY);
		
		cubes[line][column][level] = toAdd;
		levelMax[line][column] = level;
		cellClick[line][column][level] = new ClickableCell(toAdd, Textures.cellTexture);
	}
	
	public void removeCube(int line, int column, int level){
		cubes[line][column][level-1] = null;
		levelMax[line][column] = level-2;
		cellClick[line][column][level-1] = null;
	}
	
	public void addLevel(int line, int column, int level){
		for(int i = 1; i<=level; i++)
			addCube(line, column, i-1, Colour.WHITE);
	}
	
	public void printCubeList(){
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				for(int level = 0; level < 50; level++)
					if(cubes[l][c][level] != null)
						Main.window.draw(cubes[l][c][level]);
	}
	
	public CellPosition isInside(Vector2i coord){
		CellPosition pos = new CellPosition(0, 0, 0, false);
		
		for(int l = this.line-1; l>=0; l--)
			for(int c = this.column-1; c>=0; c--)
				for(int level = levelMax[l][c]; level>=0; level--){
					if(cellClick[l][c][level].isInside(coord) && !isInsideDeadZone(coord, l, c, level)){
						pos.line = l;
						pos.column = c;
						pos.level = level;
						pos.isFound = true;
						pos.isMaxCell = (levelMax[l][c] == level);
						return pos;
					}
				}
		return pos;
	}
	
	public boolean isInsideDeadZone(Vector2i coord, int lineMin, int columnMin, int levelMin){
		for(int l = this.line-1; l>=lineMin; l--)
			for(int c = this.column-1; c>=columnMin; c--)
				for(int level = levelMax[l][c]; level>=levelMin; level--)
					if(cellClick[l][c][level].isInsideDeadZone(coord)){
						//System.out.println("Is inside dead zone --> l : " + l + ", c : " + c + ", level : " + level);
						return true;
					}
		return false;
	}
}
