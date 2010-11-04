package harbinPegSTN.test.stories;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import harbinPegSTN.model.Model;

public class S300XMLSaveTest {
	
	Model model;
	
	@Before
	public void setUp() throws Exception {
		model = new Model();
	}
	
	@Test
	public void checkSaveExists() {
		//assertFalse("Diagonal moves are not allowed by default", model.isDiagonalMovesAllowed());
	}
	
	@Test
	public void checkSaveable() {
		//assertFalse("Diagonal moves are not allowed by default", model.isDiagonalMovesAllowed());
	}
	
	@Test
	public void checkStateSaved() {
		//assertFalse("Diagonal moves are not allowed by default", model.isDiagonalMovesAllowed());
	}

}
