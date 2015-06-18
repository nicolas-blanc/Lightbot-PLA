package lightbot.system.action;

import lightbot.system.Colour;
import lightbot.system.Robot;
import lightbot.system.world.Grid;
import lightbot.system.world.OutOfGridException;

public class Clone extends _Action{

	public Clone() {
		super(Colour.WHITE);
	}
	
	public Clone(Colour c){
		super(c);
	}
	
	/**
	 * 
	 * @param l : line position
	 * @param c : column position
	 */
	public void setClone(int l, int c){
		Robot.wheatleyClone.setPosition(l, c);
	}
	
	public void killClone(){
		Robot.wheatleyClone.setVisibility(false);
	}

	@Override
	public void execute(Grid grid, Robot robot) throws OutOfGridException {
		// TODO Auto-generated method stub
		
	}
	
	
}
