package harbinPegSTN.test.stories;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import harbinPegSTN.model.Peg;
import harbinPegSTN.model.SaveTheNetworkModel;

import org.junit.Before;
import org.junit.Test;

public class S110WhiteLegalMoveTest {

	SaveTheNetworkModel model = null;
	
	@Before
	public void setUp() throws Exception {
		model = new SaveTheNetworkModel();
	}
	/**
	 * white moves
	 * jump or hop
	 */
	@Test
	public void testWhiteMove(){
		model.togglePeg(4);
		model.togglePeg(5);
		model.reverseTurn();
		assertTrue("check white hop from 4 to 9", model.makeMove(4, 9));
		assertFalse("check illegal hop from 9 to 16",model.makeMove(9, 16));
		assertFalse("check illegal jump from 9 to 23",model.makeMove(9, 23));
		model.setPeg(Peg.NONE, 23);
		assertTrue("check white jump from 9 to 23",model.makeMove(9, 23));
	}
}
