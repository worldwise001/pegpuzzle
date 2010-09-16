package harbinPegSTN;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;


public class PegPuzModelTest {
	
	Random generator = new Random();

	@Test
	public void testStandardStartingBoard() {
		PegPuzModel model = new PegPuzModel();
		for (int loc = 1; loc < model.size(); loc++)
		{
			if (loc != PegPuzModel.CENTER_LOC)
				assertTrue("Initially should be a peg here: "+loc, model.isPegAtLocation(loc));
			else
				assertFalse("Initially, should not be a peg here: "+loc, model.isPegAtLocation(loc));
		}
	}
	
	@Test
	public void testForHorizontalMoveFromStartingBoard() {
		PegPuzModel model = new PegPuzModel();
		int firstClick = 15;
		int secondClick = 17;
		assertTrue("Moving from #"+firstClick+" to #"+secondClick, model.movePeg(firstClick, secondClick));
		assertTrue("Peg exists at #"+secondClick, model.isPegAtLocation(secondClick));
		assertFalse("Peg does not exist at #"+firstClick, model.isPegAtLocation(firstClick));
		assertFalse("Peg does not exist at #"+(firstClick+1), model.isPegAtLocation(firstClick+1));
	}
	
	@Test
	public void testTypeOfMoveRight() {
		PegPuzModel model = new PegPuzModel();
		assertEquals("Move from #15 to #17 should be right",
				model.checkMoveDirection(15,17),
				PegPuzModel.DIRECTION_RIGHT);
		
		assertEquals("Move from #3 to #5 should be invalid",
				model.checkMoveDirection(3,5),
				PegPuzModel.DIRECTION_INVALID);
		
		assertEquals("Move from #13 to #15 should be invalid",
				model.checkMoveDirection(13,15),
				PegPuzModel.DIRECTION_INVALID);
		
		assertEquals("Move from #12 to #14 should be invalid",
				model.checkMoveDirection(12,14),
				PegPuzModel.DIRECTION_INVALID);
		
		assertEquals("Move from #11 to #13 should be right",
				model.checkMoveDirection(11,13),
				PegPuzModel.DIRECTION_RIGHT);
		
		assertEquals("Move from #29 to #31 should be invalid",
				model.checkMoveDirection(29,31),
				PegPuzModel.DIRECTION_INVALID);
	}
	
	@Test
	public void testTypeOfMoveLeft() {
		PegPuzModel model = new PegPuzModel();
		assertEquals("Move from #17 to #15 should be left",
				model.checkMoveDirection(17,15),
				PegPuzModel.DIRECTION_LEFT);
		
		assertEquals("Move from #5 to #3 should be invalid",
				model.checkMoveDirection(5,3),
				PegPuzModel.DIRECTION_INVALID);
		
		assertEquals("Move from #15 to #13 should be invalid",
				model.checkMoveDirection(15,13),
				PegPuzModel.DIRECTION_INVALID);
		
		assertEquals("Move from #14 to #12 should be invalid",
				model.checkMoveDirection(14,12),
				PegPuzModel.DIRECTION_INVALID);
		
		assertEquals("Move from #13 to #11 should be left",
				model.checkMoveDirection(13,11),
				PegPuzModel.DIRECTION_LEFT);
		
		assertEquals("Move from #31 to #29 should be invalid",
				model.checkMoveDirection(31,29),
				PegPuzModel.DIRECTION_INVALID);
	}
	
	@Test
	public void testTypeOfMoveUp() {
		
	}
	
}
