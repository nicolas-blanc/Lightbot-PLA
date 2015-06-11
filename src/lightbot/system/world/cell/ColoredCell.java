package lightbot.system.world.cell;

import lightbot.system.Colour;

public class ColoredCell extends FullCell {

	private Colour colour;

	public ColoredCell(int x, int y, int height, Colour colour) {
		super(x, y, height);
		this.colour = colour;
	}

	public Colour getColour() {
		return this.colour;
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
