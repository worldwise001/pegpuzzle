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

	private int prevClick = 0;
	public static final int GRID_SIZE = 7;
	
	private Model model = null;
	private VisualPeg[] pegs = new VisualPeg[34];

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
					pegs[++b] = new VisualPeg(this, b, j, i);
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
	
	public void reset() {
		// TODO Auto-generated method stub
		model.reset();
		updateGUI();
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
