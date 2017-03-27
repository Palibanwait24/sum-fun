package sumfun;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

public class GamePane extends JPanel {

	private BoardPane board;
	private InfoPane info;
	private QueuePane queue;
	private Tile[][] grid;

	/**check
	 * This is the constructor for the main game panel. It handles all mouse motion and mouse clicks.
	 */
	public GamePane() {
		setFocusable(true);
		setLayout(new BoxLayout(this, 0));

		Box left = Box.createVerticalBox(); // holds board
		Box right = Box.createVerticalBox(); // holds info and queue
		Box container = Box.createHorizontalBox(); // holds left and right panels

		Box right_top = Box.createHorizontalBox(); // holds game info
		Box right_bottom = Box.createHorizontalBox(); // holds queue

		board = new BoardPane();
		info = new InfoPane();
		queue = new QueuePane();
		grid = board.getGrid();

		// build left side
		left.add(board);

		// build right side
		right_top.add(info);
		right_bottom.add(queue);

		right.add(right_top);
		right.add(right_bottom);

		// build window
		container.add(left);
		container.add(right);
		add(container);

		int dimension = board.getDimension();
		for (int row = 0; row < dimension; row++) {
			for (int col = 0; col < dimension; col++) {
				final Integer innerRow = new Integer(row);
				final Integer innerCol = new Integer(col);
				grid[row][col].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent click) {
						// *NEED* add number from top of queue to mySum
						if (grid[innerRow][innerCol].getData().equals("")) {

							boolean[] isValid = tileCheck(innerRow, innerCol);// find out which moves are valid
							int mySum = tileSum(innerRow, innerCol, isValid);// find out the sum using valid move
							System.out.println("Sum is: " + mySum);// print out
																	// the sum
							int modded = mySum % 10;
							System.out.println("modded = " + modded);
							if (modded == queue.getQueueValue()) {
								System.out.println("this is mod 10");
							} else {
								System.out.println("this is not mod 10");
								//shakeInvalidMove(); // not needed here, is still valid  move?
								// just get no points
							}
						} else {
							shakeInvalidMove();
						}

						// *NEED* if mod 10 = number from top of queue then
						// remove
						// tiles and compute score accordingly also remove 1
						// move

					}
				});
			}
		}

	}

	// calculates the sum
	public int tileSum(int innerRow, int innerCol, boolean[] validity) {
		
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				// convert below code to this
			}
		}
		
		
		
		int sum = 0;
		// check left if not out of bounds
		if (validity[0]) {
			sum += Integer.parseInt(grid[innerRow][innerCol - 1].getData());
		}
		// check right if not out of bounds
		if (validity[1]) {
			sum += Integer.parseInt(grid[innerRow][innerCol + 1].getData());
		}
		// check up if not out of bounds
		if (validity[2]) {
			sum += Integer.parseInt(grid[innerRow - 1][innerCol].getData());
		}
		// check down if not out of bounds
		if (validity[3]) {
			sum += Integer.parseInt(grid[innerRow + 1][innerCol].getData());
		}
		// check diagonal up to the left if not out of bounds
		if (validity[4]) {
			sum += Integer.parseInt(grid[innerRow - 1][innerCol - 1].getData());
		}
		// check diagonal up to the right if not out of bounds
		if (validity[5]) {
			sum += Integer.parseInt(grid[innerRow - 1][innerCol + 1].getData());
		}
		// check diagonal down to the left if not out of bounds
		if (validity[6]) {
			sum += Integer.parseInt(grid[innerRow + 1][innerCol - 1].getData());
		}
		// check diagonal down to the right if not out of bounds
		if (validity[7]) {
			sum += Integer.parseInt(grid[innerRow + 1][innerCol + 1].getData());
		}
		return sum;
	}

	public boolean[] tileCheck(int innerRow, int innerCol) {
		boolean[] validity = new boolean[8];

		// check left if not out of bounds
		if (innerCol != 0) {
			if (!grid[innerRow][innerCol - 1].getData().equals("")) {
				validity[0] = true;
			}
		}

		// check right if not out of bounds
		if (innerCol != 8) {
			if (!grid[innerRow][innerCol + 1].getData().equals("")) {
				validity[1] = true;
			}
		}

		// check up if not out of bounds
		if (innerRow != 0) {
			if (!grid[innerRow - 1][innerCol].getData().equals("")) {
				validity[2] = true;
			}
		}
		// check down if not out of bounds
		if (innerRow != 8) {
			if (!grid[innerRow + 1][innerCol].getData().equals("")) {
				validity[3] = true;
			}
		}

		// check diagonal up to the left if not out of bounds
		if (innerRow != 0 && innerCol != 0) {
			if (!grid[innerRow - 1][innerCol - 1].getData().equals("")) {
				validity[4] = true;
			}
		}
		// check diagonal up to the right if not out of bounds
		if (innerRow != 0 && innerCol != 8) {
			if (!grid[innerRow - 1][innerCol + 1].getData().equals("")) {
				validity[5] = true;
			}
		}
		// check diagonal down to the left if not out of bounds
		if (innerRow != 8 && innerCol != 0) {
			if (!grid[innerRow + 1][innerCol - 1].getData().equals("")) {
				validity[6] = true;
			}
		}
		// check diagonal down to the right if not out of bounds
		if (innerRow != 8 && innerCol != 8) {
			if (!grid[innerRow + 1][innerCol + 1].getData().equals("")) {
				validity[7] = true;
			}
		}

		return validity;
	}

	public QueuePane getQueue() {
		return queue;
	}

	private void shakeInvalidMove() {
		Window w = (Window) SwingUtilities.getWindowAncestor(this); // shake frame to signify invalid move
		w.shake();
	}
}
