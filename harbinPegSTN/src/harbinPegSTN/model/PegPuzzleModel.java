package harbinPegSTN.model;

import java.awt.Point;

public class PegPuzzleModel extends Model{

	public static final int CENTER_LOC = 17;
	/*
	 * Initializing board
	 */
	public PegPuzzleModel(){
		reset();
	}
	
	private boolean checkMove(Point fPt, Point sPt){
		Point mid = getMiddlePeg(fPt, sPt);
		return (isPegAtLocation(fPt)&&isPegAtLocation(mid)&&!isPegAtLocation(sPt));
	}
	
	public boolean makeMove(int firstLoc,int secLoc){
		if (!isPegLocationValid(firstLoc) || !isPegLocationValid(secLoc)) return false;
		
		Point fPt = pegIDToPoint(firstLoc);
		Point sPt = pegIDToPoint(secLoc);
		
		if(!checkMove(fPt,sPt)) return false;
		Point mid = getMiddlePeg(fPt,sPt);
		
		setPeg(Peg.NONE, fPt);
		setPeg(Peg.NONE, mid);
		setPeg(Peg.NORMAL, sPt);
		return true;
	}
	
	public void reset() {
		super.reset();
		setDiagonalMovesAllowed(false);
		for(int i = BOARD_LEFT_BOUND; i <= BOARD_RIGHT_BOUND; i++){
			super.setPeg(Peg.NORMAL, i);
		}
		setPeg(Peg.NONE,CENTER_LOC);
	}
	
	public int remainingPegs() {
		Peg[] board = getBoard();
		int total = 0;
		for (int i = 1; i < 34; i++)
			if (board[i] == Peg.NORMAL) total++;
		return total;
	}
}
