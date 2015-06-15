package lightbot.graphics;

import lightbot.system.world.Grid;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.cell.EmptyCell;
import lightbot.system.world.cell.FullCell;
import lightbot.system.world.cell.NormalCell;
import lightbot.tests.Main;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;

public class GridDisplay {
	
	public static final int maxHeight = 50; 
	
	public Grid grid = null;
	private int size;
	private int originX;
	private int originY;
	
	boolean isGame;
	
	private Sprite[][][] cubes;
	private ClickableCell[][][] cellClick;
	
	public static int [][] levelMax;
	
	
	/********************************************************************************************/
	/*										Constructors										*/
	/********************************************************************************************/
	
	/**
	 * Create a GridDisplay object in order to use the Editor
	 * @param line The line number of the editor
	 * @param column The column number of the editor
	 * @param originX
	 * @param originY
	 */
	public GridDisplay(int size, int originX, int originY){
		this.size = size;
		this.originX = originX;
		this.originY = originY;
		isGame = false;
		
		grid = new Grid(this.size);
		initArray();
	}

	/**
	 * Create a Display object in order to play on a grid 
	 * @param grid The grid to display
	 * @param originX
	 * @param originY
	 */
	public GridDisplay(Grid grid, int originX, int originY){
		this.grid = grid;
		isGame = true;
		
		size = grid.getSize();
		this.originX = originX;
		this.originY = originY;
		
		initArray();
	}
	
	
	/********************************************************************************************/
	/*										Accessors											*/
	/********************************************************************************************/
	
	/**
	 * Get the array of sprites corresponding to the displayed grid
	 * @return A three-dimensional array of sprites
	 */
	public Sprite[][][] getGridSprites(){return cubes;}
	
	/**
	 * Get the Grid used in the GridDisplay object
	 * @return
	 */
	public Grid getGrid(){return this.grid;}
	
	public void reinit(Grid grid, int originX, int originY){
		this.grid = grid;
		this.size = grid.getSize();
		
		this.originX = originX;
		this.originY = originY;
		
		initArray();
		
		this.isGame = true;
		initGrid();
		this.isGame = false;
	}
	
	
	/********************************************************************************************/
	/*								Initialization functions									*/
	/********************************************************************************************/
	
	/**
	 * Initialize all the array of the GridDisplay
	 */
	private void initArray(){
		cubes = new Sprite[size][size][maxHeight];
		for(int l = 0; l < cubes.length; l++)
			for(int c = 0; c < cubes[0].length; c++)
				for(int level = 0; level < cubes[0][0].length; level++)
					cubes[l][c][level] = null;
		
		cellClick = new ClickableCell[size][size][maxHeight];
		for(int l = 0; l < cellClick.length; l++)
			for(int c = 0; c < cellClick[0].length; c++)
				for(int level = 0; level < cellClick[0][0].length; level++)
					cellClick[l][c][level] = null;
		
		levelMax = new int[size][size];
		for(int l = 0; l < levelMax.length; l++)
			for(int c = 0; c < levelMax[0].length; c++)
				levelMax[l][c] = -1;
	}
	
	/**
	 * Initialize the displayed grid from a grid
	 */
	public void initGrid(){
		for(int l = 0; l<grid.getSize(); l++)
			for(int c = 0; c<grid.getSize(); c++)
				if(!(grid.getCell(l, c) instanceof EmptyCell)){
					int cubeHeight = grid.getCell(l, c).getHeight();
					addLevel(l, c, cubeHeight-1);
					addCube(grid.getCell(l, c));
				}
	}
	
	
	/********************************************************************************************/
	/*									Display construction									*/
	/********************************************************************************************/
	
	/**
	 * Add a cube to the displayed grid
	 * @param cell
	 */
	public void addCube(Cell cell){
		CubeDisplay cube = new CubeDisplay(cell);
		Sprite toAdd = cube.create() ;
		toAdd.setPosition(originX, originY);
		
		if(!isGame)
			grid.setCell(cell);
		
		cubes[cell.getX()][cell.getY()][cell.getHeight()] = toAdd;
		levelMax[cell.getX()][cell.getY()] = cell.getHeight();
		cellClick[cell.getX()][cell.getY()][cell.getHeight()] = new ClickableCell(toAdd, Textures.cellTexture);
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
		
		if(!isGame){
			Cell cell = grid.getCell(line, column);
			if(cell instanceof FullCell){
				if(((FullCell)cell).getHeight() == 0)
					cell = new EmptyCell(cell.getX(), cell.getY());
				else
					((FullCell)cell).setHeight(level-2);
				grid.setCell(cell);
			}
		}
	}
	
	/**
	 * Add a level of cubes
	 * @param line The cube's line
	 * @param column The cube's column
	 * @param level The max level
	 */
	public void addLevel(int line, int column, int level){
		for(int i = 0; i<=level; i++)
			addCube(new NormalCell(line, column, i));
	}
	
	
	/********************************************************************************************/
	/*									Printing procedures										*/
	/********************************************************************************************/	
	
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
	
	
	/********************************************************************************************/
	/*									Actions on the grid										*/
	/********************************************************************************************/
	
	/**
	 * Rotate the grid to the left
	 */
	public void rotateLeft(){
		grid.rotateLeft();
		initArray();
		initGrid();
	}
	
	/**
	 * Rotate the grid to the right
	 */
	public void rotateRight(){		
		grid.rotateRight();
		initArray();
		initGrid();
	}
	
	
	/********************************************************************************************/
	/*										Tests functions										*/
	/********************************************************************************************/
	
	/**
	 * Test if a coordinate is inside a cube's cell or not
	 * @param coord The coordinate
	 * @return A boolean
	 */
	public CellPosition isInside(Vector2i coord){
		CellPosition pos = new CellPosition(0, 0, 0, false);
		
		for(int l = size-1; l>=0; l--)
			for(int c = size-1; c>=0; c--)
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
		for(int l = size-1; l>=lineMin; l--)
			for(int c = size-1; c>=columnMin; c--)
				for(int level = levelMax[l][c]; level>=levelMin; level--)
					if(cellClick[l][c][level].isInsideDeadZone(coord)){
						//System.out.println("Is inside dead zone --> l : " + l + ", c : " + c + ", level : " + level);
						return true;
					}
		return false;
	}	
}
