package harbinPegSTN.test.stories;


import static org.junit.Assert.*;

import java.awt.Point;

import harbinPegSTN.model.Model;

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
		assertEquals("Peg ID at (0,3) should be 2", 2, model.pointToPegID(0, 3));
		assertEquals("Peg ID at (1,3) should be 5", 5, model.pointToPegID(1, 3));
		assertEquals("Peg ID at (3,3) should be 17", 17, model.pointToPegID(3, 3));
	}
	
	@Test
	public void testMiddlePeg()
	{
		assertEquals("Peg between 18 and 16 should be 17", 17, model.getMiddlePeg(18, 16));
		assertEquals("Peg between 9 and 25 should be 17 (allow diag)", 17, model.getMiddlePeg(9, 25, true));
		assertEquals("Peg between 9 and 25 should be -1 (disallow diag)", -1, model.getMiddlePeg(9, 25));
		assertEquals("Peg between 9 and 30 should be -1", -1, model.getMiddlePeg(9, 30));
	}
	
	@Test
	public void testCheckJump()
	{
		
	}
	
	@Test
	public void testCheckHop()
	{
		
	}

}
