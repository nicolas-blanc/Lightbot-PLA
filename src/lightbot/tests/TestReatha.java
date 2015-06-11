package lightbot.tests;

import lightbot.system.Colour;
import lightbot.system.CardinalDirection;
import lightbot.system.Procedure;
import lightbot.system.Robot;
import lightbot.system.action.Forward;
import lightbot.system.action.Light;
import lightbot.system.world.Grid;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.cell.LightableCell;
import lightbot.system.world.cell.NormalCell;

public class TestReatha {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int size = 10;
		
		// class Grid tests -> OK !
		System.out.println("** Class Grid tests **");
		
		Grid grid1 = new Grid(size);
		Grid grid2;
		
		System.out.println("	Size = " + grid1.getSize());
		
		Cell c = new NormalCell(2, 1, 2);
		grid1.setCell(c);
		grid1.setCell(new NormalCell(2, 2, 3));
		grid1.setCell(new NormalCell(0, 7, 2));
		grid1.printGrid();

		// class Procedure tests
		//System.out.println("** Class Procedures tests **");
		
		//Procedure proc = new Procedure("proc1", 5);
		//System.out.println("	Name procedure : " + proc.getName());
		//System.out.println("	Max number of actions : " + proc.getMaxNumOfActions());
		
		System.out.println();
		
		//Forward forward = new Forward();
		
		// class Robot tests
		/*System.out.println("** Class Robot tests **");
		Robot robot = new Robot(1,9,Colour.WHITE,CardinalDirection.EAST);
		System.out.println("	Position du robot : " + "(" + robot.getPositionX() + ", " + robot.getPositionY() + ")");
		System.out.println("	Direction du robot : " + robot.getDirection());
		System.out.println("	Execution Forward ");
		forward.execute(grid1,robot);
		System.out.println("	Nouvelle position du robot : " + "(" + robot.getPositionX() + ", " + robot.getPositionY() + ")");
		
		
		robot.setPosition(2, 1);
		robot.setDirection(CardinalDirection.SOUTH);
		System.out.println("	Position du robot : " + "(" + robot.getPositionX() + ", " + robot.getPositionY() + ")");
		System.out.println("	Direction du robot : " + robot.getDirection());
		System.out.println("	Execution Forward ");
		forward.execute(grid1,robot);
		System.out.println("	Nouvelle position du robot : " + "(" + robot.getPositionX() + ", " + robot.getPositionY() + ")");
		robot.setDirection(CardinalDirection.EAST);
		System.out.println("	MAJ direction du robot : " + robot.getDirection());
		System.out.println("	Execution Forward ");
		forward.execute(grid1,robot);
		System.out.println("	Nouvelle position du robot : " + "(" + robot.getPositionX() + ", " + robot.getPositionY() + ")");

		System.out.println();*/
		
		// class Light tests
		System.out.println("** Class Light tests **");
		
		System.out.println("	Lightable cell (0,7) : " + ((grid1.getCell(0,7)) instanceof LightableCell));
		System.out.println("	> (0,7) becomes lightable");
		grid1.changeToLightable(0, 7);
		System.out.println("	Lightable cell (0,7) : " + ((grid1.getCell(0,7)) instanceof LightableCell));

		grid1.getCell(0, 7).setLight(true);
		System.out.println("	> Enlighten (0,7)");
		System.out.println("	Is cell (0,7) enlightened ? : " + grid1.getCell(0, 7).isLightON());
		
		System.out.println();
		
		/*Light light = new Light();
		
		robot.setPosition(0, 7);
		System.out.println("	Position du robot : " + "(" + robot.getPositionX() + ", " + robot.getPositionY() + ")");
		System.out.println("	Execution Light ");
		light.execute(grid1,robot);
		System.out.println("	Cell (0,7) colour : " + (grid1.getCell(0,7)).getColour());
		System.out.println("	Lightable cell (0,7) : " + (grid1.getCell(0,7)).getLightable());
		
		System.out.println();

		robot.setPosition(0, 8);
		System.out.println("	Position du robot : " + "(" + robot.getPositionX() + ", " + robot.getPositionY() + ")");
		System.out.println("	Lightable cell (0,8) : " + (grid1.getCell(0,8)).getLightable());
		System.out.println("	Execution Light ");
		light.execute(grid1,robot);
		System.out.println("	Cell (0,8) colour : " + (grid1.getCell(0,8)).getColour());
		System.out.println("	Lightable cell (0,8) : " + (grid1.getCell(0,8)).getLightable());
		
		System.out.println();
		
		// test levelToZero method
		grid1.levelToZero();
		grid1.printGrid();
		
		*/

	}

}
