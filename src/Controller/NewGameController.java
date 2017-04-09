package Controller;

import sumfun.SumFun;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameController implements ActionListener {
	SumFun game;

	public NewGameController(SumFun currentGame) {
		this.game = currentGame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		game.mainView.setVisible(false);
		game.startNewGame();
	}

}
