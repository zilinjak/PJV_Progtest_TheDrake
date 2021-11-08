package suite02;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import suite02.TroopTileTest;
import test.suite02.BoardTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	TroopTileTest.class,
	BoardTest.class
})

public class TestSuite {

}
