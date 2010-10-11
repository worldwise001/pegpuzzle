package harbinPegSTN.gui;

import harbinPegSTN.model.Model;

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

import javax.swing.JPanel;

public class BoardPanel extends JPanel implements MouseListener {

	public static final int GRID_SIZE = 7;
	public static final float CIRCLE_MULTIPLIER = 1.7f;
	private int prevClick = 0;
	
	private Model model = null;
	private Peg[] pegs = new Peg[34];

	public BoardPanel(Model m) {
		model = m;
		buildGUI();
		updateGUI();
	}

	protected void buildGUI() {
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

	protected void updateGUI() {
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
	
	protected void colorBackground(Graphics2D g2) {
		// Do nothing
	}

	protected void drawMoveLines(Graphics2D g2) {
		// Do nothing
	}

	protected Dimension getCellSize() {		
		return new Dimension(getWidth() / GRID_SIZE, getHeight() / GRID_SIZE);
	}
	
	protected Color getBackgroundPegColor(int i) {
		return getBackground();
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
				color = getBackgroundPegColor(id);
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

	protected boolean processClick(int i) {
		boolean result = false;
		if (prevClick == 0) {
			prevClick = i;
			pegs[i].setSelected(true);
		} else {
			int click = i;
			pegs[click].setSelected(false);
			pegs[prevClick].setSelected(false);
			result = executeMove(prevClick, click);
			prevClick = 0;
		}
		updateGUI();
		return result;
	}

	private boolean executeMove(int loc1, int loc2) {
		return model.makeMove(loc1, loc2);
	}
	
	protected Model getModel() {
		// TODO Auto-generated method stub
		return model;
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
