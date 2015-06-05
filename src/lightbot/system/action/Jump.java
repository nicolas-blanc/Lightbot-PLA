package lightbot.system.action;

import lightbot.system.Direction;
import lightbot.system.Robot;
import lightbot.system.world.Cell;
import lightbot.system.world.Grid;
import lightbot.system.world.OutOfGridException;

public class Jump implements _Action {
	
	
	/**
	 * Calculates the new position of the robot if it can go jump
	 * @param robot
	 * @param grid
	 */
	public static void execute(Robot robot, Grid grid){
		if(canJump(robot, grid)){
			int posX = robot.getPositionX();
			int posY = robot.getPositionY();
			
			if(robot.getDirection() == Direction.NORTH){
				robot.setPositionX(posX - 1);
			}
			if(robot.getDirection() == Direction.EAST){
				robot.setPositionY(posY + 1);
			}
			if(robot.getDirection() == Direction.SOUTH){
				robot.setPositionX(posX + 1);
			}
			if(robot.getDirection() == Direction.WEST){
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
	private static boolean canJump(Robot robot, Grid grid){
		int currentX = robot.getPositionX();
		int currentY = robot.getPositionY();
		Direction direction = robot.getDirection();
		Cell currentCell = grid.getCell(currentX, currentY);
		Cell nextCell;
		
        try {
            nextCell = grid.getNextCell(currentX, currentY, direction);
    } catch (OutOfGridException e) {
            return false;
    }
		return (currentCell.getHeight() == nextCell.getHeight()-1 || currentCell.getHeight() == nextCell.getHeight()-1);
	}
}
