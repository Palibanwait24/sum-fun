package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.reflect.*;

import model.GridModel;
import model.QueueModel;
import model.TileModel;
import sumfun.SumFun;

public class MoveTestInvalid {
    static SumFun game;
    static GridModel gm;
    static TileModel[][] tileObj;
    static QueueModel queueObj;
    static int expected = -1;

    @BeforeClass
    public static void setup(){
        game = new SumFun();
        game.run(game);
        gm = game.grid;
        tileObj = gm.getGrid();
        queueObj = queueModelReflection(gm);
    }

    @Test
    public void topRightTest() {
        int row = -1;
        int col = 8;
        int  actual = gm.move(row,col);
        assertEquals(expected, actual);
    }

    @Test
    public void bottomLeftTest(){
        int row = -5;
        int col = 5;
        int  actual = gm.move(row,col);
        assertEquals(expected, actual);;
    }
    @Test
    public void middleTest(){
        int row = 10;
        int col = 10;
        int  actual = gm.move(row,col);
        assertEquals(expected, actual);
    }

    // This could be replaced by a simple getter in the GridModel class if needed.
    public static QueueModel queueModelReflection(GridModel gm) {

        QueueModel queueObj = null;
        Field queueField = null;

        try {
            queueField = gm.getClass().getDeclaredField("queueModel");
            queueField.setAccessible(true);

        } catch (NoSuchFieldException e) {
            fail("Field does not exist in the given class.");
        } catch (SecurityException e) {
            fail("Field security exception met.");
        }

        try {
            queueObj = (QueueModel) queueField.get(gm);
        } catch (IllegalArgumentException e) {
            fail("Illegal arguements passed.");
        } catch (IllegalAccessException e) {
            fail("Illegal access exception met.");
        }

        return queueObj;

    }

}
