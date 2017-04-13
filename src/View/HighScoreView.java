package View;

import Model.HighScoreModel;
import Model.OverallHighScoreModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
    private ArrayList<JLabel> top10;
    private  ArrayList<OverallHighScoreModel> scoreList;


    public void update(Observable o, Object arg) {

    }

    public HighScoreView(){
        top10 = new ArrayList<>();
        scoreList = new ArrayList<>(NUMBER_OF_SCORES);
        System.out.println(top10.size());
        setVisible(true);
        setLocation(LOCATION_X, LOCATION_Y);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle("High Score:");
        for(int i = 1; i < (NUMBER_OF_SCORES + 1 );i++){
            JLabel temp  = new JLabel(i+". ");
            top10.add(temp);
            add(top10.get(i-1));
        }

        setLayout(new GridLayout(10,3));
    }

    public void updateScores(OverallHighScoreModel score){
        if(score==null){
            return;
        }
        for(int i = 1 ; i < (NUMBER_OF_SCORES+1) ; i++){
            if(score.getScore() > scoreList.get(i-1).getScore()){
                JLabel temp  = new JLabel(i+". "+ score.toString());
                top10.get(i,temp);
            }else{
                return;
            }
        }

    }
}
