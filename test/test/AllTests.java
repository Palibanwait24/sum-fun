package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		MoveTestValid.class,
		MoveTestInvalid.class,
		TileSumTestValid.class,
		TileSumTestInvalid.class
})
public class AllTests {

}
