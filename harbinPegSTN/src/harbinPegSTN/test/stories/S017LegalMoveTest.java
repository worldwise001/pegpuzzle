package harbinPegSTN.test.stories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import harbinPegSTN.model.Peg;
import harbinPegSTN.model.SaveTheNetworkModel;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;


public class S017LegalMoveTest {

	SaveTheNetworkModel model = null;
	
	@Before
	public void setUp() throws Exception {
		model = new SaveTheNetworkModel();
	}
	
	@Test
	public void testStartingBoard() {
		for (int loc = 1; loc < 34; loc++)
		{
			if ((loc >= 1 && loc <= 6) || (loc >= 9 && loc <= 11))
				assertEquals("Should not be a peg in the admin area ("+loc+")", Peg.NONE, model.getPeg(loc));
			else
				assertEquals("Peg should be black in the bot area ("+loc+")", Peg.BLACK, model.getPeg(loc));
		}
	}
	
	@Test
	public void testReset(){
		model.placeWhite(4);
		model.placeWhite(6);
		
		model.makeMove(4, 5);
		model.reverseTurn();
		
		model.reset();
		
		for (int i = 1; i < 34; i++) {
			if ((i >= 1 && i <= 6) || (i >= 9 && i <= 11))
				assertEquals("Should not be a peg in the admin area ("+i+")", Peg.NONE, model.getPeg(i));
			else
				assertEquals("Peg should be black in the bot area ("+i+")", Peg.BLACK, model.getPeg(i));
		}
	}
	
	@Test
	public void testReverseTurn() {
		assertEquals("Starts off as white's turn", Peg.WHITE, model.whoseTurn());
		model.reverseTurn();
		assertEquals("Now is black's turn", Peg.BLACK, model.whoseTurn());
		model.reverseTurn();
		assertEquals("Now is white's turn", Peg.WHITE, model.whoseTurn());
	}
	
	@Test
	public void testCheckHop(){
		assertTrue("Legal hop from 12 to 11", model.checkHop(12, 11));
		assertFalse("Illegal hop from 6 to 7", model.checkHop(6, 7));
		assertFalse("Illegal hop from 27 to 28", model.checkHop(27, 28));
	}

	@Test
	public void testCheckJump () {
		assertTrue("Legal jump from 3 to 1", model.checkJump(3, 1));
		assertTrue("Legal jump from 3 to 9", model.checkJump(3, 9));
		assertTrue("Legal jump from 10 to 22", model.checkJump(10, 22));
		assertTrue("Legal jump from 5 to 17", model.checkJump(5, 17));
		assertTrue("Legal jump from 23 to 25", model.checkJump(23, 25));
		assertFalse("Illegal jump from 3 to 4", model.checkJump(3, 4));
		assertFalse("Illegal jump from 8 to 6", model.checkJump(8, 6));
		assertFalse("Illegal jump out of boundary 2 to 0", model.checkJump(2, 0));
		assertFalse("Illegal jump out of boundary 32 to 24", model.checkJump(32, 34));
		
		Point p1 = new Point();
		Point p2 = new Point();
		
		p1.setLocation(0, 2);
		p2.setLocation(0, 4);
		assertTrue("Legal jump from 3 to 1", model.checkJump(p1, p2));
		
		p1.setLocation(0, 4);
		p2.setLocation(2, 2);
		assertTrue("Legal jump from 3 to 9", model.checkJump(p1, p2));
		
		p1.setLocation(2, 3);
		p2.setLocation(4, 1);
		assertTrue("Legal jump from 10 to 22", model.checkJump(p1, p2));
		
		p1.setLocation(1, 3);
		p2.setLocation(3, 3);
		assertTrue("Legal jump from 5 to 17", model.checkJump(p1, p2));
		
		p1.setLocation(4, 2);
		p2.setLocation(4, 4);
		assertTrue("Legal jump from 23 to 25", model.checkJump(p1, p2));
		
		p1.setLocation(0, 4);
		p2.setLocation(1, 2);
		assertFalse("Illegal jump from 3 to 4", model.checkJump(p1, p2));
		
		p1.setLocation(2, 1);
		p2.setLocation(1, 4);
		assertFalse("Illegal jump from 8 to 6", model.checkJump(p1, p2));
	}
	
	@Test
	public void testCheckMove () {
		model.setPeg(Peg.WHITE, 4);
		model.setPeg(Peg.WHITE, 9);
		assertFalse("Ilegal move from 3 to 1", model.checkMove(3, 1));
		assertTrue("Legal move from 17 to 10", model.checkMove(17, 10));
		assertFalse("Ilegal move from 4 to 16", model.checkMove(4, 16));
		
		assertFalse("Ilegal move from 9 to 23", model.checkMove(9, 23));
		model.setPeg(Peg.NONE, 23);
		assertTrue("Legal move from 9 to 23 now",model.checkMove(9, 23));
		
	}
	
	@Test
	public void testSTNMove() {
		model.placeWhite(4);
		assertTrue("Legal move from 4 to 9", model.makeMove(model.pegIDToPoint(4), model.pegIDToPoint(9)));
		assertTrue("Legal move from 9 to 5", model.makeMove(model.pegIDToPoint(9), model.pegIDToPoint(5)));
		
		model.reverseTurn();
		
		assertTrue("Legal move from 8 to 9", model.makeMove(8, 9));
		assertTrue("Legal move from 9 to 4", model.makeMove(9, 4));
		assertFalse("Illgal move from 4 to 10",model.makeMove(4, 9));
	}
	
	@Test
	public void testWhitePlacement()
	{
		for (int i = 1; i <= 6; i++)
		{
			assertTrue("Able to place white on "+i, model.placeWhite(i));
			model.setPeg(Peg.NONE, i);
		}
		
		for (int i = 9; i <= 11; i++)
		{
			assertTrue("Able to place white on "+i, model.placeWhite(i));
			model.setPeg(Peg.NONE, i);
		}
	}
	
}
