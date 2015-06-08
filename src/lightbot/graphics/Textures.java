package lightbot.graphics;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;

public class Textures {
	public static final String RESSOURCES_PATH = "ressources/";
	
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
		
		try {
		    cubeTexture.loadFromFile(Paths.get(RESSOURCES_PATH+"whitecube.png"));
		    cubeTextureBlue.loadFromFile(Paths.get(RESSOURCES_PATH+"bluecube.png"));
		    cubeTextureRed.loadFromFile(Paths.get(RESSOURCES_PATH+"orangecube.png"));
		    cubeTextureYellow.loadFromFile(Paths.get(RESSOURCES_PATH+"yellowcube.png"));
		    cellTexture.loadFromFile(Paths.get(RESSOURCES_PATH+"canva.png"));
		    buttonTexture.loadFromFile(Paths.get(RESSOURCES_PATH+"button.png"));
		    
		    // load action icons
		    forwardTexture.loadFromFile(Paths.get(RESSOURCES_PATH + "iconForward.png"));
		    jumpTexture.loadFromFile(Paths.get(RESSOURCES_PATH + "iconJump.png"));
		    turnLeftTexture.loadFromFile(Paths.get(RESSOURCES_PATH + "iconTurnLeft.png"));
		    turnRightTexture.loadFromFile(Paths.get(RESSOURCES_PATH + "iconTurnRight.png"));
		    lightTexture.loadFromFile(Paths.get(RESSOURCES_PATH + "iconLight.png"));
		    
		    procedure1Texture.loadFromFile(Paths.get(RESSOURCES_PATH + "iconProcedure1.png"));
		    procedure2Texture.loadFromFile(Paths.get(RESSOURCES_PATH + "iconProcedure2.png"));
		    
		    // load play and reset icons
		    playTexture.loadFromFile(Paths.get(RESSOURCES_PATH + "iconPlay.png"));
		    resetTexture.loadFromFile(Paths.get(RESSOURCES_PATH + "iconReset.png"));
		    
		} catch(IOException ex) {
		    //Ouch! something went wrong
		    ex.printStackTrace();
		}
	}
}
