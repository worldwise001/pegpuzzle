package harbinPegSTN.demo;

import harbinPegSTN.gui.PegPuzzleBoardPanel;
import harbinPegSTN.gui.SaveTheNetworkBoardPanel;

import java.awt.Dimension;

import org.junit.Before;
import org.junit.runner.RunWith;

import de.jdemo.annotation.Demo;
import de.jdemo.extensions.SwingDemoCase;
import de.jdemo.junit.DemoAsTestRunner;

@RunWith (DemoAsTestRunner.class)
public class BoardPanelDemo extends SwingDemoCase {

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Demo
	public void demoDrawPegScreen() {
		PegPuzzleBoardPanel panel = new PegPuzzleBoardPanel(null);
		panel.setMinimumSize(new Dimension(400,300));
		panel.setPreferredSize(new Dimension(400,300));
		show(panel);
	}
	
	@Demo
	public void demoDrawSTNScreen() {
		SaveTheNetworkBoardPanel panel = new SaveTheNetworkBoardPanel(null);
		panel.setMinimumSize(new Dimension(400,300));
		panel.setPreferredSize(new Dimension(400,300));
		show(panel);
	}

}
