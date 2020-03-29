package cotesmash;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class StartPanel extends JPanel{
	private JButton start;
	private GameFrame gameFrame;
	private JLabel Player1,Player2,Smashmon,chooseYourCharacter,vsMode,on,off,liveText,difficultyText;
	private Image charmander,squirtle,bulbasaur;
	private ButtonGroup characterOne,characterTwo,vsGroup;
	private JRadioButton squirtleOne,charmanderOne,bulbasaurOne,squirtleTwo,charmanderTwo,bulbasaurTwo,vsOn,vsOff;
	private JComboBox lives,difficulty;
	
	public StartPanel(GameFrame gf){
		this(0,0,false,1,0,gf);
	}
	
	public StartPanel(int characterOne, int characterTwo, boolean vsMode, int lives, int difficulty, GameFrame gf) {
		setLayout(null);
		setOpaque(true);
		openImages();
		this.setBackground(new Color(100,100,200));
		gameFrame = gf;
		addLabels();
		addButtons();
		makeSelections(characterOne,characterTwo,vsMode,lives,difficulty);
	}
	

	private void makeSelections(int characterOne, int characterTwo, boolean vsMode, int lives, int difficulty) {
		if (characterOne==0){
			squirtleOne.setSelected(true);
		}
		else if (characterOne==1){
			charmanderOne.setSelected(true);
		}
		else{
			bulbasaurOne.setSelected(true);
		}
		
		if (characterTwo==0){
			squirtleTwo.setSelected(true);
		}
		else if (characterTwo==1){
			charmanderTwo.setSelected(true);
		}
		else{
			bulbasaurTwo.setSelected(true);
		}
		
		if (vsMode){
			vsOn.setSelected(true);
		}
		else{
			vsOff.setSelected(true);
		}
		
		this.lives.setSelectedIndex(lives-1);
		this.difficulty.setSelectedIndex(difficulty);
	}

	private void addLabels() {
		Smashmon = new JLabel("Smashmon");
		Smashmon.setBounds(200,30,600,70);
		Smashmon.setFont(new Font("smashFont",7,70));
		Smashmon.setForeground(Color.YELLOW);
		this.add(Smashmon);
		
		chooseYourCharacter = new JLabel("Choose your character!");
		chooseYourCharacter.setBounds(200,120,600,70);
		chooseYourCharacter.setFont(new Font("smashFont",7,35));
		chooseYourCharacter.setForeground(Color.WHITE);
		this.add(chooseYourCharacter);
		
		Player1 = new JLabel("Player 1");
		Player1.setBounds(150,200,600,70);
		Player1.setFont(new Font("smashFont",7,20));
		Player1.setForeground(Color.RED);
		this.add(Player1);
		
		Player2 = new JLabel("Player 2");
		Player2.setBounds(550,200,600,70);
		Player2.setFont(new Font("smashFont",7,20));
		Player2.setForeground(Color.BLUE);
		this.add(Player2);
		
		vsMode = new JLabel("Versus Mode: ");
		vsMode.setBounds(100,400,600,70);
		vsMode.setFont(new Font("smashFont",7,20));
		vsMode.setForeground(Color.YELLOW);
		this.add(vsMode);
		
		on = new JLabel("On");
		on.setBounds(260,400,600,70);
		on.setFont(new Font("smashFont",7,20));
		on.setForeground(Color.WHITE);
		this.add(on);
		
		off = new JLabel("Off");
		off.setBounds(320,400,600,70);
		off.setFont(new Font("smashFont",7,20));
		off.setForeground(Color.WHITE);
		this.add(off);
		
		liveText = new JLabel("Lives: ");
		liveText.setBounds(100,450,600,70);
		liveText.setFont(new Font("smashFont",7,20));
		liveText.setForeground(Color.YELLOW);
		this.add(liveText);
		
		difficultyText = new JLabel("Difficulty: ");
		difficultyText.setBounds(400,450,600,70);
		difficultyText.setFont(new Font("smashFont",7,20));
		difficultyText.setForeground(Color.YELLOW);
		this.add(difficultyText);
		
		
		
	}

	private void addButtons() {
		squirtleOne = new JRadioButton();
		squirtleOne.setBounds(80,370,20,20);
		squirtleOne.setBackground(new Color(100,100,200));
		this.add(squirtleOne);
		
		charmanderOne = new JRadioButton();
		charmanderOne.setBounds(180,370,20,20);
		charmanderOne.setBackground(new Color(100,100,200));
		this.add(charmanderOne);
		
		bulbasaurOne = new JRadioButton();
		bulbasaurOne.setBounds(280,370,20,20);
		bulbasaurOne.setBackground(new Color(100,100,200));
		this.add(bulbasaurOne);
		
		characterOne = new ButtonGroup();
		characterOne.add(squirtleOne);
		characterOne.add(charmanderOne);
		characterOne.add(bulbasaurOne);
		
		
		squirtleTwo = new JRadioButton();
		squirtleTwo.setBounds(480,370,20,20);
		squirtleTwo.setBackground(new Color(100,100,200));
		this.add(squirtleTwo);
		
		charmanderTwo = new JRadioButton();
		charmanderTwo.setBounds(580,370,20,20);
		charmanderTwo.setBackground(new Color(100,100,200));
		this.add(charmanderTwo);
		
		bulbasaurTwo = new JRadioButton();
		bulbasaurTwo.setBounds(680,370,20,20);
		bulbasaurTwo.setBackground(new Color(100,100,200));
		this.add(bulbasaurTwo);
		
		characterTwo = new ButtonGroup();
		characterTwo.add(squirtleTwo);
		characterTwo.add(charmanderTwo);
		characterTwo.add(bulbasaurTwo);
	
		vsOn = new JRadioButton();
		vsOn.setBounds(240,425,20,20);
		vsOn.setBackground(new Color(100,100,200));
		this.add(vsOn);
		
		vsOff = new JRadioButton();
		vsOff.setBounds(300,425,20,20);
		vsOff.setBackground(new Color(100,100,200));
		this.add(vsOff);
		
		vsGroup = new ButtonGroup();
		vsGroup.add(vsOn);
		vsGroup.add(vsOff);
		
		String[] liveArray ={"1","2","3","4","5"};
		lives = new JComboBox(liveArray);
		lives.setBounds(160,475,40,20);
		this.add(lives);
		
		String[] difficultyArray = {"Easy","Medium","Hard","Impossible"};
		difficulty = new JComboBox(difficultyArray);
		difficulty.setBounds(500,475,120,20);
		this.add(difficulty);

		
		
		start = new JButton("Start game!");
		start.setBounds(new Rectangle(200,550,400,30));
		this.add(start);
		start.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int characterOne;
				if (squirtleOne.isSelected()){
					characterOne = 0;
				}
				else if (charmanderOne.isSelected()){
					characterOne=1;
				}
				else{
					characterOne=2;
				}
				
				int characterTwo;
				if (squirtleTwo.isSelected()){
					characterTwo = 0;
				}
				else if (charmanderTwo.isSelected()){
					characterTwo=1;
				}
				else{
					characterTwo=2;
				}
				
				gameFrame.startGame(characterOne,characterTwo,vsOn.isSelected(),lives.getSelectedIndex()+1,difficulty.getSelectedIndex());
			}
			
		});
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(squirtle,40,260,100,100,null);
		g.drawImage(charmander,140,260,100,100,null);
		g.drawImage(bulbasaur,240,260,100,100,null);

		
		g.drawImage(squirtle,445,260,100,100,null);
		g.drawImage(charmander,545,260,100,100,null);
		g.drawImage(bulbasaur,645,260,100,100,null);

	}
	
	private void openImages() {
		if (squirtle==null){
			try {
				URL url = getClass().getResource("images/SquirtleStandingLeft.png");
				squirtle = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (charmander==null){
			try {
				URL url = getClass().getResource("images/CharmanderStandingLeft.png");
				charmander = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
		if (bulbasaur==null){
			try {
				URL url = getClass().getResource("images/BulbasaurStandingLeft.png");
				bulbasaur = ImageIO.read(url);
			} 
			catch (Exception e) {
				System.out.println("Image could not be opened: " + "images/" + "red" + ".png");
				e.printStackTrace();
			}
		}
	}
}
