package lightbot.system.world.cell;

/**
 * 
 * Represents an emtpy cell.
 *
 */
public class EmptyCell extends Cell {

	public EmptyCell(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean isLightON() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getHeight() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLight(boolean lightON) {
		throw new UnsupportedOperationException();
		
	}
}
