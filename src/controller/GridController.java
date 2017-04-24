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
		
		try{
			System.out.println("1");
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources/removeTile.wav").getAbsoluteFile());
			System.out.println("2");
			
			clip = AudioSystem.getClip();
			System.out.println("3");
			clip.open(audioInputStream);
			System.out.println("4");
			clip.start();
			System.out.println("5");
		}catch(Exception ex){
			System.out.println(ex);
		}
	}

}
