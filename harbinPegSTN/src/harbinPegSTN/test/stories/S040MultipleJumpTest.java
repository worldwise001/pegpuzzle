package harbinPegSTN.test.stories;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import harbinPegSTN.model.Peg;
import harbinPegSTN.model.SaveTheNetworkModel;

import org.junit.Before;
import org.junit.Test;


public class S040MultipleJumpTest {

	SaveTheNetworkModel model = null;
	
	@Before
	public void setUp() throws Exception {
		model = new SaveTheNetworkModel();
	}
	
	@Test
	public void testIsFutureJumpPossible()
	{
		assertFalse("Cannot start a jump outside the boundary", model.isFutureJumpPossible(0));
		assertFalse("Cannot start a jump outside the boundary", model.isFutureJumpPossible(34));
		
		model.processWhiteClick(6);
		model.setPeg(Peg.BLACK, 11);
		model.setPeg(Peg.NONE, 18);
		model.setPeg(Peg.NONE, 16);
		model.setPeg(Peg.BLACK, 9);
		
		assertTrue("More jumps possible", model.isFutureJumpPossible(6));
		model.makeMove(6, 18);
		//model.reverseTurn();
		assertFalse("No more jumps possible", model.isFutureJumpPossible(18));
		model.makeMove(18, 16);
		assertFalse("No more jumps possible", model.isFutureJumpPossible(16));
		assertFalse("No jumps possible", model.isFutureJumpPossible(18));
		model.makeMove(16, 4);
		assertFalse("No jumps possible", model.isFutureJumpPossible(4));
	}
	
	@Test
	public void testTogglePeg()
	{
		
	}
}
