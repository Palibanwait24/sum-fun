package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import model.GridModel;
import view.WindowView;

public class CountdownController implements ActionListener {
	private int defaultNumberOfSeconds = 300;// number of seconds for the countdown (5 min = 300)
	private int counter1 = defaultNumberOfSeconds; 
	private GridModel gridModel;
	private WindowView window;
	private JLabel timeHolder;
	private JLabel moveLabel;
	SoundController sound = new SoundController();

	public CountdownController(WindowView window, GridModel gridModel, JLabel timeHolder, JLabel moveLabel) {
		this.gridModel = gridModel;
		this.window = window;
		this.timeHolder = timeHolder;
		this.moveLabel = moveLabel;
	}
	public int getDefaultNumberOfSeconds(){
		return defaultNumberOfSeconds;
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		int minutes = counter1 / 60; // number of minutes
		int seconds = counter1 % 60; // number of seconds
		if (gridModel.getGameLost()) {
			window.getTimer().stop();
		}
		if (counter1 == 0) {
			timeHolder.setText("0:00");
			gridModel.setMoves(50);
			moveLabel.setText("0");
			
			window.getTimer().stop(); // stop game
			sound.chimeGameLost();
			JOptionPane.showMessageDialog(null, "Game over! Out of time. Please start a new game.");
		}

		if (seconds <=10 && seconds>0){
			sound.chimeTimer();
		}
		if (seconds < 10) {


			timeHolder.setText(minutes + ":0" + seconds);
			
		} else {
			timeHolder.setText(minutes + ":" + seconds);
		}
		counter1--;

	}
	public int getTime(){
		window.getTimer().stop();
		return counter1;
	}

}
