package audio;
//Credit: ALAN SUN 
//Implemented by Bryan Wang

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {
	
	private static Clip clip;
	private static AudioInputStream audioInput;
	
	public static void playAudio(String audioLocation) {
	
		try {
			
			File sound = new File(audioLocation);
			
			audioInput = AudioSystem.getAudioInputStream(sound);
			
			clip = AudioSystem.getClip();
			
			clip.open(audioInput);
			clip.start();
					
		} catch(Exception ex) {
			
			ex.printStackTrace();
			
		}
		
	}
}
