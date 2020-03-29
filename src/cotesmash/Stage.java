package cotesmash;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Stage {
	private Rectangle r;
	private boolean fallThrough;
	
	public Stage (int x, int y, int width, int height, boolean fallThrough){
		r= new Rectangle(x,y,width, height);
		this.fallThrough=fallThrough;
	}
	
	public Rectangle getBoundingRect(){
		return r;
	}
	
	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	public boolean canFallThrough(){
		return fallThrough;
	}
	
	@Override
	public String toString(){
		return "x: "+r.x+" y: "+r.y+" width: "+r.width+" height: "+r.height;
	}
}
