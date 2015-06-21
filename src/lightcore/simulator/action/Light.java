package lightcore.simulator.action;

import lightcore.LightCore;
import lightcore.graphics.Game;
import lightcore.simulator.Colour;
import lightcore.simulator._Executable;
import lightcore.world.CardinalDirection;
import lightcore.world.Grid;
import lightcore.world.Robot;
import lightcore.world.cell.Cell;
import lightcore.world.cell.LightableCell;

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
			
			if(grid.getCell(posX, posY).isLightON()){
				(grid.getCell(posX, posY)).setLight(false);
			} else {
				(grid.getCell(posX, posY)).setLight(true);
			}
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
		/*if ((currentCell instanceof LightableCell) && (currentCell.isLightON())) {
			return false;
		}*/

		return true;
	}

	@Override
	public _Executable cloneWithNewColor(_Executable e, Colour newColor) {
		return new Light();
	}

}
