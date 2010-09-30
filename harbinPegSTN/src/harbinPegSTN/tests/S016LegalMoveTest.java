package harbinPegSTN.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import harbinPegSTN.PegPuzzleModel;

import java.awt.Point;

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
	
//	@Test
//	public void testStandardStartingBoard() {
//		model = new Model();
//		for (int loc = 1; loc < model.size(); loc++)
//		{
//			if (loc != Model.CENTER_LOC)
//				assertTrue("Initially should be a peg here: "+loc, model.isPegAtLocation(loc));
//			else
//				assertFalse("Initially, should not be a peg here: "+loc, model.isPegAtLocation(loc));
//		}
//	}
	
//	@Test
//	public void testPegIDTo2DRepresentation()
//	{
//		model = new Model();
//		
//		Point p = model.pegIDToPoint(1);
//		assertEquals("X coordinate of Peg 1 should be 0", 0, p.x);
//		assertEquals("Y coordinate of Peg 1 should be 2", 2, p.y);
//		
//		p = model.pegIDToPoint(27);
//		assertEquals("X coordinate of Peg 27 should be 4", 4, p.x);
//		assertEquals("Y coordinate of Peg 27 should be 6", 6, p.y);
//		
//		p = model.pegIDToPoint(32);
//		assertEquals("X coordinate of Peg 32 should be 6", 6, p.x);
//		assertEquals("Y coordinate of Peg 32 should be 3", 3, p.y);
//	}
//	
//	@Test
//	public void test2DRepresentationToPegID()
//	{
//		model = new Model();
//		
//		int peg = model.pointToPegID(0, 3);
//		assertEquals("Peg ID at (0,3) should be 2", 2, peg);
//	}

}
