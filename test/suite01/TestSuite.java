package suite01;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import suite01.Offset2DTest;
import suite01.TroopTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	Offset2DTest.class,
	TroopTest.class,
})

public class TestSuite {

}
