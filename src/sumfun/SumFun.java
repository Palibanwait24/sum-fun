package sumfun;

import Model.*;
import View.WindowView;

// CS360 - Sum Fun Game

public class SumFun {

    public static GridModel grid = null;
    public static QueueModel queue = null;
    public static WindowView mainView = null;
    public final static int maxMoves = 50;
	
    public static void main(String[] args) {
    	grid = new GridModel();
    	queue = new QueueModel();
        mainView = new WindowView(grid, queue); // main game frame
        mainView.addObserver(grid);
        mainView.addObserver(queue);
        grid.start();// do we need
        queue.start();// do we need
        mainView.setVisible(true);
    }
    
}
