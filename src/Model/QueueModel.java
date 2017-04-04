package Model;

import java.util.*;
import sumfun.SumFun;

public class QueueModel extends Observable {

	// private static QueueModel queue;
	private final int size = 5; // size of queue
	private static QueueModel model;
	private static Queue<Integer> queue; // queue for game
	private int count; // current count of how many tiles have been added to queue

	public QueueModel() {
		queue = new LinkedList<Integer>();
		count = 0;
		for (int i = 0; i < size; i++) {
			enqueue();
		}
		model = this;
	}
	
	public void start() { // initializes queue
		setChanged();
		notifyObservers();
	}
	
	public int updateQueue() {
		int next = dequeue();
		
		if (count < (SumFun.maxMoves - size)) {
			enqueue();
		}
		
		setChanged();
		notifyObservers();
		return next;
	}

	private void enqueue() {
		queue.add(getRandomNumber());
		//count++; // TODO only allow 50 tiles to be added to queue, no more
	}

	private int dequeue() {
		return queue.poll();
	}
	
	public static QueueModel getQueueModel() {
		return model;
	}
	
	public static Queue<Integer> getQueue() {
		return queue;
	}

	private int getRandomNumber() {
		Random rand = new Random();
		int n = rand.nextInt(10); // generate random number in range [1,9]
		return n;
	}

}
