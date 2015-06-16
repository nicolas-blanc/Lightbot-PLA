package lightbot.tests;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import lightbot.graphics.Button;
import lightbot.graphics.Display;
import lightbot.graphics.DisplayMode;
import lightbot.graphics.Editor;
import lightbot.graphics.MenuDisplay;
import lightbot.graphics.Textures;
import lightbot.graphics.Editor.LoadSaveFilter;
import lightbot.system.Colour;
import lightbot.system.ParserJSON;
import lightbot.system.world.Grid;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.window.event.MouseEvent;

public class TestMenu {

	public static RenderWindow window;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		window = new RenderWindow();
		window.create(new VideoMode(1000, 600), "   LIGHTCORE  -  Menu");
		window.setFramerateLimit(60);
		
		//Editor ed = new Editor(8);
		
		
		/*while(window.isOpen()) {
			ed.display();
			
		}*/
				
		/*Textures.initTextures();
		
		Sprite menuLogo = new Sprite(Textures.menuLogo);
		Sprite menuJouer = new Sprite(Textures.menuJouer);
		Sprite menuQuitter = new Sprite(Textures.menuQuitter);
		Sprite menuEditeur = new Sprite(Textures.menuEditeur);
		Sprite menuCharger = new Sprite(Textures.menuCharger);
		Sprite menuBg = new Sprite(Textures.menuBg);
		
		Button buttonLogo = new Button(menuLogo, null, null);	
		Button buttonJouer = new Button(menuJouer, Textures.menuCharger, Textures.menuJouer);
		Button buttonQuitter = new Button(menuQuitter, null, null);
		Button buttonEditeur = new Button(menuEditeur, null, null);
		Button buttonCharger = new Button(menuCharger, null, null);
		
		menuLogo.setPosition(35, 27);
		menuJouer.setPosition(202, 206);
		menuEditeur.setPosition(202, 281);
		menuCharger.setPosition(202, 354);
		menuQuitter.setPosition(202, 430);
		
		//Main loop
		while (window.isOpen()) {
		    window.display();
		    window.draw(menuBg);
		    window.draw(menuLogo);
		    window.draw(menuJouer);
		    window.draw(menuEditeur);
		    window.draw(menuCharger);
		    window.draw(menuQuitter);
		    
		    //Events handling
			for (Event event : window.pollEvents()) {
				switch (event.type) {
				case CLOSED:
					window.close();
					break;
				case MOUSE_MOVED:
					MouseEvent mouse1 = event.asMouseEvent();
					buttonJouer.changeOnHover(mouse1.position);
					break;
				case MOUSE_BUTTON_PRESSED:
					MouseButtonEvent mouse = event.asMouseButtonEvent();
					if(mouse.button == Mouse.Button.LEFT){
						// Éditeur
						if(buttonEditeur.isInside(mouse.position)){
							int sizeInt = -1;
							String size = null;
							do{
								size = JOptionPane.showInputDialog(null, "Veuillez indiquer la taille de votre grille :", "Éditeur", JOptionPane.QUESTION_MESSAGE);
								if(size != null){
									sizeInt = (size == null)?null : Integer.parseInt(size);
									if(sizeInt<1 || sizeInt>8){
										JOptionPane.showMessageDialog(null, "Merci de saisir une taille comprise entre 1 et 8", "Éditeur", JOptionPane.ERROR_MESSAGE);
									}else{
										JOptionPane.showMessageDialog(null, "Vous allez créer une grille " + sizeInt + "x" + sizeInt, "Identité", JOptionPane.INFORMATION_MESSAGE);
									}
								}
							} while((sizeInt<1 || sizeInt>8) && size != null);
						}
						// Charger
						if(buttonCharger.isInside(mouse.position)){
							JFileChooser dialog = dialog = new JFileChooser();
							
							if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
								  File file = dialog.getSelectedFile();
								  Grid toOpen = ParserJSON.deserialize(file.getAbsolutePath()).getGrid();
								  int x = (730/2)+15;
								  int y = (600/2-15-(toOpen.getSize()*Textures.cellTexture.getSize().y)/2);


								  System.out.println(file.getAbsolutePath());
								}
							}
						// Quitter
						if(buttonQuitter.isInside(mouse.position)){
							window.close();
						}
					}
					break;
				default:
					break;
				}
			}
		}*/
		
		
		MenuDisplay.menuDisplay(window);
		
	}
}
