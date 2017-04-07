package sumfun;

import Model.*;
import View.WindowView;
import javax.swing.JOptionPane;

// CS360 - Sum Fun Game

public class SumFun {

	public GridModel grid;
	public QueueModel queue;
	public BotModel bot;
	public WindowView mainView;
	private final int maxMoves = 50;
	private boolean timedGame = false;
	private boolean botEnabled = false;

	public static void main(String[] args) {
		SumFun game = new SumFun();
		game.run(game);
	}

	public void run(SumFun game) {
		selectGame();

		queue = new QueueModel(game);
		grid = new GridModel(game, queue);
		bot = new BotModel(grid);
		mainView = new WindowView(game, grid, queue, timedGame); // main game frame
		mainView.addObserver(grid);
		mainView.addObserver(queue);
		grid.start();
		queue.start();
		mainView.setVisible(true);
		if (botEnabled) {
			bot.run();
		}
	}

	private void selectGame() {
		boolean dev = true; // turn off later before we turn in
		if (dev) {
			Object[] options = { "Watch bot", "Timed game", "Untimed game" };
			int n = JOptionPane.showOptionDialog(null, //parent container of JOptionPane
					"Welcome to Sum Fun!\nWhat type of game do you want to play?", "Select Game",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

			// we should add a quit option to this ^^^^ closing it starts new game
			if (n == 0) {
				botEnabled = true;
			} else if (n == 1) {
				timedGame = true;
			} else {
				// untimed game
			}
		} else {
			Object[] options = { "Timed game", "Untimed game" };
			int n = JOptionPane.showOptionDialog(null, //parent container of JOptionPane
					"Welcome to Sum Fun!\nWhat type of game do you want to play?", "Select Game",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

			// we should add a quit option to this ^^^^ closing it starts new game
			if (n == 0) {
				timedGame = true;
			} else {
				// untimed game
			}
		}

	}

	public void stop(SumFun game) {

	}

	public void move(int row, int col) {
		/*
		if (botEnabled) {
			bot.move();
		} else {
			grid.move(row, col);
		}
		*/
		grid.move(row, col);
	}

	public int getMaxMoves() {
		return maxMoves;
	}

	public boolean getTimedGame() {
		return timedGame;
	}

}
