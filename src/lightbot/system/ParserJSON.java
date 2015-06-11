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

import lightbot.system.world.Cell;
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
			System.out.println(fileStringified);
			JSONObject dataObject = (JSONObject) parser.parse(fileStringified);
			
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
					Colour colour = stringToColour((String)column.get("colour"));
					grid.setCell(lineAttribut, columnAttribut, levelAttribut, colour);
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
					cellObject.put("h", cell.getHeight());
					cellObject.put("colour", cell.getColour().toString());
					/*cellObject.put("type", cell.getType());
					if(cell.getType() == Type.Teleport)
						cellObject.put("dest", cell.getDest());*/
					
					lines.add(cellObject);
				}
				gridArray.add(lines);
			}
			
			dataObject.put("grid", gridArray);
			dataObject.put("size", grid.getSize());
			
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
			case "TELEPORT":
				colour = Colour.TELEPORT;
				break;
			case "TELEPORTGREEN":
				colour = Colour.TELEPORTGREEN;
				break;
			case "TELEPORTYELLOW":
				colour = Colour.TELEPORTYELLOW;
				break;
			case "TELEPORTRED":
				colour = Colour.TELEPORTRED;
				break;
			default:
				break;
		}
		return colour;
	}
}
