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
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;

public class ProcedureBlockDisplay {

	private static final int BLOCK_WIDTH = 255;
	private static final int BLOCK_HEIGHT = 182;

	private enum SelectedBox {
		MAIN, PROC1, PROC2;

	}

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
	public static boolean proc1IsActive = false;
	public static boolean proc2IsActive = false;

	private static RectangleShape mainRect = new RectangleShape(new Vector2f(BLOCK_WIDTH, BLOCK_HEIGHT));
	private static RectangleShape proc1Rect = new RectangleShape(new Vector2f(BLOCK_WIDTH, BLOCK_HEIGHT));
	private static RectangleShape proc2Rect = new RectangleShape(new Vector2f(BLOCK_WIDTH, BLOCK_HEIGHT));
	
	public static Level l;

	public static void init(Level level) {
		l = level;

		useProc1 = level.useProc1();
		useProc2 = level.useProc2();

		mainIsActive = true;
		proc1IsActive = false;
		proc2IsActive = false;

		// setup main
		main.setLimit(level.getMainLimit());

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

		assert main.getSize() == mainButtons.size() && proc1.getSize() == proc1Buttons.size()
				&& proc2.getSize() == proc2Buttons.size();

		updateDisplay();
	}

	public static void reset() {
		main.reset();
		proc1.reset();
		proc2.reset();

		mainButtons.clear();
		proc1Buttons.clear();
		proc2Buttons.clear();
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

			main.addAction(e);

			Sprite s = new Sprite(b.getSprite().getTexture());
			int spriteX = 730 + 10 + (whereToAdd % 4) * (50 + 10);
			int spriteY = 15 + line * (50 + 10);
			s.setPosition(spriteX, spriteY);

			Button bnew = new Button(s, null, null);
			mainButtons.add(bnew);

			assert main.getSize() == mainButtons.size();

			updateDisplay();
		}

		if (proc1IsActive) {
			int whereToAdd = proc1.getSize();
			int line = whereToAdd / 4;

			proc1.addAction(e);

			Sprite s = new Sprite(b.getSprite().getTexture());
			int spriteX = 730 + 10 + (whereToAdd % 4) * (50 + 10);
			int spriteY = 2 * TOP_MARGIN + BLOCK_HEIGHT + 15 + line * (50 + 10);
			s.setPosition(spriteX, spriteY);

			Button bnew = new Button(s, null, null);
			proc1Buttons.add(bnew);

			assert proc1.getSize() == proc1Buttons.size();

			updateDisplay();
		}

		if (proc2IsActive) {
			int whereToAdd = proc2.getSize();
			int line = whereToAdd / 4;

			proc2.addAction(e);

			Sprite s = new Sprite(b.getSprite().getTexture());
			int spriteX = 730 + 10 + (whereToAdd % 4) * (50 + 10);
			int spriteY = 3 * TOP_MARGIN + 2 * BLOCK_HEIGHT + 15 + line * (50 + 10);
			s.setPosition(spriteX, spriteY);

			Button bnew = new Button(s, null, null);
			proc2Buttons.add(bnew);

			assert proc2.getSize() == proc2Buttons.size();

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

				SelectedBox sl = selectedbox(mouse.position);

				if (sl != null) {
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

			if (mouse.button == Mouse.Button.RIGHT) {
				SelectedBox sl = selectedbox(mouse.position);
				int index;
				if (sl != null) {
					switch (sl) {
					case MAIN:
						index = isInsideBox(mouse.position, SelectedBox.MAIN);
						if (index != -1) {
							deleteFromProcedureAtIndex(index, SelectedBox.MAIN);
							updateDisplay();
						}
						break;
					case PROC1:
						index = isInsideBox(mouse.position, SelectedBox.PROC1);
						if (index != -1) {
							deleteFromProcedureAtIndex(index, SelectedBox.PROC1);
							updateDisplay();
						}
						break;
					case PROC2:
						index = isInsideBox(mouse.position, SelectedBox.PROC2);
						if (index != -1) {
							deleteFromProcedureAtIndex(index, SelectedBox.PROC2);
							updateDisplay();
						}
						break;
					}
				}
			}
		default:
			break;

		}
	}

	public static void updateDisplay() {
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

	private static SelectedBox selectedbox(Vector2i coords) {
		if (mainRect.getGlobalBounds().contains(coords.x, coords.y))
			return SelectedBox.MAIN;

		if (proc1Rect.getGlobalBounds().contains(coords.x, coords.y))
			return SelectedBox.PROC1;

		if (proc2Rect.getGlobalBounds().contains(coords.x, coords.y))
			return SelectedBox.PROC2;

		return null;
	}

	private static void deleteFromProcedureAtIndex(int index, SelectedBox sb) {
		int lastIndex;
		Vector2f lastPos;

		switch (sb) {
		case MAIN:
			main.removeActionAtIndex(index);

			lastIndex = index;
			lastPos = mainButtons.get(index).getSprite().getPosition();
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

	private static int isInsideBox(Vector2i coords, SelectedBox sb) {
		switch (sb) {
		case MAIN:
			for (int i = 0; i < mainButtons.size(); i++) {
				if (mainButtons.get(i).isInside(coords))
					return i;
			}
			break;

		case PROC1:
			for (int i = 0; i < proc1Buttons.size(); i++) {
				if (proc1Buttons.get(i).isInside(coords))
					return i;
			}
			break;

		case PROC2:
			for (int i = 0; i < proc2Buttons.size(); i++) {
				if (proc2Buttons.get(i).isInside(coords))
					return i;
			}
			break;
		}

		return -1;
	}

}
