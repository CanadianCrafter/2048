//imports
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import audio.MusicPlayer;

public class TwentyFortyEight extends JFrame implements KeyListener, ActionListener{
	//gui stuff
	public static JFrame frame;
	public static JPanel screen;
	public static JLabel[][] nums; //the "blocks" of numbers
	
	//stores the numbers in the board
	static int board[][];
	//stores the pictures
	private final static ImageIcon imageArr[] = new ImageIcon[13];
	
	//menubar stuff
	private static JMenuBar mb = new JMenuBar();
	private static JMenu menu = new JMenu();;
	private static JMenuItem save;
	private static JMenuItem exit;
	private static JMenuItem restart;
	
	//whether or not the player already reached 2048 or 4096 so that their respective
	//win screens do not trigger twice in one game
	private static boolean haveWon2048; 
	private static boolean haveWon4096;
	
	
	// constructor method
	public TwentyFortyEight(boolean ifLoad,boolean won2048, boolean won4096) {
    	//setup
		nums = new JLabel[4][4];
    	board = new int [4][4];
    	frame = new JFrame(); 
    	screen = new JPanel();
    	haveWon2048 = won2048;
    	haveWon4096 = won4096;
    	loadImages();
    	generateCell();
    	generateCell(); //you start with two pre-generated cells
    	menuBar();
    	frameSetup();
    	panelDesign();
    	if(ifLoad) loadSaveState();
    	panelUpdate();
    	
    }
    
	//puts the images stored in the "Images" folder into the imageArr, array
	private static void loadImages() {
		imageArr[0] = new ImageIcon(new ImageIcon("Images/blank.png").getImage());
		imageArr[1] = new ImageIcon(new ImageIcon("Images/2.png").getImage());
		imageArr[2] = new ImageIcon(new ImageIcon("Images/4.png").getImage());
		imageArr[3] = new ImageIcon(new ImageIcon("Images/8.png").getImage());
		imageArr[4] = new ImageIcon(new ImageIcon("Images/16.png").getImage());
		imageArr[5] = new ImageIcon(new ImageIcon("Images/32.png").getImage());
		imageArr[6] = new ImageIcon(new ImageIcon("Images/64.png").getImage());
		imageArr[7] = new ImageIcon(new ImageIcon("Images/128.png").getImage());
		imageArr[8] = new ImageIcon(new ImageIcon("Images/256.png").getImage());
		imageArr[9] = new ImageIcon(new ImageIcon("Images/512.png").getImage());
		imageArr[10] = new ImageIcon(new ImageIcon("Images/1024.png").getImage());
		imageArr[11] = new ImageIcon(new ImageIcon("Images/2048.png").getImage());
		imageArr[12] = new ImageIcon(new ImageIcon("Images/4096.png").getImage());
		
	}

	//generates a block with either a value of 2 or 4.
    private static void generateCell() {
    	int value = (int)(Math.random()*2)+1; //chooses the log(2,value) of the block;
    										  //This is so that the imagesArr index corresponds with the value in the board (except for 0)
		if(!checkFull()) {//checks if the board is full; if the board is full, a new block won't be added
			int yIndex;
			int xIndex;
			do{
				yIndex = (int)(Math.random()*4);//chooses the y-position of the block
				xIndex = (int)(Math.random()*4);//chooses the x-position of the block
			}while(board[yIndex][xIndex]!=0);//continues to choose coordinates until the coordinate is an empty one
	    	board[yIndex][xIndex]=value;//the coordinate gets the chosen value
		}
    	
		
	}
	
    //loads saved data(block values and positions) from a text file
	private void loadSaveState() {
		try {
			Scanner input = new Scanner(new File("Save.txt")); //read the input from the "Save.txt" file
			input.useDelimiter(","); // use comma to separate data
			for(int i =0;i<4;i++) {
				for(int j=0;j<4;j++) {
					board[i][j] = input.nextInt(); //fills the board array with the data from the file
				}
			}
			input.close();
		}
		// print the error if there is one
		catch (FileNotFoundException error) {
			System.out.println(error);
		}
		//after the data is extracted, the save is wiped so that, you cannot return to a used save.
		//saving merely pauses time; not reverse it
		try {
			new PrintWriter("Save.txt").close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//gives haveWon2048 and haveWon4096 their value, which check if 2048 and 4096 have been achieved
		//this is done through looping through the board, for values >= 2048
		for(int i=0;i<4;i++) {
			for(int j =0;j<4;j++) {
				if(board[i][j]==11) haveWon2048=true;
				else if (board[i][j]==12) {
					haveWon2048=true;
					haveWon4096=true;
				}
			}
			
		}
	}

	//sets up the JFrame
	private void frameSetup() {
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //program will end when exited
		frame.setSize(450,450); // sets the size of the frame
		frame.setTitle("2048");
		frame.setBounds(0,0,456,502);
		frame.setLayout(null);
		frame.setResizable(false); // can't resize
		frame.add(screen); // add panel to the frame
		frame.validate();
		frame.repaint();
		frame.setVisible(true); 
		frame.addKeyListener(this);
		
	}
	
	//creates menubar
	private void menuBar() {
		mb = new JMenuBar();
		menu = new JMenu("Menu");
		
		//menu items
		save = new JMenuItem("Save and Exit");
		exit = new JMenuItem("Exit");
		restart = new JMenuItem("Restart");

		// add to action listener for the menu items
		save.addActionListener(this);
		exit.addActionListener(this);
		restart.addActionListener(this);
		
		frame.setJMenuBar(mb); // add menu bar
		mb.add(menu); // add menu to menubar
		menu.add(restart); //add items
		menu.add(save); 
		menu.add(exit);
		
	}

	//sets up the panel
	private static void panelDesign() {
		screen.setBorder(null);
		screen.setBackground(new java.awt.Color(47, 47, 47));
		screen.setBounds(0,0,456,502);
		screen.setLayout(null);
		//gives each block their label, and image
		for(int i =0;i<4;i++) {
			for(int j =0;j<4;j++){
				nums[i][j]=new JLabel();
				nums[i][j].setBounds(10 + 110 * j, 10+110*i, 100, 100);  //location moves so labels don't overlap
				nums[i][j].setIcon(imageArr[board[i][j]]); //the imagesArr index corresponds with the value on the board
				screen.add(nums[i][j]);
			}
		}
		frame.repaint();
	}
	
	//updates the board again
	private static void panelUpdate() {
		for(int i =0;i<4;i++) {
			for(int j =0;j<4;j++){
				nums[i][j].setIcon(imageArr[board[i][j]]);
				screen.add(nums[i][j]);
			}
		}
		frame.repaint();
	}


    //controls the movement of the blocks
	private static void move(String s) {
		//comments are for the "UP" direction only, the other three directions are similar
		//There are probably better optimized solutions, and I probably should have combined several methods (like checkUp) to reduce redundancy
		
		//if the player presses up (gravity pulls from above)
		if(s.equals("UP")) {
			for(int i =0;i<4;i++) {
				
				//this segment makes sure there aren't any empty blocks in the middle of two numbers or the gravity side and a number
				//For example, 2,0,0,4. when they move, the zeroes should be at the end like 2,4,0,0
				ArrayList<Integer> list;
				//this will repeat until there are no middle zeroes
				do {
					for(int j =3;j>0;j--) {
						//if an above block is blank, it is swapped with the block below it
						//the zeroes sink down
						if(board[j-1][i]==0) {
							board[j-1][i]=board[j][i];
							board[j][i]=0;
						}
					}
					
					list = new ArrayList<Integer>();
					for(int j =0;j<4;j++)list.add(board[j][i]);//puts each column into the list
					
					//check that all zeroes are at the back, so no block is floating (relative to the direction pressed)
					//starting from the end of the list (side farthest from gravity), remove the zeroes
					for(int j =3;j>=0;j--) { 
						if(list.get(j)==0) list.remove(j);
						else break; //stops when the first non-zero block is reached
					}
					list.trimToSize();
					//since all the zeroes should be at the end, there shouldn't be any zeroes left
				}while(list.contains(0)); //repeat until all zeroes have sunk to the end of the list
				
				//if two adjacent blocks in the same column (row, if moving left or right)
				//add their values together to become one block (because we are in log(2,value), we just add one to the value)
				int hold = -1;
				for(int j =0;j<=3;) {
					int num = board[j][i];
					if(num==hold&&num!=0) { //if the number isn't zero, and the number is the same as the previous,
											//add one to the value closer to gravity; the other disappears and becomes blank
						board[j-1][i]++;
						board[j][i]=0;
						//since a new blank is formed, other values bubble down to fill the empty space
						for(int k =j+1;k<=3;k++) {
							board[k-1][i]=board[k][i];
							board[k][i]=0;
						}
						j--;//since a new block filled this position, it needs to be rechecked
						hold=-1;//the held block no longer exists
					}
					else hold = num;
					j++;
				}
			}
		}
		else if(s.equals("DOWN")) {
			
			for(int i =0;i<4;i++) {
				ArrayList<Integer> list;
				do {
					for(int j =0;j<3;j++) {
						if(board[j+1][i]==0) {
							board[j+1][i]=board[j][i];
							board[j][i]=0;
						}
					}
					list = new ArrayList<Integer>();
					for(int j =3;j>=0;j--) list.add(board[j][i]);

					for(int j =3;j>=0;j--) {
						if(list.get(j)==0) list.remove(j);
						else break;
					}
					list.trimToSize();
				}while(list.contains(0));
				
				int hold = -1;
				for(int j =3;j>=0;) {
					int num = board[j][i];
					if(num==hold&&num!=0) {
						board[j+1][i] ++;
						board[j][i]=0;
						for(int k =j-1;k>=0;k--) {
							board[k+1][i]=board[k][i];
							board[k][i]=0;
						}
						j++;
						hold=-1;
					}
					else hold = num;
					j--;
				}
			}
		}
		else if(s.equals("LEFT")) {
			for(int i =0;i<4;i++) {
				ArrayList<Integer> list;
				do {
					for(int j =3;j>0;j--) {
						if(board[i][j-1]==0) {
							board[i][j-1]=board[i][j];
							board[i][j]=0;
						}
					}
					list = new ArrayList<Integer>();
					for(int j =0;j<4;j++)list.add(board[i][j]);
					
					for(int j =3;j>=0;j--) {
						if(list.get(j)==0) list.remove(j);
						else break;
					}
					list.trimToSize();
				}while(list.contains(0));
				
				int hold = -1;
				for(int j =0;j<=3;) {
					int num = board[i][j];
					if(num==hold&&num!=0) {
						board[i][j-1] ++;
						board[i][j]=0;
						for(int k =j+1;k<=3;k++) {
							board[i][k-1]=board[i][k];
							board[i][k]=0;
						}
						j--;
						hold=-1;
					}
					else hold = num;
					j++;
				}
			}
		}
		else if(s.equals("RIGHT")) {
			for(int i =0;i<4;i++) {
				ArrayList<Integer> list;
				do {
					for(int j =0;j<3;j++) {
						if(board[i][j+1]==0) {
							board[i][j+1]=board[i][j];
							board[i][j]=0;
						}
					}
					
					list = new ArrayList<Integer>();
					for(int j =3;j>=0;j--)list.add(board[i][j]);
					
					for(int j =3;j>=0;j--) {
						if(list.get(j)==0) list.remove(j);
						else break;
					}
					list.trimToSize();
				}while(list.contains(0));
				
				int hold = -1;
				for(int j =3;j>=0;) {
					int num = board[i][j];
					if(num==hold&&num!=0) {
						board[i][j+1] ++;
						board[i][j]=0;
						for(int k =j-1;k>=0;k--) {
							board[i][k+1]=board[i][k];
							board[i][k]=0;
						}
						j++;
						hold=-1;
					}
					else hold = num;
					j--;
				}
			}
		}
		MusicPlayer.playClip("Switch Clack.wav");
		panelUpdate();
		frame.repaint();
		checkWin(); //checks win after each move
		
	}

	//checks if the board is full; if there are empty spaces - not if game is over
	//returns true if it is full
	//this is used to check if there are empty spaces to spawn blocks
	private static boolean checkFull() {
		for(int line[] : board) {
			for(int num : line) {
				if(num==0) return false;
			}
		}
		
		return true;
	}
	
	//checks if you can move horizontally. 
	//returns true if you can't move horizontally
	//this is used to check if the game is over
	private static boolean checkHorizontal() {
		for(int i =0;i<4;i++) {
			int hold = -1;
			for(int j =0;j<4;j++) {
				int num = board[i][j];
				if(num==hold&&num!=0)return false; //if two numbers in the same row are adjacent, then they can be smushed,
												   //thus you can still move horizontally
				hold = num;
			}
		}
		return true;
	}
	
	//checks if you can move vertically.
	//returns true if you can't move vertically
	//this is used to check if the game is over
	private static boolean checkVertical() {
		for(int i =0;i<4;i++) {
			int hold = -1;
			for(int j =0;j<4;j++) {
				int num = board[j][i];
				if(num==hold&&num!=0)return false;//if two numbers in the same column are adjacent, then they can be smushed,
												  //thus you can still move vertically
				hold = num;
			}
		}
		return true;
	}
	
	//checks if the game is over and player lost
	private static boolean checkEnd() {
		boolean full = checkFull();
		boolean horizontal = checkHorizontal();
		boolean vertical = checkVertical();
		if(full&&horizontal&&vertical) return true; //if all conditions are met, the game is over
		else return false;
	}
	
	//checks if the game has been won
	private static void checkWin() {
		//searches the board for 2048 and 4096
		boolean win2048 = false;
		boolean win4096 = false;
		for(int i=0;i<4;i++) {
			for(int j =0;j<4;j++) {
				if(board[i][j]==11) win2048=true;
				else if (board[i][j]==12) win4096=true;
			}
			
		}
		//if 2048 or 4096 is found for the first time, open the win screen
		//the win screen updates the global variables
		if(win4096&&!haveWon4096) {
			save(); //game is saved so that after the win screen, this game can be loaded and continued
			frame.dispose();
			new WinScreen(4096); //indicates 4096 win
		}
		else if (win2048&&!haveWon2048) {
			save();
			frame.dispose();
			new WinScreen(2048);//indicates 2048 win
		}
		
	}
	
	//checks if you can move up/change the gravity to up (not including smushing blocks together; that's for checkVertical)
	//returns true if there are empty blocks between two numbers or a number and the gravity side (like 2,0,0,4) (you can move up)
	//since the other 3 directions are similar, only this direction will have proper commenting
	private static boolean checkUp() {
		ArrayList<Integer> list;
		for(int i =0;i<4;i++) {
			list = new ArrayList<Integer>();
			for(int j =0;j<4;j++)list.add(board[j][i]); //puts each number from a column into a list
			//check that all zeroes are at the back, so no block is floating (relative to the direction pressed)
			//starting from the end of the list (side farthest from gravity), remove the zeroes
			for(int j =3;j>=0;j--) {
				if(list.get(j)==0) list.remove(j);
				else break; //stops when the first non-zero block is reached
			}
			list.trimToSize(); 
			if(list.contains(0))return true; //since all the trailing zeroes are removed, any remaining zeroes are in the middle; thus you can move up
		}
		return false; //you cannot move up
	}
	
	private static boolean checkDown() {
		ArrayList<Integer> list;
		for(int i =0;i<4;i++) {
			list = new ArrayList<Integer>();
			for(int j =3;j>=0;j--) list.add(board[j][i]);

			for(int j =3;j>=0;j--) {
				if(list.get(j)==0) list.remove(j);
				else break;
			}
			list.trimToSize();
		if(list.contains(0))return true;
		}
		return false;
	}
	private static boolean checkRight() {
		ArrayList<Integer> list;
		for(int i =0;i<4;i++) {
			list = new ArrayList<Integer>();
			for(int j =3;j>=0;j--)list.add(board[i][j]);
			
			for(int j =3;j>=0;j--) {
				if(list.get(j)==0) list.remove(j);
				else break;
			}
		if(list.contains(0))return true;
		}
		return false;
	}
	
	private static boolean checkLeft() {
		ArrayList<Integer> list;
		for(int i =0;i<4;i++) {
			list = new ArrayList<Integer>();
			for(int j =0;j<4;j++)list.add(board[i][j]);
			
			for(int j =3;j>=0;j--) {
				if(list.get(j)==0) list.remove(j);
				else break;
			}
			list.trimToSize();
		if(list.contains(0))return true;
		}
		return false;
	}
	
	//saves the game onto a text file
	private static void save() {
		try {
			PrintWriter writer = new PrintWriter("Save.txt");
			//adds the values of the board delimited by commas, onto the save file
			for(int i =0;i<4;i++) {
				for(int j=0;j<4;j++) {
					writer.print(board[i][j] + ",");
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//carries out the actions for each of the menu bar buttons
	public void actionPerformed(ActionEvent event) {
		//saves the game
		if(event.getSource() == save) {
			save();
			System.exit(0);
		}
		//exits the game (doesn't auto save... to small of a game for people to really want that)
		if(event.getSource()==exit) {
			System.exit(0);
		}
		//restart the game
		if(event.getSource()==restart) {
			frame.dispose();
			new TwentyFortyEight(false,false,false);
		}
	}

	//given the key pressed, first check if movement is possible in the direction, then change the gravity
	public void keyPressed(KeyEvent key) {
		if(key.getKeyCode() == key.VK_W||key.getKeyCode()==key.VK_UP) {
			if(checkVertical()&&!checkUp()) return;
			else move("UP");
		}
		else if(key.getKeyCode() == key.VK_S||key.getKeyCode()==key.VK_DOWN) {
			if(checkVertical()&&!checkDown()) return;
			else move("DOWN");
		}
		else if(key.getKeyCode() == key.VK_A||key.getKeyCode()==key.VK_LEFT) {
			if(checkHorizontal()&&!checkLeft())	return;
			else move("LEFT");
		}
		else if(key.getKeyCode() == key.VK_D||key.getKeyCode()==key.VK_RIGHT) {
			if(checkHorizontal()&&!checkRight())return;
			else move("RIGHT");
		}
		else {
			return;
		}
			
		panelUpdate();
		frame.repaint();
		generateCell(); //after a successful move, add a new block
		panelUpdate();
		if(checkEnd()) { //check if the game is over after a successful move, and a new block has spawned
			frame.setVisible(false);
			new EndScreen();
		}
	}

	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
}