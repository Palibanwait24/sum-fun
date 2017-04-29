package test;

import static org.junit.Assert.*;

import org.junit.Test;
import java.lang.reflect.*;
import java.util.Random;

import model.GridModel;
import model.TileModel;
import sumfun.SumFun;

public class TileSumTest {

	@Test
	public void summingTest() {
		
		SumFun game = new SumFun();
		game.run(game);
		GridModel gm = game.grid;
		TileModel[][] tileObj = gm.getGrid();
		Random rng = new Random();
		
		for (int i = 0; i < tileObj.length; i++) {
			for (int j = 0; j < tileObj.length; j++) {
				if (tileObj[i][j].getData() == "" && i * j != tileObj.length * tileObj.length) {
					gm.move(i, j);
				}
			}
		}
		
		int expected = upperLeftCornerSum(gm, tileObj);
		int actual = reflection(gm, 0, 0);
		
		assertEquals(expected, actual);
		System.out.println("Passed.");
		
		int middleIndex = (int) Math.ceil(tileObj.length/2.0) - 1;
		
		expected = middleSum(gm, tileObj, middleIndex);
		actual = reflection(gm, middleIndex, middleIndex);
		
		assertEquals(expected, actual);
		System.out.println("Passed.");
		
		int randomX = rng.nextInt(tileObj.length);
		int randomY = rng.nextInt(tileObj.length);
		
		expected = arbitrarySum(gm, tileObj, randomX, randomY);
		actual = reflection(gm, randomX, randomY);
		
		assertEquals(expected, actual);
		System.out.println("Passed.");
		
	}
	
	public int upperLeftCornerSum(GridModel gm, TileModel[][] tileObj) {
		
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
	
	public int middleSum(GridModel gm, TileModel[][] tileObj, int middleIndex) {
		
		int sum = 0;
		String data = "";
		for(int i = middleIndex - 1; i < middleIndex + 2; i++) {
			for (int j = middleIndex - 1; j < middleIndex + 2; j++) {
				if (i >= 0 && j >= 0) {
					data = tileObj[i][j].getData();
				}
				else
					continue;
				if (!(i == middleIndex && j == middleIndex) && (data != "")) {
					sum += Integer.parseInt(data);
					System.out.println(i + ", " + j + ": " + data);
				}
			}
		}
		return sum;
	}
	
	public int arbitrarySum(GridModel gm, TileModel[][] tileObj, int randomX, int randomY) {
		
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
