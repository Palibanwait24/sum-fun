package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StartView extends JFrame {

	private WindowView parent;

	public StartView(WindowView window) {
		parent = window;
	}

	public void show() {
		Object[] options = { "Untimed game", "Timed game", "Watch bot", "Exit" };
		int n = JOptionPane.showOptionDialog(null, "Welcome to Sum Fun!\nWhat type of game do you want to play?",
				"Select Game", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[0]);

		if (n == 2) { // bot game
			parent.setTimedGame(false);
			parent.setBotEnabled(true);
			parent.setName("bot");
		} else if (n == 1) { // timed game
			parent.setTimedGame(true);
			parent.setBotEnabled(false);
		} else if (n == 0) { // untimed game
			parent.setTimedGame(false);
			parent.setBotEnabled(false);
		} else { // exit
			System.exit(0);
		}

	}

}
