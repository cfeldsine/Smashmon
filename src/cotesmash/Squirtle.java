package cotesmash;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Squirtle  extends SmashCharacter{

	public Squirtle(int x, int y, ArrayList<Stage> stages, int playerNumber, int level, int lives, SmashPanel smashPanel){
		super(x,y,100,stages,7+level*0.25,playerNumber,level,lives,8+level*0.25,3.5,0,smashPanel);
	}


	public void startAttack(String string) {
		if(attackTimer==0){
			if (string.equals("jab")){
				jabbing = true;
				attackTimer = 15;
				if (facingRight){
					hurtBox = new HurtBox(x+80,y+38,66,39,5,1.25*power,0);
				}
				else{
					hurtBox = new HurtBox(x-46,y+38,66,39,5,1.25*power,0);
				}

			}
			else if (string.equals("smash")){
				smashing=true;
				attackTimer = 50;
			}
			else if (string.equals("shoot")){
				shooting=true;
				attackTimer=20;
				if (facingRight){
					smashPanel.addProjectile(new Projectile(new HurtBox(x+80,y+38,30,30,5,1.25*power,0),true,this));
				}
				else{
					smashPanel.addProjectile(new Projectile(new HurtBox(x-16,y+38,30,30,5,1.25*power,0),false,this));

				}
			}
			else if(string.equals("airAttack")){
				airAttacking=true;
				attackTimer=50;
				if(facingRight){
					hurtBox = new HurtBox(x+90,y+53,60,30,15,1.7*power,0);

				}
				else{
					hurtBox = new HurtBox(x-40,y+53,60,30,15,1.7*power,0);
				}

			}

		}
	}

	@Override
	public void attack() {
		if (smashing && attackTimer==25){
			if (facingRight){
				hurtBox = new HurtBox(x+80,y+30,120,50,20,3.5*power,0);
			}
			else{
				hurtBox = new HurtBox(x-100,y+30,120,50,20,3.5*power,0);
			}
		}

		if (attackTimer==1){
			attackTimer--;
			if (jabbing){
				lagTimer=7;
			}
			else if (smashing){
				lagTimer=150;
			}
			else if (shooting){
				lagTimer=10;
			}
			else{
				lagTimer=20;
			}
			hitLag=true;
			jabbing=false;
			smashing=false;
			shooting=false;
			airAttacking=false;
			hurtBox = null;
		}
		else if(attackTimer>0){
			attackTimer--;
		}	
	}

	@Override
	public void updateHitBox() {
		if(jabbing){
			if (facingRight)
				hitBox.setBounds(moveBox.x+20, moveBox.y+18, 72, 81);
			else{
				hitBox.setBounds(moveBox.x+7, moveBox.y+18, 72, 80);
			}
		}
		else if (smashing){
			if(facingRight){
				hitBox.setBounds(moveBox.x+20, moveBox.y+10, 60, 89);
			}
			else{
				hitBox.setBounds(x+21, moveBox.y+10, 60, 89);
			}
		}
		else if (shooting){
			if(facingRight){
				hitBox.setBounds(moveBox.x+17, moveBox.y+10, 60, 100);
			}
			else{
				hitBox.setBounds(moveBox.x+22, moveBox.y+10, 60, 100);
			}
		}
		else if (airAttacking){
			if(facingRight){
				hitBox.setBounds(moveBox.x, moveBox.y+30, 99, 48);
			}
			else{
				hitBox.setBounds(x, moveBox.y+30, 99, 48);
			}
		}
		else if (velocity.getX()!=0){
			if(facingRight)
				hitBox.setBounds(moveBox.x+1, moveBox.y+34, 96, 64);
			else{
				hitBox.setBounds(moveBox.x+3,moveBox.y+34, 96, 64);
			}
		}
		else{
			if(facingRight){
				hitBox.setBounds(moveBox.x+20, moveBox.y+20, 57, 79);
			}
			else{
				hitBox.setBounds(x+21, moveBox.y+20, 57, 79);
			}
		}
	}

	public void draw(Graphics g) {
		if (playerNumber==1){
			g.setColor(Color.RED);
			g.drawString("Player 1", x+20, y+10);
		}
		else{
			g.setColor(Color.BLUE);
			g.drawString("Player 2", x+20, y+10);
		}

		if (hitBoxMode){
			g.setColor(Color.GREEN);
			g.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
			if (hurtBox != null){
				g.setColor(Color.RED);
				g.fillRect(hurtBox.x, hurtBox.y, hurtBox.width, hurtBox.height);
			}
		}

		if (hitLag){
			if (facingRight){
				g.drawImage(lagRight, x, y, width,width,null);
			}
			else{
				g.drawImage(lagLeft, x, y, width,width,null);
			}
		}
		else if (jabbing){
			if (facingRight){
				g.drawImage(jabRight, x, y, width+50,width,null);
			}
			else{
				g.drawImage(jabLeft, x-50, y, width+50,width,null);
			}		
		}

		else if (smashing){
			if (facingRight){
				if (attackTimer>25){
					g.drawImage(smash1Right, x, y, width,width,null);
				}
				else{
					g.drawImage(smash2Right, x, y, width+100,width,null);
				}
			}
			else{
				if (attackTimer>25){
					g.drawImage(smash1Left, x, y, width,width,null);
				}
				else{
					g.drawImage(smash2Left, x-100, y, width+100,width,null);
				}			}	
		}
		
		else if (shooting){
			if(facingRight){
				g.drawImage(shootRight,x,y,width,width,null);
			}
			else{
				g.drawImage(shootLeft,x,y,width,width,null);			}
		}

		else if (airAttacking){
			if (facingRight){
				g.drawImage(airAttackRight, x, y, width+50,width,null);
			}
			else{
				g.drawImage(airAttackLeft, x-50, y, width+50,width,null);
			}			
		}

		else if (velocity.getX()!=0){
			if (facingRight){
				g.drawImage(runRight, x, y, width,width,null);
			}
			else{
				g.drawImage(runLeft, x, y, width,width,null);
			}
		}
		else{
			if (facingRight){
				g.drawImage(standRight, x, y, width,width,null);
			}
			else{
				g.drawImage(standLeft, x, y, width,width,null);
			}
		}
	}


	public void openImages() {
		if (standLeft==null){
			try {
				URL url = getClass().getResource("images/SquirtleStandingLeft.png");
				standLeft = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (standRight==null){
			try {
				URL url = getClass().getResource("images/SquirtleStandingRight.png");
				standRight = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (runLeft==null){
			try {
				URL url = getClass().getResource("images/SquirtleRunningLeft.png");
				runLeft = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (runRight==null){
			try {
				URL url = getClass().getResource("images/SquirtleRunningRight.png");
				runRight = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (jabLeft==null){
			try {
				URL url = getClass().getResource("images/SquirtleJabLeft.png");
				jabLeft = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (jabRight==null){
			try {
				URL url = getClass().getResource("images/SquirtleJabRight.png");
				jabRight = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (lagLeft==null){
			try {
				URL url = getClass().getResource("images/SquirtleLagLeft.png");
				lagLeft = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (lagRight==null){
			try {
				URL url = getClass().getResource("images/SquirtleLagRight.png");
				lagRight = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (smash1Left==null){
			try {
				URL url = getClass().getResource("images/SquirtleSmash1Left.png");
				smash1Left = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (smash2Left==null){
			try {
				URL url = getClass().getResource("images/SquirtleSmash2Left.png");
				smash2Left = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (smash1Right==null){
			try {
				URL url = getClass().getResource("images/SquirtleSmash1Right.png");
				smash1Right = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (smash2Right==null){
			try {
				URL url = getClass().getResource("images/SquirtleSmash2Right.png");
				smash2Right = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (airAttackLeft==null){
			try {
				URL url = getClass().getResource("images/SquirtleAirAttackLeft.png");
				airAttackLeft = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (airAttackRight==null){
			try {
				URL url = getClass().getResource("images/SquirtleAirAttackRight.png");
				airAttackRight = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (shootLeft==null){
			try {
				URL url = getClass().getResource("images/SquirtleShootLeft.png");
				shootLeft = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (shootRight==null){
			try {
				URL url = getClass().getResource("images/SquirtleShootRight.png");
				shootRight = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

	}

}
