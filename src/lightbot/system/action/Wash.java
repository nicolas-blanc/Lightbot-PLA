package lightbot.system.action;

import lightbot.system.Colour;
import lightbot.system.Robot;
import lightbot.system.world.Grid;

public class Wash extends _Action {

	public Wash(Colour colour) {
		super(colour);
	}
	
	public Wash(){
		super(Colour.WHITE);
	}
	
	public void execute(Grid grid, Robot robot){
		robot.setColour(Colour.WHITE);
	}

	
}
