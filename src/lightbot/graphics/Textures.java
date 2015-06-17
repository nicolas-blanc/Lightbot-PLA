package lightbot.graphics;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;

public class Textures {
	public static final String RESSOURCE_PATH = "ressources/";
	public static final String NEW_ICONS_PATH = RESSOURCE_PATH + "new_icons/";
	public static final String MENU_PATH = RESSOURCE_PATH + "menu/";
	public static final String LEVELS_PATH = RESSOURCE_PATH + "levels/";
	public static final String ROBOT_PATH = RESSOURCE_PATH + "robot/";
	
	public static final String EDITOR_PATH = RESSOURCE_PATH + "editor/";
	public static final String CUBE_PATH = RESSOURCE_PATH + "cube/";
	
	// Cubes
	public static Texture cubeTextureBlue;
	public static Texture cubeTextureGreen;
	public static Texture cubeTextureOrange;
	public static Texture cubeTexturePurple;
	public static Texture cubeTextureRed;
	public static Texture cubeTextureWhite;
	public static Texture cubeTextureYellow;
	
	// Teleport
	public static Texture cubeTextureTeleportBlue;
	public static Texture cubeTextureTeleportGreen;
	public static Texture cubeTextureTeleportOrange;
	public static Texture cubeTextureTeleportPurple;
	public static Texture cubeTextureTeleportRed;
	public static Texture cubeTextureTeleportWhite;
	public static Texture cubeTextureTeleportYellow;
	
	// Cell
	public static Texture cellTexture;
	
	// Game
	public static Texture playTexture;
	public static Texture resetTexture;
	
	public static Texture rotateLeft;
	public static Texture rotateRight;
	
	// Actions
	public static final int ACTION_TEXTURE_SIZE = 50;
	public static Texture forwardTexture;
	public static Texture jumpTexture;
	public static Texture turnLeftTexture;
	public static Texture turnRightTexture;
	public static Texture lightTexture;
	public static Texture procedure1Texture;
	public static Texture procedure2Texture;
	public static Texture showerTexture;
	
	
	// Robot
	public static Texture robotNorth;
	public static Texture robotSouth;
	public static Texture robotWest;
	public static Texture robotEast;	
	
	// Editor
	public static Texture lightButtonTexture;
	public static Texture lightButtonTextureRelief;
	public static Texture loadButtonTexture;
	public static Texture loadButtonTextureRelief;
	public static Texture saveButtonTexture;
	public static Texture saveButtonTextureRelief;
	public static Texture teleportButtonTexture;
	public static Texture teleportButtonTextureRelief;
	public static Texture robotButtonTexture;
	public static Texture robotButtonTextureRelief;
	public static Texture homeButtonTextureRelief;
	
	public static Texture blueSplash;
	public static Texture orangeSplash;
	public static Texture purpleSplash;
	public static Texture redSplash;
	
	//Menu
	public static Texture menuLogo;
	public static Texture menuJouer;
	public static Texture menuEditeur;
	public static Texture menuQuitter;
	public static Texture menuCharger;
	public static Texture menuBg;
	
	public static Texture menuLogoH;
	public static Texture menuJouerH;
	public static Texture menuEditeurH;
	public static Texture menuQuitterH;
	public static Texture menuChargerH;
	public static Texture menuBgH;
	
	public static Texture blinkTexture; 
	
	//Levels
	public static Texture levelBases;
	public static Texture levelProcedures;
	public static Texture levelIf;
	public static Texture levelBreak;
	public static Texture levelFork;
	public static Texture levelPointeurs;
	
	public static Texture levelBg;
	public static Texture levelBlock;
	public static Texture levelTitle;
	public static Texture level1;
	public static Texture level2;
	public static Texture level3;
	public static Texture level4;
	
	public static Texture levelBasesH;
	public static Texture levelProceduresH;
	public static Texture levelIfH;
	public static Texture levelBreakH;
	public static Texture levelForkH;
	public static Texture levelPointeursH;
	
	public static Texture level1H;
	public static Texture level2H;
	public static Texture level3H;
	public static Texture level4H;
	
	
	public static void initTextures(){
		
		// Cubes
		cubeTextureBlue = new Texture();
		cubeTextureGreen = new Texture();
		cubeTextureOrange = new Texture();
		cubeTexturePurple = new Texture();
		cubeTextureRed = new Texture();
		cubeTextureWhite = new Texture();
		cubeTextureYellow = new Texture();
		
		// Teleport
		cubeTextureTeleportBlue = new Texture();
		cubeTextureTeleportGreen = new Texture();
		cubeTextureTeleportOrange = new Texture();
		cubeTextureTeleportPurple = new Texture();
		cubeTextureTeleportRed = new Texture();
		cubeTextureTeleportWhite = new Texture();
		cubeTextureTeleportYellow = new Texture();
		
		// Cell
		cellTexture = new Texture();
		
		// Game
		playTexture = new Texture();
		resetTexture = new Texture();
		
		rotateLeft = new Texture();
		rotateRight = new Texture();
		
		// Actions
		forwardTexture = new Texture();
		jumpTexture = new Texture();
		turnLeftTexture = new Texture();
		turnRightTexture = new Texture();
		lightTexture = new Texture();
		procedure1Texture = new Texture();
		procedure2Texture = new Texture();
		showerTexture = new Texture();
		
		// Robot
		robotNorth = new Texture();
		robotSouth = new Texture();
		robotWest = new Texture();
		robotEast = new Texture();
		
		
		// Editor
		lightButtonTexture = new Texture();
		lightButtonTextureRelief = new Texture();
		loadButtonTexture = new Texture();
		loadButtonTextureRelief = new Texture();
		saveButtonTexture = new Texture();
		saveButtonTextureRelief = new Texture();
		teleportButtonTexture = new Texture();
		teleportButtonTextureRelief = new Texture();
		robotButtonTexture = new Texture();
		robotButtonTextureRelief = new Texture();
		homeButtonTextureRelief = new Texture();
		
		blueSplash = new Texture();
		orangeSplash = new Texture();
		purpleSplash = new Texture();
		redSplash = new Texture();
		
		// Menu
		menuLogo = new Texture();
		menuJouer = new Texture();
		menuQuitter = new Texture();
		menuEditeur = new Texture();
		menuCharger = new Texture();
		menuBg = new Texture();
		
		menuLogoH = new Texture();
		menuJouerH = new Texture();
		menuQuitterH = new Texture();
		menuEditeurH = new Texture();
		menuChargerH = new Texture();
		menuBgH = new Texture();
		
		blinkTexture = new Texture();
		
		//Levels
		levelBases = new Texture();
		levelProcedures = new Texture();
		levelIf = new Texture();
		levelBreak = new Texture();
		levelFork = new Texture();
		levelPointeurs = new Texture();
		
		levelBg = new Texture();
		levelBlock = new Texture();
		levelTitle = new Texture();
		level1 = new Texture();
		level2 = new Texture();
		level3 = new Texture();
		level4 = new Texture();
		
		levelBasesH = new Texture();
		levelProceduresH = new Texture();
		levelIfH = new Texture();
		levelBreakH = new Texture();
		levelForkH = new Texture();
		levelPointeursH = new Texture();
		
		level1H = new Texture();
		level2H = new Texture();
		level3H = new Texture();
		level4H = new Texture();
		
		try {
			
			/************************ Load the textures ************************/
			
			// Cubes
		    cubeTextureBlue.loadFromFile(Paths.get(CUBE_PATH+"bluecube.png"));
		    cubeTextureGreen.loadFromFile(Paths.get(CUBE_PATH+"greencube.png"));
		    cubeTextureOrange.loadFromFile(Paths.get(CUBE_PATH+"orangecube.png"));
		    cubeTexturePurple.loadFromFile(Paths.get(CUBE_PATH+"purplecube.png"));
		    cubeTextureRed.loadFromFile(Paths.get(CUBE_PATH+"redcube.png"));
		    cubeTextureWhite.loadFromFile(Paths.get(CUBE_PATH+"whitecube.png"));
		    cubeTextureYellow.loadFromFile(Paths.get(CUBE_PATH+"yellowcube.png"));
		    
		    // Teleport
		    cubeTextureTeleportBlue.loadFromFile(Paths.get(CUBE_PATH+"blueteleport.png"));
		    cubeTextureTeleportGreen.loadFromFile(Paths.get(CUBE_PATH+"greenteleport.png"));
		    cubeTextureTeleportOrange.loadFromFile(Paths.get(CUBE_PATH+"orangeteleport.png"));
		    cubeTextureTeleportPurple.loadFromFile(Paths.get(CUBE_PATH+"purpleteleport.png"));
		    cubeTextureTeleportRed.loadFromFile(Paths.get(CUBE_PATH+"redteleport.png"));
		    cubeTextureTeleportWhite.loadFromFile(Paths.get(CUBE_PATH+"whiteteleport.png"));
		    cubeTextureTeleportYellow.loadFromFile(Paths.get(CUBE_PATH+"yellowteleport.png"));
		    
		    // Cell
		    cellTexture.loadFromFile(Paths.get(RESSOURCE_PATH+"canva.png"));
		    
		    // Game
		    playTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconPlay.png"));
		    resetTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconReset.png"));
		    
		    rotateLeft.loadFromFile(Paths.get(NEW_ICONS_PATH + "rotateL.png"));
		    rotateRight.loadFromFile(Paths.get(NEW_ICONS_PATH + "rotateR.png"));
		    
		    // Actions
		    forwardTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconForward.png"));
		    jumpTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconJump.png"));
		    turnLeftTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconTurnLeft.png"));
		    turnRightTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconTurnRight.png"));
		    lightTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconLight.png"));
		    procedure1Texture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconProcedure1.png"));
		    procedure2Texture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconProcedure2.png"));
		    showerTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconShower.png"));
		    
		    // Robot
		    robotNorth.loadFromFile(Paths.get(ROBOT_PATH + "white_north.png"));
		    robotSouth.loadFromFile(Paths.get(ROBOT_PATH + "white_south.png"));
		    robotWest.loadFromFile(Paths.get(ROBOT_PATH + "white_west.png"));
		    robotEast.loadFromFile(Paths.get(ROBOT_PATH + "white_east.png"));
			
			// Editor
			lightButtonTexture.loadFromFile(Paths.get(EDITOR_PATH+"light.png"));
			lightButtonTextureRelief.loadFromFile(Paths.get(EDITOR_PATH+"lightRelief.png"));
			loadButtonTexture.loadFromFile(Paths.get(EDITOR_PATH+"load.png"));
			loadButtonTextureRelief.loadFromFile(Paths.get(EDITOR_PATH+"loadRelief.png"));
			saveButtonTexture.loadFromFile(Paths.get(EDITOR_PATH+"save.png"));
			saveButtonTextureRelief.loadFromFile(Paths.get(EDITOR_PATH+"saveRelief.png"));
			teleportButtonTexture.loadFromFile(Paths.get(EDITOR_PATH+"teleport.png"));
			teleportButtonTextureRelief.loadFromFile(Paths.get(EDITOR_PATH+"teleportRelief.png"));
			robotButtonTexture.loadFromFile(Paths.get(EDITOR_PATH+"robot.png"));
			robotButtonTextureRelief.loadFromFile(Paths.get(EDITOR_PATH+"robotRelief.png"));
			homeButtonTextureRelief.loadFromFile(Paths.get(EDITOR_PATH+"homeRelief.png"));
			
			blueSplash.loadFromFile(Paths.get(EDITOR_PATH+"bluesplash.png"));
			orangeSplash.loadFromFile(Paths.get(EDITOR_PATH+"orangesplash.png"));
			purpleSplash.loadFromFile(Paths.get(EDITOR_PATH+"purplesplash.png"));
			redSplash.loadFromFile(Paths.get(EDITOR_PATH+"redsplash.png"));
			
			//menu
			menuBg.loadFromFile(Paths.get(MENU_PATH + "menuBg.png"));
			menuJouer.loadFromFile(Paths.get(MENU_PATH + "playButton.png"));
			menuQuitter.loadFromFile(Paths.get(MENU_PATH + "exitButton.png"));
			menuEditeur.loadFromFile(Paths.get(MENU_PATH + "editorButton.png"));
			menuCharger.loadFromFile(Paths.get(MENU_PATH + "loadButton.png"));
			menuLogo.loadFromFile(Paths.get(MENU_PATH + "logoLightcore.png"));
			
			menuJouerH.loadFromFile(Paths.get(MENU_PATH + "playButtonH.png"));
			menuQuitterH.loadFromFile(Paths.get(MENU_PATH + "exitButtonH.png"));
			menuEditeurH.loadFromFile(Paths.get(MENU_PATH + "editorButtonH.png"));
			menuChargerH.loadFromFile(Paths.get(MENU_PATH + "loadButtonH.png"));
			
			blinkTexture.loadFromFile(Paths.get(MENU_PATH + "blink.png"));
			
			//levels
			levelBases.loadFromFile(Paths.get(LEVELS_PATH + "levelBases.png"));
			levelProcedures.loadFromFile(Paths.get(LEVELS_PATH + "levelProcedures.png"));
			levelIf.loadFromFile(Paths.get(LEVELS_PATH + "levelIf.png"));
			levelBreak.loadFromFile(Paths.get(LEVELS_PATH + "levelBreak.png"));
			levelFork.loadFromFile(Paths.get(LEVELS_PATH + "levelFork.png"));
			levelPointeurs.loadFromFile(Paths.get(LEVELS_PATH + "levelPointeurs.png"));
			
			levelBg.loadFromFile(Paths.get(LEVELS_PATH + "levelBg.png"));
			levelBlock.loadFromFile(Paths.get(LEVELS_PATH + "levelBlock.png"));
			levelTitle.loadFromFile(Paths.get(LEVELS_PATH + "levelTitle.png"));
			level1.loadFromFile(Paths.get(LEVELS_PATH + "levelUn.png"));
			level2.loadFromFile(Paths.get(LEVELS_PATH + "levelDeux.png"));
			level3.loadFromFile(Paths.get(LEVELS_PATH + "levelTrois.png"));
			level4.loadFromFile(Paths.get(LEVELS_PATH + "levelQuatre.png"));
			
			levelBasesH.loadFromFile(Paths.get(LEVELS_PATH + "levelBasesH.png"));
			levelProceduresH.loadFromFile(Paths.get(LEVELS_PATH + "levelProceduresH.png"));
			levelIfH.loadFromFile(Paths.get(LEVELS_PATH + "levelIfH.png"));
			levelBreakH.loadFromFile(Paths.get(LEVELS_PATH + "levelBreakH.png"));
			levelForkH.loadFromFile(Paths.get(LEVELS_PATH + "levelForkH.png"));
			levelPointeursH.loadFromFile(Paths.get(LEVELS_PATH + "levelPointeursH.png"));
			
			level1H.loadFromFile(Paths.get(LEVELS_PATH + "levelUnH.png"));
			level2H.loadFromFile(Paths.get(LEVELS_PATH + "levelDeuxH.png"));
			level3H.loadFromFile(Paths.get(LEVELS_PATH + "levelTroisH.png"));
			level4H.loadFromFile(Paths.get(LEVELS_PATH + "levelQuatreH.png"));
			
			
			/************************ Set smoothing ************************/
			
			// Cubes
			cubeTextureBlue.setSmooth(true);
			cubeTextureGreen.setSmooth(true);
			cubeTextureOrange.setSmooth(true);
			cubeTexturePurple.setSmooth(true);
			cubeTextureRed.setSmooth(true);
			cubeTextureWhite.setSmooth(true);
			cubeTextureYellow.setSmooth(true);
			
			// Teleport
			cubeTextureTeleportBlue.setSmooth(true);
			cubeTextureTeleportGreen.setSmooth(true);
			cubeTextureTeleportOrange.setSmooth(true);
			cubeTextureTeleportPurple.setSmooth(true);
			cubeTextureTeleportRed.setSmooth(true);
			cubeTextureTeleportWhite.setSmooth(true);
			cubeTextureTeleportYellow.setSmooth(true);
			
			// Cell
			cellTexture.setSmooth(true);
			
			// Game
			playTexture.setSmooth(true);
			resetTexture.setSmooth(true);
			
			rotateLeft.setSmooth(true);
			rotateRight.setSmooth(true);
			
			// Actions
			forwardTexture.setSmooth(true);
			jumpTexture.setSmooth(true);
			turnLeftTexture.setSmooth(true);
			turnRightTexture.setSmooth(true);
			lightTexture.setSmooth(true);
			procedure1Texture.setSmooth(true);
			procedure2Texture.setSmooth(true);
			showerTexture.setSmooth(true);
			
			// Robot
			robotNorth.setSmooth(true);
			robotSouth.setSmooth(true);
			robotWest.setSmooth(true);
			robotEast.setSmooth(true);
			
			// Editor
			lightButtonTexture.setSmooth(true);
			lightButtonTextureRelief.setSmooth(true);
			teleportButtonTexture.setSmooth(true);
			teleportButtonTextureRelief.setSmooth(true);
			robotButtonTexture.setSmooth(true);
			robotButtonTextureRelief.setSmooth(true);
			homeButtonTextureRelief.setSmooth(true);
			
			blueSplash.setSmooth(true);
			orangeSplash.setSmooth(true);
			purpleSplash.setSmooth(true);
			redSplash.setSmooth(true);
						
			// Menu
			menuBg.setSmooth(true);
			menuJouer.setSmooth(true);
			menuQuitter.setSmooth(true);
			menuEditeur.setSmooth(true);
			menuCharger.setSmooth(true);
			menuLogo.setSmooth(true);
			
			menuBgH.setSmooth(true);
			menuJouerH.setSmooth(true);
			menuQuitterH.setSmooth(true);
			menuEditeurH.setSmooth(true);
			menuChargerH.setSmooth(true);
			menuLogoH.setSmooth(true);
			
			blinkTexture.setSmooth(true);
			
			// Level
			levelBases.setSmooth(true);
			levelProcedures.setSmooth(true);
			levelIf.setSmooth(true);
			levelBreak.setSmooth(true);
			levelFork.setSmooth(true);
			levelPointeurs.setSmooth(true);
			
			levelBg.setSmooth(true);
			levelBlock.setSmooth(true);
			levelTitle.setSmooth(true);
			level1.setSmooth(true);
			level2.setSmooth(true);
			level3.setSmooth(true);
			level4.setSmooth(true);
			
			levelBasesH.setSmooth(true);
			levelProceduresH.setSmooth(true);
			levelIfH.setSmooth(true);
			levelBreakH.setSmooth(true);
			levelForkH.setSmooth(true);
			levelPointeursH.setSmooth(true);
			
			level1H.setSmooth(true);
			level2H.setSmooth(true);
			level3H.setSmooth(true);
			level4H.setSmooth(true);
			
		} catch(IOException ex) {
		    //Ouch! something went wrong
		    ex.printStackTrace();
		}
	}
}
