package Model;

import java.util.*;
import sumfun.SumFun;

public class QueueModel extends Observable {

	private SumFun game;
	private final int size = 5; // size of queue
	private QueueModel model;
	private Queue<Integer> queue; // queue for game
	private int count; // current count of how many tiles have been added to queue

	public QueueModel(SumFun game) {
		this.game = game;
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

		if (count < game.getMaxMoves()) {
			enqueue();
		}

		setChanged();
		notifyObservers();
		return next;
	}

	private void enqueue() {
		queue.add(getRandomNumber());
		count++;
		// TODO only allow 50 tiles to be added to queue, no more
	}

	private int dequeue() {
		return queue.poll();
	}

	public QueueModel getQueueModel() {
		return model;
	}

	public Queue<Integer> getQueue() {
		return queue;
	}

	protected int getTopOfQueue() {
		return queue.element();
	}

	private int getRandomNumber() {
		Random rand = new Random();
		int n = rand.nextInt(10); // generate random number in range [1,9]
		return n;
	}

}
