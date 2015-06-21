package lightcore.simulator;

import java.util.ArrayList;
import java.util.List;

import lightcore.simulator.action.Forward;
import lightcore.simulator.action.Jump;
import lightcore.simulator.action.Light;
import lightcore.simulator.action._Action;
import lightcore.world.Grid;
import lightcore.world.OutOfGridException;
import lightcore.world.Robot;

public class Procedure extends _Executable {

	public static final String MAIN_NAME = "main";
	public static final String PROCEDURE1_NAME = "proc1";
	public static final String PROCEDURE2_NAME = "proc2";

	private int currentAction;

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
		this.currentAction = 0;

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
		System.out.println("This procedure's colour: " + this.getColour() + ", robot's colour: " + robot.getColour());
		System.out.println(this.toString());
		System.out.println(this.actions.size());

		for (int i = 0; i < this.actions.size(); i++) {
			System.out.println(this.actions.get(0).getClass());
			if (this.actions.get(i) instanceof _Action) {
				System.out.println("found an action");
				System.out.println(this.actions.get(i).getClass());
				this.actions.get(i).execute(grid, robot);
			}

			if (this.actions.get(i) instanceof Procedure) {
				System.out.println("Found a procedure");
				if (this.actions.get(i).getColour() == robot.getColour()) {
					System.out.println("same color");
					this.actions.get(i).execute(grid, robot);
				}
			}
		}
	}

	public void executeOne(Grid grid, Robot robot) throws OutOfGridException {
		if (actions.get(currentAction) instanceof _Action) {
			actions.get(currentAction).execute(grid, robot);
			currentAction++;
		} else if (actions.get(currentAction) instanceof Procedure)
			((Procedure) actions.get(currentAction)).executeOne(grid, robot);
	}

	public void reset() {
		this.actions.clear();
	}

	/**
	 * @return the actions
	 */
	public _Executable getAction(int index) {
		return actions.get(index);
	}

	@Override
	public _Executable cloneWithNewColor(_Executable e, Colour newColor) {
		Procedure p = null;
		if (e instanceof Procedure) {
			switch (((Procedure) e).getName()) {
			case MAIN_NAME:
				p = new Procedure(MAIN_NAME, procSizeLimit, newColor);
				for (int i = 0; i < actions.size(); i++) {
					p.addAction(cloneWithNewColor(actions.get(i), newColor));
				}
				return p;
			case PROCEDURE1_NAME:
				p = new Procedure(PROCEDURE1_NAME, procSizeLimit, newColor);
				for (int i = 0; i < actions.size(); i++) {
					p.addAction(cloneWithNewColor(actions.get(i), newColor));
				}

				return p;
			case PROCEDURE2_NAME:
				p = new Procedure(PROCEDURE1_NAME, procSizeLimit, newColor);
				for (int i = 0; i < actions.size(); i++) {
					p.addAction(cloneWithNewColor(actions.get(i), newColor));
				}
				return p;
			}
		}
		if (e instanceof _Action) {
			if (e instanceof Forward)
				return new Forward(newColor);
			if (e instanceof Jump)
				return new Jump();
			if (e instanceof Light)
				return new Light();
		}
		return p;

	}

	public String toString() {
		return this.name;
	}
}
