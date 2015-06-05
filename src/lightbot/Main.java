package lightbot;

import java.io.IOException;
import java.nio.file.Paths;

import lightbot.graphics.Button;
import lightbot.graphics.GridDisplay;
import lightbot.graphics.GridDisplay.PosCell;

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
	
	static RenderWindow window;
	
	public static void clicked(MouseButtonEvent mouse){
		
	}

	public static void main(String[] args) {
		Button button;
		Sprite buttonSprite;
		GridDisplay gridD;
		Texture buttonTexture;
		
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
		
		/*buttonTexture = new Texture();
		try {
		    //Try to load the texture from file "cube.png" and "cell.png"
		    buttonTexture.loadFromFile(Paths.get("ressources/button.png"));
		} catch(IOException ex) {
		    //Ouch! something went wrong
		    ex.printStackTrace();
		}
		
		buttonSprite = new Sprite(buttonTexture);
		buttonSprite.scale(new Vector2f((float)0.5, (float)0.5));
		buttonSprite.setOrigin(Vector2f.div(new Vector2f(buttonTexture.getSize()), 2));
		buttonSprite.setPosition(200, 400);
		
		button = new Button(buttonSprite);*/
		
		gridD = new GridDisplay(window, 8, 8, 320, 100);
		gridD.initSprite();
		gridD.initCanva();
		
		/*for(int l = 0; l<4; l++)
			for(int c = 0; c<4; c++)
				gridD.addLevel(l, c, mat[l][c]);*/

		//Main loop
		while (window.isOpen()) {
		    //Draw everything
		    window.clear(Color.WHITE);
		    
		    gridD.printCanva();
		    gridD.printCubeList();
		    //window.draw(buttonSprite);
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
			        	 PosCell pos = gridD.isInside(mouse.position);
			        	 if(mouse.button == Mouse.Button.LEFT){
			        		 /*if(button.isInside(mouse.position))
			        			System.out.println("Button clicked");
			        		 else */if(pos.isFound()){
			        			System.out.println("Add cube on : \t\tLine : " + pos.getLine() + ", column : " + pos.getColumn() + ", level : " + pos.getLevel());
			        			gridD.addCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
			        		 }
			        	 }
			        	 else if(mouse.button == Mouse.Button.RIGHT){
			        		 /*if(button.isInside(mouse.position))
			        			System.out.println("Button clicked");
			        		 else */if(pos.isFound() && pos.getLevel() > -1){
			        			System.out.println("Remove cube on : \tLine : " + pos.getLine() + ", column : " + pos.getColumn() + ", level : " + pos.getLevel());
			        			gridD.removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
			        		 }
			        	 }
			        	 break;
			     }
		    }
		}
	}
}
