/* Name: Aaron Su
 * Date: November 20, 2023
 * Course Code: ICS - 4U1
 * Title: Final Summative Project - SDP #1
 * Description: This class will be used and called to play music based on the users file path
 * Major Skills: Constructor, Scanner, Sound / Music from Javax.Sound.Sampled
 * Added Features: Everything
 * Contribution to Assignment: Aaron Su - 100%
 */

// import our necessities 
package Controller;

import java.io.*;
import javax.sound.sampled.*;

// sourced from https://www.youtube.com/watch?v=TErboGLHZGA

public class PlaySong {

	// create our global variable clip, from the Clip class
	static Clip clip;

	// playMusic method will try and play music from the provided file path,
	// starting from the provided time position
	public static void playMusic(String filePath, long timePosition) {

		// create Scanner to play the music
		try {
			File musicPath = new File(filePath); // set File musicPath to a new file of the argument of filePath

			// if a musicPath is found, the following will execute
			if (musicPath.exists()) {

				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath); // set audioInput to the audio
																							// of the .wav file
				clip = AudioSystem.getClip(); // set clip to a new clip
				clip.open(audioInput); // open the audio
				clip.setMicrosecondPosition(timePosition); // set the clip to the timePosition
				clip.start(); // play the clip
			}

			// otherwise, display in the console that we can't find the file
			else {
				System.out.println("Cant Find File");
			}

		} catch (Exception e) { // if the above code did not work, display in the console that there is a file
								// error
			System.out.println("File Error");
		}
	}
}
