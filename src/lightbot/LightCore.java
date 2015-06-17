package lightbot;

import java.util.List;

import lightbot.graphics.DisplayMode;
import lightbot.graphics.Editor;
import lightbot.graphics.Game;
import lightbot.graphics.ActionListDisplay;
import lightbot.graphics.LevelDisplay;
import lightbot.graphics.MenuDisplay;
import lightbot.graphics.Textures;
import lightbot.system.ParserJSON;
import lightbot.system._Executable;
import lightbot.system.generator.WorldGenerator;
import lightbot.system.world.Grid;
import lightbot.system.world.Level;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.TextEvent;

public class LightCore {

	public static RenderWindow window;
	public static DisplayMode display;

	public static int originX;
	public static int originY;
	
	public static boolean menu = true;
	public static boolean random = false;
	public static boolean worlds = false;
	public static boolean game = false;
	public static boolean editor = false;
	public static boolean bases = false;
	public static boolean procedures = false;
	public static boolean breakaction = false;
	public static boolean ifthenelse = false;
	public static boolean pointeurs = false;
	public static boolean fork = false;
	public static boolean levelButtonIsDisplayed = false;
	public static boolean firstPrintGame = true;
	
	public static String path = null;

	public static void main(String[] args) {
		
		// always at the beginning of the main
		Textures.initTextures();
		/***********************************/
		
		boolean firstLaunch = true;
		MenuDisplay menuD = new MenuDisplay();
		LevelDisplay levels = new LevelDisplay();

		

		// Create the window
		window = new RenderWindow();

		// base 1280 * 960 for 1920*1080
		window.create(new VideoMode(1000, 600), "LightCore");

		// Limit the framerate
		window.setFramerateLimit(60);

		// WorldGenerator newWorld = new WorldGenerator();
		// newWorld.getGrid().printGrid();
		// Grid grid = newWorld.getGrid();
		
		//Level level = ParserJSON.deserialize("example.json");
		//Grid grid = level.getGrid();
		//List<_Executable> actions = level.getListOfActions();

		//display = new Editor(5);
		//display = new Game(grid);
		//display.printGrid();

		RectangleShape rect = new RectangleShape(new Vector2f(710, 475));
		rect.setPosition(15, 15);
		rect.setOutlineThickness(1);
		rect.setOutlineColor(Color.BLACK);

		// Main loop
		while (window.isOpen()) {
			if(firstLaunch) {
				firstLaunch = false;
			}
			
			// Draw everything
			window.clear(Color.WHITE);
			//window.draw(rect);
			//display.display();
			//ActionListDisplay.displayActionList(actions, 10, window);
			if(menu){
				menuD.display();
			} else {
				if(worlds){
					levels.display();
					if(bases){
						levels.displayLevelButtons();
					}
					if(procedures){
						levels.displayLevelButtons();
					}
					if(breakaction){
						levels.displayLevelButtons();
					}
					if(pointeurs){
						levels.displayLevelButtons();
					}
					if(ifthenelse){
						levels.displayLevelButtons();
					}
					if(fork){
						levels.displayLevelButtons();
					}
				} else if (editor || game || random){
					if((game || random) && firstPrintGame){
						display.printGrid();
						firstPrintGame = false;
					}
					display.display();
				}
			}
			
			/*else if(game|| editor){
				if(game && firstPrintGame){
					display.printGrid();
					firstPrintGame = false;
				}
				display.display();
			}*/
			window.display();

			// Handle events
			for (Event event : window.pollEvents()) {
				if(menu){
					menuD.eventManager(event);
				}else{
					if(worlds){
						levels.eventManager(event);
						if(bases && !worlds){
							levels.eventManager(event);
						}
						else if(procedures && !worlds){
							levels.eventManager(event);
						}
						else if(pointeurs && !worlds){
							levels.eventManager(event);
						}
						else if(fork && !worlds){
							levels.eventManager(event);
						}
						else if(ifthenelse && !worlds){
							levels.eventManager(event);
						}
						else if(breakaction && !worlds){
							levels.eventManager(event);
						}
					}else if (editor || game || random){
						display.eventManager(event);
					}
				}
				switch (event.type) {
				case CLOSED:
					System.out.println("The user pressed the close button!");
					window.close();
					break;
				/*
				 * case TEXT_ENTERED: TextEvent textEvent = event.asTextEvent();
				 * System.out.println("The user typed the following character: "
				 * + textEvent.character); break;
				 * 
				 * case MOUSE_WHEEL_MOVED:
				 * System.out.println("The user moved the mouse wheel!"); break;
				 */
				
				default:
					break;
				}
			}

			//window.display();
		}
	}
}
