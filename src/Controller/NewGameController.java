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
public class NewGameController implements ActionListener{

    public NewGameController() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        SumFun.mainView.setVisible(false);
        SumFun.grid = new GridModel();
        SumFun.queue = new QueueModel();
        SumFun.mainView = new WindowView(SumFun.grid, SumFun.queue);
        SumFun.mainView.addObserver(SumFun.grid);
        SumFun.mainView.addObserver(SumFun.queue);
        SumFun.grid.start();// do we need
        SumFun.queue.start();// do we need
        SumFun.mainView.setVisible(true);
    }


}
