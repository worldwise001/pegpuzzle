package harbinPegSTN.test.stories;

import static org.junit.Assert.*;
import harbinPegSTN.model.Model;
import harbinPegSTN.snippets.MockXML;
import harbinPegSTN.storage.Writer;

import org.junit.Before;
import org.junit.Test;

public class S300XMLSaveTest {
	
	Model model;
	Writer writer;
	
	@Before
	public void setUp() throws Exception {
		model = new Model();
		writer = new MockXML();
	}
	
	@Test
	public void checkSaveExists() {
		assertTrue("Writer ready to write", writer.readyWrite());
	}
	
	@Test
	public void checkStateSaved() {
		//assertFalse("Diagonal moves are not allowed by default", model.isDiagonalMovesAllowed());
	}

}
