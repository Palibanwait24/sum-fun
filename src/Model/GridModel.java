package Model;

import java.util.*;

public class GridModel extends Observable {

	private final TileModel[][] grid; // game board
	private final int d = 9; // dimension of game board

	public GridModel() {
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

	public void start() {
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
