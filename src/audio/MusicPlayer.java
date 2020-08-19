package audio;
//Partial Credit: ALAN SUN 
// Implemented by Bryan Wang
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {
	
	private static Clip clip;
	private static Clip SE;
	private static AudioInputStream audioInput;
	
	public static void playAudio(String audioLocation) {
	
		try {
			
			File sound = new File(audioLocation);
			
			audioInput = AudioSystem.getAudioInputStream(sound);
			
			clip = AudioSystem.getClip();
			
			clip.open(audioInput);

			clip.loop(Clip.LOOP_CONTINUOUSLY);
			
			clip.start();
					
		} catch(Exception ex) {
			
			ex.printStackTrace();
			
		}
		
	}
	
	//done by bryan; plays a clip once.
	public static void playClip(String audioLocation) { 
		
		try {
			
			File sound = new File(audioLocation);
			
			audioInput = AudioSystem.getAudioInputStream(sound);
			
			SE = AudioSystem.getClip();
			
			SE.open(audioInput);
		
			SE.start();
					
		} catch(Exception ex) {
			
			ex.printStackTrace();
			
		}
		
	}
	
	public static void stopMusic(){
		
		clip.stop();
		
	}
}
