package lightbot.system;

import java.util.Stack;

import lightbot.system.action._Action;
import lightbot.system.world.Grid;
import lightbot.system.world.OutOfGridException;

public class Scheduler {
	final private Procedure procMain;
	final private Procedure procP1;
	final private Procedure procP2;
	
	private int currentRobot;
	private int numberOfRobots;
	
	private Grid grid;
	
	private Stack<_Executable> executionMain;
	private Stack<_Executable> executionClone;
	
	/**
	 * @param procMain
	 * @param procP1
	 * @param procP2
	 * @param grid
	 */
	public Scheduler(Procedure procMain, Procedure procP1, Procedure procP2, Grid grid) {
		super();
		this.procMain = procMain;
		this.procP1 = procP1;
		this.procP2 = procP2;
		this.grid = grid;
		
		numberOfRobots = 1;

		executionMain = new Stack<_Executable>();
		executionClone = new Stack<_Executable>();
	}
	
	public void execute() {
		_Action action;
		Robot robot;
		robot = Robot.wheatley;

		currentRobot = 0;
		
		pile(procMain);
		while (!executionMain.isEmpty()) { // Changer pour une condition : tant que toutes les lumi�res ne sont pas allum�
			action = nextAction(robot);
			
			try {
				action.execute(grid, robot);
			} catch (OutOfGridException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			robot = giveNextRobot();
		}
	}

	/**
	 * @return
	 */
	private Robot giveNextRobot() {
		Robot temp;
		if (currentRobot == 0) {
			temp = Robot.wheatley;
		} else {
			temp = Robot.wheatleyClone;
		}
		
		currentRobot = ++currentRobot % numberOfRobots;
		
		return temp;
	}

	/**
	 * @param robot 
	 * @return
	 */
	private _Action nextAction(Robot robot) {
		_Executable action;
		
		if (currentRobot == 0) {
			action = executionMain.pop();
		} else {
			action = executionClone.pop();
		}
		
		if (action.getColour() != Colour.WHITE && action.getColour() != robot.getColour()) {
			action = nextAction(robot);
		}
		
		if (action instanceof Procedure) {
			if (((Procedure) action).getName().equals(Procedure.PROCEDURE1_NAME)) {
				pile(procP1);
			} else {
				pile(procP2);
			}
			
			return nextAction(robot);
		} else {
			return (_Action) action; // Cast ???
		}
	}

	/**
	 * @param proc
	 */
	private void pile(Procedure proc) {
		if (currentRobot == 0) {
			for (int i = proc.getSize() - 1; i >= 0; i--) {
				executionMain.push(proc.getAction(i));
			}
		} else {
			for (int i = proc.getSize() - 1; i >= 0; i--) {
				executionClone.push(proc.getAction(i));
			}
		}
	}
}
