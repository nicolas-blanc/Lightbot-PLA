package lightbot.system.action;

import lightbot.LightCore;
import lightbot.graphics.Game;
import lightbot.system.CardinalDirection;
import lightbot.system.Colour;
import lightbot.system.Robot;
import lightbot.system.RelativeDirection;
import lightbot.system.world.Grid;

public class Turn extends _Action {

	RelativeDirection direction;

	public Turn() {
		super(Colour.WHITE);
	}

	public RelativeDirection getDirection() {
		return this.direction;
	}

	public Turn(RelativeDirection direction, Colour colour) {
		super(colour);
		this.direction = direction;
	}

	public void execute(Grid grid, Robot robot) {
		if (robot.getColour() != this.colour && this.colour != Colour.WHITE) {
			return;
		}

		if (robot.getDirection() == CardinalDirection.NORTH && direction == RelativeDirection.LEFT) {
			robot.setDirection(CardinalDirection.WEST);
			((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
			((Game) LightCore.display).display.anim
					.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
			return;
		}
		if (robot.getDirection() == CardinalDirection.NORTH && direction == RelativeDirection.RIGHT) {
			robot.setDirection(CardinalDirection.EAST);
			((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
			((Game) LightCore.display).display.anim
					.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
			return;
		}
		if (robot.getDirection() == CardinalDirection.SOUTH && direction == RelativeDirection.LEFT) {
			robot.setDirection(CardinalDirection.EAST);
			((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
			((Game) LightCore.display).display.anim
					.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
			return;
		}
		if (robot.getDirection() == CardinalDirection.SOUTH && direction == RelativeDirection.RIGHT) {
			robot.setDirection(CardinalDirection.WEST);
			((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
			((Game) LightCore.display).display.anim
					.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
			return;
		}
		if (robot.getDirection() == CardinalDirection.EAST && direction == RelativeDirection.LEFT) {
			robot.setDirection(CardinalDirection.NORTH);
			((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
			((Game) LightCore.display).display.anim
					.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
			return;
		}
		if (robot.getDirection() == CardinalDirection.EAST && direction == RelativeDirection.RIGHT) {
			robot.setDirection(CardinalDirection.SOUTH);
			((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
			((Game) LightCore.display).display.anim
					.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
			return;
		}
		if (robot.getDirection() == CardinalDirection.WEST && direction == RelativeDirection.LEFT) {
			robot.setDirection(CardinalDirection.SOUTH);
			((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
			((Game) LightCore.display).display.anim
					.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
			return;
		}
		if (robot.getDirection() == CardinalDirection.WEST && direction == RelativeDirection.RIGHT) {
			robot.setDirection(CardinalDirection.NORTH);
			((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
			((Game) LightCore.display).display.anim
					.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
			return;
		}

	}
}
