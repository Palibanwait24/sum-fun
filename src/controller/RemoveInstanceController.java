package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.GridModel;
import model.TileModel;
import view.RemoveTileView;
import view.WindowView;

public class RemoveInstanceController implements ActionListener {

	private TileModel[][] grid;
	private boolean usedRemove = false;
	private int removeCount = 1;
	private WindowView window;

	public RemoveInstanceController(GridModel model, WindowView window) {
		this.window = window;
		grid = model.getGrid();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (usedRemove == false) {
			RemoveTileView myView = new RemoveTileView();
			String valueToRemove = myView.getValueToRemove();
			if (valueToRemove.equals("0") || valueToRemove.equals("1") || valueToRemove.equals("2")
					|| valueToRemove.equals("3") || valueToRemove.equals("4") || valueToRemove.equals("5")
					|| valueToRemove.equals("6") || valueToRemove.equals("7") || valueToRemove.equals("8")
					|| valueToRemove.equals("9")) {
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
			}
		}
	}

	public void resetRemove(boolean b) {
		usedRemove = b;
		removeCount = 1;
		window.updateRemoveButtonCount(removeCount);
	}
}
