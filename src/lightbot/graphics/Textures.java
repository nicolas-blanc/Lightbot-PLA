package lightbot.graphics;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;

public class Textures {
	public static final String RESSOURCE_PATH = "ressources/";
	public static final String NEW_ICONS_PATH = RESSOURCE_PATH + "new_icons/";
	
	public static Texture cubeTexture;
	public static Texture cubeTextureBlue;
	public static Texture cubeTextureRed;
	public static Texture cubeTextureYellow;
	public static Texture cubeTextureTeleport;
	public static Texture cellTexture;
	

	public static Texture playTexture;
	public static Texture resetTexture;
	
	public static Texture rotateLeft;
	public static Texture rotateRight;
	
	// actions textures
	public static Texture forwardTexture;
	public static Texture jumpTexture;
	public static Texture turnLeftTexture;
	public static Texture turnRightTexture;
	public static Texture lightTexture;
	public static Texture procedure1Texture;
	public static Texture procedure2Texture;
	
	
	// robot textures
	public static Texture robotNorth;
	public static Texture robotSouth;
	public static Texture robotWest;
	public static Texture robotEast;
	
	public static Texture blueSplash;
	
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
	
	//menu textures
	public static Texture menuButton;
	
	public static void initTextures(){
		cubeTexture = new Texture();
		cubeTextureBlue = new Texture();
		cubeTextureRed = new Texture();
		cubeTextureYellow = new Texture();
		cubeTextureTeleport = new Texture();
		cellTexture = new Texture();
		
		blueSplash = new Texture();
		
		
		rotateLeft = new Texture();
		rotateRight = new Texture();
		
		forwardTexture = new Texture();
		jumpTexture = new Texture();
		turnLeftTexture = new Texture();
		turnRightTexture = new Texture();
		lightTexture = new Texture();
		procedure1Texture = new Texture();
		procedure2Texture = new Texture();
		
		playTexture = new Texture();
		resetTexture = new Texture();
		
		robotNorth = new Texture();
		robotSouth = new Texture();
		robotWest = new Texture();
		robotEast = new Texture();
		
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
		
		menuButton = new Texture();
		
		try {
		    cubeTexture.loadFromFile(Paths.get(RESSOURCE_PATH+"whitecube.png"));
		    cubeTextureBlue.loadFromFile(Paths.get(RESSOURCE_PATH+"bluecube.png"));
		    cubeTextureRed.loadFromFile(Paths.get(RESSOURCE_PATH+"orangecube.png"));
		    cubeTextureYellow.loadFromFile(Paths.get(RESSOURCE_PATH+"yellowcube.png"));
		    cubeTextureTeleport.loadFromFile(Paths.get(RESSOURCE_PATH+"spacecube.png"));
		    cellTexture.loadFromFile(Paths.get(RESSOURCE_PATH+"canva.png"));
		    
		    rotateLeft.loadFromFile(Paths.get(NEW_ICONS_PATH + "rotateL.png"));
		    rotateRight.loadFromFile(Paths.get(NEW_ICONS_PATH + "rotateR.png"));
		    
		    blueSplash.loadFromFile(Paths.get(RESSOURCE_PATH+"bluesplash.png"));
		    
		    
		    // load action icons
		    forwardTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconForward.png"));
		    jumpTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconJump.png"));
		    turnLeftTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconTurnLeft.png"));
		    turnRightTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconTurnRight.png"));
		    lightTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconLight.png"));
		    
		    procedure1Texture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconProcedure1.png"));
		    procedure2Texture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconProcedure2.png"));
		    
		    // load play and reset icons
		    playTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconPlay.png"));
		    resetTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconReset.png"));
		    
		    // robot
		    robotNorth.loadFromFile(Paths.get(RESSOURCE_PATH + "robot/white_north.png"));
		    robotSouth.loadFromFile(Paths.get(RESSOURCE_PATH + "robot/white_south.png"));
		    robotWest.loadFromFile(Paths.get(RESSOURCE_PATH + "robot/white_west.png"));
		    robotEast.loadFromFile(Paths.get(RESSOURCE_PATH + "robot/white_east.png"));
			
			//menu
			menuButton.loadFromFile(Paths.get(RESSOURCE_PATH + "menuButton.png"));
			
			// Editor
			lightButtonTexture.loadFromFile(Paths.get(RESSOURCE_PATH+"light.png"));
			lightButtonTextureRelief.loadFromFile(Paths.get(RESSOURCE_PATH+"lightRelief.png"));
			loadButtonTexture.loadFromFile(Paths.get(RESSOURCE_PATH+"load.png"));
			loadButtonTextureRelief.loadFromFile(Paths.get(RESSOURCE_PATH+"loadRelief.png"));
			saveButtonTexture.loadFromFile(Paths.get(RESSOURCE_PATH+"save.png"));
			saveButtonTextureRelief.loadFromFile(Paths.get(RESSOURCE_PATH+"saveRelief.png"));
			teleportButtonTexture.loadFromFile(Paths.get(RESSOURCE_PATH+"teleport.png"));
			teleportButtonTextureRelief.loadFromFile(Paths.get(RESSOURCE_PATH+"teleportRelief.png"));
			robotButtonTexture.loadFromFile(Paths.get(RESSOURCE_PATH+"robot.png"));
			robotButtonTextureRelief.loadFromFile(Paths.get(RESSOURCE_PATH+"robotRelief.png"));
			
		    cubeTexture.setSmooth(true);
			cubeTextureBlue.setSmooth(true);
			cubeTextureRed.setSmooth(true);
			cubeTextureYellow.setSmooth(true);
			cubeTextureTeleport.setSmooth(true);
			cellTexture.setSmooth(true);
			
			blueSplash.setSmooth(true);
			
			rotateLeft.setSmooth(true);
			rotateRight.setSmooth(true);
			
			forwardTexture.setSmooth(true);
			jumpTexture.setSmooth(true);
			turnLeftTexture.setSmooth(true);
			turnRightTexture.setSmooth(true);
			lightTexture.setSmooth(true);
			procedure1Texture.setSmooth(true);
			procedure2Texture.setSmooth(true);
			
			playTexture.setSmooth(true);
			resetTexture.setSmooth(true);
			
			robotNorth.setSmooth(true);
			robotSouth.setSmooth(true);
			robotWest.setSmooth(true);
			robotEast.setSmooth(true);
			
			menuButton.setSmooth(true);
			
			lightButtonTexture.setSmooth(true);
			lightButtonTextureRelief.setSmooth(true);
			teleportButtonTexture.setSmooth(true);
			teleportButtonTextureRelief.setSmooth(true);
			robotButtonTexture.setSmooth(true);
			robotButtonTextureRelief.setSmooth(true);

		    
		} catch(IOException ex) {
		    //Ouch! something went wrong
		    ex.printStackTrace();
		}
	}
}
