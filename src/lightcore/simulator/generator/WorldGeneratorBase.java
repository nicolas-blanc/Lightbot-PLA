/**
 * 
 */
package lightcore.simulator.generator;

import lightcore.world.CardinalDirection;
import lightcore.world.OutOfGridException;
import lightcore.world.RelativeDirection;
import lightcore.world.cell.Cell;
import lightcore.world.cell.NormalCell;

public class WorldGeneratorBase extends WorldGenerator {

	public WorldGeneratorBase() {
		super();
		finishGeneration();
	}

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

		while (numberInstruction <= maximumInstructions) {
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

		grid.changeToLightable(currentCell.getX(), currentCell.getY());

		finishGeneration();
	}

	@Override
	protected int giveAction(int maxLight, int prevAction) {
		int randAction = rand.nextInt(probabilities.getRangeBase());

		while (randAction < probabilities.getProbaLight() && numberLight == (maxLight - 1)) {
			randAction = rand.nextInt(probabilities.getRangeBase());
		}

		while ((randAction >= probabilities.getProbaJump() && randAction < probabilities.getProbaLeft())
				&& (prevAction == 3 || prevAction == 4)) {
			randAction = rand.nextInt(probabilities.getRangeBase());
		}

		int action = -1;

		if (randAction < probabilities.getProbaLight()) {
			action = 0;
			numberLight++;

		} else if (randAction < probabilities.getProbaForward()) {
			action = 1;

		} else if (randAction < probabilities.getProbaJump()) {
			action = 2;

		} else if (randAction < probabilities.getProbaRight()) {
			action = 3;

		} else if (randAction < probabilities.getProbaLeft()) {
			action = 4;

		}

		return action;
	}

	@Override
	protected Cell updateGrid(Cell cell, int action) {

		numberInstruction++;
		switch (action) {
		case 0:
			grid.changeToLightable(cell.getX(), cell.getY());

			break;
		case 1:
			try {
				Cell emptyCell = grid.getNextCellGen(cell.getX(), cell.getY(), direction);

				if (emptyCell.isEmptyCell()) {
					grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), cell.getHeight()));
					cell = grid.getCell(emptyCell.getX(), emptyCell.getY());
				} else {

					if (cell.getHeight() <= (height + 1) && cell.getHeight() >= (height - 1)) {
						cell = emptyCell;
					} else {
						if (rand.nextInt(2) == 0) {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);

						} else {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);

						}
					}
				}
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
				Cell emptyCell = grid.getNextCellGen(cell.getX(), cell.getY(), direction);
				if (emptyCell.isEmptyCell()) {
					if (cell.getHeight() == 0) {
						grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), (cell.getHeight() + rand
								.nextInt(2))));
					} else if (height == 3) {
						grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), (cell.getHeight() + (rand
								.nextInt(2) - 1))));
					} else {
						grid.setCell(new NormalCell(emptyCell.getX(), emptyCell.getY(), (cell.getHeight() + (rand
								.nextInt(3) - 1))));
					}
					cell = grid.getCell(emptyCell.getX(), emptyCell.getY());
					height = cell.getHeight();
				} else {

					if (cell.getHeight() <= (height + 1) && cell.getHeight() >= (height - 1)) {
						cell = emptyCell;
					} else {
						if (rand.nextInt(2) == 0) {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.RIGHT);

						} else {
							direction = CardinalDirection.getRotationDirection(direction, RelativeDirection.LEFT);

						}
					}
				}
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
			numberInstruction--;
			break;
		}

		return cell;
	}
}