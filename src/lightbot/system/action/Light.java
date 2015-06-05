package lightbot.system.action;

import lightbot.system.Colour;
import lightbot.system.Robot;
import lightbot.system.world.Cell;
import lightbot.system.world.Grid;

public class Light implements _Action {
	
	// case allumable : verte
	// case allumée : jaunes
	public static void execute(Grid grid, Robot robot){
		
		if(canLight(robot, grid)){
			int posX = robot.getPositionX();
			int posY = robot.getPositionY();
			
			(grid.getCell(posX, posY)).setLight(true);
			(grid.getCell(posX, posY)).setLightable(false);
			(grid.getCell(posX, posY)).setColour(Colour.YELLOW);
		}
	}
	
	
	/**
	 * Checks if the robot can enlighten the current cell
	 * @param robot
	 * @param grid
	 * @return returns false if the robot can't move, true if it can
	 */
	private static boolean canLight(Robot robot, Grid grid){
		
		int currentX = robot.getPositionX();
		int currentY = robot.getPositionY();
		Cell currentCell = grid.getCell(currentX, currentY);
		
		if(currentCell.getLightable() == false || currentCell.getLightOn() == true){
			return false;
		}
		
		return true;
	}

}
