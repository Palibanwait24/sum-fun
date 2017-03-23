package sumfun;

import javax.swing.*;

public class GamePane extends JPanel {

	private BoardPane board;
	private InfoPane info;
	private QueuePane queue;

	/**
	 * This is the constructor for the main game panel. It handles all mouse
	 * motion and mouse clicks.
	 */
	public GamePane() {
		setFocusable(true);
		setLayout(new BoxLayout(this, WIDTH));

		Box left = Box.createVerticalBox(); // holds board
		Box right = Box.createVerticalBox(); // holds info and queue
		Box container = Box.createHorizontalBox(); // holds left and right
													// panels

		//right.setLayout(new BoxLayout(this, WIDTH));
		Box right_top = Box.createVerticalBox(); // holds info
		Box right_bottom = Box.createVerticalBox(); // holds queue

		// build left side
		board = new BoardPane();
		left.add(board);

		// build right side
		info = new InfoPane();
		right_top.add(info);
		queue = new QueuePane();
		right_bottom.add(queue);

		right.add(right_top);
		right.add(right_bottom);

		// build window
		container.add(left);
		container.add(right);
		add(container);
	}

}
