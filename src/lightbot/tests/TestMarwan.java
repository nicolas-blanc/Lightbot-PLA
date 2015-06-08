package lightbot.tests;

import java.util.ArrayList;
import java.util.List;

import lightbot.graphics.Textures;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

public class TestMarwan {

	public static RenderWindow window;

	public static void renderInstructionsIcons(List<Texture> textures,
			int posFirstTex, RenderWindow window) {
		for (int i = 0; i < textures.size(); i++) {
			Texture tex = textures.get(i);
			Sprite s = new Sprite(tex);
			float x = posFirstTex + i * (tex.getSize().x + 6);
			float y = 700;
			s.setPosition(x, y);
			window.draw(s);
		}
	}

	public static void main(String[] args) {
		boolean firstRendering = true;

		Textures.initTextures();

		window = new RenderWindow();
		window.create(new VideoMode(1280, 800), "Hello JSFML!");

		window.setFramerateLimit(30);

		List<Texture> tex = new ArrayList<Texture>();
		tex.add(Textures.forwardTexture);
		tex.add(Textures.turnRightTexture);
		tex.add(Textures.turnLeftTexture);
		tex.add(Textures.lightTexture);
		tex.add(Textures.jumpTexture);
		tex.add(Textures.procedure1Texture);
		tex.add(Textures.procedure2Texture);

		while (window.isOpen()) {
			if (firstRendering) {
				firstRendering = false;
				window.clear(new Color(216, 216, 216));
			}
			
			renderInstructionsIcons(tex, 50, window);

			window.display();

			for (Event event : window.pollEvents()) {
				switch (event.type) {
				case CLOSED:
					window.close();
					return;
				case RESIZED:
					window.clear(new Color(216, 216, 216));
				}
			}
		}

		// Sprite forwardSprite = new Sprite(Textures.forwardTexture);
		// forwardSprite.s

	}

}
