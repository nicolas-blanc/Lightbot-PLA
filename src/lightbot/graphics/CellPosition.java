package lightbot.graphics;

public class CellPosition {
	
	int line;
	int column;
	int level;
	int color;
	boolean isFound;
	boolean isMaxCell;
	
	public CellPosition(int line, int column, int level, boolean isFound){
		this.line = line;
		this.column = column;
		this.level = level;
		this.isFound = isFound;
	}
	
	public int getLine(){return this.line;}
	public int getColumn(){return this.column;}
	public int getLevel(){return this.level;}
	public boolean isFound(){return this.isFound;}
	public boolean isMaxCell(){return this.isMaxCell;}
	public int getColor(){return this.color;}
	
}
