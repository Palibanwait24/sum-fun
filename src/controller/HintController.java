package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.GridModel;
import model.QueueModel;
import sumfun.SumFun;
import view.WindowView;

public class HintController implements ActionListener {

	GridModel grid;
	QueueModel queue;
	WindowView window;
	SumFun game;
	int hintsRemaining;

	public HintController(GridModel grid, QueueModel queue, WindowView view, SumFun game) {
		this.window = view;
		this.grid = grid;
		this.queue = queue;
		this.game = game;
		hintsRemaining = 3;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (game.getStop()) {
			JOptionPane.showMessageDialog(null, "You cannot do that now! Please start a new game.");
			return;
		}
		int top = -1;
		if (hintsRemaining > 0) {
			top = queue.getTopOfQueue();
			int[][] gridSums = grid.getGridSums();
			int[][] neighborsRemoved = grid.getNeighborsRemoved(gridSums, top);

			int max = 0;
			int maxRow = 0;
			int maxCol = 0;

			for (int row = 0; row < neighborsRemoved.length; row++) {
				for (int col = 0; col < neighborsRemoved[row].length; col++) {
					if (neighborsRemoved[row][col] > max) {
						max = neighborsRemoved[row][col];
						maxRow = row;
						maxCol = col;
					}
				}
			}

			grid.highlightTile(maxRow, maxCol);
			hintsRemaining--;
		} else {
			JOptionPane.showMessageDialog(null, "No hints remaining!");
		}
	}

}
