package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
	
	protected  float x,y;
	protected int width , height;
	protected float marginX, marginY;
	protected Rectangle2D.Float hitBox;
	
	public Entity(float x, float y ,int width , int height) {
		this(x,  y , width ,  height , 0,  0);
	}
	
	public Entity(float x, float y ,int width , int height ,float marginX, float marginY) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.marginX = marginX;
		this.marginY = marginY;
	}
	
	protected void drawHitBox(Graphics g) {
		g.setColor(Color.YELLOW);
		g.drawRect((int)(hitBox.x), (int) (hitBox.y) , (int) hitBox.width, (int) hitBox.height);
	}

	protected void inithitbox(float x, float y ,float width , float height ,float marginX, float marginY) {
		hitBox = new Rectangle2D.Float(x + marginX, y + marginY
				, width , height);
	}

	public Rectangle2D.Float getHitBox() {
		return hitBox;
	}
}
