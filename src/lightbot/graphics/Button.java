package lightbot.graphics;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;

public class Button {
	
	private Sprite sprite;
	private Boolean isEnable;
	
	public Button(Sprite sprite){
		this.sprite = sprite;
		this.isEnable = true;
	}
	
	public void disable(){
		this.isEnable = false;
	}
	
	public void enable(){
		this.isEnable = true;
	}
	
	public boolean isInside(Vector2i coord){
		return (isEnable && sprite.getGlobalBounds().contains(coord.x, coord.y));
	}
}
