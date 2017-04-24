package view;

import javax.swing.JOptionPane;

public class NameView {

	public String getName() {
		String name = JOptionPane.showInputDialog(null, "Congratulations!! You Won The Game!!","Please enter your name.");
		return name;
	}

}
