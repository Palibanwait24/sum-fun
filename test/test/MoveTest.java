package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.*;

import model.GridModel;
import model.QueueModel;
import model.TileModel;
import sumfun.SumFun;

public class MoveTest {
	SumFun game;
	GridModel gm;
	TileModel[][] tileObj;
	QueueModel queueObj;

	@Before
	public void setup(){
		game = new SumFun();
		game.run(game);
		gm = game.grid;
		tileObj = gm.getGrid();
		queueObj = queueModelReflection(gm);
	}

	@Test
	public void topRightTest() {
		String tileExpected = String.valueOf(queueObj.getTopOfQueue());
		gm.move(0, 0);
		String tileActual = tileObj[0][0].getData();
		assertEquals(tileExpected, tileActual);
	}

	@Test
	public void bottomLeftTest(){
		String tileExpected = String.valueOf(queueObj.getTopOfQueue());
		gm.move(4, 4);
		String tileActual = tileObj[4][4].getData();
		assertEquals(tileExpected, tileActual);
	}

	// This could be replaced by a simple getter in the GridModel class if needed.
	public QueueModel queueModelReflection(GridModel gm) {
		
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
