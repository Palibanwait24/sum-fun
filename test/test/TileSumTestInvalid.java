package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.reflect.*;
import java.util.Random;

import model.GridModel;
import model.TileModel;
import sumfun.SumFun;

public class TileSumTestInvalid {

	static SumFun game;
	static GridModel gm;
	static TileModel[][] tileObj;
	static Random rng;
	
	@BeforeClass
	public static void setup(){
		game = new SumFun();
		game.run(game);
		gm = game.grid;
		tileObj = gm.getGrid();
		rng = new Random();
	}
	
	@Test
	public void outOfBoundsTest() {
		int expected = -1;
		int actual = reflection(-10, -10);
		
		assertEquals(expected, actual);
		System.out.println("Passed.");
	}
	
	@Test
	public void zeroTest() {
		int middleIndex = (int) Math.ceil(tileObj.length/2.0) - 1;
		fillBoard("0");
		
		int expected = 0;
		int actual = reflection(middleIndex, middleIndex);
		
		assertEquals(expected, actual);
		System.out.println("Passed.");
	}
	
	@Test
	public void emptyTest() {
		int middleIndex = (int) Math.ceil(tileObj.length/2.0) - 1;
		fillBoard("");
		
		int expected = -1;
		int actual = reflection(middleIndex, middleIndex);
		
		assertEquals(expected, actual);
		System.out.println("Passed.");
	}
	
	public void fillBoard(String data) {
		for (int i = 0; i < tileObj.length; i++) {
			for (int j = 0; j < tileObj.length; j++) {
				tileObj[i][j].setData(data);
			}
		}
	}
	
	// This method is definitely needed since tileSum() is protected.
	public int reflection(int arg1, int arg2) {
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
