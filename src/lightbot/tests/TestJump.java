package lightbot.tests;

import lightbot.system.Colour;
import lightbot.system.CardinalDirection;
import lightbot.system.Robot;
import lightbot.system.action.Jump;
import lightbot.system.world.Grid;
import lightbot.system.world.cell.NormalCell;

public class TestJump {

	public static void main(String[] args) {
		
		int size = 10;
		Grid grid1 = new Grid(size);
		
		grid1.setCell(new NormalCell(1, 8, 2));
		grid1.setCell(new NormalCell(1, 9, 3));
		grid1.printGrid();
		
		Robot robot = new Robot(1,8,Colour.WHITE,CardinalDirection.EAST);
		System.out.println("Position du robot : " + "(" + robot.getLine() + ", " + robot.getColumn() + ")");
		
		
		Jump j = new Jump();
		j.execute(grid1, robot);
		System.out.println("Position du robot : " + "(" + robot.getLine() + ", " + robot.getColumn() + ")");
		
		j.execute(grid1, robot);
		System.out.println("Position du robot : " + "(" + robot.getLine() + ", " + robot.getColumn() + ")");
	}

}
