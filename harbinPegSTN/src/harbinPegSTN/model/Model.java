package harbinPegSTN.model;

import java.awt.Point;

public class Model {

	public static final int PEG_ID_NONE = -1;
	protected static final int BOARD_SIZE = 7;
	protected static final int BOARD_RIGHT_BOUND = 33;
	protected static final int BOARD_LEFT_BOUND = 1;
	
	private int selectedPeg = PEG_ID_NONE;
	private Peg[][] pegs;
	private boolean diagonalMovesAllowed = false;

	private Status status;
	public Model() {
		status=Status.INVALID;
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

		selectedPeg = PEG_ID_NONE;
	}
	
	protected void selectPeg(int pegLocation){
		selectedPeg = pegLocation;
	}
	
	public int getSelectedPeg(){
		return selectedPeg;
	}
	
	protected boolean isDiagonalMovesAllowed() {
		return diagonalMovesAllowed;
	}

	protected void setDiagonalMovesAllowed(boolean diagonalMovesAllowed) {
		this.diagonalMovesAllowed = diagonalMovesAllowed;
	}
	
	protected boolean isPegLocationValid(int pegLocation) {
		return !(pegLocation < BOARD_LEFT_BOUND || pegLocation > BOARD_RIGHT_BOUND);
	}
	
	protected boolean isPegLocationValid(Point pegLocation) {
		return (pegLocation != null) && !(pegLocation.x < 0 || pegLocation.y < 0 || pegLocation.x > 6 || pegLocation.y > 6);
	}
	
	public boolean isPegAtLocation(int pegLocation) {
		if (!isPegLocationValid(pegLocation)) return false;
		Point pt = pegIDToPoint(pegLocation);
		return isPegAtLocation(pt); 
	}

	public boolean isPegAtLocation(Point pegLocation) {
		return  (isPegLocationValid(pegLocation)) &&
				(pegs[pegLocation.x][pegLocation.y] != Peg.INVALID) &&
				(pegs[pegLocation.x][pegLocation.y] != Peg.NONE) &&
				(pegs[pegLocation.x][pegLocation.y] != Peg.BLANK); 
	}

	public Peg getPeg(int pegLocation) {
		return getPeg(pegIDToPoint(pegLocation));
	}
	
	protected Peg getPeg(Point pegLocation) {
		if (!isPegLocationValid(pegLocation)) return Peg.INVALID;
		return pegs[pegLocation.x][pegLocation.y];
	}

	public void setPeg(Peg peg, int pegLocation) {
		setPeg(peg, pegIDToPoint(pegLocation));
	}
	
	protected void setPeg(Peg peg, Point pegLocation) {
		if (!isPegLocationValid(pegLocation)) return;
		pegs[pegLocation.x][pegLocation.y] = peg;
	}

	public Point pegIDToPoint(int pegLocation) {
		if (!isPegLocationValid(pegLocation)) return null;
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
		int id = PEG_ID_NONE;
		
		if (!isPegLocationValid(pegLocation)) return id;
		if (x <= 1 && (y >= 2 && y <= 4))
			id = (y-1)+(x*3);
		else if (x >= 2 && x <= 4)
			id = (x-1)*7+y;
		else if (x >= 5 && (y >= 2 && y <= 4))
			id = (y-1)+(x+4)*3;
		return id;
	}

	protected Point getMiddlePeg(Point p1, Point p2)
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
		if(diagonalMovesAllowed && Math.abs(p1.x-p2.x)==2 && Math.abs(p1.y-p2.y)==2){
			mid.x=(p1.x>p2.x)?(p1.x-1):(p1.x+1);
			mid.y=(p1.y>p2.y)?(p1.y-1):(p1.y+1);
			return mid;
		}
		return null;
	}

	protected boolean checkJump(Point p1, Point p2)
	{
		Point mid = getMiddlePeg(p1, p2);
		return mid != null;
	}

	protected boolean checkSlide(Point p1, Point p2)
	{
		return ((Math.abs(p1.x - p2.x) == 1 && p1.y == p2.y) ||
				(Math.abs(p1.y - p2.y) == 1 && p1.x == p2.x) ||
				(diagonalMovesAllowed && Math.abs(p1.y - p2.y) == 1 && (Math.abs(p1.x - p2.x) == 1)));
	}
	
	protected Move getMoveType(Point p1, Point p2) {
		if (!isPegLocationValid(p1) || !isPegLocationValid(p2)) return Move.INVALID;
		if (p1.equals(p2)) return Move.NONE;
		if (checkSlide(p1,p2)) return Move.SLIDE;
		if (checkJump(p1,p2)) return Move.JUMP;
		return Move.INVALID;
	}

	//please reimplement this!
	public boolean makeMove(int loc1, int loc2)
	{
		return false;
	}
	
	public boolean togglePeg(int loc) {
		if (!isPegLocationValid(loc)) return false;
		if (selectedPeg == PEG_ID_NONE) {
			selectedPeg = loc;
			
			return true;
		} else {
			if (selectedPeg == loc) {
				selectedPeg = PEG_ID_NONE;
				return true;
			}
			else if (makeMove(selectedPeg, loc)) {
				selectedPeg = PEG_ID_NONE;
				
				return true;
			}
		}
		return false;
	}
	
	public boolean processMoveSequence(int[][] sequence) {
		return false;
	}
	
	public void getError() {
		
	}
	public void setStatus(Status s){
		status=s;
	}
	public Status getStatus() {
		return status;
	}
	
	protected enum Move { JUMP, SLIDE, NONE, INVALID }

}
