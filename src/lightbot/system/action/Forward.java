package lightbot.system.action;

import lightbot.system.CardinalDirection;
import lightbot.system.Robot;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.Grid;
import lightbot.system.world.OutOfGridException;

public class Forward implements _Action {
	
	/**
	 * Calculates the new position of the robot if it can go forwards
	 * @param grid
	 * @param robot
	 */
	public void execute(Grid grid, Robot robot){
		
		if(canMove(robot, grid)){
			
			int posX = robot.getLine();
			int posY = robot.getColumn();
			
			if(robot.getDirection() == CardinalDirection.NORTH){
				robot.setLine(posX-1);
			}
			if(robot.getDirection() == CardinalDirection.SOUTH){
				robot.setLine(posX+1);
			}
			if(robot.getDirection() == CardinalDirection.WEST){
				robot.setColumn(posY-1);
			}
			if(robot.getDirection() == CardinalDirection.EAST){
				robot.setColumn(posY+1);
			}
		}
	}
	
	/**
	 * Checks if the current cell has the same level as the cell we want to go to (nextCell),
	 * depending on the robot's direction
	 * @param robot
	 * @param grid
	 * @param direction
	 * @return returns false if the robot can't move, true if it can
	 */
	private boolean canMove(Robot robot, Grid grid){
		
		int currentX = robot.getLine();
		int currentY = robot.getColumn();
		Cell currentCell = grid.getCell(currentX, currentY);
		Cell nextCell;
		
		try {
			nextCell = grid.getNextCell(currentX, currentY, robot.getDirection());
		} catch (OutOfGridException e) {
			return false;
		}
		
		return (currentCell.getHeight() == nextCell.getHeight());
	}



}