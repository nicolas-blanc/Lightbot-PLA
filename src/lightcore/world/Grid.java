package lightcore.world;

import lightcore.world.cell.Cell;
import lightcore.world.cell.ColoredCell;
import lightcore.world.cell.EmptyCell;
import lightcore.world.cell.LightableCell;
import lightcore.world.cell.NormalCell;
import lightcore.world.cell.ObstacleCell;
import lightcore.world.cell.TeleportCell;

public class Grid {

	// définir la "boussole" de la grille !!!!
	// matrice de cellules

	private int size;
	private Cell[][] grid;

	/**
	 * Constructor with the size
	 * 
	 * @param size
	 */
	public Grid(int size) {
		this.size = size;
		grid = new Cell[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				grid[i][j] = new EmptyCell(i, j);
			}
		}
	}

	/**
	 * Constructs a grid from another existing grid
	 * 
	 * @param gridToCopy
	 * @param size
	 */
	public Grid(Grid gridToCopy) {
		this.size = gridToCopy.getSize();
		this.grid = new Cell[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				Cell c = gridToCopy.getCell(i, j);
				if (c == null) {
					this.grid[i][j] = new EmptyCell(i, j);
				} else {
					if (c instanceof EmptyCell) {
						System.out.println(c == null);
						this.grid[i][j] = new EmptyCell(i, j);
					}
					if (c instanceof NormalCell) {
						this.grid[i][j] = new NormalCell(i, j, ((NormalCell) c).getHeight());
					}
					if (c instanceof ColoredCell) {
						this.grid[i][j] = new ColoredCell(i, j, ((ColoredCell) c).getHeight(),
								((ColoredCell) c).getColour());
					}
					if (c instanceof LightableCell) {
						this.grid[i][j] = new LightableCell(i, j, ((LightableCell) c).getHeight());
					}
					if (c instanceof ObstacleCell) {
						this.grid[i][j] = new ObstacleCell(i, j, ((ObstacleCell) c).getHeight());
					}
					if (c instanceof TeleportCell) {
						this.grid[i][j] = new TeleportCell(i, j, ((TeleportCell) c).getHeight(),
								((TeleportCell) c).getDestX(), ((TeleportCell) c).getDestY(),
								((TeleportCell) c).getColour());

					}
				}

			}
		}
	}

	/**
	 * Constructs a grid from a matrix
	 * 
	 * @param m
	 *            : matrix
	 */
	public Grid(Cell[][] m) {
		this(m.length);
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				this.grid[i][j] = m[i][j];
			}
		}
	}

	public void rotateRight() {
		Cell[][] tmp = new Cell[grid.length][grid.length];
		for (int l = 0; l < grid.length; l++)
			for (int c = 0; c < grid.length; c++)
				tmp[l][c] = grid[l][c];

		for (int l = 0; l < grid.length; l++)
			for (int c = 0; c < grid.length; c++) {
				grid[l][c] = tmp[grid.length - c - 1][l];
				grid[l][c].setX(l);
				grid[l][c].setY(c);
				if (tmp[grid.length - c - 1][l] instanceof TeleportCell) {
					Position teleport = ((TeleportCell) grid[l][c]).getDestinationPosition();
					((TeleportCell) grid[l][c]).setDestXY(teleport.getY(), grid.length - teleport.getX() - 1);
				}
			}
	}

	public void rotateLeft() {
		Cell[][] tmp = new Cell[grid.length][grid.length];
		for (int l = 0; l < grid.length; l++)
			for (int c = 0; c < grid.length; c++)
				tmp[l][c] = grid[l][c];

		for (int l = 0; l < grid.length; l++)
			for (int c = 0; c < grid.length; c++) {
				grid[l][c] = tmp[c][grid.length - l - 1];
				grid[l][c].setX(l);
				grid[l][c].setY(c);
				if (tmp[c][grid.length - l - 1] instanceof TeleportCell) {
					Position teleport = ((TeleportCell) grid[l][c]).getDestinationPosition();
					((TeleportCell) grid[l][c]).setDestXY(grid.length - teleport.getY() - 1, teleport.getX());
				}
			}
	}

	/**
	 * changeToLightable Changes any cell to a LightableCell
	 * 
	 * @param l
	 * @param c
	 */
	public void changeToLightable(int l, int c) {
		if (grid[l][c] instanceof EmptyCell)
			this.grid[l][c] = new LightableCell(l, c, 1);
		else
			this.grid[l][c] = new LightableCell(l, c, grid[l][c].getHeight());
	}

	/**
	 * changeToNormal Changes any cell to a NormalCell
	 * 
	 * @param l
	 * @param c
	 */
	public void changeToNormal(int l, int c) {
		if (grid[l][c] instanceof EmptyCell)
			this.grid[l][c] = new NormalCell(l, c, 1);
		else
			this.grid[l][c] = new NormalCell(l, c, grid[l][c].getHeight());
	}

	/**
	 * getCell
	 * 
	 * @param l
	 *            : line
	 * @param c
	 *            : column
	 * @return the (l,c) cell of the grid
	 */
	public Cell getCell(int l, int c) {
		return this.grid[l][c];
	}

	/**
	 * setCell sets the level of the (l,c) cell
	 * 
	 * @param l
	 *            : line
	 * @param c
	 *            : column
	 * @param level
	 */
	public void setCell(Cell c) {
		this.grid[c.getX()][c.getY()] = c;
	}

	/**
	 * getNextCell
	 * 
	 * @param currentL
	 *            : current l position (line) of the robot
	 * @param currentC
	 *            : current c position (column) of the robot
	 * @param direction
	 *            : the current direction of the robot
	 * @return the next cell, according to the current position and the
	 *         direction of the robot
	 * @throws OutOfGridException
	 */
	public Cell getNextCell(int currentL, int currentC, CardinalDirection direction) throws OutOfGridException {

		switch (direction) {
		case NORTH:
			if (currentL == 0) {
				throw new OutOfGridException();
			}
			return this.grid[currentL - 1][currentC];
		case SOUTH:
			if (currentL == size - 1) {
				throw new OutOfGridException();
			}
			return this.grid[currentL + 1][currentC];
		case WEST:
			if (currentC == 0) {
				throw new OutOfGridException();
			}
			return this.grid[currentL][currentC - 1];
		case EAST:
			if (currentC == size - 1) {
				throw new OutOfGridException();
			} else {
				return this.grid[currentL][currentC + 1];
			}
		default:
			throw new OutOfGridException();
		}
	}

	/**
	 * printGrid Prints a grid
	 */
	public void printGrid() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].isEmptyCell()) {
					System.out.print("* / ");
				} else {
					System.out.print(grid[i][j].getHeight() + " /");
				}
				if (grid[i][j] instanceof LightableCell) {
					System.out.print("L\t");
				} else {
					if (grid[i][j] instanceof TeleportCell) {
						System.out.print("T\t");
					} else
						System.out.print("  \t");
				}
			}
			System.out.println();
		}
	}

	/**
	 * getSize
	 * 
	 * @return the size of a size*size grid
	 */
	public int getSize() {
		return this.size;
	}
}
