package harbinPegSTN.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import harbinPegSTN.model.SaveTheNetworkModel;

public class SaveTheNetworkBoardPanel extends BoardPanel {

	public static final Color DEEP_BLUE = new Color(0x77, 0x77, 0xBB);
	public static final Color DEEP_ORANGE = new Color(0xDD, 0x77, 0x33);
	private int pegsToPlace = 0;
	private RoundRectangle2D.Double bgSafeZone = new RoundRectangle2D.Double(0,0,0,0,30,30);
	private RoundRectangle2D.Double bgDangerZone1 = new RoundRectangle2D.Double(0,0,0,0,30,30);
	private RoundRectangle2D.Double bgDangerZone2 = new RoundRectangle2D.Double(0,0,0,0,30,30);

	public SaveTheNetworkBoardPanel() {
		super(new SaveTheNetworkModel());

		pegsToPlace = 2;
		// TODO Auto-generated constructor stub
	}

	protected void colorBackground(Graphics2D g2) {
		double cellWidth = getCellSize().getWidth();
		double cellHeight = getCellSize().getHeight();

		bgSafeZone.setFrame(2*cellWidth, 0, 3*cellWidth, 3*cellHeight);
		bgDangerZone1.setFrame(0, 2*cellHeight, 7*cellWidth, 3*cellHeight);
		bgDangerZone2.setFrame(2*cellWidth, 4*cellHeight, 3*cellWidth, 3*cellHeight);

		g2.setColor(SaveTheNetworkBoardPanel.DEEP_ORANGE);
		g2.fill(bgDangerZone2);
		g2.fill(bgDangerZone1);
		g2.setColor(SaveTheNetworkBoardPanel.DEEP_BLUE);
		g2.fill(bgSafeZone);
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

		g2.drawLine(lx1, ly1, lx2, ly2);
		g2.drawLine(lx1, ly2, lx2, ly1);
	}
	
	protected Color getBackgroundPegColor(int id) {
		if ((id >= 1 && id <= 6) || (id >= 9 && id <= 11))
			return DEEP_BLUE;
		else
			return DEEP_ORANGE;
	}
	
	protected boolean processClick(int i) {
		SaveTheNetworkModel m = (SaveTheNetworkModel)getModel();
		boolean result = false;
		if (pegsToPlace > 0) {
			result = m.placeWhite(i);
			if (result) pegsToPlace--;
			updateGUI();
		}
		else {
			result = super.processClick(i);
			if (result) m.reverseTurn();
		}
		return result;
	}

}
