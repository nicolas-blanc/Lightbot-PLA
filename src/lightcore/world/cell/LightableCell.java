package lightcore.world.cell;

import lightcore.simulator.Colour;

public class LightableCell extends FullCell {
	
	public static final Colour LIGHTABLE_CELL_INITIAL_COLOUR = Colour.GREEN;
	
	
	private boolean lightON; 
	
	public LightableCell(int x, int y, int height) {
		super(x, y, height);
		this.lightON = false;
	}
	
	public boolean isLightON() {
		return this.lightON;
	}
	
	public void setLight(boolean lightON) {
		this.lightON = lightON;
	}

}
