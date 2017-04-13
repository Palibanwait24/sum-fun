package Model;

/**
 * Created by Cameron on 4/12/2017.
 */
public class OverallHighScoreModel extends HighScoreModel {
    private int score;

    public OverallHighScoreModel(String name, int score){
        super(name);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return super.toString() + " " + score;
    }
}
