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
	
	@SuppressWarnings("unused")
	private int numberProcedures;
	private int numberInstruction;
	private int numberLight;
	private int height;
	
	private CardinalDirection direction;
	
	private Random rand;
	
	/**
	 * 
	 */
	public WorldGenerator() {
		numberProcedures = 0;
		numberInstruction = 0;
		numberLight = 0;
		
		grid = new Grid(8);
		rand = new Random();
		generation();
//		finishGeneration();
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
		
		int maximumInstructions = rand.nextInt(30 - 5 + 1) + 5;
		int maximumLight = rand.nextInt((maximumInstructions / 5) + 4) + 3;
		
		Cell currentCell = firstCell();
		Cell newCell;
		int currentAction;
		int previousAction = -1;
		
		int numLoop = 0;

		while(numberInstruction <= maximumInstructions) {
			numLoop++;
			currentAction = giveAction(maximumLight, previousAction);
			newCell = updateGrid(currentCell, currentAction);
			if (newCell == null) {
				currentAction = -1;
			} else {
				currentCell = newCell;
			}
			setUpProbalities(currentAction);
			if ((numLoop % 5) == 0) {
				initProba();
			}
			
			previousAction = currentAction;
		}
		
		currentCell.setLightable(true); 
//		System.out.println("Light ? -> " + currentCell.getLightable());
		
		System.out.println("Max - Instruction : " + maximumInstructions + " / LIght : " + maximumLight);
		System.out.println("Instruction : " + numberInstruction + " / LIght : " + numberLight);
		
	}
	
	private void finishGeneration() {
		boolean[] col = getColWithFullCell();
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
		firstcell.setHeight(rand.nextInt(2) + 1);
		height = firstcell.getHeight();
		firstcell.setLightable(rand.nextInt(2) != 0);
		/*
		boolean testLightable = firstcell.getLightable();
		if (testLightable){
			firstcell.setColour(Colour.GREEN);
		}
		else {
			firstcell.setColour(Colour.WHITE);
		}
		*/
		firstcell.setLight(false);

		if (rand.nextInt(2) == 0){
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
	private int giveAction(Integer maxLight, int prevAction) {
		int randAction = rand.nextInt(range);
		
//		System.out.print("Before while : " + randAction);
		
		while (randAction < probaLight && numberLight == (maxLight - 1)) {
			randAction = rand.nextInt(range);
//			System.out.println("In while : " + randAction + " instruction : " + numberInstruction);
		}
		
		while((randAction >= probaRight && randAction <= probaLeft) && (prevAction == 3 || prevAction == 4)) {
			randAction = rand.nextInt(range);
		}
		
//		System.out.println(" // After while : " + randAction);
		
		int action = -1;
		
		if (randAction < probaLight) {
				action = 0;
				numberLight++;
				System.out.print("Light !!!!");
		} else if (randAction < probaForward) {
			action = 1;
			System.out.print("Forward !!!!");
		} else if (randAction < probaJump) {
			action = 2;
			System.out.print("Jump !!!!");
		} else if (randAction < probaRight) {
			action = 3;
			System.out.print("Right !!!!");
		} else if (randAction < probaLeft) {
			action = 4;
			System.out.print("Left !!!!");
		}
		
		System.out.println(" // Action : " + action);
		
		return action;
	}
	
	/**
	 * Create the next cell depending on the instruction and return this cell
	 * @param cell The current cell that was already
	 * @param previousAction 
	 * @param currentAction The instruction for create the next cell
	 * @return Return the new cell create depending on the action, 
	 */
	private Cell updateGrid(Cell cell, int action) {
		System.out.println("Action : " + action);
		
		numberInstruction++;	
		switch (action) {
		case 0:
			cell.setLightable(true);
//			System.out.println("Light !!!! // x : " + cell.getposX() + " - y : " + cell.getposY());
//			System.out.println("Light ? -> " + cell.getLightable());
			break;
		case 1:
			try {
				Cell newCell = grid.getNextCell(cell.getposX(), cell.getposY(), direction);
				System.out.println("Heigh cell : " + newCell.getHeight());
				if (newCell.getHeight() == -1) {
					newCell.setHeight(cell.getHeight());
					cell = newCell;
				} else {
					numberInstruction--;
					cell = null;
					System.out.println("Empty Cell");
				}
			} catch (OutOfGridException e) {
				if (rand.nextInt(2) == 0) {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
					System.out.println("Update - Right !!!!");
				} else {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
					System.out.println("Update - Left !!!!");
				}
			}
			break;
		case 2:
			try {
				Cell newCell = grid.getNextCell(cell.getposX(), cell.getposY(), direction);
				System.out.println("Heigh cell : " + newCell.getHeight());
				if (newCell.getHeight() == -1) {
					if(cell.getHeight() == 1) {
						newCell.setHeight(cell.getHeight() + rand.nextInt(2));
						} else if (height == 4) {
							newCell.setHeight(cell.getHeight() + (rand.nextInt(2) - 1));
						} else {
						newCell.setHeight(cell.getHeight() + (rand.nextInt(3) - 1));
					}
					cell = newCell;
					height = cell.getHeight();
				} else {
					numberInstruction--;
					cell = null;
					System.out.println("Empty Cell");
				}
			} catch (OutOfGridException e) {
				if (rand.nextInt(2) == 0) {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
					System.out.println("Update - Right !!!!");
				} else {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
					System.out.println("Update - Left !!!!");
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
			numberInstruction--;
			break;
		}
		
		
		return cell;
	}
	
	/**
	 * Update the probabilities of the element depending on the previous instruction
	 * @param instruction The current instruction used to create the cell
	 */
	private void setUpProbalities(int instruction) {
//		System.out.println("action in proba :" + instruction);
		
		switch(instruction){
		case 0:
			initProba();
			break;
		case 1:
			probaLight++;
			probaForward--;
			break;
		case 2:
			probaLight++;
			probaJump--;
			break;
		case 3:
			probaLight++;
			probaRight--;
			break;
		case 4:
			probaLight++;
			probaLeft--;
			break;
		default:
			System.out.println("The instruction does not exist!");
		}
		
		//printProba();
	}
	
	private boolean[] getColWithFullCell() {
		int j = 0;
		boolean[] tab = {true};
		for (int i = 0; i < 8; i++) {
			j = 0;
			while (j < 8 && !(grid.isEmpty(i,j))) { j++; }
			if (j == 8) {
				tab[i] = false;
			}
		}
		return tab;
	}
	
	private boolean[] getLineWithFullCell() {
		int j = 0;
		boolean[] tab = {true};
		for (int i = 0; i < 8; i++) {
			j = 0;
			while (j < 8 && !(grid.isEmpty(j,i))) { j++; }
			if (j == 8) {
				tab[i] = false;
			}
		}
		return tab;
	}
	
	private void printProba() {
		System.out.println("Light : " + probaLight + " / Forward : " + probaForward  + " / Jump : " + probaJump  + " / Left : " + probaLeft + " / Right : " + probaRight);
	}
}