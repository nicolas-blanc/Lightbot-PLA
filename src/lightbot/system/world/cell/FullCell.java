package lightbot.system.world.cell;

/**
 * 
 * Represents a cell that is visible.
 *
 */
public abstract class FullCell extends Cell {

	private int height;

	public FullCell(int x, int y, int height) {
		super(x, y);
		this.height = height;
	}

	public int getHeight() {
		return this.height;
	}

}
