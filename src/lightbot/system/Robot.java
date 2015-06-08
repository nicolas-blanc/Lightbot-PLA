package lightbot.system;


public class Robot {
	
	public static Robot wheatley = new Robot(0, 0, Colour.WHITE, CardinalDirection.EAST);
	
	/* TODO : might change posX and posY to cellNb afterwards */
	private int posX;
	private int posY;
	private Colour colour;
	private CardinalDirection direction;
	
	/**
	 * Default constructor for a Robot
	 */
	public Robot(){
		this.posX = 0;
		this.posY = 0;
		colour = Colour.WHITE;
		direction = CardinalDirection.NORTH;
	}
	
	/**
	 * Constructor for a Robot with positions x, y and colour
	 * @param x : column in the grid
	 * @param y : line in the grid
	 * @param colour : initial colour
	 */
	public Robot(int x, int y, Colour colour, CardinalDirection direction){
		this.posX = x;
		this.posY = y;
		this.colour = colour;
		this.direction = direction;
	}
	
	/**
	 * Sets x and y
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		this.posX = x;
		this.posY = y;
	}
	
	/**
	 * Sets x
	 * @param x
	 */
	public void setPositionX(int x){
		this.posX = x;
	}
	
	/**
	 * Sets y
	 * @param y
	 */
	public void setPositionY(int y){
		this.posY = y;
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
	 * Gets a robot's X position
	 * @return posX
	 */
	public int getPositionX(){
		return this.posX;
	}
	
	/**
	 * Gets a robot's Y position
	 * @return posY
	 */
	public int getPositionY(){
		return this.posY;
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
	
	

}
