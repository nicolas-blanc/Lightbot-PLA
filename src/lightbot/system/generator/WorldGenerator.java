/**
 * 
 */
package lightbot.system.generator;

import java.util.Random;

import lightbot.system.*;
import lightbot.system.world.*;
import lightbot.system.world.cell.*;

/**
 * @author Nasheis
 *
 */
public class WorldGenerator {
	private Grid grid;
	
	@SuppressWarnings("unused")
	private int numberProcedures;
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
	public WorldGenerator() {
		numberProcedures = 0;
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
		probabilities.initProba();
		
		int maximumInstructions = rand.nextInt(30 - 5 + 1) + 5;
		int maximumLight = rand.nextInt((maximumInstructions / 5) + 4) + 1;
		
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
			probabilities.setUpProbalities(currentAction);
			if ((numLoop % 5) == 0) {
				probabilities.initProba();
			}
			
			previousAction = currentAction;
		}
		
		grid.changeToLightable(currentCell.getX(),currentCell.getY());
//		System.out.println("Light ? -> " + currentCell.getLightable());
		
		System.out.println("Max - Instruction : " + maximumInstructions + " / LIght : " + maximumLight);
		System.out.println("Instruction : " + numberInstruction + " / LIght : " + numberLight);
		
	}
	
	private void finishGeneration() {
		int[] col = getColWithFullCell();
		int[] line = getLineWithFullCell();
		
		System.out.print("Col :");
		for (int i = 0; i < col.length; i++) { System.out.print(col[i] + " / "); }
		System.out.println();
		
		System.out.print("Line :");
		for (int i = 0; i < line.length; i++) { System.out.print(line[i] + " / "); }
		System.out.println();
		
		for (int i = 0; i < col.length; i++) {
			for (int j = 0; j < line.length; j++) {
				//!a.b+a.!b
				if(line[j] == 0 && col[i] == 0 && grid.getCell(j, i).isEmptyCell()) {
					grid.setCell(new NormalCell(j, i, rand.nextInt(3)));
				}
			}
		}
	}
	
	/**
	 * Get a random case in the grid for the first case of the algorithm and generate then.
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
	
	/**
	 * Get a random action as a function of the probabilities
	 * @param maxLight The maximum number of the light in this world
	 * @param numLight The current number of the light in the algorithm
	 * @return Return an action among the possible action of the robot, -1 if there is an error
	 */
	private int giveAction(Integer maxLight, int prevAction) {
		int randAction = rand.nextInt(probabilities.getRange());
		
//		System.out.print("Before while : " + randAction);
		
		while (randAction < probabilities.getProbaLight() && numberLight == (maxLight - 1)) {
			randAction = rand.nextInt(probabilities.getRange());
//			System.out.println("In while : " + randAction + " instruction : " + numberInstruction);
		}
		
		while((randAction >= probabilities.getProbaRight() && randAction <= probabilities.getProbaLeft()) && (prevAction == 3 || prevAction == 4)) {
			randAction = rand.nextInt(probabilities.getRange());
		}
		
//		System.out.println(" // After while : " + randAction);
		
		int action = -1;
		
		if (randAction < probabilities.getProbaLight()) {
				action = 0;
				numberLight++;
				System.out.print("Light !!!!");
		} else if (randAction < probabilities.getProbaForward()) {
			action = 1;
			System.out.print("Forward !!!!");
		} else if (randAction < probabilities.getProbaJump()) {
			action = 2;
			System.out.print("Jump !!!!");
		} else if (randAction < probabilities.getProbaRight()) {
			action = 3;
			System.out.print("Right !!!!");
		} else if (randAction < probabilities.getProbaLeft()) {
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
			grid.changeToLightable(cell.getX(), cell.getY());
//			System.out.println("Light !!!! // x : " + cell.getposX() + " - y : " + cell.getposY());
//			System.out.println("Light ? -> " + cell.getLightable());
			break;
		case 1:
			try {
				Cell emptyCell = grid.getNextCell(cell.getX(), cell.getY(), direction);
//				System.out.println("Heigh cell : " + emptyCell.getHeight());
				if (emptyCell.isEmptyCell()) {
					grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), cell.getHeight()));
					cell = grid.getCell(emptyCell.getX(), emptyCell.getY());
				} else {
					//numberInstruction--;
					//cell = null;
					System.out.println("Not Empty Cell");					
					if (cell.getHeight() <= (height + 1) && cell.getHeight() >= (height - 1)) {
						cell = emptyCell;
					} else {
						if (rand.nextInt(2) == 0) {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
							System.out.println("Update - Right !!!!");
						} else {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
							System.out.println("Update - Left !!!!");
						}
					}
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
				Cell emptyCell = grid.getNextCell(cell.getX(), cell.getY(), direction);
//				System.out.println("Heigh cell : " + emptyCell.getHeight());
				if (emptyCell.isEmptyCell()) {
					if(cell.getHeight() == 0) {
						grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), (cell.getHeight() + rand.nextInt(2))));
						} else if (height == 4) {
							grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), (cell.getHeight() + (rand.nextInt(2) - 1))));
						} else {
							grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), (cell.getHeight() + (rand.nextInt(3) - 1))));
					}
					cell = grid.getCell(emptyCell.getX(), emptyCell.getY());
					height = cell.getHeight();
				} else {
					//numberInstruction--;
					//cell = null;
					System.out.println("Not Empty Cell");
					if (cell.getHeight() <= (height + 1) && cell.getHeight() >= (height - 1)) {
						cell = emptyCell;
					} else {
						if (rand.nextInt(2) == 0) {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
							System.out.println("Update - Right !!!!");
						} else {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
							System.out.println("Update - Left !!!!");
						}
					}
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
	
	private int[] getColWithFullCell() {
		int j = 0;
		int[] tab = new int[size];
		for (int i = 0; i < size; i++) {
			j = 0;
			while (j < size && (grid.getCell(j, i).isEmptyCell())) { j++; }
			if (j == size) {
				tab[i] = 1;
			}
		}
		return tab;
	}
	
	private int[] getLineWithFullCell() {		
		int j = 0;
		int[] tab = new int[size];
		for (int i = 0; i < size; i++) {
			j = 0;
			while (j < size && (grid.getCell(i, j).isEmptyCell())) { j++; }
			if (j == size) {
				tab[i] = 1;
			}
		}
		return tab;
	}
}