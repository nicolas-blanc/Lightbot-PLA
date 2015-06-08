/**
 * 
 */
package lightbot.system.generator;

import java.util.Random;

import lightbot.system.*;
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
	final private int range = 12;
	
	private int numberProcedures;
	private int numberInstruction;
	private int numberLight;
	
	private CardinalDirection direction;
	
	private Random rand;
	
	/**
	 * 
	 */
	public WorldGenerator() {
		numberProcedures = 0;
		numberInstruction = 0;
		numberLight = 0;
		
		grid = new Grid(10);
		rand = new Random();
		generation();
		grid.levelToZero();
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
		Cell newCell;
		int currentAction;

		while(numberInstruction <= maximumInstructions) {
			currentAction = giveAction(maximumLight);
			newCell = updateGrid(currentCell, currentAction);
			if (newCell == null) {
				currentAction = -1;
			} else {
				currentCell = newCell;
			}
			setUpProbalities(currentAction);
		}
		
		currentCell.setLightable(true);
	}
	
	/**
	 * 
	 */
	private void initProba() {
		probaLight = 2;
		probaForward = 6;
		probaJump = 10;
		probaRight= 11;
		probaLeft = 12;
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
	 * Get a random action as a function of the probabilities
	 * @param maxLight The maximum number of the light in this world
	 * @param numLight The current number of the light in the algorithm
	 * @return Return an action among the possible action of the robot, -1 if there is an error
	 */
	private int giveAction(Integer maxLight) {
		int randAction = rand.nextInt(range);
		int action = -1;
		
		if (randAction <= probaLight) {
			if (numberLight < maxLight) {
				action = 0;
				numberLight++;
			} else {
				action = giveAction(maxLight);
			}
		} else if (randAction <= (probaForward - 1)) {
			action = 1;
		} else if (randAction <= (probaJump - 1)) {
			action = 2;
		} else if (randAction == (probaRight - 1)) {
			action = 3;
		} else if (randAction == (probaLeft - 1)) {
			action = 4;
		}
		
		return action;
	}
	
	/**
	 * Create the next cell depending on the instruction and return this cell
	 * @param cell The current cell that was already
	 * @param currentAction The instruction for create the next cell
	 * @return Return the new cell create depending on the action, 
	 */
	private Cell updateGrid(Cell cell, int action) {
		switch (action) {
		case 0:
			cell.getLightable();
			break;
		case 1:
			try {
				Cell newCell = grid.getNextCell(cell.getposX(), cell.getposY(), direction);
				newCell.setHeight(cell.getHeight());
				cell = newCell;
			} catch (OutOfGridException e) {
				if (rand.nextInt(2) == 0) {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
				} else {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
				}
			}
			break;
		case 2:
			try {
				Cell newCell = grid.getNextCell(cell.getposX(), cell.getposY(), direction);
				if(cell.getHeight() == 1) {
					newCell.setHeight(cell.getHeight() + rand.nextInt(2));
				} else {
					newCell.setHeight(cell.getHeight() + (rand.nextInt(3) - 1));
				}
				cell = newCell;
			} catch (OutOfGridException e) {
				if (rand.nextInt(2) == 0) {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
				} else {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
				}
			}
			break;
		case 3:
			direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
			break;
		case 4:
			direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
			break;
		default:
			cell = null;
			break;
		}
		
		return cell;
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