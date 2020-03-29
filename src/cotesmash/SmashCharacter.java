package cotesmash;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class SmashCharacter {
	protected int x,y,width,hspeed,vspeed, attackTimer=0, lagTimer=0, damage=0, playerNumber;
	protected Rectangle moveBox, hitBox;
	protected HurtBox hurtBox;
	protected Image standLeft, standRight, runLeft, runRight,jabRight,jabLeft,lagRight,lagLeft,smash1Left,smash1Right,smash2Left,smash2Right, airAttackLeft, airAttackRight, shootLeft, shootRight;
	protected Vector velocity;
	private ArrayList<Stage> stages;
	protected boolean facingRight = true, jabbing=false, smashing=false, airAttacking=false, shooting=false, hitBoxMode = false, hitLag=false;
	protected SmashCharacter opponent;
	protected double power, maxSpeed,smashHPower,smashVPower,resistance;
	protected SmashPanel smashPanel;

	private int lives;

	public SmashCharacter(int x, int y, int width, ArrayList<Stage> stages, double maxSpeed, int playerNumber, int level,int lives,double resistance, double smashHPower, double smashVPower, SmashPanel smashPanel){
		this.x=x;
		this.y=y;
		this.width=width;
		this.stages=stages;
		this.power = 1+(level*0.1);
		this.lives=lives;
		this.playerNumber=playerNumber;
		this.smashPanel=smashPanel;
		this.smashHPower=smashHPower;
		this.smashVPower=smashVPower;
		this.resistance=resistance;
		moveBox = new Rectangle(x,y,width,width);
		hitBox = new Rectangle(x,y,width,width);
		velocity = new Vector(0,0);
		if (maxSpeed>10){
			this.maxSpeed=10;
		}
		else{
			this.maxSpeed = maxSpeed;
		}
		openImages();
	}


	public int getLives(){
		return lives;
	}



	//MOVEMENT METHODS






	public Vector accelerate(int v, int h) {
		vspeed += v;

		if (Math.abs(hspeed + h) <= maxSpeed){
			hspeed += h;
		}

		//		int speed = (int) Math.sqrt(vspeed*vspeed + hspeed*hspeed);



		velocity.setX(hspeed);
		velocity.setY(vspeed);
		return velocity;		
	}


	public Vector decelerate(int v, int h) {

		if (v == 1){
			vspeed ++;

		}

		if (h == 1){
			if (hspeed < 0) hspeed ++;
			else if (hspeed == 0);
			else hspeed --;
		}
		//		move();
		velocity.setX(hspeed);
		velocity.setY(vspeed);
		return velocity;		
	}


	public void move() {
		//		x+= speed*Math.cos(direction);
		//		moveBox.translate(hspeed, vspeed);
		for (int i = 0; i < Math.abs(vspeed); i++){
			moveBox.translate(0, (Math.abs(vspeed))/vspeed);
			stopFall();

		}
		for (int i = 0; i < Math.abs(hspeed); i++){
			moveBox.translate((Math.abs(hspeed))/hspeed, 0);
		}
		updateHitBox();
		//		System.out.println("hspeed" + hspeed + " vspeed " + vspeed);
		x =(int) moveBox.getMinX();
		y = (int) moveBox.getMinY();

		// maybe "push" back onto the screen change direction if
		// this object goes off the screen
		if (hurtBox != null){
			hurtBox.translate(hspeed, vspeed);
		}
	}


	public void fallThroughPlat() {
		Stage s = getCurrentStage();
		if (s.canFallThrough()){
			moveBox.translate(0, 3);
		}
	}




	//COLLISION METHODS






	public boolean onPlatform() {
		for (Stage stage:stages){
			if ((int) (stage.getBoundingRect().getMinY()) == (int) (this.moveBox.getMaxY()) && 
					stage.getBoundingRect().getMinX() < this.hitBox.getMaxX() && 
					stage.getBoundingRect().getMaxX() > this.hitBox.getMinX()){
				return true;
			}
		}
		return false;
	}


	public void stopFall() {
		for (Stage stage: stages){
			if ((int) (stage.getBoundingRect().getMinY()) == (int) (this.moveBox.getMaxY()) && 
					stage.getBoundingRect().getMinX() < this.hitBox.getMaxX() && 
					stage.getBoundingRect().getMaxX() > this.hitBox.getMinX() &&
					vspeed>0){
				vspeed=0;
			}		
		}
	}


	public Stage getCurrentStage(){
		for (Stage stage: stages){
			if ((int) (stage.getBoundingRect().getMinY()) == (int) (this.moveBox.getMaxY()) && 
					stage.getBoundingRect().getMinX() < this.hitBox.getMaxX() && 
					stage.getBoundingRect().getMaxX() > this.hitBox.getMinX()){
				return stage;
			}		
		}
		return null;
	}



	//GAME METHODS




	public void respawn() {
		damage=0;
		lagTimer=0;
		lives--;
		velocity = new Vector(0,0);
		vspeed=0;
		hspeed=0;
		moveBox = new Rectangle(350,70,width,width);
		x= (int)moveBox.getMinX();
		y= (int)moveBox.getMinY();
		
		hitLag=false;
		shooting=false;
		jabbing=false;
		smashing=false;
		airAttacking=false;

	}


	public boolean offScreen() {
		if (hitBox.getMaxX()>900 || hitBox.getMinX()<-100 ||
				hitBox.getMaxY()>1200 || hitBox.getMinY()<-100)
			return true;
		return false;
	}






	//ATTACKING METHODS

	public abstract void startAttack(String string);
	public abstract void attack();

	public void advanceHitLag() {
		if (lagTimer>0){
			lagTimer--;
		}
		else{
			hitLag=false;
		}

	}

	public boolean attacking(){
		if (jabbing || smashing || shooting)
			return true;
		return false;
	}


	public void getHit(ArrayList<Projectile> projectiles) {

		for (int x=0;x<projectiles.size();x++){
			if (!(projectiles.get(x).getShooter().equals(this)) && projectiles.get(x).getHurtBox().intersects(hitBox)){
				damage+=projectiles.get(x).getHurtBox().damage();


				if ((int)(-projectiles.get(x).getHurtBox().getVPower()*damage)/resistance>-10){
					if (!(projectiles.get(x).facingRight())){
						knockBack(-10,(int) ((int)(-projectiles.get(x).getHurtBox().getHPower()*damage)/resistance));
					}
					else{
						knockBack(-10,(int) ((int)(projectiles.get(x).getHurtBox().getHPower()*damage)/resistance));
					}
				}
				
				else if(this.x<projectiles.get(x).getHurtBox().x){
					knockBack((int) ((int)(-projectiles.get(x).getHurtBox().getVPower()*damage)/resistance),(int) ((int)(-projectiles.get(x).getHurtBox().getHPower()*damage)/resistance));
				}
				else{
					knockBack((int) ((int)(-projectiles.get(x).getHurtBox().getVPower()*damage)/resistance),(int) ((int)(projectiles.get(x).getHurtBox().getHPower()*damage)/resistance));
				}
				smashPanel.removeProjectile(x);
				x--;
			}
		}
		
		if (opponent.hurtBox != null && hitBox.intersects(opponent.hurtBox)){

			damage+=opponent.hurtBox.damage();
			
			
			if ((int)(-opponent.hurtBox.getVPower()*damage)/resistance>-10){
				if (x<opponent.x){
					knockBack(-10, (int) ((int)(-opponent.hurtBox.getHPower()*damage)/resistance));
				}
				else{
					knockBack(-10,(int) ((int)(opponent.hurtBox.getHPower()*damage)/resistance));
				}
			}
			else if(x<opponent.x){
				knockBack((int) ((int)(-opponent.hurtBox.getVPower()*damage)/resistance),(int) ((int)(-opponent.hurtBox.getHPower()*damage)/resistance));
			}
			else{
				knockBack((int) ((int)(-opponent.hurtBox.getVPower()*damage)/resistance),(int) ((int)(opponent.hurtBox.getHPower()*damage)/resistance));
			}
		}
	}


	public void knockBack(int v, int h) {
		vspeed += v;
		hspeed += h;

		velocity.setX(hspeed);
		velocity.setY(vspeed);

		opponent.deleteHurtBox();

	}


	private void deleteHurtBox() {
		hurtBox = null;
	}


	public void setOpponent(SmashCharacter s) {
		opponent = s;
	}





	//DRAWING METHODS






	public void faceRight() {
		facingRight=true;
	}


	public void faceLeft() {
		facingRight = false;		
	}


	public abstract void draw(Graphics g);
	public abstract void openImages();
	public abstract void updateHitBox();


}