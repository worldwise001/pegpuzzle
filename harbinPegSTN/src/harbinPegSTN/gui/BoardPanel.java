package harbinPegSTN.gui;

import harbinPegSTN.model.Model;
import harbinPegSTN.model.Status;

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
	
	
	public String status[]={"white peg placed", "white click", "White jump","white slide","white removed","its white's turn"
			,"its black's turn","black click", "black slide", "Peg Jump", "its invalid move/click"};
	private Model model = null;
	private VisualPeg[] pegs = new VisualPeg[34];

	protected Main mainWindow;
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
				if (isLocationUsable(i, j))
					pegs[++b] = new VisualPeg(this, b, j, i);
			}
		}
		addMouseListener(this);
	}

	protected boolean isLocationUsable(int i, int j) {
		return !(i < 2 && j < 2) && !(i < 2 && j > 4) &&
				!(i > 4 && j < 2) && !(i > 4 && j > 4);
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
		String s = status[Status.toInt(model.getStatus())];
		
		
		if(mainWindow!=null)
			mainWindow.updateStatus(s);
	}

	protected boolean processClick(int i) {
		boolean result = model.togglePeg(i);
		for(int j=1;j<34;j++)
			pegs[j].setSelected(false);
		if(model.getSelectedPeg() != Model.PEG_ID_NONE)
			pegs[model.getSelectedPeg()].setSelected(true);
		updateGUI();
		return result;
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
