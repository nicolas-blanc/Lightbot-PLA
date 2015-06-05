package lightbot.tests;

import lightbot.system.Colour;
import lightbot.system.Direction;
import lightbot.system.Robot;
import lightbot.system.action.Jump;
import lightbot.system.world.Grid;

public class TestJump {

	public static void main(String[] args) {
		
		int size = 10;
		Grid grid1 = new Grid(size);
		
		grid1.setCell(1, 9, 2);
		grid1.setCell(1, 10, 3);
		grid1.setCell(2, 3, 3);
		grid1.printGrid();
		
		Robot robot = new Robot(1,9,Colour.WHITE,Direction.EAST);
		Jump.execute(robot, grid1);
		System.out.println("Position du robot : " + "(" + robot.getPositionX() + ", " + robot.getPositionY() + ")");
		
		Jump.execute(robot, grid1);
		System.out.println("Position du robot : " + "(" + robot.getPositionX() + ", " + robot.getPositionY() + ")");
	}

}
