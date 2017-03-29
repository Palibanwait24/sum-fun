package sumfun;

import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class QueueView extends JPanel {

	// private int[] queue; // queue to hold upcoming moves
	private TileModel[] queue;

	public QueueView() {
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

	public int getQueueValue() {
		return Integer.parseInt(queue[0].getData());
	}
	public void updateQueue(){

		for(int x = 0; x < queue.length-1; x++){
			queue[x].setDataString(queue[x+1].getData());
		}
		Random rand = new Random();
		queue[queue.length-1].setDataInteger(rand.nextInt(9) + 1);
	}
	public String remove(){
		String temp =  queue[0].getData();
		updateQueue();
		return temp;
	}
	public void refresh() {
		for (int x = 0; x < queue.length; x++) {
			Random rand = new Random();
			int n = rand.nextInt(9) + 1; // generate random number in range [1,9]
			String temp = "" + n;
			queue[x].setDataString(temp);
		}

	}
}
