package Model;

import sumfun.SumFun;

public class BotModel {

	private GridModel gridModel;
	private SumFun game;
	private final int sleep = 500; // time between moves

	public BotModel(SumFun game, GridModel gridModel) {
		this.game = game;
		this.gridModel = gridModel;
	}

	public void run() {
		while (!gridModel.checkWin() || !game.getStop()) {
			move();
		}
	}

	public void move() {
		try {
			Thread.sleep(sleep); // come on, let the bot think
			gridModel.moveBot();
		} catch (Exception ex) {
			// do nothing
		}
	}
}
