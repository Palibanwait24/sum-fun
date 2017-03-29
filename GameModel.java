package sumfun;

import java.util.*;

public class GameModel extends Observable {

	private BoardView board;
	private InfoView info;
	private QueueView queue;
	private TileModel[][] grid;
	private Window main;
	
	public GameModel() {
		
	}
	
	@Override
	protected void setChanged() {
		// TODO Auto-generated method stub
		super.setChanged();
	}
	
	@Override
	public void notifyObservers(Object arg) {
		// TODO Auto-generated method stub
		super.notifyObservers(arg);
	}
	
}
