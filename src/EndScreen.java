import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import audio.MusicPlayer;


// hello
public class EndScreen extends JFrame implements ActionListener {
	JPanel splashScreen = new JPanel();
	JButton screenButton = new JButton();

	// constructor method
	public EndScreen() {

		MusicPlayer.playClip("Voltorb Flip Game Over.wav");
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
		screenButton.setIcon(new ImageIcon(new ImageIcon("Images/End Screen.png").getImage().getScaledInstance(500, 500, 0)));
		splashScreen.add(screenButton); // add the button to the screen

	}

	// this method opens another GUI when clicked the screen
	public void actionPerformed(ActionEvent arg0) {
		TwentyFortyEight.frame.dispose();
		dispose();
		new TwentyFortyEight(false,false,false); // open the next GUI
		// new MazeRaceGUI();

	}
}