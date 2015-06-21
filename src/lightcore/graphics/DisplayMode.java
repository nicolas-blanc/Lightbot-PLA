package lightcore.graphics;

import java.util.ArrayList;

import org.jsfml.graphics.Drawable;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;

public interface DisplayMode {
	
	public void initConstantDisplay();
	
	public ArrayList<Drawable> getConstantDisplay();
	
	public void display();
	
	public GridDisplay getGrid();
	
	public void printGrid();
	
	public CellPosition isInside(Vector2i coord);
	
	public void eventManager(Event event);
}
