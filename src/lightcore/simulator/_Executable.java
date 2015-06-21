package lightcore.simulator;


import lightcore.world.Grid;
import lightcore.world.OutOfGridException;
import lightcore.world.Robot;

public abstract class _Executable {

	protected Colour colour;

	public _Executable(Colour colour) {
		this.colour = colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	public Colour getColour() {
		return this.colour;
	}
	
	public abstract _Executable cloneWithNewColor(_Executable e, Colour newColor);

	/**
	 * executes a process given a grid and a robot
	 * 
	 * @param grid
	 * @param robot
	 * @throws OutOfGridException 
	 */
	public abstract void execute(Grid grid, Robot robot) throws OutOfGridException;

}
