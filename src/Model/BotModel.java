package Model;

public class BotModel {

	private GridModel gridModel;
	private final int sleep = 2000; // time between moves

	public BotModel(GridModel gridModel) {
		this.gridModel = gridModel;
	}

	public void run() {
		while (!gridModel.checkWin()) {
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
