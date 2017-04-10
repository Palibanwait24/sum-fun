package Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.GridModel;
import View.WindowView;

public class CountdownController implements ActionListener {
	private int counter1 = 5; // number of seconds for the countdown, 5 min = 300
	private GridModel gridModel;
	private WindowView window;
	private JLabel time_holder;
	private JLabel moveLable;

	public CountdownController(WindowView window, GridModel gridModel, JLabel time_holder, JLabel moveLable) {
		this.gridModel = gridModel;
		this.window = window;
		this.time_holder = time_holder;
		this.moveLable = moveLable;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int minutes = counter1 / 60; // number of minutes
		int seconds = counter1 % 60; // number of seconds
		if (gridModel.isGameLost()) {
			window.getTimer().stop();
		}
		if (counter1 == 0) {
			time_holder.setText("0:00");
			gridModel.setMoves(50);
			moveLable.setText("0");
			JOptionPane.showMessageDialog(null, "Game Over: Out of time. Start New Game");// stop game;
			window.getTimer().stop();
		}
		if (seconds < 10) {
			time_holder.setText(minutes + ":0" + seconds);
		} else {
			time_holder.setText(minutes + ":" + seconds);
		}
		counter1--;

	}

}
