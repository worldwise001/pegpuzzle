package harbinPegSTN.model;

import java.awt.Point;

public class Model {

	private static final int BOARD_SIZE = 7;
	private static final int BOARD_RIGHT_BOUND = 33;
	private static final int BOARD_LEFT_BOUND = 1;
	private int previousClicked;
	protected Peg[][] pegs;

	public Model() {
		reset();
	}
	
	public void reset() {
		// TODO Auto-generated method stub

		pegs = new Peg[BOARD_SIZE][BOARD_SIZE];
		for (int x = 0; x < BOARD_SIZE; x++) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				if ((x < 2 && y < 2) || (x < 2 && y > 4) || (x > 4 && y < 2) || (x > 4 && y > 4)) pegs[x][y] = Peg.BLANK;
				else pegs[x][y] = Peg.NONE;
			}
		}

		previousClicked = -1;
	}
	
	public void setPreviousClicked(int pegLocation){
		previousClicked=pegLocation;
	}
	public int getPreviousClicked(){
		return previousClicked;
	}
	
	public boolean isPegAtLocation(int pegLocation) {
		if (pegLocation < BOARD_LEFT_BOUND || pegLocation > BOARD_RIGHT_BOUND) return false;
		Point pt = pegIDToPoint(pegLocation);
		return isPegAtLocation(pt); 
	}

	public boolean isPegAtLocation(Point pegLocation) {
		return (pegs[pegLocation.x][pegLocation.y] != Peg.INVALID) && (pegs[pegLocation.x][pegLocation.y] != Peg.NONE) && (pegs[pegLocation.x][pegLocation.y] != Peg.BLANK); 
	}

	public Peg getPeg(int pegLocation) {
		if (pegLocation < BOARD_LEFT_BOUND || pegLocation > BOARD_RIGHT_BOUND) return Peg.INVALID;
		Point pt = pegIDToPoint(pegLocation);
		if (pegs[pt.x][pt.y] == null) return Peg.INVALID;
		return pegs[pt.x][pt.y];
	}

	public void setPeg(Peg peg, int pegLocation) {
		Point pt = pegIDToPoint(pegLocation);
		if (pt == null) return;
		pegs[pt.x][pt.y] = peg;
	}

	public Point pegIDToPoint(int pegLocation) {
		if (pegLocation < 1 || pegLocation > 33) return null;
		Point p = new Point();
		if (pegLocation >= 1 && pegLocation <= 6)
		{
			p.x = (pegLocation-1)/3;
			p.y = ((pegLocation-1)%3)+2;
		}
		else if (pegLocation >= 7 && pegLocation <= 27)
		{
			p.x = (pegLocation/7)+1;
			p.y = (pegLocation%7);
		}
		else
		{
			p.x = ((pegLocation-1)/3)-4;
			p.y = ((pegLocation-1)%3)+2;
		}
		return p;
	}

	public int pointToPegID(Point pegLocation) {
		int x = pegLocation.x;
		int y = pegLocation.y;
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

	public int getMiddlePeg(int peg1, int peg2)
	{
		return getMiddlePeg(peg1, peg2, false);
	}

	public int getMiddlePeg(int loc1, int loc2, boolean allowDiag) {
		Point p = getMiddlePeg(pegIDToPoint(loc1), pegIDToPoint(loc2), allowDiag);
		if (p == null) return -1;
		return pointToPegID(p);
	}

	protected Point getMiddlePeg(Point p1, Point p2)
	{
		return getMiddlePeg(p1, p2, false);
	}

	protected Point getMiddlePeg(Point p1, Point p2, boolean allowDiag)
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

	protected boolean checkJump(Point p1, Point p2)
	{
		return checkJump(p1, p2, false);
	}

	protected boolean checkJump(Point p1, Point p2, boolean allowDiag)
	{
		Point mid = getMiddlePeg(p1, p2, allowDiag);
		return mid != null;
	}

	protected boolean checkSlide(Point p1, Point p2)
	{
		return checkSlide(p1, p2, false);
	}

	protected boolean checkSlide(Point p1, Point p2, boolean allowDiag)
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
	
	public boolean togglePeg(int loc) {
		return false;
	}
	
	public boolean processMoveSequence(int[][] sequence) {
		return false;
	}
	
	public void getError() {
		
	}
	
	public Status getStatus() {
		return Status.INVALID;
	}

}
