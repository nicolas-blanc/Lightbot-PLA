package lightbot.graphics;

import lightbot.LightCore;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;

public class LevelDisplay {
	
	static Sprite levelBases;
	static Sprite levelProcedures;
	static Sprite levelIf;
	static Sprite levelBreak;
	static Sprite levelFork;
	static Sprite levelPointeurs;
	
	static Sprite levelBg;
	//static Sprite levelBlock;
	static Sprite levelTitle;
	/*static Sprite level1;
	static Sprite level2;
	static Sprite level3;
	static Sprite level4;*/
	
	static Button buttonBases;
	static Button buttonProcedures;
	static Button buttonIf;
	static Button buttonBreak;
	static Button buttonFork;
	static Button buttonPointeurs;
	
	static Button homeButton;
	static Button buttonBg;
	//static Button buttonBlock;
	static Button buttonTitle;
	/*static Button button1;
	static Button button2;
	static Button button3;
	static Button button4;*/
	
	public LevelDisplay(){
		setButtons();
	}
	
	public void setButtons(){
		Textures.initTextures();
		
		levelBases = new Sprite(Textures.levelBases);
		levelProcedures = new Sprite(Textures.levelProcedures);
		levelIf = new Sprite(Textures.levelIf);
		levelBreak = new Sprite(Textures.levelBreak);
		levelFork = new Sprite(Textures.levelFork);
		levelPointeurs = new Sprite(Textures.levelPointeurs);
		
		levelBg = new Sprite(Textures.levelBg);
		//levelBlock = new Sprite(Textures.levelBlock);
		levelTitle = new Sprite(Textures.levelTitle);
		/*level1 = new Sprite(Textures.level1);
		level2 = new Sprite(Textures.level2);
		level3 = new Sprite(Textures.level3);
		level4 = new Sprite(Textures.level4);*/
		
		buttonBases = new Button(levelBases, null, null);
		buttonProcedures = new Button(levelProcedures, null, null);
		buttonIf = new Button(levelIf, null, null);
		buttonBreak = new Button(levelBreak, null, null);
		buttonFork = new Button(levelFork, null, null);
		buttonPointeurs = new Button(levelPointeurs, null, null);
		
		buttonBg = new Button(levelBg, null, null);
		//buttonBlock = new Button(levelBlock, null, null);
		buttonTitle = new Button(levelTitle, null, null);
		/*button1 = new Button(level1, null, null);
		button2 = new Button(level2, null, null);
		button3 = new Button(level3, null, null);
		button4 = new Button(level4, null, null);*/
		
		levelBases.setPosition(190, 120);
		levelProcedures.setPosition(190, 190);
		levelIf.setPosition(190, 260);
		levelBreak.setPosition(190, 330);
		levelPointeurs.setPosition(190, 400);
		levelFork.setPosition(190, 470);
		
		levelBg.setPosition(0, 0);
		//levelBlock.setPosition(650, 300);
		levelTitle.setPosition(100, 0);
		/*level1.setPosition(arg0, arg1);
		level2.setPosition(arg0, arg1);;
		level3.setPosition(arg0, arg1);
		level4.setPosition(arg0, arg1);*/	
		
		Sprite homeSprite = new Sprite(Textures.homeButtonTextureRelief);
		homeSprite.setPosition(15, 15);
		homeButton = new Button(homeSprite, null, null);
	}
	
	public void displayButtons(){
	    LightCore.window.draw(levelBg);
	    LightCore.window.draw(levelTitle);
	    LightCore.window.draw(levelBases);
	    LightCore.window.draw(levelProcedures);
	    LightCore.window.draw(levelFork);
	    LightCore.window.draw(levelPointeurs);
	    LightCore.window.draw(levelIf);
	    LightCore.window.draw(levelBreak);
	    LightCore.window.draw(homeButton.getSprite());
	}
	
	public void display() {
		displayButtons();
	}
	
	public void eventManager(Event event){
		switch (event.type) {
		case CLOSED:
			LightCore.window.close();
			break;
		case MOUSE_BUTTON_PRESSED:
			MouseButtonEvent mouse = event.asMouseButtonEvent();
			if(homeButton.isInside(mouse.position)){
				LightCore.game = false;
				LightCore.menu = true;
			}
		default:
			break;
		}
	}
}
