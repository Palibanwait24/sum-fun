package View;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Model.TileModel;

public class WindowView extends JFrame implements Observer {

	// utilities to size window
	private final int WINDOW_WIDTH = 680;
	private final int WINDOW_HEIGHT = 720;
	private final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int LOCATION_X = screensize.height / 3;
	private final int LOCATION_Y = (int) (screensize.width - (screensize.width * 0.97));

	// data members
	private JPanel gridView;
	private JPanel queueView;
	private JPanel infoView;
	private final JMenuBar menu; // menu for options and operations

	private final int d = 9; // dimension of game board
	private TileModel[][] grid; // grid of TileModels is the game board

	private TileModel[] queue; // queue of TileModels

	/**
	 * Constructor for a Window object.
	 */
	public WindowView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(LOCATION_X, LOCATION_Y);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(true);
		setTitle("Sum Fun");

		gridView = new JPanel();
		buildGridView();
		queueView = new JPanel();
		buildQueueView();
		infoView = new JPanel();
		buildInfoView();
		menu = createMenu();
		setJMenuBar(menu);
		
		JPanel view = new JPanel(); // from here on is fucked up!

		view.setLayout(new BoxLayout(this, 0));
		view.setFocusable(true);

		Box left = Box.createVerticalBox(); // holds board
		Box right = Box.createVerticalBox(); // holds info and queue
		Box container = Box.createHorizontalBox(); // holds left and right

		Box right_top = Box.createHorizontalBox(); // holds game info
		Box right_bottom = Box.createHorizontalBox(); // holds queue

		// build left side
		left.add(gridView);

		// build right side
		right_top.add(infoView);
		right_bottom.add(queueView);

		right.add(right_top);
		right.add(right_bottom);

		// build window
		container.add(left);
		container.add(right);
		add(container);

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
		if (o.getClass().getName().equals("GridModel")) {

		} else if (o.getClass().getName().equals("QueueModel")) {

		} else {
			System.out.println("Error occured in WindowView.update().");
		}

	}

	public void addObserver(Observable model) {
		model.addObserver(this);
	}

	private void buildGridView() {
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

	private void buildQueueView() {
		queue = new TileModel[5];

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		for (int i = 0; i < queue.length; i++) {
			gbc.gridy = i;
			TileModel temp = new TileModel(true);
			add(temp, gbc);
			queue[i] = temp;
		}
	}

	private void buildInfoView() {

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
		left.add(gridView);

		// build right side
		right_top.add(infoView);
		right_bottom.add(queueView);

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
