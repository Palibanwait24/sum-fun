package Model;

import java.util.*;

import javax.swing.JOptionPane;

import sumfun.SumFun;
import Model.QueueModel;

public class GridModel extends Observable {

	private SumFun game; // reference to main game... controller?
	private QueueModel queueModel; // reference to game queue
	private TileModel[][] grid; // game board
	private final int d = 9; // dimension of game board
	private int moves; // current number of moves on board
	private int score; // score for current game
	private boolean valid; // flag to show if move is valid
	private boolean win; // flag to show if game is over

	public GridModel(SumFun game, QueueModel queue) {
		this.game = game;
		this.queueModel = queue;
		grid = new TileModel[d][d];
		moves = 0;
		score = 0;
		valid = true;
		win = false;
		boolean fill = false;

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

	public void move(int row, int col) {
		if (moves > game.getMaxMoves() - 1) {
			// we should move this pop-up to view.... just do max calculation there with getter
			JOptionPane.showMessageDialog(null, "You are out of moves! Please start a new game.");
			return; // out of moves
		}
		valid = true; // reset valid flag

		if (grid[row][col].isEmpty()) {
			int sum = tileSum(row, col); // determine sum of tile and its neighbors

			int tileToAdd = queueModel.updateQueue();
			grid[row][col].setData(tileToAdd);

			if (sum != 0) {
				int mod = sum % 10; // calculates remainder using sum modulus 10

				if (mod == tileToAdd) {
					int tilesRemoved = erase(row, col); // erase surrounding tiles
					if (tilesRemoved >= 3) {
						score += (tilesRemoved * 10);
					}
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
			// should move this to view too, i think
			JOptionPane.showMessageDialog(null, "Game is over! Nice job!");
			// not sure what else
		}
	}

	// iterate thru board to determine if game is over
	private boolean checkWin() {
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

	// calculates sum of surrounding tiles
	private int tileSum(int row, int col) {
		int sum = 0;
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				try {
					if (dx == 0 && dy == 0) {
						continue;
					}
					if (!grid[row + dx][col + dy].isEmpty()) {
						sum += Integer.parseInt(grid[row + dx][col + dy].getData());
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					// tile is not on board, do nothing
				}
			}
		}
		return sum;
	}

	private int erase(int row, int col) {
		int c = 0; // number of tiles removed from move

		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				try {
					if (grid[row + dx][col + dy].isEmpty()) { // empty tile, do nothing
						continue;
					} else if (dx == 0 && dy == 0) { // current tile, remove but do not count as removed tile
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
}
