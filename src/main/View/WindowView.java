package main.View;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class WindowView extends JFrame implements Observer {

	// utilities to size window
	private final int WINDOW_WIDTH = 680;
	private final int WINDOW_HEIGHT = 720;
	private final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int LOCATION_X = screensize.height / 3;
	private final int LOCATION_Y = (int) (screensize.width - (screensize.width * 0.97));

	// data members
	private BoardView board;
	private QueueView queue;
	private InfoView info;
	private final JMenuBar menu; // menu for options and operations

	/**
	 * Constructor for a Window object.
	 */
	public WindowView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(LOCATION_X, LOCATION_Y);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(true);
		setTitle("Sum Fun");

		board = new BoardView();
		queue = new QueueView();
		info = new InfoView();
		menu = createMenu();
		setJMenuBar(menu);

		JPanel gameView = buildGameView();
		
		/*
		int dimension = board.getDimension();
		for (int row = 0; row < dimension; row++) {
			for (int col = 0; col < dimension; col++) {
				final Integer innerRow = new Integer(row);
				final Integer innerCol = new Integer(col);
				//grid[row][col].addActionListener(new GridListener(grid, queue, innerRow, innerCol, main));
			}
		}
		*/

		pack();
	}

	@Override
	public void update(Observable o, Object arg) {
		//GameModel game = (GameModel) o;
		//TileModel tile = (TileModel) o;
	}

	public void addObserver(Observable model) {
		model.addObserver(this);
	}

	private JPanel buildGameView() {
		JPanel view = new JPanel();

		view.setLayout(new BoxLayout(this, 0));
		view.setFocusable(true);

		Box left = Box.createVerticalBox(); // holds board
		Box right = Box.createVerticalBox(); // holds info and queue
		Box container = Box.createHorizontalBox(); // holds left and right

		Box right_top = Box.createHorizontalBox(); // holds game info
		Box right_bottom = Box.createHorizontalBox(); // holds queue

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

		return view;
	}

	private JMenuBar createMenu() {
		JMenuBar temp = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem n = new JMenuItem("New game"); // start new game
		n.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				gamePane.setVisible(false);
				remove(gamePane);
				gamePane = new GameView();
				add(gamePane);
				gamePane.setFocusable(true);
				*/
			}
		});
		fileMenu.add(n);
		JMenuItem e = new JMenuItem("Exit"); // exit game
		e.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				int option = JOptionPane.showConfirmDialog((Component) getParent(), "Are you sure you want to exit Sum Fun?",
						"Confirm Exit", 0);
				if (option == 0) {
					System.exit(0);
				} else {
					return;
				}
				*/
				System.exit(0); // use for development, remove later
			}
		});
		fileMenu.add(e);
		temp.add(fileMenu);

		JMenu optionMenu = new JMenu("Option");
		JMenuItem GameViewHighScores = new JMenuItem("View high scores"); // GameView high scores in game
		GameViewHighScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// show hi scores
			}
		});
		optionMenu.add(GameViewHighScores);
		temp.add(optionMenu);

		return temp;
	}

	public void shake() {
		final int length = 8;
		final int ox = getLocationOnScreen().x; // original x position
		final int oy = getLocationOnScreen().y; // original y position

		int offset = ox;
		try {
			for (int i = 0; i < length; i++) {
				if (i % 2 == 0) {
					offset = ox + 5;
				} else {
					offset = ox - 5;
				}
				setLocation(offset, oy);
				Thread.sleep(10);
			}
		} catch (Exception ex) {
			System.out.println("Error occured.");
			ex.printStackTrace();
		}
	}
}
