package lightcore.world.cell;

import lightcore.simulator.TeleportColour;
import lightcore.world.Position;

public class TeleportCell extends FullCell {

	private Position destination;

	private TeleportColour colour;

	public TeleportCell(int x, int y, int height, int destX, int destY,
			TeleportColour colour) {
		super(x, y, height);
		this.destination = new Position(destX, destY);
		this.colour = colour;
	}

	public Position getDestinationPosition() {
		return this.destination;
	}

	public int getDestX() {
		return this.destination.getX();
	}

	public int getDestY() {
		return this.destination.getY();
	}

	public void setDestXY(int x, int y) {
		this.destination = new Position(x, y);
	}

	public TeleportColour getColour() {
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
