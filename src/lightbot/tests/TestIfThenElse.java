package lightbot.tests;

import lightbot.system.CardinalDirection;
import lightbot.system.Colour;
import lightbot.system.Robot;
import lightbot.system.action.Forward;
import lightbot.system.action.Jump;
import lightbot.system.world.Grid;
import lightbot.system.world.cell.ColoredCell;
import lightbot.system.world.cell.NormalCell;

public class TestIfThenElse {

	public static void main(String[] args) {
		
		Grid grid = new Grid(10);
		
		grid.setCell(new NormalCell(0, 0, 1));
		grid.setCell(new ColoredCell(1, 0, 1, Colour.RED));
		grid.setCell(new NormalCell(2, 0, 1));
		grid.setCell(new NormalCell(3, 0, 2));
		grid.setCell(new NormalCell(4, 0, 3));
		grid.setCell(new NormalCell(5, 0, 3));
		grid.setCell(new ColoredCell(6, 0, 3,  Colour.GREEN));
		grid.setCell(new NormalCell(7, 0, 3));
		
		Robot robot = new Robot(0, 0, Colour.WHITE, CardinalDirection.SOUTH);
		System.out.println(robot.getLine() + " " + robot.getColumn() + " " + robot.getColour() + " forward");
		
		Forward forward = new Forward();
		forward.execute(grid, robot);
		System.out.println(robot.getLine() + " " + robot.getColumn() + " " + robot.getColour() + " forward");
		
		forward.execute(grid, robot);
		System.out.println(robot.getLine() + " " + robot.getColumn() + " " + robot.getColour() + " jump red" );
		
		Jump jumpr = new Jump(Colour.RED);
		jumpr.execute(grid, robot);
		System.out.println(robot.getLine() + " " + robot.getColumn() + " " + robot.getColour() + " jump green");
		
		Jump jump = new Jump(Colour.GREEN);
		jump.execute(grid, robot);
		System.out.println(robot.getLine() + " " + robot.getColumn() + " " + robot.getColour() + " jump red");
		
		jumpr.execute(grid, robot);
		System.out.println(robot.getLine() + " " + robot.getColumn() + " " + robot.getColour() + " forward");
		
		forward.execute(grid, robot);
		System.out.println(robot.getLine() + " " + robot.getColumn() + " " + robot.getColour() + " forward");
		
		forward.execute(grid, robot);
		System.out.println(robot.getLine() + " " + robot.getColumn() + " " + robot.getColour() + " forward red");
		
		Forward forwardr = new Forward(Colour.RED);
		forwardr.execute(grid, robot);
		System.out.println(robot.getLine() + " " + robot.getColumn() + " " + robot.getColour() + " forward green");
		
		Forward forwardg = new Forward(Colour.GREEN);
		forwardg.execute(grid, robot);
		System.out.println(robot.getLine() + " " + robot.getColumn() + " " + robot.getColour() + " X");
		
	}

}
