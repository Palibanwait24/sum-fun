package sumfun;

import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class QueuePane extends JPanel {

	// private int[] queue; // queue to hold upcoming moves
	private Tile[] queue;

	public QueuePane() {
		queue = new Tile[5];

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		for (int i = 0; i < queue.length; i++) {
			gbc.gridy = i;
			Tile temp = new Tile(true);
			add(temp, gbc);
			queue[i] = temp;
		}

	}

	public int getQueueValue() {
		return Integer.parseInt(queue[0].getData());
	}

	public void refresh() {
		for (int x = 0; x < queue.length; x++) {
			Random rand = new Random();
			int n = rand.nextInt(9) + 1; // generate random number in range
											// [1,9]
			String temp = "" + n;
			queue[x].setDataString(temp);
		}

	}
}
