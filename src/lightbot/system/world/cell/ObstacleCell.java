package lightbot.system.world.cell;

import lightbot.system.Colour;

public class ObstacleCell extends FullCell {

	public final static Colour OBSTACLE_CELL_COLOUR = Colour.OBSTACLE;

	public ObstacleCell(int x, int y, int height) {
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
