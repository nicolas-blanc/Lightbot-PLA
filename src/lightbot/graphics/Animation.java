package lightbot.graphics;

import lightbot.tests.Main;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

public class Animation {
	
	private final float fallY = 20;
	private final float fallTime = 200;
	
	private Sprite[][][] cubes;
	
	public Animation(Sprite[][][] cubes){
		this.cubes = cubes;
	}
	
	public void updateSprite(Sprite[][][] cubes){
		this.cubes = cubes;
	}
	
	public void addRemoveCube(int line, int column, int level, boolean add, boolean block){
		boolean finished = false;
		boolean endDisplay = false;
		Vector2f initialPosition = cubes[line][column][level].getPosition();
		
		//Create a frame clock which will be used to control the cube's movement
		Clock frameClock = new Clock();

		//Create a reset clock that will measure the time until 200 milliseconds have passed to finish the animation
		Clock resetClock = new Clock();
		
		int transparency;
		if(add){
			transparency = 55;
			cubes[line][column][level].move(0, -fallY);
			cubes[line][column][level].setColor(new Color(255, 255, 255, transparency));
		}
		else
			transparency = 255;

		//Main loop
		while(Main.window.isOpen() && !finished) {
			
			//Draw everything
		    Main.window.clear(Color.WHITE);
		    
	    	for(Sprite s : Main.display.getConstantDisplay())
	    		Main.window.draw(s);
		    
	    	if(!block){
			    for(int l = 0; l<cubes.length; l++)
					for(int c = 0; c<cubes[0].length; c++)
						for(int lvl=0; lvl<50; lvl++)
							if(cubes[l][c][lvl] != null)
								Main.window.draw(cubes[l][c][lvl]);
	    	}
			else{
				for(int l = 0; l<cubes.length && !endDisplay; l++){
					for(int c = 0; c<cubes[0].length && !endDisplay; c++)
							for(int lvl=0; lvl<50 && !endDisplay; lvl++){
								if(lvl == level && l == line && c == column)
									endDisplay = true;
								else
									if(cubes[l][c][lvl] != null)
										Main.window.draw(cubes[l][c][lvl]);
							}
				}
				Main.window.draw(cubes[line][column][level]);
				endDisplay = false;
			}
		    
		    Main.window.display();

		    //Handle events
		    for(Event event : Main.window.pollEvents()) {
		    	switch(event.type) {
		    		case CLOSED:
		    			System.out.println("The user pressed the close button!");
		    			Main.window.close();
		    			break;
					default:
						break;
		    	}
		    }

		    //Get the amount of time that has passed since the last frame and restart the frame clock
		    Time deltaTime = frameClock.restart();

		    //Convert the time into seconds
		    float deltaMilliseconds = deltaTime.asMilliseconds();

		    //Move the cube
		    if(add){
		    	cubes[line][column][level].move(0, deltaMilliseconds * (fallY/fallTime));
		    	transparency += (int) deltaMilliseconds;
		    	cubes[line][column][level].setColor(new Color(255, 255, 255, transparency));
		    }
		    else{
		    	cubes[line][column][level].move(0, -deltaMilliseconds * (fallY/fallTime));
		    	transparency -= (int) deltaMilliseconds;
		    	cubes[line][column][level].setColor(new Color(255, 255, 255, transparency));
		    }

		    //Check if 200 milliseconds have passed on the reset clock
		    if(resetClock.getElapsedTime().asMilliseconds() >= fallTime)
		    	finished = true;
		}
		
		if(cubes[line][column][level].getPosition() != initialPosition && add)
			cubes[line][column][level].setPosition(initialPosition);
			
		if(!add)
			cubes[line][column][level].setColor(new Color(255, 255, 255, 0));
	}
}
