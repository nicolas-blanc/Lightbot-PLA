package lightcore.simulator.action;

import lightcore.simulator.Colour;
import lightcore.simulator._Executable;
import lightcore.world.Grid;
import lightcore.world.OutOfGridException;
import lightcore.world.Robot;

public class Break extends _Action {

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
		return new Break(newColor);
	}

}
