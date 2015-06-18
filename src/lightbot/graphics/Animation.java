package lightbot.graphics;

import lightbot.LightCore;
import lightbot.system.CardinalDirection;
import lightbot.system.Robot;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.IntRect;
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
	
	private final float robotArrivalTime = 500; 
	private final float movementTime = 1000;
	
	private Sprite[][][] cubes;
	
	private Sprite robotSprite = null;
	private Sprite cloneSprite = null;
	
	
	/********************************************************************************************/
	/*										Constructors										*/
	/********************************************************************************************/
	
	/**
	 * 
	 * @param cubes
	 * @param robotSprite
	 * @param robot
	 */
	public Animation(Sprite[][][] cubes, Sprite robotSprite){
		this.cubes = cubes;
		this.robotSprite = robotSprite;
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
	
	public void updateSprite(Sprite[][][] cubes, Sprite robot){
		this.cubes = cubes;
		this.robotSprite = robot;
	}
	
	public void updateRobot(Sprite robot){
		this.robotSprite = robot;
	}
	
	public void updateClone(Sprite clone){
		this.cloneSprite = clone;
	}
	
	/********************************************************************************************/
	/*										Animations											*/
	/********************************************************************************************/
	
	/**
	 * Print a pillar
	 * @param line The line of the pillar
	 * @param column The column of the pillar
	 */
	public void printPillar(int line, int column){
		if(line > -1 && line < cubes.length && column > -1 && column < cubes.length )
			for(int level=0; level<GridDisplay.maxHeight; level++)
				if(cubes[line][column][level] != null)
					LightCore.window.draw(cubes[line][column][level]);
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
		
		Clock frameClock = new Clock();
		Clock resetClock = new Clock();
		
		float transparency;
		float incrementTransparency = 200 / fallTime;
		
		if(add){
			transparency = 55;
			cubes[line][column][level].move(0, -fallY);
			cubes[line][column][level].setColor(new Color(255, 255, 255, (int)transparency));
		}
		else
			transparency = 255;

		while(LightCore.window.isOpen() && !finished) {
			
		    LightCore.window.clear(Color.WHITE);
		    
	    	for(Drawable s : LightCore.display.getConstantDisplay())
	    		LightCore.window.draw(s);
	    	
	    	if(!block){
			    for(int l = 0; l<cubes.length; l++)
					for(int c = 0; c<cubes[0].length; c++){
						printPillar(l, c);
						if(robotSprite != null && l == Robot.wheatley.getLine() && c == Robot.wheatley.getColumn() && Robot.wheatley.getVisibility())
							LightCore.window.draw(robotSprite);
					}
	    	}
			else{
				for(int l = 0; l<cubes.length && !endDisplay; l++)
					for(int c = 0; c<cubes[0].length && !endDisplay; c++)
						for(int lvl=0; lvl<GridDisplay.maxHeight && !endDisplay; lvl++){
							if(lvl == level && l == line && c == column)
								endDisplay = true;
							else{
								if(cubes[l][c][lvl] != null)
									LightCore.window.draw(cubes[l][c][lvl]);
								if(robotSprite != null 
										&& l == Robot.wheatley.getLine() && c == Robot.wheatley.getColumn() 
										&& (lvl == GridDisplay.levelMax[l][c] || GridDisplay.levelMax[l][c] == -1))
									LightCore.window.draw(robotSprite);
							}
						}
				LightCore.window.draw(cubes[line][column][level]);
				endDisplay = false;
			}
		    
		    LightCore.window.display();

		    //Handle events
		    for(Event event : LightCore.window.pollEvents()) {
		    	switch(event.type) {
		    		case CLOSED:
		    			System.out.println("The user pressed the close button!");
		    			LightCore.window.close();
		    			break;
					default:
						break;
		    	}
		    }

		    Time deltaTime = frameClock.restart();
		    float deltaMilliseconds = deltaTime.asMilliseconds();

		    //Move the cube
		    if(add){
		    	cubes[line][column][level].move(0, deltaMilliseconds * (fallY/fallTime));
		    	transparency += incrementTransparency*deltaMilliseconds;
		    	cubes[line][column][level].setColor(new Color(255, 255, 255, (int)transparency));
		    }
		    else{
		    	cubes[line][column][level].move(0, -deltaMilliseconds * (fallY/fallTime));
		    	transparency -= incrementTransparency*deltaMilliseconds;
		    	cubes[line][column][level].setColor(new Color(255, 255, 255, (int)transparency));
		    }

		    if(resetClock.getElapsedTime().asMilliseconds() >= fallTime)
		    	finished = true;
		}
		
		if(cubes[line][column][level].getPosition() != initialPosition && add)
			cubes[line][column][level].setPosition(initialPosition);
			
		if(!add)
			cubes[line][column][level].setColor(new Color(255, 255, 255, 0));
		else
			cubes[line][column][level].setColor(new Color(255, 255, 255, 255));
	}
	
	/**
	 * Display Wheatley to the screen or its clone
	 * @param line
	 * @param column
	 * @param level
	 * @param add
	 */
	void displayRobot(int line, int column, int level, boolean add, boolean isClone){
		boolean finished = false;
		boolean endDisplay = false;
		
		Clock frameClock = new Clock();
		Clock resetClock = new Clock();
		
		float transparency;
		float incrementTransparency = 200 / robotArrivalTime;
		if(add){
			transparency = 55;
			if(isClone)
				cloneSprite.setColor(new Color(255, 255, 255, (int)transparency));
			else
				robotSprite.setColor(new Color(255, 255, 255, (int)transparency));
		}
		else
			transparency = 255;

		while(LightCore.window.isOpen() && !finished) {
			
		    LightCore.window.clear(Color.WHITE);
		    
	    	for(Drawable s : LightCore.display.getConstantDisplay())
	    		LightCore.window.draw(s);
	    	
			for(int l = 0; l<cubes.length && !endDisplay; l++)
				for(int c = 0; c<cubes[0].length && !endDisplay; c++){
					if(l == line && c == column)
						endDisplay = true;
					printPillar(l, c);
				}
			
			endDisplay = false;
		    
			if(isClone)
				LightCore.window.draw(cloneSprite);
			else
				LightCore.window.draw(robotSprite);
			
		    LightCore.window.display();

		    //Handle events
		    for(Event event : LightCore.window.pollEvents()) {
		    	switch(event.type) {
		    		case CLOSED:
		    			System.out.println("The user pressed the close button!");
		    			LightCore.window.close();
		    			break;
					default:
						break;
		    	}
		    }

		    Time deltaTime = frameClock.restart();
		    float deltaMilliseconds = deltaTime.asMilliseconds();

		    //Change the robot's transparency
		    if(add){
		    	transparency += incrementTransparency*deltaMilliseconds;
		    	if(isClone)
		    		cloneSprite.setColor(new Color(255, 255, 255, (int)transparency));
		    	else
		    		robotSprite.setColor(new Color(255, 255, 255, (int)transparency));
		    }
		    else{
		    	transparency -= incrementTransparency*deltaMilliseconds;
		    	if(isClone)
		    		cloneSprite.setColor(new Color(255, 255, 255, (int)transparency));
		    	else
		    		robotSprite.setColor(new Color(255, 255, 255, (int)transparency));
		    }
		    
		    if(resetClock.getElapsedTime().asMilliseconds() >= robotArrivalTime)
		    	finished = true;
		}
			
		if(!add){
			if(isClone)
				cloneSprite.setColor(new Color(255, 255, 255, 0));
			else
				robotSprite.setColor(new Color(255, 255, 255, 0));
		}
		else{
			if(isClone)
				cloneSprite.setColor(new Color(255, 255, 255, 255));
			else
				robotSprite.setColor(new Color(255, 255, 255, 255));
		}
	}
	
	/**
	 * 
	 * @param direction
	 * @param upOrDown
	 */
	 public void moveRobot(CardinalDirection direction, int upOrDown, boolean isClone){
		boolean finished = false;
		
		float movementX = 0;
		float movementY;
		
		int nextCellX;
		int nextCellY;
		
		int frame = 0;
		boolean rotate = true;
		
		if(isClone){
			nextCellX = Robot.wheatleyClone.getLine();
			nextCellY = Robot.wheatleyClone.getColumn();
		}
		else{
			nextCellX = Robot.wheatley.getLine();
			nextCellY = Robot.wheatley.getColumn();
		}
		
		if(upOrDown == 0){
			switch(direction){
				case EAST:
					movementX = (Textures.cellTexture.getSize().x)/2 / this.movementTime;
					movementY = (Textures.cellTexture.getSize().y)/2 / this.movementTime;
					nextCellY++;
					break;
				case NORTH:
					movementX = (Textures.cellTexture.getSize().x)/2 / this.movementTime;
					movementY = -((Textures.cellTexture.getSize().y)/2 / this.movementTime);
					
					if(isClone){
						nextCellX = Robot.wheatleyClone.getLine()-1;
						nextCellY = Robot.wheatleyClone.getColumn()+1;
					}
					else{
						nextCellX = Robot.wheatley.getLine()-1;
						nextCellY = Robot.wheatley.getColumn()+1;
					}
					
					break;
				case SOUTH:
					movementX = -((Textures.cellTexture.getSize().x)/2 / this.movementTime);
					movementY = (Textures.cellTexture.getSize().y)/2 / this.movementTime;
					//nextCellY++;
					nextCellX++;
					break;
				case WEST:
					movementX = -((Textures.cellTexture.getSize().x)/2 / this.movementTime);
					movementY = -((Textures.cellTexture.getSize().y)/2 / this.movementTime);
					break;
				default:
					movementX = (Textures.cellTexture.getSize().x)/2 / this.movementTime;
					movementY = (Textures.cellTexture.getSize().y)/2 / this.movementTime;
					break;
			}
		}
		else if(upOrDown == 1){
			movementY = (Textures.cubeTextureWhite.getSize().y-Textures.cellTexture.getSize().y) / this.movementTime;
		}
		else
			movementY = -(Textures.cubeTextureWhite.getSize().y-Textures.cellTexture.getSize().y) / this.movementTime;
		
		Clock frameClock = new Clock();
		Clock resetClock = new Clock();
		Clock animClock = new Clock();

		while(LightCore.window.isOpen() && !finished) {
			
		    LightCore.window.clear(Color.WHITE);
		    
	    	for(Drawable s : LightCore.display.getConstantDisplay())
	    		LightCore.window.draw(s);
	    	
	    	for(int l = 0; l < cubes.length; l++){
				for(int c = 0; c < cubes[0].length; c++){
					
					// animate the clone
					if(isClone){
						if(Robot.wheatley.getVisibility() && Robot.wheatley.getLine() == l && Robot.wheatley.getColumn() == c){
							printPillar(l, c);
							LightCore.window.draw(robotSprite);
						}
						
						if(!(nextCellY > Robot.wheatleyClone.getColumn() 
								&& (l >= nextCellX && l <= Robot.wheatleyClone.getLine())
								&& (c >= nextCellY && c <= Robot.wheatleyClone.getColumn())) 
								&& (l != nextCellX || c != nextCellY)){
							if(!Robot.wheatley.getVisibility() || (Robot.wheatley.getVisibility() && Robot.wheatley.getLine() != l && Robot.wheatley.getColumn() != c))
								printPillar(l, c);
						}
						
						if(l == Robot.wheatleyClone.getLine() && c == Robot.wheatleyClone.getColumn()){
							
							if(nextCellY > Robot.wheatleyClone.getColumn() && nextCellX < Robot.wheatleyClone.getLine()){
								LightCore.window.draw(cloneSprite);
								
								for(int lin = nextCellX; lin<=Robot.wheatleyClone.getLine(); lin++){
									for(int cin = nextCellY; cin<cubes[0].length; cin++){
										if(!(lin == nextCellX && cin < nextCellY)){
											printPillar(lin, cin);
										}
									}
								}
							}
							else{
								printPillar(nextCellX, nextCellY);
								LightCore.window.draw(cloneSprite);
							}
						}
					}
					else{
						if(Robot.wheatleyClone.getVisibility() && Robot.wheatleyClone.getLine() == l && Robot.wheatleyClone.getColumn() == c){
							printPillar(l, c);
							LightCore.window.draw(cloneSprite);
						}
						
						if(!(nextCellY > Robot.wheatley.getColumn() 
								&& (l >= nextCellX && l <= Robot.wheatley.getLine())
								&& (c >= nextCellY && c <= Robot.wheatley.getColumn())) 
								&& (l != nextCellX || c != nextCellY)){
							if(!Robot.wheatleyClone.getVisibility() || (Robot.wheatleyClone.getVisibility() && Robot.wheatleyClone.getLine() != l && Robot.wheatleyClone.getColumn() != c))
								printPillar(l, c);
						}
						
						if(l == Robot.wheatley.getLine() && c == Robot.wheatley.getColumn()){
							
							if(nextCellY > Robot.wheatley.getColumn() && nextCellX < Robot.wheatley.getLine()){
								LightCore.window.draw(robotSprite);
								
								for(int lin = nextCellX; lin<=Robot.wheatley.getLine(); lin++){
									for(int cin = nextCellY; cin<cubes[0].length; cin++){
										if(!(lin == nextCellX && cin < nextCellY)){
											printPillar(lin, cin);
										}
									}
								}
							}
							else{
								printPillar(nextCellX, nextCellY);
								LightCore.window.draw(robotSprite);
							}
						}
					}
				}
	    	}
	    	
		    LightCore.window.display();

		    //Handle events
		    for(Event event : LightCore.window.pollEvents()) {
		    	switch(event.type) {
		    		case CLOSED:
		    			System.out.println("The user pressed the close button!");
		    			LightCore.window.close();
		    			break;
					default:
						break;
		    	}
		    }
		    
		    if(animClock.getElapsedTime().asMilliseconds() >= 50 && rotate && upOrDown == 0){
				animClock.restart();
				
				frame++;
				if(frame > 14)
					frame = 0;
			
				int frameRow = frame / 5;
				int frameCol = frame % 5;
				robotSprite.setTextureRect(new IntRect(frameCol * 38, frameRow * 50, 38, 50));
				
				if(frame == 0)
					rotate = false;
			}

		    Time deltaTime = frameClock.restart();
		    float deltaMilliseconds = deltaTime.asMilliseconds();
			    
		    //Move the robot
		    if(isClone)
		    	cloneSprite.move(movementX*deltaMilliseconds, movementY*deltaMilliseconds);
		    else
		    	robotSprite.move(movementX*deltaMilliseconds, movementY*deltaMilliseconds);

		    if(resetClock.getElapsedTime().asMilliseconds() >= movementTime)
		    	finished = true;
		}
	}
	
	public void animeBlackHole(int line, int column, int level, boolean open){
		boolean finished = false;
		boolean endDisplay = false;
		Sprite blackHole;
		
		int frame;
		
		if(open)
			frame = 0;
		else
			frame = 19;
		
		int frameRow = frame / 5;
		int frameCol = frame % 5;
		
		Clock animClock = new Clock();
		
		Vector2f initialPosition = cubes[line][column][level].getPosition();
		System.out.println(initialPosition.x + " " + initialPosition.y);
		
		cubes[line][column][level].setTexture(Textures.cubeTextureWhite);
		
		
		blackHole = new Sprite(Textures.teleportAnimTexture);
		blackHole.setTextureRect(new IntRect(frameCol * Textures.cellTexture.getSize().x, 
				frameRow * Textures.cellTexture.getSize().y, 
				Textures.cellTexture.getSize().x, 
				Textures.cellTexture.getSize().y));
		blackHole.setOrigin(cubes[line][column][level].getOrigin());
		blackHole.setPosition(initialPosition);
		

		while(LightCore.window.isOpen() && !finished) {
			
		    LightCore.window.clear(Color.WHITE);
		    
	    	for(Drawable s : LightCore.display.getConstantDisplay())
	    		LightCore.window.draw(s);
	    	
			for(int l = 0; l<cubes.length && !endDisplay; l++)
				for(int c = 0; c<cubes[0].length && !endDisplay; c++){
					printPillar(l, c);
					if(l == line && c == column)
						LightCore.window.draw(blackHole);
					if(robotSprite != null && l == Robot.wheatley.getLine() && c == Robot.wheatley.getColumn() && Robot.wheatley.getVisibility())
						LightCore.window.draw(robotSprite);
					if(cloneSprite != null && l == Robot.wheatleyClone.getLine() && c == Robot.wheatleyClone.getColumn() && Robot.wheatleyClone.getVisibility())
						LightCore.window.draw(cloneSprite);
				}
			
		    LightCore.window.display();

		    //Handle events
		    for(Event event : LightCore.window.pollEvents()) {
		    	switch(event.type) {
		    		case CLOSED:
		    			System.out.println("The user pressed the close button!");
		    			LightCore.window.close();
		    			break;
					default:
						break;
		    	}
		    }
		    
		    if(animClock.getElapsedTime().asMilliseconds() >= 17){
				animClock.restart();
				
				if(open){
					frame++;
					if(frame > 19)
						frame = 0;
				}
				else{
					frame--;
					if(frame < 0)
						frame = 19;
				}
				
				frameRow = frame / 5;
				frameCol = frame % 5;
				blackHole.setTextureRect(new IntRect(frameCol * Textures.cellTexture.getSize().x, 
						frameRow * Textures.cellTexture.getSize().y, 
						Textures.cellTexture.getSize().x, 
						Textures.cellTexture.getSize().y));
				
				if(open){
					if(frame == 0)
						finished = true;
				}
				else{
					if(frame == 19)
						finished = true;
				}
			}
		}
	}
}
