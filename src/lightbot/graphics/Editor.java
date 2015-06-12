package lightbot.graphics;

import java.util.ArrayList;

import lightbot.system.Colour;
import lightbot.system.ParserJSON;
import lightbot.system.world.cell.EmptyCell;
import lightbot.system.world.cell.LightableCell;
import lightbot.system.world.cell.NormalCell;
import lightbot.system.world.cell.TeleportCell;
import lightbot.system.world.cell.TeleportColour;
import lightbot.tests.Main;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;

public class Editor implements DisplayMode{
	
	public ArrayList<Sprite> toDisplay;
	
	private Display display;
	private CanvaDisplay canva;
	
	//private Button blueSplash;
	//private Button orangeSplash;
	
	private Button blueSplash;
	private Button orangeSplash;
	private Button redSplash;
	private Button greenSplash;
	
	private Button teleportButton;
	private Button lightButton;
	private Button saveButton;
	
	private Boolean light;
	private Boolean teleport;
	
	private Colour colour;
	
	private int originX;
	private int originY;
	
	private final int MARGIN_LEFT = 15;
	private final int GRID_DISPLAY_SIZE = 730;
	private final int WINDOW_HEIGHT = 600;
	
	
	/********************************************************************************************/
	/*										Constructors										*/
	/********************************************************************************************/
	
	/* Creates an interface for the editor */
	public Editor(int size){
		originX = (GRID_DISPLAY_SIZE/2)+MARGIN_LEFT;
		originY = (WINDOW_HEIGHT/2-MARGIN_LEFT-(size*Textures.cellTexture.getSize().y)/2);
		
		display = new Display(size, originX, originY);
		
		this.toDisplay = new ArrayList<Sprite>();
		
		this.light = false;
		this.teleport = false;
		
		this.canva = new CanvaDisplay(size, originX, originY);
		
		initConstantDisplay();
	}
	
	/********************************************************************************************/
	/*										Accessors											*/
	/********************************************************************************************/
	
	/**
	 * Get the constant display
	 * @return A list of Sprite
	 */
	public ArrayList<Sprite> getConstantDisplay(){return this.toDisplay;}
	
	/**
	 * Get the grid from the editor
	 */
	public GridDisplay getGrid(){
		return this.display.gridDisplay;
		//return this.grid;
	}
	
	
	/********************************************************************************************/
	/*								Initialization functions									*/
	/********************************************************************************************/
	
	/**
	 * Initialize the constant display for an editor 
	 */
	public void initConstantDisplay(){
		
		
		// Add colour buttons
		Sprite blueSprite = new Sprite(Textures.blueSplash);
		Sprite orangeSprite = new Sprite(Textures.blueSplash);
		Sprite redSprite = new Sprite(Textures.blueSplash);
		Sprite greenSprite = new Sprite(Textures.blueSplash);
		
		blueSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+7), 20);
		orangeSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+(7*2)+Textures.blueSplash.getSize().y), 20);
		redSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+(7*3)+Textures.blueSplash.getSize().y*2), 20);
		greenSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+(7*4)+Textures.blueSplash.getSize().y*3), 20);
		
		blueSplash = new Button(blueSprite, null, null);
		orangeSplash = new Button(orangeSprite, null, null);
		redSplash = new Button(redSprite, null, null);
		greenSplash = new Button(greenSprite, null, null);
		
		// Add lightable button and teleport button
		Sprite teleportSprite = new Sprite(Textures.teleportButtonTextureRelief);
		Sprite lightSprite = new Sprite(Textures.lightButtonTextureRelief);
		teleportSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+(135/3)), 90);
		lightSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+(135/3)*2+Textures.lightButtonTextureRelief.getSize().y), 90);
		
		teleportButton = new Button(teleportSprite, Textures.teleportButtonTexture, Textures.teleportButtonTextureRelief);
		lightButton = new Button(lightSprite, Textures.lightButtonTexture, Textures.lightButtonTextureRelief);
		
		// Add save button
		Sprite saveSprite = new Sprite(Textures.saveButtonTextureRelief);
		saveSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+92.5), 160);
		
		saveButton = new Button(saveSprite, Textures.saveButtonTexture, Textures.saveButtonTextureRelief);
		
		
		
		canva.initCanva();
		toDisplay.addAll(canva.getCanva());
		
		int id = toDisplay.size();
		toDisplay.add(blueSprite);
		toDisplay.add(orangeSprite);
		toDisplay.add(redSprite);
		toDisplay.add(greenSprite);
		
		toDisplay.add(teleportSprite);
		toDisplay.add(lightSprite);
		
		toDisplay.add(saveSprite);
		
		blueSplash.setId(id);
		orangeSplash.setId(id+1);
		redSplash.setId(id+2);
		greenSplash.setId(id+3);
		
		teleportButton.setId(id+4);
		lightButton.setId(id+5);
		saveButton.setId(id+6);
	}
	
	/**
	 * Display the editor
	 */
	public void display(){		
		for(Sprite s : toDisplay)
			Main.window.draw(s);
		this.display.print();
	}
	
	/**
	 * Get if the mouse is inside a cell or not
	 */
	public CellPosition isInside(Vector2i coord){
		CellPosition pos;
		pos = this.display.gridDisplay.isInside(coord);
		if(pos.isFound)
			return pos;
		
		pos = this.canva.isInside(coord);
		return pos;
	}
	
	/**
	 * Event manager for editor
	 */
	public void eventManager(Event event){
		if(event.type == Event.Type.MOUSE_BUTTON_PRESSED){
			
			MouseButtonEvent mouse = event.asMouseButtonEvent();
       	 	CellPosition pos = isInside(mouse.position);
       	 	
       	 	if(mouse.button == Mouse.Button.LEFT){
       	 		
       	 		
       	 		if(blueSplash.isInside(mouse.position)){
       	 			colour = Colour.GREEN;
       	 			System.out.println("Button blue clicked");
       	 			
       	 			if(light){
       	 				lightButton.setTexture(Textures.lightButtonTextureRelief);
       	 				toDisplay.set(lightButton.getId(), lightButton.getSprite());
       	 				light = false;
       	 			}
       	 			if(teleport){
       	 				teleport = false;
	       	 			teleportButton.setTexture(Textures.teleportButtonTextureRelief);
	   	 				toDisplay.set(teleportButton.getId(), teleportButton.getSprite());
       	 			}
       	 		}
       	 		else if(teleportButton.isInside(mouse.position)){
       	 			teleportButton.changeTexture();
	 				toDisplay.set(teleportButton.getId(), teleportButton.getSprite());
	       	 		if(light){
	   	 				lightButton.setTexture(Textures.lightButtonTextureRelief);
	   	 				toDisplay.set(lightButton.getId(), lightButton.getSprite());
	   	 				light = false;
	   	 			}
		       	 	teleport = !teleport;
	   	 			colour = Colour.WHITE;
       	 		}
       	 		else if(lightButton.isInside(mouse.position)){
	       	 		lightButton.changeTexture();
   	 				toDisplay.set(lightButton.getId(), lightButton.getSprite());
   	 				teleportButton.setTexture(Textures.teleportButtonTextureRelief);
	 				toDisplay.set(teleportButton.getId(), teleportButton.getSprite());
	       	 		light = !light;
	   	 			teleport = false;
	   	 			colour = Colour.WHITE;
       	 		}
       	 		else if(saveButton.isInside(mouse.position)){
       	 			ParserJSON.serialize("grid1.json", this.display.gridDisplay.getGrid());
       	 		}
       	 		else if(pos.isFound()){
       	 			System.out.println("Add cube on : \t\tLine : " + pos.getLine() + ", column : " + pos.getColumn() + ", level : " + pos.getLevel());
       	 			if(light){
       	 				if(pos.getLevel() > -1){
       	 					this.display.gridDisplay.removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
       	 					LightableCell cell = new LightableCell(pos.getLine(), pos.getColumn(), pos.getLevel());
       	 					this.display.gridDisplay.addCube(cell);
       	 				}
       	 				else{
       	 					LightableCell cell = new LightableCell(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
       	 					this.display.gridDisplay.addCube(cell);
       	 					this.display.anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, true, false);
       	 					this.display.anim.updateSprite(this.display.gridDisplay.getGridSprites());
       	 				}
       	 			}
       	 			else if(teleport){
	       	 			if(pos.getLevel() > -1){
	       	 				TeleportCell cell = new TeleportCell(pos.getLine(), pos.getColumn(), pos.getLevel(), 0, 0, TeleportColour.TELEPORT);
	       	 				this.display.gridDisplay.removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
	       	 				this.display.gridDisplay.addCube(cell);
	   	 				}
	   	 				else{
	   	 					TeleportCell cell = new TeleportCell(pos.getLine(), pos.getColumn(), pos.getLevel()+1, 0, 0, TeleportColour.TELEPORT);
	   	 					this.display.gridDisplay.addCube(cell);
	   	 					this.display.anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, true, false);
	   	 					this.display.anim.updateSprite(this.display.gridDisplay.getGridSprites());
	   	 				}
       	 			}
       	 			else{
	       	 			if(this.display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof NormalCell 
	       	 					|| this.display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof EmptyCell){
	       	 				NormalCell cell = new NormalCell(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
	       	 				
	       	 				this.display.gridDisplay.addCube(cell);
	       	 				this.display.anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, true, false);
	       	 				this.display.anim.updateSprite(this.display.gridDisplay.getGridSprites());
	       	 			}
       	 			}
       	 		}
       	 	}
       	 	else if(mouse.button == Mouse.Button.RIGHT){
       	 		if(pos.isFound() && pos.getLevel() > -1){
       	 			System.out.println("Remove cube on : \tLine : " + pos.getLine() + ", column : " + pos.getColumn() + ", level : " + pos.getLevel());
       	 			this.display.anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel(), false, false);
       	 			this.display.gridDisplay.removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
       	 			this.display.anim.updateSprite(this.display.gridDisplay.getGridSprites());
       	 		}
       	 	}
		}
	}

	public void printGrid() {}
}
