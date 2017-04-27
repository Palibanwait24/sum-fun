package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.GridModel;
import model.TileModel;
import sumfun.SumFun;
import view.RemoveTileView;
import view.WindowView;

public class RemoveInstanceController implements ActionListener {

	private TileModel[][] grid;
	private boolean usedRemove = false;
	private int removeCount = 1;
	private WindowView window;
	private SumFun game;

	public RemoveInstanceController(GridModel model, WindowView window, SumFun game) {
		this.window = window;
		this.grid = model.getGrid();
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (game.getStop()) {
			JOptionPane.showMessageDialog(null, "You cannot do that now! Please start a new game.");
			return;
		}
		if (usedRemove == false) {
			RemoveTileView myView = new RemoveTileView();
			String valueToRemove = myView.getValueToRemove();

			if (valueToRemove == null) {
				return;
			}

			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					if (grid[i][j].getData().equals(valueToRemove)) {
						grid[i][j].setData("");
					}
				}
			}

			usedRemove = true;
			removeCount--;
			window.updateRemoveButtonCount(removeCount);
		} else {
			JOptionPane.showMessageDialog(null, "Remove instance already used!");
		}
	}

	public void resetRemove(boolean bool) {
		usedRemove = bool;
		removeCount = 1;
		window.updateRemoveButtonCount(removeCount);
	}
}
