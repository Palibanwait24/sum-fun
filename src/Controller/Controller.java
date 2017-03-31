package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import Model.*;
import View.*;

public class Controller implements ActionListener {

	private TileModel[][] grid;
	private TileModel[] queue;
	private int row;
	private int col;
	private WindowView parent ;

	public Controller(int innerRow, int innerCol, TileModel[][] grid, TileModel[] queue, WindowView son) {
		parent = son;
		this.grid = grid;
		this.queue = queue;
		row = innerRow;
		col = innerCol;
	}

	public void actionPerformed(ActionEvent click) {
		// *NEED* add number from top of queue to mySum
		

		if (grid[row][col].getData().equals("") && parent.getMoveRem()>0) {
			parent.setMoves_rem(parent.getMoveRem()-1);

			int mySum = tileSum(row, col);// find out the sum using valid move

			if (mySum != 0) {

				int modded = mySum % 10; // gets the modded value

				//grid[row][col].setDataString("99");

				if (modded == getTopQueueValue()) {
					erase(row, col); // erase surrounding box texts and remove queue[0]
					
					updateQueue();
					//queue.remove();

					// calculate points here!!!!!
					// calculate points here!!!!!
					// calculate points here!!!!!

				} else {
					// update queue and set queue[0] number to current location
					grid[row][col].setDataString(getTopQueueValue() + "");
					updateQueue();
				}

			}

		} else {

			 ////parent.SwingUtilities.getWindowAncestor(this.);
			 parent.shake(); // invalid move, shake window to notify
		}
	}

	public void updateQueue() {

        for (int i = 0; i < queue.length - 1; i++) {
            queue[i].setDataString(queue[i + 1].getData());
        }
        if(parent.getMoveRem()>=5) {
            Random r = new Random();
            queue[queue.length - 1].setDataInteger(r.nextInt(9) + 1);
        }
		else{
            queue[4].setDataString("");
        }
	}

	public int getTopQueueValue() {
		return Integer.parseInt(queue[0].getData());
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
	public int tileSum(int row, int col) {
		int sum = 0;
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				try {
					if (!grid[row + dx][col + dy].getData().equals("")) {
						sum += Integer.parseInt(grid[row + dx][col + dy].getData());
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					// do nothing
				}
			}
		}

		return sum;
	}

}

