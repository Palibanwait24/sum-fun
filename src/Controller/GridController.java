package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sumfun.SumFun;

public class GridController implements ActionListener {

	private SumFun game;
	private int row;
	private int col;

	public GridController(int r, int c, SumFun game) {
		this.game = game;
		row = r;
		col = c;
	}

	public void actionPerformed(ActionEvent click) {
		game.move(row, col);
	}

}
