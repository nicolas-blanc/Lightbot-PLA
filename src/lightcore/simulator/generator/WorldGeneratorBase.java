/**
 * 
 */
package lightcore.simulator.generator;

import lightcore.simulator.*;
import lightcore.world.*;
import lightcore.world.cell.*;

/**
 * @author Nasheis
 *
 */
public class WorldGeneratorBase extends WorldGenerator{
	
	/**
	 * 
	 */
	public WorldGeneratorBase() {
		super();
		finishGeneration();
	}
	
	/*
	 * (non-Javadoc)
	 * @see lightbot.system.generator.WorldGenerator#generation()
	 */
	@Override
	protected void generation() {
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
		
//		System.out.println("Max - Instruction : " + maximumInstructions + " / LIght : " + maximumLight);
//		System.out.println("Instruction : " + numberInstruction + " / LIght : " + numberLight);

		finishGeneration();
	}
	
	/*
	 * (non-Javadoc)
	 * @see lightbot.system.generator.WorldGenerator#giveAction(int, int)
	 */
	@Override
	protected int giveAction(int maxLight, int prevAction) {
		int randAction = rand.nextInt(probabilities.getRangeBase());
		
//		System.out.print("Before while : " + randAction);
		
		while (randAction < probabilities.getProbaLight() && numberLight == (maxLight - 1)) {
			randAction = rand.nextInt(probabilities.getRangeBase());
		}
		
		while((randAction >= probabilities.getProbaJump() && randAction < probabilities.getProbaLeft()) && (prevAction == 3 || prevAction == 4)) {
			randAction = rand.nextInt(probabilities.getRangeBase());
		}
		
//		System.out.println(" // After while : " + randAction);
		
		int action = -1;
		
		if (randAction < probabilities.getProbaLight()) {
				action = 0;
				numberLight++;
//				System.out.print("Light !!!!");
		} else if (randAction < probabilities.getProbaForward()) {
			action = 1;
//			System.out.print("Forward !!!!");
		} else if (randAction < probabilities.getProbaJump()) {
			action = 2;
//			System.out.print("Jump !!!!");
		} else if (randAction < probabilities.getProbaRight()) {
			action = 3;
//			System.out.print("Right !!!!");
		} else if (randAction < probabilities.getProbaLeft()) {
			action = 4;
//			System.out.print("Left !!!!");
		}
		
//		System.out.println(" // Action : " + action);
		
		return action;
	}
	
	/*
	 * (non-Javadoc)
	 * @see lightbot.system.generator.WorldGenerator#updateGrid(lightbot.system.world.cell.Cell, int)
	 */
	@Override
	protected Cell updateGrid(Cell cell, int action) {
//		System.out.println("Action : " + action);
		
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
//					System.out.println("Not Empty Cell");					
					if (cell.getHeight() <= (height + 1) && cell.getHeight() >= (height - 1)) {
						cell = emptyCell;
					} else {
						if (rand.nextInt(2) == 0) {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
//							System.out.println("Update - Right !!!!");
						} else {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
//							System.out.println("Update - Left !!!!");
						}
					}
				}
			} catch (OutOfGridException e) {
				if (rand.nextInt(2) == 0) {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
//					System.out.println("Update - Right !!!!");
				} else {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
//					System.out.println("Update - Left !!!!");
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
						} else if (height == 3) {
							grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), (cell.getHeight() + (rand.nextInt(2) - 1))));
						} else {
							grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), (cell.getHeight() + (rand.nextInt(3) - 1))));
					}
					cell = grid.getCell(emptyCell.getX(), emptyCell.getY());
					height = cell.getHeight();
				} else {
//					System.out.println("Not Empty Cell");
					if (cell.getHeight() <= (height + 1) && cell.getHeight() >= (height - 1)) {
						cell = emptyCell;
					} else {
						if (rand.nextInt(2) == 0) {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
//							System.out.println("Update - Right !!!!");
						} else {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
//							System.out.println("Update - Left !!!!");
						}
					}
				}
			} catch (OutOfGridException e) {
				if (rand.nextInt(2) == 0) {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
//					System.out.println("Update - Right !!!!");
				} else {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
//					System.out.println("Update - Left !!!!");
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
}