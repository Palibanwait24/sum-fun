package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class RemoveTileView extends JFrame {

	public String getValueToRemove() {
		String value = null;

		while (true) {
			value = JOptionPane.showInputDialog(null, "Please enter the number you want to remove from the grid:",
					null);
			if (value == null || value.equals("")) {
				return null;
			}
			int n = Integer.parseInt(value);
			if (n >= 0 && n <= 9) {
				break;
			} else {
				shake();
			}
		}

		return value;
	}

	public void shake() {
		final int length = 8;
		final int ox = getLocationOnScreen().x; // original x position
		final int oy = getLocationOnScreen().y; // original y position

		int offset = ox;
		try {
			for (int i = 0; i < length; i++) {
				if (i % 2 == 0) {
					offset = ox + 5;
				} else {
					offset = ox - 5;
				}
				setLocation(offset, oy); // shake window
				Thread.sleep(10);
			}
		} catch (Exception ex) {
			System.out.println("Error occured in WindowView.shake()");
			ex.printStackTrace();
		}
		setLocation(ox, oy); // place window back in original position
	}

}
