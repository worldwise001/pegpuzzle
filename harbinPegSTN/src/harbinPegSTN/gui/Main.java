package harbinPegSTN.gui;

import harbinPegSTN.model.SaveTheNetworkModel;
import harbinPegSTN.model.Status;

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
	private PegPuzzleBoardPanel pegPuzzle ;
	private SaveTheNetworkBoardPanel stnPuzzle ;

	private BoardState state = BoardState.SAVE_THE_NETWORK;
	private JPanel boardArea = new JPanel();
	private JLabel statusArea = new JLabel();
	private JButton continueButton = new JButton("Continue");
	
	public Main()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pegPuzzle=new PegPuzzleBoardPanel(this);
		stnPuzzle=new SaveTheNetworkBoardPanel(this);
		buildGUI();
	}
	public void updateStatus(Status st){
		if(st==Status.WHITE_JUMP)
			continueButton.setEnabled(true);
		statusArea.setText(Status.toString(st));
	}
	private void buildGUI() {
		// TODO Convert this to two BoardPanels using CardLayout

		this.setLayout(new BorderLayout());
		CardLayout cardStack = new CardLayout();
		boardArea.setLayout(cardStack);

		boardArea.add(stnPuzzle, "STN");
		boardArea.add(pegPuzzle, "PEG");
		
		this.add(boardArea, BorderLayout.CENTER);
		this.add(new BottomPanel(), BorderLayout.SOUTH);
		this.add(statusArea, BorderLayout.NORTH);

		setSize(600, 600);
		continueButton.setEnabled(false);
	}

	public static void main(String[] args)
	{
		Main m = new Main();
		m.setVisible(true);
	}

	protected class BottomPanel extends JPanel {

		public BottomPanel() {
			buildGUI();
		}

		private void buildGUI() {
			// TODO Auto-generated method stub
			JButton newButton = new JButton("New Game");
			JButton switchButton = new JButton("Switch Games");
			JButton displayNumberButton = new JButton("Toggle Number Display");
			add(newButton);
			add(switchButton);
			add(displayNumberButton);
			add(continueButton);

			setMinimumSize(new Dimension(80, 36));
			setMaximumSize(new Dimension(Short.MAX_VALUE, 36));

			newButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					resetBoard();
				}
			});
			continueButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					stnPuzzle.endWhiteJump();
					continueButton.setEnabled(false);
					
				}
			});
			switchButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					switchBoard();
				}
			});
			displayNumberButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pegPuzzle.setShowNumbers(!pegPuzzle.isShowNumbers());
					pegPuzzle.repaint();
					stnPuzzle.setShowNumbers(!stnPuzzle.isShowNumbers());
					stnPuzzle.repaint();
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
