package lightbot.graphics;

import java.util.ArrayList;
import java.util.List;

import lightbot.LightCore;
import lightbot.system.Colour;
import lightbot.system.Procedure;
import lightbot.system.RelativeDirection;
import lightbot.system._Executable;
import lightbot.system.action.Forward;
import lightbot.system.action.Jump;
import lightbot.system.action.Light;
import lightbot.system.action.Turn;
import lightbot.system.action.Wash;
import lightbot.system.world.Level;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.MouseButtonEvent;

public class ActionListDisplay {
	
	public static final int FIRST_BUTTON_TOP_LEFT = 15;

	private static List<_Executable> actionsL = new ArrayList<_Executable>();
	private static List<Button> buttonsL = new ArrayList<Button>();

	public static void init(Level level) {

		if (actionsL != null) {
			actionsL.clear();
			buttonsL.clear();
		} else {
			actionsL = new ArrayList<_Executable>();
			buttonsL = new ArrayList<Button>();
		}

		List<_Executable> levelActions = level.getListOfActions();
		
		// add buttons for proc1 or proc2
		if(level.useProc1())
			levelActions.add(new Procedure(Procedure.PROCEDURE1_NAME, level.getProc1Limit(), Colour.WHITE));
		
		if(level.useProc2())
			levelActions.add(new Procedure(Procedure.PROCEDURE2_NAME, level.getProc2Limit(), Colour.WHITE));

		for (_Executable e : levelActions) {
			Texture t = getTextureForAction(e);
			Sprite s = new Sprite(t);
			Button b = new Button(s, null, null);
			actionsL.add(e);
			buttonsL.add(b);
		}

		display(FIRST_BUTTON_TOP_LEFT);
	}

	public static void display(int firstTopLeft) {

		int i = 0;
		for (Button button : buttonsL) {
			Sprite s = new Sprite(button.getSprite().getTexture());
			float x = firstTopLeft + i * (Textures.ACTION_TEXTURE_SIZE + 6);
			float y = 500;
			s.setPosition(x, y);
			buttonsL.get(i).getSprite().setPosition(x, y);
			i++;
			LightCore.window.draw(s);
		}
	}
	
	public static void reset() {
		actionsL.clear();
		buttonsL.clear();
	}

	public static void eventManager(Event event) {
		switch (event.type) {
		case CLOSED:
			LightCore.window.close();
			break;
		case MOUSE_BUTTON_PRESSED:
			MouseButtonEvent mouse = event.asMouseButtonEvent();
			if (mouse.button == Mouse.Button.LEFT) {

				int pressedActionIndex = isInsideList(mouse.position);

				if (pressedActionIndex == -1)
					break;
				else {
					_Executable e = null;
					Button b = null;

					for (int i = 0; i < actionsL.size(); i++) {
						if (i == pressedActionIndex) {
							e = actionsL.get(i);
							b = buttonsL.get(i);
						}
					}

					ProcedureBlockDisplay.add(e, b);
				}
			}

		default:
			break;
		}
	}

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
		if (e instanceof Procedure) {
			if (((Procedure) e).getName().equals(Procedure.PROCEDURE1_NAME))
				return Textures.procedure1Texture;
			if (((Procedure) e).getName().equals(Procedure.PROCEDURE2_NAME))
				return Textures.procedure2Texture;
		}

		return null;
	}

	private static int isInsideList(Vector2i coord) {
		for (int i = 0; i < buttonsL.size(); i++) {
			if (buttonsL.get(i).isInside(coord))
				return i;
		}

		return -1;
	}

}
