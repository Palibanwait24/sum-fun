package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.GridModel;
import model.QueueModel;

import view.WindowView;

public class HintController implements ActionListener {

	GridModel grid;
	QueueModel queue;
	WindowView window;
	int hintsRemaining;

	public HintController(GridModel grid, QueueModel queue, WindowView view) {
		this.window = view;
		this.grid = grid;
		this.queue = queue;
		hintsRemaining = 3;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int top = -1;
		if (hintsRemaining > 0) {
			top = queue.getTopOfQueue();
			hintsRemaining--;
		} else {
			JOptionPane.showMessageDialog(null, "No hints remaining!");
		}
	}

}
