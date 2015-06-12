package lightbot.tests;

import lightbot.graphics.Button;
import lightbot.graphics.Textures;
import lightbot.system.Colour;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;

public class TestMenu {

	public static RenderWindow window;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean firstRendering = true;
		window = new RenderWindow();
		window.create(new VideoMode(1000, 600), "   LIGHTCORE  -  Menu");
		
		window.setFramerateLimit(60);
		
		RectangleShape centerBox = new RectangleShape(new Vector2f(400, 450));
		centerBox.setFillColor(new Color(235,235,235));
		centerBox.setPosition(300, 100);
		
		Textures.initTextures();
		Sprite buttonBg = new Sprite(Textures.menuButton);
		buttonBg.setPosition(310, 290);
		
		Button button = new Button(buttonBg);		
		
		//Main loop
		while (window.isOpen()) {
			if (firstRendering) {
				firstRendering = false;
				window.clear(new Color(216, 216, 216));
			}
		    window.display();
		    window.draw(centerBox);
		    window.draw(buttonBg);
		    
		    //Events handling
			for (Event event : window.pollEvents()) {
				switch (event.type) {
				case CLOSED:
					window.close();
					break;
				case MOUSE_BUTTON_PRESSED:
					MouseButtonEvent mouse = event.asMouseButtonEvent();
					if(mouse.button == Mouse.Button.LEFT && button.isInside(mouse.position)){
						window.close();
					}
					break;
				default:
					break;
				}
			}
		}
	}
}
