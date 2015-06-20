package lightbot.system;

import java.util.EmptyStackException;
import java.util.Stack;

import lightbot.system.action.Break;
import lightbot.system.action.Clone;
import lightbot.system.action._Action;
import lightbot.system.world.Level;
import lightbot.system.world.OutOfGridException;

public class Scheduler {
	final private Procedure procMain;
	final private Procedure procP1;
	final private Procedure procP2;
	
	private int currentRobot;
	private int numberOfRobots;
	
	private Level level;
	
	private Stack<_Executable> executionMain;
	private Stack<_Executable> executionClone;
	
	/**
	 * @param procMain The main procedure, generally, the procedure of the first Robot
	 * @param procP1 An others procedure, to represent fonction
	 * @param procP2 The last procedure, a fonction or the procedure for the second Robot
	 * @param grid The grid of the game
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
	 * Execute the action of Robots in parallele
	 * @throws LevelEndException Exception if the level is finish
	 */
	public void execute() throws LevelEndException {
		boolean notEnd = true;
		
		_Action action;
		Robot robot;
		robot = Robot.wheatley;
		
		currentRobot = 0;

		for (int i = procMain.getSize() - 1; i >= 0; i--) {
			executionMain.push(procMain.getAction(i));
		}
		
		for (int i = procP2.getSize() - 1; i >= 0; i--) {
			executionClone.push(procP2.getAction(i));
		}
		
		while (!executionMain.isEmpty() && notEnd) { // Changer pour une condition : tant que toutes les lumières ne sont pas allumé
			try {
				action = nextAction(robot);
			} catch (EmptyStackException e) {
				action = null;
				notEnd = false;
			}
			
			if (notEnd) {
				try {
					System.out.println("Action effectuer : " + action.toString() + " // Robot : " + currentRobot + " // number of robot : " + numberOfRobots);
					action.execute(level.getGrid(), robot);
					if(level.isCompleted()) {
						throw new LevelEndException();
					}
				} catch (OutOfGridException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				robot = giveNextRobot();			
			}
		}
	}

	/**
	 * Return the next robot who execute the next action
	 * @return the next Robot
	 */
	private Robot giveNextRobot() {
		Robot temp;
		if (currentRobot == 0) {
			temp = Robot.wheatley;
			System.out.println("Current robot : wheatley");
		} else {
			temp = Robot.wheatleyClone;
			System.out.println("Current robot : wheatleyClone");
		}
		
		System.out.print("Previous robot : " + currentRobot);
		currentRobot = ++currentRobot % numberOfRobots;
		System.out.println(" // current robot : " + currentRobot);
		
		return temp;
	}

	/**
	 * The next action who is executed, if the next is a procedure, this function return the first action of this procedure
	 * @param robot The current robot who execute the action
	 * @return The next action
	 */
	private _Action nextAction(Robot robot) {
		_Executable action;
		try {
			if (currentRobot == 0) {
				action = executionMain.pop();
			} else {
				action = executionClone.pop();
			}
		} catch (EmptyStackException e) {
			System.out.println("Changed robot, excetption");
			robot = giveNextRobot();
			if (currentRobot == 0) {
				action = executionMain.pop();
			} else {
				action = executionClone.pop();
			}
		}
		
		if (action == null) {
			return nextAction(robot);
		}
		
		if (action.getColour() != Colour.WHITE && action.getColour() != robot.getColour()) {
			action = nextAction(robot);
		}
		
		if (action instanceof Break) {
			if (currentRobot == 0) {
				while (executionMain.pop() != null);
				action = nextAction(robot);
			} else {
				while (executionClone.pop() != null);
				action = nextAction(robot);
			}
		}
		
		if (action instanceof Procedure) {
			if (((Procedure) action).getName().equals(Procedure.PROCEDURE1_NAME)) {
				pile(procP1);
			} else {
				pile(procP2);
			}
			
			return nextAction(robot);
		} else {
			if (action instanceof  Clone) {
				numberOfRobots++;
			}
			
			return (_Action) action; // Cast ???
		}
	}

	/**
	 * Stack on the pile the procedure give in parameters
	 * @param proc The procedure who add in the execution
	 */
	private void pile(Procedure proc) {
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
