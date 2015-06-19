package lightbot.system.world;

import java.util.ArrayList;

import lightbot.system._Executable;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.cell.LightableCell;

public class Level {

	private Grid grid;

	private ArrayList<_Executable> listOfActions;
	
	private int robotInitialX;
	private int robotInitialY;

	// TODO: add these to the json parser
	private boolean useProc1;
	private boolean useProc2;

	// max number of actions in procedures
	private int mainLimit;
	private int proc1Limit;
	private int proc2Limit;

	//

	public Level(Grid grid, ArrayList<_Executable> listOfActions, boolean useProc1, boolean useProc2, int mainLimit,
			int proc1Limit, int proc2Limit) {
		this.grid = grid;
		this.listOfActions = listOfActions;

		this.useProc1 = useProc1;
		this.useProc2 = useProc2;

		this.mainLimit = mainLimit;
		this.proc1Limit = proc1Limit;
		this.proc2Limit = proc2Limit;
	}

	public Grid getGrid() {
		return this.grid;
	}

	public ArrayList<_Executable> getListOfActions() {
		return this.listOfActions;
	}

	public boolean useProc1() {
		return this.useProc1;
	}

	public boolean useProc2() {
		return this.useProc2;
	}

	public int getMainLimit() {
		return this.mainLimit;
	}

	public int getProc1Limit() {
		return this.proc1Limit;
	}

	public int getProc2Limit() {
		return this.proc2Limit;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	public int getRobotInitialX() {
		return this.robotInitialX;
	}
	
	public int getRobotInitialY() {
		return this.robotInitialY;
	}
	
	public void setRobotInitialX(int initialX) {
		this.robotInitialX = initialX;
	}
	
	public void setRobotInitialY(int initialY) {
		this.robotInitialY = initialY;
	}

	public void setListOfActions(ArrayList<_Executable> actions) {
		this.listOfActions = actions;
	}

	public boolean isCompleted() {
		int size = this.grid.getSize();

		for (int l = 0; l < size; l++)
			for (int c = 0; c < size; c++) {
				Cell cell = this.grid.getCell(l, c);
				if (cell instanceof LightableCell && !((LightableCell) cell).isLightON())
					return false;
			}

		return true;

	}
}
