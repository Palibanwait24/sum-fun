package View;

import Model.HighScoreModel;
import Model.OverallHighScoreModel;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Cameron on 4/12/2017.
 */
public class HighScoreView extends JFrame implements Observer {

    private final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int LOCATION_X = screensize.height / 3;
    private final int LOCATION_Y = (int) (screensize.width - (screensize.width * 0.97));
    private final int WINDOW_WIDTH = 400;
    private final int WINDOW_HEIGHT = 400;
    private final int NUMBER_OF_SCORES = 10;
    private final String FILENAME = "D:\\IdeaProjects\\sum-fun\\resources\\scores.txt";
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
        setTitle("High Score");
        setLayout(new GridLayout(11, 3));
        try {
            file = new File(FILENAME);
            sc = new Scanner(file).useDelimiter(",");
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

        } catch (FileNotFoundException | ParseException e) {
            System.out.printf(e.toString());
        }
        sc.close();
        Collections.sort(scoreList);
        printScores();
        updateJlabels();


    }

    public void printScores() {
        if (scoreList.size() != 10) {
            int index = NUMBER_OF_SCORES - scoreList.size();
            for (int i = 0, j = scoreList.size(); i < index + 1; i++, j++) {
                JLabel temp = new JLabel(j + ". ");
                top10.add(i, temp);
            }
            for (int i = 0, j = 1; i < scoreList.size(); i++, j++) {
                JLabel temp = new JLabel(j + ". " + scoreList.get(i));
                top10.add(i, temp);
            }
        }
    }

    public void updateJlabels() {
        for (JLabel el : top10) {
            add(el);
        }
    }

    public void addScore(OverallHighScoreModel addedScore) {
        scoreList.add(addedScore);
        Collections.sort(scoreList);
        printScores();
        updateJlabels();
    }

    public void updateFile(){
        try {
            PrintWriter writer = new PrintWriter(FILENAME);
            for(OverallHighScoreModel el: scoreList) {
                writer.println(el.getName()+","+formatter.format(el.getDate())+ "," + el.getScore()+",");
            }
            writer.close();
        }catch (FileNotFoundException e){

        }


        }

    }


