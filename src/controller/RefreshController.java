package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.QueueModel;
import sumfun.SumFun;
import view.WindowView;

public class RefreshController implements ActionListener {

	private boolean usedRefresh = false;
	private int refreshCount = 1;
	private QueueModel model;
	private WindowView window;
	private SumFun game;
	private SoundController sound;

	public RefreshController(QueueModel model, SumFun game, WindowView view) {
		this.game = game;
		this.model = model;
		this.window = view;
		sound = new SoundController();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (game.getStop()) {
			sound.chimeError();
			JOptionPane.showMessageDialog(null, "You cannot do that now! Please start a new game.");
			return;
		}
		if (usedRefresh == false) {
			model.setAllowNewTiles(true);
			model.refresh();
			model.setAllowNewTiles(false);
			sound.chimeRefresh();
			usedRefresh = true;
			refreshCount--;
			window.updateRefreshButtonCount(refreshCount);
		} else {
			sound.chimeError();
			JOptionPane.showMessageDialog(null, "Refresh queue already used!");
		}
	}

	public void resetRefresh(boolean b) {
		usedRefresh = b;
		refreshCount = 1;
		window.updateRefreshButtonCount(refreshCount);
	}

}
