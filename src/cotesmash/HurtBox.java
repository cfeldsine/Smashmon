package cotesmash;

import java.awt.Rectangle;

public class HurtBox extends Rectangle{
	private int moveDamage;
	private double hPower, vPower;
	public HurtBox(int x, int y, int width, int height, int moveDamage, double hPower, double vPower){
		super(x,y,width,height);
		this.moveDamage = moveDamage;
		this.hPower=hPower;
		this.vPower=vPower;
	}
	
	public int damage(){
		return moveDamage;
	}
	
	public double getVPower(){
		return vPower;
	}
	
	public double getHPower(){
		return hPower;
	}
	
}
