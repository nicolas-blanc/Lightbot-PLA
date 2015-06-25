package lightcore.simulator;

import java.util.EmptyStackException;
import java.util.Stack;

import lightcore.simulator.action.Break;
import lightcore.simulator.action.Clone;
import lightcore.simulator.action._Action;
import lightcore.world.OutOfGridException;
import lightcore.world.Robot;

public class Scheduler {
	final private Procedure procMain;
	final private Procedure procP1;
	final private Procedure procP2;

	private int currentRobot;
	private Robot robot;
	private int numberOfRobots;

	private Level level;

	private Stack<_Executable> executionMain;
	private Stack<_Executable> executionClone;

	/**
	 * @param procMain
	 *            The main procedure executed by the first robot
	 * @param procP1
	 *            The first procedure that belongs to both robots
	 * @param procP2
	 *            The second procedure executed by the second robot
	 * @param grid
	 *            A grid of cells
	 */
	public Scheduler(Procedure procMain, Procedure procP1, Procedure procP2, Level level) {
		super();
		this.procMain = procMain;
		this.procP1 = procP1;
		this.procP2 = procP2;
		this.level = level;

		numberOfRobots = 1;

		executionMain = new Stack<_Executable>();
		executionClone = new Stack<_Executable>();
	}

	/**
	 * Execute the action of Robots in parallel
	 * 
	 * @throws LevelEndException
	 *             Exception if the level is finished
	 */
	public void execute() throws LevelEndException, OutOfGridException {
		boolean notEnd = true;

		_Action action;

		robot = Robot.wheatley;
		currentRobot = 0;

		for (int i = procMain.getSize() - 1; i >= 0; i--) {
			executionMain.push(procMain.getAction(i));
		}

		for (int i = procP2.getSize() - 1; i >= 0; i--) {
			executionClone.push(procP2.getAction(i));
		}

		while (notEnd && !level.isCompleted()) {
			try {
				action = nextAction();
			} catch (EmptyStackException e) {
				action = null;
				notEnd = false;
			}

			if (notEnd) {
				try {
					if (robot == Robot.wheatley) {
						System.out.println("Action effectuer : " + action.toString() + " // Robot : weathley -> "
								+ currentRobot + " // number of robot : " + numberOfRobots);
					} else {
						System.out.println("Action effectuer : " + action.toString() + " // Robot : weathleyClone -> "
								+ currentRobot + " // number of robot : " + numberOfRobots);
					}
					action.execute(level.getGrid(), robot);
					if (level.isCompleted()) {
						System.out.println("finished!!!");
						throw new LevelEndException();
					}
				} catch (OutOfGridException ge) {

					throw new OutOfGridException();
				}
				robot = giveNextRobot();
			}
		}
	}

	/**
	 * Return the next robot that must execute
	 * 
	 * @return the next Robot
	 */
	private Robot giveNextRobot() {
		Robot temp;
		if (numberOfRobots == 2) {
			if (currentRobot == 1) {
				temp = Robot.wheatley;
			} else {
				temp = Robot.wheatleyClone;
			}
			currentRobot = ++currentRobot % numberOfRobots;
		} else {
			temp = Robot.wheatley;
		}

		return temp;
	}

	/**
	 * Returns the next action to be executed
	 * 
	 * @return The next action
	 */
	private _Action nextAction() {
		_Executable action;
		try {
			if (currentRobot == 0) {
				action = executionMain.pop();
			} else {
				action = executionClone.pop();
			}
		} catch (EmptyStackException e) {
			robot = giveNextRobot();
			if (currentRobot == 0) {
				action = executionMain.pop();
			} else {
				action = executionClone.pop();
			}
		}

		if (action == null) {
			return nextAction();
		}

		if (action.getColour() != Colour.WHITE && action.getColour() != robot.getColour()) {
			action = nextAction();
		}

		if (action instanceof Break) {
			if (currentRobot == 0) {
				while (executionMain.pop() != null)
					;
				action = nextAction();
			} else {
				while (executionClone.pop() != null)
					;
				action = nextAction();
			}
		}

		if (action instanceof Procedure) {
			if (((Procedure) action).getName().equals(Procedure.PROCEDURE1_NAME)) {
				stack(procP1);
			} else {
				stack(procP2);
			}

			return nextAction();
		} else {
			if (action instanceof Clone) {
				numberOfRobots++;

			}

			return (_Action) action;
		}
	}

	/**
	 * Stack the procedure on the execution list
	 * 
	 * @param proc
	 *            a procedure to execute
	 */
	private void stack(Procedure proc) {
		if (currentRobot == 0) {
			executionMain.push(null);
			for (int i = proc.getSize() - 1; i >= 0; i--) {
				executionMain.push(proc.getAction(i));
			}
		} else {
			executionClone.push(null);
			for (int i = proc.getSize() - 1; i >= 0; i--) {
				executionClone.push(proc.getAction(i));
			}
		}
	}
}
