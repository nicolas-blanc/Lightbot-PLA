package lightcore.simulator.action;

import lightcore.LightCore;
import lightcore.graphics.Game;
import lightcore.simulator.Colour;
import lightcore.simulator._Executable;
import lightcore.world.CardinalDirection;
import lightcore.world.Grid;
import lightcore.world.OutOfGridException;
import lightcore.world.Robot;
import lightcore.world.cell.Cell;
import lightcore.world.cell.ColoredCell;
import lightcore.world.cell.EmptyCell;
import lightcore.world.cell.ObstacleCell;
import lightcore.world.cell.TeleportCell;

public class Jump extends _Action {

	public Jump() {
		super(Colour.WHITE);
	}

	public Jump(Colour colour) {
		super(colour);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Calculates the new position of the robot if it can go jump
	 * 
	 * @param grid
	 * @param robot
	 */
	public void execute(Grid grid, Robot robot) {
		if (robot.getColour() != this.colour && this.colour != Colour.WHITE) {
			return;
		}
		
		boolean isClone = robot == Robot.wheatleyClone;

		if (canJump(robot, grid)) {
			int posX = robot.getLine();
			int posY = robot.getColumn();

			if (robot.getDirection() == CardinalDirection.NORTH) {
				try {
					if (grid.getNextCell(posX, posY, CardinalDirection.NORTH).getHeight() > grid.getCell(posX, posY)
							.getHeight()) {
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.NORTH, 2, isClone);
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.NORTH, 0, isClone);
					} else {
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.NORTH, 0, isClone);
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.NORTH, 1, isClone);
					}
				} catch (OutOfGridException oge) {
					((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.NORTH, 2, isClone);
					((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.NORTH, 1, isClone);
				}
				robot.setLine(posX - 1);
				takeColour(robot, grid);
				teleport(robot, grid);

			}
			if (robot.getDirection() == CardinalDirection.EAST) {
				try {
					if (grid.getNextCell(posX, posY, CardinalDirection.EAST).getHeight() > grid.getCell(posX, posY)
							.getHeight()) {
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.EAST, 2, isClone);
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.EAST, 0, isClone);
					} else {
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.EAST, 0, isClone);
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.EAST, 1, isClone);
					}
				} catch (OutOfGridException oge) {
					((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.EAST, 2, isClone);
					((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.EAST, 1, isClone);
				}
				robot.setColumn(posY + 1);
				takeColour(robot, grid);
				teleport(robot, grid);

			}
			if (robot.getDirection() == CardinalDirection.SOUTH) {
				try {
					if (grid.getNextCell(posX, posY, CardinalDirection.SOUTH).getHeight() > grid.getCell(posX, posY)
							.getHeight()) {

						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.SOUTH, 2, isClone);
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.SOUTH, 0, isClone);
					} else {
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.SOUTH, 0, isClone);
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.SOUTH, 1, isClone);
					}
				} catch (OutOfGridException oge) {
					((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.SOUTH, 2, isClone);
					((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.SOUTH, 1, isClone);
				}
				robot.setLine(posX + 1);
				takeColour(robot, grid);
				teleport(robot, grid);

			}
			if (robot.getDirection() == CardinalDirection.WEST) {
				try {
					if (grid.getNextCell(posX, posY, CardinalDirection.WEST).getHeight() > grid.getCell(posX, posY)
							.getHeight()) {
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.WEST, 2, isClone);
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.WEST, 0, isClone);
					} else {
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.WEST, 0, isClone);
						((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.WEST, 1, isClone);
					}
				} catch (OutOfGridException oge) {
					((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.WEST, 2, isClone);
					((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.WEST, 1, isClone);
				}
				robot.setColumn(posY - 1);
				takeColour(robot, grid);
				teleport(robot, grid);

			}
		} else {
			((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.WEST, 2, isClone);
			((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.WEST, 1, isClone);
		}

	}

	/**
	 * Checks if the current cell has the different level (more or less one) as
	 * the cell we want to go to (nextCell), depending on the robot's direction
	 * 
	 * @param robot
	 * @param grid
	 * @return false if the robot can't jump, true if it can
	 */
	private boolean canJump(Robot robot, Grid grid) {
		int currentX = robot.getLine();
		int currentY = robot.getColumn();
		CardinalDirection direction = robot.getDirection();
		Cell currentCell = grid.getCell(currentX, currentY);
		Cell nextCell;

		try {
			nextCell = grid.getNextCell(currentX, currentY, direction);
		} catch (OutOfGridException e) {
			return false;
		}
		return (!(nextCell instanceof EmptyCell)
				&& (currentCell.getHeight() == nextCell.getHeight() - 1 || currentCell.getHeight() == nextCell
						.getHeight() + 1) && !(nextCell instanceof ObstacleCell));
	}

	/**
	 * the robot takes the color if it is a ColoredCell
	 * 
	 * @param robot
	 * @param grid
	 */
	private void takeColour(Robot robot, Grid grid) {
		int posX, posY;
		Cell Cell;

		posX = robot.getLine();
		posY = robot.getColumn();
		Cell = grid.getCell(posX, posY);

		if (Cell instanceof ColoredCell) {
			robot.setColour(((ColoredCell) Cell).getColour());
		}
	}
	
	/**
	 * Teleports the robot from its current cell to the destination if it's an
	 * instance of TeleportCell
	 * 
	 * @param robot
	 * @param grid
	 */
	private void teleport(Robot robot, Grid grid) {
		int line, column;
		Cell cell;

		line = robot.getLine();
		column = robot.getColumn();
		cell = grid.getCell(line, column);

		if (cell instanceof TeleportCell) {
			TeleportCell arrivalCell= (TeleportCell)grid.getCell(((TeleportCell) cell).getDestX(), ((TeleportCell) cell).getDestY());
			
			robot.setPosition(arrivalCell.getX(), arrivalCell.getY());
			
			if(robot == Robot.wheatley){
				((Game) LightCore.display).display.robotDisplay.updateRobot(robot, 255);
				((Game)LightCore.display).display.anim.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
			}
			else{
				((Game) LightCore.display).display.cloneDisplay.updateRobot(robot, 150);
				((Game)LightCore.display).display.anim.updateClone(((Game) LightCore.display).display.cloneDisplay.robotSprite);
			}
			
			((Game)LightCore.display).display.anim.animeBlackHole(cell.getX(), cell.getY(), ((TeleportCell) cell).getHeight(), false);
			grid.changeToNormal(line, column);
			((Game)LightCore.display).display.gridDisplay.addCube(grid.getCell(line, column));
			((Game)LightCore.display).display.anim.updateSprite(((Game)LightCore.display).display.gridDisplay.getGridSprites());
			
			((Game)LightCore.display).display.anim.animeBlackHole(arrivalCell.getX(), arrivalCell.getY(), arrivalCell.getHeight(), false);
			grid.changeToNormal(arrivalCell.getX(), arrivalCell.getY());
			((Game)LightCore.display).display.gridDisplay.addCube(grid.getCell(arrivalCell.getX(), arrivalCell.getY()));
			((Game)LightCore.display).display.anim.updateSprite(((Game)LightCore.display).display.gridDisplay.getGridSprites());
		}
	}

	@Override
	public _Executable cloneWithNewColor(_Executable e, Colour newColor) {
		return new Jump(newColor);
	}

}
