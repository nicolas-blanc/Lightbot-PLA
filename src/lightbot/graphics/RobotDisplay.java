package lightbot.graphics;

import lightbot.system.CardinalDirection;
import lightbot.system.Colour;
import lightbot.system.Robot;
import lightbot.tests.Main;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class RobotDisplay implements DisplayPrimitive{
	
	private Robot robot;
	
	private int line;
	private int column;
	private int level;
	
	private int originX;
	private int originY;
	
	private Colour colour;
	private CardinalDirection direction;
	private int transparency;
	
	private final float robotHeight = 20;
	
	// The texture of the robot
	public Texture currentTexture;
	public Sprite robotSprite;
	
	/**
	 * Constructor
	 * @param robot
	 * @param transparency
	 */
	public RobotDisplay(Robot robot, int transparency, int originX, int originY){
		this.originX = originX;
		this.originY = originY;
		updateRobot(robot, transparency);
	}
	
	/**
	 * Update the robot with a new robot and a new transparency
	 * @param robot
	 * @param transparency
	 */
	public void updateRobot(Robot robot, int transparency){
		this.robot = robot;
		
		this.line = this.robot.getLine();
		this.column = this.robot.getColumn();
		this.level = GridDisplay.levelMax[this.line][this.column];
		this.colour = this.robot.getColour();
		this.direction = this.robot.getDirection();
		
		this.transparency = transparency;
		
		System.out.println("Line : " + line + ", column : " + column + ", level : " + level + " " + direction.toString());
		
		
		switch(this.robot.getDirection()){
			case EAST:
				currentTexture = Textures.robotEast;
				break;
			case NORTH:
				currentTexture = Textures.robotNorth;
				break;
			case SOUTH:
				currentTexture = Textures.robotSouth;
				break;
			case WEST:
				currentTexture = Textures.robotWest;
				break;
			default:
				currentTexture = Textures.robotNorth;
				break;
		}
		
		this.robotSprite = create();
	}
	
	public void print(){
		Main.window.draw(robotSprite);
	}
	
	/**
	 * Initialize a robot from the data given at the instantiation
	 * @return A sprite of this robot
	 */
	public Sprite create(){
		Sprite robot = new Sprite(currentTexture);
		
		float decalX = Textures.cellTexture.getSize().x / 2;
		float decalY = Textures.cellTexture.getSize().y / 2;
		float sizeCubeY = Textures.cubeTexture.getSize().y;
		
		float decalXRobot = (Textures.cellTexture.getSize().x - this.currentTexture.getSize().x)/2;
		
		Vector2f decal;
		if(this.level == 0){
			if(this.line == this.column)
				decal = new Vector2f(-1+decalXRobot, this.line*2*(decalY-1)-this.robotHeight);
			else
				decal = new Vector2f((this.column-this.line)*(decalX-1)+decalXRobot, (this.line+this.column)*(decalY-1)-this.robotHeight);
		}
		else{
			if(this.line == this.column)
				decal = new Vector2f(-1+decalXRobot, (this.line+1)*2*(decalY-1)-(sizeCubeY-2)-(this.level-1)*decalY-this.robotHeight);
			else
				decal = new Vector2f((this.column-this.line)*(decalX-1)+decalXRobot, (this.line+this.column+2)*(decalY-1)-(sizeCubeY-2)-(this.level-1)*decalY-this.robotHeight);
		}

		Vector2f origin = Vector2f.div(new Vector2f(Textures.cubeTexture.getSize()), 2);
		
		robot.scale(new Vector2f(1, 1));
		robot.setOrigin(Vector2f.sub(origin, decal));
		robot.setColor(new Color(255, 255, 255, this.transparency));
		robot.setPosition(originX, originY);
		return robot;
	}
	
	public Sprite getSprite(){return this.robotSprite;}
	
	public void setTransparency(int transparency){
		this.robotSprite.setColor(new Color(255, 255, 255, transparency));
	}
	
	
	// TODO
	/************ Maybe to suppress *************/
	public void turnLeft(){
		if(currentTexture == Textures.robotNorth)
			currentTexture = Textures.robotWest;
		else if(currentTexture == Textures.robotWest)
			currentTexture = Textures.robotSouth;
		else if(currentTexture == Textures.robotSouth)
			currentTexture = Textures.robotEast;
		else if(currentTexture == Textures.robotEast)
			currentTexture = Textures.robotNorth;
		
		this.robotSprite.setTexture(currentTexture);
	}
	
	public void turnRight(){
		if(currentTexture == Textures.robotNorth)
			currentTexture = Textures.robotEast;
		else if(currentTexture == Textures.robotEast)
			currentTexture = Textures.robotSouth;
		else if(currentTexture == Textures.robotSouth)
			currentTexture = Textures.robotWest;
		else if(currentTexture == Textures.robotWest)
			currentTexture = Textures.robotNorth;
		
		this.robotSprite.setTexture(currentTexture);
	}
	
	/*public void setLine(int line){
		this.line = line;
	}
	
	public void setColumn(int column){
		this.column = column;
	}*/
	
	/***************************************************/
}
