package lightbot.system.action;

import lightbot.system.Colour;
import lightbot.system.Robot;
import lightbot.system._Executable;
import lightbot.system.world.Grid;
import lightbot.system.world.OutOfGridException;

public class Break extends _Action{

	public Break() {
		super(Colour.WHITE);
	}
	
	public Break(Colour colour) {
		super(colour);
	}

	@Override
	public void execute(Grid grid, Robot robot) throws OutOfGridException {
		
	}

	@Override
	public _Executable cloneWithNewColor(_Executable e, Colour newColor) {
		return new Break();
	}
	
}
