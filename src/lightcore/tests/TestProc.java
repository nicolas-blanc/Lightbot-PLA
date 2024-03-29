package lightcore.tests;

import lightcore.simulator.Colour;
import lightcore.simulator.Procedure;
import lightcore.simulator.action.Forward;
import lightcore.simulator.action.Jump;
import lightcore.simulator.action.Light;
import lightcore.simulator.action.Turn;
import lightcore.world.CardinalDirection;
import lightcore.world.Grid;
import lightcore.world.RelativeDirection;
import lightcore.world.Robot;
import lightcore.world.cell.LightableCell;
import lightcore.world.cell.NormalCell;

public class TestProc {

	public static void main(String[] args) {
		Grid g = new Grid(10);
		g.setCell(new NormalCell(0, 0, 1));
		g.setCell(new NormalCell(1, 0, 1));
		g.setCell(new NormalCell(2, 0, 1));
		g.setCell(new LightableCell(3, 0, 2));
		g.setCell(new NormalCell(3, 1, 1));
		g.setCell(new LightableCell(3, 2, 2));
		
		Robot r = new Robot(0, 0, Colour.WHITE, CardinalDirection.SOUTH);
		
		Procedure main = new Procedure("main", 10, Colour.WHITE);
		
		main.addAction(new Forward());
		main.addAction(new Forward());
		
		Procedure proc1 = new Procedure("proc1", 5, Colour.WHITE);
		
		proc1.addAction(new Jump());
		proc1.addAction(new Light());
		proc1.addAction(new Turn(RelativeDirection.LEFT, Colour.WHITE));
		
		main.addAction(proc1);
		main.addAction(new Jump());
		main.addAction(new Jump());
		main.addAction(new Light());
		
		main.execute(g, r);
		
		System.out.println(r.getLine() + " " + r.getColumn());
		
		
	}

}
