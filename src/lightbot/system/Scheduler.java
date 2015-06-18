package lightbot.system;

import lightbot.graphics.ProcedureBlockDisplay;
import lightbot.system.world.OutOfGridException;

public class Scheduler {
	
	private int currentRobot = 0;
	private int numberOfRobots;
	
	public Scheduler(int numberOfRobots){
		this.numberOfRobots = numberOfRobots;
	}
	
	public void execute() throws OutOfGridException{
		ProcedureBlockDisplay.main.executeOne(ProcedureBlockDisplay.lvl.getGrid(), Robot.wheatley);
		if(numberOfRobots == 2) // TODO change main to robot procedure
			ProcedureBlockDisplay.main.executeOne(ProcedureBlockDisplay.lvl.getGrid(), Robot.wheatleyClone);
	}
	
	public void updateNumberOfRobots(int numberOfRobots){
		this.numberOfRobots = numberOfRobots;
	}

}
