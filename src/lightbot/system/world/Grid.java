package lightbot.system.world;

import lightbot.system.Colour;
import lightbot.system.CardinalDirection;

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
				this.setCell(i,j,cellToCopy.getHeight(), cellToCopy.getColour());
			}
		}
	}
	
	/**
	 * Constructs a grid from a matrix
	 * @param m : matrix
	 */
	public Grid(Cell[][] m){
		this(m.length);
		for(int i=0; i<m.length; i++){
			for(int j=0; j<m.length; j++){
				Cell cell = new Cell(m[i][j].getHeight(), m[i][j].getColour(), i, j, m[i][j].getLightable());
				this.setCell(i,j,cell.getHeight(), Colour.WHITE);
			}
		}
	}
	
	public void rotateRight(){
		Cell[][] tmp = new Cell[grid.length][grid.length];
		for(int l=0; l<grid.length; l++)
			for(int c=0; c<grid.length; c++)
				tmp[l][c] = grid[l][c];
		
		for(int l=0; l<grid.length; l++)
			for(int c=0; c<grid.length; c++)
				grid[l][c] = tmp[grid.length-c-1][l];
	}
	
	public void rotateLeft(){
		Cell[][] tmp = new Cell[grid.length][grid.length];
		for(int l=0; l<grid.length; l++)
			for(int c=0; c<grid.length; c++)
				tmp[l][c] = grid[l][c];
		
		for(int l=0; l<grid.length; l++)
			for(int c=0; c<grid.length; c++)
				grid[l][c] = tmp[c][grid.length-l-1];
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
	public void setCell(int l, int c, int level, Colour colour){
		this.grid[l][c].setHeight(level);
		this.grid[l][c].setColour(colour);
	}

	/**
	 * getNextCell
	 * @param currentL : current l position (line) of the robot
	 * @param currentC : current c position (column) of the robot
	 * @param direction : the current direction of the robot
	 * @return the next cell, according to the current position and the direction of the robot
	 * @throws OutOfGridException
	 */
	public Cell getNextCell(int currentL, int currentC, CardinalDirection direction) throws OutOfGridException {

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
				System.out.print(grid[i][j].getHeight() + " / ");
				if (grid[i][j].getLightable()) {
					System.out.print("L \t");
				} else {
				System.out.print("  \t");
				}
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
