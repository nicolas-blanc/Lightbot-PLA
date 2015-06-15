/**
 * 
 */
package lightbot.system.generator;

import java.util.Random;

import lightbot.system.CardinalDirection;
import lightbot.system.world.Grid;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.cell.LightableCell;
import lightbot.system.world.cell.NormalCell;

/**
 * @author Nasheis
 *
 */
public class WorldGeneratorProcedure {
	private Grid grid;
	
	private int numberInstruction;
	private int numberLight;
	private int height;
	
	private Probabilities probabilities;
	
	private CardinalDirection direction;
	
	private Random rand;
	
	final private int size = 7;
	
	/**
	 * 
	 */
	public WorldGeneratorProcedure() {
		numberInstruction = 0;
		numberLight = 0;
			
		grid = new Grid(size);
		rand = new Random();
		probabilities = new Probabilities();
		
		generation();
		finishGeneration();
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
	private void generation() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	private void finishGeneration() {
		probabilities.initProba();
		
		int maximumInstructions = rand.nextInt(30 - 5 + 1) + 5;
		int maximumLight = rand.nextInt((maximumInstructions / 5) + 4) + 1;
		
		Cell currentCell = firstCell();
		Cell newCell;
		int currentAction;
		int previousAction = -1;
		
		int numLoop = 0;
	}

	/**
	 * Generate the first cell of the algorithm at the position 0,0.
	 * @return Return the first case of the algorithm
	 */
	private Cell firstCell() {
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
}
