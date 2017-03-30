package main.View;

import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import main.Model.TileModel;
import main.sumfun.Window;
public class GameView extends JPanel implements Observer {

	private BoardView board;
	private InfoView info;
	private QueueView queue;
	private TileModel[][] grid;
	private Window main;

	/**
	 * This is the constructor for the main game panes. It handles all mouse
	 * motion and mouse clicks.
	 */
	public class GameView extends JPanel implements Observer {

		private BoardView board;
		private InfoView info;
		private QueueView queue;
		private TileModel[][] grid;
		private Window main;

		/**
		 * This is the constructor for the main game panes. It handles all mouse
		 * motion and mouse clicks.
		 */
		public GameView() {
			setFocusable(true);
			setLayout(new BoxLayout(this, 0));

			Box left = Box.createVerticalBox(); // holds board
			Box right = Box.createVerticalBox(); // holds info and queue
			Box container = Box.createHorizontalBox(); // holds left and right

			Box right_top = Box.createHorizontalBox(); // holds game info
			Box right_bottom = Box.createHorizontalBox(); // holds queue

			//GameModel model = new GameModel();

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
					//grid[row][col].addActionListener(new GridListener(grid, queue, innerRow, innerCol, main));
				}
			}

		}

		public QueueView getQueue() {
			return queue;
		}

		@Override
		public void update(Observable o, Object arg) {
			// TODO Auto-generated method stub

		}

	}

}