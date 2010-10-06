package harbinPegSTN.gui;

import harbinPegSTN.model.Model;
import harbinPegSTN.model.Model.Peg;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class BoardPanel extends JPanel {

	private Model model = null;
	private JToggleButton[] buttons = new JToggleButton[34];
	
	public BoardPanel(Model m) {
		model = m;
		buildGUI();
		updateGUI();
	}

	private void updateGUI() {
		// TODO Auto-generated method stub
		for (int i = 1; i < buttons.length; i++) {
			updatePegState(i);
		}
	}

	private void updatePegState(int i) {
		// TODO Auto-generated method stub
		Peg state = model.getPeg(i);
		JToggleButton peg = buttons[i];
		
		switch (state) {
			case BLACK:
				peg.setBackground(Color.BLACK);
				peg.setForeground(Color.WHITE);
				peg.setEnabled(true);
				break;
			case NORMAL:
				peg.setBackground(Color.RED);
				peg.setForeground(Color.BLACK);
				peg.setEnabled(true);
				break;
			case WHITE:
				peg.setBackground(Color.WHITE);
				peg.setForeground(Color.BLACK);
				peg.setEnabled(true);
				break;
			case NONE:
				peg.setBackground(Color.DARK_GRAY);
				peg.setForeground(Color.WHITE);
				peg.setEnabled(false);
				break;
			default:
				peg.setBackground(Color.GRAY);
				peg.setForeground(Color.GRAY);
				peg.setEnabled(false);
				break;
		}
	}

	private void buildGUI() {
		// TODO Auto-generated method stub
		setLayout(new GridLayout(7,7));
		for (int i = 1; i < buttons.length; i++) {
			buttons[i] = new JToggleButton("" + i);
		}
		int b = 1;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if ((i < 2 && j < 2) || (i < 2 && j > 4) ||
					(i > 4 && j < 2) || (i > 4 && j > 4))
					add(new JPanel());
				else add(buttons[b++]);
			}
		}
	}
	
	public Model getModel() {
		return model;
	}

}
