/**
 * 
 */
package lightbot.system.generator;

import java.util.Random;

import lightbot.system.CardinalDirection;
import lightbot.system.world.Grid;
import lightbot.system.world.cell.Cell;

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
	 * 
	 */
	private void generation() {
		// TODO Auto-generated method stub
		
	}

	
}
