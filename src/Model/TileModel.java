package Model;

import java.util.*;
import java.awt.*;
import javax.swing.*;

import sumfun.SumFun;

public class TileModel extends JButton {

	private final int d = 60; // dimension of tile
	private final Color cellColor = Color.WHITE; // default color of each cell
	private String data = "";

	/**
	 * Constructor for a Tile object.
	 *
	 * @param fill
	 */
	public TileModel(boolean fill) {
		setContentAreaFilled(true);
		setBorderPainted(true);
		setOpaque(false);
		setFont(new Font("Arial", Font.BOLD, 24));
		setBackground(this.cellColor);
		setForeground(getRandomColor());

		if (fill) {
			Random rand = new Random();
			int n = rand.nextInt(10); // generate random number in range [1,9]
			data = "" + n;
		}

		setData(data);
	}

	public boolean isEmpty() {
		return data.equals("");
	}

	// set number from a string
	public void setData(String s) {
		data = s;
		setText(data);
	}

	// set data from a integer
	public void setData(int i) {
		data = "" + i;
		setText(data);
	}

	public void newRandomValue() {
		Random rand = new Random();
		int n = rand.nextInt(10); // generate random number in range [1,9]
		this.setData(n);
	}

	// get data and return as string
	public String getData() {
		return data;
	}

	private Color getRandomColor() {
		Random rand = new Random();
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);

		Color c = new Color(r, g, b);
		return c.darker();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(d, d);
	}

}
