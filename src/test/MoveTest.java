package test;

import static org.junit.Assert.*;

import org.junit.Test;
import java.lang.reflect.*;

import model.GridModel;
import model.QueueModel;
import model.TileModel;
import sumfun.SumFun;

public class MoveTest {

	@Test
	public void test() {
		
		SumFun game = new SumFun();
		game.run(game);
		GridModel gm = game.grid;
		TileModel[][] tileObj = gm.getGrid();
		QueueModel queueObj = queueModelReflection(gm);
		
		// Theoretically this test should work but I'm not sure why move() isn't placing the tile.
		gm.move(0, 0);
		
		String tileActual = tileObj[0][0].getData();
		String tileExpected = String.valueOf(queueObj.getTopOfQueue());
		
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
