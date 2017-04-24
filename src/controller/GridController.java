package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
//import javax.sound.sampled.UnsupportedFIleException;

import sumfun.SumFun;

public class GridController implements ActionListener {

	private SumFun game;
	private int row;
	private int col;

	public GridController(int r, int c, SumFun game) {
		this.game = game;
		row = r;
		col = c;
	}

	public void actionPerformed(ActionEvent click) {
		game.move(row, col);

		AudioInputStream audioInputStream;
		Clip clip;

		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources/removeTile.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException ex) {
			System.out.println("Could not open sound effect file");
		} catch (Exception ex) {
			System.out.println("Error occured in sound()");
		}
	}

}
