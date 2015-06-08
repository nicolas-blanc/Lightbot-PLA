package lightbot.system;

import lightbot.system.world.Grid;

public interface _Executable {
	/**
	 * executes a process given a grid and a robot
	 * @param grid
	 * @param robot
	 */
	public void execute(Grid grid, Robot robot);

}
