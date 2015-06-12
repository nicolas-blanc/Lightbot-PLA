package lightbot.tests;

import lightbot.system.Colour;
import lightbot.system.CardinalDirection;
import lightbot.system.Procedure;
import lightbot.system.RelativeDirection;
import lightbot.system.Robot;
import lightbot.system.action.Forward;
import lightbot.system.action.Jump;
import lightbot.system.action.Light;
import lightbot.system.action.Turn;
import lightbot.system.world.Grid;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.cell.LightableCell;
import lightbot.system.world.cell.NormalCell;
import lightbot.system.world.cell.TeleportCell;
import lightbot.system.world.cell.TeleportColour;

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
		System.out.println("** Class Procedures tests **");
		
		Procedure proc = new Procedure("proc1", 5);
		System.out.println("	Name procedure : " + proc.getName());
		System.out.println("	Max number of actions : " + proc.getMaxNumOfActions());
		
		System.out.println();
		
		Forward forward = new Forward();
		
		// class Robot tests
		System.out.println("** Class Robot tests **");
		Robot robot = new Robot(1,9,Colour.WHITE,CardinalDirection.EAST);
		System.out.println("	Position du robot : " + "(" + robot.getLine() + ", " + robot.getColumn() + ")");
		System.out.println("	Direction du robot : " + robot.getDirection());
		System.out.println("	Execution Forward ");
		forward.execute(grid1,robot);
		System.out.println("	Nouvelle position du robot : " + "(" + robot.getLine() + ", " + robot.getColumn() + ")");
		
		
		robot.setPosition(2, 1);
		robot.setDirection(CardinalDirection.SOUTH);
		System.out.println("	Position du robot : " + "(" + robot.getLine() + ", " + robot.getColumn() + ")");
		System.out.println("	Direction du robot : " + robot.getDirection());
		System.out.println("	Execution Forward ");
		forward.execute(grid1,robot);
		System.out.println("	Nouvelle position du robot : " + "(" + robot.getLine() + ", " + robot.getColumn() + ")");
		robot.setDirection(CardinalDirection.EAST);
		System.out.println("	MAJ direction du robot : " + robot.getDirection());
		System.out.println("	Execution Forward ");
		forward.execute(grid1,robot);
		System.out.println("	Nouvelle position du robot : " + "(" + robot.getLine() + ", " + robot.getColumn() + ")");

		System.out.println();
		
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
		
		Light light = new Light();
		
		robot.setPosition(0, 7);
		System.out.println("	Position du robot : " + "(" + robot.getLine() + ", " + robot.getColumn() + ")");
		System.out.println("	Execution Light ");
		light.execute(grid1,robot);
		System.out.println("	Lightable cell (0,7) : " + (grid1.getCell(0,7)).isLightON());
		
		System.out.println();

		robot.setPosition(0, 8);
		System.out.println("	Position du robot : " + "(" + robot.getLine() + ", " + robot.getColumn() + ")");
		System.out.println("	Execution Light ");
		grid1.changeToLightable(0, 8);
		light.execute(grid1,robot);
		System.out.println("	Lightable cell (0,8) : " + (grid1.getCell(0,8)).isLightON());
		
		System.out.println();

		grid1.printGrid();
		
		System.out.println();
		
		// Teleportation tests
		System.out.println("** Teleportation tests  **");
		System.out.println("		Forward");
		
		Jump jump = new Jump();
		Turn turnLeft = new Turn(RelativeDirection.LEFT);
		Turn turnRight = new Turn(RelativeDirection.RIGHT);
		
		Robot robot1 = new Robot(2,2,Colour.WHITE,CardinalDirection.WEST);
		System.out.println("	Position du robot1 : " + "(" + robot1.getLine() + ", " + robot1.getColumn() + ")");
		grid1.setCell(new NormalCell(3,1,2));
		grid1.setCell(new NormalCell(4,1,1));
		System.out.println("	> Set cells (4,0) & (9,9) to TeleportCell");
		System.out.println();
		grid1.setCell(new TeleportCell(4,0,1,9,9,TeleportColour.TELEPORT));
		grid1.setCell(new TeleportCell(9,9,1,4,0,TeleportColour.TELEPORT));
		
		grid1.printGrid();
		
		jump.execute(grid1, robot1);
		System.out.println("	Sauter -> Position du robot1 : " + "(" + robot1.getLine() + ", " + robot1.getColumn() + ")");
		turnLeft.execute(grid1, robot1);
		forward.execute(grid1, robot1);
		System.out.println("	Tourner à gauche+Avancer -> Position du robot1 : " + "(" + robot1.getLine() + ", " + robot1.getColumn() + ")");
		jump.execute(grid1, robot1);
		System.out.println("	Sauter -> Position du robot1 : " + "(" + robot1.getLine() + ", " + robot1.getColumn() + ")");
		turnRight.execute(grid1, robot1);
		forward.execute(grid1, robot1);
		System.out.println("	Tourner à droite+Avancer -> Position du robot1 : " + "(" + robot1.getLine() + ", " + robot1.getColumn() + ")");
		grid1.printGrid();
		System.out.println();
		
		System.out.println("		Jump");

		System.out.println("	> Set cells (8,9) & (9,0) to TeleportCell");
		grid1.setCell(new TeleportCell(8,9,2,9,0,TeleportColour.TELEPORT));
		grid1.setCell(new TeleportCell(9,0,2,8,9,TeleportColour.TELEPORT));		
		grid1.printGrid();
		System.out.println("	Position du robot1 : " + "(" + robot1.getLine() + ", " + robot1.getColumn() + ")");
		turnRight.execute(grid1, robot1);
		jump.execute(grid1, robot1);
		System.out.println("	Tourner à droite+Sauter -> Position du robot1 : " + "(" + robot1.getLine() + ", " + robot1.getColumn() + ")");
		grid1.printGrid();
	}
}
