package harbinPegSTN.gui;

import harbinPegSTN.model.Status;
import harbinPegSTN.storage.XMLEngine;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
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
	private JButton saveButton = new JButton("Save Game");
	private JButton loadButton = new JButton("Load Game");
	
	public Main()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pegPuzzle=new PegPuzzleBoardPanel(this);
		stnPuzzle=new SaveTheNetworkBoardPanel(this);
		credits = new CreditsPanel();
		buildGUI();
		updateGUI();
	}
	
	public void updateGUI() {
		if (showCredits) {
			statusArea.setVisible(false);
			saveButton.setEnabled(false);
			loadButton.setEnabled(false);
			return;
		}
		statusArea.setVisible(true);
		Status status = Status.NONE;
		switch (state) {
			case PEG_PUZZLE:
				status = pegPuzzle.getModel().getStatus();
				saveButton.setEnabled(false);
				loadButton.setEnabled(false);
				break;
			case SAVE_THE_NETWORK:
				status = stnPuzzle.getModel().getStatus();
				saveButton.setEnabled(true);
				loadButton.setEnabled(true);
				break;
		}
		continueButton.setEnabled((status == Status.WHITE_JUMP)||(status == Status.PENALTY_REQUIRED));
		statusArea.setText(Status.toString(status));
	}
	
	private void buildGUI() {
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		Font font = Font.decode("DejaVu Serif-24");
		CardLayout cardStack = new CardLayout();
		boardArea.setLayout(cardStack);

		boardArea.add(stnPuzzle, CARD_STN);
		boardArea.add(pegPuzzle, CARD_PEG);
		boardArea.add(credits, CARD_CREDITS);
		
		statusArea.setPreferredSize(new Dimension(80, 48));
		statusArea.setHorizontalAlignment(SwingConstants.CENTER);
		statusArea.setFont(font);
		
		add(boardArea, BorderLayout.CENTER);
		add(new BottomPanel(), BorderLayout.SOUTH);
		add(statusArea, BorderLayout.NORTH);

		setSize(900, 600);
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
			add(newButton);
			add(switchButton);
			add(displayNumberButton);
			add(continueButton);
			add(credGameToggleButton);
			add(saveButton);
			add(loadButton);

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
			saveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					XMLEngine engine = new XMLEngine();
					engine.write(stnPuzzle.getModel());
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
		updateGUI();
	}
	
	protected void toggleNumbers() {
		pegPuzzle.setShowNumbers(!pegPuzzle.isShowNumbers());
		pegPuzzle.repaint();
		stnPuzzle.setShowNumbers(!stnPuzzle.isShowNumbers());
		stnPuzzle.repaint();
	}

	protected void endJump() {
		continueButton.setEnabled(!stnPuzzle.endWhiteJump());
		updateGUI();
		
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
		updateGUI();
	}
}
