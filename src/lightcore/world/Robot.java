package lightcore.world;

import lightcore.simulator.Colour;


public class Robot {
	
	public static Robot wheatley = new Robot(0, 0, Colour.WHITE, CardinalDirection.EAST);
	public static Robot wheatleyClone = new Robot(0, 0, Colour.WHITE, CardinalDirection.EAST);
	
	/* TODO : might change posX and posY to cellNb afterwards */
	private int line;
	private int column;
	private Colour colour;
	private CardinalDirection direction;
	private boolean visible;
	
	/**
	 * Default constructor for a Robot
	 */
	public Robot(){
		this.line = 0;
		this.column = 0;
		colour = Colour.WHITE;
		direction = CardinalDirection.NORTH;
		this.visible = true;
	}
	
	/**
	 * Constructor for a Robot with positions x, y and colour
	 * @param x : column in the grid
	 * @param y : line in the grid
	 * @param colour : initial colour
	 */
	public Robot(int x, int y, Colour colour, CardinalDirection direction){
		this.line = x;
		this.column = y;
		this.colour = colour;
		this.direction = direction;
		this.visible = true;
	}
	
	/**
	 * Sets x and y
	 * @param line
	 * @param column
	 */
	public void setPosition(int line, int column){
		this.line = line;
		this.column = column;
	}
	
	/**
	 * Sets x
	 * @param x
	 */
	public void setLine(int x){
		this.line = x;
	}
	
	/**
	 * Sets y
	 * @param y
	 */
	public void setColumn(int y){
		this.column = y;
	}
	
	/**
	 * Sets direction
	 * @param direction
	 */
	public void setDirection(CardinalDirection direction){
		this.direction = direction;
	}
	
	/**
	 * Colour setter
	 * @param col : the colour to set
	 */
	public void setColour(Colour colour){
		this.colour = colour;
	}
	
	/**
	 * Visibility setter
	 * @param visible : true or false
	 */
	public void setVisibility(boolean visible){
		this.visible = visible;
	}
	
	/**
	 * Gets a robot's X position
	 * @return posX
	 */
	public int getLine(){
		return this.line;
	}
	
	/**
	 * Gets a robot's Y position
	 * @return posY
	 */
	public int getColumn(){
		return this.column;
	}
	
	/**
	 * Gets the robot's colour
	 * @return the colour
	 */
	public Colour getColour(){
		return this.colour;
	}
	
	/**
	 * Gets direction
	 * @param direction
	 */
	public CardinalDirection getDirection(){
		return this.direction;
	}
	
	/**
	 * Gets the visibility of the robot
	 * @return true is the robot is visible
	 */
	public boolean getVisibility(){
		return this.visible;
	}

}
