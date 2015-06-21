package lightcore.graphics;

import lightcore.world.cell.Cell;
import lightcore.world.cell.ColoredCell;
import lightcore.world.cell.LightableCell;
import lightcore.world.cell.NormalCell;
import lightcore.world.cell.TeleportCell;

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
				case BLUE:
					this.currentTexture = Textures.cubeTextureBlue;
					break;
				case ORANGE:
					this.currentTexture = Textures.cubeTextureOrange;
					break;
				case PURPLE:
					this.currentTexture = Textures.cubeTexturePurple;
					break;
				case RED:
					this.currentTexture = Textures.cubeTextureRed;
					break;
				default:
					this.currentTexture = Textures.cubeTextureWhite;
					break;
			}
		}
		else if(cell instanceof LightableCell){
			if(((LightableCell)cell).isLightON())
				this.currentTexture = Textures.cubeTextureYellow;
			else
				this.currentTexture = Textures.cubeTextureGreen;
		}
		else if(cell instanceof NormalCell)
			this.currentTexture = Textures.cubeTextureWhite;
		else if(cell instanceof TeleportCell){
			switch(((TeleportCell)cell).getColour()){
				case TELEPORT:
					this.currentTexture = Textures.cubeTextureTeleportWhite;
					break;
				case TELEPORTBLUE:
					this.currentTexture = Textures.cubeTextureTeleportBlue;
					break;
				case TELEPORTGREEN:
					this.currentTexture = Textures.cubeTextureTeleportGreen;
					break;
				case TELEPORTORANGE:
					this.currentTexture = Textures.cubeTextureTeleportOrange;
					break;
				case TELEPORTPURPLE:
					this.currentTexture = Textures.cubeTextureTeleportPurple;
					break;
				case TELEPORTRED:
					this.currentTexture = Textures.cubeTextureTeleportRed;
					break;
				case TELEPORTYELLOW:
					this.currentTexture = Textures.cubeTextureTeleportYellow;
					break;
				default:
					this.currentTexture = Textures.cubeTextureTeleportWhite;
					break;
			}
		}
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
