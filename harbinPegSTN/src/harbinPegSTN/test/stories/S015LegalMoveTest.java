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
		assertEquals("Peg ID at (0,3) should be 2", 2, model.pointToPegID(new Point(0, 3)));
		assertEquals("Peg ID at (1,3) should be 5", 5, model.pointToPegID(new Point(1, 3)));
		assertEquals("Peg ID at (3,3) should be 17", 17, model.pointToPegID(new Point(3, 3)));

		assertEquals("Peg ID at (5,3) should be 29", 29, model.pointToPegID(new Point(5, 3)));
	}

}
