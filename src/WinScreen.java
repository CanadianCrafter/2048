//imports
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import audio.MusicPlayer;

public class WinScreen extends JFrame implements ActionListener {
	//gui stuff
	JPanel splashScreen = new JPanel();
	JButton screenButton = new JButton();
	//win type
	int win;

	//constructor method
	public WinScreen(int i) {
		win = i;
		frameSetup();
		panelDesign();

	}

	//sets up the frame
	private void frameSetup() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLayout(null);
		add(splashScreen);
		setVisible(true);
		setResizable(false);
	}

	//sets up the panel
	private void panelDesign() {
		//add features for the screen
		splashScreen.setBorder(null);
		splashScreen.setBackground(Color.BLACK);
		splashScreen.setBounds(0, 0, 500, 500);
		splashScreen.setLayout(null);

		//add features for the button
		screenButton.addActionListener(this);
		screenButton.setBounds(0, 0, 500, 500); //the button fills the entire screen
		//if the player got 2048 or 4096 for the first time, show/play the appropriate win message/jingle
		if(win ==2048) {
			screenButton.setIcon(new ImageIcon(new ImageIcon("Images/Win Screen 2048.png").getImage().getScaledInstance(500, 500, 0)));
			MusicPlayer.playClip("SlotsWin.wav");
		}
		else if(win ==4096) {
			screenButton.setIcon(new ImageIcon(new ImageIcon("Images/Win Screen 4096.png").getImage().getScaledInstance(500, 500, 0)));
			MusicPlayer.playClip("SlotsBigWin.wav");
		}
		splashScreen.add(screenButton);

	}

	//returns to continue the game
	//before the win screen was opened, the game was saved - that game is being loaded again
	public void actionPerformed(ActionEvent arg0) {
		TwentyFortyEight.frame.dispose();
		dispose();
		if(win ==2048) new TwentyFortyEight(true,true,false); //the past game is loaded with a new flag that the player reached 2048
		else if(win ==4096) new TwentyFortyEight(true,true,true); //the past game is loaded with a new flag that the player reached 4096
	}
}