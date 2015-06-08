package lightbot.graphics;

import lightbot.system.Direction;
import lightbot.system.Robot;
import lightbot.tests.Main;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

public class Animation {
	
	/**
	 * Variables for cube's animation
	 */
	private final float fallY = 40;
	private final float fallTime = 200;
	
	private final float robotArrivalTime = 200; 
	private final float movementTime = 1000;
	
	private Sprite[][][] cubes;
	
	private Sprite robotSprite = null;
	private Robot robot = null;
	
	public Animation(Sprite[][][] cubes, Sprite robotSprite, Robot robot){
		this.cubes = cubes;
		this.robotSprite = robotSprite;
		this.robot = robot;
	}
	
	public Animation(Sprite[][][] cubes){
		this.cubes = cubes;
	}
	
	/**
	 * Update The list of cubes' sprites
	 * @param cubes
	 */
	public void updateSprite(Sprite[][][] cubes){
		this.cubes = cubes;
	}
	
	/**
	 * Animation for adding or removing a cube
	 * @param line The cube's line
	 * @param column The cube's column
	 * @param level The cube's level
	 * @param add If the cube has to be added or removed
	 * @param block If we have to stop the display before the added or removed cube
	 */
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
								else{
									if(cubes[l][c][lvl] != null)
										Main.window.draw(cubes[l][c][lvl]);
									if(robotSprite != null && robot != null 
											&& l == robot.getPositionX() && c == robot.getPositionY() 
											&& (lvl == GridDisplay.levelMax[l][c] || GridDisplay.levelMax[l][c] == -1))
										Main.window.draw(robotSprite);
								}
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
	
	void displayRobot(int line, int column, int level, boolean add){
		boolean finished = false;
		boolean endDisplay = false;
		
		//Create a frame clock which will be used to control the cube's movement
		Clock frameClock = new Clock();
		//Create a reset clock that will measure the time until 200 milliseconds have passed to finish the animation
		Clock resetClock = new Clock();
		
		int transparency;
		if(add){
			transparency = 55;
			robotSprite.setColor(new Color(255, 255, 255, transparency));
		}
		else
			transparency = 255;

		//Main loop
		while(Main.window.isOpen() && !finished) {
			
			//Draw everything
		    Main.window.clear(Color.WHITE);
		    
	    	for(Sprite s : Main.display.getConstantDisplay())
	    		Main.window.draw(s);
	    	
			for(int l = 0; l<cubes.length && !endDisplay; l++){
				for(int c = 0; c<cubes[0].length && !endDisplay; c++){
						for(int lvl=0; lvl<50 && !endDisplay; lvl++){
							if(lvl == level && l == line && c == column)
								endDisplay = true;
							if(cubes[l][c][lvl] != null)
								Main.window.draw(cubes[l][c][lvl]);
						}
				}
			}
			endDisplay = false;
		    
			Main.window.draw(robotSprite);
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
		    	transparency += (int) deltaMilliseconds;
		    	robotSprite.setColor(new Color(255, 255, 255, transparency));
		    }
		    else{
		    	transparency -= (int) deltaMilliseconds;
		    	robotSprite.setColor(new Color(255, 255, 255, transparency));
		    }

		    //Check if 200 milliseconds have passed on the reset clock
		    if(resetClock.getElapsedTime().asMilliseconds() >= robotArrivalTime)
		    	finished = true;
		}
			
		if(!add)
			robotSprite.setColor(new Color(255, 255, 255, 0));
	}
	
	void moveRobot(Direction direction, int upOrDown){
		boolean finished = false;
		boolean endDisplay = false;
		
		float movementX;
		float movementY;
		
		int nextCellX = robot.getPositionX();
		int nextCellY = robot.getPositionY();
		
		if(upOrDown == 0){
			switch(direction){
				case EAST:
					movementX = (Textures.cellTexture.getSize().x*robotSprite.getScale().x)/2 / this.movementTime;
					movementY = (Textures.cellTexture.getSize().y*robotSprite.getScale().y)/2 / this.movementTime;
					nextCellY++;
					break;
				case NORTH:
					movementX = (Textures.cellTexture.getSize().x*robotSprite.getScale().x)/2 / this.movementTime;
					movementY = -((Textures.cellTexture.getSize().y*robotSprite.getScale().y)/2 / this.movementTime);
					break;
				case SOUTH:
					movementX = -((Textures.cellTexture.getSize().x*robotSprite.getScale().x)/2 / this.movementTime);
					movementY = (Textures.cellTexture.getSize().y*robotSprite.getScale().y)/2 / this.movementTime;
					nextCellX++;
					break;
				case WEST:
					movementX = -((Textures.cellTexture.getSize().x*robotSprite.getScale().x)/2 / this.movementTime);
					movementY = -((Textures.cellTexture.getSize().y*robotSprite.getScale().y)/2 / this.movementTime);
					break;
				default:
					movementX = (Textures.cellTexture.getSize().x*robotSprite.getScale().x)/2 / this.movementTime;
					movementY = (Textures.cellTexture.getSize().y*robotSprite.getScale().y)/2 / this.movementTime;
					break;
			}
		}
		
		System.out.println(movementX);
		
		//Create a frame clock which will be used to control the cube's movement
		Clock frameClock = new Clock();
		//Create a reset clock that will measure the time until 200 milliseconds have passed to finish the animation
		Clock resetClock = new Clock();

		//Main loop
		while(Main.window.isOpen() && !finished) {
			
			//Draw everything
		    Main.window.clear(Color.WHITE);
		    
	    	for(Sprite s : Main.display.getConstantDisplay())
	    		Main.window.draw(s);
	    	
	    	for(int l = 0; l < cubes.length; l++)
				for(int c = 0; c < cubes[0].length; c++)
					for(int level = 0; level < 50; level++){
						if(cubes[l][c][level] != null)
							Main.window.draw(cubes[l][c][level]);
						if(nextCellY == l && nextCellX == c && (level == GridDisplay.levelMax[l][c] || GridDisplay.levelMax[l][c] == -1))
							Main.window.draw(robotSprite);
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
		    robotSprite.move(movementX*deltaMilliseconds, movementY*deltaMilliseconds);
		    System.out.println(movementX*deltaMilliseconds);

		    //Check if 200 milliseconds have passed on the reset clock
		    if(resetClock.getElapsedTime().asMilliseconds() >= movementTime)
		    	finished = true;
		}
	}
}
