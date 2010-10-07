package harbinPegSTN.gui;

import harbinPegSTN.model.PegPuzzleModel;
import harbinPegSTN.model.SaveTheNetworkModel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
	private PegPuzzleModel pegPuzzle = new PegPuzzleModel();
	private SaveTheNetworkModel stnPuzzle = new SaveTheNetworkModel();

	private BoardState state = BoardState.SAVE_THE_NETWORK;
	private JPanel boardArea = new JPanel();
	private BoardPanel boardPanel = new BoardPanel(stnPuzzle);

	public Main()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		buildGUI();
		pack();
		updateGUI();
	}

	private void buildGUI() {
		// TODO Auto-generated method stub
		boardArea.setLayout(new BoxLayout(boardArea, BoxLayout.Y_AXIS));
		boardArea.add(boardPanel);
		boardArea.add(new BottomPanel(this));
		add(boardArea);
	}

	private void updateGUI() {
		// TODO Auto-generated method stub
		switch (state) {
		case PEG_PUZZLE:
			boardPanel.setModel(pegPuzzle);
			break;
		case SAVE_THE_NETWORK:
			boardPanel.setModel(stnPuzzle);
			break;
		}
		boardPanel.updateGUI();
	}

	public static void main(String[] args)
	{
		Main m = new Main();
		m.setVisible(true);
	}

	protected class BottomPanel extends JPanel {
		private Main main = null;

		public BottomPanel(Main m) {
			main = m;
			this.buildGUI();
		}

		private void buildGUI() {
			// TODO Auto-generated method stub
			JButton newButton = new JButton("New Game");
			JButton switchButton = new JButton("Switch Games");
			this.add(newButton);
			this.add(switchButton);

			this.setMinimumSize(new Dimension(80, 36));
			this.setMaximumSize(new Dimension(Short.MAX_VALUE, 36));

			newButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					main.resetBoard();
				}
			});
			switchButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					main.switchBoard();
				}
			});
		}
	}

	public enum BoardState { PEG_PUZZLE, SAVE_THE_NETWORK }

	protected void resetBoard() {
		// TODO Auto-generated method stub
		switch (state) {
		case PEG_PUZZLE:
			pegPuzzle.reset();
			break;
		case SAVE_THE_NETWORK:
			stnPuzzle.reset();
			break;
		}

		updateGUI();
	}

	protected void switchBoard() {
		// TODO Auto-generated method stub
		switch (state) {
		case PEG_PUZZLE:
			boardPanel.setModel(stnPuzzle);
			state = BoardState.SAVE_THE_NETWORK;
			break;
		case SAVE_THE_NETWORK:
			boardPanel.setModel(pegPuzzle);
			state = BoardState.PEG_PUZZLE;
			break;
		}

		updateGUI();
	};
}
