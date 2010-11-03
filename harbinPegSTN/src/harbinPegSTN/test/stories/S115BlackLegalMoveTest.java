package harbinPegSTN.test.stories;

import static org.junit.Assert.*;
import harbinPegSTN.model.SaveTheNetworkModel;

import org.junit.Before;
import org.junit.Test;

public class S115BlackLegalMoveTest {

	SaveTheNetworkModel model = null;
	
	@Before
	public void setUp() throws Exception {
		model = new SaveTheNetworkModel();
		model.togglePeg(6);
		model.togglePeg(5);
	}
	
	@Test
	public void testBlackLegalMove(){
		//model.reverseTurn();
		assertTrue("black legal hop from 8 to 9",model.makeMove(8, 9));
		//model.reverseTurn();
		assertTrue("black legal hop from 9 to 4",model.makeMove(9, 4));
		assertFalse("Illegal black backward hop from 4 to 9",model.makeMove(4, 9));
		assertFalse("Illegal black jump from 4 to 6",model.makeMove(4, 6));
		
	}
}
