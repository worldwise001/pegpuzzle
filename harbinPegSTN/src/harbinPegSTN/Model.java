package harbinPegSTN;

import java.awt.Point;

public class Model {

	public enum Peg { NONE, NORMAL, WHITE, BLACK, BLANK, INVALID };

	protected Peg[][] pegs;

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

	public Peg getPeg(int loc) {
		if (loc < 1 || loc > 33)
			return Peg.INVALID;
		Point pt = pegIDToPoint(loc);
		if (pegs[pt.x][pt.y] == null) return Peg.INVALID;
		return pegs[pt.x][pt.y]; 
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
	
	public int getMiddlePeg(int loc1, int loc2)
	{
		return getMiddlePeg(loc1, loc2, false);
	}

	public int getMiddlePeg(int loc1, int loc2, boolean allowDiag) {
		Point mid=new Point();
		int midLoc=0;
		Point fPt=pegIDToPoint(loc1);
		Point sPt=pegIDToPoint(loc2);
		if(Math.abs(fPt.x-sPt.x)==2 && fPt.y==sPt.y)
		{
			mid.y=fPt.y;
			if(fPt.x>sPt.x)
				mid.x=fPt.x-1;
			else
				mid.x=fPt.x+1;
			midLoc=pointToPegID(mid.x, mid.y);  
			return midLoc;
		}
		if(Math.abs(fPt.y-sPt.y)==2 && fPt.x==sPt.x)
		{
			mid.x=fPt.x;
			if(fPt.y>sPt.y)
				mid.y=fPt.y-1;
			else
				mid.y=fPt.y+1;
			midLoc=pointToPegID(mid.x, mid.y);  
			return midLoc;
		}
		if(allowDiag && Math.abs(fPt.x-sPt.x)==2 && Math.abs(fPt.y-sPt.y)==2){
			mid.x=(fPt.x>sPt.x)?(fPt.x-1):(fPt.x+1);
			mid.y=(fPt.y>sPt.y)?(fPt.y-1):(fPt.y+1);
			midLoc=pointToPegID(mid.x, mid.y);  
			return midLoc;
		}
		return -1;
	}


}
