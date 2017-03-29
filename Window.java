package sumfun;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window extends JFrame {

	private final int WINDOW_WIDTH = 680;
	private final int WINDOW_HEIGHT = 720;
	private final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int LOCATION_X = screensize.height / 3;
	private final int LOCATION_Y = (int) (screensize.width - (screensize.width * 0.97));
	private GameView gamePane;
	private final JMenuBar menu; // menu for options and operations

	/**
	 * Constructor for a Window object.
	 */
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(LOCATION_X, LOCATION_Y);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(true);

		menu = createMenu();
		setJMenuBar(menu);

		gamePane = new GameView();
		gamePane.setFocusable(true);
		add(gamePane);
		pack();
	}

	private JMenuBar createMenu() {
		JMenuBar temp = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem n = new JMenuItem("New game"); // start new game
		n.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePane.setVisible(false);
				remove(gamePane);
				gamePane = new GameView();
				add(gamePane);
				gamePane.setFocusable(true);
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
		JMenuItem GameViewHighScores = new JMenuItem("GameView high scores"); // GameView high scores in game
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
