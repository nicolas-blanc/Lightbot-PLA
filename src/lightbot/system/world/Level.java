package lightbot.system.world;

import java.util.ArrayList;

import lightbot.system._Executable;

public class Level {
	
	private Grid grid;
	
	private ArrayList<_Executable> listOfActions;
	
	public Level(Grid grid, ArrayList<_Executable> listOfActions){
		this.grid = grid;
		this.listOfActions = listOfActions;
	}
	
	public Grid getGrid() {
		return this.grid;
	}
	
	public ArrayList<_Executable> getListOfActions() {
		return this.listOfActions;
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	public void setListOfActions(ArrayList<_Executable> actions) {
		this.listOfActions = actions;
	}

}
