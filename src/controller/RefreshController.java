package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.QueueModel;
import model.TileModel;
import sumfun.SumFun;

public class RefreshController implements ActionListener {

	private boolean usedRefresh = false;
	private TileModel[] queue;
	private QueueModel model;
	private SumFun game;

	public RefreshController(TileModel[] queue, QueueModel model, SumFun game) {
		this.game = game;
		this.queue = queue;
		this.model = model;
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
		} else {
			JOptionPane.showMessageDialog(null, "Refresh queue already used!");
		}
	}

	public void setRefresh(boolean b) {
		usedRefresh = b;
	}

}
