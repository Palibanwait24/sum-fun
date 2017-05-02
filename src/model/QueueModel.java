package model;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;
import java.util.Random;
import sumfun.SumFun;

public class QueueModel extends Observable {

	private SumFun game;
	private final int size = 5; // size of queue
	private QueueModel model;
	private Queue<Integer> queue; // queue for game
	private int count; // current count of how many tiles have been added to queue
	private int maxTiles; // total possible number of tiles generated
	private boolean allowNewTiles;

	public QueueModel(SumFun game) {
		this.game = game;
		queue = new LinkedList<Integer>();
		count = 0;
		maxTiles = game.getMaxMoves();
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

		if (count <= game.getMaxMoves()) {
			enqueue();
		}

		setChanged();
		notifyObservers();
		return next;
	}

	public void refresh() {
		int size = 0;
		if (queue.size() == 5) {
			size = queue.size();
		} else {
			size = queue.size() - 1; // handles -1 tail of queue
		}

		queue.clear();

		for (int i = 0; i < size; i++) {
			enqueue();
		}

		if (queue.size() < 5) {
			queue.add(-1);
		}

		setChanged();
		notifyObservers();
	}

	private void enqueue() {
		if (allowNewTiles) {
			queue.add(getRandomNumber());
			return;
		}
		if (maxTiles - count > 0) {
			queue.add(getRandomNumber());
		} else {
			queue.add(-1);
		}
		count++;
	}

	private int dequeue() {
		if (game.getStop()) {
			return -1;
		}
		return queue.poll();
	}

	public void reinitialize() {
		count = 0; // reset number of items generated
		queue.clear(); // empty the current queue
		for (int i = 0; i < size; i++) {
			enqueue();
		}
		setChanged();
		notifyObservers();
	}

	public QueueModel getQueueModel() {
		return model;
	}

	public Queue<Integer> getQueue() {
		return queue;
	}

	public int getTopOfQueue() {
		return queue.element();
	}

	private int getRandomNumber() {
		Random rand = new Random();
		int n = rand.nextInt(10); // generate random number in range [0,9]
		return n;
	}

	public void setAllowNewTiles(boolean allow) {
		allowNewTiles = allow;
	}

}
