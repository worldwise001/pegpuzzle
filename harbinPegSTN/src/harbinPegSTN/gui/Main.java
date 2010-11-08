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
import javax.swing.SwingConstants;

public class Main extends JFrame {
	private static final String CARD_PEG = "PEG";
	private static final String CARD_STN = "STN";
	private static final String CARD_CREDITS = "CREDITS";
	private PegPuzzleBoardPanel pegPuzzle ;
	private SaveTheNetworkBoardPanel stnPuzzle ;
	private CreditsPanel credits;

	private BoardState state = BoardState.SAVE_THE_NETWORK;
	private boolean showCredits = false;
	private JPanel boardArea = new JPanel();
	private JLabel statusArea = new JLabel();
	
	private JButton continueButton = new JButton("Continue");
	private JButton newButton = new JButton("New Game");
	private JButton switchButton = new JButton("Switch Games");
	private JButton credGameToggleButton = new JButton("Show Credits");
	private JButton displayNumberButton = new JButton("Toggle Number Display");
	
	public Main()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pegPuzzle=new PegPuzzleBoardPanel(this);
		stnPuzzle=new SaveTheNetworkBoardPanel(this);
		credits = new CreditsPanel();
		buildGUI();
	}
	
	public void updateStatus(Status st){
		continueButton.setEnabled((st == Status.WHITE_JUMP)||st==Status.PENALTY_REQUIRED);
		statusArea.setText(Status.toString(st));
	}
	
	private void buildGUI() {
		setLayout(new BorderLayout());
		CardLayout cardStack = new CardLayout();
		boardArea.setLayout(cardStack);

		boardArea.add(stnPuzzle, CARD_STN);
		boardArea.add(pegPuzzle, CARD_PEG);
		boardArea.add(credits, CARD_CREDITS);
		
		add(boardArea, BorderLayout.CENTER);
		add(new BottomPanel(), BorderLayout.SOUTH);
		add(statusArea, BorderLayout.NORTH);

		setSize(600, 600);
		continueButton.setEnabled(false);
		statusArea.setSize(Integer.MAX_VALUE, 50);
		statusArea.setHorizontalTextPosition(SwingConstants.CENTER);
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
			add(newButton);
			add(switchButton);
			add(displayNumberButton);
			add(continueButton);
			add(credGameToggleButton);

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
					endJump();
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
					toggleNumbers();
				}
			});
			credGameToggleButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					toggleCredits();
				}
			});
			
		}
	}

	public enum BoardState { PEG_PUZZLE, SAVE_THE_NETWORK }

	protected void resetBoard() {
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
		switch (state) {
		case PEG_PUZZLE:
			((CardLayout)boardArea.getLayout()).show(boardArea, CARD_STN);
			state = BoardState.SAVE_THE_NETWORK;
			break;
		case SAVE_THE_NETWORK:
			((CardLayout)boardArea.getLayout()).show(boardArea, CARD_PEG);
			state = BoardState.PEG_PUZZLE;
			break;
		}
	};
	
	protected void toggleNumbers() {
		pegPuzzle.setShowNumbers(!pegPuzzle.isShowNumbers());
		pegPuzzle.repaint();
		stnPuzzle.setShowNumbers(!stnPuzzle.isShowNumbers());
		stnPuzzle.repaint();
	}

	protected void endJump() {
		
		continueButton.setEnabled(!stnPuzzle.endWhiteJump());
		updateStatus(stnPuzzle.getStatus());
		
	}

	protected void toggleCredits() {
		if (!showCredits) {
			((CardLayout)boardArea.getLayout()).show(boardArea, CARD_CREDITS);
			showCredits = true;
			continueButton.setEnabled(false);
			newButton.setEnabled(false);
			switchButton.setEnabled(false);
			displayNumberButton.setEnabled(false);
			credGameToggleButton.setText("Back to Game");
		}
		else
		{
			switch (state) {
			case PEG_PUZZLE:
				((CardLayout)boardArea.getLayout()).show(boardArea, CARD_PEG);
				break;
			case SAVE_THE_NETWORK:
				((CardLayout)boardArea.getLayout()).show(boardArea, CARD_STN);
				break;
			}
			showCredits = false;
			continueButton.setEnabled(true);
			newButton.setEnabled(true);
			switchButton.setEnabled(true);
			displayNumberButton.setEnabled(true);
			credGameToggleButton.setText("Show Credits");
		}
	}
}
