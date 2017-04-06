package sumfun;

import Model.*;
import View.WindowView;

// CS360 - Sum Fun Game

public class SumFun {

	public GridModel grid;
	public QueueModel queue;
	public WindowView mainView;
	private final int maxMoves = 50;

	public static void main(String[] args) {
		SumFun game = new SumFun();
		game.run(game);
	}

	public void run(SumFun game) {
		grid = new GridModel(game);
		queue = new QueueModel(game);
		mainView = new WindowView(game, grid, queue); // main game frame
		mainView.addObserver(grid);
		mainView.addObserver(queue);
		grid.start();
		queue.start();
		mainView.setVisible(true);
	}
	public void stop(SumFun game){

    }

	public void move(int row, int col) {
		grid.move(row, col);
	}

	public int getMaxMoves() {
		return maxMoves;
	}

}
