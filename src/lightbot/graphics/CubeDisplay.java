package lightbot.graphics;

import lightbot.system.world.cell.Cell;
import lightbot.system.world.cell.ColoredCell;
import lightbot.system.world.cell.LightableCell;
import lightbot.system.world.cell.NormalCell;
import lightbot.system.world.cell.TeleportCell;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class CubeDisplay implements DisplayPrimitive{
	
	private int line;
	private int column;
	private int level;
	
	// The texture of the cube
	public Texture currentTexture;
	
	public CubeDisplay(Cell cell){
		this.line = cell.getX();
		this.column = cell.getY();
		this.level = cell.getHeight();
		
		if(cell instanceof ColoredCell){
			switch(((ColoredCell)cell).getColour()){
				case GREEN:
					this.currentTexture = Textures.cubeTextureGreen;
					break;
				case RED:
					this.currentTexture = Textures.cubeTextureRed;
					break;
				case YELLOW:
					this.currentTexture = Textures.cubeTextureYellow;
					break;
				default:
					this.currentTexture = Textures.cubeTextureWhite;
					break;
			}
		}
		else if(cell instanceof LightableCell)
			this.currentTexture = Textures.cubeTextureGreen; // TODO change this
		else if(cell instanceof NormalCell)
			this.currentTexture = Textures.cubeTextureWhite;
		else if(cell instanceof TeleportCell)
			this.currentTexture = Textures.cubeTextureTeleportWhite;
		else //TODO change this
			this.currentTexture = Textures.cubeTextureWhite;
			
	}
	
	/**
	 * Initialize a cube from the data given at the instantiation
	 * @return A sprite of this cube
	 */
	public Sprite create(){
		Sprite toAdd = new Sprite(currentTexture);
		
		float decalX = Textures.cellTexture.getSize().x / 2;
		float decalY = Textures.cellTexture.getSize().y / 2;
		float sizeCubeY = Textures.cubeTextureWhite.getSize().y;
		
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
		
		Vector2f origin = Vector2f.div(new Vector2f(Textures.cubeTextureWhite.getSize()), 2);
		
		toAdd.scale(new Vector2f((float)1, (float)1));
		toAdd.setOrigin(Vector2f.sub(origin, decal));
		return toAdd;
	}
}
