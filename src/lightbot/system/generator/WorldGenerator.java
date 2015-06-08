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
		_Executable currentAction;
		
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
		return null;		
	}
	
	/**
	 * Get a random action as a function of the probabilities
	 * @param numberLight The current number of the light in the algorithm
	 * @param maximumLight The maximum number of the light in this world
	 * @return Return an action among the possible action of the robot
	 */
	private _Executable giveAction(int maximumLight, int numberLight) {
		return null;
	}
	
	/**
	 * Create the next cell depending on the instruction and return this cell
	 * @param cell The current cell that was already
	 * @param instruction The instruction for create the next cell
	 * @param numInst The current number of the instruction in the algorithm 
	 * @param numProc The current number of the procedure in the algorithm
	 * @return Return the new cell create depending on the action
	 */
	private Cell setUpGrid(Cell cell, _Executable instruction, int numProc, int numInst) {
		return null;
	}
	
	/**
	 * Set up the probabilities of the element as a function of the previous instruction
	 * @param instruction The current instruction use for create the cell
	 */
	private void setUpProbalities(_Executable instruction) {
		
	}
}
