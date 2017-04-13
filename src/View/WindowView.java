package View;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.*;
import Controller.*;
import Model.*;
import sumfun.SumFun;

public class WindowView extends JFrame implements Observer {

	// utilities to size window
	private final int WINDOW_WIDTH = 660;
	private final int WINDOW_HEIGHT = 700;
	private final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int LOCATION_X = screensize.height / 5;
	private final int LOCATION_Y = (int) (screensize.width - (screensize.width * 0.97));

	// design members
	private JPanel gameView; // holds all sub-views below
	private JPanel gridView; // view for game board
	private JPanel queueView; // view for game queue
	private JPanel infoView; // view for info/statistics on current game
	private final JMenuBar menu; // menu for options and operations
	private JLabel moves_holder, score_holder;

	private SumFun game;

	// model members
	private GridModel gridModel; // grid model
	private QueueModel queueModel; // queue model
	private OverallHighScoreModel overallScoreModel;//score model

	// data members
	private TileModel[][] grid; // grid data -> game board
	private TileModel[] queue; // queue data -> game queue
	private boolean timedGame;

	private Timer timer;
	// statistic members
	private int movesRem; // moves remaining in game
	private boolean usedHint; // flag to determine if hint has been used

	/**
	 * Constructor for a Window object.
	 */
	public WindowView(SumFun game, GridModel g, QueueModel q, boolean tG) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(LOCATION_X, LOCATION_Y);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		setTitle("Sum Fun");
		timedGame = tG;
		gameView = new JPanel();
		gameView.setLayout(new BorderLayout());

		gridView = new JPanel();
		queueView = new JPanel();
		infoView = new JPanel();

		this.game = game;
		gridModel = g;
		queueModel = q;
		grid = gridModel.getGrid();
		queue = new TileModel[5];

		movesRem = game.getMaxMoves();
		usedHint = false;

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

			if (!gridModel.getValid()) {
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
			score_holder.setText("" + gridModel.getScore());
			moves_holder.setText("" + (game.getMaxMoves() - gridModel.getMovesTaken()));
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
				tile.addActionListener(new GridController(row, col, game));
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
		time_holder = new JLabel();
		if (timedGame) {

			timer = new Timer(1000, new CountdownController(this, gridModel, time_holder, moves_holder));

			timer.start();

		}

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
		newGame.addActionListener(new NewGameController(game));
		fileMenu.add(newGame);

		JMenuItem save = new JMenuItem("Save game");

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
				HighScoreView h1 = new HighScoreView();
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
		JMenuItem refresh = new JMenuItem("Refresh queue");
		refresh.addActionListener(new RefreshController(queue, queueModel));
		helpMenu.add(refresh);

		JMenuItem about = new JMenuItem("About game");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BufferedReader br = new BufferedReader(new FileReader("resources/about.txt"));
					String message = "";
					String line = "";
					while ((line = br.readLine()) != null) {
						message += line + "\n";
					}
					JOptionPane.showMessageDialog(null, message, "About Sum Fun", JOptionPane.INFORMATION_MESSAGE);
					br.close();
				} catch (Exception ex) {
					System.out.println("Error occured in WindowView.createMenu()");
					System.out.println(ex);
				}
			}
		});
		helpMenu.add(about);
		temp.add(helpMenu);

		return temp;
	}

	public Timer getTimer() {
		return timer;
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
				setLocation(offset, oy); // shake window
				Thread.sleep(10);
			}
		} catch (Exception ex) {
			System.out.println("Error occured in WindowView.shake()");
			ex.printStackTrace();
		}
		setLocation(ox, oy); // place window back in original position
	}
}
