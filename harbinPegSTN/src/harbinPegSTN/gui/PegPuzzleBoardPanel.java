package harbinPegSTN.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import harbinPegSTN.model.PegPuzzleModel;

public class PegPuzzleBoardPanel extends BoardPanel {

	
	public PegPuzzleBoardPanel(Main mWin) {
		super(new PegPuzzleModel());
		mainWindow=mWin;
		// TODO Auto-generated constructor stub
	}
	
	protected void drawMoveLines(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		for (int i = 2; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				drawBox(g2, i, j);
			}
		}
		for (int i = 0; i < 6; i++) {
			for (int j = 2; j < 4; j++) {
				drawBox(g2, i, j);
			}
		}
		for (int i = 2; i < 4; i++) {
			for (int j = 4; j < 6; j++) {
				drawBox(g2, i, j);
			}
		}
	}

	private void drawBox(Graphics2D g2, int i, int j) {
		double cw = getCellSize().getWidth();
		double ch = getCellSize().getHeight();
		int lx1 = (int)((i+0.5)*cw);
		int ly1 = (int)((j+0.5)*ch);
		int lx2 = (int)((i+1.5)*cw);
		int ly2 = (int)((j+1.5)*ch);
		g2.drawLine(lx1, ly1, lx1, ly2);
		g2.drawLine(lx2, ly1, lx2, ly2);
		g2.drawLine(lx1, ly1, lx2, ly1);
		g2.drawLine(lx1, ly2, lx2, ly2);
	}

}
