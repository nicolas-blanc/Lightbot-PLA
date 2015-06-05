package lightbot.system.world;

import lightbot.system.Colour;
import lightbot.system.Direction;

public class Grid {

	// définir la "boussole" de la grille !!!!
	// matrice de cellules

	private int size;
	private Cell[][] grid;

	public Grid(int size) {
		this.size = size;
		grid = new Cell[size][size];
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				grid[i][j] = new Cell(-1, Colour.WHITE, i, j);
			}
		}
	}
	
	public Grid(Grid gridToCopy, int size){
		this(size);
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				Cell cellToCopy = gridToCopy.getCell(i,j);
				this.setCell(i,j,cellToCopy.getHeight());
			}
		}
	}

	public Cell getCell(int x, int y) {
		return this.grid[x][y];
	}
	
	public void setCell(int x, int y, int level){
		this.grid[x][y].setHeight(level);
	}

	/**
	 * 
	 * @param currentX
	 * @param currentY
	 * @param direction
	 * @return
	 * @throws OutOfGridException
	 */
	public Cell getNextCell(int currentX, int currentY, Direction direction) throws OutOfGridException {

		switch (direction) {
		case NORTH:
			if (currentX == 0) {
				throw new OutOfGridException();
			}
			return this.grid[currentX-1][currentY];
		case SOUTH:
			if (currentX == size-1){
				throw new OutOfGridException();
			}
			return this.grid[currentX+1][currentY];
		case WEST:
			if (currentY == 0){
				throw new OutOfGridException();
			}
			return this.grid[currentX][currentY-1];
		case EAST:
			System.out.println(currentY);
			System.out.println(size);
			if (currentY == size-1){
				throw new OutOfGridException();
			}else{
				return this.grid[currentX][currentY+1];
			}
		default:
			throw new OutOfGridException();
		}
	}
	
	public void printGrid(){
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[i].length; j++){
				System.out.print(grid[i][j].getHeight() + "\t");
			}
			System.out.println();
		}
	}
	
	public int getSize(){
		return this.size;
	}
	
	/* TODO !
	 * levelToZero -> met à zéro les hauteurs restantes à -1
	 */
	
	
}
