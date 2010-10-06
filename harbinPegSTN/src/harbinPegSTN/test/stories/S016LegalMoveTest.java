package harbinPegSTN.test.stories;


import static org.junit.Assert.*;
import harbinPegSTN.model.Model.Peg;
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
	public void testCheckMove () {
		assertTrue("Legal move from 3 to 1", model.checkMove(3, 1));
		assertTrue("Legal move from 3 to 11", model.checkMove(3, 11));
		assertTrue("Legal move from 10 to 2", model.checkMove(10, 2));
		assertTrue("Legal move from 17 to 5", model.checkMove(17, 5));
		assertTrue("Legal move from 23 to 25", model.checkMove(23, 25));
		assertFalse("Illegal move from 3 to 4", model.checkMove(3, 4));
		assertFalse("Illegal move from 8 to 6", model.checkMove(8, 6));
		assertFalse("Illegal move out of boundary 2 to 0", model.checkMove(2, 0));
		assertFalse("Illegal move out of boundary 32 to 24", model.checkMove(32, 34));
	}
	
	@Test
	public void testPegPuzzleMove() {
		assertTrue("Legal move from 5 to 17", model.makeMove(5, 17));
		assertTrue("Legal move from 12 to 10", model.makeMove(12, 10));
		assertTrue("Legal move from 26 to 12", model.makeMove(26, 12));
		assertTrue("Legal move from 24 to 26", model.makeMove(24, 26));
		
		assertFalse("Illegal move from 8 to 10 (peg-to-peg)", model.makeMove(8, 10));
		assertFalse("Illegal move from 24 to 10 (blank-to-peg)", model.makeMove(24, 10));
	}

}
