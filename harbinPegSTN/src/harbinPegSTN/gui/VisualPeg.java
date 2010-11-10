package harbinPegSTN.gui;

import harbinPegSTN.model.Peg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class VisualPeg {
	private int x = 0;
	private int y = 0;
	private int id = 0;

	private float strX = 0;
	private float strY = 0;
	private boolean selected = false;
	private boolean penalty = false;
	private Color color = null;
	private Color textColor = Color.BLACK;
	private Ellipse2D.Double shape = new Ellipse2D.Double();
	private Font font = Font.decode("DejaVu Serif-12");
	
	public static final float CIRCLE_MULTIPLIER = 1.7f;
	
	private BoardPanel parent = null;
	
	public VisualPeg(BoardPanel parent, int i, int x, int y) {
		id = i;
		this.x = x;
		this.y = y;
		this.parent = parent;
	}

	public void updateState(Peg state) {
		switch (state) {
		case BLACK: color = Color.BLACK; textColor = Color.WHITE; break;
		case WHITE: color = Color.WHITE; textColor = Color.BLACK; break;
		case NORMAL: color = Color.RED; textColor = Color.BLACK; break;
		default: 
			color = parent.getBackgroundPegColor(id);
			textColor = Color.BLACK; break;
		}

		if (selected) color = Color.CYAN;
	}

	private void updateGraphic() {
		double cellWidth = parent.getCellSize().getWidth();
		double cellHeight = parent.getCellSize().getHeight();

		double cellSmallest = (cellWidth > cellHeight)?cellHeight:cellWidth;
		double circleDiameter = cellSmallest / CIRCLE_MULTIPLIER;

		double circleX = (cellWidth - circleDiameter)/2 + cellWidth*x;
		double circleY = (cellHeight - circleDiameter)/2 + cellHeight*y;

		shape.setFrame(circleX, circleY, circleDiameter, circleDiameter);
	}

	private void updateText(Graphics2D g2) {
		font = font.deriveFont((float)shape.getWidth()/2);

		double cellWidth = parent.getCellSize().getWidth();
		double cellHeight = parent.getCellSize().getHeight();

		String text = "" + id;

		LineMetrics lineMetrics = font.getLineMetrics(text, g2.getFontRenderContext());
		Rectangle2D bounds = font.getStringBounds(text, g2.getFontRenderContext());

		strX = (float)(cellWidth*x + (cellWidth - bounds.getWidth())/2);
		strY = (float)(cellHeight*y + (cellHeight - bounds.getHeight())/2 + lineMetrics.getAscent());

	}

	public void draw(Graphics2D g2) {
		updateGraphic();

		if (color != null) {
			g2.setColor(color);
			g2.fill(shape);
		}

		g2.setColor(Color.BLACK);
		g2.draw(shape);
		
		if (penalty) {
			g2.setColor(Color.RED);
			g2.drawLine((int)shape.x, (int)shape.y, (int)(shape.x+shape.width), (int)(shape.y+shape.width));
			g2.drawLine((int)(shape.x+shape.width), (int)shape.y, (int)shape.x, (int)(shape.y+shape.width));
		}
	}
	
	public void drawText(Graphics2D g2) {
		updateText(g2);
		g2.setColor(textColor);
		g2.setFont(font);
		g2.drawString(""+id, strX, strY);
	}

	public boolean contains(Point pt) {
		return shape.contains(pt);
	}

	public void setSelected(boolean b) {
		selected = b;
	}
	
	public void setPenalty(boolean b) {
		penalty = b;
	}
}
