package lightbot.graphics;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import lightbot.system.ParserJSON;
import lightbot.system.world.Grid;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.window.event.MouseEvent;

public class MenuDisplay {

	private static RenderWindow window;
	private static int windowWidth = 1000;
	private static int windowHeight = 600;
	private static int leftMargin = 202;
	private static int spaceBtwButtons = 75;
	
	static Sprite menuLogo;
	static Sprite menuJouer;
	static Sprite menuQuitter;
	static Sprite menuEditeur;
	static Sprite menuCharger;
	static Sprite menuBg;
	
	static Button buttonLogo;	
	static Button buttonJouer;
	static Button buttonQuitter;
	static Button buttonEditeur;
	static Button buttonCharger;
	
	public static void menuDisplay(){
		createWindow();
		setButtons();
		
		while (window.isOpen()) {
			displayButtons();
		    
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
		}
	}
	
	public static void createWindow(){
		window = new RenderWindow();
		window.create(new VideoMode(windowWidth, windowHeight), "   LIGHTCORE  -  Menu");
		window.setFramerateLimit(60);
	}
	
	public static void setButtons(){
		Textures.initTextures();
		
		menuLogo = new Sprite(Textures.menuLogo);
		menuJouer = new Sprite(Textures.menuJouer);
		menuQuitter = new Sprite(Textures.menuQuitter);
		menuEditeur = new Sprite(Textures.menuEditeur);
		menuCharger = new Sprite(Textures.menuCharger);
		menuBg = new Sprite(Textures.menuBg);
		
		buttonLogo = new Button(menuLogo, null, null);	
		buttonJouer = new Button(menuJouer, Textures.menuCharger, Textures.menuJouer);
		buttonQuitter = new Button(menuQuitter, null, null);
		buttonEditeur = new Button(menuEditeur, null, null);
		buttonCharger = new Button(menuCharger, null, null);
		
		menuLogo.setPosition(35, 27);
		menuJouer.setPosition(leftMargin, 206);
		menuEditeur.setPosition(leftMargin, 206 + spaceBtwButtons);
		menuCharger.setPosition(leftMargin, 206 + 2*spaceBtwButtons);
		menuQuitter.setPosition(leftMargin, 206 + 3*spaceBtwButtons);
	}
	
	public static void displayButtons(){
	    window.display();
	    window.draw(menuBg);
	    window.draw(menuLogo);
	    window.draw(menuJouer);
	    window.draw(menuEditeur);
	    window.draw(menuCharger);
	    window.draw(menuQuitter);
	}
	
	
	
}
