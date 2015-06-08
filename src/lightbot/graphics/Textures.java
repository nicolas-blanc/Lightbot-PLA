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
	public static Texture cellTexture;
	

	public static Texture playTexture;
	public static Texture resetTexture;
	
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
	
	public static Texture buttonTexture;
	
	public static void initTextures(){
		cubeTexture = new Texture();
		cubeTextureBlue = new Texture();
		cubeTextureRed = new Texture();
		cubeTextureYellow = new Texture();
		cellTexture = new Texture();
		
		buttonTexture = new Texture();
		
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
		
		try {
		    cubeTexture.loadFromFile(Paths.get(RESSOURCE_PATH+"whitecube.png"));
		    cubeTextureBlue.loadFromFile(Paths.get(RESSOURCE_PATH+"bluecube.png"));
		    cubeTextureRed.loadFromFile(Paths.get(RESSOURCE_PATH+"orangecube.png"));
		    cubeTextureYellow.loadFromFile(Paths.get(RESSOURCE_PATH+"yellowcube.png"));
		    cellTexture.loadFromFile(Paths.get(RESSOURCE_PATH+"canva.png"));
		    buttonTexture.loadFromFile(Paths.get(RESSOURCE_PATH+"button.png"));
		    
		    // load action icons
		    forwardTexture.loadFromFile(Paths.get(NEW_ICONS_PATH + "iconForward.png"));
		    jumpTexture.loadFromFile(Paths.get(RESSOURCE_PATH + "iconJump.png"));
		    turnLeftTexture.loadFromFile(Paths.get(RESSOURCE_PATH + "iconTurnLeft.png"));
		    turnRightTexture.loadFromFile(Paths.get(RESSOURCE_PATH + "iconTurnRight.png"));
		    lightTexture.loadFromFile(Paths.get(RESSOURCE_PATH + "iconLight.png"));
		    
		    procedure1Texture.loadFromFile(Paths.get(RESSOURCE_PATH + "iconProcedure1.png"));
		    procedure2Texture.loadFromFile(Paths.get(RESSOURCE_PATH + "iconProcedure2.png"));
		    
		    // load play and reset icons
		    playTexture.loadFromFile(Paths.get(RESSOURCE_PATH + "iconPlay.png"));
		    resetTexture.loadFromFile(Paths.get(RESSOURCE_PATH + "iconReset.png"));
		    
		    // robot
		    robotNorth.loadFromFile(Paths.get(RESSOURCE_PATH + "robot/white_north.png"));
		    robotSouth.loadFromFile(Paths.get(RESSOURCE_PATH + "robot/white_south.png"));
		    robotWest.loadFromFile(Paths.get(RESSOURCE_PATH + "robot/white_west.png"));
		    robotEast.loadFromFile(Paths.get(RESSOURCE_PATH + "robot/white_east.png"));
		    
		} catch(IOException ex) {
		    //Ouch! something went wrong
		    ex.printStackTrace();
		}
	}
}
