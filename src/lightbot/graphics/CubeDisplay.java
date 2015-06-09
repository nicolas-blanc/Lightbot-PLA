package lightbot.graphics;

import lightbot.system.Colour;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class CubeDisplay {
	
	private int line;
	private int column;
	private int level;
	private Colour colour;
	
	// The texture of the cube
	public Texture currentTexture;
	
	public CubeDisplay(int line, int column, int level, Colour colour){
		this.line = line;
		this.column = column;
		this.level = level;
		this.colour = colour;
		
		switch(this.colour){
			case WHITE:
				this.currentTexture = Textures.cubeTexture;
				break;
				
			case GREEN:
				// TODO change
				this.currentTexture = Textures.cubeTextureBlue;
				break;
				
			case RED:
				this.currentTexture = Textures.cubeTextureRed;
				break;
				
			case YELLOW:
				this.currentTexture = Textures.cubeTextureYellow;
				break;
				
			case TELEPORT:
				this.currentTexture = Textures.cubeTextureTeleport;
				break;
				
			default:
				this.currentTexture = Textures.cubeTexture;
				break;
		}		
	}
	
	/**
	 * Initialize a cube from the data given at the instantiation
	 * @return A sprite of this cube
	 */
	public Sprite createCube(){
		Sprite toAdd = new Sprite(currentTexture);
		
		float decalX = Textures.cellTexture.getSize().x / 2;
		float decalY = Textures.cellTexture.getSize().y / 2;
		float sizeCubeY = Textures.cubeTexture.getSize().y;
		
		Vector2f decal;
		if(this.level == 0){
			if(this.line == this.column)
				decal = new Vector2f(0, this.line*2*(decalY));
			else
				decal = new Vector2f((this.column-this.line)*(decalX), (this.line+this.column)*(decalY));
		}
		else{
			if(this.line == this.column)
				decal = new Vector2f(0, (this.line+1)*2*(decalY)-(sizeCubeY-2)-(this.level-1)*decalY);
			else
				decal = new Vector2f((this.column-this.line)*(decalX), (this.line+this.column+2)*(decalY)-(sizeCubeY-2)-(this.level-1)*decalY);
		}
		
		Vector2f origin = Vector2f.div(new Vector2f(Textures.cubeTexture.getSize()), 2);
		
		toAdd.scale(new Vector2f((float)1, (float)1));
		toAdd.setOrigin(Vector2f.sub(origin, decal));
		return toAdd;
	}
	
	public Colour getColour(){return this.colour;}
}
