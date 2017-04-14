package Model;
import java.util.*;
/**
 * Created by Cameron on 4/12/2017.
 */
public class OverallHighScoreModel extends HighScoreModel implements Comparable<OverallHighScoreModel> {
    private int score;

    public OverallHighScoreModel(String name,  Date d,int score){
        super(name,d);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return super.toString() + " " + score;
    }

    @Override
    public int compareTo(OverallHighScoreModel other) {
        return this.score > other.score ? -1 : this.score < other.score ? 1 : 0;
    }
}
