package lightbot.graphics;

import java.util.List;

import lightbot.system.Procedure;
import lightbot.system.RelativeDirection;
import lightbot.system._Executable;
import lightbot.system.action.Forward;
import lightbot.system.action.Jump;
import lightbot.system.action.Light;
import lightbot.system.action.Turn;
import lightbot.system.action.Wash;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public class ActionListDisplay {
	
	private static int firstActionTextureX;
	private static int firstActionTextureY;
	
	//public static

	public static void displayActionList(List<_Executable> actions, int firstTopLeft, RenderWindow window) {
		for (int i = 0; i < actions.size(); i++) {
			Texture tex = getTextureForAction(actions.get(i));
			Sprite s = new Sprite(tex);
			float x = firstTopLeft + i * (tex.getSize().x + 6);
			float y = 500;
			s.setPosition(x, y);
			window.draw(s);
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

}
