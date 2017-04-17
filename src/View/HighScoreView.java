package View;

import Model.OverallHighScoreModel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class HighScoreView extends JFrame implements Observer {

	private final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int LOCATION_X = screensize.height / 3;
	private final int LOCATION_Y = (int) (screensize.width - (screensize.width * 0.97));
	private final int WINDOW_WIDTH = 400;
	private final int WINDOW_HEIGHT = 400;
	private final int NUMBER_OF_SCORES = 10;
	private final String FILENAME = "resources\\scores.txt";
	private ArrayList<JLabel> top10;
	private ArrayList<OverallHighScoreModel> scoreList;
	private Scanner sc;
	private File file;
	private String tmpName, tmpDate, tmpScore;
	private Date date;
	private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	public void update(Observable o, Object arg) {

	}

	public HighScoreView() {
		top10 = new ArrayList<>(NUMBER_OF_SCORES);
		int index = 0;
		int score = 0;
		scoreList = new ArrayList<>(NUMBER_OF_SCORES + 1);
		setVisible(true);
		setLocation(LOCATION_X, LOCATION_Y);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		setTitle("High Scores");
		setLayout(new GridLayout(11, 3));
		try {
			file = new File(FILENAME);
			sc = new Scanner(file);
			sc.useDelimiter(",");
			while (sc.hasNext()) {
				if (index == 0) {
					tmpName = sc.next();
				} else if (index == 1) {
					tmpDate = sc.next();
					date = formatter.parse(tmpDate);
				} else {
					tmpScore = sc.next();
					score = Integer.parseInt(tmpScore);
				}
				if (index == 2) {
					index = 0;
					scoreList.add(new OverallHighScoreModel(tmpName, date, score));
				} else {
					index++;
				}
			}
			sc.close();
			Collections.sort(scoreList);
			printScores();
			updateJlabels();
		} catch (Exception ex) {
			System.out.printf("Error occured in HighScoreView");
		}

	}

	public void printScores() {
		int index = NUMBER_OF_SCORES - scoreList.size();
		for (int i = 0, j = scoreList.size() + 1; i < index; i++, j++) {
			JLabel temp = new JLabel(j + ". ");
			top10.add(i, temp);
		}
		for (int i = 0, j = 1; i < scoreList.size(); i++, j++) {
			JLabel temp = new JLabel(j + ". " + scoreList.get(i));
			top10.add(i, temp);
		}

	}

	public void updateJlabels() {
		for (JLabel el : top10) {
			add(el);
		}
	}

	public void addScore(OverallHighScoreModel addedScore) {
		if (scoreList.size() >= 10) {
			scoreList.set(11, addedScore);
			Collections.sort(scoreList);
			scoreList.remove(11);
			updateFile();
			printScores();
			updateJlabels();
		} else {
			scoreList.add(addedScore);
			Collections.sort(scoreList);
			updateFile();
			printScores();
			updateJlabels();
		}
	}

	public void updateFile() {
		try {
			PrintWriter writer = new PrintWriter(FILENAME);
			for (OverallHighScoreModel el : scoreList) {
				writer.print(el.getName() + "," + formatter.format(el.getDate()) + "," + el.getScore() + ",");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("file not updating");
		}

	}

}
