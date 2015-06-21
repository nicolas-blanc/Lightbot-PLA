package lightcore.world;


public enum CardinalDirection {
	NORTH, SOUTH, WEST, EAST;

	public static CardinalDirection getRotationDirection(CardinalDirection direction, RelativeDirection turnDirection) {
		
		if ((direction == CardinalDirection.NORTH && turnDirection == RelativeDirection.LEFT)
				|| direction == CardinalDirection.SOUTH
				&& turnDirection == RelativeDirection.RIGHT) {
			return CardinalDirection.WEST;
		} else if ((direction == CardinalDirection.NORTH && turnDirection == RelativeDirection.RIGHT)
				|| direction == CardinalDirection.SOUTH
				&& turnDirection == RelativeDirection.LEFT) {
			return CardinalDirection.EAST;
		} else if ((direction == CardinalDirection.WEST && turnDirection == RelativeDirection.RIGHT)
				|| direction == CardinalDirection.EAST
				&& turnDirection == RelativeDirection.LEFT) {
			return CardinalDirection.NORTH;
		} else {
			return CardinalDirection.SOUTH;
		}

	}
	
}
