package lightbot.system.action;

import lightbot.LightCore;
import lightbot.graphics.Game;
import lightbot.system.CardinalDirection;
import lightbot.system.Colour;
import lightbot.system.Robot;
import lightbot.system._Executable;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.cell.LightableCell;
import lightbot.system.world.Grid;

public class Light extends _Action {

	// lightable cell (cell that can be enlightened) : green
	// enlightened cell : yellow

	public Light() {
		super(Colour.WHITE);
	}

	public Light(Colour colour) {
		super(colour);
		// TODO Auto-generated constructor stub
	}

	/**
	 * execute : switches on the light and the colour of the cell to YELLOW if
	 * the robot can enlighten the cell. Changes the lightable attribut from
	 * true to false (can't switch on an enlightened cell)
	 * 
	 * @param grid
	 * @param robot
	 */
	public void execute(Grid grid, Robot robot) {
		if (robot.getColour() != this.colour && this.colour != Colour.WHITE) {
			return;
		}

		if (canLight(robot, grid)) {
			int posX = robot.getLine();
			int posY = robot.getColumn();

			(grid.getCell(posX, posY)).setLight(true);
			((Game) LightCore.display).display.gridDisplay.addCube(grid.getCell(posX, posY));
			((Game) LightCore.display).display.anim.updateSprite(((Game) LightCore.display).display.gridDisplay
					.getGridSprites());
		}
	}

	/**
	 * Checks if the robot can enlighten the current cell
	 * 
	 * @param robot
	 * @param grid
	 * @return returns false if the robot can't switch on the cell, true if it
	 *         can
	 */
	private boolean canLight(Robot robot, Grid grid) {

		int currentX = robot.getLine();
		int currentY = robot.getColumn();
		Cell currentCell = grid.getCell(currentX, currentY);

		if (!(currentCell instanceof LightableCell)) {
			return false;
		}
		if ((currentCell instanceof LightableCell) && (currentCell.isLightON())) {
			return false;
		}

		return true;
	}

	@Override
	public _Executable cloneWithNewColor(_Executable e, Colour newColor) {
		return new Light();
	}

}
