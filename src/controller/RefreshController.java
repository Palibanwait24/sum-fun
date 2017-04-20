package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.QueueModel;
import model.TileModel;

public class RefreshController implements ActionListener {

	private boolean usedRefresh = false;
	private TileModel[] queue;
	private QueueModel model;

	public RefreshController(TileModel[] queue, QueueModel model) {
		this.queue = queue;
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (usedRefresh == false) {
			for (@SuppressWarnings("unused") TileModel x : queue) {
				model.updateQueue();
			}
			usedRefresh = true;
		} else {
			JOptionPane.showMessageDialog(null, "Refresh queue already used!");
		}
	}

	public void setRefresh(boolean b) {
		usedRefresh = b;
	}

}
