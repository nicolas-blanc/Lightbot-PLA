package lightbot.system;

import java.util.ArrayList;
import java.util.List;

import lightbot.system.action._Action;
import lightbot.system.world.Grid;
import lightbot.system.world.OutOfGridException;

public class Procedure extends _Executable {

	public static final String MAIN_NAME = "main";
	public static final String PROCEDURE1_NAME = "proc1";
	public static final String PROCEDURE2_NAME = "proc2";

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

	public Procedure(String name, int actionLimit, Colour colour) {
		super(colour);
		this.name = name;
		this.procSizeLimit = actionLimit;

		actions = new ArrayList<_Executable>(this.procSizeLimit);

	}

	/**
	 * @return the maximum number of actions that this procedure can hold
	 */
	public int getMaxNumOfActions() {
		return this.procSizeLimit;
	}

	public int getSize() {
		return this.actions.size();
	}

	public String getName() {
		return this.name;
	}

	public void setLimit(int limit) {
		this.procSizeLimit = limit;
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

		return true;
	}

	/**
	 * removes the action at position index
	 * 
	 * @param index
	 */
	public void removeActionAtIndex(int index) {
		if (index < 0 || index >= this.actions.size())
			throw new IllegalArgumentException();

		this.actions.remove(index);
	}

	/**
	 * Executes the next action in the procedure.
	 * 
	 * @throws OutOfGridException
	 */
	@Override
	public void execute(Grid grid, Robot robot) throws OutOfGridException {

		for (_Executable e : actions) {
			if (e instanceof _Action)
				e.execute(grid, robot);

			if (e instanceof Procedure)
				e.execute(grid, robot);

		}
	}

	public void reset() {
		this.actions.clear();
	}
}
