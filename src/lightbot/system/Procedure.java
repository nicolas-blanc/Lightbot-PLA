package lightbot.system;

import java.util.ArrayList;
import java.util.List;

import lightbot.system.action._Action;
import lightbot.system.world.Grid;

public class Procedure extends _Executable {

	
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

	/*
	 * The caller of this procedure. if main procedure, caller == null
	 */
	private Procedure caller;

	private int nextInLine;

	public Procedure(String name, int actionLimit, Colour colour) {
		super(colour);
		this.name = name;
		this.procSizeLimit = actionLimit;

		actions = new ArrayList<_Executable>(this.procSizeLimit);

		this.caller = null;

		this.nextInLine = 0;
	}

	/**
	 * @return the maximum number of actions that this procedure can hold
	 */
	public int getMaxNumOfActions() {
		return this.procSizeLimit;
	}
	
	public Procedure getCaller() {
		return this.caller;
	}

	/**
	 * 
	 * @param action
	 * @return true if the action was added to the procedure, false otherwise.
	 */
	public boolean addAction(_Executable action) {
		if (this.actions.size() == procSizeLimit)
			return false;

		if (action instanceof Procedure)
			((Procedure) action).caller = this;

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
	 */
	@Override
	public void execute(Grid grid, Robot robot) {
		_Executable e = this.actions.get(this.nextInLine);
		
	}

	public _Executable Next() {
		int oldNextInLine = this.nextInLine;
		this.nextInLine += 1;
		
		_Executable next = this.actions.get(oldNextInLine);
		
		if(next instanceof Procedure)
			return ((Procedure) next).Next();
		
		if(next instanceof _Action) {
			
		}
	}

	public boolean End() {
		return this.nextInLine == this.actions.size();
	}

}
