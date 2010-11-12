package harbinPegSTN.test.stories;

import harbinPegSTN.model.Model;

import org.junit.Before;
import org.junit.Test;

public class S300XMLSaveTest {
	
	Model model;
	
	@Before
	public void setUp() throws Exception {
		model = new Model();
	}
	
	@Test
	public void checkSaveExists() {
	}
	
	@Test
	public void checkStateSaved() {
		//assertFalse("Diagonal moves are not allowed by default", model.isDiagonalMovesAllowed());
	}

}
