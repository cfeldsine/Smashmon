package cotesmash;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;


public class SmashPanel extends JPanel{
	private SmashCharacter player, computer, player2;
	private Timer timer;
	private ArrayList<Stage> stages;
	private ArrayList<Projectile> projectiles;
	private boolean w,a,s,d,t,y,u;
	private boolean computerLeft, computerRight, vsMode;
	private int inputTimer=0, movingTimer=0, movingRange,inputTimerMax;
	private boolean up,down,left,right,k,l,j;
	private GameFrame gameFrame;
	private Image background, squirtleHead, charmanderHead,bulbasaurHead;

	private int lives, difficulty,player1damage,player2damage,player1jabs,player2jabs,player1smashes,player2smashes,player1airAttacks,player2airAttacks,player1shots,player2shots;

	public SmashPanel(int characterOne, int characterTwo, boolean vsMode, int lives, int difficulty, GameFrame gf){
		this.setPreferredSize(new Dimension(800,800));
		this.setOpaque(true);
		storeSettings(characterOne,characterTwo,vsMode,lives,difficulty);
		openImages();
		gameFrame=gf;
		setUpObjects(characterOne,characterTwo);
		setUpPlayer1KeyMappings();
		if (vsMode){
			setUpPlayer2KeyMappings();
		}
		setUpTimer();

	}


	private void storeSettings(int characterOne, int characterTwo, boolean vsMode, int lives, int difficulty) {
		this.vsMode=vsMode;
		this.lives=lives;
		this.difficulty=difficulty;
		if (difficulty==0){
			inputTimerMax=100;
		}
		else if (difficulty==1){
			inputTimerMax=70;
		}
		else{
			inputTimerMax=20;
		}
	}


	private void openImages() {
		if (background==null){
			try {
				URL url = getClass().getResource("images/Valley.jpg");
				background = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}

		if (squirtleHead==null){
			try {
				URL url = getClass().getResource("images/SquirtleHead.png");
				squirtleHead = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (charmanderHead==null){
			try {
				URL url = getClass().getResource("images/CharmanderHead.png");
				charmanderHead = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}	
		if (bulbasaurHead==null){
			try {
				URL url = getClass().getResource("images/BulbasaurHead.png");
				bulbasaurHead = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}	
	}


	private void tick() {
		if (vsMode){
			if (player.getLives()==0 || player2.getLives()==0){
				timer.stop();
				gameFrame.showScores(player.getLives(),player2.getLives(),player1damage,player2damage,player1jabs,player1smashes,player1airAttacks,player1shots,player2jabs,player2smashes,player2airAttacks,player2shots);
			}
		}
		else{
			if (player.getLives()==0 || computer.getLives()==0){
				timer.stop();
				gameFrame.showScores(player.getLives(),computer.getLives(),player1damage,player2damage,player1jabs,player1smashes,player1airAttacks,player1shots,player2jabs,player2smashes,player2airAttacks,player2shots);
			}

			//			timer.stop();
			//			gameFrame.getContentPane().removeAll();
			//			gameFrame.getContentPane().invalidate();
			//			gameFrame.getContentPane().add(gameFrame.getWorldPanel());
			//			gameFrame.getWorld().setIsInGame(false);
			//			gameFrame.getWorldPane().startGameAgain();
			//			gameFrame.getContentPane().validate();
			//			gameFrame.repaint();
		}
		playerTick();

		if (vsMode){
			player2Tick();
		}
		else{
			computerTick();
		}

		moveProjectiles();
		checkForHits();
		repaint();
	}


	private void playerTick() {	
		//attacking section
		if (!player.hitLag){
			if (u){
				player.startAttack("shoot");
				player1shots++;
			}
			else if(player.onPlatform()){
				if (player.airAttacking){
					player.attackTimer=1;
				}

				if (t){
					if (player.attackTimer==0){
						player1jabs++;
					}
					player.startAttack("jab");

				}
				else if(y){
					if (player.attackTimer==0){
						player1smashes++;
					}			
					player.startAttack("smash");	
					}
			}
			else if (t && !player.onPlatform()){
				if (player.attackTimer==0){
				player1airAttacks++;
				}
				player.startAttack("airAttack");
			}

			player.attack();

		}
		else {
			player.advanceHitLag();
		}


		//movement section



		if (w && !s && player.onPlatform() && player.vspeed==0 && !player.attacking() && !player.hitLag){
			//jump
			player.accelerate(-15, 0);
		}
		else if (s && !w && player.onPlatform()&& !player.attacking() && !player.hitLag){
			//fall through platform
			player.fallThroughPlat();
		}
		else{
			if (!player.onPlatform())
				//gravity
				player.decelerate(1, 0);
		}

		if (a && !d&& !player.attacking() && !player.hitLag){
			//move left
			player.accelerate(0, -1);
			player.faceLeft();
		}
		else if(!a && d&& !player.attacking() && !player.hitLag){
			//move right
			player.accelerate(0, 1);
			player.faceRight();
		}
		else{
			//decelerate horizontally
			player.decelerate(0, 1);
		}

		player.move();
		player.stopFall();
		if (player.offScreen()){
			player1damage+=player.damage;
			player.respawn();

		}
	}


	private void player2Tick() {
		//attacking section
		if (!player2.hitLag){
			if (j){
				player2.startAttack("shoot");
				player2shots++;
			}
			if(player2.onPlatform()){
				if (player2.airAttacking){
					player2.attackTimer=1;
				}

				if (k){
					if (player2.attackTimer==0){
					player2jabs++;
					}
					player2.startAttack("jab");

				}
				else if(l){
					if (player2.attackTimer==0){
					player2smashes++;
					}
					player2.startAttack("smash");
				}
			}
			else if (k && !player2.onPlatform()){
				if (player2.attackTimer==0){
				player2airAttacks++;
				}
				player2.startAttack("airAttack");
			}

			player2.attack();

		}
		else {
			player2.advanceHitLag();
		}


		//movement section



		if (up && !down && player2.onPlatform() && player2.vspeed==0 && !player2.attacking() && !player2.hitLag){
			//jump
			player2.accelerate(-15, 0);
		}
		else if (down && !up && player2.onPlatform()&& !player2.attacking() && !player2.hitLag){
			//fall through platform
			player2.fallThroughPlat();
		}
		else{
			if (!player2.onPlatform())
				//gravity
				player2.decelerate(1, 0);
		}

		if (left && !right&& !player2.attacking() && !player2.hitLag){
			//move left
			player2.accelerate(0, -1);
			player2.faceLeft();
		}
		else if(!left && right&& !player2.attacking() && !player2.hitLag){
			//move right
			player2.accelerate(0, 1);
			player2.faceRight();
		}
		else{
			//decelerate horizontally
			player2.decelerate(0, 1);
		}

		player2.move();
		player2.stopFall();
		if (player2.offScreen()){
			player2damage+=player2.damage;
			player2.respawn();

		}		
	}

	private void computerTick(){

		inputTimer++;
		//attacking section
		if (!computer.hitLag){
			if(computer.onPlatform() && 
					computer.getCurrentStage().equals(player.getCurrentStage())){

				if (player.x-computer.x>0){
					computer.facingRight=true;
				}
				else{
					computer.facingRight=false;
				}
				if (player.x-computer.x<100 
						&& player.x-computer.x>20){
					if (inputTimer==inputTimerMax && 
							((int)(-computer.power*computer.smashVPower*player.damage)/player.resistance < -38 ||
									(int)(computer.power*computer.smashHPower*player.damage)/player.resistance > 33)
							){
						if (computer.attackTimer==0){
						player2smashes++;
						}
						computer.startAttack("smash");
					}
					else if (inputTimer==inputTimerMax){
						if (computer.attackTimer==0){
						player2jabs++;
						}
						computer.startAttack("jab");
					}
				}
				else if(player.x-computer.x>-100 
						&& player.x-computer.x<-20){
					if (inputTimer==inputTimerMax && 
							((int)(-computer.power*computer.smashVPower*player.damage)/player.resistance < -38 ||
									(int)(computer.power*computer.smashHPower*player.damage)/player.resistance > 33)
							){
						if (computer.attackTimer==0){
						player2smashes++;
						}
						computer.startAttack("smash");
					}
					else if (inputTimer==inputTimerMax){
						if (computer.attackTimer==0){
						player2jabs++;
						}
						computer.startAttack("jab");
					}
				}
				else if (player.x-computer.x>250){
					if (inputTimer==inputTimerMax && (int)(Math.random()*4)>0){
						if (computer.attackTimer==0){
						player2shots++;
						}
						computer.startAttack("shoot");
					}
				}
				else if (player.x-computer.x<-250 
						&& (int)(Math.random()*4)>0){
					if (inputTimer==inputTimerMax){
						if (computer.attackTimer==0){
						player2shots++;
						}
						computer.startAttack("shoot");
					}
				}
			}

			computer.attack();
		}
		else {
			computer.advanceHitLag();
		}



		if (movingTimer ==0){
			computerLeft = false;
			computerRight = false;
		}

		if (computer.onPlatform() && 
				computer.vspeed==0){
			if (inputTimer == inputTimerMax){
				if (!computer.hitLag && 
						!computer.attacking() && 
						player.y<computer.y){
					computer.accelerate(-15, 0);

				}
				else if (!computer.hitLag && 
						!computer.attacking() && 
						player.y>computer.y){
					computer.fallThroughPlat();
				}
			}
		}
		else{
			if (!computer.onPlatform())
				computer.decelerate(1, 0);
		}

		if (computer.x>400 && player.x>computer.x){
			movingRange = (700-computer.x)/10;
		}
		else if (computer.x<=400 && player.x<=computer.x){
			movingRange = (computer.x-100)/10;
		}
		else{
			movingRange=30;
		}
		if (movingRange>30){
			movingRange=30;
		}

		if (movingTimer ==0 && 
				inputTimer == inputTimerMax &&
				!computer.hitLag &&
				!computer.attacking()){
			if (computer.x-player.x>80 && 
					!computerRight){
				computerLeft = true;
				if (movingRange==10){
					movingTimer = 11 + (int)(Math.random()*(movingRange-10));
				}
				else{
					movingTimer = 1+(int)(Math.random()*movingRange);
				}			}
			else if(player.x-computer.x>100 && 
					!computerLeft){
				computerRight = true;
				if (movingRange==10){
					movingTimer = 11 + (int)(Math.random()*(movingRange-10));
				}
				else{
					movingTimer = 1+(int)(Math.random()*movingRange);
				}
			}
			else if (Math.abs(player.x-computer.x)<30){
				if (computer.x<400){
					computerRight=true;
					movingTimer=15;
				}
				else{
					computerLeft=true;
					movingTimer=15;
				}
			}
		}

		if (computerLeft){
			computer.accelerate(0, -1);
			computer.faceLeft();
			movingTimer--;
		}
		else if(computerRight){
			computer.accelerate(0, 1);
			computer.faceRight();
			movingTimer--;
		}
		else{
			computer.decelerate(0, 1);
		}

		if (inputTimer == inputTimerMax){
			inputTimer = 0;
		}

		computer.move();
		computer.stopFall();

		if (computer.offScreen()){
			player2damage+=computer.damage;
			computer.respawn();
		}

	}

	private void moveProjectiles() {
		for (int x=0;x<projectiles.size();x++){
			Projectile y = projectiles.get(x);
			if (y.facingRight()){
				y.getHurtBox().translate(3, 0);
			}
			else{
				y.getHurtBox().translate(-3, 0);

			}

			if (y.offScreen()){
				projectiles.remove(x);
				x--;
			}
		}
	}


	private void checkForHits() {
		player.getHit(projectiles);
		if (vsMode){
			player2.getHit(projectiles);
		}
		else{
			computer.getHit(projectiles);
		}
	}

	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}

	public void removeProjectile(int x) {
		projectiles.remove(x);
	}


	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(background, 0, 0, 800,800,null);
		player.draw(g);
		if (vsMode){
			player2.draw(g);
		}
		else{
			computer.draw(g);
		}

		for (int x=0;x<stages.size();x++){
			stages.get(x).draw(g);
		}

		for (int x=0;x<projectiles.size();x++){
			projectiles.get(x).draw(g);
		}

		drawStats(g);
	}


	private void drawStats(Graphics g) {
		g.setColor(Color.RED);
		g.drawString("Player 1:", 150, 727);
		int playerPos = 150;
		if (player instanceof Squirtle){
			for (int x=0;x<player.getLives();x++){
				g.drawImage(squirtleHead, playerPos, 730, 20, 20, null);
				playerPos += 21;
			}
		}
		else if (player instanceof Charmander){
			for (int x=0;x<player.getLives();x++){
				g.drawImage(charmanderHead, playerPos, 730, 20, 20, null);
				playerPos += 21;
			}
		}
		else {
			for (int x=0;x<player.getLives();x++){
				g.drawImage(bulbasaurHead, playerPos, 730, 20, 20, null);
				playerPos += 21;
			}
		}
		g.drawString(String.valueOf("Damage: " + player.damage), 150, 762);


		if (vsMode){
			g.setColor(Color.BLUE);
			g.drawString("Player 2:", 500, 727);
			int player2Pos = 500;
			if (player2 instanceof Squirtle){
				for (int x=0;x<player2.getLives();x++){
					g.drawImage(squirtleHead, player2Pos, 730, 20, 20, null);
					player2Pos += 21;
				}
			}
			else if (player2 instanceof Charmander){
				for (int x=0;x<player2.getLives();x++){
					g.drawImage(charmanderHead, player2Pos, 730, 20, 20, null);
					player2Pos += 21;
				}
			}
			else {
				for (int x=0;x<player2.getLives();x++){
					g.drawImage(bulbasaurHead, player2Pos, 730, 20, 20, null);
					player2Pos += 21;
				}
			}
			g.drawString(String.valueOf("Damage: " + player2.damage), 500, 762);
		}
		else{
			g.setColor(Color.BLUE);
			g.drawString("Player 2:", 500, 727);
			int computerPos = 500;
			if (computer instanceof Squirtle){
				for (int x=0;x<computer.getLives();x++){
					g.drawImage(squirtleHead, computerPos, 730, 20, 20, null);
					computerPos += 21;
				}
			}
			else if (computer instanceof Charmander){
				for (int x=0;x<computer.getLives();x++){
					g.drawImage(charmanderHead, computerPos, 730, 20, 20, null);
					computerPos += 21;
				}
			}
			else {
				for (int x=0;x<computer.getLives();x++){
					g.drawImage(bulbasaurHead, computerPos, 730, 20, 20, null);
					computerPos += 21;
				}
			}
			g.drawString(String.valueOf("Damage: " + computer.damage), 500, 762);	
		}
	}




	//SET-UP METHODS




	private void setUpObjects(int characterOne, int characterTwo) {

		stages = new ArrayList<Stage>();
		stages.add(new Stage(100,700,600,100,false));
		stages.add(new Stage(125,600,150,10,true));
		stages.add(new Stage(525,600,150,10,true));
		stages.add(new Stage(325,500,150,10,true));
		projectiles = new ArrayList<Projectile>();
		if (characterOne==0){
			player = new Squirtle(110,70,stages,1,1,lives,this);
		}
		else if (characterOne==1){
			player = new Charmander(110,70,stages,1,1,lives,this);
		}
		else{
			player = new Bulbasaur(110,70,stages,1,1,lives,this);
		}

		if (vsMode){
			if (characterTwo==0){
				player2 = new Squirtle(590,70,stages,2,1,lives,this);
			}
			else if (characterTwo==1){
				player2 = new Charmander(590,70,stages,2,1,lives,this);
			}
			else{
				player2 = new Bulbasaur(590,70,stages,2,1,lives,this);
			}
			player.setOpponent(player2);
			player2.setOpponent(player);
		}
		else{

			int computerLevel = 1;
			if (difficulty==1){
				computerLevel=5;
			}
			else if (difficulty==2){
				computerLevel=10;
			}
			else if (difficulty==3){
				computerLevel=100;
			}

			System.out.println(computerLevel);
			if (characterTwo==0){
				computer = new Squirtle(590,70,stages,2,computerLevel,lives,this);
			}
			else if (characterTwo==1){
				computer = new Charmander(590,70,stages,2,computerLevel,lives,this);
			}
			else{
				computer = new Bulbasaur(590,70,stages,2,computerLevel,lives,this);
			}
			player.setOpponent(computer);
			computer.setOpponent(player);
		}
	}




	private void setUpPlayer1KeyMappings() {				
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed D"), "beginRight");
		this.getInputMap().put(KeyStroke.getKeyStroke("released D"), "endRight");
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed A"), "beginLeft");
		this.getInputMap().put(KeyStroke.getKeyStroke("released A"), "endLeft");
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed W"), "beginUp");
		this.getInputMap().put(KeyStroke.getKeyStroke("released W"), "endUp");
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed S"), "beginDown");
		this.getInputMap().put(KeyStroke.getKeyStroke("released S"), "endDown");
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed T"), "beginT");
		this.getInputMap().put(KeyStroke.getKeyStroke("released T"), "endT");
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed Y"), "beginY");
		this.getInputMap().put(KeyStroke.getKeyStroke("released Y"), "endY");
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed U"), "beginU");
		this.getInputMap().put(KeyStroke.getKeyStroke("released U"), "endU");


		this.getActionMap().put("beginRight", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d = true;
			}
		});

		this.getActionMap().put("endRight", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d=false;
			}
		});

		this.getActionMap().put("beginLeft", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				a=true;
			}
		});

		this.getActionMap().put("endLeft", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				a=false;
			}
		});
		this.getActionMap().put("beginUp", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				w=true;

			}
		});

		this.getActionMap().put("endUp", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				w=false;
			}
		});
		this.getActionMap().put("beginDown", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				s=true;
			}
		});

		this.getActionMap().put("endDown", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				s=false;
			}

		});
		this.getActionMap().put("beginT", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				t=true;

			}

		});
		this.getActionMap().put("endT", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				t=false;

			}

		});
		this.getActionMap().put("beginY", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				y=true;

			}

		});
		this.getActionMap().put("endY", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				y=false;

			}

		});
		this.getActionMap().put("beginU", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				u=true;

			}

		});
		this.getActionMap().put("endU", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				u=false;

			}

		});

		this.requestFocusInWindow();


	}

	private void setUpPlayer2KeyMappings() {
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed RIGHT"), "beginRight2");
		this.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "endRight2");
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed LEFT"), "beginLeft2");
		this.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "endLeft2");
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed UP"), "beginUp2");
		this.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "endUp2");
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed DOWN"), "beginDown2");
		this.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "endDown2");
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed K"), "beginK");
		this.getInputMap().put(KeyStroke.getKeyStroke("released K"), "endK");
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed L"), "beginL");
		this.getInputMap().put(KeyStroke.getKeyStroke("released L"), "endL");
		this.getInputMap().put(KeyStroke.getKeyStroke("pressed J"), "beginJ");
		this.getInputMap().put(KeyStroke.getKeyStroke("released J"), "endJ");


		this.getActionMap().put("beginRight2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				right = true;
			}
		});

		this.getActionMap().put("endRight2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				right=false;
			}
		});

		this.getActionMap().put("beginLeft2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				left=true;
			}
		});

		this.getActionMap().put("endLeft2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				left=false;
			}
		});
		this.getActionMap().put("beginUp2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				up=true;

			}
		});

		this.getActionMap().put("endUp2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				up=false;
			}
		});
		this.getActionMap().put("beginDown2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				down=true;
			}
		});

		this.getActionMap().put("endDown2", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				down=false;
			}

		});
		this.getActionMap().put("beginK", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				k=true;

			}

		});
		this.getActionMap().put("endK", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				k=false;

			}

		});
		this.getActionMap().put("beginL", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l=true;

			}

		});
		this.getActionMap().put("endL", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				l=false;

			}

		});
		this.getActionMap().put("beginJ", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				j=true;

			}

		});
		this.getActionMap().put("endJ", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				j=false;

			}

		});

		this.requestFocusInWindow();



	}






	private void setUpTimer() {
		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tick();
			}
		});
		timer.start();
	}


}
