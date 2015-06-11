package lightbot.graphics;

import org.jsfml.graphics.Sprite;

import lightbot.system.RelativeDirection;
import lightbot.system.Robot;
import lightbot.system.action.Turn;
import lightbot.system.world.Grid;

public class Display {

	private Grid grid;
	public GridDisplay gridDisplay;
	private RobotDisplay robotDisplay;
	private boolean isGame = false;
	
	public Animation anim;
	
	private int line;
	private int column;
	
	public Display(Grid grid, int originX, int originY){
		this.grid = grid;
		this.isGame = true;
		
		this.line = this.grid.getSize();
		this.column = this.grid.getSize();
		
		this.gridDisplay = new GridDisplay(grid, originX, originY);
		this.gridDisplay.initGrid();
		this.robotDisplay = new RobotDisplay(Robot.wheatley, 255, originX, originY);
	}
	
	public Display(int line, int column, int originX, int originY){
		this.isGame = false;
		this.line = line;
		this.column = column;
		
		this.gridDisplay = new GridDisplay(line, column, originX, originY);
		anim = new Animation(this.gridDisplay.getGridSprites());
	}
	
	public void print(){
		for(int l=0; l<this.line; l++)
			for(int c=0; c<this.column; c++){
				this.gridDisplay.printPillar(l, c);
				if(this.isGame && Robot.wheatley.getLine() == l && Robot.wheatley.getColumn() == c)
					this.robotDisplay.print();
			}
	}
	
	public void printAnim(){
		anim = new Animation(this.gridDisplay.getGridSprites(), this.robotDisplay.robotSprite);
		
		Sprite[][][] grid = this.gridDisplay.getGridSprites();
		for(int l = 0; l < grid.length; l++){
			for(int c = 0; c < grid[0].length; c++){
				for(int level = 0; level < 50; level++){
					if(l == Robot.wheatley.getLine() && c == Robot.wheatley.getColumn() && level == GridDisplay.levelMax[l][c]){
						anim.addRemoveCube(l, c, level, true, true);
						anim.displayRobot(l, c, level, true);
					}
					else{
						if(grid[l][c][level] != null)
							anim.addRemoveCube(l, c, level, true, true);
					}
				}
			}
		}
	}
	
	public void removeAnim(){
		Sprite[][][] grid = this.gridDisplay.getGridSprites();
		for(int l = grid.length-1; l >= 0; l--)
			for(int c = grid[0].length-1; c >= 0; c--)
				for(int level = 49; level >= 0; level--)
					if(grid[l][c][level] != null)
						anim.addRemoveCube(l, c, level, false, true);
	}
	
	public GridDisplay getGridDisplay(){return this.gridDisplay;}
	
	public void rotate(int way){
		if(way == 0){
			this.gridDisplay.rotateLeft();
			int previousPosX = Robot.wheatley.getLine();
			Robot.wheatley.setLine(grid.getSize()-Robot.wheatley.getColumn()-1);
			Robot.wheatley.setColumn(previousPosX);
			
			Turn turn = new Turn(RelativeDirection.LEFT);
			turn.execute(null, Robot.wheatley);
			this.robotDisplay.updateRobot(Robot.wheatley, 255);
		}
		else{
			this.gridDisplay.rotateRight();
			int previousPosX = Robot.wheatley.getLine();
			Robot.wheatley.setLine(Robot.wheatley.getColumn());
			Robot.wheatley.setColumn(grid.getSize()-previousPosX-1);
			
			Turn turn = new Turn(RelativeDirection.RIGHT);
			turn.execute(null, Robot.wheatley);
			this.robotDisplay.updateRobot(Robot.wheatley, 255);
		}
		anim.updateSprite(this.gridDisplay.getGridSprites(), this.robotDisplay.getSprite());
	}
}
