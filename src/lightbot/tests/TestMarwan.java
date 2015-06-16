package lightbot.tests;

import java.util.ArrayList;
import java.util.List;

import lightbot.graphics.Textures;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Mouse;
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
			float y = 530;
			s.setPosition(x, y);
			window.draw(s);
		}
	}

	/* TODO: change this method so it takes a variable number of icons to display
	 * 		 integrate this method in a procedure display class
	 */
	
	public static void foo(RenderWindow window) {
		Sprite s = new Sprite(Textures.forwardTexture);
		int currentLine = 0;
		for (int i = 0; i < 12; i++) {

			s.setPosition(730 + 10 + (i % 4) * (50 + 10), 15 + currentLine
					* (s.getTexture().getSize().y + 10));
			window.draw(s);

			if ((i + 1) % 4 == 0)
				currentLine += 1;

		}
	}

	public static void main(String[] args) {
		boolean firstRendering = true;

		Textures.initTextures();

		window = new RenderWindow();
		window.create(new VideoMode(1000, 600), "Hello JSFML!");

		window.setFramerateLimit(30);

		List<Texture> tex = new ArrayList<Texture>();
		tex.add(Textures.forwardTexture);
		tex.add(Textures.turnRightTexture);
		tex.add(Textures.turnLeftTexture);
		tex.add(Textures.lightTexture);
		tex.add(Textures.jumpTexture);
		tex.add(Textures.procedure1Texture);
		tex.add(Textures.procedure2Texture);
		tex.add(Textures.showerTexture);

		RectangleShape mainBox = new RectangleShape(new Vector2f(255, 182));
		mainBox.setFillColor(new Color(79, 179, 201));
		mainBox.setPosition(730, 1*10);
		
		RectangleShape p1Box = new RectangleShape(new Vector2f(255, 182));
		p1Box.setFillColor(new Color(171, 171, 171));
		p1Box.setPosition(730, 2*10+182);
		
		RectangleShape p2Box = new RectangleShape(new Vector2f(255, 182));
		p2Box.setFillColor(new Color(171, 171, 171));
		p2Box.setPosition(730, 3*10+2*182);

		RectangleShape gameBox = new RectangleShape(new Vector2f(710, 475));
		gameBox.setPosition(10,10);
		
		while (window.isOpen()) {
			if (firstRendering) {
				firstRendering = false;
				window.clear(new Color(216, 216, 216));
			}

			window.draw(mainBox);
			window.draw(p1Box);
			window.draw(p2Box);
			window.draw(gameBox);
			renderInstructionsIcons(tex, 125, window);
			foo(window);

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
