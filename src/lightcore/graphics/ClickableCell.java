package lightcore.graphics;

import java.awt.Polygon;

import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;

public class ClickableCell {
	
	private Polygon polygon;
	private Polygon deadZone;
	
	public ClickableCell(Sprite sprite, Texture cell){
		/**
		 * 			1*
		 * 
		 *  2*				3*
		 *  		4*
		 */
		
		int x1 = Math.round(sprite.getGlobalBounds().left) + Math.round(cell.getSize().x/(2/sprite.getScale().x));
		int y1 = Math.round(sprite.getGlobalBounds().top);
		
		int x2 = Math.round(sprite.getGlobalBounds().left);
		int y2 = Math.round(sprite.getGlobalBounds().top) + Math.round(cell.getSize().y/(2/sprite.getScale().y));
		
		int x3 = Math.round(sprite.getGlobalBounds().left) + Math.round(cell.getSize().x*sprite.getScale().x);
		int y3 = Math.round(sprite.getGlobalBounds().top) + Math.round(cell.getSize().y/(2/sprite.getScale().y));
		
		int x4 = Math.round(sprite.getGlobalBounds().left) + Math.round(cell.getSize().x/(2/sprite.getScale().x));
		int y4 = Math.round(sprite.getGlobalBounds().top) + Math.round(cell.getSize().y*sprite.getScale().y);
		
		/*System.out.println("x1 : " + x1 + ", y1 : " + y1);
		System.out.println("x2 : " + x2 + ", y2 : " + y2);
		System.out.println("x3 : " + x3 + ", y3 : " + y3);
		System.out.println("x4 : " + x4 + ", y4 : " + y4);*/
		
		this.polygon = new Polygon();
		this.polygon.addPoint(x1, y1);
		this.polygon.addPoint(x2, y2);
		this.polygon.addPoint(x4, y4);
		this.polygon.addPoint(x3, y3);
		
		/* Dead zone */
		
		/**
		 * 		1*				5*
		 * 				6*
		 * 		2*				4*
		 * 				3*
		 */
		
		int x1D = x2;
		int y1D = y2;
		
		int x2D = x2;
		int y2D = y2 + (Math.round(sprite.getGlobalBounds().height) - (Math.round(cell.getSize().y*sprite.getScale().y)));
		
		int x3D = x4;
		int y3D = y4 + (Math.round(sprite.getGlobalBounds().height) - (Math.round(cell.getSize().y*sprite.getScale().y)));
		
		int x4D = x3;
		int y4D = y2 + (Math.round(sprite.getGlobalBounds().height) - (Math.round(cell.getSize().y*sprite.getScale().y)));
		
		int x5D = x3;
		int y5D = y3;
		
		int x6D = x4;
		int y6D = y4;
		
		/*System.out.println("x1D : " + x1D + ", y1D : " + y1D);
		System.out.println("x2D : " + x2D + ", y2D : " + y2D);
		System.out.println("x3D : " + x3D + ", y3D : " + y3D);
		System.out.println("x4D : " + x4D + ", y4D : " + y4D);
		System.out.println("x5D : " + x5D + ", y5D : " + y5D);
		System.out.println("x6D : " + x6D + ", y6D : " + y6D);*/
		
		this.deadZone = new Polygon();
		this.deadZone.addPoint(x1D, y1D);
		this.deadZone.addPoint(x2D, y2D);
		this.deadZone.addPoint(x3D, y3D);
		this.deadZone.addPoint(x4D, y4D);
		this.deadZone.addPoint(x5D, y5D);
		this.deadZone.addPoint(x6D, y6D);
	}
	
	public boolean isInside(Vector2i coord){
		//System.out.println("Mouse position --> x : " + coord.x + ", y : " + coord.y);
		return this.polygon.contains((double)coord.x, (double)coord.y);
	}
	
	public boolean isInsideDeadZone(Vector2i coord){
		return this.deadZone.contains((double)coord.x, (double)coord.y);
	}

}
