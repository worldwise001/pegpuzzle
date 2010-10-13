package harbinPegSTN.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends JFrame {
	private PegPuzzleBoardPanel pegPuzzle = new PegPuzzleBoardPanel();
	private SaveTheNetworkBoardPanel stnPuzzle = new SaveTheNetworkBoardPanel();

	private BoardState state = BoardState.SAVE_THE_NETWORK;
	private JPanel boardArea = new JPanel();
	private JLabel statusArea = new JLabel();

	public Main()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		buildGUI();
		pack();
	}

	private void buildGUI() {
		// TODO Convert this to two BoardPanels using CardLayout

		this.setLayout(new BorderLayout());
		CardLayout cardStack = new CardLayout();
		boardArea.setLayout(cardStack);

		boardArea.add(stnPuzzle, "STN");
		boardArea.add(pegPuzzle, "PEG");
		
		this.add(boardArea, BorderLayout.CENTER);
		this.add(new BottomPanel(this), BorderLayout.SOUTH);
		this.add(statusArea, BorderLayout.NORTH);
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
	}

	protected void switchBoard() {
		// TODO Auto-generated method stub
		
		switch (state) {
		case PEG_PUZZLE:
			((CardLayout)boardArea.getLayout()).next(boardArea);
			state = BoardState.SAVE_THE_NETWORK;
			break;
		case SAVE_THE_NETWORK:
			((CardLayout)boardArea.getLayout()).next(boardArea);
			state = BoardState.PEG_PUZZLE;
			break;
		}
	};
}
