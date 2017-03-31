package sumfun;

import Model.*;
import View.WindowView;

// CS360 - Sum Fun Game

public class SumFun {

    public static GridModel grid;
    public static QueueModel queue;
    public final static int maxMoves = 50;
	
    public static void main(String[] args) {
    	grid = new GridModel();
    	queue = QueueModel.getQueueModel();
        WindowView main = new WindowView(); // main game frame
        main.addObserver(grid);
        main.addObserver(queue);
        grid.start();
        queue.start();
        main.setVisible(true);
    }
    
}
