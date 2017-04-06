package Model;

import java.util.*;

import sumfun.SumFun;
import Model.QueueModel;

public class GridModel extends Observable {

	private SumFun game; // reference to main game... controller?
	private TileModel[][] grid; // game board
	private final int d = 9; // dimension of game board
	private static int moves; // current number of moves on board
	private static int score; // score for current game
	private static boolean valid; // flag to show if move is valid or not

	public GridModel(SumFun game) {
		this.game = game;
		grid = new TileModel[d][d];
		moves = 0;
		score = 0;
		valid = true;
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
			return; // out of moves
		}

		if (grid[row][col].isEmpty()) {
			int sum = tileSum(row, col); // determine sum of tile and its neighbors

			int tileToAdd = QueueModel.getQueueModel().updateQueue();
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
			valid = false;
		}

		setChanged();
		notifyObservers();
		valid = true;
		// check win?
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
					if (!grid[row + dx][col + dy].getData().equals("")) {
						sum += Integer.parseInt(grid[row + dx][col + dy].getData());
					}
				} catch (ArrayIndexOutOfBoundsException e) {
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
					if (grid[row + dx][col + dy].getData().equals("")) { // empty tile, do nothing
						continue;
					} else if (dx == 0 && dy == 0) { // current tile, remove but do not count as removed tile
						grid[row + dx][col + dy].setData("");
						continue;
					}
					grid[row + dx][col + dy].setData("");
					c++;
				} catch (ArrayIndexOutOfBoundsException e) {
					// no tile, do nothing
				}
			}
		}

		return c;
	}

	public TileModel[][] getGrid() {
		return grid;
	}

	public static int getScore() {
		return score;
	}

	public static int getMovesTaken() {
		return moves;
	}

	public static boolean getValid() {
		return valid;
	}
}
