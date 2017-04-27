package model;

import java.awt.Color;
import java.util.Date;
import java.util.Observable;
import java.util.Random;

import javax.swing.JOptionPane;

import controller.CountdownController;
import controller.SoundController;
import model.QueueModel;

import sumfun.SumFun;

import view.HighScoreView;
import view.NameView;
import view.TimedHighScoreView;
import view.WindowView;

public class GridModel extends Observable {

	private static GridModel gridModel;

	private SumFun game; // reference to main game
	private QueueModel queueModel; // reference to game queue
	private TileModel[][] grid; // game board
	private final int dimension = 3; // dimension of game board
	private int moves; // current number of moves on board
	private int score; // score for current game
	private boolean valid; // flag to show if move is valid
	private boolean win; // flag to show if game is over
	private boolean gameLost = false;
	private boolean stopJoptionPaneBot = true;
	private String name;
	private HighScoreView hsView;
	private TimedHighScoreView TimedHSView;
	private SoundController sound;

	private GridModel(SumFun game, QueueModel queue, HighScoreView hsView, TimedHighScoreView timedHSView) {
		this.game = game;
		this.queueModel = queue;
		
		grid = new TileModel[dimension][dimension];
		moves = 0;
		score = 0;
		valid = true;
		win = false;
		boolean fill = false;
		name = null;
		this.hsView = hsView;
		this.TimedHSView=timedHSView;
		sound = new SoundController();

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				if (row == 0 || row == grid.length - 1) {
					fill = false;
				} else if (col == 0 || col == grid.length - 1) {
					fill = false;
				} else {
					fill = true;
				}
				TileModel tile = new TileModel(fill);
				grid[row][col] = tile;
			}
		}
	}

	public void start() { // initializes grid
		setChanged();
		notifyObservers();
	}

	public static GridModel getInstance(SumFun game, QueueModel queue, HighScoreView hsView, TimedHighScoreView TimedHSView) {
		if (gridModel == null) {
			gridModel = new GridModel(game, queue, hsView,TimedHSView);
		}
		return gridModel;
	}

	public void reinitialize() {
		name = null;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (i == 0 || i == grid.length - 1) {
					grid[i][j].setData("");
					continue;
				} else if (j == 0 || j == grid.length - 1) {
					grid[i][j].setData("");
					continue;
				}
				Random rand = new Random();
				int n = rand.nextInt(10);
				grid[i][j].setData(n);/////asfd
			}
		}
		setChanged();
		notifyObservers();
	}
	public CountdownController getCountdown(){
		return game.getCountdown();
	}
	// perform move for user
	public void move(int row, int col) {
		if (moves > game.getMaxMoves() - 1) {
			gameLost = true;
			game.setStop(true);
			setChanged();
			notifyObservers();
			return; // out of moves
		}
		valid = true; // reset valid flag

		if (grid[row][col].isEmpty()) {
			
			int sum = tileSum(row, col); // determine sum of tile and its neighbors

			int tileToAdd = queueModel.updateQueue();
			grid[row][col].setData(tileToAdd);

			if (sum == -1) {
				sum = 0;
			}

			int mod = sum % 10; // calculates remainder using sum modulus 10

			sound.chimeRemoveTile();
			if (mod == tileToAdd) {
				int tilesRemoved = erase(row, col); // erase surrounding tiles
				if (tilesRemoved >= 3) {
					score += (tilesRemoved * 10);
				}
			}

			moves++;
		} else {
			valid = false; // tile already has number, invalid move
		}
		setChanged();
		notifyObservers();
		// valid = true; // reset valid flag
		win = checkWin(); // check if game is over
		if (win) {
			int time=999;
			try{
			game.getTimerInstance().stop();
			time = getCountdown().getTime()+1;// add 1 second for lag
			}catch(Exception ex){}
			
			game.setStop(true);
			name = promptName();
			gameLost = false;
			if(game.getTimedGame() == false){
			
				OverallHighScoreModel m1 = new OverallHighScoreModel(name, new Date(), score);
				hsView.addScore(m1);
			}
			else{
				TimedHighScoreModel timed1 = new TimedHighScoreModel(name, new Date(), time);
				TimedHSView.addScore(timed1);

			}

			setChanged();
			notifyObservers();
			return;
		}

	}

	// perform move for bot
	protected void moveBot() {
		if (moves > game.getMaxMoves() - 1 && stopJoptionPaneBot) {
			gameLost = true;
			game.setStop(true);
			stopJoptionPaneBot = false;
			return; // out of moves
		}
		valid = true; // reset valid flag

		int[][] gridSums = getGridSums();
		if (stopJoptionPaneBot == true) {
			int[][] neighborsRemoved = getNeighborsRemoved(gridSums, queueModel.getTopOfQueue());

			int max = 0;
			int maxRow = 0;
			int maxCol = 0;
			boolean selection = false;

			for (int row = 0; row < neighborsRemoved.length; row++) {
				for (int col = 0; col < neighborsRemoved[row].length; col++) {
					if (neighborsRemoved[row][col] > max) {
						max = neighborsRemoved[row][col];
						maxRow = row;
						maxCol = col;
						selection = true;
					}
				}
			}

			int tileToAdd;
			if (selection) {
				tileToAdd = queueModel.updateQueue();
				grid[maxRow][maxCol].setData(tileToAdd);
				int tilesRemoved = erase(maxRow, maxCol); // erase surrounding tiles
				if (tilesRemoved >= 3) {
					score += (tilesRemoved * 10);
				}
				moves++;
			} else {
				boolean breakPoint = true;

				for (int row = 0; row < neighborsRemoved.length; row++) {
					for (int col = 0; col < neighborsRemoved[row].length; col++) {
						if (!grid[row][col].isEmpty()) {
							continue;
						}

						for (int dx = -1; dx <= 1; dx++) {
							for (int dy = -1; dy <= 1; dy++) {
								try {
									if (dx == 0 && dy == 0) {
										continue;
									}

									if (!grid[row + dx][col + dy].isEmpty()) {
										if (breakPoint) {
											tileToAdd = queueModel.updateQueue();
											grid[row][col].setData(tileToAdd);
											breakPoint = false;
											moves++;
										}
									}
								} catch (ArrayIndexOutOfBoundsException ex) {
									// tile is not on board, do nothing
								}
							}
						}
					}

				}

			}
		}

		setChanged();
		notifyObservers();
		// valid = true; // reset valid flag
		win = checkWin(); // check if game is over

		if (win && stopJoptionPaneBot) {
			gameLost = false;
			JOptionPane.showMessageDialog(null, "Game is over! Nice job!");
			game.setStop(true);
			stopJoptionPaneBot = false;
			OverallHighScoreModel m1 = new OverallHighScoreModel(name, new Date(), score);
			hsView.addScore(m1);
			return;
		}

	}

	// returns an array of the sum of each tile on the board based on its current neighbors
	public int[][] getGridSums() {
		int[][] temp = new int[dimension][dimension];

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				if (grid[row][col].isEmpty()) {
					int sum = tileSum(row, col); // determine sum of tile and its neighbors
					temp[row][col] = sum;
				} else {
					temp[row][col] = -1;
				}
			}
		}

		return temp;
	}

	// returns an array of the number of neighbors that could possibly be removed from a given tile
	public int[][] getNeighborsRemoved(int[][] gridSums, int tileToAdd) {
		int[][] temp = new int[dimension][dimension];
		int count = 0; // number of neighbors for given tile

		for (int row = 0; row < gridSums.length; row++) {
			for (int col = 0; col < gridSums[row].length; col++) {
				if (gridSums[row][col] == -1) {
					continue; // not a valid move, continue
				}
				count = 0;
				int mod = gridSums[row][col] % 10;
				if (mod == tileToAdd) {
					for (int dx = -1; dx <= 1; dx++) {
						for (int dy = -1; dy <= 1; dy++) {
							try {
								if (!grid[row + dx][col + dy].isEmpty()) {
									count++;
								}
							} catch (ArrayIndexOutOfBoundsException ex) {
								// no tile, do nothing
							}
						}
					}
				}
				temp[row][col] = count;
			}
		}

		return temp;
	}

	// iterate thru board to determine if game is over
	protected boolean checkWin() {
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid[row].length; col++) {
				try {
					if (grid[row][col].isEmpty()) {
						continue; // tile is empty, continue to next tile
					} else {
						return false; // tile is not empty, game is not over
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					// tile is not on board, do nothing
				}
			}
		}

		
		return true; // all tiles are empty, game is over
	}

	private String promptName() {
		NameView getTheName = new NameView();
		name = getTheName.getName();
		return name;
	}


	// calculates sum of surrounding tiles
	protected int tileSum(int row, int col) {
		int sum = -1;
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				try {
					if (dx == 0 && dy == 0) {
						continue;
					}
					if (!grid[row + dx][col + dy].isEmpty()) {
						if (sum == -1) {
							sum++;
						}
						sum += Integer.parseInt(grid[row + dx][col + dy].getData());
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					// tile is not on board, do nothing
				}
			}
		}
		return sum;
	}

	protected int erase(int row, int col) {
		int c = 0; // number of tiles removed from move

		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				try {
					if (grid[row + dx][col + dy].isEmpty()) { // empty tile, do nothing
						continue;
					} else if (dx == 0 && dy == 0) { // current tile, remove but do not count as removed tile
						String temp = grid[row + dx][col + dy].getData();
						try {
							grid[row + dx][col + dy].setData(temp);
						} catch (Exception ex) {
							System.out.println("Error occured in GridModel.erase()");
						}
						grid[row + dx][col + dy].setData("");
						continue;
					}
					grid[row + dx][col + dy].setData("");
					c++;

				} catch (ArrayIndexOutOfBoundsException ex) {
					// no tile, do nothing
				}
			}
		}

		return c;
	}

	public void highlightTile(int row, int col) {
		grid[row][col].setHighlight(Color.GREEN);
		setChanged();
		notifyObservers();
		grid[row][col].setHighlight(Color.WHITE);
	}

	public TileModel[][] getGrid() {
		return grid;
	}

	public int getScore() {
		return score;
	}

	public int getMovesTaken() {
		return moves;
	}

	public boolean getValid() {
		return valid;
	}

	public boolean getWin() {
		return win;
	}

	public boolean getGameLost() {
		return gameLost;
	}

	public void setGameLost(boolean answer) {
		gameLost = answer;
	}

	public void setValid(boolean bool) {
		valid = bool;
	}

	public void setMoves(int number) {
		moves = number;
	}

	public void setGame(SumFun newGame) {
		game = newGame;
	}

	public void setQueue(QueueModel newQueue) {
		queueModel = newQueue;
	}

	public void setName(String name) {
		this.name = name; 
		
	}

	public void setScore(int s) {
		this.score = s;
	}
}
