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
	
	public static Texture buttonTexture;
	
	public static void initTextures(){
		cubeTexture = new Texture();
		cubeTextureBlue = new Texture();
		cubeTextureRed = new Texture();
		cubeTextureYellow = new Texture();
		cellTexture = new Texture();
		
		buttonTexture = new Texture();
		
		try {
		    cubeTexture.loadFromFile(Paths.get(RESSOURCES_PATH+"whitecube.png"));
		    cubeTextureBlue.loadFromFile(Paths.get(RESSOURCES_PATH+"bluecube.png"));
		    cubeTextureRed.loadFromFile(Paths.get(RESSOURCES_PATH+"orangecube.png"));
		    cubeTextureYellow.loadFromFile(Paths.get(RESSOURCES_PATH+"yellowcube.png"));
		    cellTexture.loadFromFile(Paths.get(RESSOURCES_PATH+"canva.png"));
		    buttonTexture.loadFromFile(Paths.get(RESSOURCES_PATH+"button.png"));
		} catch(IOException ex) {
		    //Ouch! something went wrong
		    ex.printStackTrace();
		}
	}
}
