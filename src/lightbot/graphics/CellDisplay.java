package lightbot.graphics;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

public class CellDisplay {
	
	public static Sprite createCell(int line, int column){
		Sprite toAdd = new Sprite(Textures.cellTexture);
		
		float decalX = Textures.cellTexture.getSize().x / 2;
		float decalY = Textures.cellTexture.getSize().y / 2;
		
		Vector2f decal;
		if(line == column)
			decal = new Vector2f(-1, 
					line*2*(decalY-1) 
						+ (Math.round(Textures.cubeTexture.getSize().y) 
						- (Math.round(Textures.cellTexture.getSize().y))));
		else
			decal = new Vector2f((column-line)*(decalX-1),
					(line+column)*(decalY-1)
					+ (Math.round(Textures.cubeTexture.getSize().y) 
					- (Math.round(Textures.cellTexture.getSize().y))));
		
		Vector2f origin = Vector2f.div(new Vector2f(Textures.cubeTexture.getSize()), 2);
		
		toAdd.scale(new Vector2f((float)0.5, (float)0.5));
		toAdd.setOrigin(Vector2f.sub(origin, decal));
		return toAdd;
	}
	
}