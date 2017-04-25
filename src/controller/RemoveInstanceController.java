package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.GridModel;
import sumfun.SumFun;
import view.WindowView;

public class RemoveInstanceController implements ActionListener {

	GridModel grid;
	SumFun game;
	WindowView window;

	public RemoveInstanceController(GridModel grid, SumFun game, WindowView window) {
		this.grid = grid;
		this.game = game;
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
