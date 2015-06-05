package lightbot.tests;

import java.io.IOException;
import java.nio.file.Paths;

import lightbot.graphics.Button;
import lightbot.graphics.CellPosition;
import lightbot.graphics.Display;
import lightbot.graphics.Editor;
import lightbot.graphics.Game;
import lightbot.graphics.Textures;
import lightbot.system.Colour;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.window.event.TextEvent;

public class Main {
	
	public static RenderWindow window;
	
	public static void clicked(MouseButtonEvent mouse){
		
	}

	public static void main(String[] args) {
		Textures.initTextures();
		
		Button button;
		Sprite buttonSprite;
		Texture buttonTexture;
		
		Display display;
		
		boolean light = false;
		
		int[][] mat = {
			{3, 3, 1, 1},
			{1, 0, 1, 2},
			{1, 1, 0, 1},
			{1, 2, 4, 4}
		};
		
		//Create the window
		window = new RenderWindow();
		window.create(new VideoMode(640, 480), "Hello JSFML!");

		//Limit the framerate
		window.setFramerateLimit(30);
		
		//display = new Editor(8, 8, 320, 100);
		display = new Game(mat, 320, 100);
		
		buttonTexture = new Texture();
		try {
		    //Try to load the texture from file "cube.png" and "cell.png"
		    buttonTexture.loadFromFile(Paths.get("ressources/button.png"));
		} catch(IOException ex) {
		    //Ouch! something went wrong
		    ex.printStackTrace();
		}
		
		buttonSprite = new Sprite(buttonTexture);
		buttonSprite.scale(new Vector2f((float)0.2, (float)0.2));
		buttonSprite.setOrigin(Vector2f.div(new Vector2f(buttonTexture.getSize()), 2));
		buttonSprite.setPosition(50, 425);
		
		button = new Button(buttonSprite);

		//Main loop
		while (window.isOpen()) {
		    //Draw everything
		    window.clear(Color.WHITE);
		    
		    display.display();
		    
		    window.draw(buttonSprite);
		    window.display();

		  //Handle events
		    for(Event event : window.pollEvents()) {
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
			             
			         case MOUSE_BUTTON_PRESSED:
			        	 MouseButtonEvent mouse = event.asMouseButtonEvent();
			        	 
			        	 CellPosition pos = display.isInside(mouse.position);
			        	 if(mouse.button == Mouse.Button.LEFT){
			        		 if(button.isInside(mouse.position)){
			        			System.out.println("Button blue clicked");
			        			light = !light;
			        		 }
			        		 else if(pos.isFound()){
			        			System.out.println("Add cube on : \t\tLine : " + pos.getLine() + ", column : " + pos.getColumn() + ", level : " + pos.getLevel());
			        			if(light){
			        				if(pos.getLevel() > -1){
			        					display.getGrid().removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
			        					display.getGrid().addCube(pos.getLine(), pos.getColumn(), pos.getLevel(), Colour.GREEN);
			        				}
			        				else
			        					display.getGrid().addCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, Colour.GREEN);
			        			}
			        			else
			        				display.getGrid().addCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, Colour.WHITE);
			        		 }
			        	 }
			        	 else if(mouse.button == Mouse.Button.RIGHT){
			        		 if(pos.isFound() && pos.getLevel() > -1){
			        			System.out.println("Remove cube on : \tLine : " + pos.getLine() + ", column : " + pos.getColumn() + ", level : " + pos.getLevel());
			        			display.getGrid().removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
			        		 }
			        	 }
			        	 break;
					default:
						break;
			     }
		    }
		}
	}
}
