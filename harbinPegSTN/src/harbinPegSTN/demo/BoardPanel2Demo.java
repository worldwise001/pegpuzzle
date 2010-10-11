package harbinPegSTN.demo;

import java.awt.Dimension;

import harbinPegSTN.model.PegPuzzleModel;
import harbinPegSTN.model.SaveTheNetworkModel;
import harbinPegSTN.snippets.BoardPanel2;

import org.junit.Before;
import org.junit.runner.RunWith;

import de.jdemo.annotation.Demo;
import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.junit.DemoAsTestRunner;

@RunWith (DemoAsTestRunner.class)
public class BoardPanel2Demo extends SwingDemoCase {

	private PegPuzzleModel pegPuzzle = null;
	private SaveTheNetworkModel stnPuzzle = null;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		pegPuzzle = new PegPuzzleModel();
		stnPuzzle = new SaveTheNetworkModel();
	}
	
	@Demo
	public void demoDrawPegScreen() {
		BoardPanel2 panel = new BoardPanel2(pegPuzzle);
		panel.setMinimumSize(new Dimension(400,300));
		panel.setPreferredSize(new Dimension(400,300));
		show(panel);
	}
	
	@Demo
	public void demoDrawSTNScreen() {
		BoardPanel2 panel = new BoardPanel2(stnPuzzle);
		panel.setMinimumSize(new Dimension(400,300));
		panel.setPreferredSize(new Dimension(400,300));
		show(panel);
	}

}
