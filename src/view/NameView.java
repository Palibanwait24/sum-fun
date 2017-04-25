package view;

import javax.swing.JOptionPane;

public class NameView {

	public String getName() {
		String name = JOptionPane.showInputDialog(null, "Congratulations!! You Won The Game!!\n Please enter your name below.",null);
		return name;
	}

}
