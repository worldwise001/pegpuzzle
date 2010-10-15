package harbinPegSTN.test.stories;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import harbinPegSTN.model.Peg;
import harbinPegSTN.model.PegPuzzleModel;

import org.junit.Before;
import org.junit.Test;

/**
 * @author sarah
 * 
 * This class tests for the "016 Legal Move" story for the Peg Puzzle Game
 *
 */
public class S016LegalMoveTest {
	
	PegPuzzleModel model = null;
	
	@Before
	public void setUp() throws Exception {
		model = new PegPuzzleModel();
	}
	
	@Test
	public void testStartingBoard() {
		for (int loc = 1; loc < 34; loc++)
		{
			if (loc == PegPuzzleModel.CENTER_LOC)
				assertEquals("Peg at "+loc+" should be none", Peg.NONE, model.getPeg(loc));
			else
				assertEquals("Initially, should be a peg here: "+loc, Peg.NORMAL, model.getPeg(loc));
		}
	}
	@Test 
	public void testReset(){
		model.setPeg(Peg.NORMAL, 17);
		assertEquals("There is a peg at Location 17",Peg.NORMAL,model.getPeg(17));
		model.reset();
		assertEquals("There is no peg at Location 17 now",Peg.NONE,model.getPeg(17));
	}
	@Test
	public void testCheckHop(){
		assertFalse("Illegal hop from 3 to 2", model.checkHop(3, 2));
		assertFalse("Illegal hop from 6 to 7", model.checkHop(6, 7));
		assertFalse("Illegal hop from 31 to 32", model.checkHop(31, 32));
	}

	@Test
	public void testCheckJump () {
		assertTrue("Legal jump from 3 to 1", model.checkJump(3, 1));
		assertTrue("Legal jump from 3 to 11", model.checkJump(3, 11));
		assertTrue("Legal jump from 10 to 2", model.checkJump(10, 2));
		assertTrue("Legal jump from 5 to 17", model.checkJump(5, 17));
		assertTrue("Legal jump from 23 to 25", model.checkJump(23, 25));
		assertFalse("Illegal jump from 3 to 4", model.checkJump(3, 4));
		assertFalse("Illegal jump from 8 to 6", model.checkJump(8, 6));
		assertFalse("Illegal jump out of boundary 2 to 0", model.checkJump(2, 0));
		assertFalse("Illegal jump out of boundary 32 to 24", model.checkJump(32, 34));
	}
	
	@Test
	public void testCheckMove () {
		assertFalse("Ilegal move from 3 to 1", model.checkMove(3, 1));
		assertFalse("Ilegal move from 3 to 11", model.checkMove(3, 11));
		assertFalse("Ilegal move from 10 to 2", model.checkMove(10, 2));
		assertTrue("Legal move from 5 to 17", model.checkMove(5, 17));
		assertFalse("Ilegal move from 23 to 25", model.checkMove(23, 25));
		assertFalse("Illegal move from 3 to 4", model.checkMove(3, 4));
		assertFalse("Illegal move from 8 to 6", model.checkMove(8, 6));
		assertFalse("Illegal move out of boundary 2 to 0", model.checkMove(2, 0));
		assertFalse("Illegal move out of boundary 32 to 24", model.checkMove(32, 34));
	}
	@Test
	public void testPegPuzzleMove() {
		assertTrue("Legal move from 5 to 17", model.makeMove(5, 17));
		
		
		assertTrue("Legal move from 12 to 10", model.makeMove(model.pegIDToPoint(12), model.pegIDToPoint(10)));
		assertTrue("Legal move from 26 to 12", model.makeMove(26, 12));
		assertTrue("Legal move from 24 to 26", model.makeMove(24, 26));
		
		assertFalse("Illegal move from 8 to 10 (peg-to-peg)", model.makeMove(8, 10));
		assertFalse("Illegal move from 24 to 10 (blank-to-peg)", model.makeMove(24, 10));
	}

}
