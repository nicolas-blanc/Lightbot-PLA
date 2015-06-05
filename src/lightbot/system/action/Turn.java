package lightbot.system.action;

import lightbot.system.Direction;
import lightbot.system.Robot;

public class Turn implements _Action {
	
	/**
	 * Calculates the new direction of the robot if it turn
	 * @param robot
	 * @param turn : direction he turns
	 */
	public static void execute(Robot robot, TurnDirection turn){
		if(robot.getDirection() == Direction.NORTH && turn == TurnDirection.LEFT){
			robot.setDirection(Direction.WEST);
			return;
		}		
		if(robot.getDirection() == Direction.NORTH && turn == TurnDirection.RIGHT){
			robot.setDirection(Direction.EAST);
			return;
		}
		if(robot.getDirection() == Direction.SOUTH && turn == TurnDirection.LEFT){
			robot.setDirection(Direction.EAST);
			return;
		}
		if(robot.getDirection() == Direction.SOUTH && turn == TurnDirection.RIGHT){
			robot.setDirection(Direction.WEST);
			return;
		}
		if(robot.getDirection() == Direction.EAST && turn == TurnDirection.LEFT){
			robot.setDirection(Direction.NORTH);
			return;
		}
		if(robot.getDirection() == Direction.EAST && turn == TurnDirection.RIGHT){
			robot.setDirection(Direction.SOUTH);
			return;
		}
		if(robot.getDirection() == Direction.WEST && turn == TurnDirection.LEFT){
			robot.setDirection(Direction.SOUTH);
			return;
		}
		if(robot.getDirection() == Direction.WEST && turn == TurnDirection.RIGHT){
			robot.setDirection(Direction.NORTH);
			return;
		}
		
		
	}
}
