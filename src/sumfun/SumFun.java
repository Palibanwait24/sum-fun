package sumfun;

import Model.*;
import View.WindowView;

// CS360 - Sum Fun Game

public class SumFun {

	public GridModel grid;
	public QueueModel queue;
	public WindowView mainView;
	private final int maxMoves = 50;
	public boolean timedGame = true;

	public static void main(String[] args) {
		SumFun game = new SumFun();
		game.run(game);
	}

	public void run(SumFun game) {

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
