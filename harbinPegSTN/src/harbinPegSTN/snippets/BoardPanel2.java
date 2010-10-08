package harbinPegSTN.snippets;

import harbinPegSTN.model.Model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class BoardPanel2 extends JPanel implements MouseListener {
	
	public static final int GRID_SIZE = 7;
	public static final Color DEEP_BLUE = new Color(0, 0, 0x66);
	public static final Color DEEP_ORANGE = new Color(0xCC, 0x66, 00);
	
	private Model model = null;
	private Peg[] pegs = new Peg[34];
	
	private RoundRectangle2D.Double bgSafeZone = new RoundRectangle2D.Double();
	private RoundRectangle2D.Double bgDangerZone1 = new RoundRectangle2D.Double();
	private RoundRectangle2D.Double bgDangerZone2 = new RoundRectangle2D.Double();
	
	public BoardPanel2(Model m) {
		model = m;
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
					pegs[++b] = new Peg(b, i, j);
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
		
	}
	
	private Dimension getCellSize() {		
		return new Dimension(getWidth() / GRID_SIZE, getHeight() / GRID_SIZE);
	}

	protected class Peg {
		int x = 0;
		int y = 0;
		int id = 0;
		Color color = null;
		Ellipse2D.Double shape = new Ellipse2D.Double();
		
		public Peg(int i, int x, int y) {
			id = i;
			this.x = x;
			this.y = y;
		}
		
		public void updateState(Model.Peg state) {
			switch (state) {
				case BLACK: color = Color.BLACK; break;
				case WHITE: color = Color.WHITE; break;
				case NORMAL: color = Color.RED; break;
				default: color = null; break;
			}
		}
		
		private void updateGraphic() {
			double cellWidth = getCellSize().getWidth();
			double cellHeight = getCellSize().getHeight();
			
			double cellSmallest = (cellWidth > cellHeight)?cellHeight:cellWidth;
			double circleDiameter = cellSmallest / 3;
			
			double circleX = (cellWidth - circleDiameter)/2 + cellWidth*x;
			double circleY = (cellHeight - circleDiameter)/2 + cellHeight*y;
			
			shape.setFrame(circleX, circleY, circleDiameter, circleDiameter);
		}
		
		private void updateText() {
			
		}
		
		public void draw(Graphics2D g2) {
			updateGraphic();
			updateText();

			g2.setColor(Color.BLACK);
			g2.draw(shape);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getPoint());
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
