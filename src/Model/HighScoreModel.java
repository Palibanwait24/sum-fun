package Model;

/**
 * Created by Cameron on 4/12/2017.
 */
public class HighScoreModel {
    private String name;

    public HighScoreModel(String n){
        name = n;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "";
    }
}
