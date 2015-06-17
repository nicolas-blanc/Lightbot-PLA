package lightbot.system;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TeleportColour {
	TELEPORT,
	TELEPORTBLUE,
	TELEPORTGREEN,
	TELEPORTORANGE,
	TELEPORTPURPLE,
	TELEPORTRED,
	TELEPORTYELLOW;
	
	private static final List<TeleportColour> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static TeleportColour randomColour(){
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}
