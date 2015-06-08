package lightbot.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import lightbot.system.world.Grid;

public class Procedure implements _Executable {

	/*
	 * Procedure name
	 */
	private String name;
	
	/*
	 * Maximum number of actions that can be stored in the procedure
	 */
	private int procSizeLimit;
	
	/*
	 * The list of actions stored in the procedure 
	 */
	private List<_Executable> actions;

	public Procedure(String name, int actionLimit) {
		this.name = name;
		this.procSizeLimit = actionLimit;

		actions = new ArrayList<_Executable>(this.procSizeLimit);
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return the maximum number of actions that this procdeure can hold
	 */
	public int getMaxNumOfActions() {
		return this.procSizeLimit;
	}

	/**
	 * 
	 * @param action
	 * @return true if the action was added to the procedure, false otherwise.
	 */
	public boolean addAction(_Executable action) {
		if (this.actions.size() == procSizeLimit)
			return false;

		this.actions.add(action);

		assert this.actions.size() <= this.procSizeLimit;

		return true;
	}

	/**
	 * removes the action at position index 
	 * @param index
	 */
	public void removeActionAtIndex(int index) {
		if (index < 0 || index >= this.actions.size())
			throw new IllegalArgumentException();

		this.actions.remove(index);
	}

	@Override
	public void execute(Grid grid, Robot robot) {
		for (_Executable _action : actions) {
			_action.execute(grid, robot);
		}
	}

}
