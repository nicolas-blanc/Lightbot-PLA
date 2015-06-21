/**
 * 
 */
package lightcore.simulator.generator;

import lightcore.world.CardinalDirection;
import lightcore.world.OutOfGridException;
import lightcore.world.RelativeDirection;
import lightcore.world.cell.Cell;
import lightcore.world.cell.NormalCell;

/**
 * @author Nasheis
 *
 */
public class WorldGeneratorProcedure extends WorldGenerator{
	private int numberPattern;
	
	private Pattern pattern;
	
	/**
	 * 
	 */
	public WorldGeneratorProcedure() {
		super();
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
	
		pattern = new Pattern(maximumInstructions);
		
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
	
	/* (non-Javadoc)
	 * @see lightbot.system.generator.WorldGenerator#initVariable()
	 */
	@Override
	protected void initVariable() {
		super.initVariable();
		numberPattern = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see lightbot.system.generator.WorldGenerator#giveAction(int, int)
	 */
	@Override
	protected int giveAction(int maxLight, int prevAction) {
		int randAction = rand.nextInt(probabilities.getRangePattern());
		
//		System.out.print("Before while : " + randAction);
		
		while (randAction < probabilities.getProbaLight() && numberLight == (maxLight - 1)) {
			randAction = rand.nextInt(probabilities.getRangePattern());
//			System.out.println("In while : " + randAction + " instruction : " + numberInstruction);
		}
		
		while((randAction >= probabilities.getProbaJump() && randAction < probabilities.getProbaLeft()) && (prevAction == 3 || prevAction == 4)) {
			randAction = rand.nextInt(probabilities.getRangePattern());
		}
		
		while(randAction >= probabilities.getProbaLeft() &&  randAction < probabilities.getProbaPattern() && numberPattern > pattern.getVariation()) {
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
		} else if (randAction < probabilities.getProbaPattern()) {
			action = 5;
//			System.out.println("Pattern !!!!");
		}

//		System.out.println();
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
//							System.out.println("Update - Forward - Right !!!!");
						} else {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
//							System.out.println("Update - Forward - Left !!!!");
						}
					}
				}
			} catch (OutOfGridException e) {
				if (rand.nextInt(2) == 0) {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
//					System.out.println("Update - Forward - Right !!!!");
				} else {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
//					System.out.println("Update - Forward - Left !!!!");
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
//							System.out.println("Update - Jump - Right !!!!");
						} else {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
//							System.out.println("Update - Jump - Left !!!!");
						}
					}
				}
			} catch (OutOfGridException e) {
				if (rand.nextInt(2) == 0) {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
//					System.out.println("Update - Jump - Right !!!!");
				} else {
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
//					System.out.println("Update - Jump - Left !!!!");
				}
			}
			break;
		case 3:
			direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
			break;
		case 4:
			direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
			break;
		case 5:
			cell = updateGridWithPattern(cell);
			break;
		default:
			cell = null;
			numberInstruction--;
			break;
		}
		
		
		return cell;
	}

	/**
	 * 
	 * @param cell
	 * @return
	 */
	private Cell updateGridWithPattern(Cell cell) {
		boolean error = false;
		int action;
		
		if(patternIsPossible(cell)) {
//			System.out.println("Pattern ok, go for while");
			numberPattern++;
			while (!(pattern.endOfPattern()) && !(error)) {
				action = pattern.nextAction(); 
//				System.out.println("action in pattern : " + action);
				switch (action) {
				case 0:
					grid.changeToLightable(cell.getX(), cell.getY());
					break;
				case 1:
					try {
						Cell emptyCell = grid.getNextCell(cell.getX(), cell.getY(), direction);
						if (emptyCell.isEmptyCell()) {
							grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), cell.getHeight()));
							cell = grid.getCell(emptyCell.getX(), emptyCell.getY());
						} else {
							if (cell.getHeight() == height) {
								cell = emptyCell;
							} else {
								error = true;
								cell = null;
							}
						}
					} catch (OutOfGridException e) {
						error = true;
//						System.out.println("Error in update grid for a pattern with forward");
					}
					break;
				case 2:
					try {
						Cell emptyCell = grid.getNextCell(cell.getX(), cell.getY(), direction);
						if (emptyCell.isEmptyCell()) {
							if(cell.getHeight() == 0) {
								grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), 1));
								} else if (height == 3) {
									grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), 3));
								} else {
									int jump = 0;
									while (jump == 0) {
										jump = rand.nextInt(3) - 1;
									}
									grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), (cell.getHeight() + jump)));
							}
							cell = grid.getCell(emptyCell.getX(), emptyCell.getY());
							height = cell.getHeight();
						} else {

							if (cell.getHeight() <= (height + 1) && cell.getHeight() >= (height - 1)) {
								cell = emptyCell;
							} else {
								error = true;
								cell = null;
							}
						}
					} catch (OutOfGridException e) {
						error = true;
//						System.out.println("Error in update grid for a pattern with jump");
					}
					break;
				case 3:
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
					break;
				case 4:
					direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
					break;
				default:
					break;
				}
			}
		} else {
			cell = null;
		}
		
		return cell;
	}

	/**
	 * @param cell 
	 * @return
	 */
	private boolean patternIsPossible(Cell cell) {
		boolean bool = true;
		CardinalDirection dir = direction;
		
//		System.out.println("In test pattern");
		
		while (bool && !(pattern.endOfPattern())) {
			try {
				switch (pattern.nextAction()) {
				case 1:
					cell = grid.getNextCell(cell.getX(), cell.getY(), dir);
					break;
				case 2:
					cell = grid.getNextCell(cell.getX(), cell.getY(), dir);
					break;
				case 3:
					dir = CardinalDirection.getRotationDirection(dir, RelativeDirection.RIGHT);
					break;
				case 4:
					dir = CardinalDirection.getRotationDirection(dir, RelativeDirection.LEFT);
					break;
				default:
					break;
				}
			} catch (OutOfGridException e) {
				
				bool = false;
			}
		}

/*
		if(bool)
			System.out.println("Pattern ok");
		else
			System.out.println("Pattern non possible");
*/
		
		pattern.resetIteration();
		
		return bool;
	}
}
