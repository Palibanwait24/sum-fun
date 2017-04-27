package controller;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class SoundController {

	private AudioInputStream audioInputStream;
	private Clip clip;

	public SoundController() {
		// new sound controller
	}

	public void chimeRemoveTile() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources/removeTile.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException ex) {
			System.out.println("Could not open sound effect file");
		} catch (Exception ex) {
			System.out.println("Error occured in SoundController.chimeRemoveTile()");
		}
	}
}
