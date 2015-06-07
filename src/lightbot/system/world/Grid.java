package lightbot.system.world;

import lightbot.system.Colour;
import lightbot.system.Direction;

public class Grid {

	// définir la "boussole" de la grille !!!!
	// matrice de cellules

	private int size;
	private Cell[][] grid;

	/**
	 * Constructor with the size
	 * @param size
	 */
	public Grid(int size) {
		this.size = size;
		grid = new Cell[size][size];
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				grid[i][j] = new Cell(-1, Colour.WHITE, i, j, false);
			}
		}
	}
	
	/**
	 * Constructs a grid from another existing grid
	 * @param gridToCopy
	 * @param size
	 */
	public Grid(Grid gridToCopy, int size){
		this(size);
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				Cell cellToCopy = gridToCopy.getCell(i,j);
				this.setCell(i,j,cellToCopy.getHeight());
			}
		}
	}

	/**
	 * getCell
	 * @param l : line
	 * @param c : column
	 * @return the (l,c) cell of the grid
	 */
	public Cell getCell(int l, int c) {
		return this.grid[l][c];
	}
	
	/**
	 * setCell
	 * sets the level of the (l,c) cell
	 * @param l : line
	 * @param c : column
	 * @param level
	 */
	public void setCell(int l, int c, int level){
		this.grid[l][c].setHeight(level);
	}

	/**
	 * getNextCell
	 * @param currentL : current l position (line) of the robot
	 * @param currentC : current c position (column) of the robot
	 * @param direction : the current direction of the robot
	 * @return the next cell, according to the current position and the direction of the robot
	 * @throws OutOfGridException
	 */
	public Cell getNextCell(int currentL, int currentC, Direction direction) throws OutOfGridException {

		switch (direction) {
		case NORTH:
			if (currentL == 0) {
				throw new OutOfGridException();
			}
			return this.grid[currentL-1][currentC];
		case SOUTH:
			if (currentL == size-1){
				throw new OutOfGridException();
			}
			return this.grid[currentL+1][currentC];
		case WEST:
			if (currentC == 0){
				throw new OutOfGridException();
			}
			return this.grid[currentL][currentC-1];
		case EAST:
			if (currentC == size-1){
				throw new OutOfGridException();
			}else{
				return this.grid[currentL][currentC+1];
			}
		default:
			throw new OutOfGridException();
		}
	}
	
	/**
	 * printGrid
	 * Prints a grid
	 */
	public void printGrid(){
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[i].length; j++){
				System.out.print(grid[i][j].getHeight() + "\t");
			}
			System.out.println();
		}
	}
	
	/**
	 * getSize
	 * @return the size of a size*size grid
	 */
	public int getSize(){
		return this.size;
	}

	/**
	 * levelToZero : replaces every '-1' by '0' in the grid
	 */
	public void levelToZero(){
		size = getSize();
		
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				if(this.getCell(i,j).getHeight() == -1){
					this.getCell(i,j).setHeight(0);
				}
			}
		}
	}
	
	
}
