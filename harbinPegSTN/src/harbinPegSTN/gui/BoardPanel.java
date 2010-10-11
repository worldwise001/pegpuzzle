package harbinPegSTN.gui;

import harbinPegSTN.model.Model;
import harbinPegSTN.model.SaveTheNetworkModel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class BoardPanel extends JPanel implements MouseListener {

	public static final int GRID_SIZE = 7;
	public static final float CIRCLE_MULTIPLIER = 1.7f;
	public static final Color DEEP_BLUE = new Color(0x77, 0x77, 0xBB);
	public static final Color DEEP_ORANGE = new Color(0xDD, 0x77, 0x33);

	private int prevClick = 0;
	private int pegsToPlace = 0;

	private Model model = null;
	private Peg[] pegs = new Peg[34];

	private RoundRectangle2D.Double bgSafeZone = new RoundRectangle2D.Double(0,0,0,0,30,30);
	private RoundRectangle2D.Double bgDangerZone1 = new RoundRectangle2D.Double(0,0,0,0,30,30);
	private RoundRectangle2D.Double bgDangerZone2 = new RoundRectangle2D.Double(0,0,0,0,30,30);

	public BoardPanel(Model m) {
		model = m;
		if (model instanceof SaveTheNetworkModel) pegsToPlace = 2;
		buildGUI();
		updateGUI();
	}

	private void buildGUI() {
		// TODO Auto-generated method stub
		int b = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (!(i < 2 && j < 2) && !(i < 2 && j > 4) &&
						!(i > 4 && j < 2) && !(i > 4 && j > 4))
					pegs[++b] = new Peg(b, j, i);
			}
		}
		addMouseListener(this);
	}

	private void updateGUI() {
		for (int i = 1; i < 34; i++)
			pegs[i].updateState(model.getPeg(i));

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;

		updateBackground(g2);

		for (int i = 1; i < 34; i++)
			pegs[i].draw(g2);

	}

	private void updateBackground(Graphics2D g2) {
		// TODO Auto-generated method stub
		colorBackground(g2);
		drawMoveLines(g2);
	}

	private void drawMoveLines(Graphics2D g2) {
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

	private void colorBackground(Graphics2D g2) {
		double cellWidth = getCellSize().getWidth();
		double cellHeight = getCellSize().getHeight();

		if (model instanceof SaveTheNetworkModel) {

			bgSafeZone.setFrame(2*cellWidth, 0, 3*cellWidth, 3*cellHeight);
			bgDangerZone1.setFrame(0, 2*cellHeight, 7*cellWidth, 3*cellHeight);
			bgDangerZone2.setFrame(2*cellWidth, 4*cellHeight, 3*cellWidth, 3*cellHeight);

			g2.setColor(DEEP_ORANGE);
			g2.fill(bgDangerZone2);
			g2.fill(bgDangerZone1);
			g2.setColor(DEEP_BLUE);
			g2.fill(bgSafeZone);
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

		if (model instanceof SaveTheNetworkModel) {
			g2.drawLine(lx1, ly1, lx2, ly2);
			g2.drawLine(lx1, ly2, lx2, ly1);
		}
	}

	private Dimension getCellSize() {		
		return new Dimension(getWidth() / GRID_SIZE, getHeight() / GRID_SIZE);
	}

	protected class Peg {
		int x = 0;
		int y = 0;
		int id = 0;

		float sx = 0;
		float sy = 0;
		boolean selected = false;
		Color color = null;
		Color textColor = Color.BLACK;
		Ellipse2D.Double shape = new Ellipse2D.Double();
		Font font = Font.decode("DejaVu Serif-12");

		public Peg(int i, int x, int y) {
			id = i;
			this.x = x;
			this.y = y;
		}

		public void updateState(Model.Peg state) {
			switch (state) {
			case BLACK: color = Color.BLACK; textColor = Color.WHITE; break;
			case WHITE: color = Color.WHITE; textColor = Color.BLACK; break;
			case NORMAL: color = Color.RED; textColor = Color.BLACK; break;
			default: 
				if (model instanceof SaveTheNetworkModel) {
					if ((id >= 1 && id <= 6) || (id >= 9 && id <= 11))
						color = DEEP_BLUE;
					else
						color = DEEP_ORANGE;
				}
				else color = getBackground();
				textColor = Color.BLACK; break;
			}

			if (selected) color = Color.CYAN;
		}

		private void updateGraphic() {
			double cellWidth = getCellSize().getWidth();
			double cellHeight = getCellSize().getHeight();

			double cellSmallest = (cellWidth > cellHeight)?cellHeight:cellWidth;
			double circleDiameter = cellSmallest / CIRCLE_MULTIPLIER;

			double circleX = (cellWidth - circleDiameter)/2 + cellWidth*x;
			double circleY = (cellHeight - circleDiameter)/2 + cellHeight*y;

			shape.setFrame(circleX, circleY, circleDiameter, circleDiameter);
		}

		private void updateText(Graphics2D g2) {
			font = font.deriveFont((float)shape.getWidth()/2);

			double cellWidth = getCellSize().getWidth();
			double cellHeight = getCellSize().getHeight();

			String text = "" + id;

			LineMetrics lineMetrics = font.getLineMetrics(text, g2.getFontRenderContext());
			Rectangle2D bounds = font.getStringBounds(text, g2.getFontRenderContext());

			sx = (float)(cellWidth*x + (cellWidth - bounds.getWidth())/2);
			sy = (float)(cellHeight*y + (cellHeight - bounds.getHeight())/2 + lineMetrics.getAscent());

		}

		public void draw(Graphics2D g2) {
			updateGraphic();
			updateText(g2);

			if (color != null) {
				g2.setColor(color);
				g2.fill(shape);
			}

			g2.setColor(Color.BLACK);
			g2.draw(shape);
			g2.setColor(textColor);
			g2.setFont(font);
			g2.drawString(""+id, sx, sy);
		}

		public boolean contains(Point pt) {
			return shape.contains(pt);
		}

		public void setSelected(boolean b) {
			selected = b;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Point pt = e.getPoint();
		for (int i = 1; i < 34; i++) {
			if (pegs[i].contains(pt)) {
				processClick(i);
				break;
			}

		}
	}

	private void processClick(int i) {
		if (pegsToPlace > 0) {
			if (model.placeWhite(i)) pegsToPlace--;
		}
		else
		{
			if (prevClick == 0) {
				prevClick = i;
				pegs[i].setSelected(true);
			} else {
				int click = i;
				pegs[click].setSelected(false);
				pegs[prevClick].setSelected(false);
				executeMove(prevClick, click);
				prevClick = 0;
			}
		}
		updateGUI();
	}

	private void executeMove(int loc1, int loc2) {
		boolean result = model.makeMove(loc1, loc2);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
