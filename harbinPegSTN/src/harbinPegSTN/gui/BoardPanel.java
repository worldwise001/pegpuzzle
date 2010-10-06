package harbinPegSTN.gui;

import harbinPegSTN.model.Model;
import harbinPegSTN.model.Model.Peg;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class BoardPanel extends JPanel implements ActionListener {

	private Model model = null;
	private PegButton[] buttons = new PegButton[34];

	private int prevClick = 0;

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
			peg.setEnabled(true);
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
			buttons[i] = new PegButton(i);
			buttons[i].addActionListener(this);
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

	protected class PegButton extends JToggleButton {
		int id = 0;

		public PegButton(int id) {
			super(""+id);
			this.id = id;
		}

		public int getID() {
			return id;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (!(o instanceof PegButton)) return;
		PegButton peg = (PegButton)o;
		if (prevClick == 0) {
			prevClick = peg.getID();
		} else {
			int click = peg.getID();
			peg.setSelected(false);
			buttons[prevClick].setSelected(false);
			executeMove(prevClick, click);
			prevClick = 0;
		}
	}

	private void executeMove(int loc1, int loc2) {
		boolean result = model.makeMove(loc1, loc2);
		System.out.println("Trying to move from "+loc1+" to "+loc2+" :"+result);
		updateGUI();
	}

}
