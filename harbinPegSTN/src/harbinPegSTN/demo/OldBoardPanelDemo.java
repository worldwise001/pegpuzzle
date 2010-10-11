package harbinPegSTN.demo;

import harbinPegSTN.model.PegPuzzleModel;
import harbinPegSTN.model.SaveTheNetworkModel;
import harbinPegSTN.snippets.BoardPanel;

import org.junit.Before;
import org.junit.runner.RunWith;

import de.jdemo.annotation.Demo;
import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.junit.DemoAsTestRunner;

@RunWith (DemoAsTestRunner.class)
public class OldBoardPanelDemo extends SwingDemoCase {
	private PegPuzzleModel pegPuzzle = null;
	private SaveTheNetworkModel stnPuzzle = null;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		
		pegPuzzle = new PegPuzzleModel();
		stnPuzzle = new SaveTheNetworkModel();
	}
	
	@Demo
	public void demoSTNInitBoard() {
		BoardPanel panel = new BoardPanel(stnPuzzle);
		show(panel);
	}
	
	@Demo
	public void demoPegInitBoard() {
		BoardPanel panel = new BoardPanel(pegPuzzle);
		show(panel);
	}

}
