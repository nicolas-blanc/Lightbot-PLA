package lightbot.graphics;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import lightbot.LightCore;
import lightbot.system.ParserJSON;
import lightbot.system.world.Grid;
import lightbot.system.world.Level;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.window.event.MouseEvent;

public class MenuDisplay {

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
	
	public MenuDisplay(){
		setButtons();
	}
	
	/**
	 * To display the menu
	 * @param window
	 */
	public void display() {
		displayButtons();
	}
	
	/**
	 * To set all the buttons of the menu
	 */
	public void setButtons(){
		Textures.initTextures();
		
		menuLogo = new Sprite(Textures.menuLogo);
		menuJouer = new Sprite(Textures.menuJouer);
		menuQuitter = new Sprite(Textures.menuQuitter);
		menuEditeur = new Sprite(Textures.menuEditeur);
		menuCharger = new Sprite(Textures.menuCharger);
		menuBg = new Sprite(Textures.menuBg);
		
		buttonLogo = new Button(menuLogo, null, null);	
		buttonJouer = new Button(menuJouer, Textures.menuJouerH, Textures.menuJouer);
		buttonQuitter = new Button(menuQuitter, Textures.menuQuitterH, Textures.menuQuitter);
		buttonEditeur = new Button(menuEditeur, Textures.menuEditeurH, Textures.menuEditeur);
		buttonCharger = new Button(menuCharger, Textures.menuChargerH, Textures.menuCharger);
		
		menuLogo.setPosition(35, 27);
		menuJouer.setPosition(leftMargin, 206);
		menuEditeur.setPosition(leftMargin, 206 + spaceBtwButtons);
		menuCharger.setPosition(leftMargin, 206 + 2*spaceBtwButtons);
		menuQuitter.setPosition(leftMargin, 206 + 3*spaceBtwButtons);
	}
	
	/**
	 * To display all the buttons of the menu
	 * @param window
	 */
	public void displayButtons(){
	    LightCore.window.draw(menuBg);
	    LightCore.window.draw(menuLogo);
	    LightCore.window.draw(menuJouer);
	    LightCore.window.draw(menuEditeur);
	    LightCore.window.draw(menuCharger);
	    LightCore.window.draw(menuQuitter);
	}
	
	/**
	 * Event manager for the menu
	 * @param window
	 */
	public void eventManager(Event event){
			switch (event.type) {
			case CLOSED:
				LightCore.window.close();
				break;
			case MOUSE_MOVED:
				MouseEvent mouse1 = event.asMouseEvent();
				buttonJouer.changeOnHover(mouse1.position);
				buttonQuitter.changeOnHover(mouse1.position);
				buttonCharger.changeOnHover(mouse1.position);
				buttonEditeur.changeOnHover(mouse1.position);
				break;
			case MOUSE_BUTTON_PRESSED:
				MouseButtonEvent mouse = event.asMouseButtonEvent();
				if(mouse.button == Mouse.Button.LEFT){
					
					// Jouer
					if(buttonJouer.isInside(mouse.position)){
						//TODO change this
						Level level = ParserJSON.deserialize("example.json");
						Grid grid = level.getGrid();
						LightCore.display = new Game(grid);
						LightCore.menu = false;
						LightCore.game = true;
						//LightCore.window.clear(Color.WHITE);
						//LightCore.display.display();
					}
					
					// Éditeur
					if(buttonEditeur.isInside(mouse.position)){
						int sizeInt = -1;
						String size = null;
						do{
							size = JOptionPane.showInputDialog(null, "Veuillez indiquer la taille de votre grille :", "Éditeur", JOptionPane.QUESTION_MESSAGE);
							if(size != null && !size.equals("")){
								sizeInt = (size == null)?null : Integer.parseInt(size);
								if(sizeInt<1 || sizeInt>8){
									JOptionPane.showMessageDialog(null, "Merci de saisir une taille comprise entre 1 et 8", "Éditeur", JOptionPane.ERROR_MESSAGE);
								}else{
									JOptionPane.showMessageDialog(null, "Vous allez créer une grille " + sizeInt + "x" + sizeInt, "Identité", JOptionPane.INFORMATION_MESSAGE);
									LightCore.display = new Editor(sizeInt);
									LightCore.editor = true;					
									LightCore.menu = false;
								}
							}
						} while((sizeInt<1 || sizeInt>8) && size != null && !size.equals(""));
					}
					
					// Charger
					if(buttonCharger.isInside(mouse.position)){
						JFileChooser dialog = new JFileChooser();
						
						if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
							  File file = dialog.getSelectedFile();
							  Grid toOpen = ParserJSON.deserialize(file.getAbsolutePath()).getGrid();
							  int x = (730/2)+15;
							  int y = (600/2-15-(toOpen.getSize()*Textures.cellTexture.getSize().y)/2);
							  LightCore.display = new Game(toOpen);
							  LightCore.menu = false;
							  LightCore.game = true;
							  System.out.println(file.getAbsolutePath());
						}
					}
					
					// Quitter
					if(buttonQuitter.isInside(mouse.position)){
						LightCore.window.close();
					}
					
				}
				break;
			default:
				break;
			}
		}
	
	
	
}
