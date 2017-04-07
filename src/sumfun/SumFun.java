package sumfun;

import Model.*;
import View.WindowView;
import javax.swing.JOptionPane;

// CS360 - Sum Fun Game

public class SumFun {

	public GridModel grid;
	public QueueModel queue;
	public WindowView mainView;
	private final int maxMoves = 50;
	public boolean timedGame = false;


	public static void main(String[] args) {
		SumFun game = new SumFun();
		game.run(game);
	}

	public void run(SumFun game) {
		Object[] options = {"Untimed Game", "Timed Game"};
		int n = JOptionPane.showOptionDialog(null,//parent container of JOptionPane
				"What type of Game do you want to play?", "Type Of Game",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

		if (n == JOptionPane.NO_OPTION){
			timedGame = true;
		}
		queue = new QueueModel(game);
		grid = new GridModel(game, queue);
		mainView = new WindowView(game, grid, queue, timedGame); // main game frame
		mainView.addObserver(grid);
		mainView.addObserver(queue);
		grid.start();
		queue.start();
		mainView.setVisible(true);
	}

	public void stop(SumFun game) {

	}

	public void move(int row, int col) {
		grid.move(row, col);
	}

	public int getMaxMoves() {
		return maxMoves;
	}

}
