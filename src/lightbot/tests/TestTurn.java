package lightbot.tests;

import lightbot.system.Colour;
import lightbot.system.CardinalDirection;
import lightbot.system.Robot;
import lightbot.system.RelativeDirection;
import lightbot.system.action.Jump;
import lightbot.system.action.Turn;
import lightbot.system.world.Grid;

public class TestTurn {

	public static void main(String[] args) {
		
		int size = 10;
		Grid grid1 = new Grid(size);
		
		//grid1.setCell(1, 2, 2);
		
		//Testing the change of direction
		Robot robot = new Robot(1,9,Colour.WHITE,CardinalDirection.EAST);
		Turn turnLeft = new Turn(RelativeDirection.LEFT);
		Turn turnRight = new Turn(RelativeDirection.RIGHT);
		
		
		System.out.println("Position du robot : " + robot.getDirection());
		
		turnLeft.execute(grid1, robot);
		System.out.println("Position du robot : " + robot.getDirection());
		
		turnLeft.execute(grid1, robot);
		System.out.println("Position du robot : " + robot.getDirection());
		
		turnLeft.execute(grid1, robot);
		System.out.println("Position du robot : " + robot.getDirection());
		
		turnRight.execute(grid1, robot);
		System.out.println("Position du robot : " + robot.getDirection());
		
		turnRight.execute(grid1, robot);
		System.out.println("Position du robot : " + robot.getDirection());
		
		turnRight.execute(grid1, robot);
		System.out.println("Position du robot : " + robot.getDirection());
		
		turnRight.execute(grid1, robot);
		System.out.println("Position du robot : " + robot.getDirection());
	}

}
