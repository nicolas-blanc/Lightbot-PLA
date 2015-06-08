/**
 * 
 */
package lightbot.system.generator;

import java.util.Random;

import lightbot.system.*;
import lightbot.system.action.*;
import lightbot.system.world.*;

/**
 * @author Nasheis
 *
 */
public class WorldGenerator {	
	private Grid grid;
	
	private int probaLight;
	private int probaForward;
	private int probaJump;
	private int probaRight;
	private int probaLeft;
	private CardinalDirection direction;
	
	private Random rand;
	
	/**
	 * 
	 */
	public WorldGenerator() {
		grid = new Grid(10);
		rand = new Random();
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
	private void generation() {
		initProba();
		
		int maximumInstructions = rand.nextInt(20 - 5 + 1) + 5;
		int maximumLight = rand.nextInt((maximumInstructions / 5) + 1);
		
		Cell currentCell = firstCell();
		int currentAction;
		
		int numberProcedures = 0;
		int numberInstruction = 0;
		int numberLight = 0;

		while(numberInstruction <= maximumInstructions) {
			currentAction = giveAction(maximumLight, numberLight);
			currentCell = setUpGrid(currentCell, currentAction, numberProcedures, numberInstruction);
			setUpProbalities(currentAction);
		}
		
		currentCell.setLightable(true);
	}
	
	/**
	 * 
	 */
	private void initProba() {
		probaLight = 2;
		probaForward = 4;
		probaJump = 4;
		probaRight= 1;
		probaLeft = 1;
	}

	
	/**
	 * Get a random case in the grid for the first case of the algorithm and generate then.
	 * @return Return the first case of the algorithm
	 */
	private Cell firstCell() {
		Cell firstcell = grid.getCell(0, 0);
		firstcell.setHeight(rand.nextInt(1) + 1);
		firstcell.setLightable(rand.nextInt(0) != 0);
		boolean testLightable = firstcell.getLightable();
		if (testLightable){
			firstcell.setColour(Colour.GREEN);
		}
		else {
			firstcell.setColour(Colour.WHITE);
		}
		firstcell.setLight(false);
		int dir = rand.nextInt(0);
		if (dir == 0){
			direction = CardinalDirection.EAST;
		}
		else {
			direction = CardinalDirection.SOUTH;
		}
		return firstcell;		
	}
	
	/**
	 * Get a random action depending on its probabilities
	 * @param numberLight The current number of the light in the algorithm
	 * @param maximumLight The maximum number of the light in this world
	 * @return Return an action among the possible action of the robot
	 */
	private int giveAction(int maximumLight, int numberLight) {
		return 0;
	}
	
	/**
	 * Create the next cell depending on the instruction and return this cell
	 * @param cell The current cell that was already
	 * @param currentAction The instruction for create the next cell
	 * @param numInst The current number of the instruction in the algorithm 
	 * @param numProc The current number of the procedure in the algorithm
	 * @return Return the new cell create depending on the action
	 */
	private Cell setUpGrid(Cell cell, int currentAction, int numProc, int numInst) {
		return null;
	}
	
	/**
	 * Update the probabilities of the element depending on the previous instruction
	 * @param instruction The current instruction used to create the cell
	 */
	private void setUpProbalities(int instruction) {
		switch(instruction){
		case 1:
			initProba();
			break;
		case 2:
			probaLight++;
			probaForward--;
			break;
		case 3:
			probaLight++;
			probaJump--;
			break;
		case 4:
			probaLight++;
			probaRight--;
			break;
		case 5:
			probaLight++;
			probaLeft--;
			break;
		default:
			System.out.println("The instruction does not exist!");
		}
	}
}
