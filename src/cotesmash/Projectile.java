package cotesmash;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;

public class Projectile {
	private HurtBox hurtBox;
	private boolean facingRight;
	private SmashCharacter shooter;
	private Image rightImg,leftImg;
	public Projectile(HurtBox hurtBox, boolean facingRight, SmashCharacter origin){
		this.hurtBox=hurtBox;
		this.facingRight=facingRight;
		shooter=origin;
		openImage();
	}

	public HurtBox getHurtBox(){
		return hurtBox;
	}

	public boolean facingRight(){
		return facingRight;
	}

	public void draw(Graphics g) {
		if (facingRight){
			g.drawImage(rightImg,hurtBox.x-10, hurtBox.y, hurtBox.width+10, hurtBox.height,null);
		}
		else{
			g.drawImage(leftImg, hurtBox.x, hurtBox.y, hurtBox.width+10, hurtBox.height,null);
		}
	}

	public boolean offScreen() {
		if (hurtBox.getMaxX()>900 || hurtBox.getMinX()<-100)
			return true;
		return false;
	}

	public SmashCharacter getShooter(){
		return shooter;
	}

	@Override
	public String toString(){
		String s = "x: "+hurtBox.x + " y: " + hurtBox.y + " width: " + hurtBox.width + " height: " + hurtBox.height;
		if (facingRight){
			s+= " Right";
		}
		else{
			s+= " Left";
		}
		return s;
	}

	private void openImage(){
		if (shooter instanceof Squirtle){
			if (rightImg==null){
				try {
					URL url = getClass().getResource("images/SquirtleProjectileRight.png");
					rightImg = ImageIO.read(url);
				} 
				catch (Exception e) {
					System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
					e.printStackTrace();
				}
			}
			if (leftImg==null){
				try {
					URL url = getClass().getResource("images/SquirtleProjectileLeft.png");
					leftImg = ImageIO.read(url);
				} 
				catch (Exception e) {
					System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
					e.printStackTrace();
				}
			}
		}
		else if (shooter instanceof Charmander){
			if (rightImg==null){
				try {
					URL url = getClass().getResource("images/CharmanderProjectileRight.png");
					rightImg = ImageIO.read(url);
				} 
				catch (Exception e) {
					System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
					e.printStackTrace();
				}
			}
			if (leftImg==null){
				try {
					URL url = getClass().getResource("images/CharmanderProjectileLeft.png");
					leftImg = ImageIO.read(url);
				} 
				catch (Exception e) {
					System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
					e.printStackTrace();
				}
			}
		}
		else{
			if (rightImg==null){
				try {
					URL url = getClass().getResource("images/BulbasaurProjectileRight.png");
					rightImg = ImageIO.read(url);
				} 
				catch (Exception e) {
					System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
					e.printStackTrace();
				}
			}
			if (leftImg==null){
				try {
					URL url = getClass().getResource("images/BulbasaurProjectileLeft.png");
					leftImg = ImageIO.read(url);
				} 
				catch (Exception e) {
					System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
					e.printStackTrace();
				}
			}
		}
	}
}
