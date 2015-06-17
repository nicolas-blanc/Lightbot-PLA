package lightbot.graphics;

import java.util.ArrayList;
import java.util.List;

import lightbot.LightCore;
import lightbot.system.Colour;
import lightbot.system.Procedure;
import lightbot.system._Executable;
import lightbot.system.world.Level;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;

public class ProcedureBlockDisplay {

	private static final int BLOCK_WIDTH = 255;
	private static final int BLOCK_HEIGHT = 182;

	private static final int TOP_MARGIN = 10;

	public static Procedure main = new Procedure(Procedure.MAIN_NAME, 12, Colour.WHITE);
	public static Procedure proc1 = new Procedure(Procedure.PROCEDURE1_NAME, 12, Colour.WHITE);
	public static Procedure proc2 = new Procedure(Procedure.PROCEDURE2_NAME, 12, Colour.WHITE);

	private static List<Button> mainButtons = new ArrayList<Button>();
	private static List<Button> proc1Buttons = new ArrayList<Button>();
	private static List<Button> proc2Buttons = new ArrayList<Button>();

	public static boolean useProc1;
	public static boolean useProc2;

	public static boolean mainIsActive = true;
	public static boolean proc1IsActive;
	public static boolean proc2IsActive;

	private static RectangleShape mainRect;
	private static RectangleShape proc1Rect;
	private static RectangleShape proc2Rect;

	public static void init(Level level) {

		// main.reset();
		// proc1.reset();
		// proc2.reset();

		// mainButtons.clear();
		// proc1Buttons.clear();
		// proc2Buttons.clear();

		useProc1 = level.useProc1();
		useProc2 = level.useProc2();

		mainIsActive = true;
		proc1IsActive = false;
		proc2IsActive = false;

		// setup main
		// main = new Procedure(Procedure.MAIN_NAME, level.getMainLimit(),
		// Colour.WHITE);
		main.setLimit(level.getMainLimit());

		// mainButtons = new ArrayList<Button>();

		mainRect = new RectangleShape(new Vector2f(BLOCK_WIDTH, BLOCK_HEIGHT));
		mainRect.setFillColor(new Color(79, 179, 201));
		mainRect.setPosition(730, TOP_MARGIN);

		// setup proc1 if available in level
		if (useProc1) {
			proc1.setLimit(level.getProc1Limit());

			proc1Buttons = new ArrayList<Button>();

			proc1Rect = new RectangleShape(new Vector2f(BLOCK_WIDTH, BLOCK_HEIGHT));
			proc1Rect.setFillColor(new Color(171, 171, 171));
			proc1Rect.setPosition(730, 2 * TOP_MARGIN + BLOCK_HEIGHT);
		}

		// setup proc2 if available in level
		if (useProc2) {
			proc2.setLimit(level.getProc2Limit());

			proc2Buttons = new ArrayList<Button>();

			proc2Rect = new RectangleShape(new Vector2f(BLOCK_WIDTH, BLOCK_HEIGHT));
			proc2Rect.setFillColor(new Color(171, 171, 171));
			proc2Rect.setPosition(730, 3 * TOP_MARGIN + 2 * BLOCK_HEIGHT);
		}

		updateDisplay();
	}

	public static void add(_Executable e, Button b) {
		if (mainIsActive && main.getSize() == main.getMaxNumOfActions())
			return;

		if (useProc1 && proc1IsActive && proc1.getSize() == proc1.getMaxNumOfActions())
			return;

		if (useProc2 && proc2IsActive && proc2.getSize() == proc2.getMaxNumOfActions())
			return;

		if (mainIsActive) {

			int whereToAdd = main.getSize();
			int line = whereToAdd / 4;

			System.out.println("wheretoadd : " + whereToAdd);
			System.out.println("line : " + line);

			main.addAction(e);

			Sprite s = new Sprite(b.getSprite().getTexture());
			int spriteX = 730 + 10 + (whereToAdd % 4) * (50 + 10);
			int spriteY = 15 + line * (50 + 10);
			s.setPosition(spriteX, spriteY);

			Button bnew = new Button(s, null, null);
			mainButtons.add(bnew);

			updateDisplay();
		}

		if (proc1IsActive) {
			int whereToAdd = proc1.getSize();
			int line = whereToAdd / 4;

			proc1.addAction(e);

			Sprite s = new Sprite(b.getSprite().getTexture());
			int spriteX = 730 + 10 + (whereToAdd % 4) * (50 + 10);
			int spriteY = 15 + line * (50 + 10);
			s.setPosition(spriteX, spriteY);

			Button bnew = new Button(s, null, null);
			proc1Buttons.add(bnew);
			updateDisplay();
		}

		if (proc2IsActive) {
			int whereToAdd = proc2.getSize();
			int line = whereToAdd / 4;

			proc2.addAction(e);

			Sprite s = new Sprite(b.getSprite().getTexture());
			int spriteX = 730 + 10 + (whereToAdd % 4) * (50 + 10);
			int spriteY = 15 + line * (50 + 10);
			s.setPosition(spriteX, spriteY);

			Button bnew = new Button(s, null, null);
			proc2Buttons.add(bnew);
			updateDisplay();

		}
	}

	public static void eventManager(Event event) {
		switch (event.type) {
		case CLOSED:
			LightCore.window.close();
			return;
		case MOUSE_BUTTON_PRESSED:
			MouseButtonEvent mouse = event.asMouseButtonEvent();
			if (mouse.button == Mouse.Button.LEFT) {

			}
		default:
			break;

		}
	}
	
	//private static 
	

	private static void updateDisplay() {
		LightCore.window.draw(mainRect);

		for (int i = 0; i < mainButtons.size(); i++) {
			LightCore.window.draw(mainButtons.get(i).getSprite());
		}

		if (useProc1) {
			LightCore.window.draw(proc1Rect);
			for (Button button : proc1Buttons) {
				LightCore.window.draw(button.getSprite());
			}
		}

		if (useProc2) {
			LightCore.window.draw(proc2Rect);
			for (Button button : proc2Buttons) {
				LightCore.window.draw(button.getSprite());
			}
		}
	}
}
