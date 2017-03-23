package sumfun;

import java.awt.*;
import javax.swing.*;

public class QueuePane extends JPanel {

    private int[] queue; // queue to hold upcoming moves

    public QueuePane() {
        queue = new int[5];

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < queue.length; i++) {
            gbc.gridy = i;
            Tile temp = new Tile(true);
            add(temp, gbc);
            queue[i] = Integer.parseInt(temp.getData());
        }
       
    }
    public int getQueue(){
    	return queue[0];
    }
}
