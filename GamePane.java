package sumfun;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.AlreadyBoundException;

import javax.swing.*;
import java.awt.*;

public class GamePane extends JPanel {

	private BoardPane board;
	private InfoPane info;
	private QueuePane queue;
	private Tile[][] grid;

	/**
	 * check This is the constructor for the main game panel. It handles all
	 * mouse motion and mouse clicks.
	 */
	public GamePane() {
		setFocusable(true);
		setLayout(new BoxLayout(this, 0));

		Box left = Box.createVerticalBox(); // holds board
		Box right = Box.createVerticalBox(); // holds info and queue
		Box container = Box.createHorizontalBox(); // holds left and right
													// panels

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
						boolean add = false;
						if (grid[innerRow][innerCol].getData().equals("")) {

							boolean[] isValid = tileCheck(innerRow, innerCol);// find out which moves are valid
							int mySum = tileSum(innerRow, innerCol);// find out the sum using valid move
							int modded = mySum % 10; // gets the modded value
							if (modded == queue.getQueueValue()) {
								// erase surrounding box texts and remove queue[0]
								erase(innerRow,innerCol);
								queue.remove();
								
								// calculate points here!!!!!
								// calculate points here!!!!!
								// calculate points here!!!!!
								
							} else {
								//update queue and set queue[0] number to current location
								grid[innerRow][innerCol].setDataString(queue.remove());
								System.out.println(grid[innerRow][innerCol].getData());

							}
						} else {
							shakeInvalidMove();
						}
					}
				});
			}
		}

	}
	public void erase(int row, int col){
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				try {
					grid[row + dx][col + dy].setDataString("");
					
				} catch (ArrayIndexOutOfBoundsException e) {}
			}
		}
	}
	// calculates the sum
	public int tileSum(int innerRow, int innerCol) {
		int sum = 0;
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				
				try {
					if (!grid[innerRow + dx][innerCol + dy].getData().equals("")) {
						sum += Integer.parseInt(grid[innerRow + dx][innerCol + dy].getData());
					}
				} catch (ArrayIndexOutOfBoundsException e) {}

			}
		}

		return sum;
	}

	public boolean[] tileCheck(int innerRow, int innerCol) {
		boolean[] validity = new boolean[8];
		int counter = 0;
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if(dx==0 && dy == 0){
					continue;
				}
				try {
					if (!grid[innerRow + dx][innerCol + dy].getData().equals("")) {
						validity[counter]= true;
					}
				} catch (ArrayIndexOutOfBoundsException e) {}
				counter ++;

			}
		}
		/*
		System.out.println(validity[0] +" "+ validity[1]+" "+ validity[2]);
		System.out.println(validity[3] +" "+ "none" +" "+ validity[4]);
		System.out.println(validity[5] +" "+ validity[6]+" "+ validity[7]);
		*/
		return validity;
	}

	public QueuePane getQueue() {
		return queue;
	}

	private void shakeInvalidMove() {
		Window w = (Window) SwingUtilities.getWindowAncestor(this); // shake frame to signify invalid  move
		w.shake();
	}
}
