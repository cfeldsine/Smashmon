package cotesmash;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Charmander extends SmashCharacter{

	public Charmander(int x, int y, ArrayList<Stage> stages, int playerNumber, int level,int lives, SmashPanel smashPanel){
		super(x,y,100,stages,6+level*0.25,playerNumber,level,lives,11+level*0.25,0,4.5,smashPanel);
	}

	@Override
	public void startAttack(String string) {
		if(attackTimer==0){
			if (string.equals("jab")){
				jabbing = true;
				attackTimer = 15;
				if (facingRight){
					hurtBox = new HurtBox(x+80,y+38,66,30,5,1.25*power,0);
				}
				else{
					hurtBox = new HurtBox(x-46,y+38,66,30,5,1.25*power,0);
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
					smashPanel.addProjectile(new Projectile(new HurtBox(x+80,y+28,30,30,5,1.25*power,0),true,this));
				}
				else{
					smashPanel.addProjectile(new Projectile(new HurtBox(x-26,y+28,30,30,5,1.25*power,0),false,this));

				}
			}
			else if(string.equals("airAttack")){
				airAttacking=true;
				attackTimer=50;
				if(facingRight){
					hurtBox = new HurtBox(x+90,y+30,60,33,15,1.7*power,0);

				}
				else{
					hurtBox = new HurtBox(x-50,y+30,60,33,15,1.7*power,0);
				}

			}

		}
	}

	@Override
	public void attack(){
		if (smashing && attackTimer==25){
			if (facingRight){
				hurtBox = new HurtBox(x+100,y,50,100,20,0,4.5*power);
			}
			else{
				hurtBox = new HurtBox(x-50,y,50,100,20,0,4.5*power);
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
			shooting=false;
			jabbing=false;
			smashing=false;
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
				hitBox.setBounds(moveBox.x+30, moveBox.y+3, 77, 97);
			else{
				hitBox.setBounds(moveBox.x-7, moveBox.y+3, 77, 97);
			}
		}
		else if (smashing){
			if(facingRight){
				hitBox.setBounds(moveBox.x+25, moveBox.y+8, 73, 93);
			}
			else{
				hitBox.setBounds(x+3, moveBox.y+8, 73, 93);
			}
		}
		else if (airAttacking){
			if(facingRight){
				hitBox.setBounds(moveBox.x, moveBox.y+10, 105, 72);
			}
			else{
				hitBox.setBounds(x, moveBox.y+10, 105, 72);
			}
		}
		else if (shooting){
			if(facingRight){
				hitBox.setBounds(moveBox.x+20, moveBox.y+3, 78, 97);
			}
			else{
				hitBox.setBounds(moveBox.x+2, moveBox.y+3, 78, 97);
			}
		}
		else if (velocity.getX()!=0){
			if(facingRight)
				hitBox.setBounds(moveBox.x+5, moveBox.y+4, 92, 92);
			else{
				hitBox.setBounds(moveBox.x+3,moveBox.y+4, 92, 92);
			}
		}
		else{
			if(facingRight){
				hitBox.setBounds(moveBox.x+30, moveBox.y+5, 70, 95);
			}
			else{
				hitBox.setBounds(x, moveBox.y+5, 70, 95);
			}
		}	
	}

	@Override
	public void draw(Graphics g) {
		if (playerNumber==1){
			g.setColor(Color.RED);
			g.drawString("Player 1", x+25, y);
		}
		else{
			g.setColor(Color.BLUE);
			g.drawString("Player 2", x+25, y);
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
					g.drawImage(smash1Right, x, y, width+50,width,null);
				}
				else{
					g.drawImage(smash2Right, x, y, width+50,width,null);
				}
			}
			else{
				if (attackTimer>25){
					g.drawImage(smash1Left, x-50, y, width+50,width,null);
				}
				else{
					g.drawImage(smash2Left, x-50, y, width+50,width,null);
				}			
			}	
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

	@Override
	public void openImages() {
		if (standLeft==null){
			try {
				URL url = getClass().getResource("images/CharmanderStandingLeft.png");
				standLeft = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (standRight==null){
			try {
				URL url = getClass().getResource("images/CharmanderStandingRight.png");
				standRight = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (runLeft==null){
			try {
				URL url = getClass().getResource("images/CharmanderRunningLeft.png");
				runLeft = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (runRight==null){
			try {
				URL url = getClass().getResource("images/CharmanderRunningRight.png");
				runRight = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (jabLeft==null){
			try {
				URL url = getClass().getResource("images/CharmanderJabLeft.png");
				jabLeft = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (jabRight==null){
			try {
				URL url = getClass().getResource("images/CharmanderJabRight.png");
				jabRight = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (lagLeft==null){
			try {
				URL url = getClass().getResource("images/CharmanderLagLeft.png");
				lagLeft = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (lagRight==null){
			try {
				URL url = getClass().getResource("images/CharmanderLagRight.png");
				lagRight = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (smash1Left==null){
			try {
				URL url = getClass().getResource("images/CharmanderSmash1Left.png");
				smash1Left = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (smash2Left==null){
			try {
				URL url = getClass().getResource("images/CharmanderSmash2Left.png");
				smash2Left = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (smash1Right==null){
			try {
				URL url = getClass().getResource("images/CharmanderSmash1Right.png");
				smash1Right = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (smash2Right==null){
			try {
				URL url = getClass().getResource("images/CharmanderSmash2Right.png");
				smash2Right = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (airAttackLeft==null){
			try {
				URL url = getClass().getResource("images/CharmanderAirAttackLeft.png");
				airAttackLeft = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (airAttackRight==null){
			try {
				URL url = getClass().getResource("images/CharmanderAirAttackRight.png");
				airAttackRight = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (shootLeft==null){
			try {
				URL url = getClass().getResource("images/CharmanderShootLeft.png");
				shootLeft = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (shootRight==null){
			try {
				URL url = getClass().getResource("images/CharmanderShootRight.png");
				shootRight = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
	}

}
