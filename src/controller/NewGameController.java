package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sumfun.SumFun;

public class NewGameController implements ActionListener {
	SumFun game;

	public NewGameController(SumFun currentGame) {
		this.game = currentGame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		game.startNewGame();
	}

}
