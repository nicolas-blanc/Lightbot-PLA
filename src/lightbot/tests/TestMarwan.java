package lightbot.tests;

import lightbot.graphics.Textures;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

public class TestMarwan {

	public static RenderWindow window;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Textures.initTextures();

		window = new RenderWindow();
		window.create(new VideoMode(1280, 800), "Hello JSFML!");

		window.setFramerateLimit(30);
		

		while (window.isOpen()) {
			window.clear();
			
			window.display();
			
			for (Event event : window.pollEvents()) {
				switch (event.type) {
				case CLOSED:
					window.close();
					return;
				}
			}
		}

		// Sprite forwardSprite = new Sprite(Textures.forwardTexture);
		// forwardSprite.s

	}

}
