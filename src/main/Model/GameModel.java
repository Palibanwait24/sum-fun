package main.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import main.Model.TileModel;
import main.View.QueueView;
import main.View.WindowView;

public class GameModel extends Observable {

	private final TileModel[][] grid; // game board
	private final int d = 9; // dimension of game board

	public GameModel() {
		grid = new TileModel[d][d];

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid.length; col++) {
				if (row == 0 || row == grid.length - 1) {
					TileModel tile = new TileModel(false);
					grid[row][col] = tile;
				} else if (col == 0 || col == grid.length - 1) {
					TileModel tile = new TileModel(false);
					grid[row][col] = tile;
				} else {
					TileModel tile = new TileModel(true);
					grid[row][col] = tile;
				}

			}

		}
	}

	public void startGame() {
		setChanged();
		notifyObservers();
	}

	public TileModel[][] getGrid() {
		return grid;
	}

	public void move() {

		// set stuff

		setChanged();
		notifyObservers();
	}

}

class GridController implements ActionListener {

	private QueueView queue;
	private TileModel[][] grid;
	private int row;
	private int col;
	private WindowView parent;

	public GridController(TileModel[][] grid, QueueView queue, int innerRow, int innerCol, WindowView w) {
		this.grid = grid;
		this.queue = queue;
		row = innerRow;
		col = innerCol;
		parent = w;
	}

	public void actionPerformed(ActionEvent click) {
		// *NEED* add number from top of queue to mySum

		if (grid[row][col].getData().equals("")) {

			int mySum = tileSum(row, col);// find out the sum using valid move

			if (mySum != 0) {

				int modded = mySum % 10; // gets the modded value
				if (modded == queue.getQueueValue()) {
					// erase surrounding box texts and remove queue[0]
					erase(row, col);
					queue.remove();

					// calculate points here!!!!!
					// calculate points here!!!!!
					// calculate points here!!!!!

				} else {
					// update queue and set queue[0] number to current location
					grid[row][col].setDataString(queue.remove());

				}
			}

		} else {
			System.out.println("not valid");
			// parent = (Window) SwingUtilities.getWindowAncestor(this.);
			// parent.shake(); // invalid move, shake window to notify
		}
	}

	public void erase(int row, int col) {
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				try {
					grid[row + dx][col + dy].setDataString("");
				} catch (ArrayIndexOutOfBoundsException e) {
					// do nothing
				}
			}
		}
	}

	// calculates the sum
	public int tileSum(int innerRow, int innerCol) {
		int sum = 0;
		//check left

		if (innerCol != 0 && (innerRow == 0 || innerRow == 8 || innerCol == 8)) {

			if (!grid[innerRow][innerCol - 1].getData().equals("")) {

				sum += Integer.parseInt(grid[innerRow][innerCol - 1].getData());
			}
		}

		// check right if not out of bounds
		if (innerCol != 8 && (innerRow == 0 || innerRow == 8 || innerCol == 0)) {

			if (!grid[innerRow][innerCol + 1].getData().equals("")) {
				sum += Integer.parseInt(grid[innerRow][innerCol + 1].getData());
			}
		}

		// check up if not out of bounds
		if (innerRow != 0 && (innerCol == 0 || innerCol == 8 || innerRow == 8)) {

			if (!grid[innerRow - 1][innerCol].getData().equals("")) {
				sum += Integer.parseInt(grid[innerRow - 1][innerCol].getData());
			}
		}

		// check down if not out of bounds
		if (innerRow != 8 && (innerCol == 0 || innerCol == 8 || innerRow == 0)) {

			if (!grid[innerRow + 1][innerCol].getData().equals("")) {
				sum += Integer.parseInt(grid[innerRow + 1][innerCol].getData());
			}
		}

		// check diagnal up to the left if not out of bounds
		if (innerRow != 0 && innerCol != 0 && (innerCol == 8 || innerRow == 8)) {

			if (!grid[innerRow - 1][innerCol - 1].getData().equals("")) {
				sum += Integer.parseInt(grid[innerRow - 1][innerCol - 1].getData());
			}
		}

		// check diagnal up to the right if not out of bounds
		if (innerRow != 0 && innerCol != 8 && (innerCol == 0 || innerRow == 8)) {

			if (!grid[innerRow - 1][innerCol + 1].getData().equals("")) {
				sum += Integer.parseInt(grid[innerRow - 1][innerCol + 1].getData());
			}
		}

		// check diagnal down to the left if not out of bounds
		if (innerRow != 8 && innerCol != 0 && (innerCol == 8 || innerRow == 0)) {

			if (!grid[innerRow + 1][innerCol - 1].getData().equals("")) {
				sum += Integer.parseInt(grid[innerRow + 1][innerCol - 1].getData());
			}
		}

		// check diagnal down to the right if not out of bounds
		if (innerRow != 8 && innerCol != 8 && (innerCol == 0 || innerRow == 0)) {

			if (!grid[innerRow + 1][innerCol + 1].getData().equals("")) {
				sum += Integer.parseInt(grid[innerRow + 1][innerCol + 1].getData());
			}
		}

		return sum;
	}

}
