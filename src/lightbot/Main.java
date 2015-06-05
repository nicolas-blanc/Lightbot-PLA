package lightbot;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import lightbot.graphics.GridDisplay;

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
	
	static final String RESSOURCES_PATH = "ressources/";
	
	static RenderWindow window;
	/*static Texture cubeTexture;
	static Texture cellTexture;
	static ArrayList<Sprite> cubeList;
	static ArrayList<int[]> coord;
	
	public static void init_sprite(){
		cubeList = new ArrayList<Sprite>();
		coord = new ArrayList<int[]>();
		cubeTexture = new Texture();
		cellTexture = new Texture();
		
		try {
		    //Try to load the texture from file "cube.png"
		    cubeTexture.loadFromFile(Paths.get(RESSOURCES_PATH+"cube.png"));
		    cellTexture.loadFromFile(Paths.get(RESSOURCES_PATH+"cell.png"));
		} catch(IOException ex) {
		    //Ouch! something went wrong
		    ex.printStackTrace();
		}
	}
	
	public static void add_cube(int l, int c, int level){
		Sprite toAdd = new Sprite(cubeTexture);
		float decalX = cellTexture.getSize().x / 2;
		float decalY = cellTexture.getSize().y / 2;
		float sizeCubeY = cubeTexture.getSize().y;
		Vector2f decal;
		
		if(level == 0){
			if(l == c)
				decal = new Vector2f(-1, l*2*(decalY-1));
			else
				decal = new Vector2f((c-l)*(decalX-1), (l+c)*(decalY-1));
		}
		else{
			if(l == c)
				decal = new Vector2f(-1, (l+1)*2*(decalY-1)-(sizeCubeY-2)-(level-1)*decalY);
			else
				decal = new Vector2f((c-l)*(decalX-1), (l+c+2)*(decalY-1)-(sizeCubeY-2)-(level-1)*decalY);
		}
		
		Vector2f origin = Vector2f.div(new Vector2f(cubeTexture.getSize()), 2);
		
		toAdd.scale(new Vector2f((float)0.5, (float)0.5));
		toAdd.setOrigin(Vector2f.sub(origin, decal));
		toAdd.setPosition(320, 150);
		cubeList.add(toAdd);
	}
	
	public static void print_cubeList(){
		for(Sprite cube : cubeList)
			window.draw(cube);
	}
	
	public static void add_level(int l, int c, int level){
		if(level != 0)
			for(int i = 1; i<=level; i++)
				add_cube(l, c, i-1);
	}*/
	
	public static void clicked(MouseButtonEvent mouse){
		
	}

	public static void main(String[] args) {
		GridDisplay gridD;
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
		
		gridD = new GridDisplay(window);
		gridD.initSprite();
		
		for(int l = 0; l<4; l++)
			for(int c = 0; c<4; c++)
				gridD.addLevel(l, c, mat[l][c]);

		//Main loop
		while (window.isOpen()) {
		    //Draw everything
		    window.clear(Color.WHITE);
		    
		    gridD.printCubeList();
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
			        	 if(mouse.button == Mouse.Button.LEFT){
			        		 clicked(mouse);
			        	 }
			        	 break;
			     }
		    }
		}
	}
}
