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

	public void chimeTileSet() { // placed 
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources/tileSet.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException ex) {
			//System.out.println("Could not open sound effect file");
		} catch (Exception ex) {
			//System.out.println("Error occured in SoundController.chimeRemoveTile()");
		}
	}
	public void chimeTimer() { // placed 
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources/timer.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException ex) {
			//System.out.println("Could not open sound effect file");
		} catch (Exception ex) {
			//System.out.println("Error occured in SoundController.chimeRemoveTile()");
		}
	}
	public void chimeRemove3Tiles() { // placed
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources/3TilesRemoved.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException ex) {
			//System.out.println("Could not open sound effect file");
		} catch (Exception ex) {
			//System.out.println("Error occured in SoundController.chimeRemoveTile()");
		}
	}
	public void chimeInvalidMove() { // placed
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources/invalidMove.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException ex) {
			//System.out.println("Could not open sound effect file");
		} catch (Exception ex) {
			//System.out.println("Error occured in SoundController.chimeRemoveTile()");
		}
	}
	public void chimeGameWon() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources/winGame.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException ex) {
			//System.out.println("Could not open sound effect file");
		} catch (Exception ex) {
			//System.out.println("Error occured in SoundController.chimeRemoveTile()");
		}
	}
	public void chimeGameLost() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources/gameOver.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException ex) {
			//System.out.println("Could not open sound effect file");
		} catch (Exception ex) {
			//System.out.println("Error occured in SoundController.chimeRemoveTile()");
		}
	}
	public void chimeHint() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources/hint.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException ex) {
			//System.out.println("Could not open sound effect file");
		} catch (Exception ex) {
			//System.out.println("Error occured in SoundController.chimeRemoveTile()");
		}
	}
	public void chimeRefresh() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources/refresh.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException ex) {
			//System.out.println("Could not open sound effect file");
		} catch (Exception ex) {
			//System.out.println("Error occured in SoundController.chimeRemoveTile()");
		}
	}
	public void chimeRemoveInstance() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources/removeInstance.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException ex) {
			//System.out.println("Could not open sound effect file");
		} catch (Exception ex) {
			//System.out.println("Error occured in SoundController.chimeRemoveTile()");
		}
	}
}
