package test;

import static org.junit.Assert.*;

import org.junit.Test;
import java.lang.reflect.*;

import model.GridModel;
import sumfun.SumFun;

public class TileSumTest {

	@Test
	public void test() {
		
		SumFun game = new SumFun();
		game.run(game);
		GridModel gm = game.grid;
		
		int actual = reflection(gm, 5, 3);
		int expected = -1;
		
		assertEquals(expected, actual);
	}
	
	// This method is definitely needed since tileSum() is protected.
	public int reflection(GridModel gm, int arg1, int arg2) {
		
		Method tileSum = null;
		Class<?> para[] = new Class[2];
		para[0] = int.class;
		para[1] = int.class;
		Object args[] = new Object[2];
		args[0] = arg1;
		args[1] = arg2;
		int result = 0;
		
		try {
			tileSum = gm.getClass().getDeclaredMethod("tileSum", para);
			tileSum.setAccessible(true);
		} catch (NoSuchMethodException e) {
			fail("No such method inside the GridModel class.");
		} catch (SecurityException e) {
			fail("Security exception met.");
		}
		
		try {
			result = (int) tileSum.invoke(gm, args);
		} catch (IllegalAccessException e) {
			fail("Illegal access exception met.");
		} catch (IllegalArgumentException e) {
			fail("Illegal arguments passed into method parameters.");
		} catch (InvocationTargetException e) {
			fail("Invocation target exception met.");
		}
		
		return result;
		
	}

}