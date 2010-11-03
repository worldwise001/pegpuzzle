package harbinPegSTN.test.stories;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import harbinPegSTN.model.Peg;
import harbinPegSTN.model.SaveTheNetworkModel;

import org.junit.Before;
import org.junit.Test;

public class S105ForceTurnTest {
SaveTheNetworkModel model = null;
	
	@Before
	public void setUp() throws Exception {
		model = new SaveTheNetworkModel();
	}
	
	@Test
	public void testForceTurn(){
		model.togglePeg(4);
		model.togglePeg(5);
		assertTrue("Start with black's turn, so try black move from 8 to 9", model.makeMove(8, 9));
		model.reverseTurn();
		assertTrue("White legal move from 4 to 10", model.makeMove(4, 10));
		model.reverseTurn();
		assertTrue("Now black's turn, legal move from 12 to 11",model.makeMove(12, 11));
		assertFalse("illegal balck's turn from 9 to 15", model.makeMove(9, 15));
		
	}
}
