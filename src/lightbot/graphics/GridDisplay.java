package lightbot.graphics;

import lightbot.system.Colour;
import lightbot.system.world.Grid;
import lightbot.tests.Main;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;

public class GridDisplay {
	
	private Sprite[][][] cubes;
	
	private Grid grid = null;
	boolean isGame;
	
	private ClickableCell[][][] cellClick;
	
	// Keep the maximum level of cubes
	public static int [][] levelMax;
	
	private int line;
	private int column;
	
	private int originX;
	private int originY;
	
	/**
	 * 
	 * @param line
	 * @param column
	 * @param originX
	 * @param originY
	 */
	public GridDisplay(int line, int column, int originX, int originY){
		this.line = line;
		this.column = column;
		this.originX = originX;
		this.originY = originY;
		this.isGame = false;
		
		grid = new Grid(this.line);
		grid.levelToZero();
		initArray();
	}
	
	/**
	 * Create a display from a grid
	 * @param grid
	 */
	public GridDisplay(Grid grid, int originX, int originY){
		this.grid = grid;
		this.isGame = true;
		
		this.line = grid.getSize();
		this.column = grid.getSize();
		this.originX = originX;
		this.originY = originY;
		
		initArray();
	}
	
	/********************************************************************************************/
	/*								Initialization functions								*/
	/********************************************************************************************/
	
	/**
	 * Initialize all the array of the GridDisplay
	 */
	private void initArray(){
		cubes = new Sprite[this.line][this.column][50];
		for(int l = 0; l < cubes.length; l++)
			for(int c = 0; c < cubes[0].length; c++)
				for(int level = 0; level < cubes[0][0].length; level++)
					cubes[l][c][level] = null;
		
		cellClick = new ClickableCell[this.line][this.column][50];
		for(int l = 0; l < cellClick.length; l++)
			for(int c = 0; c < cellClick[0].length; c++)
				for(int level = 0; level < cellClick[0][0].length; level++)
					cellClick[l][c][level] = null;
		
		levelMax = new int[this.line][this.column];
		for(int l = 0; l < levelMax.length; l++)
			for(int c = 0; c < levelMax[0].length; c++)
				levelMax[l][c] = -1;
	}
	
	/**
	 * Initialize the displayed grid from a grid
	 */
	public void initGrid(){
		for(int l = 0; l<this.grid.getSize(); l++){
			for(int c = 0; c<this.grid.getSize(); c++){
				int cubeHeight = this.grid.getCell(l, c).getHeight();
				if(cubeHeight > 0){
					addLevel(l, c, cubeHeight-1);
					addCube(l, c, cubeHeight-1, this.grid.getCell(l, c).getColour());
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Add a cube to the displayed grid
	 * @param line The cube's line
	 * @param column The cube's column
	 * @param level The cube's level
	 * @param colour The cube's colour
	 */
	public void addCube(int line, int column, int level, Colour colour){
		CubeDisplay cube = new CubeDisplay(line, column, level, colour);
		Sprite toAdd = cube.create() ;
		toAdd.setPosition(originX, originY);
		
		if(!this.isGame)
			grid.setCell(line, column, level+1, colour);
		
		cubes[line][column][level] = toAdd;
		levelMax[line][column] = level;
		cellClick[line][column][level] = new ClickableCell(toAdd, Textures.cellTexture);
	}
	
	/**
	 * Remove a cube to the displayed grid
	 * @param line The cube's line
	 * @param column The cube's column
	 * @param level The cube's level
	 */
	public void removeCube(int line, int column, int level){
		cubes[line][column][level-1] = null;
		levelMax[line][column] = level-2;
		cellClick[line][column][level-1] = null;
		
		if(!this.isGame)
			grid.setCell(line, column, level-1, Colour.WHITE);
	}
	
	/**
	 * Add a level of cubes
	 * @param line The cube's line
	 * @param column The cube's column
	 * @param level The max level
	 */
	public void addLevel(int line, int column, int level){
		for(int i = 1; i<=level; i++)
			addCube(line, column, i-1, Colour.WHITE);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Print a pillar
	 * @param line The line of the pillar
	 * @param column The column of the pillar
	 */
	public void printPillar(int line, int column){
		for(int level=0; level<=levelMax[line][column]; level++)
			if(cubes[line][column][level] != null)
				Main.window.draw(cubes[line][column][level]);
	}	
	
	public void rotateLeft(){
		grid.rotateLeft();
		initArray();
		initGrid();
	}
	
	public void rotateRight(){
		grid.rotateRight();
		initArray();
		initGrid();
	}
	
	/****** test functions *****/
	
	/**
	 * Test if a coordinate is inside a cube's cell or not
	 * @param coord The coordinate
	 * @return A boolean
	 */
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
	
	
	/**
	 * Test if a coordinate is inside a cube's cell dead zone or not
	 * @param coord The coordinate
	 * @param lineMin The first line search has to be performed
	 * @param columnMin The first column search has to be performed
	 * @param levelMin The first level search has to be performed
	 * @return A boolean
	 */
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
	
	/**
	 * Get the array of sprites corresponding to the displayed grid
	 * @return
	 */
	public Sprite[][][] getGridSprites(){return cubes;}
	
	public Grid getGrid(){return this.grid;}
	
}
