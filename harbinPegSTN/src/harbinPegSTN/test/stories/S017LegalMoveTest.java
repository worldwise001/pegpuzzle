package harbinPegSTN.test.stories;

import static org.junit.Assert.*;
import harbinPegSTN.model.SaveTheNetworkModel;
import harbinPegSTN.model.Model.Peg;

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
	public void testReset(){
		model.setPegAt(Peg.WHITE, 10);
		assertEquals("There is a peg at Location 10",Peg.WHITE,model.getPeg(10));
		model.reset();
		assertEquals("There is no peg at Location 10 now",Peg.NONE,model.getPeg(10));
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
	}
	@Test
	public void testCheckMove () {
		model.setPegAt(Peg.WHITE, 4);
		model.setPegAt(Peg.WHITE, 9);
		assertFalse("Ilegal move from 3 to 1", model.checkMove(3, 1));
		assertTrue("Legal move from 17 to 10", model.checkMove(17, 10));
		assertFalse("Ilegal move from 4 to 16", model.checkMove(4, 16));
		
		assertFalse("Ilegal move from 9 to 23", model.checkMove(9, 23));
		model.setPegAt(Peg.NONE, 23);
		assertTrue("Legal move from 9 to 23 now",model.checkMove(9, 23));
		
	}
	@Test
	public void testSTNMove() {
		model.setPegAt(Peg.WHITE, 4);
		model.setPegAt(Peg.WHITE, 6);
		
		assertTrue("Legal move from 17 to 10", model.makeMove(17, 10));

		assertTrue("Legal move from 16 to 9", model.makeMove(16, 9));
		
		assertTrue("Legal move from 4 to 16", model.makeMove(4, 16));
		
		assertTrue("Legal move from 8 to 9", model.makeMove(8, 9));
		
		
		assertFalse("Illegal move from 10 to 17 (black move backward)", model.makeMove(10, 17));
		assertFalse("Illegal move from 6 to 18 (white tried to jump)", model.makeMove(6, 18));
	}
}
