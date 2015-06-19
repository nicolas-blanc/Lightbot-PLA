package lightbot.system;

import java.util.Stack;

import javax.sound.sampled.ReverbType;

import lightbot.graphics.ProcedureBlockDisplay;
import lightbot.system.action._Action;
import lightbot.system.world.Grid;
import lightbot.system.world.Level;
import lightbot.system.world.OutOfGridException;

public class Scheduler {
	private Procedure procMain;
	private Procedure procP1;
	private Procedure procP2;
	
	private int currentRobot;
	private int numberOfRobots;
	
	private Level level;
	
	private Stack<_Executable> executionMain;
	private Stack<_Executable> executionClone;
	
	/**
	 * @param procMain
	 * @param procP1
	 * @param procP2
	 * @param grid
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
	
	public void execute() throws LevelEndException {
		_Action action;
		Robot robot;
		
		currentRobot = 0;
		
		pile(procMain);
		
		while (!executionMain.isEmpty()) { // Changer pour une condition : tant que toutes les lumi�res ne sont pas allum�
			action = nextAction();
			robot = giveRobot();
			
			try {
				action.execute(level.getGrid(), robot);
				if(level.isCompleted()) {
					throw new LevelEndException();
				}
			} catch (OutOfGridException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return
	 */
	private Robot giveRobot() {
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
	 * @return
	 */
	private _Action nextAction() {
		_Executable action;
		
		if (currentRobot == 0) {
			action = executionMain.pop();
		} else {
			action = executionClone.pop();
		}
		
		if (action instanceof Procedure) {
			if (((Procedure) action).getName().equals("proc1")) {
				pile(procP1);
			} else {
				pile(procP2);
			}
			
			return nextAction();
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
