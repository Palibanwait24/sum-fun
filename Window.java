package sumfun;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Window extends JFrame {

    private final int WINDOW_WIDTH = 680;
    private final int WINDOW_HEIGHT = 720;
    private final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int LOCATION_X = screensize.height / 3;
    private final int LOCATION_Y = (int) (screensize.width - (screensize.width * 0.97));

    private final JMenuBar menu; // menu for options and operations

    /**
     * Constructor for a Window object.
     */
    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(LOCATION_X, LOCATION_Y);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(true);

        menu = createMenu();
        setJMenuBar(menu);

        GamePane pane = new GamePane();
        pane.setFocusable(true);
        add(pane);
        pack();
    }

    private JMenuBar createMenu() {
        JMenuBar temp = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem n = new JMenuItem("New game"); // start new game
        n.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // start new game
            }
        });
        fileMenu.add(n);
        JMenuItem e = new JMenuItem("Exit"); // exit game
        e.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(e);
        temp.add(fileMenu);

        JMenu optionMenu = new JMenu("Option");
        JMenuItem viewHighScores = new JMenuItem("View high scores"); // view high scores in game
        viewHighScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // show hi scores
            }
        });
        optionMenu.add(viewHighScores);
        temp.add(optionMenu);

        return temp;
    }
}
