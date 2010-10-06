package harbinPegSTN.gui;

import harbinPegSTN.model.PegPuzzleModel;

import javax.swing.JFrame;

public class Main extends JFrame {
	public Main()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		BoardPanel bp = new BoardPanel(new PegPuzzleModel());
		add(bp);
		pack();
	}
	
	public static void main(String[] args)
	{
		Main m = new Main();
		m.setVisible(true);
	}
}
