package View;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Controller.Controller;
import Model.*;

public class WindowView extends JFrame implements Observer {

	// utilities to size window
	private final int WINDOW_WIDTH = 680;
	private final int WINDOW_HEIGHT = 720;
	private final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int LOCATION_X = screensize.height / 3;
	private final int LOCATION_Y = (int) (screensize.width - (screensize.width * 0.97));

	// data members
	private JPanel gameView; // game view
	private JPanel gridView;
	private JPanel queueView;
	private JPanel infoView;
	private final JMenuBar menu; // menu for options and operations

	private final int d = 9; // dimension of game board
	private TileModel[][] grid; // grid of TileModels is the game board

	private TileModel[] queue; // queue of TileModels

	private int moves_rem = 50; // moves remaining in game

	/**
	 * Constructor for a Window object.
	 */
	public WindowView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(LOCATION_X, LOCATION_Y);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(true);
		setTitle("Sum Fun");

		gameView = new JPanel();
		gameView.setLayout(new BorderLayout());

		gridView = new JPanel();
		queueView = new JPanel();
		infoView = new JPanel();
		menu = createMenu();
		setJMenuBar(menu);

		grid = new TileModel[d][d];
		queue = new TileModel[5];

		buildGridView();
		buildQueueView();
		buildInfoView();
		//GridController gControler = new GridController(, null, d, d, null);
		gameView.add(gridView, BorderLayout.CENTER);
		gameView.add(queueView, BorderLayout.WEST);
		gameView.add(infoView, BorderLayout.SOUTH);
		add(gameView);

		//pack();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("hey");
		if (o.getClass().getName().equals("Model.GridModel")) {
			System.out.println("hey");
			TileModel[][] temp = ((GridModel) o).getGrid();

			for (int row = 0; row < grid.length; row++) {
				for (int col = 0; col < grid.length; col++) {
					if (!temp[row][col].getData().equals("")) {
						grid[row][col].setDataString(temp[row][col].getData());
					} else {
						grid[row][col].setDataString("");
					}
				}
			}

		} else if (o.getClass().getName().equals("Model.QueueModel")) {
			/*
						setLayout(new GridBagLayout());
						GridBagConstraints gbc = new GridBagConstraints();
						for (int i = 0; i < queue.length; i++) {
							gbc.gridy = i;
							TileModel temp = new TileModel(true);
							add(temp, gbc);
							queue[i] = temp;
						}
						*/
		} else {
			System.out.println("Error occured in WindowView.update().");
		}

	}

	public void addObserver(Observable model) {
		model.addObserver(this);
	}

	private void buildGridView() {
		gridView.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		boolean fill = true;

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid.length; col++) {
				gbc.gridy = row;
				gbc.gridx = col;
				if (row == 0 || row == grid.length - 1) { // do not fill
					fill = false;
				} else if (col == 0 || col == grid.length - 1) { // do not fill
					fill = false;
				} else {
					fill = true;
				}
				TileModel tile = new TileModel(fill);
				tile.addActionListener(new Controller(row, col, grid, queue));
				grid[row][col] = tile;
				gridView.add(tile, gbc);
			}
		}
	}

	public void buildQueueView() {
		queueView.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel label = new JLabel("  Number Queue");
		label.setForeground(Color.BLUE);
		JLabel separ = new JLabel(" ===========");
		queueView.add(label, gbc);
		gbc.gridy = 1;
		queueView.add(separ, gbc);

		for (int i = 0; i < queue.length; i++) {
			gbc.gridy = i + 2;
			TileModel temp = new TileModel(true);
			queueView.add(temp, gbc);
			queue[i] = temp;
		}
		queue[0].setBackground(Color.GREEN);
		queue[0].setOpaque(true);
	}

	public void buildInfoView() {

		// data fields
		int score = 0;
		long time = 0; // later

		// design fields
		JLabel score_label, time_label, moves_label;
		JLabel score_holder, time_holder, moves_holder, empty_holder;

		// construct info pane layout
		infoView.setLayout(new GridLayout(4, 2));

		score_label = new JLabel("  Score: ");
		moves_label = new JLabel("  Moves remaining: ");
		time_label = new JLabel("  Time: ");

		score_holder = new JLabel("" + score);
		moves_holder = new JLabel("" + moves_rem);
		time_holder = new JLabel("--:--");
		empty_holder = new JLabel("");

		//build top pane
		infoView.add(score_label);
		infoView.add(score_holder);
		infoView.add(moves_label);
		infoView.add(moves_holder);
		infoView.add(time_label);
		infoView.add(time_holder);
		infoView.add(empty_holder);
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
