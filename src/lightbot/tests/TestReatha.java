package lightbot.tests;

import lightbot.system.Colour;
import lightbot.system.Direction;
import lightbot.system.Procedure;
import lightbot.system.Robot;
import lightbot.system.action.Forward;
import lightbot.system.world.Grid;

public class TestReatha {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int size = 10;
		
		// class Grid tests -> OK !
		Grid grid1 = new Grid(size);
		Grid grid2;
		
		System.out.println("Size = " + grid1.getSize());
		
		grid1.setCell(2, 1, 2);
		grid1.setCell(2, 2, 2);
		grid1.setCell(0, 7, 3);
		grid1.printGrid();
		
		System.out.println();
		
		/*grid2 = new Grid(grid1, size);
		grid2.printGrid();
		
		System.out.println(grid2.getCell(2, 1).getHeight());*/
		
		// class Procedure tests
		Procedure proc = new Procedure("proc1", 5);
		System.out.println(proc.getName());
		System.out.println(proc.getActionLimit());
		
		// class Robot tests
		Robot robot = new Robot(1,9,Colour.WHITE,Direction.EAST);
		System.out.println(Forward.canMove(robot, grid1));
		Forward.execute(grid1,robot);
		System.out.println(robot.getPositionX());
		System.out.println(robot.getPositionY());
	}

}
