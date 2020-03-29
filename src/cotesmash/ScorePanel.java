package cotesmash;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	private GameFrame gameFrame;
	private JLabel winner, player1, player1Stats,player2,player2Stats;
	private Image squirtleHead,charmanderHead,bulbasaurHead;
	private int characterOne,characterTwo;

	public ScorePanel(int totalLives, int lives1, int lives2,int characterOne,int characterTwo,int player1damage, int player2damage, int player1jabs, int player1smashes, int player1airAttacks, int player1shots, int player2jabs, int player2smashes, int player2airAttacks, int player2shots, GameFrame gf) {
		setLayout(null);
		setOpaque(true);
		openImages();
		this.setBackground(new Color(100,100,200));
		gameFrame = gf;
		this.characterOne=characterOne;
		this.characterTwo=characterTwo;
		addLabels(totalLives,lives1,lives2,player1damage,player2damage,player1jabs,player1smashes,player1airAttacks,player1shots,player2jabs,player2smashes,player2airAttacks,player2shots);
		addButtons();
	}

	private void addLabels(int totalLives, int lives1, int lives2, int player1damage, int player2damage, int player1jabs, int player1smashes, int player1airAttacks, int player1shots, int player2jabs, int player2smashes, int player2airAttacks, int player2shots) {
		if (lives2==0){
			winner = new JLabel("Player 1 Wins!");
			winner.setForeground(Color.RED);

		}
		else{
			winner = new JLabel("Player 2 Wins!");
			winner.setForeground(Color.BLUE);

		}
		winner.setBounds(150,30,600,100);
		winner.setFont(new Font("smashFont",7,70));
		this.add(winner);
		
		player1 = new JLabel("Player 1:");
		player1.setForeground(Color.RED);
		player1.setBounds(155,155,100,20);
		player1.setFont(new Font("asdf",7,20));
		this.add(player1);
		
		player2 = new JLabel("Player 2:");
		player2.setForeground(Color.BLUE);
		player2.setBounds(555,155,100,20);
		player2.setFont(new Font("asdf",7,20));
		this.add(player2);
		
		int player1kills = totalLives-lives2;
		int player1deaths = totalLives-lives1;
		player1Stats = new JLabel("<html>Kills: "+player1kills+"<br>Deaths: "+player1deaths+"<br>Damage Dealt: "+player2damage+ "<br>Jabs: "+player1jabs+"<br>Smashes: "+player1smashes+"<br>Air Attacks: "+player1airAttacks+"<br>Shots: "+player1shots+ "<html>");
		player1Stats.setForeground(Color.WHITE);
		player1Stats.setBounds(100,180,200,400);
		player1Stats.setFont(new Font("asdf",7,20));
		this.add(player1Stats);
		
		player2Stats = new JLabel("<html>Kills: "+player1deaths+"<br>"+"Deaths: "+player1kills+"<br>Damage Dealt: "+player1damage+"<br>Jabs: "+player2jabs+"<br>Smashes: "+player2smashes+"<br>Air Attacks: "+player2airAttacks+"<br>Shots: "+player2shots+"<html>");
		player2Stats.setForeground(Color.WHITE);
		player2Stats.setBounds(500,180,200,400);
		player2Stats.setFont(new Font("asdf",7,20));
		this.add(player2Stats);


	}

	private void addButtons() {
		
	}

	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (characterOne==0){
			g.drawImage(squirtleHead, 165, 200,70,70, null);
		}
		else if (characterOne==1){
			g.drawImage(charmanderHead, 165, 200,70,70, null);

		}
		else{
			g.drawImage(bulbasaurHead, 165, 195,70,90, null);
		}

		if (characterTwo==0){
			g.drawImage(squirtleHead, 565, 200,70,70, null);

		}
		else if (characterTwo==1){
			g.drawImage(charmanderHead, 565, 200,70,70, null);

		}
		else{
			g.drawImage(bulbasaurHead, 565, 195,70,90, null);

		}

	}

	private void openImages() {
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

}
