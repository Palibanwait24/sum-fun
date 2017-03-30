package Model;

import java.util.Observable;

public class QueueModel extends Observable {

	public QueueModel() {
		
	}
	

	public void start() {
		setChanged();
		notifyObservers();
	}
	
}
