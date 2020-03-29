package cotesmash;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame{
	private int characterOne,characterTwo,lives,difficulty;
	private boolean vsMode;

	public void startGame(int characterOne, int characterTwo, boolean vsMode, int lives, int difficulty) {
		this.characterOne=characterOne;
		this.characterTwo=characterTwo;
		this.lives=lives;
		this.difficulty=difficulty;
		this.getContentPane().removeAll();
		this.getContentPane().invalidate();
		SmashPanel sp = new SmashPanel(characterOne,characterTwo,vsMode,lives,difficulty,this);
		this.getContentPane().add(sp);
		sp.requestFocusInWindow();
		this.getContentPane().validate();
		this.repaint();
	}


	public void newGame() {
		String[] buttons = {"Restart", "Exit"};

		int rc = JOptionPane.showOptionDialog(null, "Game Over!", "Game Over!",
				JOptionPane.PLAIN_MESSAGE, 0, null, buttons, buttons[0]);

		if (rc == 0) {
			getContentPane().removeAll(); 
		    getContentPane().invalidate();
		    StartPanel sp = new StartPanel(characterOne,characterTwo,vsMode,lives,difficulty,this);
		    getContentPane().add(sp);
		    getContentPane().validate();
		    sp.requestFocusInWindow();
		} 
		else {
			System.exit(0);
		}
	}


	public void showScores(int lives1, int lives2, int player1damage, int player2damage, int player1jabs, int player1smashes, int player1airAttacks, int player1shots, int player2jabs, int player2smashes, int player2airAttacks, int player2shots) {
		System.out.println("here");
		getContentPane().removeAll(); 
	    getContentPane().invalidate();
	    ScorePanel sp = new ScorePanel(lives,lives1,lives2,characterOne,characterTwo,player1damage,player2damage,player1jabs,player1smashes,player1airAttacks,player1shots,player2jabs,player2smashes,player2airAttacks,player2shots,this);
	    getContentPane().add(sp);
	    getContentPane().validate();
	    sp.requestFocusInWindow();
	}

}
