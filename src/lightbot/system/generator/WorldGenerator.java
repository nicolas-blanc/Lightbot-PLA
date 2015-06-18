/**
 * 
 */
package lightbot.system.generator;

import java.util.Random;

import lightbot.system.CardinalDirection;
import lightbot.system.Robot;
import lightbot.system.world.Grid;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.cell.LightableCell;
import lightbot.system.world.cell.NormalCell;

/**
 * @author Nasheis
 *
 */
public abstract class WorldGenerator {
	protected Grid grid;
	
	protected int numberInstruction;
	protected int numberLight;
	protected int height;
	
	protected Probabilities probabilities;
	
	protected CardinalDirection direction;
	
	protected final Random rand = new Random();
	
	protected final int size = 8;

	/**
	 * 
	 */
	public WorldGenerator() {
		initVariable();
		
		if (rand.nextInt(2) == 0){
			direction = CardinalDirection.EAST;
		}
		else {
			direction = CardinalDirection.SOUTH;
		}
		
		Robot.wheatley.setPosition(0, 0);
		Robot.wheatley.setDirection(direction);
		
		generation();
	}
	
	/**
	 * 
	 * @return
	 */
	public Grid getGrid() {
		return grid;
	}
	
	/**
	 * 
	 */
	protected abstract void generation();
	
	/**
	 * Get a random action as a function of the probabilities
	 * @param maxLight The maximum number of the light in this world
	 * @param numLight The current number of the light in the algorithm
	 * @return Return an action among the possible action of the robot, -1 if there is an error
	 */
	protected abstract int giveAction(int maxLight, int prevAction);
	
	/**
	 * Create the next cell depending on the instruction and return this cell
	 * @param cell The current cell that was already
	 * @param action The instruction for create the next cell
	 * @return Return the new cell create depending on the action, 
	 */
	protected abstract Cell updateGrid(Cell cell, int action);
	
	/**
	 * 
	 */
	protected void initVariable() {
		numberInstruction = 0;
		numberLight = 0;
		
		grid = new Grid(size);
		probabilities = new Probabilities();
	}

	/**
	 * Get a random case in the grid for the first case of the algorithm and generate then.
	 * @return Return the first case of the algorithm
	 */
	protected Cell firstCell() {
		if(rand.nextInt(2) != 0) {
			grid.setCell(new LightableCell(0, 0, 0));
		} else {
			grid.setCell(new NormalCell(0, 0, 0));
		}
		
		Cell firstcell = grid.getCell(0, 0);
		height = firstcell.getHeight();

		if (rand.nextInt(2) == 0){
			direction = CardinalDirection.EAST;
		}
		else {
			direction = CardinalDirection.SOUTH;
		}
		return firstcell;		
	}
	
	/**
	 * 
	 */
	protected void finishGeneration() {
		int[] col = getColWithFullCell();
		int[] line = getLineWithFullCell();
		
/*
		System.out.print("Col :  ");
		for (int i = 0; i < col.length; i++) { System.out.print(col[i] + " / "); }
		System.out.println();
		
		System.out.print("Line : ");
		for (int i = 0; i < line.length; i++) { System.out.print(line[i] + " / "); }
		System.out.println();
*/
		
		for (int i = 0; i < col.length; i++) {
			for (int j = 0; j < line.length; j++) {
				if(line[j] > 0 && col[i] > 0 && grid.getCell(j, i).isEmptyCell()) {
					
					if (col[i] < line[j]) {
						grid.setCell(new NormalCell(j, i, rand.nextInt(col[i])));
					} else {
						grid.setCell(new NormalCell(j, i, rand.nextInt(line[j])));
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private int[] getColWithFullCell() {
		int[] tab = new int[size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!(grid.getCell(j, i).isEmptyCell()) && grid.getCell(j, i).getHeight() >= tab[i]) {
					tab[i] = grid.getCell(j, i).getHeight() + 1;
				}
			}
		}
		return tab;
	}
	
	/**
	 * 
	 * @return
	 */
	private int[] getLineWithFullCell() {		
		int[] tab = new int[size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!(grid.getCell(i, j).isEmptyCell()) && grid.getCell(i, j).getHeight() >= tab[i]) {
						tab[i] = grid.getCell(i, j).getHeight() + 1;
				}
			}
		}
		return tab;
	}
}
