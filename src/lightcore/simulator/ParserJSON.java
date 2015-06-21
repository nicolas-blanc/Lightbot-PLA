package lightcore.simulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lightcore.simulator.action.*;
import lightcore.world.CardinalDirection;
import lightcore.world.Grid;
import lightcore.world.RelativeDirection;
import lightcore.world.Robot;
import lightcore.world.cell.*;

public class ParserJSON {

	public static Level deserialize(String filename) {
		Level level = null;
		Grid grid = null;
		ArrayList<_Executable> listOfActions = new ArrayList<_Executable>();
		
		// Deserialization objects
		InputStream inStream = null;
		JSONParser parser = new JSONParser();
		
		try {
			
			// Get the file content in an input stream
			inStream = new FileInputStream(new File(filename));

			// Build the content string from the input stream
			StringBuilder build = new StringBuilder();
			int character;
			while ((character = inStream.read()) != -1)
				build.append((char) character);

			String fileStringified = build.toString();
			
			// Create a JSONObject from the parsed string
			JSONObject dataObject = (JSONObject) parser.parse(fileStringified);
			JSONObject robot = (JSONObject) dataObject.get("robot");
			
			// Set robot's position
			Robot.wheatley.setLine((int) (long) robot.get("l"));
			Robot.wheatley.setColumn((int) (long) robot.get("c"));
			
			// Set robot's direction
			Robot.wheatley.setDirection(stringToCardinalDirection((String)robot.get("dir")));

			// Get the list of available actions
			JSONArray actions = (JSONArray) dataObject.get("actions");
			for (Object o : actions) {
				switch ((String) o) {
				case "Forward":
					listOfActions.add(new Forward());
					break;

				case "Jump":
					listOfActions.add(new Jump());
					break;

				case "Light":
					listOfActions.add(new Light());
					break;

				case "Turn_LEFT":
					listOfActions.add(new Turn(RelativeDirection.LEFT, Colour.WHITE));
					break;

				case "Turn_RIGHT":
					listOfActions.add(new Turn(RelativeDirection.RIGHT, Colour.WHITE));
					break;

				case "Clone":
					listOfActions.add(new Clone());
					break;

				case "Wash":
					listOfActions.add(new Wash());
					break;

				case "Break":
					listOfActions.add(new Break());
					break;
				}
			}

			// Get the grid size
			int size = (int) (long) dataObject.get("size");
			grid = new Grid(size);

			// Get the grid content
			JSONArray gridArray = (JSONArray) dataObject.get("grid");
			for (int l = 0; l < size; l++) {
				JSONArray lines = (JSONArray) gridArray.get(l);
				for (int c = 0; c < size; c++) {
					JSONObject column = (JSONObject) lines.get(c);
					int lineAttribut = (int) (long) column.get("l");
					int columnAttribut = (int) (long) column.get("c");
					int levelAttribut = (int) (long) column.get("h");
					String type = (String) column.get("type");

					Cell cell = null;

					switch (type) {
					case "LightableCell":
						cell = new LightableCell(lineAttribut, columnAttribut, levelAttribut);
						break;

					case "ColoredCell":
						Colour colour = stringToColour((String) column.get("colour"));
						cell = new ColoredCell(lineAttribut, columnAttribut, levelAttribut, colour);
						break;

					case "TeleportCell":
						TeleportColour teleportColour = stringToTeleportColour((String) column.get("colour"));
						JSONArray dest = (JSONArray) column.get("dest");
						cell = new TeleportCell(lineAttribut, columnAttribut, levelAttribut, (int) (long) dest.get(0),
								(int) (long) dest.get(1), teleportColour);
						break;

					case "NormalCell":
						cell = new NormalCell(lineAttribut, columnAttribut, levelAttribut);
						break;

					default:
						cell = new EmptyCell(lineAttribut, columnAttribut);
						break;
					}
					grid.setCell(cell);
				}
			}

			// Get informations about the procedures
			JSONObject procedures = (JSONObject) dataObject.get("proc");
			int mainLimit = (int) (long) procedures.get("main_limit");
			int p1Limit = 0;
			int p2Limit = 0;
			boolean p1Active = Boolean.parseBoolean((String) procedures.get("P1"));
			boolean p2Active = Boolean.parseBoolean((String) procedures.get("P2"));

			if (p1Active)
				p1Limit = (int) (long) procedures.get("p1_limit");
			if (p2Active)
				p2Limit = (int) (long) procedures.get("p2_limit");

			// Create a level from all the previous collected informations
			level = new Level(grid, listOfActions, p1Active, p2Active, mainLimit, p1Limit, p2Limit);
			level.setRobotInitialX(Robot.wheatley.getLine());
			level.setRobotInitialY(Robot.wheatley.getColumn());

			inStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file : " + filename);
		} catch (IOException e) {
			System.out.println("Error while reading file : " + filename);
		} catch (ParseException e) {
			System.out.println("Error while parsing file : " + filename);
			e.printStackTrace();
		}
		return level;
	}

	@SuppressWarnings("unchecked")
	public static void serialize(String filename, Level level) {
		OutputStream outStream = null;
		try {
			// Create a new output stream from the filename
			outStream = new FileOutputStream(new File(filename));

			JSONObject dataObject = new JSONObject();
			JSONArray gridArray = new JSONArray();

			// Create a JSONObject describing a grid
			for (int l = 0; l < level.getGrid().getSize(); l++) {
				JSONArray lines = new JSONArray();
				for (int c = 0; c < level.getGrid().getSize(); c++) {
					Cell cell = level.getGrid().getCell(l, c);
					JSONObject cellObject = new JSONObject();
					cellObject.put("l", l);
					cellObject.put("c", c);

					if (cell instanceof EmptyCell) {
						cellObject.put("h", 0);
					} else {
						cellObject.put("h", ((FullCell) cell).getHeight());
					}

					cellObject.put("type", getClassName(cell));

					switch (getClassName(cell)) {
					case "ColoredCell":
						cellObject.put("colour", ((ColoredCell) cell).getColour().toString());
						break;
					case "TeleportCell":
						JSONArray dest = new JSONArray();
						dest.add(((TeleportCell) cell).getDestX());
						dest.add(((TeleportCell) cell).getDestY());
						cellObject.put("dest", dest);
						cellObject.put("colour", ((TeleportCell) cell).getColour().toString());
						break;
					default:
						break;
					}

					lines.add(cellObject);
				}
				gridArray.add(lines);
			}

			dataObject.put("grid", gridArray);
			
			// Add the grid's size to the object
			dataObject.put("size", level.getGrid().getSize());

			// Add informations about the robot
			JSONObject robot = new JSONObject();
			robot.put("l", Robot.wheatley.getLine());
			robot.put("c", Robot.wheatley.getColumn());
			robot.put("dir", Robot.wheatley.getDirection().toString());
			dataObject.put("robot", robot);

			// Add informations about the list of actions
			JSONArray actions = new JSONArray();
			for (_Executable e : level.getListOfActions()) {
				String toAdd = getClassName(e);
				if (toAdd.equals("Turn"))
					actions.add(toAdd + "_" + ((Turn) e).getDirection().toString());
				else
					actions.add(toAdd);
			}
			dataObject.put("actions", actions);

			// Add informations about the procedures
			JSONObject procedures = new JSONObject();
			procedures.put("P1", Boolean.toString(level.useProc1()));
			if (level.useProc1())
				procedures.put("p1_limit", level.getProc1Limit());
			procedures.put("P2", Boolean.toString(level.useProc2()));
			if (level.useProc2())
				procedures.put("p2_limit", level.getProc2Limit());
			procedures.put("main_limit", level.getMainLimit());

			dataObject.put("proc", procedures);

			// Write the serialized JSONObject into the output file
			byte[] contentToWrite = dataObject.toJSONString().getBytes();
			outStream.write(contentToWrite);

			outStream.close();

		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file : " + filename);
		} catch (IOException e) {
			System.out.println("Error while writing file : " + filename);
		}
	}

	/**
	 * Convert a string to a Colour
	 * @param colourIn The string to convert into Colour
	 * @return A Colour described by the input string
	 */
	private static Colour stringToColour(String colourIn) {
		Colour colour = null;
		switch (colourIn) {
		case "WHITE":
			colour = Colour.WHITE;
			break;
		case "GREEN":
			colour = Colour.GREEN;
			break;
		case "YELLOW":
			colour = Colour.YELLOW;
			break;
		case "RED":
			colour = Colour.RED;
			break;
		case "BLUE":
			colour = Colour.BLUE;
			break;
		case "ORANGE":
			colour = Colour.ORANGE;
			break;
		case "PURPLE":
			colour = Colour.PURPLE;
			break;
		case "OBSTACLE":
			colour = Colour.OBSTACLE;
			break;
		}
		return colour;
	}

	/**
	 * Convert a string to a TeleportColour
	 * @param colourIn The string to convert into TeleportColour
	 * @return A TeleportColour described by the input string
	 */
	private static TeleportColour stringToTeleportColour(String colourIn) {
		TeleportColour colour = null;
		switch (colourIn) {
		case "TELEPORT":
			colour = TeleportColour.TELEPORT;
			break;
		case "TELEPORTBLUE":
			colour = TeleportColour.TELEPORTBLUE;
			break;
		case "TELEPORTGREEN":
			colour = TeleportColour.TELEPORTGREEN;
			break;
		case "TELEPORTORANGE":
			colour = TeleportColour.TELEPORTORANGE;
			break;
		case "TELEPORTPURPLE":
			colour = TeleportColour.TELEPORTPURPLE;
			break;
		case "TELEPORTRED":
			colour = TeleportColour.TELEPORTRED;
			break;
		case "TELEPORTYELLOW":
			colour = TeleportColour.TELEPORTYELLOW;
			break;
		}
		return colour;
	}
	
	/**
	 * Convert a string to a CardinalDirection
	 * @param directionIn The string to convert into CardinalDirection
	 * @return A CardinalDirection described by the input string
	 */
	private static CardinalDirection stringToCardinalDirection(String directionIn){
		switch(directionIn){
		case "EAST":
			return CardinalDirection.EAST;
		case "WEST":
			return CardinalDirection.WEST;
		case "NORTH":
			return CardinalDirection.NORTH;
		case "SOUTH":
			return CardinalDirection.SOUTH;
		default:
			return CardinalDirection.EAST;
		}
	}

	/**
	 * Get the classname of a cell, deprived of the package name, and folder names
	 * @param cell The cell to get the classname
	 * @return A String corresponding to the cell classname
	 */
	public static String getClassName(Cell cell) {
		String[] className = cell.getClass().getName().split("\\.");
		return className[className.length - 1];
	}

	/**
	 * Get the classname of a _Executable, deprived of the package name, and folder names
	 * @param action The action to get the classname
	 * @return A String corresponding to the action classname
	 */
	public static String getClassName(_Executable action) {
		String[] className = action.getClass().getName().split("\\.");
		return className[className.length - 1];
	}
}
