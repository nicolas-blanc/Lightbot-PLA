package lightbot.graphics;

import lightbot.LightCore;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.window.event.MouseEvent;

public class LevelDisplay {
	
	/*
	 * Sprites for the "world page"
	 */
	static Sprite levelBases;
	static Sprite levelProcedures;
	static Sprite levelIf;
	static Sprite levelBreak;
	static Sprite levelFork;
	static Sprite levelPointeurs;
	static Sprite levelBg;
	static Sprite levelTitle;
	
	/*
	 * Sprites for the "level page"
	 */
	static Sprite levelBlock;
	static Sprite level1;
	static Sprite level2;
	static Sprite level3;
	static Sprite level4;
	
	/*
	 * Buttons for the "world page"
	 */
	static Button buttonBases;
	static Button buttonProcedures;
	static Button buttonIf;
	static Button buttonBreak;
	static Button buttonFork;
	static Button buttonPointeurs;
	static Button homeButton;
	static Button buttonBg;
	static Button buttonTitle;
	
	/*
	 * Buttons for the "level page"
	 */
	static Button buttonBlock;
	static Button button1;
	static Button button2;
	static Button button3;
	static Button button4;
	
	
	public LevelDisplay(){
		setButtons();
	}
	
	public void setButtons(){
		Textures.initTextures();
		
		/*
		 * Sprites for the "world page"
		 */
		levelBases = new Sprite(Textures.levelBases);
		levelProcedures = new Sprite(Textures.levelProcedures);
		levelIf = new Sprite(Textures.levelIf);
		levelBreak = new Sprite(Textures.levelBreak);
		levelFork = new Sprite(Textures.levelFork);
		levelPointeurs = new Sprite(Textures.levelPointeurs);
		levelBg = new Sprite(Textures.levelBg);
		levelTitle = new Sprite(Textures.levelTitle);
		
		/*
		 * Sprites for the "level page"
		 */
		levelBlock = new Sprite(Textures.levelBlock);
		level1 = new Sprite(Textures.level1);
		level2 = new Sprite(Textures.level2);
		level3 = new Sprite(Textures.level3);
		level4 = new Sprite(Textures.level4);
		
		/*
		 * Buttons for the "world page"
		 */
		buttonBases = new Button(levelBases, Textures.levelBasesH, Textures.levelBases);
		buttonProcedures = new Button(levelProcedures, Textures.levelProceduresH, Textures.levelProcedures);
		buttonIf = new Button(levelIf, Textures.levelIfH, Textures.levelIf);
		buttonBreak = new Button(levelBreak, Textures.levelBreakH, Textures.levelBreak);
		buttonFork = new Button(levelFork, Textures.levelForkH, Textures.levelFork);
		buttonPointeurs = new Button(levelPointeurs, Textures.levelPointeursH, Textures.levelPointeurs);
		buttonBg = new Button(levelBg, null, null);
		buttonTitle = new Button(levelTitle, null, null);	
		
		/*
		 * Buttons for the "level page"
		 */
		buttonBlock = new Button(levelBlock, null, null);
		button1 = new Button(level1, Textures.level1H, Textures.level1);
		button2 = new Button(level2, Textures.level2H, Textures.level2);
		button3 = new Button(level3, Textures.level3H, Textures.level3);
		button4 = new Button(level4, Textures.level4H, Textures.level4);
		
		/*
		 * Set positions
		 */
		levelBases.setPosition(190, 120);
		levelProcedures.setPosition(190, 190);
		levelIf.setPosition(190, 260);
		levelBreak.setPosition(190, 330);
		levelPointeurs.setPosition(190, 400);
		levelFork.setPosition(190, 470);
		levelBg.setPosition(0, 0);
		levelTitle.setPosition(100, 0);
		
		levelBlock.setPosition(530, 70);
		level1.setPosition(590, 180);
		level2.setPosition(590, 240);;
		level3.setPosition(590, 300);
		level4.setPosition(590, 360);	
		
		/*
		 * Sprite & button for home icon
		 */
		Sprite homeSprite = new Sprite(Textures.homeButtonTextureRelief);
		homeSprite.setPosition(15, 15);
		homeButton = new Button(homeSprite, null, null);
	}
	
	public void displayButtons(){
	    LightCore.window.draw(levelBg);
	    LightCore.window.draw(levelTitle);
	    LightCore.window.draw(levelBases);
	    LightCore.window.draw(levelProcedures);
	    LightCore.window.draw(levelIf);
	    LightCore.window.draw(levelBreak);
	    LightCore.window.draw(levelFork);
	    LightCore.window.draw(levelPointeurs);
		LightCore.window.draw(homeButton.getSprite());
	}
	
	public void displayLevelButtons(){
	    LightCore.window.draw(levelBlock);
	    LightCore.window.draw(level1);
	    LightCore.window.draw(level2);
	    LightCore.window.draw(level3);
	    LightCore.window.draw(level4);	    
	}
	
	public void display() {
		displayButtons();
	}
	
	public void eventManager(Event event){
		switch (event.type) {
		case CLOSED:
			LightCore.window.close();
			break;
		case MOUSE_MOVED:
			MouseEvent mouse1 = event.asMouseEvent();
			buttonBases.changeOnHover(mouse1.position);
			buttonProcedures.changeOnHover(mouse1.position);
			buttonBreak.changeOnHover(mouse1.position);
			buttonIf.changeOnHover(mouse1.position);
			buttonPointeurs.changeOnHover(mouse1.position);
			buttonFork.changeOnHover(mouse1.position);
			button1.changeOnHover(mouse1.position);
			button2.changeOnHover(mouse1.position);
			button3.changeOnHover(mouse1.position);
			button4.changeOnHover(mouse1.position);
			break;
		case MOUSE_BUTTON_PRESSED:
			MouseButtonEvent mouse = event.asMouseButtonEvent();
			if(homeButton.isInside(mouse.position)){
				LightCore.bases = false;
				LightCore.procedures = false;
				LightCore.ifthenelse = false;
				LightCore.fork = false;
				LightCore.pointeurs = false;
				LightCore.breakaction = false;
				LightCore.worlds = false;
				LightCore.menu = true;
			}
			if(buttonBases.isInside(mouse.position)){
				LightCore.procedures = false;
				LightCore.ifthenelse = false;
				LightCore.breakaction = false;
				LightCore.pointeurs = false;
				LightCore.fork = false;
				LightCore.bases = true;
			}
			if(buttonProcedures.isInside(mouse.position)){
				LightCore.ifthenelse = false;
				LightCore.breakaction = false;
				LightCore.pointeurs = false;
				LightCore.fork = false;
				LightCore.bases = false;
				LightCore.procedures = true;
			}
			if(buttonIf.isInside(mouse.position)){
				LightCore.breakaction = false;
				LightCore.pointeurs = false;
				LightCore.fork = false;
				LightCore.bases = false;
				LightCore.procedures = false;
				LightCore.ifthenelse = true;
			}
			if(buttonFork.isInside(mouse.position)){
				LightCore.ifthenelse = false;
				LightCore.breakaction = false;
				LightCore.pointeurs = false;
				LightCore.bases = false;
				LightCore.procedures = false;
				LightCore.fork = true;
			}
			if(buttonPointeurs.isInside(mouse.position)){
				LightCore.ifthenelse = false;
				LightCore.breakaction = false;
				LightCore.fork = false;
				LightCore.bases = false;
				LightCore.procedures = false;
				LightCore.pointeurs = true;
			}
			if(buttonBreak.isInside(mouse.position)){
				LightCore.ifthenelse = false;
				LightCore.pointeurs = false;
				LightCore.fork = false;
				LightCore.bases = false;
				LightCore.procedures = false;
				LightCore.breakaction = true;
			}
		default:
			break;
		}
	}
}
