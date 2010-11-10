package harbinPegSTN.gui;

import harbinPegSTN.model.Model;
import harbinPegSTN.model.Status;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class BoardPanel extends JPanel implements MouseListener {

	public static final int GRID_SIZE = 7;
	private boolean showNumbers = true;
	
	private Model model = null;
	private VisualPeg[] pegs = new VisualPeg[34];

	protected Main mainWindow;
	public BoardPanel(Model m) {
		model = m;
		buildGUI();
		updateGUI();
	}

	protected void buildGUI() {
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
		for(int j=1;j<34;j++)
			pegs[j].setSelected(false);
		if(model.getSelectedPeg() != Model.PEG_ID_NONE)
			pegs[model.getSelectedPeg()].setSelected(true);
		for (int i = 1; i < 34; i++)
			pegs[i].updateState(model.getPeg(i));
		repaint();
	}
	
	protected VisualPeg getPeg(int loc) {
		if (loc < 1 || loc > 34) return null;
		return pegs[loc];
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;

		updateBackground(g2);

		for (int i = 1; i < 34; i++)
		{
			pegs[i].draw(g2);
			if (showNumbers) pegs[i].drawText(g2);
		}

	}

	private void updateBackground(Graphics2D g2) {
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
		Point pt = e.getPoint();
		for (int i = 1; i < 34; i++) {
			if (pegs[i].contains(pt)) {
				processClick(i);
				break;
			}
		}
		
		if(mainWindow!=null) mainWindow.updateGUI();
	}
	public Status getStatus(){
		return model.getStatus();
	}
	
	protected boolean processClick(int i) {
		boolean result = model.togglePeg(i);
		updateGUI();
		return result;
	}
	
	protected Model getModel() {
		return model;
	}
	
	public void reset() {
		model.reset();
		updateGUI();
	}

	protected boolean isShowNumbers() {
		return showNumbers;
	}

	protected void setShowNumbers(boolean showNumbers) {
		this.showNumbers = showNumbers;
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
