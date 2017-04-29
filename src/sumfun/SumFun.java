package sumfun;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.Timer;

import controller.CountdownController;
import model.BotModel;
import model.GridModel;
import model.QueueModel;
import view.HighScoreView;
import view.StartView;
import view.TimedHighScoreView;
import view.WindowView;

// Sum Fun Game

public class SumFun {

	public GridModel grid;
	public QueueModel queue;
	public BotModel bot;
	public WindowView mainView;
	private StartView greetingDialog;
	private final int maxMoves = 30;
	private boolean timedGame = false; // flag for timed or untimed game
	private boolean botEnabled = false; // flag for if bot is enabled
	private boolean stop = false; // flag to continue or stop game
	private String name; // player name
	private HighScoreView hsView;
	private TimedHighScoreView timedHSView;

	public static void main(String[] args) {
		SumFun game = new SumFun();
		game.run(game);
	}

	public void run(SumFun game) {
		queue = new QueueModel(game);
		hsView = new HighScoreView();
		timedHSView = new TimedHighScoreView();
		grid = GridModel.getInstance(game, queue, hsView, timedHSView);
		mainView = new WindowView(game, grid, queue, timedGame); // main game frame

		//generateCursor();
		greetingDialog = new StartView(mainView, hsView, timedHSView);
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

	public void setStop(boolean isStop) {
		stop = isStop;
	}

	private void generateCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("resources/cursor.png");
		Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "img");
		mainView.setCursor(c);
	}

	public CountdownController getCountdown() {
		return mainView.getCountdown();
	}

	public Timer getTimerInstance() {
		return mainView.getTimer();
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
