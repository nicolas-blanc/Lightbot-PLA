package lightbot.tests;

import lightbot.system.Colour;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

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
		
		//Main loop
		while (window.isOpen()) {
			if (firstRendering) {
				firstRendering = false;
				window.clear(new Color(216, 216, 216));
			}
		    window.display();
		    window.draw(centerBox);
		    
		    //Events handling
			for (Event event : window.pollEvents()) {
				switch (event.type) {
				case CLOSED:
					window.close();
					break;
				default:
					break;
				}
			}
		}
	}
}
