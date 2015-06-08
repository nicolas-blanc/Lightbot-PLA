package lightbot.graphics;

import java.util.ArrayList;

import lightbot.system.Colour;
import lightbot.tests.Main;

import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;

public class Editor implements Display{
	
	private int line;
	private int column;
	
	private int originX;
	private int originY;
	
	private CanvaDisplay canva;
	private GridDisplay grid;
	
	private Button button;
	
	private Boolean light;
	
	public ArrayList<Sprite> toDisplay;
	
	private Animation anim;
	
	/* Creates an interface for the editor */
	public Editor(int nLine, int nColumn, int originX, int originY){
		this.toDisplay = new ArrayList<Sprite>();
		
		this.line = nLine;
		this.column = nColumn;
		this.originX = originX;
		this.originY = originY;
		
		this.light = false;
		
		this.canva = new CanvaDisplay(this.line, this.column, this.originX, this.originY);
		this.grid = new GridDisplay(this.line, this.column, this.originX, this.originY);
		anim = new Animation(this.grid.getGrid());
		
		initConstantDisplay();
	}
	
	/**
	 * Initialize the constant display for an editor 
	 */
	public void initConstantDisplay(){
		
		Sprite buttonSprite = new Sprite(Textures.buttonTexture);
		buttonSprite.scale(new Vector2f((float)0.2, (float)0.2));
		buttonSprite.setOrigin(Vector2f.div(new Vector2f(Textures.buttonTexture.getSize()), 2));
		buttonSprite.setPosition(50, 425);
		
		button = new Button(buttonSprite);
		
		canva.initCanva();
		toDisplay.addAll(canva.getCanva());
		toDisplay.add(buttonSprite);
	}
	
	/**
	 * Get the constant display
	 * @return A list of Sprite
	 */
	public ArrayList<Sprite> getConstantDisplay(){return this.toDisplay;}
	
	/**
	 * Display the editor
	 */
	public void display(){		
		for(Sprite s : toDisplay)
			Main.window.draw(s);
		grid.printCubeList();	
	}
	
	/**
	 * Get the grid from the editor
	 */
	public GridDisplay getGrid(){
		return this.grid;
	}
	
	/**
	 * Get if the mouse is inside a cell or not
	 */
	public CellPosition isInside(Vector2i coord){
		CellPosition pos;
		pos = this.grid.isInside(coord);
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
       	 		if(button.isInside(mouse.position)){
       	 			System.out.println("Button blue clicked");
       	 			light = !light;
       	 		}
       	 		else if(pos.isFound()){
       	 			System.out.println("Add cube on : \t\tLine : " + pos.getLine() + ", column : " + pos.getColumn() + ", level : " + pos.getLevel());
       	 			if(light){
       	 				if(pos.getLevel() > -1){
       	 					this.grid.removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
       	 					this.grid.addCube(pos.getLine(), pos.getColumn(), pos.getLevel(), Colour.GREEN);
       	 				}
       	 				else{
       	 					this.grid.addCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, Colour.GREEN);
       	 					anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, true, false);
       	 					anim.updateSprite(this.grid.getGrid());
       	 				}
       	 			}
       	 			else{
       	 				this.grid.addCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, Colour.WHITE);
       	 				anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, true, false);
	 					anim.updateSprite(this.grid.getGrid());
       	 			}
       	 		}
       	 	}
       	 	else if(mouse.button == Mouse.Button.RIGHT){
       	 		if(pos.isFound() && pos.getLevel() > -1){
       	 			System.out.println("Remove cube on : \tLine : " + pos.getLine() + ", column : " + pos.getColumn() + ", level : " + pos.getLevel());
       	 			anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel(), false, false);
       	 			this.grid.removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
       	 			anim.updateSprite(this.grid.getGrid());
       	 		}
       	 	}
		}
	}

	public void printGrid() {}
}
