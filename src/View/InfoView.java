package View;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class InfoView extends JPanel {
	
	// data fields
	private int score = 0;
	private int moves_rem = 50;
	private long time = 0; // later
	
	
	// design fields
	private JLabel score_label, time_label, moves_label;
	private JLabel score_holder, time_holder, moves_holder;
	private JButton hint, refresh;
	
	public InfoView() {
		
		// construct info pane layout
		setLayout(new BoxLayout(this, 0));
		Box container = Box.createVerticalBox();
		Box top = Box.createHorizontalBox();
		Box bottom = Box.createHorizontalBox();
		
		// pane to hold statistics (score, moves, time)
		JPanel statsPane = new JPanel();
		statsPane.setLayout(new GridLayout(3, 2));
		
		score_label = new JLabel("Score: ");
		moves_label = new JLabel("Moves remaining: ");
		time_label = new JLabel("Time: ");
		
		score_holder = new JLabel("" + score);
		moves_holder = new JLabel("" + moves_rem);
		time_holder = new JLabel("--:--");
		
		//build top pane
		statsPane.add(score_label);
		statsPane.add(score_holder);
		statsPane.add(moves_label);
		statsPane.add(moves_holder);
		statsPane.add(time_label);
		statsPane.add(time_holder);
		top.add(statsPane);
		
		// panel to hold buttons
		JPanel buttonPane = new JPanel();
		//buttonPane.setLayout();
		hint = new JButton("Hint");
		refresh = new JButton("Refresh queue");
		
		hint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// hint button pressed
			}
		});
		
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// refresh button pressed
			}
		});
				
		// build button panel
		buttonPane.add(hint);
		buttonPane.add(refresh);
		bottom.add(buttonPane);
		
		// build info pane
		container.add(top);
		container.add(bottom);
		add(container);
	}
	
}
