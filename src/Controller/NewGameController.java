package Controller;

import Model.GridModel;
import Model.QueueModel;
import View.*;
import sumfun.SumFun;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameController implements ActionListener {
	SumFun oldGame;

	public NewGameController(SumFun oldGame) {
		this.oldGame = oldGame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		oldGame.mainView.setVisible(false);
		oldGame.queue = new QueueModel(oldGame);
		oldGame.grid = new GridModel(oldGame, oldGame.queue);
		oldGame.mainView = new WindowView(oldGame, oldGame.grid, oldGame.queue, oldGame.getTimedGame());
		oldGame.mainView.addObserver(oldGame.grid);
		oldGame.mainView.addObserver(oldGame.queue);
		oldGame.grid.start();
		oldGame.queue.start();
		oldGame.mainView.setVisible(true);

	}

}
