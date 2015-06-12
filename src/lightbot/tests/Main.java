package lightbot.tests;

import lightbot.graphics.DisplayMode;
import lightbot.graphics.Editor;
import lightbot.graphics.Game;
import lightbot.graphics.Textures;
import lightbot.system.ParserJSON;
import lightbot.system.generator.WorldGenerator;
import lightbot.system.world.Grid;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.TextEvent;

public class Main {
	
	public static RenderWindow window;
	public static DisplayMode display;
	
	public static VideoMode screenInformations;
	public static float scaleRatio;
	
	public static int originX;
	public static int originY;

	public static void main(String[] args) {
		
		Textures.initTextures();
		
		//Create the window
		window = new RenderWindow();
		
		screenInformations = VideoMode.getDesktopMode();
		scaleRatio = ((float)screenInformations.width)/1920;
		System.out.println(scaleRatio);
		
		// base 1280 * 960 for 1920*1080
		window.create(new VideoMode(1000, 600), "LightCore");
		
		//Limit the framerate
		window.setFramerateLimit(60);
		
		//WorldGenerator newWorld = new WorldGenerator();
		
		//newWorld.getGrid().printGrid();
		//Grid grid = newWorld.getGrid();
		Grid grid = ParserJSON.deserialize("grid1.json");

		originX = (750/2)+15;
		originY = (475-(5*Textures.cellTexture.getSize().y));
		
		
		int sizeX = (750/2)+15;
		//int sizeY = (475-15-(grid.getSize()*Textures.cellTexture.getSize().y));
		int sizeY = (475-(5*Textures.cellTexture.getSize().y));
		//display = new Editor(5, 5, sizeX, sizeY);
		display = new Game(grid, sizeX, sizeY);
		//display.printGrid();
		
		RectangleShape rect = new RectangleShape(new Vector2f(750, 475));
		rect.setPosition(15, 15);
		rect.setOutlineThickness(1);
		rect.setOutlineColor(Color.BLACK);

		//Main loop
		while (window.isOpen()) {
		    //Draw everything
		    window.clear(Color.WHITE);
		    window.draw(rect);
		    display.display();
		    window.display();

		  //Handle events
		    for(Event event : window.pollEvents()) {
		    	display.eventManager(event);
		    	 switch(event.type) {
			         case CLOSED:
			             System.out.println("The user pressed the close button!");
			             window.close();
			             break;
			        	    
			         case TEXT_ENTERED:
			        	    TextEvent textEvent = event.asTextEvent();
			        	    System.out.println("The user typed the following character: " + textEvent.character);
			        	    break;
	
			         case MOUSE_WHEEL_MOVED:
			             System.out.println("The user moved the mouse wheel!");
			             break;
					default:
						break;
			     }
		    }
		}
	}
}
