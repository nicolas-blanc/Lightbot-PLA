package lightbot.system.world;

import lightbot.system.Colour;


public class Cell {
	
	private int height;
	private Colour colour;
	private int posX;
	private int posY;
	
	/**
	 * Default constructor for a Cell
	 */
	public Cell(){
		this.height = -1;
		this.colour = Colour.WHITE;
	}
	
	/**
	 * Constructor for a Cell with level and colour
	 * @param level
	 * @param col
	 */
	public Cell(int level, Colour colour, int posX, int posY){
		this.height = level;
		this.colour = colour;
		this.posX = posX;
		this.posY = posY;
	}
	
	public void setHeight(int level){
		this.height = level;
	}
	
	public void setColour(Colour colour){
		this.colour = colour;
	}
	
	public void setPosX(int posX){
		this.posX = posX;
	}
	
	public void setPosY(int posY){
		this.posY = posY;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public Colour getColour(){
		return this.colour;
	}
	
	public int getposX(){
		return this.posX;
	}
	
	public int getposY(){
		return this.posY;
	}
	
}


