package lightcore.simulator.action;

import lightcore.LightCore;
import lightcore.graphics.Editor;
import lightcore.graphics.Game;
import lightcore.simulator.Colour;
import lightcore.simulator._Executable;
import lightcore.world.CardinalDirection;
import lightcore.world.Grid;
import lightcore.world.RelativeDirection;
import lightcore.world.Robot;
import lightcore.world.cell.Cell;
import lightcore.world.cell.TeleportCell;

public class Turn extends _Action {

	RelativeDirection direction;

	public Turn(RelativeDirection direction) {
		super(Colour.WHITE);
		this.direction = direction;
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
			if (LightCore.display instanceof Game) {
				if(robot == Robot.wheatley){
					((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
					((Game) LightCore.display).display.anim
						.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
				}
				else{
					((Game) LightCore.display).display.cloneDisplay.updateRobot(Robot.wheatleyClone, 150);
					((Game) LightCore.display).display.anim
						.updateClone(((Game) LightCore.display).display.cloneDisplay.robotSprite);
				}
			} else {
				((Editor) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
				((Editor) LightCore.display).display.anim
						.updateRobot(((Editor) LightCore.display).display.robotDisplay.robotSprite);
			}
			return;
		}
		if (robot.getDirection() == CardinalDirection.NORTH && direction == RelativeDirection.RIGHT) {
			robot.setDirection(CardinalDirection.EAST);
			if (LightCore.display instanceof Game) {
				if(robot == Robot.wheatley){
					((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
					((Game) LightCore.display).display.anim
						.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
				}
				else{
					((Game) LightCore.display).display.cloneDisplay.updateRobot(Robot.wheatleyClone, 150);
					((Game) LightCore.display).display.anim
						.updateClone(((Game) LightCore.display).display.cloneDisplay.robotSprite);
				}
			} else {
				((Editor) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
				((Editor) LightCore.display).display.anim
						.updateRobot(((Editor) LightCore.display).display.robotDisplay.robotSprite);
			}
			return;
		}
		if (robot.getDirection() == CardinalDirection.SOUTH && direction == RelativeDirection.LEFT) {
			robot.setDirection(CardinalDirection.EAST);
			if (LightCore.display instanceof Game) {
				if(robot == Robot.wheatley){
					((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
					((Game) LightCore.display).display.anim
						.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
				}
				else{
					((Game) LightCore.display).display.cloneDisplay.updateRobot(Robot.wheatleyClone, 150);
					((Game) LightCore.display).display.anim
						.updateClone(((Game) LightCore.display).display.cloneDisplay.robotSprite);
				}
			} else {
				((Editor) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
				((Editor) LightCore.display).display.anim
						.updateRobot(((Editor) LightCore.display).display.robotDisplay.robotSprite);
			}
			return;
		}
		if (robot.getDirection() == CardinalDirection.SOUTH && direction == RelativeDirection.RIGHT) {
			robot.setDirection(CardinalDirection.WEST);
			if (LightCore.display instanceof Game) {
				if(robot == Robot.wheatley){
					((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
					((Game) LightCore.display).display.anim
						.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
				}
				else{
					((Game) LightCore.display).display.cloneDisplay.updateRobot(Robot.wheatleyClone, 150);
					((Game) LightCore.display).display.anim
						.updateClone(((Game) LightCore.display).display.cloneDisplay.robotSprite);
				}
			} else {
				((Editor) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
				((Editor) LightCore.display).display.anim
						.updateRobot(((Editor) LightCore.display).display.robotDisplay.robotSprite);
			}
			return;
		}
		if (robot.getDirection() == CardinalDirection.EAST && direction == RelativeDirection.LEFT) {
			robot.setDirection(CardinalDirection.NORTH);
			if (LightCore.display instanceof Game) {
				if(robot == Robot.wheatley){
					((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
					((Game) LightCore.display).display.anim
						.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
				}
				else{
					((Game) LightCore.display).display.cloneDisplay.updateRobot(Robot.wheatleyClone, 150);
					((Game) LightCore.display).display.anim
						.updateClone(((Game) LightCore.display).display.cloneDisplay.robotSprite);
				}
			} else {
				((Editor) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
				((Editor) LightCore.display).display.anim
						.updateRobot(((Editor) LightCore.display).display.robotDisplay.robotSprite);
			}
			return;
		}
		if (robot.getDirection() == CardinalDirection.EAST && direction == RelativeDirection.RIGHT) {
			robot.setDirection(CardinalDirection.SOUTH);
			if (LightCore.display instanceof Game) {
				if(robot == Robot.wheatley){
					((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
					((Game) LightCore.display).display.anim
						.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
				}
				else{
					((Game) LightCore.display).display.cloneDisplay.updateRobot(Robot.wheatleyClone, 150);
					((Game) LightCore.display).display.anim
						.updateClone(((Game) LightCore.display).display.cloneDisplay.robotSprite);
				}
			} else {
				((Editor) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
				((Editor) LightCore.display).display.anim
						.updateRobot(((Editor) LightCore.display).display.robotDisplay.robotSprite);
			}
			return;
		}
		if (robot.getDirection() == CardinalDirection.WEST && direction == RelativeDirection.LEFT) {
			robot.setDirection(CardinalDirection.SOUTH);
			if (LightCore.display instanceof Game) {
				if(robot == Robot.wheatley){
					((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
					((Game) LightCore.display).display.anim
						.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
				}
				else{
					((Game) LightCore.display).display.cloneDisplay.updateRobot(Robot.wheatleyClone, 150);
					((Game) LightCore.display).display.anim
						.updateClone(((Game) LightCore.display).display.cloneDisplay.robotSprite);
				}
			} else {
				((Editor) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
				((Editor) LightCore.display).display.anim
						.updateRobot(((Editor) LightCore.display).display.robotDisplay.robotSprite);
			}
			return;
		}
		if (robot.getDirection() == CardinalDirection.WEST && direction == RelativeDirection.RIGHT) {
			robot.setDirection(CardinalDirection.NORTH);
			if (LightCore.display instanceof Game) {
				if(robot == Robot.wheatley){
					((Game) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
					((Game) LightCore.display).display.anim
						.updateRobot(((Game) LightCore.display).display.robotDisplay.robotSprite);
				}
				else{
					((Game) LightCore.display).display.cloneDisplay.updateRobot(Robot.wheatleyClone, 150);
					((Game) LightCore.display).display.anim
						.updateClone(((Game) LightCore.display).display.cloneDisplay.robotSprite);
				}
			} else {
				((Editor) LightCore.display).display.robotDisplay.updateRobot(Robot.wheatley, 255);
				((Editor) LightCore.display).display.anim
						.updateRobot(((Editor) LightCore.display).display.robotDisplay.robotSprite);
			}
			return;
		}
	}

	@Override
	public _Executable cloneWithNewColor(_Executable e, Colour newColor) {
		return new Turn(this.getDirection(), newColor);
	}
}
