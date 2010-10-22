package harbinPegSTN.test.stories;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import harbinPegSTN.model.Peg;
import harbinPegSTN.model.SaveTheNetworkModel;

import org.junit.Before;
import org.junit.Test;

public class S041DetectingPotentialJumpTest {

	//detecting potential jumps for STN Model
SaveTheNetworkModel model = null;
	
	@Before
	public void setUp() throws Exception {
		model = new SaveTheNetworkModel();
	}
	
	@Test
	public void testCheckMove(){
		model.setPeg(Peg.WHITE, 10);
		model.setPeg(Peg.NONE, 24);
		assertTrue("White potential jump from 10 to 24", model.checkMove(model.pegIDToPoint(10), model.pegIDToPoint(24)));
		assertTrue("White potential jump from 10 to 9", model.checkMove(model.pegIDToPoint(10), model.pegIDToPoint(9)));
		assertFalse("Illegal move from 10 to 22",model.checkMove(model.pegIDToPoint(10), model.pegIDToPoint(22)));
		
	}
}
