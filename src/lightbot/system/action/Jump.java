package lightbot.system.action;

import lightbot.system.CardinalDirection;
import lightbot.system.Robot;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.Grid;
import lightbot.system.world.OutOfGridException;

public class Jump implements _Action {
	
	
	/**
	 * Calculates the new position of the robot if it can go jump
	 * @param grid
	 * @param robot
	 */
	public void execute(Grid grid, Robot robot){
		if(canJump(robot, grid)){
			int posX = robot.getPositionX();
			int posY = robot.getPositionY();
			
			if(robot.getDirection() == CardinalDirection.NORTH){
				robot.setPositionX(posX - 1);
			}
			if(robot.getDirection() == CardinalDirection.EAST){
				robot.setPositionY(posY + 1);
			}
			if(robot.getDirection() == CardinalDirection.SOUTH){
				robot.setPositionX(posX + 1);
			}
			if(robot.getDirection() == CardinalDirection.WEST){
				robot.setPositionY(posY - 1);
			}
		}
		
	}
	
	/**
	 * Checks if the current cell has the different level (more or less one) as the cell we want to go to (nextCell), depending on the robot's direction
	 * @param robot
	 * @param grid
	 * @return false if the robot can't jump, true if it can
	 */
	private boolean canJump(Robot robot, Grid grid){
		int currentX = robot.getPositionX();
		int currentY = robot.getPositionY();
		CardinalDirection direction = robot.getDirection();
		Cell currentCell = grid.getCell(currentX, currentY);
		Cell nextCell;
		
        try {
            nextCell = grid.getNextCell(currentX, currentY, direction);
    } catch (OutOfGridException e) {
            return false;
    }
		return (nextCell.getHeight() !=0 || currentCell.getHeight() == nextCell.getHeight()-1 || currentCell.getHeight() == nextCell.getHeight()+1);
	}

}
