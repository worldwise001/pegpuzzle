package harbinPegSTN.gui;

import harbinPegSTN.model.PegPuzzleModel;
import harbinPegSTN.model.SaveTheNetworkModel;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	private BoardPanel ppp = new BoardPanel(new PegPuzzleModel());
	private BoardPanel stnp = new BoardPanel(new SaveTheNetworkModel());
	
	public Main()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(stnp);
		pack();
	}
	
	public static void main(String[] args)
	{
		Main m = new Main();
		m.setVisible(true);
	}
}
