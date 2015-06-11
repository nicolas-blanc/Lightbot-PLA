package lightbot.system.world.cell;

import lightbot.system.world.Position;

/**
 * 
 * Represents a basic cell defined only its position.
 *
 */
public abstract class Cell {

	
	private Position position;

	public Cell(int x, int y) {
		this.position = new Position(x, y);
	}

	public Position getPosition() {
		return this.position;
	}

	public int getX() {
		return this.position.getX();
	}

	public int getY() {
		return this.position.getY();
	}
	
	public void setX(int x){
		this.position.setX(x);
	}
	
	public void setY(int y){
		this.position.setY(y);
	}

	/**
	 * @return true if cell is an EmptyCell.
	 */
	public boolean isEmptyCell() {
		return this instanceof EmptyCell;
	}
	
	public abstract void setLight(boolean lightON);
	
	public abstract boolean isLightON();
	
	public abstract int getHeight();
	
	
}
