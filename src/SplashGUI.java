import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import audio.MusicPlayer;

public class SplashGUI extends JFrame implements ActionListener {
	JPanel splashScreen = new JPanel();
	JLabel image = new JLabel();
	JButton playButton = new JButton();
	JButton loadButton = new JButton();

	// constructor method
	public SplashGUI() {

		MusicPlayer.playAudio("Music - Route 2.wav");
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
		splashScreen.setBounds(0, 0, 500, 500);
		splashScreen.setLayout(null);
		
		
		//add features for the buttons
		playButton.addActionListener(this);
		playButton.setBounds(85, 285, 330, 40); 
		playButton.setOpaque(false); // makes the button clear
		playButton.setContentAreaFilled(false);
		playButton.setBorderPainted(false);
		splashScreen.add(playButton); // add the button to the screen
		
		
		loadButton.addActionListener(this);
		loadButton.setBounds(85, 365, 330, 40);
		loadButton.setOpaque(false); // makes the button clear
		loadButton.setContentAreaFilled(false);
		loadButton.setBorderPainted(false);
		splashScreen.add(loadButton); // add the button to the screen
		
		
		
		image.setBounds(0,0,500,500);
		image.setIcon(new ImageIcon(new ImageIcon("Images/Splash Screen.png").getImage().getScaledInstance(500, 500, 0)));
//		image.setIcon(new ImageIcon(getClass().getResource("Images/Splash Screen.png")));
		splashScreen.add(image);
		repaint();

	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource()==playButton) {
			try {
				new PrintWriter("Save.txt").close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			dispose();
			new TwentyFortyEight(false,false,false);
			
		}
		else if(event.getSource()==loadButton) {
			if(!checkLoad()) {
				image.setIcon(new ImageIcon(new ImageIcon("Images/Splash Screen Error.png").getImage().getScaledInstance(500, 500, 0)));
			}
			else {
				dispose();
				new TwentyFortyEight(true,false,false);
			}
		}

	}

	private boolean checkLoad() {
		File newFile = new File("Save.txt");
	    if (newFile.length() == 0) return false;
		return true;
	}
}