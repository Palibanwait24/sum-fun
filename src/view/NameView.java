package view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NameView {

	public String getName() {

		String[] options = { "OK" };
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		JLabel message = new JLabel("Congratulations!! You Won The Game!! Please enter your name: ");
		JTextField txt = new JTextField(20);
		panel.add(message);
		panel.add(txt);

		while (true) {
			int selectedOption = JOptionPane.showOptionDialog(null, panel, "Congratulations", JOptionPane.NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			if (selectedOption == 0) {
				String name = txt.getText();
				if (name.equals("")) {
					continue;
				} else {
					return name;
				}
			}
		}

	}

}
