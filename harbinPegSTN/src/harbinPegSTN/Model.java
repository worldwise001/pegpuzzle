package harbinPegSTN;

import java.awt.Point;

public class Model {
	
	public enum Peg { NONE, NORMAL, WHITE, BLACK, BLANK, INVALID };

	private Peg[][] pegs;

	public Model() {
		pegs = new Peg[7][7];
		for (int i = 0; i < pegs.length; i++) {
			for (int j = 0; j < pegs[i].length; j++) {
				if ((i < 2 && j < 2) || (i < 2 && j > 4) || (i > 4 && j < 2) || (i > 4 && j > 4)) pegs[i][j] = Peg.BLANK;
				else pegs[i][j] = Peg.NONE;
			}
		}
		
	}

	public int size() {
		// TODO Auto-generated method stub
		return pegs.length;
	}

	public boolean isPegAtLocation(int loc) {
		if (loc < 1 || loc > 33)
			return false;
		Point pt = pegIDToPoint(loc);
		return (pegs[pt.x][pt.y] != Peg.INVALID) && (pegs[pt.x][pt.y] != Peg.NONE); 
	}
//
//	public boolean movePeg(int firstClick, int secondClick) {
//		// TODO Auto-generated method stub
//		if (secondClick == firstClick + 2) {
//			if (pegs[firstClick] && pegs[firstClick + 1]
//					&& !pegs[firstClick + 2]) {
//				pegs[firstClick+2] = true;
//				pegs[firstClick] = false;
//				pegs[firstClick+1] = false;
//				return true;
//			}
//		}
//		return false;
//	}
	
	public void setPegAt(Peg p, int i) {
		Point pt = pegIDToPoint(i);
		pegs[pt.x][pt.y] = p;
	}

	public Point pegIDToPoint(int i) {
		if (i < 1 || i > 33) return null;
		Point p = new Point();
		if (i >= 1 && i <= 6)
		{
			p.x = (i-1)/3;
			p.y = ((i-1)%3)+2;
		}
		else if (i >= 7 && i <= 27)
		{
			p.x = (i/7)+1;
			p.y = (i%7);
		}
		else
		{
			p.x = ((i-1)/3)-4;
			p.y = ((i-1)%3)+2;
		}
		return p;
	}

	public int pointToPegID(int x, int y) {
		if (x < 0 || y < 0 || x > 6 || y > 6) return -1;
		int id = -1;
		if (x <= 1 && (y >= 2 && y <= 4))
			id = (y-1)+(x*3);
		else if (x >= 2 && x <= 4)
			id = (x-1)*7+y;
		else if (x >= 5 && (y >= 2 && y <= 4))
			id = (y-1)+(x+4)*3;
		return id;
	}
	

}
