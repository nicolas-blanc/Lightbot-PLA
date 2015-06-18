package lightbot.system.action;

import lightbot.LightCore;
import lightbot.graphics.Game;
import lightbot.system.CardinalDirection;
import lightbot.system.Colour;
import lightbot.system.Robot;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.cell.ColoredCell;
import lightbot.system.world.cell.ObstacleCell;
import lightbot.system.world.cell.TeleportCell;
import lightbot.system.world.Grid;
import lightbot.system.world.OutOfGridException;

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

		if (canJump(robot, grid)) {
			int posX = robot.getLine();
			int posY = robot.getColumn();

			if (robot.getDirection() == CardinalDirection.NORTH) {
				robot.setLine(posX - 1);
				takeColour(robot, grid);
				teleport(robot, grid);
				((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.NORTH, 2);
				((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.NORTH, 0);
			}
			if (robot.getDirection() == CardinalDirection.EAST) {
				robot.setColumn(posY + 1);
				takeColour(robot, grid);
				teleport(robot, grid);
				((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.EAST, 2);
				((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.EAST, 0);
			}
			if (robot.getDirection() == CardinalDirection.SOUTH) {
				robot.setLine(posX + 1);
				takeColour(robot, grid);
				teleport(robot, grid);
				((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.SOUTH, 2);
				((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.SOUTH, 0);
			}
			if (robot.getDirection() == CardinalDirection.WEST) {
				robot.setColumn(posY - 1);
				takeColour(robot, grid);
				teleport(robot, grid);
				((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.WEST, 2);
				((Game) LightCore.display).display.anim.moveRobot(CardinalDirection.WEST, 0);
			}
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
		return (nextCell.getHeight() != 0
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
			robot.setPosition(((TeleportCell) cell).getDestX(), ((TeleportCell) cell).getDestY());
			grid.changeToNormal(line, column);
			grid.changeToNormal(((TeleportCell) cell).getDestX(), ((TeleportCell) cell).getDestY());
		}
	}

}
