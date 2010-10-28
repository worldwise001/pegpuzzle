package harbinPegSTN.model;

import java.awt.Point;

public class SaveTheNetworkModel extends Model {
	private Peg turn;
	private boolean isJumping = false;
	private int numWhitePegsToPlace = 2;

	public SaveTheNetworkModel() {
		reset();
	}

	public boolean togglePeg(int loc) {
		if (!isPegLocationValid(loc)) return false;
		if (processWhiteClick(loc)) return true;
		if (getSelectedPeg() == PEG_ID_NONE && isPegAtLocation(loc)) {
			selectPeg(loc);
			return true;
		}
		return processMove(loc);
	}

	protected boolean processMove(int loc) {
		switch (getMoveType(pegIDToPoint(getSelectedPeg()), pegIDToPoint(loc))) {
		case SLIDE:
			if (isJumping) {
				isJumping = false;
				selectPeg(PEG_ID_NONE);
				reverseTurn();
				return false;
			}
			else
			{
				if (makeMove(getSelectedPeg(), loc)){
					reverseTurn();
					return true;
				}
				selectPeg(PEG_ID_NONE);
				setStatus(Status.INVALID);
				return false;
			}
		case JUMP:
			if (makeMove(getSelectedPeg(), loc)) {
				isJumping = true;
				if (isFutureJumpPossible(loc)) selectPeg(loc);
				else {
					isJumping = false;
					reverseTurn();
				}
				selectPeg(PEG_ID_NONE);
				setStatus(Status.WHITE_JUMP);
				return true;
			}
			selectPeg(PEG_ID_NONE);
			setStatus(Status.INVALID);
			return false;
		case NONE:
			isJumping = false;
			selectPeg(PEG_ID_NONE);
			return true;
		case INVALID:
			isJumping = false;
			selectPeg(PEG_ID_NONE);
			setStatus(Status.INVALID);
			return false;
		}
		return false;
	}

	public boolean processWhiteClick(int i) {
		if (numWhitePegsToPlace > 0) {
			if ((i >= 1 && i <= 6) || (i >= 9 && i <= 11))
			{
				if (getPeg(i) == Peg.WHITE) return false;
				setPeg(Peg.WHITE, i);
				setStatus(Status.WHITE_CLICK);
				numWhitePegsToPlace--;
				return true;
			}
		}
		return false;
	}

	public Peg whoseTurn(){
		return turn;
	}

	public void reverseTurn() {
		if(turn==Peg.WHITE)
		{
			turn=Peg.BLACK;
			setStatus(Status.BLACK_TURN);
		}
		else
		{
			turn=Peg.WHITE;
			setStatus(Status.WHITE_TURN);
		}
	}

	public boolean checkSlide(Point fPt, Point sPt){
		if (!super.checkSlide(fPt, sPt)) return false;
		//black can only move forward and sideways
		if (getSquaredDistance(fPt, getNearestCorner(fPt)) == 5 && getSquaredDistance(sPt, getNearestCorner(sPt)) == 5)
			return false;
		return (getPeg(fPt) == Peg.BLACK && (sPt.x <= fPt.x)) || (getPeg(fPt) == Peg.WHITE);
	}
	
	private int getSquaredDistance(Point p1, Point p2) {
		return (int)((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y));
	}
	
	private Point getNearestCorner(Point p) {
		Point corner = new Point();
		corner.x = (p.x / (BOARD_SIZE/2+1))*(BOARD_SIZE-1);
		corner.y = (p.y / (BOARD_SIZE/2+1))*(BOARD_SIZE-1);
		return corner;
	}

	public boolean checkMove(Point fPt, Point sPt) {
		if (!isPegLocationValid(fPt) || !isPegLocationValid(sPt)) return false;

		switch (getPeg(fPt)) {
		case BLACK:
			// black dots can only move forward and sideways
			return (checkSlide(fPt,sPt) && !isPegAtLocation(sPt));

		case WHITE:
			// white can move either way
			Point mid = getMiddlePeg(fPt, sPt);
			return (checkSlide(fPt,sPt) && !isPegAtLocation(sPt)) || // 1: slide
			(checkJump(fPt,sPt) && (getPeg(mid) == Peg.BLACK) && !isPegAtLocation(sPt)); // 2: jump
		default:
			return false;
		}
	}

	@Override
	protected boolean checkJump(Point p1, Point p2) {
		if (!super.checkJump(p1, p2)) return false;
		if ((getSquaredDistance(p1, getNearestCorner(p1)) == 5 && getSquaredDistance(p2, getNearestCorner(p2)) == 9) ||
				(getSquaredDistance(p1, getNearestCorner(p1)) == 9 && getSquaredDistance(p2, getNearestCorner(p2)) == 5))
			return false;
		return true;
	}

	public boolean makeMove(Point fPt, Point sPt) {
		if (!checkMove(fPt, sPt)) return false;
		if(getPeg(fPt) != turn) return false;
		switch (getPeg(fPt)) {
		case BLACK:
			setPeg(Peg.NONE, fPt);
			setPeg(Peg.BLACK, sPt);
			return true;

		case WHITE:
			// white can move either way
			// 1: move
			if (checkSlide(fPt,sPt)) {
				setPeg(Peg.NONE, fPt);
				setPeg(Peg.WHITE, sPt);
				return true;
			}
			// 2: jump
			Point mid = getMiddlePeg(fPt, sPt);
			setPeg(Peg.NONE, fPt);
			setPeg(Peg.NONE, mid);
			setPeg(Peg.WHITE, sPt);
			return true;
		default: return false;
		}

	}

	public boolean makeMove(int firstLoc, int secLoc) {
		if (!isPegLocationValid(firstLoc) || !isPegLocationValid(secLoc)) return false;

		Point fPt = pegIDToPoint(firstLoc);
		Point sPt = pegIDToPoint(secLoc);
		return makeMove(fPt, sPt);

	}

	public void reset() {
		super.reset();
		setDiagonalMovesAllowed(true);
		// Board is NONE by default
		setPeg(Peg.BLACK, 7);
		setPeg(Peg.BLACK, 8);
		setPeg(Peg.BLACK, 12);
		setPeg(Peg.BLACK, 13);
		for (int i = 14; i <= 33; i++)
			super.setPeg(Peg.BLACK, i);
		turn = Peg.BLACK;
		numWhitePegsToPlace = 2;
	}

	public boolean isFutureJumpPossible(int loc){
		if (!isPegLocationValid(loc)) return false;

		Point p=pegIDToPoint(loc);
		return (checkMove(p, modifyPoint(p, 0, -2)) ||
				checkMove(p, modifyPoint(p, -2, -2)) ||
				checkMove(p, modifyPoint(p, -2, 0)) ||
				checkMove(p, modifyPoint(p,-2, 2)) ||
				checkMove(p, modifyPoint(p, 0, 2)) ||
				checkMove(p, modifyPoint(p, 2, 2)) ||
				checkMove(p, modifyPoint(p, 2, 0)) ||
				checkMove(p, modifyPoint(p, 2, -2)));
	}

	private Point modifyPoint(Point p, int x, int y) {
		return new Point(p.x+x, p.y+y);
	}
}
