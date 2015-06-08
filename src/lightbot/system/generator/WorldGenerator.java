/**
 * 
 */
package lightbot.system.generator;

import java.util.Random;

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
	
	/**
	 * 
	 */
	public WorldGenerator() {
		grid = new Grid(10);
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
		
		int maximumInstructions = Random[5;20];;
		int maximumLight = Random[0;(maximumInstructions / 5)] + 1;;
		
		Cell currentCell = firstCell();
		_Action currentAction;
		
		int numberProcedures = 0;
		int numberinstruction = 0;

		while(numberinstruction <= maximumInstructions) {
			currentAction = giveAction();
			currentCell = setUpGrid(currentCell, currentAction);
			setUpProbalities();
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
	 * 
	 * @return
	 */
	private Cell firstCell() {
		return null;		
	}
	
	/**
	 * 
	 * @return
	 */
	private _Action giveAction() {
		return null;
	}
	
	/**
	 * 
	 * @param cell
	 * @param instruction
	 * @return
	 */
	private Cell setUpGrid(Cell cell, _Action instruction) {
		return null;
	}
	
	private void setUpProbalities() {
		
	}
}
