package lightbot.system.world;

import java.util.ArrayList;

import lightbot.system._Executable;
import lightbot.system.world.cell.LightableCell;

public class Level {

	private Grid grid;

	private ArrayList<_Executable> listOfActions;

	public Level(Grid grid, ArrayList<_Executable> listOfActions) {
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

	public int NumLightableCells() {
		int toReturn = 0;

		for (int i = 0; i < this.grid.getSize(); i++)
			for (int j = 0; j < this.grid.getSize(); j++)
				if (this.grid.getCell(i, j) instanceof LightableCell)
					toReturn++;

		return toReturn;
	}

}
