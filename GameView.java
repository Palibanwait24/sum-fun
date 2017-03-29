package sumfun;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameView extends JPanel {

	private BoardView board;
	private InfoView info;
	private QueueView queue;
	private TileModel[][] grid;
	private Window main;

	/**
	 * This is the constructor for the main game panes. It handles all mouse motion and mouse clicks.
	 */
	public GameView() {
		setFocusable(true);
		setLayout(new BoxLayout(this, 0));

		Box left = Box.createVerticalBox(); // holds board
		Box right = Box.createVerticalBox(); // holds info and queue
		Box container = Box.createHorizontalBox(); // holds left and right

		Box right_top = Box.createHorizontalBox(); // holds game info
		Box right_bottom = Box.createHorizontalBox(); // holds queue

		board = new BoardView();
		info = new InfoView();
		queue = new QueueView();
		grid = board.getGrid();
		main = (Window) SwingUtilities.getWindowAncestor(this);

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
				grid[row][col].addActionListener(new GridListener(grid, queue, innerRow, innerCol, main));
			}
		}

	}

	public QueueView getQueue() {
		return queue;
	}

}

class GridListener implements ActionListener {

	private QueueView queue;
	private TileModel[][] grid;
	private int row;
	private int col;
	private Window parent;

	public GridListener(TileModel[][] grid, QueueView queue, int innerRow, int innerCol, Window w) {
		this.grid = grid;
		this.queue = queue;
		row = innerRow;
		col = innerCol;
		parent = w;
	}

	public void actionPerformed(ActionEvent click) {
		// *NEED* add number from top of queue to mySum
		boolean add = false;
		if (grid[row][col].getData().equals("")) {

			boolean[] isValid = tileCheck(row, col);// find out which moves are valid
			int mySum = tileSum(row, col);// find out the sum using valid move
			int modded = mySum % 10; // gets the modded value
			if (modded == queue.getQueueValue()) {
				// erase surrounding box texts and remove queue[0]
				erase(row, col);
				queue.remove();

				// calculate points here!!!!!
				// calculate points here!!!!!
				// calculate points here!!!!!

			} else {
				//update queue and set queue[0] number to current location
				grid[row][col].setDataString(queue.remove());
				System.out.println(grid[row][col].getData());
			}
		} else {
			//parent = (Window) SwingUtilities.getWindowAncestor(this.);
			//parent.shake(); // invalid move, shake window to notify
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

	public boolean[] tileCheck(int row, int col) {
		boolean[] validity = new boolean[8];
		int counter = 0;
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if (dx == 0 && dy == 0) {
					continue;
				}
				try {
					if (!grid[row + dx][col + dy].getData().equals("")) {
						validity[counter] = true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					// do nothing
				}
				counter++;
			}
		}

		return validity;
	}

}
