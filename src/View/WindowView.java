package View;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Queue;

import javax.swing.*;

import Controller.*;
import Model.*;
import sumfun.SumFun;

public class WindowView extends JFrame implements Observer {

	// utilities to size window
	private final int WINDOW_WIDTH = 660;
	private final int WINDOW_HEIGHT = 700;
	private final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int LOCATION_X = screensize.height / 3;
	private final int LOCATION_Y = (int) (screensize.width - (screensize.width * 0.97));

	// design members
	private JPanel gameView; // holds all sub-views below
	private JPanel gridView; // view for game board
	private JPanel queueView; // view for game queue
	private JPanel infoView; // view for info/statistics on current game
	private final JMenuBar menu; // menu for options and operations
	private JLabel moves_holder, score_holder;

	// model members
	private GridModel gridModel; // grid model

	// data members
	private TileModel[][] grid; // grid data -> game board
	private TileModel[] queue; // queue data -> game queue

	// statistic members
	private int movesRem = 50; // moves remaining in game
	private boolean usedHint = false; // flag to determine if hint has been used

	/**
	 * Constructor for a Window object.
	 */
	public WindowView(GridModel g, QueueModel q) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(LOCATION_X, LOCATION_Y);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		setTitle("Sum Fun");

		gameView = new JPanel();
		gameView.setLayout(new BorderLayout());

		gridView = new JPanel();
		queueView = new JPanel();
		infoView = new JPanel();

		gridModel = g;
		grid = gridModel.getGrid();
		queue = new TileModel[5];

		buildGridView();
		buildQueueView();
		buildInfoView();
		menu = createMenu();
		setJMenuBar(menu);
		gameView.add(gridView, BorderLayout.CENTER);
		gameView.add(queueView, BorderLayout.WEST);
		gameView.add(infoView, BorderLayout.SOUTH);
		add(gameView);

		//pack();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o.getClass().getName().equals("Model.GridModel")) {
			// process grid update
			TileModel[][] temp = ((GridModel) o).getGrid();

			if (!GridModel.getValid()) {
				shake();
			}
			for (int r = 0; r < temp.length; r++) {
				for (int c = 0; c < temp[r].length; c++) {
					if (temp[r][c].isEmpty()) {
						grid[r][c].setData("");
					} else {
						grid[r][c].setData(temp[r][c].getData());
					}
				}
			}
			score_holder.setText("" + GridModel.getScore());
			moves_holder.setText("" + (SumFun.maxMoves - GridModel.getMovesTaken()));
		} else if (o.getClass().getName().equals("Model.QueueModel")) {
			// process queue update
			Queue<Integer> temp = ((QueueModel) o).getQueue();
			int i = 0;

			for (Integer item : temp) {
				queue[i].setData(item.toString());
				i++;
			}

		} else {
			System.out.println("Error occured in WindowView.update()");
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
				if (row == 0 || row == grid.length - 1) { // top/bottom row, do not fill
					fill = false;
				} else if (col == 0 || col == grid.length - 1) { // left/right column, do not fill
					fill = false;
				} else {
					fill = true;
				}
				TileModel tile = new TileModel(fill);
				tile.addActionListener(new GridController(row, col));
				grid[row][col] = tile;
				gridView.add(tile, gbc);
			}
		}
	}

	public void buildQueueView() {
		queueView.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JLabel label = new JLabel("  Queue");
		label.setForeground(Color.BLACK);
		JLabel separ = new JLabel("  =======");
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
		//
	}

	private void buildInfoView() {

		// data fields
		int score = 0;
		long time = 0; // later

		// design fields
		JLabel score_label, time_label, moves_label;
		JLabel time_holder, empty_holder;

		// construct info pane layout
		infoView.setLayout(new GridLayout(4, 2));

		score_label = new JLabel("  Score: ");
		moves_label = new JLabel("  Moves remaining: ");
		time_label = new JLabel("  Time: ");

		score_holder = new JLabel("" + score);
		moves_holder = new JLabel("" + movesRem);
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
		JMenuItem newGame = new JMenuItem("New game");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// start new game
			}
		});
		fileMenu.add(newGame);
		JMenuItem save = new JMenuItem("Save game");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// save game
			}
		});
		fileMenu.add(save);
		JMenuItem load = new JMenuItem("Load game");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// load game
			}
		});
		fileMenu.add(load);
		JMenuItem exit = new JMenuItem("Exit"); // exit game
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				int option = JOptionPane.showConfirmDialog((Component) getParent(),
						"Are you sure you want to exit Sum Fun?\nAll unsaved progress will be lost.", "Confirm Exit", 0);
				if (option == 0) {
					System.exit(0);
				} else {
					return;
				}
				*/
				System.exit(0); // use for development, remove later
			}
		});
		fileMenu.add(exit);
		temp.add(fileMenu);

		JMenu viewMenu = new JMenu("View");
		JMenuItem highScores = new JMenuItem("High scores"); // view local high scores
		highScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// show hi scores
			}
		});
		viewMenu.add(highScores);
		temp.add(viewMenu);

		JMenu helpMenu = new JMenu("Help");
		JMenuItem hint = new JMenuItem("Hint");
		hint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// show hint
			}
		});
		helpMenu.add(hint);
		JMenuItem refresh = new JMenuItem("Refresh Queue");
		refresh.addActionListener(new RefreshController(queue));
		helpMenu.add(refresh);

		JMenuItem about = new JMenuItem("About game");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextArea message = new JTextArea("");
				message.setLineWrap(true);
				message.setWrapStyleWord(true);

				JScrollPane scroll = new JScrollPane(message);

				JOptionPane.showMessageDialog(null, scroll);
				try {

				} catch (Exception ex) {
					System.out.println("Error occured in WindowView.createMenu()");
					ex.printStackTrace();
				}
			}
		});
		helpMenu.add(about);
		temp.add(helpMenu);

		return temp;
	}

	private void shake() {
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
			System.out.println("Error occured in WindowView.shake()");
			ex.printStackTrace();
		}
	}
}
