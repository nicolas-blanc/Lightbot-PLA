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
				
			default:
				this.currentTexture = Textures.cubeTexture;
				break;
		}		
	}
	
	public Sprite createCube(){
		Sprite toAdd = new Sprite(currentTexture);
		
		float decalX = Textures.cellTexture.getSize().x / 2;
		float decalY = Textures.cellTexture.getSize().y / 2;
		float sizeCubeY = Textures.cubeTexture.getSize().y;
		
		Vector2f decal;
		if(this.level == 0){
			if(this.line == this.column)
				decal = new Vector2f(-1, this.line*2*(decalY-1));
			else
				decal = new Vector2f((this.column-this.line)*(decalX-1), (this.line+this.column)*(decalY-1));
		}
		else{
			if(this.line == this.column)
				decal = new Vector2f(-1, (this.line+1)*2*(decalY-1)-(sizeCubeY-2)-(this.level-1)*decalY);
			else
				decal = new Vector2f((this.column-this.line)*(decalX-1), (this.line+this.column+2)*(decalY-1)-(sizeCubeY-2)-(this.level-1)*decalY);
		}
		
		Vector2f origin = Vector2f.div(new Vector2f(Textures.cubeTexture.getSize()), 2);
		
		toAdd.scale(new Vector2f((float)0.5, (float)0.5));
		toAdd.setOrigin(Vector2f.sub(origin, decal));		
		return toAdd;
	}
}
