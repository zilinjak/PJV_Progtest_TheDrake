package suite04;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import suite04.ActionsTest;
import suite04.GameStateTest;
import suite04.ActionsTest;
import suite04.GameStateTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	ActionsTest.class,
	GameStateTest.class
})

public class TestSuite {

}
