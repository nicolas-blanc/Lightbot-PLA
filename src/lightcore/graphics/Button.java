package lightcore.graphics;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

public class Button {

	private Sprite sprite;
	private Boolean isEnable;
	private int id;

	private Texture pushOn;
	private Texture pushOff;

	private boolean pushed;
	private boolean hovered = false;

	public Button(Sprite sprite, Texture pushOn, Texture pushOff) {
		this.sprite = sprite;
		this.isEnable = true;

		this.pushOn = pushOn;
		this.pushOff = pushOff;
		this.pushed = false;
	}

	public void changeTexture() {
		if (pushed) {
			if (pushOff != null)
				sprite.setTexture(pushOff);
			sprite.setColor(new Color(255, 255, 255, 255));
		} else {
			if (pushOn != null)
				sprite.setTexture(pushOn);
			sprite.setColor(new Color(150, 150, 150, 255));
		}
		pushed = !pushed;
	}

	public Color getColor() {
		return sprite.getColor();
	}

	public void setColor(int R, int G, int B, int alpha) {
		sprite.setColor(new Color(R, G, B, alpha));
	}
	
	public void setColor(Color color) {
		sprite.setColor(color);
	}

	public void reset() {
		if (pushOff != null)
			sprite.setTexture(pushOff);
		sprite.setColor(new Color(255, 255, 255, 255));
		pushed = false;
	}

	public Sprite getSprite() {
		return this.sprite;
	}

	public int getId() {
		return this.id;
	}

	public void setTexture(Texture texture) {
		this.sprite.setTexture(texture);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void disable() {
		sprite.setColor(new Color(180, 180, 180, 255));
		this.isEnable = false;
	}

	public void enable() {
		if (pushed)
			sprite.setColor(new Color(220, 220, 220, 255));
		else
			sprite.setColor(new Color(255, 255, 255, 255));
		this.isEnable = true;
	}

	public boolean isInside(Vector2i coord) {
		return (isEnable && sprite.getGlobalBounds().contains(coord.x, coord.y));
	}

	public void changeOnHover(Vector2i coord) {
		if (isInside(coord) && !pushed) {
			if (hovered) {
				if (pushOn != null)
					sprite.setTexture(pushOn);
				hovered = false;
			}
		} else {
			if (!hovered) {
				if (pushOff != null)
					sprite.setTexture(pushOff);
				hovered = true;
			}
		}
	}
}
