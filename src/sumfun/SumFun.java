package sumfun;

import model.BotModel;
import model.GridModel;
import model.QueueModel;

import view.StartView;
import view.WindowView;

// Sum Fun Game

public class SumFun {

	public GridModel grid;
	public QueueModel queue;
	public BotModel bot;
	public WindowView mainView;
	private StartView greetingDialog;
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
		queue = new QueueModel(game);
		grid = GridModel.getInstance(game, queue);
		mainView = new WindowView(game, grid, queue, timedGame); // main game frame
		greetingDialog = new StartView(mainView);
		greetingDialog.show();
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
		mainView.setVisible(false);
		stop = true;
		grid.setMoves(0);
		grid.setScore(0);
		grid.setValid(true);

		greetingDialog.show();

		stop = false;
		grid.reinitialize();
		queue.reinitialize();
		mainView.resetRefresh();
		mainView.resetHint();
		mainView.resetRemove();
		if (timedGame) {
			mainView.setTimedGame(true);
		} else {
			mainView.removeTimedGame();
		}

		mainView.setVisible(true);
		if (botEnabled) {
			bot.run();
		}
	}

	public void setStop() {
		stop = true;
	}

	public void setBotEnabled(boolean isBotEnabled) {
		botEnabled = isBotEnabled;
	}

	public void setTimedGame(boolean isTimedGame) {
		timedGame = isTimedGame;
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

	public boolean getStop() {
		return stop;
	}

}
