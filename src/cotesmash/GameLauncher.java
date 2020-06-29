package cotesmash;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameLauncher {
	public static void main(String[] args){
		GameFrame gf = new GameFrame();
		gf.add(new StartPanel(gf));
//		gf.add(new ScorePanel(gf));
		gf.pack();
		gf.setSize(800, 900);
		gf.setResizable(false);
		gf.setVisible(true);
		
		gf.setDefaultCloseOperation(gf.EXIT_ON_CLOSE);
		
	}
}
