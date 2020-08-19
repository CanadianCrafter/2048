import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import audio.MusicPlayer;



public class WinScreen extends JFrame implements ActionListener {
	JPanel splashScreen = new JPanel();
	JButton screenButton = new JButton();
	int win;

	// constructor method
	public WinScreen(int i) {
		win = i;
		frameSetup();
		panelDesign();

	}

	// set up the frame
	private void frameSetup() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLayout(null);
		add(splashScreen); // add to screen
		setVisible(true);
		setResizable(false);
	}

	// set up the panel
	private void panelDesign() {
		
		//add features for the screen
		splashScreen.setBorder(null);
		splashScreen.setBackground(Color.BLACK);
		splashScreen.setBounds(0, 0, 500, 500);
		splashScreen.setLayout(null);

		//add features for the button
		screenButton.addActionListener(this);
		screenButton.setBounds(0, 0, 500, 500); // sets size and location; location moves so buttons don't over
		if(win ==2048) {
			screenButton.setIcon(new ImageIcon(new ImageIcon("Images/Win Screen 2048.png").getImage().getScaledInstance(500, 500, 0)));
			MusicPlayer.playClip("SlotsWin.wav");
		}
		else if(win ==4096) {
			screenButton.setIcon(new ImageIcon(new ImageIcon("Images/Win Screen 4096.png").getImage().getScaledInstance(500, 500, 0)));
			MusicPlayer.playClip("SlotsBigWin.wav");
		}
		splashScreen.add(screenButton); // add the button to the screen

	}

	// this method opens another GUI when clicked the screen
	public void actionPerformed(ActionEvent arg0) {
		TwentyFortyEight.frame.dispose();
		dispose();
		if(win ==2048) new TwentyFortyEight(true,true,false); 
		else if(win ==4096) new TwentyFortyEight(true,true,true); 

	}
}