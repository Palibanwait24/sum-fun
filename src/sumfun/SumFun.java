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
	private String name; // player name

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
		System.out.println(name);
	}

	public void startNewGame() {
		stop = true;
		SumFun newGame = new SumFun();
		newGame.run(newGame);
	}

	private void selectGame() {
		boolean dev = true; // turn off later before we turn in
		if (dev) {
			Object[] options = { "Exit", "Watch bot", "Timed game", "Untimed game" };
			int n = JOptionPane.showOptionDialog(null, //parent container of JOptionPane
					"Welcome to Sum Fun!\nWhat type of game do you want to play?", "Select Game",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[3]);

			if (n == 1) { // bot game
				timedGame = false;
				botEnabled = true;
				name = "bot";
			} else if (n == 2) { // timed game
				timedGame = true;
				botEnabled = false;
				promptForName();
			} else if (n == 3) { // untimed game
				timedGame = false;
				botEnabled = false;
				promptForName();
			} else { // exit
				System.exit(0);
			}

		} else {
			Object[] options = { "Exit", "Timed game", "Untimed game" };
			int n = JOptionPane.showOptionDialog(null, //parent container of JOptionPane
					"Welcome to Sum Fun!\nWhat type of game do you want to play?", "Select Game",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

			if (n == 1) { // timed game
				timedGame = true;
			} else if (n == 2) { // untimed game
				timedGame = false;
			} else { // exit
				System.exit(0);
			}

			promptForName();
		}
	}

	private void promptForName() {
		name = JOptionPane.showInputDialog("Enter player name:");
	}

	public boolean getStop() {
		return stop;
	}

	public void setStop() {
		stop = true;
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

	public String getName() {
		return name;
	}
}
