package Model;

import java.util.*;

import View.BoardView;
import View.InfoView;
import View.QueueView;
import sumfun.Window;

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
