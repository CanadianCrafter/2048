import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import audio.MusicPlayer;

public class TwentyFortyEight extends JFrame implements KeyListener, ActionListener{
	public static JFrame frame;
	public static JPanel screen;
	public static JLabel[][] nums; 
	static int board[][];
	private static JMenuBar mb = new JMenuBar();
	private static JMenu menu = new JMenu();;
	private static JMenuItem save;
	private static JMenuItem exit;
	private static JMenuItem restart;
	private static boolean haveWon2048;
	private static boolean haveWon4096;
	// pictures
	private final static ImageIcon imageArr[] = new ImageIcon[13];
	
	
//    public static void main (String[] args){
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
    	generateCell();
    	menuBar();
    	frameSetup();
    	panelDesign();
    	if(ifLoad)loadSaveState();
    	panelUpdate();
    	
    	
//    	for(int i =0;i<16;i++) {
//		generateCell();
//		
//	}
//    	board[0][2]=2;
//    	board[0][3]=4;
//		console input
//    	Scanner in = new Scanner(System.in);
//    	//MAKE SURE THAT IF NOTHING ACTUALLY MOVES, DO NOT SPAWN NEW NUMBER
//    	char ch='b';
//    	boolean endGame = false;
//    	while(ch!='q'&&!endGame) {
//    		ch = in.next().toLowerCase().charAt(0);
//    		if(ch=='w') move("UP");
//    		else if(ch =='s') move("DOWN");
//    		else if(ch =='d') move("RIGHT");
//    		else if(ch =='a') move("LEFT");
//    		else if(ch =='q') System.out.println("FINISHED");
//       		generateCell();
//       		panelUpdate();
//    		printBoard();
//    		endGame = checkEnd();
//    		
//    	}
//    	System.out.println("GAME OVER");
    	
    	
    	
//    	for(int i =0;i<16;i++) {
//    		generateCell();
//    		printBoard();
//    		
//    	}
//    	System.out.println("----");
//    	move("RIGHT");
//    	printBoard();
    	

    }
    



	private void loadSaveState() {
		try {
			Scanner input = new Scanner(new File("Save.txt")); //read the input from the "Save.txt" file
			input.useDelimiter(","); // use comma to separate data
			for(int i =0;i<4;i++) {
				for(int j=0;j<4;j++) {
					board[i][j] = input.nextInt();
				}
			}
			input.close();
		}
		// print the error if there is one
		catch (FileNotFoundException error) {
			System.out.println(error);
		}
		try {
			new PrintWriter("Save.txt").close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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


	private void menuBar() {
		mb = new JMenuBar();
		menu = new JMenu("Menu");

		save = new JMenuItem("Save and Exit");
		exit = new JMenuItem("Exit");
		restart = new JMenuItem("Restart");

		// add to action listener
		save.addActionListener(this);
		exit.addActionListener(this);
		restart.addActionListener(this);
		
		frame.setJMenuBar(mb); // add menu bar
		mb.add(menu); // add menu to menubar
		menu.add(restart);
		menu.add(save); // add item1
		menu.add(exit);
		
	}





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


	private void frameSetup() {
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(450,450); // sets the size of the frame
//		frame.setBounds(0,0,450,450);
		frame.setTitle("2048");
		frame.setBounds(0,0,456,502);
		frame.setLayout(null);
		frame.setResizable(false); // can't resize
		frame.add(screen); // add panel to the frame
		frame.validate();
		frame.repaint();
		frame.setVisible(true);
		frame.addKeyListener(this);
		frame.setResizable(false);
		
	}
	
	private static void panelDesign() {
		screen.setBorder(null);
		screen.setBackground(new java.awt.Color(47, 47, 47));
//		screen.setBounds(0,0,450,450);
		screen.setBounds(0,0,456,502);
		screen.setLayout(null);
		for(int i =0;i<4;i++) {
			for(int j =0;j<4;j++){
				nums[i][j]=new JLabel();
				nums[i][j].setBounds(10 + 110 * j, 10+110*i, 100, 100);
				nums[i][j].setIcon(imageArr[board[i][j]]);
				screen.add(nums[i][j]);
			}
		}
		frame.repaint();
	}
	private static void panelUpdate() {
		for(int i =0;i<4;i++) {
			for(int j =0;j<4;j++){
				nums[i][j].setIcon(imageArr[board[i][j]]);
				screen.add(nums[i][j]);
			}
		}
		frame.repaint();
	}




	//generates a random square.
    private static void generateCell() {
//		int value = (int) Math.pow(2, (int)(Math.random()*2)+1);
    	int value = (int)(Math.random()*2)+1;
		if(!checkFull()) {
			int yIndex;
			int xIndex;
			do{
				yIndex = (int)(Math.random()*4);
				xIndex = (int)(Math.random()*4);			
			}while(board[yIndex][xIndex]!=0);
			
	    	board[yIndex][xIndex]=value;
		}
    	
		
	}
    
	private static void move(String s) {
		if(s.equals("UP")) {
			for(int i =0;i<4;i++) {
				ArrayList<Integer> list;
				do {
					for(int j =3;j>0;j--) {
						if(board[j-1][i]==0) {
							board[j-1][i]=board[j][i];
							board[j][i]=0;
						}
					}
					list = new ArrayList<Integer>();
					for(int j =0;j<4;j++)list.add(board[j][i]);
					
					for(int j =3;j>=0;j--) {
						if(list.get(j)==0) list.remove(j);
						else break;
					}
					list.trimToSize();
//					System.out.println(list.size());
//					System.out.println("A" + list.toString());
				}while(list.contains(0));
				
				int hold = -1;
				for(int j =0;j<=3;) {
					int num = board[j][i];
					if(num==hold&&num!=0) {
						board[j-1][i] ++;
						board[j][i]=0;
						for(int k =j+1;k<=3;k++) {
							board[k-1][i]=board[k][i];
							board[k][i]=0;
						}
						j--;
						hold=-1;
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
//					System.out.println("B" + list.toString());
					//replace string with arraylist with same function
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
//						board[i][j+1] +=num;
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
		checkWin();
		
	}



	private static boolean checkFull() {
		for(int line[] : board) {
			for(int num : line) {
				if(num==0) return false;
			}
		}
		
		return true;
	}
	//checks if you can move horizontally. returns true if you can't
	private static boolean checkHorizontal() {
		
		for(int i =0;i<4;i++) {
			int hold = -1;
			for(int j =0;j<4;j++) {
				int num = board[i][j];
				if(num==hold&&num!=0)return false;
				hold = num;
			}
		}
		return true;
	}
	
	//checks if you can move vertically. returns true if you can't
	private static boolean checkVertical() {
		for(int i =0;i<4;i++) {
			int hold = -1;
			for(int j =0;j<4;j++) {
				int num = board[j][i];
				if(num==hold&&num!=0)return false;
				hold = num;
			}
		}
		return true;
	}
	
	private static boolean checkUp() {
		ArrayList<Integer> list;
		for(int i =0;i<4;i++) {
			list = new ArrayList<Integer>();
			for(int j =0;j<4;j++)list.add(board[j][i]);
			
			for(int j =3;j>=0;j--) {
				if(list.get(j)==0) list.remove(j);
				else break;
			}
			list.trimToSize();
		if(list.contains(0))return true;
		}
		return false;
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
	

	
	private static boolean checkEnd() {
		boolean full = checkFull();
		boolean horizontal = checkHorizontal();
		boolean vertical = checkVertical();
		
		if(full&&horizontal&&vertical) return true;
		else return false;
		
		
	}
	private static void checkWin() {
		boolean win2048 = false;
		boolean win4096 = false;
		for(int i=0;i<4;i++) {
			for(int j =0;j<4;j++) {
				if(board[i][j]==11) win2048=true;
				else if (board[i][j]==12) win4096=true;
			}
			
		}
		if(win4096&&!haveWon4096) {
			save();
			frame.dispose();
			new WinScreen(4096);
		}
		else if (win2048&&!haveWon2048) {
			save();
			frame.dispose();
			new WinScreen(2048);
		}
		
	}


	//prints board in console
	private static void printBoard() {
    	for(int line[] : board) {
			for(int num : line) {
				if(num!=0)System.out.print(Math.pow(2, num) + " \t");
//				else System.out.print("  ");
				else System.out.print(". \t");//temp
			}
			System.out.println();
			System.out.println();
		}
    	System.out.println();
		
	}




	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == save) {
			save();
			System.exit(0);
		}
		if(event.getSource()==exit) {
			System.exit(0);
		}
		if(event.getSource()==restart) {
			frame.dispose();
			new TwentyFortyEight(false,false,false);
		}
		
	}


	private static void save() {
		try {
			PrintWriter writer = new PrintWriter("Save.txt");
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




	@Override
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
		generateCell();
		panelUpdate();
		if(checkEnd()) {
			frame.setVisible(false);
			new EndScreen();
		}
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}