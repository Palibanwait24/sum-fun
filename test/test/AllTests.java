package test;

		import org.junit.*;
		import org.junit.runner.RunWith;
		import org.junit.runners.Suite;
		import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
		MoveTest.class,
		TileSumTest.class
})
public class AllTests {

}