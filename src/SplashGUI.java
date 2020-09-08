//imports
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import audio.MusicPlayer;

//hello
public class SplashGUI extends JFrame implements ActionListener {
	//gui stuff
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

	//set up the frame
	private void frameSetup() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(500, 500);
		setLayout(null);
		add(splashScreen);
		setVisible(true);
		setResizable(false);
	}

	//set up the panel
	private void panelDesign() {
		//add features for the screen
		splashScreen.setBorder(null);
		splashScreen.setBounds(0, 0, 500, 500);
		splashScreen.setLayout(null);
		
		//add features for the buttons
		playButton.addActionListener(this);
		playButton.setBounds(85, 285, 330, 40); 
		playButton.setOpaque(false); //The following three lines make the button completely invisible
		playButton.setContentAreaFilled(false);
		playButton.setBorderPainted(false);
		splashScreen.add(playButton); 
		
		loadButton.addActionListener(this);
		loadButton.setBounds(85, 365, 330, 40);
		loadButton.setOpaque(false); 
		loadButton.setContentAreaFilled(false);
		loadButton.setBorderPainted(false);
		splashScreen.add(loadButton);
		
		//adds the image that is the splash screen
		image.setBounds(0,0,500,500);
		image.setIcon(new ImageIcon(new ImageIcon("Images/Splash Screen.png").getImage().getScaledInstance(500, 500, 0)));
		splashScreen.add(image);
		repaint();

	}
	
	//carries out the actions for each of the buttons
	public void actionPerformed(ActionEvent event) {
		//starts a new game
		if(event.getSource()==playButton) {
			//wipes the save file
			try {
				new PrintWriter("Save.txt").close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			dispose();
			new TwentyFortyEight(false,false,false); //starts a game without loading saves
			
		}
		//loads in a saved game
		else if(event.getSource()==loadButton) {
			//if the save file is empty, change the splash screen image to show an error message
			if(!checkLoad()) {
				image.setIcon(new ImageIcon(new ImageIcon("Images/Splash Screen Error.png").getImage().getScaledInstance(500, 500, 0)));
			}
			//if the save file isn't empty, start a game that loads the save
			else {
				dispose();
				new TwentyFortyEight(true,false,false);
			}
		}

	}
	
	//checks if the save file has data
	//if the save file is empty, return false
	private boolean checkLoad() {
		File newFile = new File("Save.txt");
	    if (newFile.length() == 0) return false;
		return true;
	}
}