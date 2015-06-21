package lightcore.tests;

import lightcore.simulator.Colour;
import lightcore.simulator.action.Jump;
import lightcore.simulator.action.Turn;
import lightcore.world.CardinalDirection;
import lightcore.world.Grid;
import lightcore.world.RelativeDirection;
import lightcore.world.Robot;

public class TestTurn {

	public static void main(String[] args) {
		
		int size = 10;
		Grid grid1 = new Grid(size);
		
		//grid1.setCell(1, 2, 2);
		
		//Testing the change of direction
		Robot robot = new Robot(1,9,Colour.WHITE,CardinalDirection.EAST);
		Turn turnLeft = new Turn(RelativeDirection.LEFT, Colour.WHITE);
		Turn turnRight = new Turn(RelativeDirection.RIGHT, Colour.WHITE);
		
		
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
