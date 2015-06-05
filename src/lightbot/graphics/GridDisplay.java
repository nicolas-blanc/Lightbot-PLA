package lightbot.graphics;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class GridDisplay {
	
	public class PosCell{
		int line;
		int column;
		int level;
		boolean isFound;
		boolean isMaxCell;
		
		public PosCell(int line, int column, int level, boolean isFound){
			this.line = line;
			this.column = column;
			this.level = level;
			this.isFound = isFound;
		}
		
		public int getLine(){return this.line;}
		public int getColumn(){return this.column;}
		public int getLevel(){return this.level;}
		public boolean isFound(){return this.isFound;}
		public boolean isMaxCell(){return this.isMaxCell;}
	}
	
	/**************************************************************/
	
	public final String RESSOURCES_PATH = "ressources/";
	
	private RenderWindow window;
	
	public Texture cubeTexture;
	public Texture cellTexture;
	
	private Sprite[][][] cubes;
	private Sprite[][] canva;
	
	private ClickableCell[][][] cellClick;
	private ClickableCell[][] canvaClick;
	
	private int [][] levelMax;
	
	private int line;
	private int column;
	
	private int originX;
	private int originY;
	
	public GridDisplay(RenderWindow window, int line, int column, int originX, int originY){
		this.window = window;
		this.line = line;
		this.column = column;
		this.originX = originX;
		this.originY = originY;
		
		cubes = new Sprite[this.line][this.column][50];
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				for(int level = 0; level < 50; level++)
					cubes[l][c][level] = null;
		
		canva = new Sprite[this.line][this.column];
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
					canva[l][c]= null;
		
		canvaClick = new ClickableCell[this.line][this.column];
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
					canvaClick[l][c] = null;
		
		cellClick = new ClickableCell[this.line][this.column][50];
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				for(int level = 0; level < 50; level++)
					cellClick[l][c][level] = null;
		
		levelMax = new int[this.line][this.column];
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				levelMax[l][c] = -1;
	}
	
	public void initSprite(){
		
		cubeTexture = new Texture();
		cellTexture = new Texture();
		try {
		    //Try to load the texture from file "cube.png" and "cell.png"
		    cubeTexture.loadFromFile(Paths.get(RESSOURCES_PATH+"cube.png"));
		    cellTexture.loadFromFile(Paths.get(RESSOURCES_PATH+"canva.png"));
		} catch(IOException ex) {
		    //Ouch! something went wrong
		    ex.printStackTrace();
		}
	}
	
	public void addCel(int line, int column){
		Sprite toAdd = new Sprite(cellTexture);
		
		float decalX = cellTexture.getSize().x / 2;
		float decalY = cellTexture.getSize().y / 2;
		
		Vector2f decal;
		if(line == column)
			decal = new Vector2f(-1, 
					line*2*(decalY-1) 
						+ (Math.round(cubeTexture.getSize().y) 
						- (Math.round(cellTexture.getSize().y))));
		else
			decal = new Vector2f((column-line)*(decalX-1),
					(line+column)*(decalY-1)
					+ (Math.round(cubeTexture.getSize().y) 
					- (Math.round(cellTexture.getSize().y))));
		
		Vector2f origin = Vector2f.div(new Vector2f(cubeTexture.getSize()), 2);
		
		toAdd.scale(new Vector2f((float)0.5, (float)0.5));
		toAdd.setOrigin(Vector2f.sub(origin, decal));
		toAdd.setPosition(originX, originY);
		
		canva[line][column] = toAdd;
		canvaClick[line][column] = new ClickableCell(toAdd, cellTexture);
	}
	
	public void initCanva(){
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				addCel(l, c);
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
		toAdd.setPosition(originX, originY);
		
		cubes[line][column][level] = toAdd;
		levelMax[line][column] = level;
		cellClick[line][column][level] = new ClickableCell(toAdd, cellTexture);
	}
	
	public void removeCube(int line, int column, int level){
		cubes[line][column][level-1] = null;
		levelMax[line][column] = level-2;
		cellClick[line][column][level-1] = null;
	}
	
	public void addLevel(int line, int column, int level){
		for(int i = 1; i<=level; i++)
			addCube(line, column, i-1);
	}
	
	public void printCanva(){
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				window.draw(canva[l][c]);
	}
	
	public void printCubeList(){
		for(int l = 0; l < this.line; l++)
			for(int c = 0; c < this.column; c++)
				for(int level = 0; level < 50; level++)
					if(cubes[l][c][level] != null)
						window.draw(cubes[l][c][level]);
	}
	
	public PosCell isInside(Vector2i coord){
		PosCell pos = new PosCell(0, 0, 0, false);
		
		for(int l = this.line-1; l>=0; l--)
			for(int c = this.column-1; c>=0; c--)
				for(int level = levelMax[l][c]; level>=0; level--){
					if(cellClick[l][c][level].isInside(coord) && !isInsideDeadZone(coord, l, c, level)){
						pos.line = l;
						pos.column = c;
						pos.level = level;
						pos.isFound = true;
						pos.isMaxCell = (levelMax[l][c] == level);
						return pos;
					}
				}
		
		for(int l = this.line-1; l>=0; l--)
			for(int c = this.column-1; c>=0; c--)
				if(canvaClick[l][c].isInside(coord)){
					pos.line = l;
					pos.column = c;
					pos.level = -1;
					pos.isFound = true;
					pos.isMaxCell = true;
					return pos;
				}
		return pos;
	}
	
	public boolean isInsideDeadZone(Vector2i coord, int lineMin, int columnMin, int levelMin){
		for(int l = this.line-1; l>=lineMin; l--)
			for(int c = this.column-1; c>=columnMin; c--)
				for(int level = levelMax[l][c]; level>=levelMin; level--)
					if(cellClick[l][c][level].isInsideDeadZone(coord)){
						//System.out.println("Is inside dead zone --> l : " + l + ", c : " + c + ", level : " + level);
						return true;
					}
		return false;
	}
}
