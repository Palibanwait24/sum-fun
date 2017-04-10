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
	private boolean timedGame = false; // flag for timed or untimed game
	private boolean botEnabled = false; // flag for if bot is enabled
	private boolean stop = false; // flag to continue or stop game

	public static void main(String[] args) {
		SumFun game = new SumFun();
		game.run(game);
	}

	public void run(SumFun game) {
		selectGame();

		queue = new QueueModel(game);
		grid = new GridModel(game, queue);
		mainView = new WindowView(game, grid, queue, timedGame); // main game frame
		bot = new BotModel(game, grid);
		mainView.addObserver(grid);
		mainView.addObserver(queue);
		grid.start();
		queue.start();
		mainView.setVisible(true);
		if (botEnabled) {
			bot.run();
		}
	}

	public void startNewGame() {
		// TODO stop old game
		stop = true;
		SumFun newGame = new SumFun();
		//newGame.setStop() = true; // something like this but fuck
		newGame.run(newGame);
		/*
		selectGame();
		
		queue = new QueueModel(oldGame);
		grid = new GridModel(oldGame, oldGame.queue);
		oldGame.mainView = new WindowView(oldGame, oldGame.grid, oldGame.queue, oldGame.getTimedGame());
		oldGame.mainView.addObserver(oldGame.grid);
		oldGame.mainView.addObserver(oldGame.queue);
		oldGame.grid.start();
		oldGame.queue.start();
		oldGame.mainView.setVisible(true);
		*/
	}

	private void selectGame() {
		boolean dev = true; // turn off later before we turn in
		if (dev) {
			Object[] options = { "Exit", "Watch bot", "Timed game", "Untimed game" };
			int n = JOptionPane.showOptionDialog(null, //parent container of JOptionPane
					"Welcome to Sum Fun!\nWhat type of game do you want to play?", "Select Game",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[3]);

			// we should add a quit option to this ^^^^ closing it starts new game
			if (n == 1) { // bot game
				timedGame = false;
				botEnabled = true;
			} else if (n == 2) { // timed game
				timedGame = true;
				botEnabled = false;
			} else if (n == 3) { // untimed game
				timedGame = false;
				botEnabled = false;
			} else { // exit
				System.exit(0);
			}
		} else {
			Object[] options = { "Exit", "Timed game", "Untimed game" };
			int n = JOptionPane.showOptionDialog(null, //parent container of JOptionPane
					"Welcome to Sum Fun!\nWhat type of game do you want to play?", "Select Game",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

			// we should add a quit option to this ^^^^ closing it starts new game
			if (n == 1) { // timed game
				timedGame = true;
			} else if (n == 2) { // untimed game
				timedGame = false;
			} else { // exit
				System.exit(0);
			}
		}

	}

	public boolean getStop() {
		return stop;
	}

	public void move(int row, int col) {
		grid.move(row, col);
	}

	public int getMaxMoves() {
		return maxMoves;
	}

	public boolean getTimedGame() {
		return timedGame;
	}

}
