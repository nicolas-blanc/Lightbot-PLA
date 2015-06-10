package lightbot.system.action;

import lightbot.system.CardinalDirection;
import lightbot.system.Robot;
import lightbot.system.RelativeDirection;
import lightbot.system.world.Grid;

public class Turn implements _Action {

	RelativeDirection direction;

	public Turn(RelativeDirection direction) {
		this.direction = direction;
	}

	public void execute(Grid grid, Robot robot) {
		if (robot.getDirection() == CardinalDirection.NORTH
				&& direction == RelativeDirection.LEFT) {
			robot.setDirection(CardinalDirection.WEST);
			return;
		}
		if (robot.getDirection() == CardinalDirection.NORTH
				&& direction == RelativeDirection.RIGHT) {
			robot.setDirection(CardinalDirection.EAST);
			return;
		}
		if (robot.getDirection() == CardinalDirection.SOUTH
				&& direction == RelativeDirection.LEFT) {
			robot.setDirection(CardinalDirection.EAST);
			return;
		}
		if (robot.getDirection() == CardinalDirection.SOUTH
				&& direction == RelativeDirection.RIGHT) {
			robot.setDirection(CardinalDirection.WEST);
			return;
		}
		if (robot.getDirection() == CardinalDirection.EAST
				&& direction == RelativeDirection.LEFT) {
			robot.setDirection(CardinalDirection.NORTH);
			return;
		}
		if (robot.getDirection() == CardinalDirection.EAST
				&& direction == RelativeDirection.RIGHT) {
			robot.setDirection(CardinalDirection.SOUTH);
			return;
		}
		if (robot.getDirection() == CardinalDirection.WEST
				&& direction == RelativeDirection.LEFT) {
			robot.setDirection(CardinalDirection.SOUTH);
			return;
		}
		if (robot.getDirection() == CardinalDirection.WEST
				&& direction == RelativeDirection.RIGHT) {
			robot.setDirection(CardinalDirection.NORTH);
			return;
		}

	}
}
