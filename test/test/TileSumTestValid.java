package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.reflect.*;
import java.util.Random;

import model.GridModel;
import model.TileModel;
import sumfun.SumFun;

public class TileSumTestValid {

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
	public void upperLeftTest() {
		int expected = upperLeftCornerSum();
		int actual = reflection(0, 0);
		
		assertEquals(expected, actual);
		System.out.println("Passed.");
	}
	
	@Test
	public void middleTest() {
		int middleIndex = (int) Math.ceil(tileObj.length/2.0) - 1;
		int expected = arbitrarySum(middleIndex, middleIndex);
		int actual = reflection(middleIndex, middleIndex);
		
		assertEquals(expected, actual);
		System.out.println("Passed.");
	}
	
	@Test
	public void arbitraryTest() {
		int randomX = rng.nextInt(tileObj.length);
		int randomY = rng.nextInt(tileObj.length);
		int expected = arbitrarySum(randomX, randomY);
		int actual = reflection(randomX, randomY);
		
		assertEquals(expected, actual);
		System.out.println("Passed.");
	}
	
	public int upperLeftCornerSum() {
		
		int sum = 0;
		String data = "";
		for(int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				data = tileObj[i][j].getData();
				if (!(i == 0 && j == 0) && (data != "")) {
					sum += Integer.parseInt(data);
					System.out.println(i + ", " + j + ": " + data);
				}
			}
		}
		return sum;
	}
	
	public int arbitrarySum(int randomX, int randomY) {
		
		int sum = 0;
		String data = "";
		for(int i = randomX - 1; i < randomX + 2; i++) {
			for (int j = randomY - 1; j < randomY + 2; j++) {
				if ((i >= 0 && j >= 0) && (i < tileObj.length && j < tileObj.length)) {
					data = tileObj[i][j].getData();
				}
				else
					continue;
				if (!(i == randomX && j == randomY) && (data != "")) {
					sum += Integer.parseInt(data);
					System.out.println(i + ", " + j + ": " + data);
				}
			}
		}
		return sum;
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
