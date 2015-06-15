package lightbot.graphics;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import lightbot.system.ParserJSON;
import lightbot.system.Robot;
import lightbot.system.world.Grid;
import lightbot.system.world.Position;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.cell.EmptyCell;
import lightbot.system.world.cell.LightableCell;
import lightbot.system.world.cell.NormalCell;
import lightbot.system.world.cell.TeleportCell;
import lightbot.system.world.cell.TeleportColour;
import lightbot.tests.Main;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;

public class Editor implements DisplayMode{
	
	public ArrayList<Sprite> toDisplay;
	
	private Display display;
	private CanvaDisplay canva;
	
	private static Button blueSplash;
	private static Button orangeSplash;
	private static Button redSplash;
	private static Button greenSplash;
	
	private static Button teleportButton;
	private static Button lightButton;
	private static Button saveButton;
	private static Button loadButton;
	
	private static Button robotButton;
	private static Button homeButton;
	
	private Button turnLeftButton;
	private Button turnRightButton;
	
	private Boolean light;
	private Boolean teleport;
	private Boolean robot;
	
	private Position teleport1;
	private Position teleport2;
	private Boolean firstTeleport = false;
	
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
		
		toDisplay = new ArrayList<Sprite>();
		
		light = false;
		teleport = false;
		robot = false;
		
		canva = new CanvaDisplay(size, originX, originY);
		
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
		Sprite loadSprite = new Sprite(Textures.loadButtonTextureRelief);
		saveSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+(135/3)), 160);
		loadSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+(135/3)*2+Textures.loadButtonTextureRelief.getSize().y), 160);
		
		saveButton = new Button(saveSprite, Textures.saveButtonTexture, Textures.saveButtonTextureRelief);
		loadButton = new Button(loadSprite, Textures.loadButtonTexture, Textures.loadButtonTextureRelief);
		
		// Robot button
		Sprite robotSprite = new Sprite(Textures.robotButtonTextureRelief);
		robotSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+(185/2)), 230);
		
		robotButton = new Button(robotSprite, Textures.robotButtonTexture, Textures.robotButtonTextureRelief);
		
		Sprite turnLeftSprite = new Sprite(Textures.rotateLeft);
		turnLeftSprite.setPosition(MARGIN_LEFT+35, (WINDOW_HEIGHT-MARGIN_LEFT-30-Textures.rotateLeft.getSize().y));
		
		Sprite turnRightSprite = new Sprite(Textures.rotateRight);
		turnRightSprite.setPosition((GRID_DISPLAY_SIZE+MARGIN_LEFT-35-Textures.rotateRight.getSize().y), (WINDOW_HEIGHT-MARGIN_LEFT-30-Textures.rotateRight.getSize().y));
		
		turnLeftButton = new Button(turnLeftSprite, null, null);
		turnRightButton = new Button(turnRightSprite, null, null);
		
		
		canva.initCanva();
		toDisplay.addAll(canva.getCanva());
		toDisplay.add(turnLeftSprite);
		toDisplay.add(turnRightSprite);
		
		int id = toDisplay.size();
		
		toDisplay.add(blueSprite);
		toDisplay.add(orangeSprite);
		toDisplay.add(redSprite);
		toDisplay.add(greenSprite);
		
		toDisplay.add(teleportSprite);
		toDisplay.add(lightSprite);
		
		toDisplay.add(saveSprite);
		toDisplay.add(loadSprite);
		toDisplay.add(robotSprite);
		
		blueSplash.setId(id);
		orangeSplash.setId(id+1);
		redSplash.setId(id+2);
		greenSplash.setId(id+3);
		
		teleportButton.setId(id+4);
		lightButton.setId(id+5);
		saveButton.setId(id+6);
		loadButton.setId(id+7);
		robotButton.setId(id+8);
	}
	
	/**
	 * Display the editor
	 */
	public void display(){		
		for(Sprite s : toDisplay)
			Main.window.draw(s);
		display.print();
	}
	
	/**
	 * Get if the mouse is inside a cell or not
	 */
	public CellPosition isInside(Vector2i coord){
		CellPosition pos;
		pos = display.gridDisplay.isInside(coord);
		if(pos.isFound)
			return pos;
		
		pos = canva.isInside(coord);
		return pos;
	}
	
	/**
	 * Event manager for editor
	 */
	public void eventManager(Event event){
		if(event.type == Event.Type.MOUSE_BUTTON_PRESSED){
			
			JFileChooser dialog;
			LoadSaveFilter filter;
			MouseButtonEvent mouse = event.asMouseButtonEvent();
       	 	CellPosition pos = isInside(mouse.position);
       	 	
       	 	switch(getEvent(mouse)){
				case GRID_ADD:
					if(pos.isFound()){
						if(robot){
							if(!(display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof EmptyCell))
								display.displayRobot(pos.getLine(), pos.getColumn(), originX, originY);
						}
						else{
							System.out.println("Add cube on : \t\tLine : " + pos.getLine() + ", column : " + pos.getColumn() + ", level : " + pos.getLevel());
		       	 			if(light){     	 			
		       	 				if(pos.getLevel() > -1){
		       	 					display.gridDisplay.removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
		       	 					LightableCell cell = new LightableCell(pos.getLine(), pos.getColumn(), pos.getLevel());
		       	 					display.gridDisplay.addCube(cell);
		       	 				}
		       	 				else{
		       	 					LightableCell cell = new LightableCell(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
		       	 					display.gridDisplay.addCube(cell);
		       	 					display.anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, true, false);
		       	 					display.anim.updateSprite(display.gridDisplay.getGridSprites());
		       	 				}
		       	 			}
		       	 			else if(teleport){
		       	 				
			       	 			if(!firstTeleport){
						       	 	disableAllButton();
					       	 		
					       	 		teleport1 = new Position(pos.getLine(), pos.getColumn());
					       	 		teleport2 = null;
		       	 				}
		       	 			
		       	 				if(pos.getLevel() > -1){
			       	 				TeleportCell cell = new TeleportCell(pos.getLine(), pos.getColumn(), pos.getLevel(), -1, -1, TeleportColour.TELEPORT);
			       	 				display.gridDisplay.removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
			       	 				display.gridDisplay.addCube(cell);
			   	 				}
			   	 				else{
			   	 					TeleportCell cell = new TeleportCell(pos.getLine(), pos.getColumn(), pos.getLevel()+1, -1, -1, TeleportColour.TELEPORT);
			   	 					display.gridDisplay.addCube(cell);
			   	 					display.anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, true, false);
			   	 					display.anim.updateSprite(display.gridDisplay.getGridSprites());
			   	 				}
		       	 				
			       	 			if(!firstTeleport)
			       	 				firstTeleport = true;
			       	 			else if(firstTeleport && (pos.getLine() != teleport1.getX() || pos.getColumn() != teleport1.getY())){
			       	 				enableAllButton();
					       	 		firstTeleport = false;
					       	 		
					       	 		teleport2 = new Position(pos.getLine(), pos.getColumn());
					       	 		
					       	 		// Get teleport cells
					       	 		TeleportCell teleportCell1 = (TeleportCell)display.gridDisplay.grid.getCell(teleport1.getX(), teleport1.getY());
					       	 		TeleportCell teleportCell2 = (TeleportCell)display.gridDisplay.grid.getCell(teleport2.getX(), teleport2.getY());
					       	 		
					       	 		teleportCell1.setDestXY(teleport2.getX(), teleport2.getY());
					       	 		teleportCell2.setDestXY(teleport1.getX(), teleport1.getY());
					       	 		
					       	 		display.gridDisplay.grid.setCell(teleportCell1);
					       	 		display.gridDisplay.grid.setCell(teleportCell2);
		       	 				}
		       	 			}
		       	 			else{
			       	 			if(display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof NormalCell 
			       	 					|| display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof EmptyCell){
			       	 				NormalCell cell = new NormalCell(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
			       	 				
			       	 				display.gridDisplay.addCube(cell);
			       	 				display.anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, true, false);
			       	 				display.anim.updateSprite(display.gridDisplay.getGridSprites());
			       	 			}
		       	 			}
		       	 			
		       	 			if(pos.getColumn() == Robot.wheatley.getColumn() && pos.getLine() == Robot.wheatley.getLine())
		       	 				display.displayRobot(pos.getLine(), pos.getColumn(), originX, originY);
						}
					}
					break;
					
				case GRID_DELETE:
					if(pos.isFound() && pos.getLevel() > -1){
						Cell cell = display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn());
						
						System.out.println("Remove cube on : \tLine : " + pos.getLine() + ", column : " + pos.getColumn() + ", level : " + pos.getLevel());
	       	 			display.anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel(), false, false);
	       	 			display.gridDisplay.removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
	       	 			display.anim.updateSprite(display.gridDisplay.getGridSprites());
	       	 			
						if(cell instanceof TeleportCell){
							if(firstTeleport){
								firstTeleport = false;
								enableAllButton();
							}
							
							if(((TeleportCell)cell).getDestX() != -1 && ((TeleportCell)cell).getDestY() != -1){
								TeleportCell newCell = (TeleportCell)display.gridDisplay.grid.getCell(((TeleportCell)cell).getDestX(), ((TeleportCell)cell).getDestY());
								display.anim.addRemoveCube(newCell.getX(), newCell.getY(), newCell.getHeight(), false, false);
			       	 			display.gridDisplay.removeCube(newCell.getX(), newCell.getY(), newCell.getHeight()+1);
			       	 			display.anim.updateSprite(display.gridDisplay.getGridSprites());
							}
						}
						
						if(pos.getColumn() == Robot.wheatley.getColumn() && pos.getLine() == Robot.wheatley.getLine())
							if(display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof EmptyCell)
								display.deleteRobot();
							else
								display.displayRobot(pos.getLine(), pos.getColumn(), originX, originY);
					}
					break;
					
				case LIGHT:
					lightButton.changeTexture();
   	 				toDisplay.set(lightButton.getId(), lightButton.getSprite());
   	 				
   	 				if(teleport){
	   	 				teleportButton.reset();
		 				toDisplay.set(teleportButton.getId(), teleportButton.getSprite());
		 				teleport = false;
   	 				}
	   	 			if(robot){
	   	 				robotButton.reset();
	   	 				toDisplay.set(robotButton.getId(), robotButton.getSprite());
	   	 				robot = false;
	   	 			}
	       	 		light = !light;
					break;
					
				case SAVE:
					if(display.robotIsDisplayed){
						saveButton.changeTexture();
						toDisplay.set(saveButton.getId(), saveButton.getSprite());
						Main.window.clear(Color.WHITE);
						display();
						Main.window.display();
						
						dialog = new JFileChooser();
						
						filter = new LoadSaveFilter(new String[]{"json"}, "Fichier texte au format JSON (*.json)");
						dialog.setAcceptAllFileFilterUsed(false);
						dialog.addChoosableFileFilter(filter);
						
						if (dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						  File file = dialog.getSelectedFile();
						  
						  String fileName = file.getAbsolutePath();
						  
						  int i = fileName.lastIndexOf(".");
						  if(i != -1 && fileName.charAt(i-1) != '/'){
							  fileName = fileName.substring(0, i); 
						  }
						  //System.out.println(fileName + ".json");
						  ParserJSON.serialize(fileName+".json", display.gridDisplay.grid);
						  display.gridDisplay.grid.printGrid();
						}
						saveButton.changeTexture();
						toDisplay.set(saveButton.getId(), saveButton.getSprite());
					}
					else{
						JOptionPane.showMessageDialog(null, "Le robot n'est pas affiché, impossible de sauvegarder.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					break;
					
				case LOAD:
					loadButton.changeTexture();
					toDisplay.set(loadButton.getId(), loadButton.getSprite());
					Main.window.clear(Color.WHITE);
					display();
					Main.window.display();
					
					dialog = new JFileChooser();
					
					filter = new LoadSaveFilter(new String[]{"json"}, "Fichier texte au format JSON (*.json)");
					dialog.setAcceptAllFileFilterUsed(false);
					dialog.addChoosableFileFilter(filter);
					
					if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					  File file = dialog.getSelectedFile();
					  Grid toOpen = ParserJSON.deserialize(file.getAbsolutePath());
					  originX = (GRID_DISPLAY_SIZE/2)+MARGIN_LEFT;
					  originY = (WINDOW_HEIGHT/2-MARGIN_LEFT-(toOpen.getSize()*Textures.cellTexture.getSize().y)/2);
					  display.reinit(toOpen, originX, originY);
					  System.out.println(file.getAbsolutePath());
					}
					loadButton.changeTexture();
					toDisplay.set(loadButton.getId(), loadButton.getSprite());
					break;
					
				case ROBOT:
					robotButton.changeTexture();
   	 				toDisplay.set(robotButton.getId(), robotButton.getSprite());
   	 				
   	 				if(teleport){
	   	 				teleportButton.reset();
		 				toDisplay.set(teleportButton.getId(), teleportButton.getSprite());
		 				teleport = false;
   	 				}
	   	 			if(light){
	   	 				lightButton.reset();
	   	 				toDisplay.set(lightButton.getId(), lightButton.getSprite());
	   	 				light = false;
	   	 			}
	       	 		robot = !robot;
					break;
					
				case TURN_LEFT:
					display.rotate(0);
					break;
				case TURN_RIGHT:
					display.rotate(1);
					break;
					
				case SPLASH_BLUE:
					break;
				case SPLASH_GREEN:
					break;
				case SPLASH_ORANGE:
					break;
				case SPLASH_RED:
					break;
				case TELEPORT:
					teleportButton.changeTexture();
	 				toDisplay.set(teleportButton.getId(), teleportButton.getSprite());
	 				
	       	 		if(light){
	   	 				lightButton.reset();
	   	 				toDisplay.set(lightButton.getId(), lightButton.getSprite());
	   	 				light = false;
	   	 			}
		       	 	if(robot){
	   	 				robotButton.reset();
	   	 				toDisplay.set(robotButton.getId(), robotButton.getSprite());
	   	 				robot = false;
	   	 			}
		       	 	teleport = !teleport;
					break;
					
				default:
					break;
       	 	}
		}
	}
	
	public void disableAllButton(){
		blueSplash.disable();
 		orangeSplash.disable();
 		redSplash.disable();
 		greenSplash.disable();
 		
 		teleportButton.disable();
 		lightButton.disable();
 		saveButton.disable();
 		loadButton.disable();
 		robotButton.disable();
 		
 		turnLeftButton.disable();
		turnRightButton.disable();
	}
	
	public void enableAllButton(){
		blueSplash.enable();
 		orangeSplash.enable();
 		redSplash.enable();
 		greenSplash.enable();
 		
 		teleportButton.enable();
 		lightButton.enable();
 		saveButton.enable();
 		loadButton.enable();
 		robotButton.enable();
 		
 		turnLeftButton.enable();
		turnRightButton.enable();
	}
	
	public EditorEvent getEvent(MouseButtonEvent mouse){
		if(mouse.button == Mouse.Button.LEFT){
			if(blueSplash.isInside(mouse.position))
				return EditorEvent.SPLASH_BLUE;
			else if(orangeSplash.isInside(mouse.position))
				return EditorEvent.SPLASH_ORANGE;
			else if(redSplash.isInside(mouse.position))
				return EditorEvent.SPLASH_RED;
			else if(greenSplash.isInside(mouse.position))
				return EditorEvent.SPLASH_GREEN;
			else if(teleportButton.isInside(mouse.position))
				return EditorEvent.TELEPORT;
			else if(lightButton.isInside(mouse.position))
				return EditorEvent.LIGHT;
			else if(saveButton.isInside(mouse.position))
				return EditorEvent.SAVE;
			else if(loadButton.isInside(mouse.position))
				return EditorEvent.LOAD;
			else if(robotButton.isInside(mouse.position))
				return EditorEvent.ROBOT;
			else if(turnLeftButton.isInside(mouse.position))
				return EditorEvent.TURN_LEFT;
			else if(turnRightButton.isInside(mouse.position))
				return EditorEvent.TURN_RIGHT;
			else
				return EditorEvent.GRID_ADD;
		}
		else if(mouse.button == Mouse.Button.RIGHT){
			return EditorEvent.GRID_DELETE;
		}
		else
			return null;
	}
	
	
	/**
	 * Filter for JFileChooser, dialog window
	 */
	public class LoadSaveFilter extends FileFilter{
		String[] suffix;
		String  description;
		
		public LoadSaveFilter(String[] suffix, String description){
			this.suffix = suffix;
			this.description = description;
		}
		   
		boolean belongTo(String suffix){
			for( int i = 0; i<this.suffix.length; ++i)
				if(suffix.equals(this.suffix[i]))
					return true;
			return false;
		}
		
		public boolean accept(File file){
			if (file.isDirectory())  
				return true;
			
			String suffix = null;
			String str = file.getName();
			int i = str.lastIndexOf('.');
			
			if(i > 0 &&  i < str.length() - 1)
				suffix=str.substring(i+1).toLowerCase();
			
			return suffix != null && belongTo(suffix);
		}
		
		public String getDescription(){
			return this.description;
		}
	}

	public void printGrid() {}
}
