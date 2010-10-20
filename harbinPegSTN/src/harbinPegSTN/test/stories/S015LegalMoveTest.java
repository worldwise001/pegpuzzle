package harbinPegSTN.test.stories;


import static org.junit.Assert.*;

import java.awt.Point;

import harbinPegSTN.model.Model;
import harbinPegSTN.model.Peg;
import harbinPegSTN.model.Status;

import org.junit.Before;
import org.junit.Test;

/**
 * @author sarah
 * 
 * This class tests for basic model methods
 *
 */
public class S015LegalMoveTest {
	
	Model model;

	@Before
	public void setUp() throws Exception {
		model = new Model();
	}
	
	@Test
	public void testPegIDTo2DRepresentation()
	{
		Point p = model.pegIDToPoint(1);
		assertEquals("X coordinate of Peg 1 should be 0", 0, p.x);
		assertEquals("Y coordinate of Peg 1 should be 2", 2, p.y);
		
		p = model.pegIDToPoint(27);
		assertEquals("X coordinate of Peg 27 should be 4", 4, p.x);
		assertEquals("Y coordinate of Peg 27 should be 6", 6, p.y);
		
		p = model.pegIDToPoint(32);
		assertEquals("X coordinate of Peg 32 should be 6", 6, p.x);
		assertEquals("Y coordinate of Peg 32 should be 3", 3, p.y);
	}
	
	@Test
	public void test2DRepresentationToPegID()
	{
		assertEquals("Peg ID at (0,3) should be 2", 2, model.pointToPegID(new Point(0, 3)));
		assertEquals("Peg ID at (1,3) should be 5", 5, model.pointToPegID(new Point(1, 3)));
		assertEquals("Peg ID at (3,3) should be 17", 17, model.pointToPegID(new Point(3, 3)));

		assertEquals("Peg ID at (5,3) should be 29", 29, model.pointToPegID(new Point(5, 3)));
	}

	@Test
	public void testStatus(){
		assertEquals("Should be invalid in the begining",Status.INVALID,model.getStatus());
		model.setStatus(Status.WHITE_CLICK);
		assertEquals("Should be white click",Status.WHITE_CLICK,model.getStatus());
		
	}
	@Test 
	public void testTogglePeg(){
		assertFalse("no selection made yet, so toggle return false",model.togglePeg(0));
		assertTrue("now toggle peg at 19", model.togglePeg(19));
		assertFalse("now toggle peg at 17, should be false because not jump possible for base model",model.togglePeg(17));
		assertEquals("selected peg should be 19 now",19,model.getSelectedPeg());
		
	}
	@Test
	public void testIsPegAtLocation(){
		assertFalse("no peg yet",model.isPegAtLocation(17));
		model.setPeg(Peg.NORMAL, 17);
		assertTrue("have a peg at location 17",model.isPegAtLocation(17));
	}
	
	@Test 
	public void testStatusToInt(){
		assertEquals("true",3,Status.toInt(Status.WHITE_SLIDE));
		assertEquals("true",10,Status.toInt(Status.INVALID));
		assertEquals("true",7,Status.toInt(Status.BLACK_CLICK));

		assertEquals("true",1,Status.toInt(Status.WHITE_CLICK));
		assertEquals("true",2,Status.toInt(Status.WHITE_JUMP));
		assertEquals("true",5,Status.toInt(Status.WHITE_TURN));
		

		assertEquals("true",4,Status.toInt(Status.WHITE_REMOVED));
		assertEquals("true",6,Status.toInt(Status.BLACK_TURN));
		
	}
}
