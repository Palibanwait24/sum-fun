package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HighScoreModel {
	private String name;
	private Date date;
	private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	public HighScoreModel(String n, Date d) {
		name = n;
		date = d;
	}

	public String getName() {
		return name;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return name + " " + formatter.format(date);
	}
}
