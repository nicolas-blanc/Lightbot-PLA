package lightbot.graphics;

import java.util.ArrayList;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

public interface DisplayMode {
	
	public void initConstantDisplay();
	
	public ArrayList<Sprite> getConstantDisplay();
	
	public void display();
	
	public GridDisplay getGrid();
	
	public void printGrid();
	
	public CellPosition isInside(Vector2i coord);
	
	public void eventManager(Event event);
}
