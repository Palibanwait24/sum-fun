package view;

import controller.CountdownController;
import controller.GridController;
import controller.HintController;
import controller.NewGameController;
import controller.RefreshController;
import controller.RemoveInstanceController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.GridModel;
import model.QueueModel;
import model.TileModel;

import sumfun.SumFun;

public class WindowView extends JFrame implements Observer {

	// utilities to size window
	private final int width = 850;
	private final int height = 700;
	private final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int locationX = screensize.height / 3;
	private final int locationY = (int) (screensize.width - (screensize.width * 0.96));

	// design members
	private JPanel gameView; // holds all sub-views below
	private JPanel gridView; // view for game board
	private JPanel queueView; // view for game queue
	private JPanel infoView; // view for info/statistics on current game
	private JPanel helperView; // view for hint and refresh in-game helpers
	private final JMenuBar menu; // menu for options and operations
	private JLabel movesHolder;
	private JLabel scoreHolder;
	private JLabel timeHolder;
	private JButton hintButton;
	private JButton refreshButton;
	private JButton removeButton;

	private SumFun game;
	private Timer timer;
	private RefreshController rc;
	private HintController hc;
	private RemoveInstanceController ric;
	private CountdownController countdownControl;
	// model members
	private GridModel gridModel; // grid model
	private QueueModel queueModel; // queue model
	private HighScoreView h1;//score model
	private TimedHighScoreView timedScore;//score model
	// data members
	private TileModel[][] grid; // grid data -> game board
	private TileModel[] queue; // queue data -> game queue
	private boolean timedGame;
	private boolean outOfMoves;
	private boolean botEnabled;

	// statistic members
	private int hintsRem; // hints remaining in game
	private int movesRem; // moves remaining in game

	/**
	 * Constructor for a Window object.
	 */
	public WindowView(SumFun game, GridModel g, QueueModel q, boolean isTimedGame) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(locationX, locationY);
		setSize(width, height);
		setResizable(false);
		setTitle("Sum Fun");
		timedGame = isTimedGame;
		gameView = new JPanel();
		gameView.setLayout(new BorderLayout());
		outOfMoves = false;
		botEnabled = false;
		gridView = new JPanel();
		queueView = new JPanel();
		infoView = new JPanel();
		helperView = new JPanel();

		this.game = game;
		gridModel = g;
		queueModel = q.getQueueModel();
		grid = gridModel.getGrid();
		queue = new TileModel[5];

		movesRem = game.getMaxMoves();
		hintsRem = 3;

		buildGridView();
		buildQueueView();
		buildInfoView();
		buildHelperView();
		menu = createMenu();
		setJMenuBar(menu);
		gameView.add(gridView, BorderLayout.CENTER);
		gameView.add(queueView, BorderLayout.WEST);
		gameView.add(infoView, BorderLayout.SOUTH);
		gameView.add(helperView, BorderLayout.EAST);
		add(gameView);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o.getClass().getName().equals("model.GridModel")) {
			// process grid update
			GridModel gridModel = (GridModel) o;
			TileModel[][] temp = ((GridModel) o).getGrid();

			if (game.getStop()) {
				if (gridModel.getWin()) {
					JOptionPane.showMessageDialog(null, "Game is over! Nice job!");
					return;
				} else if (gridModel.getFull()) {
					JOptionPane.showMessageDialog(null, "Board is full! Game over! Please start a new game");
					return;
				} else if (outOfMoves == false) {
					JOptionPane.showMessageDialog(null, "You are out of moves! Please start a new game.");
					outOfMoves = true;
					return;
				} else if (botEnabled == false) {
					JOptionPane.showMessageDialog(null, "You are out of moves! Please start a new game.");
					return;
				}
			}

			if (!gridModel.getValid()) {
				shake();
				return;
			}

			for (int r = 0; r < temp.length; r++) {
				for (int c = 0; c < temp[r].length; c++) {
					grid[r][c].setBackground(temp[r][c].getHighlight());
					if (temp[r][c].isEmpty()) {
						grid[r][c].setData("");
					} else {
						grid[r][c].setData(temp[r][c].getData());
					}
				}
			}
			scoreHolder.setText("" + gridModel.getScore());
			movesHolder.setText("" + (game.getMaxMoves() - gridModel.getMovesTaken()));
		} else if (o.getClass().getName().equals("model.QueueModel")) {
			// process queue update
			Queue<Integer> temp = ((QueueModel) o).getQueue();
			int i = 0;

			for (Integer item : temp) {
				if (i > 4) {
					break;
				}
				if (item.intValue() == -1) {
					queue[i].setData("");
				} else {
					queue[i].setData(item.toString());
				}
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
		gridView.setBackground(Color.DARK_GRAY);
	}

	public void buildQueueView() {
		queueView.setLayout(new GridBagLayout());
		queueView.setPreferredSize(new Dimension(100, 200));
		queueView.revalidate();
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel label = new JLabel("Queue");
		label.setFont(new Font("Rockwell", Font.BOLD, 20));
		JLabel separ = new JLabel("=======");
		separ.setFont(new Font("Rockwell", Font.BOLD, 20));
		queueView.add(label, gbc);
		gbc.gridy = 1;
		queueView.add(separ, gbc);

		for (int i = 0; i < queue.length; i++) {
			gbc.gridy = i + 2;
			TileModel temp = new TileModel(true);
			queueView.add(temp, gbc);
			queue[i] = temp;
			queue[i].setBorderPainted(false);
			queue[i].setFocusPainted(false);
		}
		queue[0].setBackground(Color.YELLOW);
		queue[0].setOpaque(true);

		queueView.setBackground(Color.CYAN.darker());
	}

	private void buildInfoView() {

		// data fields
		int score = 0;

		// design fields
		JLabel scoreLabel;
		JLabel movesLabel;
		JLabel timeLabel;

		// construct info pane layout
		infoView.setLayout(new GridLayout(3, 2));

		scoreLabel = new JLabel("\t\tScore: ");
		movesLabel = new JLabel("\t\tMoves remaining: ");
		timeLabel = new JLabel("\t\tTime: ");

		scoreLabel.setFont(new Font("Rockwell", Font.BOLD, 16));
		movesLabel.setFont(new Font("Rockwell", Font.BOLD, 16));
		timeLabel.setFont(new Font("Rockwell", Font.BOLD, 16));

		scoreHolder = new JLabel("" + score);
		movesHolder = new JLabel("" + movesRem);
		timeHolder = new JLabel();

		scoreHolder.setFont(new Font("Rockwell", Font.BOLD, 16));
		movesHolder.setFont(new Font("Rockwell", Font.BOLD, 16));
		timeHolder.setFont(new Font("Rockwell", Font.BOLD, 16));

		if (timedGame) {
			timer = new Timer(1000, countdownControl);
			timer.start();
		} else {
			timeHolder.setText("--:--");
		}

		//build top pane
		infoView.add(scoreLabel);
		infoView.add(scoreHolder);
		infoView.add(movesLabel);
		infoView.add(movesHolder);
		infoView.add(timeLabel);
		infoView.add(timeHolder);

		infoView.setBackground(Color.CYAN.darker());
	}

	private void buildHelperView() {
		helperView.setLayout(new GridLayout(3, 1));

		hintButton = new JButton("Hint (" + hintsRem + ")");
		refreshButton = new JButton("<html>Refresh <br/>Queue (1)</html>");
		removeButton = new JButton("<html>Remove <br/>Instance (1)</html>");

		hintButton.setFont(new Font("Rockwell", Font.BOLD, 20));
		refreshButton.setFont(new Font("Rockwell", Font.BOLD, 20));
		removeButton.setFont(new Font("Rockwell", Font.BOLD, 20));

		hintButton.setOpaque(true);
		hintButton.setBackground(Color.RED.brighter());
		refreshButton.setOpaque(true);
		refreshButton.setBackground(Color.BLUE);
		removeButton.setOpaque(true);
		removeButton.setBackground(Color.GREEN.darker());

		rc = new RefreshController(queueModel, game, this);
		refreshButton.addActionListener(rc);
		helperView.add(refreshButton);

		hc = new HintController(gridModel, queueModel, this, game);
		hintButton.addActionListener(hc);
		helperView.add(hintButton);

		ric = new RemoveInstanceController(gridModel, this, game);
		removeButton.addActionListener(ric);
		helperView.add(removeButton);

		helperView.setBackground(Color.CYAN.darker());
	}

	private JMenuBar createMenu() {
		JMenuBar temp = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem newGame = new JMenuItem("New game");
		newGame.addActionListener(new NewGameController(game));
		fileMenu.add(newGame);

		JMenuItem exit = new JMenuItem("Exit"); // exit game
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog((Component) getParent(),
						"Are you sure you want to exit Sum Fun?", "Confirm Exit", 0);
				if (option == 0) {
					System.exit(0);
				} else {
					return;
				}
				//System.exit(0); // use for development, remove later
			}
		});
		fileMenu.add(exit);
		temp.add(fileMenu);

		JMenu viewMenu = new JMenu("View");
		JMenuItem highScores = new JMenuItem("High scores"); // view local high scores
		highScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				h1 = new HighScoreView();
				h1.showView(true);
			}
		});
		JMenuItem timedHighScores = new JMenuItem("Fastest times"); // view local high scores
		timedHighScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timedScore = new TimedHighScoreView();
				timedScore.showView(true);
			}
		});
		viewMenu.add(timedHighScores);

		viewMenu.add(highScores);
		temp.add(viewMenu);

		JMenu helpMenu = new JMenu("Help");

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
				}
			}
		});
		helpMenu.add(about);
		temp.add(helpMenu);

		return temp;
	}

	public void setTimedGame(boolean isTimedGame) {
		timedGame = isTimedGame;

		if (timedGame) {

			initializeTimer();
			timer.start();
		}

		game.setTimedGame(timedGame);
	}

	public CountdownController getCountdown() {
		return countdownControl;
	}

	public void initializeTimer() {
		timedGame = true;

		if (timer != null) {
			timer.stop();

		}
		countdownControl = new CountdownController(this, gridModel, timeHolder, movesHolder);
		timer = new Timer(1000, countdownControl);
		timer.start();
	}

	public void setBotEnabled(boolean isBotEnabled) {
		outOfMoves = false;
		botEnabled = isBotEnabled;
		game.setBotEnabled(isBotEnabled);
	}

	public void resetRefresh() {

		rc.resetRefresh(false);
	}

	public void resetHint() {
		hc.resetHint();
	}

	public void resetRemove() {
		ric.resetRemove(false);
	}

	public void removeTimedGame() {
		if (timer != null) {
			timer.stop();
		}
		timeHolder.setText("--:--");
	}

	public Timer getTimer() {
		return timer;
	}

	public HighScoreView getHighScoreView() {
		return h1;
	}

	public void updateHintButtonCount(int hintsRemaining) {
		hintButton.setText("Hint (" + hintsRemaining + ")");
	}

	public void updateRefreshButtonCount(int refreshCount) {

		refreshButton.setText("<html>Refresh <br/>Queue (" + refreshCount + ")</html>");
	}

	public void updateRemoveButtonCount(int removeCount) {

		removeButton.setText("<html>Remove <br/>Instance (" + removeCount + ")</html>");
	}

	/*
	 *
	 * BEGIN ENGAGING SPECIAL EFFECTS SECTION
	 * 
	 */

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
