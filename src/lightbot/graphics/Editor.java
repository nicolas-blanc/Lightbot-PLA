package lightbot.graphics;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.MaskFormatter;

import lightbot.LightCore;
import lightbot.system.Colour;
import lightbot.system.ParserJSON;
import lightbot.system.RelativeDirection;
import lightbot.system.Robot;
import lightbot.system.TeleportColour;
import lightbot.system._Executable;
import lightbot.system.action.Forward;
import lightbot.system.action.Jump;
import lightbot.system.action.Light;
import lightbot.system.action.Turn;
import lightbot.system.world.Grid;
import lightbot.system.world.Level;
import lightbot.system.world.Position;
import lightbot.system.world.cell.Cell;
import lightbot.system.world.cell.ColoredCell;
import lightbot.system.world.cell.EmptyCell;
import lightbot.system.world.cell.LightableCell;
import lightbot.system.world.cell.NormalCell;
import lightbot.system.world.cell.TeleportCell;

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
	private static Button purpleSplash;
	private static Button redSplash;
	
	private static Button teleportButton;
	private static Button lightButton;
	private static Button saveButton;
	private static Button loadButton;
	
	private static Button robotButton;
	private static Button homeButton;
	
	private Button turnLeftButton;
	private Button turnRightButton;
	
	private Boolean light = false;
	private Boolean teleport = false;
	private Boolean robot = false;
	
	private Boolean blue = false;
	private Boolean orange = false;
	private Boolean purple = false;
	private Boolean red = false;
	
	
	private Colour colour;
	
	private Position teleport1;
	private Position teleport2;
	private Boolean firstTeleport = false;
	
	private TeleportColour nextColour = TeleportColour.TELEPORT;
	private ArrayList<TeleportColour> usedColour = new ArrayList<TeleportColour>();
	
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
		
		canva = new CanvaDisplay(size, originX, originY);
		
		initConstantDisplay();
		colour = Colour.WHITE;
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
		Sprite orangeSprite = new Sprite(Textures.orangeSplash);
		Sprite purpleSprite = new Sprite(Textures.purpleSplash);
		Sprite redSprite = new Sprite(Textures.redSplash);
		
		blueSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+7), 20);
		orangeSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+(7*2)+Textures.blueSplash.getSize().y), 20);
		purpleSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+(7*3)+Textures.blueSplash.getSize().y*2), 20);
		redSprite.setPosition((float)(GRID_DISPLAY_SIZE+MARGIN_LEFT+(7*4)+Textures.blueSplash.getSize().y*3), 20);
		
		blueSplash = new Button(blueSprite, null, null);
		orangeSplash = new Button(orangeSprite, null, null);
		purpleSplash = new Button(purpleSprite, null, null);
		redSplash = new Button(redSprite, null, null);
		
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
		
		Sprite homeSprite = new Sprite(Textures.homeButtonTextureRelief);
		homeSprite.setPosition(MARGIN_LEFT, MARGIN_LEFT);
		
		homeButton = new Button(homeSprite, null, null);
		
		canva.initCanva();
		toDisplay.addAll(canva.getCanva());
		toDisplay.add(turnLeftSprite);
		toDisplay.add(turnRightSprite);
		
		int id = toDisplay.size();
		
		toDisplay.add(blueSprite);
		toDisplay.add(orangeSprite);
		toDisplay.add(purpleSprite);
		toDisplay.add(redSprite);
		
		toDisplay.add(teleportSprite);
		toDisplay.add(lightSprite);
		
		toDisplay.add(saveSprite);
		toDisplay.add(loadSprite);
		toDisplay.add(robotSprite);
		
		toDisplay.add(homeSprite);
		
		blueSplash.setId(id);
		orangeSplash.setId(id+1);
		purpleSplash.setId(id+2);
		redSplash.setId(id+3);
		
		teleportButton.setId(id+4);
		lightButton.setId(id+5);
		saveButton.setId(id+6);
		loadButton.setId(id+7);
		robotButton.setId(id+8);
		
		homeButton.setId(id+9);
	}
	
	/**
	 * Display the editor
	 */
	public void display(){		
		for(Sprite s : toDisplay)
			LightCore.window.draw(s);
		display.print();
	}
	
	public void nextColour(){
		if(usedColour.size() != TeleportColour.values().length){
			TeleportColour colour;
			do{
				colour = TeleportColour.randomColour();
			}while(usedColour.contains(colour));
			nextColour = colour;
		}
		else{
			this.nextColour = null;
		}
	}

	public void printGrid() {}
	
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
	
	
	/********************************************************************************************/
	/*										Event Manager										*/
	/********************************************************************************************/
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
		       	 				if(!(display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof TeleportCell) 
		       	 							&& !(display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof ColoredCell)
		       	 							&& !(display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof LightableCell)){
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
		       	 			}
		       	 			else if(teleport){
		       	 				if(nextColour != null){
		       	 					if(!(display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof TeleportCell) 
		       	 							&& !(display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof ColoredCell)
		       	 							&& !(display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof LightableCell)){
					       	 			if(!firstTeleport){
								       	 	disableAllButton();
							       	 		
							       	 		teleport1 = new Position(pos.getLine(), pos.getColumn());
							       	 		teleport2 = null;
				       	 				}
				       	 			
				       	 				if(pos.getLevel() > -1){
					       	 				TeleportCell cell = new TeleportCell(pos.getLine(), pos.getColumn(), pos.getLevel(), -1, -1, nextColour);
					       	 				display.gridDisplay.removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
					       	 				display.gridDisplay.addCube(cell);
					   	 				}
					   	 				else{
					   	 					TeleportCell cell = new TeleportCell(pos.getLine(), pos.getColumn(), pos.getLevel()+1, -1, -1, nextColour);
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
							       	 		
							       	 		usedColour.add(nextColour);
							       	 		nextColour();
				       	 				}
		       	 					}
		       	 				}
		       	 			}
		       	 			else{
			       	 			if(display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof NormalCell 
			       	 					|| display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof EmptyCell
			       	 					|| display.gridDisplay.grid.getCell(pos.getLine(), pos.getColumn()) instanceof ColoredCell){
			       	 				if(blue || orange || purple || red){
				       	 				if(pos.getLevel() > -1){
				       	 					ColoredCell cell = new ColoredCell(pos.getLine(), pos.getColumn(), pos.getLevel(), colour);
				       	 					display.gridDisplay.removeCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
				       	 					display.gridDisplay.addCube(cell);
				       	 				}
				       	 				else{
				       	 					ColoredCell cell = new ColoredCell(pos.getLine(), pos.getColumn(), pos.getLevel()+1, colour);
				       	 					display.gridDisplay.addCube(cell);
				       	 					display.anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, true, false);
				       	 					display.anim.updateSprite(display.gridDisplay.getGridSprites());
				       	 				}
			       	 					
			       	 				}
			       	 				else{
			       	 					NormalCell cell = new NormalCell(pos.getLine(), pos.getColumn(), pos.getLevel()+1);
				       	 				display.gridDisplay.addCube(cell);
				       	 				display.anim.addRemoveCube(pos.getLine(), pos.getColumn(), pos.getLevel()+1, true, false);
			       	 				}			       	 				
			       	 				display.anim.updateSprite(display.gridDisplay.getGridSprites());
			       	 			}
		       	 			}
		       	 			
		       	 			if(pos.getColumn() == Robot.wheatley.getColumn() && pos.getLine() == Robot.wheatley.getLine() && pos.getLevel() > -1)
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
							TeleportColour currentTeleportColour = ((TeleportCell)cell).getColour();
							if(firstTeleport && currentTeleportColour.equals(nextColour)){
								firstTeleport = false;
								enableAllButton();
							}
							
							if(((TeleportCell)cell).getDestX() != -1 && ((TeleportCell)cell).getDestY() != -1){
								TeleportCell newCell = (TeleportCell)display.gridDisplay.grid.getCell(((TeleportCell)cell).getDestX(), ((TeleportCell)cell).getDestY());
								display.anim.addRemoveCube(newCell.getX(), newCell.getY(), newCell.getHeight(), false, false);
			       	 			display.gridDisplay.removeCube(newCell.getX(), newCell.getY(), newCell.getHeight()+1);
			       	 			display.anim.updateSprite(display.gridDisplay.getGridSprites());
							}
							
							usedColour.remove(currentTeleportColour);
							if(currentTeleportColour.equals(nextColour))
								nextColour();
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
   	 				
   	 				resetButton(EditorEvent.LIGHT);
   	 			
	       	 		light = !light;
	       	 		if(!light)
	       	 			colour = Colour.WHITE;
					break;
					
				case SAVE:
					if(display.robotIsDisplayed){
						saveButton.changeTexture();
						toDisplay.set(saveButton.getId(), saveButton.getSprite());
						LightCore.window.clear(Color.WHITE);
						display();
						LightCore.window.display();
						
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
						  
						  // Print a selection menu for list of actions
						  ActionSelector selec = new ActionSelector();
						  while(!selec.isValid && !selec.isAborted){
							  System.out.print("");
						  }
						  
						  if(selec.isValid){
							  ArrayList<_Executable> listOfActions = new ArrayList<_Executable>();
							  if(selec.checkForward.getState())
								  listOfActions.add(new Forward());
							  if(selec.checkJump.getState())
								  listOfActions.add(new Jump());
							  if(selec.checkTurnRight.getState())
								  listOfActions.add(new Turn(RelativeDirection.RIGHT, Colour.WHITE));
							  if(selec.checkTurnLeft.getState())
								  listOfActions.add(new Turn(RelativeDirection.LEFT, Colour.WHITE));
							  listOfActions.add(new Light());
							  
							  int numbOfMain = Integer.parseInt(selec.mainLimit.getText());
							  int numbOfProc1 = Integer.parseInt(selec.p1Limit.getText());
							  int numbOfProc2 = Integer.parseInt(selec.p2Limit.getText());
							  
							  Level level = new Level(display.gridDisplay.grid, listOfActions, selec.checkP1.getState(), selec.checkP2.getState(), numbOfMain, numbOfProc1, numbOfProc2);
							  ParserJSON.serialize(fileName+".json", level);
							  display.gridDisplay.grid.printGrid();
						  }
						  
						  selec.dispose();
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
					LightCore.window.clear(Color.WHITE);
					display();
					LightCore.window.display();
					
					dialog = new JFileChooser();
					
					filter = new LoadSaveFilter(new String[]{"json"}, "Fichier texte au format JSON (*.json)");
					dialog.setAcceptAllFileFilterUsed(false);
					dialog.addChoosableFileFilter(filter);
					
					if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					  File file = dialog.getSelectedFile();
					  Grid toOpen = ParserJSON.deserialize(file.getAbsolutePath()).getGrid();
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
   	 				
   	 				resetButton(EditorEvent.ROBOT);
				   	
	       	 		robot = !robot;
	       	 		if(!robot)
	       	 			colour = Colour.WHITE;
					break;
					
				case HOME:
					LightCore.menu = true;
					LightCore.editor = false;
					break;
					
				case TURN_LEFT:
					display.rotate(0);
					break;
				case TURN_RIGHT:
					display.rotate(1);
					break;
					
				case SPLASH_BLUE:
					blueSplash.changeTexture();
					toDisplay.set(blueSplash.getId(), blueSplash.getSprite());
					
					colour = Colour.BLUE;
					resetButton(EditorEvent.SPLASH_BLUE);
					
					blue = !blue;
					if(!blue)
						colour = Colour.WHITE;
					break;
					
				case SPLASH_ORANGE:
					orangeSplash.changeTexture();
					toDisplay.set(orangeSplash.getId(), orangeSplash.getSprite());
					
					colour = Colour.ORANGE;
					resetButton(EditorEvent.SPLASH_ORANGE);
					
					orange = !orange;
					if(!orange)
						colour = Colour.WHITE;
					break;
					
				case SPLASH_PURPLE:
					purpleSplash.changeTexture();
					toDisplay.set(purpleSplash.getId(), purpleSplash.getSprite());
					
					colour = Colour.PURPLE;
					resetButton(EditorEvent.SPLASH_PURPLE);
					
					purple = !purple;
					if(!purple)
						colour = Colour.WHITE;
					break;
					
				case SPLASH_RED:
					redSplash.changeTexture();
					toDisplay.set(redSplash.getId(), redSplash.getSprite());
					
					colour = Colour.RED;
					resetButton(EditorEvent.SPLASH_RED);
					
					red = !red;
					if(!red)
						colour = Colour.WHITE;
					break;
					
				case TELEPORT:
					teleportButton.changeTexture();
	 				toDisplay.set(teleportButton.getId(), teleportButton.getSprite());
	 				
	       	 		resetButton(EditorEvent.TELEPORT);
	       	 		
		       	 	teleport = !teleport;
		       	 	if(!teleport)
						colour = Colour.WHITE;
					break;
					
				default:
					break;
       	 	}
		}
	}
	
	public EditorEvent getEvent(MouseButtonEvent mouse){
		if(mouse.button == Mouse.Button.LEFT){
			if(blueSplash.isInside(mouse.position))
				return EditorEvent.SPLASH_BLUE;
			else if(orangeSplash.isInside(mouse.position))
				return EditorEvent.SPLASH_ORANGE;
			else if(purpleSplash.isInside(mouse.position))
				return EditorEvent.SPLASH_PURPLE;
			else if(redSplash.isInside(mouse.position))
				return EditorEvent.SPLASH_RED;
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
			else if(homeButton.isInside(mouse.position))
				return EditorEvent.HOME;
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
	
	/********************************************************************************************/
	/*								Procedures on Buttons										*/
	/********************************************************************************************/
	public void disableAllButton(){
		blueSplash.disable();
 		orangeSplash.disable();
 		redSplash.disable();
 		purpleSplash.disable();
 		
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
 		purpleSplash.enable();
 		
 		teleportButton.enable();
 		lightButton.enable();
 		saveButton.enable();
 		loadButton.enable();
 		robotButton.enable();
 		
 		turnLeftButton.enable();
		turnRightButton.enable();
	}
	
	public void resetButton(EditorEvent event){
		if(event != EditorEvent.LIGHT)
			if(light){
				lightButton.reset();
				toDisplay.set(lightButton.getId(), lightButton.getSprite());
				light = false;
			}
		if(event != EditorEvent.ROBOT)
			if(robot){
 				robotButton.reset();
 				toDisplay.set(robotButton.getId(), robotButton.getSprite());
 				robot = false;
 			}
		if(event != EditorEvent.TELEPORT)
			if(teleport){
 				teleportButton.reset();
 				toDisplay.set(teleportButton.getId(), teleportButton.getSprite());
 				teleport = false;
			}
		if(event != EditorEvent.SPLASH_BLUE)
			if(blue){
	 			blueSplash.reset();
				toDisplay.set(blueSplash.getId(), blueSplash.getSprite());
				blue = false;
			}
		if(event != EditorEvent.SPLASH_ORANGE)
			if(orange){
	 			orangeSplash.reset();
				toDisplay.set(orangeSplash.getId(), orangeSplash.getSprite());
				orange = false;
			}
		if(event != EditorEvent.SPLASH_PURPLE)
			if(purple){
	 			purpleSplash.reset();
				toDisplay.set(purpleSplash.getId(), purpleSplash.getSprite());
				purple = false;
			}
	 	if(event != EditorEvent.SPLASH_RED)
	 		if(red){
	 			redSplash.reset();
				toDisplay.set(redSplash.getId(), redSplash.getSprite());
				red = false;
			}
	}
	
	
	/********************************************************************************************/
	/*										Sub-classes											*/
	/********************************************************************************************/
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
	
	
	public class ActionSelector extends JFrame{
		private static final long serialVersionUID = 1L;
		
		private JButton valid = new JButton("Valider");
		private JButton abort = new JButton("Annuler");
		
		private JLabel labelMain = new JLabel("Main Limit");
		private JLabel labelP1 = new JLabel("Proc1 Limit");
		private JLabel labelP2 = new JLabel("Proc2 Limit");
		
		public JFormattedTextField mainLimit;// = new JFormattedTextField(NumberFormat.getIntegerInstance());
		public JFormattedTextField p1Limit;// = new JFormattedTextField(NumberFormat.getIntegerInstance());
		public JFormattedTextField p2Limit;// = new JFormattedTextField(NumberFormat.getPercentInstance());
		
		public boolean isValid = false;
		public boolean isAborted = false;
		
		public Checkbox checkP1 = new Checkbox("Proc1 active");
		public Checkbox checkP2 = new Checkbox("Proc2 active");
		public Checkbox checkForward = new Checkbox("Avancer");
		public Checkbox checkJump = new Checkbox("Sauter");
		public Checkbox checkTurnRight = new Checkbox("Tourner à droite");
		public Checkbox checkTurnLeft = new Checkbox("Tourner à gauche");
		
		public ActionSelector(){
			this.setTitle("Sélection des actions");
		    this.setSize(300, 300);
		    this.setLocationRelativeTo(null);
		    
		    this.setLayout(new BorderLayout());
		    
		    DecimalFormat format = new DecimalFormat("##");
			format.setMaximumFractionDigits(0);
			format.setMaximumIntegerDigits(2);
			format.setParseIntegerOnly(true);
			
			mainLimit = new JFormattedTextField(format);
			p1Limit = new JFormattedTextField(format);
			p2Limit = new JFormattedTextField(format);
		    
		    GridLayout gl = new GridLayout(2, 2);
		    gl.setHgap(5);
		    gl.setVgap(5);
		    JPanel select = new JPanel();
		    select.setLayout(gl);
		    
		    JPanel select1 = new JPanel();
		    select1.setLayout(new BoxLayout(select1, BoxLayout.LINE_AXIS));
		    select1.add(checkForward);
		    select1.add(checkJump);
		    
		    JPanel select2 = new JPanel();
		    select2.setLayout(new BoxLayout(select2, BoxLayout.LINE_AXIS));
		    select2.add(checkTurnRight);
		    select2.add(checkTurnLeft);
		    
		    select.add(select1);
		    select.add(select2);
		    
		    GridLayout sizeLayout = new GridLayout(4, 2);
		    sizeLayout.setHgap(5);
		    sizeLayout.setVgap(5);
		    JPanel size = new JPanel();
		    size.setLayout(sizeLayout);
		    
		    size.add(checkP1);
		    size.add(checkP2);
		    
		    size.add(labelMain);
		    size.add(mainLimit);
		    size.add(labelP1);
		    size.add(p1Limit);
		    size.add(labelP2);
		    size.add(p2Limit);
		    
		    JPanel acceptDeny = new JPanel();
		    acceptDeny.add(valid);
		    acceptDeny.add(abort);
		    this.getContentPane().add(acceptDeny, BorderLayout.SOUTH);
		    this.getContentPane().add(select, BorderLayout.NORTH);
		    this.getContentPane().add(size, BorderLayout.CENTER);
		    
		    
		    /*this.setLayout(gl);
		    this.getContentPane().add(checkP1);
		    this.getContentPane().add(checkP2);
		    this.getContentPane().add(checkForward);
		    this.getContentPane().add(checkJump);
		    this.getContentPane().add(checkTurnRight);
		    this.getContentPane().add(checkTurnLeft);
		    
		    this.getContentPane().add(mainLimit);
		    this.getContentPane().add(p1Limit);
		    this.getContentPane().add(p2Limit);
		    
		    this.getContentPane().add(valid);
		    this.getContentPane().add(abort);*/
		    this.pack();
		    this.setVisible(true);
		    
		    valid.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					isValid = true;
					System.out.println("buttonpushed");
				}
			});
		    
		    abort.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					isAborted = true;
					System.out.println("buttonpushed");
				}
		    });
		    
		    this.addWindowListener(new WindowAdapter(){
		    	public void windowClosing(WindowEvent e){
		    		isAborted = true;
		    		System.out.println("buttonpushed");
		    	}
		    });
		}
	}
}
