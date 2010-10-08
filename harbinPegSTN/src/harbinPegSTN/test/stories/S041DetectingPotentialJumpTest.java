package harbinPegSTN.test.stories;

import static org.junit.Assert.*;
import harbinPegSTN.model.SaveTheNetworkModel;
import harbinPegSTN.model.Model.Peg;

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
		model.setPegAt(Peg.WHITE, 10);
		model.setPegAt(Peg.NONE, 24);
		assertTrue("White potential jump from 10 to 24", model.checkMove(10, 24));
		assertTrue("White potential jump from 10 to 9", model.checkMove(10, 9));
		assertFalse("Illegal move from 10 to 22",model.checkMove(10, 22));
		
	}
}
