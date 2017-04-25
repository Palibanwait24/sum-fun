package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.QueueModel;
import model.TileModel;
import sumfun.SumFun;
import view.WindowView;

public class RefreshController implements ActionListener {

	private boolean usedRefresh = false;
	private int refreshCount = 1;
	private TileModel[] queue;
	private QueueModel model;
	private WindowView window;
	private SumFun game;

	public RefreshController(TileModel[] queue, QueueModel model, SumFun game, WindowView view) {
		this.game = game;
		this.queue = queue;
		this.model = model;
		this.window = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (game.getStop()) {
			JOptionPane.showMessageDialog(null, "You cannot do that now! Please start a new game.");
			return;
		}
		if (usedRefresh == false) {
			for (@SuppressWarnings("unused")
			TileModel x : queue) {
				model.setAllowNewTiles(true);
				model.updateQueue();
			}
			model.setAllowNewTiles(false);
			usedRefresh = true;
			refreshCount--;
			window.updateRefreshButtonCount(refreshCount);
		} else {
			JOptionPane.showMessageDialog(null, "Refresh queue already used!");
		}
	}

	public void resetRefresh(boolean b) {
		usedRefresh = b;
		refreshCount = 1;
		window.updateRefreshButtonCount(refreshCount);
	}

}
