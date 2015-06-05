package lightbot.graphics;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class GridDisplay {
	
	private final String RESSOURCES_PATH = "ressources/";
	
	private RenderWindow window;
	
	private Texture cubeTexture;
	private Texture cellTexture;
	private ArrayList<Sprite> cubeList;
	private ArrayList<int[]> coord;
	
	public GridDisplay(RenderWindow window){
		this.window = window;
	}
	
	public void initSprite(){
		cubeList = new ArrayList<Sprite>();
		coord = new ArrayList<int[]>();
		
		cubeTexture = new Texture();
		cellTexture = new Texture();
		try {
		    //Try to load the texture from file "cube.png" and "cell.png"
		    cubeTexture.loadFromFile(Paths.get(RESSOURCES_PATH+"cube.png"));
		    cellTexture.loadFromFile(Paths.get(RESSOURCES_PATH+"cell.png"));
		} catch(IOException ex) {
		    //Ouch! something went wrong
		    ex.printStackTrace();
		}
	}
	
	public void addCube(int line, int column, int level){
		Sprite toAdd = new Sprite(cubeTexture);
		float decalX = cellTexture.getSize().x / 2;
		float decalY = cellTexture.getSize().y / 2;
		float sizeCubeY = cubeTexture.getSize().y;
		
		Vector2f decal;
		if(level == 0){
			if(line == column)
				decal = new Vector2f(-1, line*2*(decalY-1));
			else
				decal = new Vector2f((column-line)*(decalX-1), (line+column)*(decalY-1));
		}
		else{
			if(line == column)
				decal = new Vector2f(-1, (line+1)*2*(decalY-1)-(sizeCubeY-2)-(level-1)*decalY);
			else
				decal = new Vector2f((column-line)*(decalX-1), (line+column+2)*(decalY-1)-(sizeCubeY-2)-(level-1)*decalY);
		}
		
		Vector2f origin = Vector2f.div(new Vector2f(cubeTexture.getSize()), 2);
		
		toAdd.scale(new Vector2f((float)0.5, (float)0.5));
		toAdd.setOrigin(Vector2f.sub(origin, decal));
		toAdd.setPosition(320, 150);
		cubeList.add(toAdd);
	}
	
	public void addLevel(int line, int column, int level){
		if(level != 0)
			for(int i = 1; i<=level; i++)
				addCube(line, column, i-1);
	}
	
	public void printCubeList(){
		for(Sprite cube : cubeList)
			window.draw(cube);
	}
}
