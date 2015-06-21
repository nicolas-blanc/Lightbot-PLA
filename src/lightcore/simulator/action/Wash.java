package lightcore.simulator.action;

import lightcore.LightCore;
import lightcore.graphics.Game;
import lightcore.simulator.Colour;
import lightcore.simulator._Executable;
import lightcore.world.Grid;
import lightcore.world.Robot;

public class Wash extends _Action {

	/*public Wash(Colour colour) {
		super(colour);
	}*/
	
	public Wash(){
		super(Colour.WHITE);
	}
	
	public void execute(Grid grid, Robot robot){
		if(robot.getColour() != Colour.WHITE){
			robot.setColour(Colour.WHITE);
			if(robot == Robot.wheatley){
				((Game)(LightCore.display)).display.robotDisplay.updateRobot(Robot.wheatley, 255);
				((Game)(LightCore.display)).display.anim.updateRobot(((Game)LightCore.display).display.robotDisplay.getSprite());
			}
			else{
				((Game)(LightCore.display)).display.cloneDisplay.updateRobot(Robot.wheatleyClone, 150);
				((Game)(LightCore.display)).display.anim.updateClone(((Game)LightCore.display).display.cloneDisplay.getSprite());
			}
			((Game)(LightCore.display)).display.anim.changeColor();
		}
	}

	@Override
	public _Executable cloneWithNewColor(_Executable e, Colour newColor) {
		return new Wash();
	}

	
}
