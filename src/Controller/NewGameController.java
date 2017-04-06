package Controller;

import Model.GridModel;
import Model.QueueModel;
import Model.TileModel;
import View.*;
import sumfun.SumFun;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by AmritPal on 4/4/2017.
 */
public class NewGameController implements ActionListener {
    SumFun oldGame;
    public NewGameController(SumFun oldGame) {
        this.oldGame = oldGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        oldGame.mainView.setVisible(false);
        oldGame.grid = new GridModel(oldGame);
        oldGame.queue = new QueueModel(oldGame);
        oldGame.mainView = new WindowView(oldGame,oldGame.grid, oldGame.queue);
        oldGame.mainView.addObserver(oldGame.grid);
        oldGame.mainView.addObserver(oldGame.queue);
        oldGame.grid.start();// do we need
        oldGame.queue.start();// do we need
        oldGame.mainView.setVisible(true);

    }

}
