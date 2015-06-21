package lightcore.world.cell;

import lightcore.simulator.Colour;

public class NormalCell extends FullCell {

	public final static Colour NORMAL_CELL_COLOUR = Colour.WHITE;

	public NormalCell(int x, int y, int height) {
		super(x, y, height);
	}

	@Override
	public void setLight(boolean lightON) {
		throw new UnsupportedOperationException();

	}

	@Override
	public boolean isLightON() {
		throw new UnsupportedOperationException();
	}
}
