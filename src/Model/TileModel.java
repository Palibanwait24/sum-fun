package Model;

import java.util.*;
import java.awt.*;
import javax.swing.*;

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
        setFont(new Font("Arial", Font.PLAIN, 24));
        setBackground(this.cellColor);
        setForeground(getRandomColor());

        if (fill) {
            Random rand = new Random();
            int n = rand.nextInt(9) + 1; // generate random number in range [1,9]
            data = "" + n;
        }
        
        setDataString(data);

    }
    /// set number from a string
    public void setDataString(String s){
        setText("" + s);
        data="" + s;
    }
    //set data from a integer
    public void setDataInteger(int i){
        setText("" + i);
        data="" + i;
    }
    //get data in String form
    public String getData(){
    	return data;
    }
    
    
    public Color getRandomColor() {
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
