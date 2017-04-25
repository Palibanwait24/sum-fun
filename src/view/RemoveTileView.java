package view;

import javax.swing.JOptionPane;

public class RemoveTileView {

	
	public String getValueToRemove() {
		String value = JOptionPane.showInputDialog(null, "Please enter the number you want to remove from the grid" ,null);
		return value;
	}

}
