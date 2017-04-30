package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.TimedHighScoreModel;

public class TimedHighScoreView extends JFrame implements Observer {

	private final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int locationX = screensize.height;
	private final int locationY = (int) (screensize.width - (screensize.width * 0.97));
	private final int width = 400;
	private final int height = 400;
	private final int numberOfScores = 10;
	private final String fileName = "resources/timedScores.txt";
	private ArrayList<JLabel> top10;
	private ArrayList<TimedHighScoreModel> scoreList;
	private Scanner sc;
	private File file;
	private String tmpName;
	private String tmpDate;
	private String tmpTime;
	private Date date;
	private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	public TimedHighScoreView() {
		top10 = new ArrayList<>(numberOfScores);
		int index = 0;
		int time = 0;
		scoreList = new ArrayList<>(numberOfScores + 1);
		setVisible(false);
		setLocation(locationX, locationY);
		setSize(width, height);
		setResizable(false);
		setTitle("High Scores");
		setLayout(new GridLayout(11, 3));
		try {
			file = new File(fileName);
			file.createNewFile();
			sc = new Scanner(file);
			sc.useDelimiter(",");
			while (sc.hasNext()) {
				if (index == 0) {
					tmpName = sc.next();
				} else if (index == 1) {
					tmpDate = sc.next();
					date = formatter.parse(tmpDate);
				} else {
					tmpTime = sc.next();
					time = Integer.parseInt(tmpTime);
				}
				if (index == 2) {
					index = 0;
					scoreList.add(new TimedHighScoreModel(tmpName, date, time));
				} else {
					index++;
				}
			}
			sc.close();
			Collections.sort(scoreList);
			printScores();
			updateJlabels();
		} catch (Exception ex) {
			System.out.println(ex);
			System.out.printf("Error occured in HighScoreView");
		}

	}

	public void printScores() {

		int index = numberOfScores - scoreList.size();
		for (int i = 0, j = scoreList.size() + 1; i < index; i++, j++) {
			JLabel temp = new JLabel(j + ". ");
			top10.add(i, temp);
		}

		for (int i = 0, j = 1; i < scoreList.size(); i++, j++) {
			JLabel temp = new JLabel(j + ". " + scoreList.get(i));
			top10.add(i, temp);
		}

	}
	public void showView(boolean answer){
		this.setVisible(answer);
	}
	public void updateJlabels() {
		JLabel title = new JLabel("Timed Game Leaderboard", SwingConstants.CENTER);
		this.add(title);
		for (JLabel el : top10) {
			add(el);
		}
	}

	public void addScore(TimedHighScoreModel addedScore) {
		System.out.println(scoreList.toString());
		if (scoreList.size() > 10) {

			scoreList.set(11, addedScore);
			Collections.sort(scoreList);
			scoreList.remove(11);
			updateFile();
			printScores();
			updateJlabels();
		} else {

			scoreList.add(addedScore);
			System.out.println(scoreList.toString());
			Collections.sort(scoreList);
			System.out.println(scoreList.toString());
			updateFile();
			System.out.println(scoreList.toString());
			printScores();
			System.out.println(scoreList.toString());
			updateJlabels();
		}
		System.out.println(scoreList.toString());
	}

	public void updateFile() {
		try {
			PrintWriter writer = new PrintWriter(fileName);
			for (TimedHighScoreModel el : scoreList) {
				writer.print(el.getName() + "," + formatter.format(el.getDate()) + "," + el.getScore() + ",");
			}
			writer.close();
		} catch (Exception ex) {
			System.out.println("Error occured in HighScoreView.updateFile()");
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
