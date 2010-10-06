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
	
}
