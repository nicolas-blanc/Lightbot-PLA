package lightbot.tests;

import lightbot.system.CardinalDirection;
import lightbot.system.Colour;
import lightbot.system.RelativeDirection;
import lightbot.system.Robot;
import lightbot.system.action.Forward;
import lightbot.system.action.Jump;
import lightbot.system.action.Turn;
import lightbot.system.world.Grid;
import lightbot.system.world.cell.NormalCell;
import lightbot.system.world.cell.ObstacleCell;

public class TestObstacle {

	public static void main(String[] args) {
		
		Grid grid = new Grid(10);
		grid.setCell(new NormalCell(1, 1, 2));
		grid.setCell(new ObstacleCell(1, 2, 2));
		grid.setCell(new ObstacleCell(2, 1, 3));
		
		Robot robot = new Robot(1, 1, Colour.WHITE, CardinalDirection.EAST);
		System.out.println(robot.getLine() + " " + robot.getColumn());
		
		Forward f = new Forward();
		f.execute(grid, robot);
		System.out.println(robot.getLine() + " " + robot.getColumn());
		
		Turn t = new Turn(RelativeDirection.RIGHT, Colour.WHITE);
		t.execute(grid, robot);
		System.out.println(robot.getLine() + " " + robot.getColumn());
		
		Jump j = new Jump();
		j.execute(grid, robot);
		System.out.println(robot.getLine() + " " + robot.getColumn());
		
	}

}
