package main.View;

import java.util.*;
import javax.swing.*;

import main.Model.TileModel;

import java.awt.*;

public class BoardView extends JPanel implements Observer {

	private final int d = 9; // dimension of game board
	private final TileModel[][] grid; // grid of TileModels

	public BoardView() {
		grid = new TileModel[d][d];

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid.length; col++) {
				gbc.gridy = row;
				gbc.gridx = col;
				if (row == 0 || row == grid.length - 1) {
					TileModel c = new TileModel(false);
					grid[row][col] = c;
					add(c, gbc);
				} else if (col == 0 || col == grid.length - 1) {
					TileModel c = new TileModel(false);
					grid[row][col] = c;
					add(c, gbc);
				} else {
					TileModel c = new TileModel(true);
					grid[row][col] = c;
					add(c, gbc);
				}

			}

		}

	}
	public int getDimension(){
		return d;
	}
	public TileModel[][] getGrid(){
		return grid;
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}