package Controller;

import Model.QueueModel;
import Model.TileModel;
import View.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class RefreshController implements ActionListener {
	private boolean usedRefresh = false;
	private TileModel[] queue;

	public RefreshController(TileModel[] queue) {
		this.queue = queue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (usedRefresh == false) {
			for (TileModel x :queue ) {
				QueueModel.getQueueModel().updateQueue();
			}
			usedRefresh = true;
		} else {
			JOptionPane.showMessageDialog(null, "Refresh queue already used!");
		}
		
	}
	
}
