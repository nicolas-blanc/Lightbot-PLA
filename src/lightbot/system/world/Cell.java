package lightbot.system.world;

import lightbot.system.Colour;


public class Cell {
	
	private int height;
	private Colour colour;
	private int posX;
	private int posY;
	private boolean lightable;
	private boolean lightOn;
	/**
	 * Default constructor for a Cell
	 */
	public Cell(){
		this.height = -1;
		this.colour = Colour.WHITE;
		this.lightable = false;
		this.lightOn = false;
	}
	
	/**
	 * Constructor for a Cell with level and colour
	 * @param level
	 * @param colour
	 * @param 
	 */
	public Cell(int level, Colour colour, int posX, int posY, boolean lightable){
		this.height = level;
		this.colour = colour;
		this.posX = posX;
		this.posY = posY;
		this.lightable = lightable;
		this.lightOn = false;
		if(lightable == true){
			this.setColour(Colour.GREEN);
		}
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

	public void setLight(boolean lightOn){
		this.lightOn = lightOn;
	}

	public void setLightable(boolean lightable){
		this.lightable = lightable;
		if(lightable == true){
			this.setColour(Colour.GREEN);
		}
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
	
	public boolean getLightOn(){
		return this.lightOn;
	}	

	public boolean getLightable(){
		return this.lightable;
	}
}


