package lightbot.graphics;

import lightbot.system.Colour;

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
	private Sprite buttonSprite;
	
	private Boolean light;
	
	public Editor(int nLine, int nColumn, int originX, int originY){
		this.line = nLine;
		this.column = nColumn;
		this.originX = originX;
		this.originY = originY;
		
		this.light = false;
		
		this.canva = new CanvaDisplay(this.line, this.column, this.originX, this.originY);
		this.grid = new GridDisplay(this.line, this.column, this.originX, this.originY);
		
		canva.initCanva();
	}
	
	public void initButtons(){		
		buttonSprite = new Sprite(Textures.buttonTexture);
		buttonSprite.scale(new Vector2f((float)0.2, (float)0.2));
		buttonSprite.setOrigin(Vector2f.div(new Vector2f(Textures.buttonTexture.getSize()), 2));
		buttonSprite.setPosition(50, 425);
		
		button = new Button(buttonSprite);
	}
	
	public void display(){		
		canva.printCanva();
		grid.printCubeList();		
	}
	
	public GridDisplay getGrid(){
		return this.grid;
	}
	
	public CellPosition isInside(Vector2i coord){
		CellPosition pos;
		pos = this.grid.isInside(coord);
		if(pos.isFound)
			return pos;
		
		pos = this.canva.isInside(coord);
		return pos;
	}
	
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
       	 				else
       	 					this.grid.addCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, Colour.GREEN);
       	 			}
       	 			else{
       	 				this.grid.addCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, Colour.WHITE);
       	 			}
       	 		}
       	 	}
       	 	else if(mouse.button == Mouse.Button.RIGHT){
       	 		if(pos.isFound() && pos.getLevel() > -1){
       	 			System.out.println("Remove cube on : \tLine : " + pos.getLine() + ", column : " + pos.getColumn() + ", level : " + pos.getLevel());
       	 			this.grid.removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
       	 		}
       	 	}
		}
	}
}
