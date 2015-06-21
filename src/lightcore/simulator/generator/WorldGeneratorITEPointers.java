/**
 * 
 */
package lightcore.simulator.generator;

import java.util.ArrayList;

import lightcore.simulator.TeleportColour;
import lightcore.world.CardinalDirection;
import lightcore.world.OutOfGridException;
import lightcore.world.RelativeDirection;
import lightcore.world.cell.Cell;
import lightcore.world.cell.NormalCell;
import lightcore.world.cell.TeleportCell;

/**
 * @author Nasheis
 *
 */
public class WorldGeneratorITEPointers extends WorldGenerator{
	private int numberPattern;
	
	private Pattern pattern;

	private ArrayList<TeleportColour> usedColour;
	
	/**
	 * 
	 */
	public WorldGeneratorITEPointers() {
		super();
		//finishGeneration();
	}
	
	/*
	 * (non-Javadoc)
	 * @see lightbot.system.generator.WorldGenerator#generation()
	 */
	@Override
	protected void generation() {
		probabilities.initProba();
			
		int maximumInstructions = rand.nextInt(25 - 5 + 1) + 5;
		int maximumLight = rand.nextInt((maximumInstructions / 5) + 4) + 1;
	
		pattern = new Pattern(maximumInstructions);
		
		Cell currentCell = firstCell();
		Cell newCell;
		int currentAction;
		int previousAction = -2;
		
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
		
		if (currentCell instanceof NormalCell) {
			grid.changeToLightable(currentCell.getX(),currentCell.getY());
		}
//			System.out.println("Max - Instruction : " + maximumInstructions + " / LIght : " + maximumLight);
//			System.out.println("Instruction : " + numberInstruction + " / LIght : " + numberLight);
		
	}
	
	/* (non-Javadoc)
	 * @see lightbot.system.generator.WorldGenerator#initVariable()
	 */
	@Override
	protected void initVariable() {
		// TODO Auto-generated method stub
		super.initVariable();
		usedColour = new ArrayList<TeleportColour>();
		numberPattern = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see lightbot.system.generator.WorldGenerator#giveAction(int, int)
	 */
	@Override
	protected int giveAction(int maxLight, int prevAction) {
		int randAction = -1;
		if (prevAction == -2) {
			randAction = rand.nextInt(probabilities.getRangePattern());
		} else {
			randAction = rand.nextInt(probabilities.getRange());
		}
//			System.out.print("Before while : " + randAction);
		
		while (randAction < probabilities.getProbaLight() && numberLight == (maxLight - 1)) {
			randAction = rand.nextInt(probabilities.getRangePattern());
		}
		
		while((randAction >= probabilities.getProbaJump() && randAction < probabilities.getProbaLeft()) && (prevAction == 3 || prevAction == 4)) {
			randAction = rand.nextInt(probabilities.getRange());
		}
		
		while(randAction >= probabilities.getProbaLeft() &&  randAction < probabilities.getProbaPattern() && numberPattern > pattern.getVariation()) {
			randAction = rand.nextInt(probabilities.getRangeBase());			
		}
		
		while(randAction >= probabilities.getProbaPattern() && randAction < probabilities.getProbaPointer() && prevAction == 6) {
			randAction = rand.nextInt(probabilities.getRange());						
		}
		
//			System.out.println(" // After while : " + randAction);
		
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
		} else if (randAction < probabilities.getProbaPointer()) {
			action = 6;
//			System.out.println("Pointers !!!");
		}

//		System.out.println();
//			System.out.println(" // Action : " + action);
		
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
			if (cell instanceof NormalCell) {
				grid.changeToLightable(cell.getX(), cell.getY());
//				System.out.println("Light !!!! // x : " + cell.getposX() + " - y : " + cell.getposY());
//				System.out.println("Light ? -> " + cell.getLightable());
			} else {
				cell = null;
				numberInstruction--;
			}
			break;
		case 1:
			try {
				Cell emptyCell = grid.getNextCell(cell.getX(), cell.getY(), direction);
//					System.out.println("Heigh cell : " + emptyCell.getHeight());
				if (emptyCell.isEmptyCell()) {
					grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), cell.getHeight()));
					cell = grid.getCell(emptyCell.getX(), emptyCell.getY());
				} else {
//						System.out.println("Not Empty Cell");					
					if (cell.getHeight() <= (height + 1) && cell.getHeight() >= (height - 1)) {
						cell = emptyCell;
					} else {
						if (rand.nextInt(2) == 0) {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
//								System.out.println("Update - Forward - Right !!!!");
						} else {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
//								System.out.println("Update - Forward - Left !!!!");
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
//					System.out.println("Heigh cell : " + emptyCell.getHeight());
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
//						System.out.println("Not Empty Cell");
					if (cell.getHeight() <= (height + 1) && cell.getHeight() >= (height - 1)) {
						cell = emptyCell;
					} else {
						if (rand.nextInt(2) == 0) {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);
//								System.out.println("Update - Jump - Right !!!!");
						} else {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);
//								System.out.println("Update - Jump - Left !!!!");
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
		case 6:
			if (cell.getX() != 0 || cell.getY() != 0) {
				cell = updateCellPointers(cell);
			} else {
				cell = null;
				numberInstruction--;
			}
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
			while (!(pattern.endOfPattern()) && !(error)) {
				action = pattern.nextAction(); 
//				System.out.println("action in pattern : " + action);
				switch (action) {
				case 0:
					if (cell instanceof NormalCell) {
						grid.changeToLightable(cell.getX(), cell.getY());
					} else {
						error = true;
						cell = null;
					}
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
	 * 
	 * @param cell
	 * @return
	 */
	private Cell updateCellPointers(Cell cell) {
		TeleportColour colour = getColour();
//		System.out.println("Normal cell ? : " + (cell instanceof NormalCell));
		if ((cell instanceof NormalCell) && colour != null) {
			int xTp;
			int yTp;
			
			do {
				xTp = rand.nextInt(size);
				yTp = rand.nextInt(size);
			} while (!(grid.getCell(xTp, yTp).isEmptyCell()));
			
			TeleportCell cellTp = new TeleportCell(xTp, yTp, height, cell.getX(), cell.getY(), colour);
			grid.setCell(cellTp);
			grid.setCell(new TeleportCell(cell.getX(), cell.getY(), height, cellTp.getX(), cellTp.getY(), colour));
			
			System.out.println("first cell -> X : " + cell.getX() + " -  Y : " + cell.getY() + " // dest cell -> X : " + cellTp.getX() + " - Y : " + cellTp.getY() + " // Colour : " + colour.toString());
			
			return cellTp;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	private TeleportColour getColour(){
		if (usedColour.size() != TeleportColour.values().length) {
			TeleportColour colour;
			do{
				colour = TeleportColour.randomColour();
			} while(usedColour.contains(colour));
			usedColour.add(colour);
			return colour;
		}
		else{
			return null;
		}
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

