package harbinPegSTN.model;

import java.awt.Point;

public class Model {

	public enum Peg { NONE, NORMAL, WHITE, BLACK, BLANK, INVALID };
	
	private int previousClicked;
	protected Peg[][] pegs;

	public Model() {
		reset();
		previousClicked=-1;
	}
	public void setPreviousClicked(int pc){
		previousClicked=pc;
	}
	public int getPreviousClicked(){
		return previousClicked;
	}
	public boolean isPegAtLocation(int loc) {
		if (loc < 1 || loc > 33) return false;
		Point pt = pegIDToPoint(loc);
		return isPegAtLocation(pt); 
	}
	
	public boolean isPegAtLocation(Point pt) {
		return (pegs[pt.x][pt.y] != Peg.INVALID) && (pegs[pt.x][pt.y] != Peg.NONE)&&(pegs[pt.x][pt.y]!=Peg.BLANK); 
	}

	public Peg getPeg(int loc) {
		if (loc < 1 || loc > 33)
			return Peg.INVALID;
		Point pt = pegIDToPoint(loc);
		return getPeg(pt); 
	}
	
	public Peg getPeg(Point pt) {
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
		Point p = getMiddlePeg(pegIDToPoint(loc1), pegIDToPoint(loc2), allowDiag);
		if (p == null) return -1;
		return pointToPegID(p.x, p.y);
	}
	
	public Point getMiddlePeg(Point p1, Point p2)
	{
		return getMiddlePeg(p1, p2, false);
	}
	
	public Point getMiddlePeg(Point p1, Point p2, boolean allowDiag)
	{
		Point mid=new Point();
		if(Math.abs(p1.x-p2.x)==2 && p1.y==p2.y)
		{
			mid.y=p1.y;
			if(p1.x>p2.x)
				mid.x=p1.x-1;
			else
				mid.x=p1.x+1;
			return mid;
		}
		if(Math.abs(p1.y-p2.y)==2 && p1.x==p2.x)
		{
			mid.x=p1.x;
			if(p1.y>p2.y)
				mid.y=p1.y-1;
			else
				mid.y=p1.y+1;
			return mid;
		}
		if(allowDiag && Math.abs(p1.x-p2.x)==2 && Math.abs(p1.y-p2.y)==2){
			mid.x=(p1.x>p2.x)?(p1.x-1):(p1.x+1);
			mid.y=(p1.y>p2.y)?(p1.y-1):(p1.y+1);
			return mid;
		}
		return null;
	}
	
	public boolean checkJump(Point p1, Point p2)
	{
		return checkJump(p1, p2, false);
	}
	
	public boolean checkJump(Point p1, Point p2, boolean allowDiag)
	{
		Point mid = getMiddlePeg(p1, p2, allowDiag);
		return mid != null;
	}
	
	public boolean checkHop(Point p1, Point p2)
	{
		return checkHop(p1, p2, false);
	}
	
	public boolean checkHop(Point p1, Point p2, boolean allowDiag)
	{
		return ((Math.abs(p1.x - p2.x) == 1 && p1.y == p2.y) ||
				(Math.abs(p1.y - p2.y) == 1 && p1.x == p2.x) ||
				(allowDiag && Math.abs(p1.y - p2.y) == 1 && (Math.abs(p1.x - p2.x) == 1)));
	}
	
	//please reimplement this!
	public boolean makeMove(int loc1, int loc2)
	{
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub

		pegs = new Peg[7][7];
		for (int i = 0; i < pegs.length; i++) {
			for (int j = 0; j < pegs[i].length; j++) {
				if ((i < 2 && j < 2) || (i < 2 && j > 4) || (i > 4 && j < 2) || (i > 4 && j > 4)) pegs[i][j] = Peg.BLANK;
				else pegs[i][j] = Peg.NONE;
			}
		}
	}
	
}
