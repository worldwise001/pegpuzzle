package harbinPegSTN.tests;


import static org.junit.Assert.*;
import harbinPegSTN.Model.Peg;
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
			if (loc == PegPuzzleModel.CENTER_LOC)
				assertEquals("Peg at "+loc+" should be none", Peg.NONE, model.getPeg(loc));
			else
				assertEquals("Initially, should be a peg here: "+loc, Peg.NORMAL, model.getPeg(loc));
		}
	}

}
