package lightcore.graphics;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import lightcore.LightCore;
import lightcore.simulator.Level;
import lightcore.simulator.ParserJSON;
import lightcore.simulator._Executable;
import lightcore.simulator.action.Forward;
import lightcore.simulator.action.Jump;
import lightcore.simulator.action.Light;
import lightcore.simulator.action.Turn;
import lightcore.simulator.generator.WorldGenerator;
import lightcore.simulator.generator.WorldGeneratorBase;
import lightcore.simulator.generator.WorldGeneratorITEPointers;
import lightcore.simulator.generator.WorldGeneratorProcedure;
import lightcore.world.Grid;
import lightcore.world.RelativeDirection;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;
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
	static Sprite menuAleatoire;
	static Sprite menuBg;

	static Sprite blink;

	static Button buttonLogo;
	static Button buttonJouer;
	static Button buttonQuitter;
	static Button buttonEditeur;
	static Button buttonCharger;
	static Button buttonAleatoire;

	private int frame = 0;
	private Clock animClock = null;
	private Clock elapsedTime = null;

	private Random rand = new Random();
	private int newTime = 5;

	public MenuDisplay() {
		setButtons();
	}

	/**
	 * To display the menu
	 * 
	 * @param window
	 */
	public void display() {
		displayButtons();
		if (elapsedTime == null)
			elapsedTime = new Clock();
		if (elapsedTime.getElapsedTime().asSeconds() >= newTime) {
			elapsedTime.restart();
			animClock = new Clock();
			newTime = rand.nextInt((7 - 2) + 1) + 2;
		}

		if (animClock != null && animClock.getElapsedTime().asMilliseconds() >= 17) {
			animClock.restart();

			frame++;
			if (frame > 9)
				frame = 0;

			int frameRow = frame / 5;
			int frameCol = frame % 5;
			blink.setTextureRect(new IntRect(frameCol * 269, frameRow * 310, 269, 310));
			if (frame == 0)
				animClock = null;
		}
	}

	/**
	 * To set all the buttons of the menu
	 */
	public void setButtons() {
		menuLogo = new Sprite(Textures.menuLogo);
		menuJouer = new Sprite(Textures.menuJouer);
		menuQuitter = new Sprite(Textures.menuQuitter);
		menuEditeur = new Sprite(Textures.menuEditeur);
		menuCharger = new Sprite(Textures.menuCharger);
		menuAleatoire = new Sprite(Textures.menuAleatoire);
		menuBg = new Sprite(Textures.menuBg);

		blink = new Sprite(Textures.blinkTexture);
		blink.setTextureRect(new IntRect(0, 0, 269, 310));
		blink.setPosition(600, 200);

		buttonLogo = new Button(menuLogo, null, null);
		buttonJouer = new Button(menuJouer, Textures.menuJouerH, Textures.menuJouer);
		buttonQuitter = new Button(menuQuitter, Textures.menuQuitterH, Textures.menuQuitter);
		buttonEditeur = new Button(menuEditeur, Textures.menuEditeurH, Textures.menuEditeur);
		buttonCharger = new Button(menuCharger, Textures.menuChargerH, Textures.menuCharger);
		buttonAleatoire = new Button(menuAleatoire, Textures.menuAleatoireH, Textures.menuAleatoire);

		menuLogo.setPosition(35, 27);
		menuJouer.setPosition(leftMargin, 206);
		menuEditeur.setPosition(leftMargin, 206 + spaceBtwButtons);
		menuCharger.setPosition(leftMargin, 206 + 2 * spaceBtwButtons);
		menuAleatoire.setPosition(leftMargin, 206 + 3 * spaceBtwButtons);
		menuQuitter.setPosition(leftMargin, 206 + 4 * spaceBtwButtons);
	}

	/**
	 * To display all the buttons of the menu
	 * 
	 * @param window
	 */
	public void displayButtons() {
		LightCore.window.draw(menuBg);
		LightCore.window.draw(menuLogo);
		LightCore.window.draw(menuJouer);
		LightCore.window.draw(menuEditeur);
		LightCore.window.draw(menuCharger);
		LightCore.window.draw(menuAleatoire);
		LightCore.window.draw(menuQuitter);

		LightCore.window.draw(blink);
	}

	/**
	 * Event manager for the menu
	 * 
	 * @param window
	 */
	public void eventManager(Event event) {
		switch (event.type) {
		case CLOSED:
			LightCore.window.close();
			break;
		case MOUSE_MOVED:
			MouseEvent mouse1 = event.asMouseEvent();
			buttonJouer.changeOnHover(mouse1.position);
			buttonQuitter.changeOnHover(mouse1.position);
			buttonCharger.changeOnHover(mouse1.position);
			buttonAleatoire.changeOnHover(mouse1.position);
			buttonEditeur.changeOnHover(mouse1.position);
			break;
		case MOUSE_BUTTON_PRESSED:
			MouseButtonEvent mouse = event.asMouseButtonEvent();
			if (mouse.button == Mouse.Button.LEFT) {

				// Jouer
				if (buttonJouer.isInside(mouse.position)) {
					// TODO change this
					/*
					 * Level level = ParserJSON.deserialize("example.json");
					 * Grid grid = level.getGrid(); LightCore.display = new
					 * Game(grid); LightCore.menu = false;
					 */
					LightCore.worlds = true;
					LightCore.menu = false;
					LightCore.firstPrintGame = true;
				}

				// Éditeur
				if (buttonEditeur.isInside(mouse.position)) {
					int sizeInt = -1;
					String size = null;
					do {
						size = JOptionPane.showInputDialog(null, "Veuillez indiquer la taille de votre grille :",
								"Éditeur", JOptionPane.QUESTION_MESSAGE);
						if (size != null && !size.equals("")) {
							try {
								sizeInt = Integer.parseInt(size);
							} catch (NumberFormatException nfe) {
							}

							if (sizeInt < 1 || sizeInt > 8) {
								JOptionPane.showMessageDialog(null,
										"Merci de saisir un nombre entier compris entre 1 et 8", "Éditeur",
										JOptionPane.ERROR_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, "Vous allez créer une grille " + sizeInt + "x"
										+ sizeInt, "Identité", JOptionPane.INFORMATION_MESSAGE);
								LightCore.display = new Editor(sizeInt);
								LightCore.editor = true;
								LightCore.menu = false;
							}
						}
					} while ((sizeInt < 1 || sizeInt > 8) && size != null && !size.equals(""));
				}

				// Charger
				if (buttonCharger.isInside(mouse.position)) {
					JFileChooser dialog = new JFileChooser();

					if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						File file = dialog.getSelectedFile();
						Level lvl = ParserJSON.deserialize(file.getAbsolutePath());
						//int x = (730 / 2) + 15;
						//int y = (600 / 2 - 15 - (toOpen.getSize() * Textures.cellTexture.getSize().y) / 2);
						LightCore.display = new Game(lvl);
						LightCore.menu = false;
						LightCore.game = true;
						//System.out.println(file.getAbsolutePath());
						LightCore.firstPrintGame = true;
					}
				}

				// Aleatoire
				if (buttonAleatoire.isInside(mouse.position)) {
					LightCore.menu = false;
					LightCore.random = true;
					WorldGenerator newWorld;

					switch (rand.nextInt(3)) {
					// switch (0) {
					case 0:
						newWorld = new WorldGeneratorBase();
						break;
					case 1:
						newWorld = new WorldGeneratorProcedure();
						break;
					case 2:
						newWorld = new WorldGeneratorITEPointers();
						break;
					default:
						newWorld = null;
						break;
					}

					Grid grid = newWorld.getGrid();
					ArrayList<_Executable> a = new ArrayList<_Executable>();
					a.add(new Forward());
					a.add(new Jump());
					a.add(new Turn(RelativeDirection.LEFT));
					a.add(new Turn(RelativeDirection.RIGHT));
					a.add(new Light());
					Level lvl = new Level(grid, a, true, true, 12, 12, 12);
					LightCore.display = new Game(lvl);
					LightCore.firstPrintGame = true;
				}
				// Quitter
				if (buttonQuitter.isInside(mouse.position)) {
					LightCore.window.close();
				}

			}
			break;
		default:
			break;
		}
	}
}
