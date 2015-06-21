package lightcore;

import java.io.IOException;
import java.nio.file.Paths;

import lightcore.graphics.Button;
import lightcore.graphics.DisplayMode;
import lightcore.graphics.LevelDisplay;
import lightcore.graphics.MenuDisplay;
import lightcore.graphics.Textures;
import lightcore.world.Robot;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;

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
	public static String tutoPath = null;
	
	public static SoundBuffer soundBuffer;
	public static Sound sound = null;
	
	public static Button soundButton;

	public static void main(String[] args) {
		
		// always at the beginning of the main
		Textures.initTextures();
		/***********************************/
		
		Robot.wheatleyClone.setVisibility(false);
		
		Sprite soundTexture = new Sprite(Textures.soundTexture);
		soundButton = new Button(soundTexture, Textures.muteTexture, Textures.soundTexture);
		
		boolean firstLaunch = true;
		MenuDisplay menuD = new MenuDisplay();
		LevelDisplay levels = new LevelDisplay();

		// Create the window
		window = new RenderWindow();

		// base 1280 * 960 for 1920*1080
		window.create(new VideoMode(1000, 600), "LightCore", WindowStyle.CLOSE | WindowStyle.TITLEBAR);

		// Limit the framerate
		window.setFramerateLimit(60);
		
		// Main loop
		while (window.isOpen()) {
			if(firstLaunch) {
				firstLaunch = false;
			}
			
			// Draw everything
			window.clear(Color.WHITE);

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
			
			window.display();
			
			if(sound == null){
				soundBuffer = new SoundBuffer();
				try {
				    soundBuffer.loadFromFile(Paths.get("ressources/menu.flac"));
				} catch(IOException ex) {
				    System.err.println("Failed to load the sound:");
				}
				
				//Create a sound and set its buffer
				sound = new Sound();
				sound.setBuffer(soundBuffer);
				sound.setLoop(true);

				sound.play();
			}

			// Handle events
			for (Event event : window.pollEvents()) {
				if(menu){
					menuD.eventManager(event);
				}else if(worlds){
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
		}
	}
}
