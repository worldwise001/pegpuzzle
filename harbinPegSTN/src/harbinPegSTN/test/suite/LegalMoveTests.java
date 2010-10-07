package harbinPegSTN.test.suite;
import harbinPegSTN.test.stories.S015LegalMoveTest;
import harbinPegSTN.test.stories.S016LegalMoveTest;
import harbinPegSTN.test.stories.S017LegalMoveTest;
import harbinPegSTN.test.stories.S110WhiteLegalMoveTest;
import harbinPegSTN.test.stories.S115BlackLegalMoveTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	S015LegalMoveTest.class,
	S016LegalMoveTest.class,
	S017LegalMoveTest.class,
	S110WhiteLegalMoveTest.class,
	S115BlackLegalMoveTest.class
})


public class LegalMoveTests {
	
}
