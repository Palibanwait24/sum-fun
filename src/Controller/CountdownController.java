package Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.GridModel;
import View.WindowView;

public class CountdownController implements ActionListener {
	private int counter1 = 300; // number of seconds for the countdown, 5 min = 300
	private GridModel gridModel;
	private WindowView window;
	private JLabel time_holder;
	private JLabel moveLabel;

	public CountdownController(WindowView window, GridModel gridModel, JLabel time_holder, JLabel moveLabel) {
		this.gridModel = gridModel;
		this.window = window;
		this.time_holder = time_holder;
		this.moveLabel = moveLabel;
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
			moveLabel.setText("0");
			JOptionPane.showMessageDialog(null, "Game over! Out of time. Please start a new game.");
			window.getTimer().stop(); // stop game
		}
		if (seconds < 10) {
			time_holder.setText(minutes + ":0" + seconds);
		} else {
			time_holder.setText(minutes + ":" + seconds);
		}
		counter1--;

	}

}
