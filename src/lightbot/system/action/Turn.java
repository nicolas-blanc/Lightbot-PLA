package lightbot.system.action;

import lightbot.system.Direction;
import lightbot.system.Robot;
import lightbot.system._Action;
import lightbot.system.world.Grid;

public class Turn implements _Action {

	TurnDirection direction;

	public Turn(TurnDirection direction) {
		this.direction = direction;
	}

	public void execute(Grid grid, Robot robot) {
		if (robot.getDirection() == Direction.NORTH
				&& direction == TurnDirection.LEFT) {
			robot.setDirection(Direction.WEST);
			return;
		}
		if (robot.getDirection() == Direction.NORTH
				&& direction == TurnDirection.RIGHT) {
			robot.setDirection(Direction.EAST);
			return;
		}
		if (robot.getDirection() == Direction.SOUTH
				&& direction == TurnDirection.LEFT) {
			robot.setDirection(Direction.EAST);
			return;
		}
		if (robot.getDirection() == Direction.SOUTH
				&& direction == TurnDirection.RIGHT) {
			robot.setDirection(Direction.WEST);
			return;
		}
		if (robot.getDirection() == Direction.EAST
				&& direction == TurnDirection.LEFT) {
			robot.setDirection(Direction.NORTH);
			return;
		}
		if (robot.getDirection() == Direction.EAST
				&& direction == TurnDirection.RIGHT) {
			robot.setDirection(Direction.SOUTH);
			return;
		}
		if (robot.getDirection() == Direction.WEST
				&& direction == TurnDirection.LEFT) {
			robot.setDirection(Direction.SOUTH);
			return;
		}
		if (robot.getDirection() == Direction.WEST
				&& direction == TurnDirection.RIGHT) {
			robot.setDirection(Direction.NORTH);
			return;
		}

	}
}
