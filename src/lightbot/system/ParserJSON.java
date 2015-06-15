package lightbot.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lightbot.system.world.cell.*;
import lightbot.system.world.Grid;

public class ParserJSON {
	
	
	// TODO add the robot
	public static Grid deserialize(String filename){
		Grid grid = null;
		InputStream inStream = null;
		JSONParser parser = new JSONParser();
		try {
			inStream = new FileInputStream(new File(filename));
			
			// Build the string read in the InputStream
			StringBuilder build = new StringBuilder();
			int ch;
			while((ch = inStream.read()) != -1)
				build.append((char)ch);
			
			String fileStringified = build.toString();
			JSONObject dataObject = (JSONObject) parser.parse(fileStringified);
			JSONObject robot = (JSONObject)dataObject.get("robot");
			Robot.wheatley.setLine((int)(long)robot.get("l"));
			Robot.wheatley.setColumn((int)(long)robot.get("c"));
			
			int size = (int)(long)dataObject.get("size");
			grid = new Grid(size);
			
			JSONArray gridArray = (JSONArray)dataObject.get("grid");
			for(int l=0; l<size; l++){
				JSONArray lines = (JSONArray)gridArray.get(l);
				for(int c=0; c<size; c++){
					JSONObject column = (JSONObject)lines.get(c);
					int lineAttribut = (int)(long)column.get("l");
					int columnAttribut = (int)(long)column.get("c");
					int levelAttribut = (int)(long)column.get("h");
					String type = (String)column.get("type");
					
					Cell cell = null;
					
					switch(type){
						case "LightableCell":
							cell = new LightableCell(lineAttribut, columnAttribut, levelAttribut);
							break;
							
						case "ColoredCell":
							Colour colour = stringToColour((String)column.get("colour"));
							cell = new ColoredCell(lineAttribut, columnAttribut, levelAttribut, colour);
							break;
							
						case "TeleportCell":
							TeleportColour teleportColour = stringToTeleportColour((String)column.get("colour"));
							JSONArray dest = (JSONArray)column.get("dest");
							cell = new TeleportCell(lineAttribut, columnAttribut, levelAttribut, (int)(long)dest.get(0), (int)(long)dest.get(1), teleportColour);
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
			
			inStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file : "+filename);
		} catch (IOException e) {
			System.out.println("Error while reading file : "+filename);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Error while parsing file : "+filename);
			e.printStackTrace();
		}
		return grid;
	}
	
	@SuppressWarnings("unchecked")
	public static void serialize(String filename, Grid grid){
		OutputStream outStream = null;
		try {
			outStream = new FileOutputStream(new File(filename));
			
			JSONObject dataObject = new JSONObject();
			JSONArray gridArray = new JSONArray();
			
			for(int l=0; l<grid.getSize(); l++){
				JSONArray lines = new JSONArray();
				for(int c=0; c<grid.getSize(); c++){
					Cell cell = grid.getCell(l, c);
					JSONObject cellObject = new JSONObject();
					cellObject.put("l", l);
					cellObject.put("c", c);
					
					
					if(cell instanceof EmptyCell){
						cellObject.put("h", 0);
					}
					else{
						cellObject.put("h", ((FullCell)cell).getHeight());
					}
					
					cellObject.put("type", getClassName(cell));
					
					switch(getClassName(cell)){
						case "ColoredCell":
							cellObject.put("colour", ((ColoredCell)cell).getColour().toString());
						case "TeleportCell":
							JSONArray dest = new JSONArray();
							dest.add(((TeleportCell)cell).getDestX());
							dest.add(((TeleportCell)cell).getDestY());
							cellObject.put("dest", dest);
							cellObject.put("colour", ((TeleportCell)cell).getColour().toString());
						default:
							break;
					}
					
					lines.add(cellObject);
				}
				gridArray.add(lines);
			}
			
			dataObject.put("grid", gridArray);
			dataObject.put("size", grid.getSize());
			
			JSONObject robot = new JSONObject();
			robot.put("l", Robot.wheatley.getLine());
			robot.put("c", Robot.wheatley.getColumn());
			
			dataObject.put("robot", robot);
			
			// writing process
			byte [] contentToWrite = dataObject.toJSONString().getBytes();
			outStream.write(contentToWrite);
			
			outStream.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file : "+filename);
		} catch (IOException e) {
			System.out.println("Error while writing file : "+filename);
		}
	}
	
	private static Colour stringToColour(String colourIn){
		Colour colour = null;
		switch(colourIn){
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
			default:
				break;
		}
		return colour;
	}
	
	private static TeleportColour stringToTeleportColour(String colourIn){
		TeleportColour colour = null;
		switch(colourIn){
			case "TELEPORT":
				colour = TeleportColour.TELEPORT;
				break;
			case "TELEPORTGREEN":
				colour = TeleportColour.TELEPORTGREEN;
				break;
			case "TELEPORTYELLOW":
				colour = TeleportColour.TELEPORTYELLOW;
				break;
			case "TELEPORTRED":
				colour = TeleportColour.TELEPORTRED;
				break;
		}
		return colour;
	}
	
	public static String getClassName(Cell cell){
		String []className = cell.getClass().getName().split("\\.");
		return className[className.length-1];
	}
}
