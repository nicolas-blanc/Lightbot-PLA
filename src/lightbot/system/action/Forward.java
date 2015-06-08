package lightbot.system.action;

import lightbot.system.Direction;
import lightbot.system.Robot;
import lightbot.system._Action;
import lightbot.system.world.Cell;
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
			
			int posX = robot.getPositionX();
			int posY = robot.getPositionY();
			
			if(robot.getDirection() == Direction.NORTH){
				robot.setPositionX(posX-1);
			}
			if(robot.getDirection() == Direction.SOUTH){
				robot.setPositionX(posX+1);
			}
			if(robot.getDirection() == Direction.WEST){
				robot.setPositionY(posY-1);
			}
			if(robot.getDirection() == Direction.EAST){
				robot.setPositionY(posY+1);
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
		
		int currentX = robot.getPositionX();
		int currentY = robot.getPositionY();
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