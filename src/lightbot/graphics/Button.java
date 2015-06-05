package lightbot.graphics;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;

public class Button {
	private Sprite sprite;
	
	public Button(Sprite sprite){
		this.sprite = sprite;
	}
	
	public boolean isInside(Vector2i coord){
		return sprite.getGlobalBounds().contains(coord.x, coord.y);
	}
}
