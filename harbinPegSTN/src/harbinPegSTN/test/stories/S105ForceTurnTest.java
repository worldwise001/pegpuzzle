package harbinPegSTN.test.stories;

import static org.junit.Assert.*;
import harbinPegSTN.model.SaveTheNetworkModel;
import harbinPegSTN.model.Model.Peg;

import org.junit.Before;
import org.junit.Test;

public class S105ForceTurnTest {
SaveTheNetworkModel model = null;
	
	@Before
	public void setUp() throws Exception {
		model = new SaveTheNetworkModel();
	}
	
	@Test
	public void testForceTurn(){
		model.setPegAt(Peg.WHITE, 4);
		assertFalse("Start with white's turn, so all black move is illegal, try black move from 8 to 9", model.makeMove(8, 9));
		assertTrue("White legal move from 4 to 9", model.makeMove(4, 9));
		model.reverseTurn();
		assertTrue("Now black's turn, legal move from 12 to 11",model.makeMove(12, 11));
		assertFalse("illegal white's turn from 9 to 8", model.makeMove(9, 8));
		
	}
}
