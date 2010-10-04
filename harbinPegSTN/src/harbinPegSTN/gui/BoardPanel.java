package harbinPegSTN.gui;

import harbinPegSTN.model.Model;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Model model = null;
	private JButton[] buttons = new JButton[33];
	
	public BoardPanel(Model m) {
		model = m;
		buildGUI();
		updateGUI();
	}

	private void updateGUI() {
		// TODO Auto-generated method stub
		
	}

	private void buildGUI() {
		// TODO Auto-generated method stub
		
	}

}
