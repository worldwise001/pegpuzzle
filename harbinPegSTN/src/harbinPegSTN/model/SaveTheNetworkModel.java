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
		if (getSelectedPeg() == PEG_ID_NONE) {
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
					selectPeg(PEG_ID_NONE);
					reverseTurn();
					return true;
				}
				return false;
			}
		case JUMP:
			if (makeMove(getSelectedPeg(), loc)) {
				isJumping = true;
				if (isFutureJumpPossible(loc)) selectPeg(loc);
				else {
					isJumping = false;
					selectPeg(PEG_ID_NONE);
					reverseTurn();
				}
				return true;
			}
			return false;
		case NONE:
			if (isJumping) {
				isJumping = false;
				reverseTurn();
			}
			selectPeg(PEG_ID_NONE);
			return true;
		default:
			return false;
		}
	}

	public boolean processWhiteClick(int i) {
		if (numWhitePegsToPlace > 0) {
			if ((i >= 1 && i <= 6) || (i >= 9 && i <= 11))
			{
				this.setPeg(Peg.WHITE, i);
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
			turn=Peg.BLACK;
		else turn=Peg.WHITE;
	}

	public boolean checkSlide(Point fPt, Point sPt){
		if (!super.checkSlide(fPt, sPt)) return false;
		//black can only move forward and sideways
		if (getPeg(fPt) == Peg.BLACK) {
			if (sPt.x > fPt.x) return false;
		}
		return true;
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
		turn = Peg.WHITE;
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
