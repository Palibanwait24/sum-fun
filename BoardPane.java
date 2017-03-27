package sumfun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardPane extends JPanel {

	private final int d = 9; // dimension of game board
	private final Tile[][] grid; // grid of tiles

	public BoardPane() {
		grid = new Tile[d][d];

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid.length; col++) {
				gbc.gridy = row;
				gbc.gridx = col;
				if (row == 0 || row == grid.length - 1) {
					Tile c = new Tile(false);
					grid[row][col] = c;
					add(c, gbc);
				} else if (col == 0 || col == grid.length - 1) {
					Tile c = new Tile(false);
					grid[row][col] = c;
					add(c, gbc);
				} else {
					Tile c = new Tile(true);
					grid[row][col] = c;
					add(c, gbc);
				}

			}

		}

		

	}
	public int getDimension(){
		return d;
	}
	public Tile[][] getGrid(){
		return grid;
	}

}