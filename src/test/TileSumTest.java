package test;

import static org.junit.Assert.*;

import org.junit.Test;

import sumfun.SumFun;

public class TileSumTest {

	@Test
	public void test() {
		SumFun game = new SumFun();
		game.run(game);

		//assertEquals(expected, actual);
	}

}
