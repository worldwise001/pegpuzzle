package harbinPegSTN.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import harbinPegSTN.PegPuzzleModel;

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
			if (loc != PegPuzzleModel.CENTER_LOC)
				assertTrue("Initially should be a peg here: "+loc, model.isPegAtLocation(loc));
			else
				assertFalse("Initially, should not be a peg here: "+loc, model.isPegAtLocation(loc));
		}
	}

}
