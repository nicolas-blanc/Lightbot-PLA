package lightbot.graphics;

import java.util.ArrayList;
import java.util.List;

import lightbot.LightCore;
import lightbot.system.Colour;
import lightbot.system.Procedure;
import lightbot.system.RelativeDirection;
import lightbot.system.Robot;
import lightbot.system._Executable;
import lightbot.system.action.Break;
import lightbot.system.action.Clone;
import lightbot.system.action.Forward;
import lightbot.system.action.Jump;
import lightbot.system.action.Light;
import lightbot.system.action.Turn;
import lightbot.system.action.Wash;
import lightbot.system.world.Level;
import lightbot.system.world.OutOfGridException;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;

public class Game implements DisplayMode {

	public ArrayList<Drawable> toDisplay;

	private Button turnLeftButton;
	private Button turnRightButton;

	private static Button homeButton;
	private static Button returnButton;
	private static Button buttonPlay;

	public Display display;
	
	private Level level;

	private int originX;
	private int originY;

	private final int MARGIN_LEFT = 15;
	private final int GRID_DISPLAY_SIZE = 710;
	private final int WINDOW_HEIGHT = 475;
	public static final int FIRST_BUTTON_TOP_LEFT = 15;
	
	private static ArrayList<_Executable> actionsL = new ArrayList<_Executable>();
	private static ArrayList<Button> buttonsL = new ArrayList<Button>();
	
	/************************************** Procedures **************************************/ 
	private static final int BLOCK_WIDTH = 255;
	private static final int BLOCK_HEIGHT = 182;

	private enum SelectedBox {MAIN, PROC1, PROC2;}

	private static final int TOP_MARGIN_OUTSIDE_BOX = 10;
	private static final int TOP_MARGIN_INSIDE_BOX = 5;

	public static Procedure main = new Procedure(Procedure.MAIN_NAME, 12, Colour.WHITE);
	public static Procedure proc1 = new Procedure(Procedure.PROCEDURE1_NAME, 12, Colour.WHITE);
	public static Procedure proc2 = new Procedure(Procedure.PROCEDURE2_NAME, 12, Colour.WHITE);

	private static ArrayList<Button> mainButtons = new ArrayList<Button>();
	private static ArrayList<Button> proc1Buttons = new ArrayList<Button>();
	private static ArrayList<Button> proc2Buttons = new ArrayList<Button>();

	public static boolean useProc1;
	public static boolean useProc2;

	public static boolean mainIsActive = true;
	public static boolean proc1IsActive = false;
	public static boolean proc2IsActive = false;

	private static RectangleShape mainRect = new RectangleShape(new Vector2f(BLOCK_WIDTH, BLOCK_HEIGHT));
	private static RectangleShape proc1Rect = new RectangleShape(new Vector2f(BLOCK_WIDTH, BLOCK_HEIGHT));
	private static RectangleShape proc2Rect = new RectangleShape(new Vector2f(BLOCK_WIDTH, BLOCK_HEIGHT));
	

	/********************************************************************************************/
	/*										Constructors										*/
	/********************************************************************************************/

	public Game(Level level) {
		reset();
		this.level = level;
		originX = (GRID_DISPLAY_SIZE / 2) + MARGIN_LEFT;
		originY = MARGIN_LEFT + ((WINDOW_HEIGHT - (level.getGrid().getSize() * Textures.cellTexture.getSize().y))/2);
		
		System.out.println(originY);

		toDisplay = new ArrayList<Drawable>();

		initConstantDisplay();

		display = new Display(level.getGrid(), originX, originY);
	}
	
	/********************************************************************************************/
	/*								Initialization functions									*/
	/********************************************************************************************/

	/**
	 * Initialize the constant display for a game
	 */
	public void initConstantDisplay() {
		
		if (actionsL != null) {
			actionsL.clear();
			buttonsL.clear();
		} else {
			actionsL = new ArrayList<_Executable>();
			buttonsL = new ArrayList<Button>();
		}
		
		List<_Executable> levelActions = level.getListOfActions();

		// add buttons for proc1 or proc2
		if (level.useProc1())
			levelActions.add(new Procedure(Procedure.PROCEDURE1_NAME, level.getProc1Limit(), Colour.WHITE));

		if (level.useProc2())
			levelActions.add(new Procedure(Procedure.PROCEDURE2_NAME, level.getProc2Limit(), Colour.WHITE));

		int i = 0;
		for (_Executable e : levelActions) {
			Texture t = getTextureForAction(e);
			Sprite s = new Sprite(t);
			
			float x = FIRST_BUTTON_TOP_LEFT + i * (Textures.ACTION_TEXTURE_SIZE + 6);
			float y = 500;
			s.setPosition(x, y);
			
			Button b = new Button(s, null, null);
			b.setColor(237, 100, 255, 255);
			
			actionsL.add(e);
			buttonsL.add(b);
			
			toDisplay.add(s);
			
			i++;
		}
		
		Sprite spritePlay = new Sprite(Textures.playTexture);
		spritePlay.setPosition(650, 15);
		buttonPlay = new Button(spritePlay, null, null);
		
		Sprite turnLeftSprite = new Sprite(Textures.rotateLeft);
		turnLeftSprite.setPosition(50, (WINDOW_HEIGHT + MARGIN_LEFT - 30 - Textures.rotateLeft.getSize().y));

		Sprite turnRightSprite = new Sprite(Textures.rotateRight);
		turnRightSprite.setPosition((GRID_DISPLAY_SIZE - MARGIN_LEFT - 35 - Textures.rotateRight.getSize().y),
				(WINDOW_HEIGHT + MARGIN_LEFT - 30 - Textures.rotateRight.getSize().y));

		turnLeftButton = new Button(turnLeftSprite, null, null);
		turnRightButton = new Button(turnRightSprite, null, null);

		Sprite homeSprite = new Sprite(Textures.homeButtonTextureRelief);
		homeSprite.setPosition(MARGIN_LEFT, MARGIN_LEFT);
		
		Sprite returnSprite = new Sprite(Textures.returnTexture);
		returnSprite.setPosition(MARGIN_LEFT+50, MARGIN_LEFT);

		homeButton = new Button(homeSprite, null, null);
		returnButton = new Button(returnSprite, null, null);

		toDisplay.add(spritePlay);
		toDisplay.add(turnLeftSprite);
		toDisplay.add(turnRightSprite);
		toDisplay.add(homeSprite);
		toDisplay.add(returnSprite);
		
		initProcedures();

	}
	
	public void initProcedures(){
		useProc1 = level.useProc1();
		useProc2 = level.useProc2();

		mainIsActive = true;
		proc1IsActive = false;
		proc2IsActive = false;

		// setup main
		main.setLimit(level.getMainLimit());

		mainRect = new RectangleShape(new Vector2f(BLOCK_WIDTH, BLOCK_HEIGHT));
		mainRect.setFillColor(new Color(79, 179, 201));
		mainRect.setPosition(730, TOP_MARGIN_OUTSIDE_BOX);
		
		toDisplay.add(mainRect);

		// setup proc1 if available in level
		if (useProc1) {
			proc1.setLimit(level.getProc1Limit());

			proc1Buttons = new ArrayList<Button>();

			proc1Rect = new RectangleShape(new Vector2f(BLOCK_WIDTH, BLOCK_HEIGHT));
			proc1Rect.setFillColor(new Color(171, 171, 171));
			proc1Rect.setPosition(730, 2 * TOP_MARGIN_OUTSIDE_BOX + BLOCK_HEIGHT);
			toDisplay.add(proc1Rect);
		}

		// setup proc2 if available in level
		if (useProc2) {
			proc2.setLimit(level.getProc2Limit());

			proc2Buttons = new ArrayList<Button>();

			proc2Rect = new RectangleShape(new Vector2f(BLOCK_WIDTH, BLOCK_HEIGHT));
			proc2Rect.setFillColor(new Color(171, 171, 171));
			proc2Rect.setPosition(730, 3 * TOP_MARGIN_OUTSIDE_BOX + 2 * BLOCK_HEIGHT);
			toDisplay.add(proc2Rect);
		}

		assert main.getSize() == mainButtons.size() && proc1.getSize() == proc1Buttons.size()
				&& proc2.getSize() == proc2Buttons.size();

	}
	
	
	/********************************************************************************************/
	

	/**
	 * Get the constant display
	 * 
	 * @return A list of Sprite
	 */
	public ArrayList<Drawable> getConstantDisplay() {
		return this.toDisplay;
	}

	/**
	 * Display the game interface
	 */
	public void display() {
		for (Drawable s : toDisplay)
			LightCore.window.draw(s);
		display.print();
	}

	public void printGrid() {
		display.createGridAnim();
	}

	public void deleteGrid() {
		display.deleteGridAnim();
	}

	/**
	 * Get the grid from the game
	 */
	public GridDisplay getGrid() {
		return this.display.gridDisplay;
	}
	
	/********************************************************************************************/
	/*										Event Manager										*/
	/********************************************************************************************/
	
	public void eventManager(Event event) {
		switch (event.type) {
			case CLOSED:
				LightCore.window.close();
				break;
			case MOUSE_BUTTON_PRESSED:
				MouseButtonEvent mouse = event.asMouseButtonEvent();
				int pressedActionIndex = isInsideList(mouse.position);
				SelectedBox sl = selectedbox(mouse.position);
				
				if (mouse.button == Mouse.Button.LEFT) {
					
					if (buttonPlay.isInside(mouse.position)) {
						System.out.println("Play");
						try {
							main.execute(level.getGrid(), Robot.wheatley);
						} catch (OutOfGridException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (turnLeftButton.isInside(mouse.position)) {
						display.rotate(0);
					} else if (turnRightButton.isInside(mouse.position)) {
						display.rotate(1);
					} else if (homeButton.isInside(mouse.position)) {
						LightCore.game = false;
						LightCore.random = false;
						LightCore.menu = true;
						resetButtons();
					} else if (returnButton.isInside(mouse.position)){
						LightCore.game = false;
						LightCore.firstPrintGame = true;
						if(LightCore.random == true){
							LightCore.random = false;
							LightCore.menu = true;
						} else {
							LightCore.worlds = true;
							resetButtons();
							
						}
					} else if(pressedActionIndex >= 0){
						_Executable e = null;
						Button b = null;

						for (int i = 0; i < actionsL.size(); i++) {
							if (i == pressedActionIndex) {
								e = actionsL.get(i);
								b = buttonsL.get(i);

								_Executable toAdd;

								if (e instanceof Procedure) {
									Procedure p = (Procedure) e;
									switch (p.getName()) {
									case Procedure.MAIN_NAME:
										toAdd = main;
										break;
									case Procedure.PROCEDURE1_NAME:
										toAdd = proc1;
										break;
									case Procedure.PROCEDURE2_NAME:
										toAdd = proc2;
										break;
									default:
										toAdd = null;
									}

									add(toAdd, b);
								} else {
									add(e, b);
								}
							}
						}
					} else if (sl != null) {
						switch (sl) {
						case MAIN:
							mainIsActive = true;
							proc1IsActive = false;
							proc2IsActive = false;
							break;

						case PROC1:
							proc1IsActive = true;
							mainIsActive = false;
							proc2IsActive = false;
							break;

						case PROC2:
							proc2IsActive = true;
							mainIsActive = false;
							proc1IsActive = false;
							break;
						}
					}
				}
				else if (mouse.button == Mouse.Button.RIGHT) {
					int index;
					if (sl != null) {
						switch (sl) {
						case MAIN:
							index = isInsideBox(mouse.position, SelectedBox.MAIN);
							if (index != -1) {
								deleteFromProcedureAtIndex(index, SelectedBox.MAIN);
							}
							break;
						case PROC1:
							index = isInsideBox(mouse.position, SelectedBox.PROC1);
							if (index != -1) {
								deleteFromProcedureAtIndex(index, SelectedBox.PROC1);
							}
							break;
						case PROC2:
							index = isInsideBox(mouse.position, SelectedBox.PROC2);
							if (index != -1) {
								deleteFromProcedureAtIndex(index, SelectedBox.PROC2);
							}
							break;
						}
					}
				}
				break;
			default:
				break;
		}
	}
	
	/********************************************************************************************/
	/*								Procedures on Action Buttons								*/
	/********************************************************************************************/
	
	public void add(_Executable e, Button b) {
		if (mainIsActive && main.getSize() == main.getMaxNumOfActions())
			return;

		if (useProc1 && proc1IsActive && proc1.getSize() == proc1.getMaxNumOfActions())
			return;

		if (useProc2 && proc2IsActive && proc2.getSize() == proc2.getMaxNumOfActions())
			return;

		if (mainIsActive) {

			int whereToAdd = main.getSize();
			int line = whereToAdd / 4;

			main.addAction(e);

			Sprite s = new Sprite(b.getSprite().getTexture());
			int spriteX = 730 + 10 + (whereToAdd % 4) * (50 + 10);
			//int spriteY = 15 + line * (50 + 10);
			int spriteY = TOP_MARGIN_OUTSIDE_BOX + TOP_MARGIN_INSIDE_BOX + line * (50 + 10);
			s.setPosition(spriteX, spriteY);

			Button bnew = new Button(s, null, null);
			mainButtons.add(bnew);
			toDisplay.add(mainButtons.get(mainButtons.indexOf(bnew)).getSprite());

			assert main.getSize() == mainButtons.size();
		}

		if (proc1IsActive) {
			int whereToAdd = proc1.getSize();
			int line = whereToAdd / 4;

			proc1.addAction(e);

			Sprite s = new Sprite(b.getSprite().getTexture());
			int spriteX = 730 + 10 + (whereToAdd % 4) * (50 + 10);
			int spriteY = 2 * TOP_MARGIN_OUTSIDE_BOX + BLOCK_HEIGHT + TOP_MARGIN_INSIDE_BOX + line * (50 + 10);
			s.setPosition(spriteX, spriteY);

			Button bnew = new Button(s, null, null);
			proc1Buttons.add(bnew);
			toDisplay.add(proc1Buttons.get(proc1Buttons.indexOf(bnew)).getSprite());

			assert proc1.getSize() == proc1Buttons.size();
		}

		if (proc2IsActive) {
			int whereToAdd = proc2.getSize();
			int line = whereToAdd / 4;

			proc2.addAction(e);

			Sprite s = new Sprite(b.getSprite().getTexture());
			int spriteX = 730 + 10 + (whereToAdd % 4) * (50 + 10);
			int spriteY = 3 * TOP_MARGIN_OUTSIDE_BOX + 2 * BLOCK_HEIGHT + TOP_MARGIN_INSIDE_BOX + line * (50 + 10);
			s.setPosition(spriteX, spriteY);

			Button bnew = new Button(s, null, null);
			proc2Buttons.add(bnew);
			toDisplay.add(proc2Buttons.get(proc2Buttons.indexOf(bnew)).getSprite());

			assert proc2.getSize() == proc2Buttons.size();
		}
	}
	
	private void deleteFromProcedureAtIndex(int index, SelectedBox sb) {
		int lastIndex;
		Vector2f lastPos;

		switch (sb) {
			case MAIN:
				main.removeActionAtIndex(index);
	
				lastIndex = index;
				lastPos = mainButtons.get(index).getSprite().getPosition();
				toDisplay.remove(mainButtons.get(index).getSprite());
				mainButtons.remove(index);
	
				for (int i = lastIndex; i < mainButtons.size(); i++) {
					Vector2f oldPos = mainButtons.get(i).getSprite().getPosition();
					mainButtons.get(i).getSprite().setPosition(lastPos);
					lastPos = oldPos;
				}
				assert main.getSize() == mainButtons.size();
				break;
	
			case PROC1:
				proc1.removeActionAtIndex(index);
	
				lastIndex = index;
				lastPos = proc1Buttons.get(index).getSprite().getPosition();
				toDisplay.remove(proc1Buttons.get(index).getSprite());
				proc1Buttons.remove(index);
	
				for (int i = lastIndex; i < proc1Buttons.size(); i++) {
					Vector2f oldPos = proc1Buttons.get(i).getSprite().getPosition();
					proc1Buttons.get(i).getSprite().setPosition(lastPos);
					lastPos = oldPos;
				}
				assert proc1.getSize() == proc1Buttons.size();
				break;
	
			case PROC2:
				proc2.removeActionAtIndex(index);
	
				lastIndex = index;
				lastPos = proc2Buttons.get(index).getSprite().getPosition();
				toDisplay.remove(proc2Buttons.get(index).getSprite());
				proc2Buttons.remove(index);
	
				for (int i = lastIndex; i < proc2Buttons.size(); i++) {
					Vector2f oldPos = proc2Buttons.get(i).getSprite().getPosition();
					proc2Buttons.get(i).getSprite().setPosition(lastPos);
					lastPos = oldPos;
				}
				assert proc2.getSize() == proc2Buttons.size();
				break;
		}
	}
	
	/********************************************************************************************/
	/*										Tests functions										*/
	/********************************************************************************************/
	
	public CellPosition isInside(Vector2i coord) {
		return new CellPosition(0, 0, 0, false);
	}
	
	private static int isInsideList(Vector2i coord) {
		for (int i = 0; i < buttonsL.size(); i++)
			if (buttonsL.get(i).isInside(coord))
				return i;
		return -1;
	}
	
	private int isInsideBox(Vector2i coords, SelectedBox sb) {
		switch (sb) {
		case MAIN:
			for (int i = 0; i < mainButtons.size(); i++)
				if (mainButtons.get(i).isInside(coords))
					return i;
			break;

		case PROC1:
			for (int i = 0; i < proc1Buttons.size(); i++)
				if (proc1Buttons.get(i).isInside(coords))
					return i;
			break;

		case PROC2:
			for (int i = 0; i < proc2Buttons.size(); i++)
				if (proc2Buttons.get(i).isInside(coords))
					return i;
			break;
		}
		return -1;
	}
	
	private SelectedBox selectedbox(Vector2i coords) {
		if (mainRect.getGlobalBounds().contains(coords.x, coords.y))
			return SelectedBox.MAIN;

		if (proc1Rect.getGlobalBounds().contains(coords.x, coords.y))
			return SelectedBox.PROC1;

		if (proc2Rect.getGlobalBounds().contains(coords.x, coords.y))
			return SelectedBox.PROC2;

		return null;
	}
	
	/********************************************************************************************/
	/*								Procedures on Buttons										*/
	/********************************************************************************************/
	
	/**
	 * Reset all the buttons to their initial state
	 */
	public void resetButtons(){
		LevelDisplay.buttonBases.reset();
		LevelDisplay.buttonProcedures.reset();
		LevelDisplay.buttonFork.reset();
		LevelDisplay.buttonPointeurs.reset();
		LevelDisplay.buttonIf.reset();
		LevelDisplay.buttonBreak.reset();
	}
	
	/********************************************************************************************/
	/*											Others											*/
	/********************************************************************************************/
	
	/**
	 * Get the bound Texture of an _Executable
	 * @param e The _Executable
	 * @return The Texture of the _Executable
	 */
	public static Texture getTextureForAction(_Executable e) {
		if (e instanceof Forward)
			return Textures.forwardTexture;
		if (e instanceof Jump)
			return Textures.jumpTexture;
		if (e instanceof Light)
			return Textures.lightTexture;
		if (e instanceof Turn) {
			if (((Turn) e).getDirection() == RelativeDirection.LEFT)
				return Textures.turnLeftTexture;
			if (((Turn) e).getDirection() == RelativeDirection.RIGHT)
				return Textures.turnRightTexture;
		}
		if (e instanceof Wash)
			return Textures.showerTexture;
		if (e instanceof Break)
			return Textures.breakTexture;
		if (e instanceof Clone)
			return Textures.cloneTexture;

		if (e instanceof Procedure) {
			if (((Procedure) e).getName().equals(Procedure.PROCEDURE1_NAME))
				return Textures.procedure1Texture;
			if (((Procedure) e).getName().equals(Procedure.PROCEDURE2_NAME))
				return Textures.procedure2Texture;
		}

		return null;
	}
	
	/**
	 * Reset all the action and button lists
	 */
	public static void reset() {
		actionsL.clear();
		buttonsL.clear();
		
		main.reset();
		proc1.reset();
		proc2.reset();

		mainButtons.clear();
		proc1Buttons.clear();
		proc2Buttons.clear();
	}
}
