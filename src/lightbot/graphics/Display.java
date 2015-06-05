package lightbot.graphics;

import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

public interface Display {
	
	public void display();
	
	public GridDisplay getGrid();
	
	public CellPosition isInside(Vector2i coord);
	
	public void eventManager(Event event);
}
