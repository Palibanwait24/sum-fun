package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sumfun.SumFun;

public class Controller implements ActionListener {

	private int row;
	private int col;

	public Controller(int r, int c) {
		row = r;
		col = c;
	}

	public void actionPerformed(ActionEvent click) {
		SumFun.grid.move(row, col);
	}

}
