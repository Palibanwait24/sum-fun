package model;

import java.util.Date;

public class TimedHighScoreModel extends HighScoreModel implements Comparable<TimedHighScoreModel> {
	private int time;

	public TimedHighScoreModel(String name, Date d, int time) {
		super(name, d);
		this.time = time;
	}

	public int getScore() {
		return time;
	}

	@Override
	public String toString() {
		String timeValue = null;
		int minutes = time / 60; // number of minutes
		int seconds = time % 60; // number of seconds

		if (seconds < 10) {
			timeValue = minutes + ":0" + seconds;
		} else {
			timeValue = (minutes + ":" + seconds);
		}
		return super.toString() + " " + timeValue;
	}

	@Override
	public int compareTo(TimedHighScoreModel other) {
		return this.time < other.time ? -1 : this.time < other.time ? 1 : 0;
	}
}
