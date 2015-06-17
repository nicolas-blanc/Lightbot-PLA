package lightbot.system.action;

import lightbot.LightCore;
import lightbot.graphics.Game;
import lightbot.system.CardinalDirection;
import lightbot.system.Colour;
import lightbot.system.Robot;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.cell.ColoredCell;
import lightbot.system.world.cell.FullCell;
import lightbot.system.world.cell.ObstacleCell;
import lightbot.system.world.cell.TeleportCell;
import lightbot.system.world.Grid;
import lightbot.system.world.OutOfGridException;

public class Forward extends _Action {

	public Forward() {
		super(Colour.WHITE);
	}

	public Forward(Colour colour) {
		super(colour);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Calculates the new position of the robot if it can go forwards
	 * 
	 * @param grid
	 * @param robot
	 * @throws OutOfGridException 
	 */
	public void execute(Grid grid, Robot robot) throws OutOfGridException {
		if (robot.getColour() != this.colour && this.colour != Colour.WHITE) {
			return;
		}

		try {

			if (canMove(robot, grid)) {

				int posX = robot.getLine();
				int posY = robot.getColumn();

				if (robot.getDirection() == CardinalDirection.NORTH) {
					//((Game)LightCore.display).display.anim.moveRobot(CardinalDirection.NORTH, 0);
					robot.setLine(posX - 1);
					takeColour(robot, grid);
					teleport(robot, grid);
				}
				if (robot.getDirection() == CardinalDirection.SOUTH) {
					//((Game)LightCore.display).display.anim.moveRobot(CardinalDirection.SOUTH, 0);
					robot.setLine(posX + 1);
					takeColour(robot, grid);
					teleport(robot, grid);
				}
				if (robot.getDirection() == CardinalDirection.WEST) {
					//((Game)LightCore.display).display.anim.moveRobot(CardinalDirection.WEST, 0);
					robot.setColumn(posY - 1);
					takeColour(robot, grid);
					teleport(robot, grid);
				}
				if (robot.getDirection() == CardinalDirection.EAST) {
					//((Game)LightCore.display).display.anim.moveRobot(CardinalDirection.EAST, 0);
					robot.setColumn(posY + 1);
					takeColour(robot, grid);
					teleport(robot, grid);
				}
			}
		} catch (OutOfGridException oge) {
			throw new OutOfGridException();
		}

	}

	/**
	 * Checks if the current cell has the same level as the cell we want to go
	 * to (nextCell), depending on the robot's direction
	 * 
	 * @param robot
	 * @param grid
	 * @param direction
	 * @return returns false if the robot can't move, true if it can
	 */
	private boolean canMove(Robot robot, Grid grid) throws OutOfGridException {

		int currentX = robot.getLine();
		int currentY = robot.getColumn();
		Cell currentCell = grid.getCell(currentX, currentY);
		Cell nextCell;

		try {
			nextCell = grid.getNextCell(currentX, currentY, robot.getDirection());
		} catch (OutOfGridException e) {
			throw new OutOfGridException();
		}

		if (currentCell instanceof FullCell && nextCell instanceof FullCell && !(nextCell instanceof ObstacleCell)) {
			return (currentCell.getHeight() == nextCell.getHeight());
		} else {
			return false;
		}
	}

	/**
	 * the robot takes the color if it is a ColoredCell
	 * 
	 * @param robot
	 * @param grid
	 */
	private void takeColour(Robot robot, Grid grid) {
		int posX, posY;
		Cell cell;

		posX = robot.getLine();
		posY = robot.getColumn();
		cell = grid.getCell(posX, posY);

		if (cell instanceof ColoredCell) {
			robot.setColour(((ColoredCell) cell).getColour());
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
			robot.setPosition(((TeleportCell) cell).getDestX(), ((TeleportCell) cell).getDestY());
			grid.changeToNormal(line, column);
			grid.changeToNormal(((TeleportCell) cell).getDestX(), ((TeleportCell) cell).getDestY());
		}
	}
}
