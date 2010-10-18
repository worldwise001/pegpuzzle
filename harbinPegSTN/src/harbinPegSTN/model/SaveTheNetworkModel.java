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
		if (numWhitePegsToPlace > 0 && placeWhite(loc)) {
			numWhitePegsToPlace--;
			super.setStatus(Status.WHITE_CLICK);
			return true;
		}
		if (getSelectedPeg() == PEG_ID_NONE) {
			selectPeg(loc);
			
			
			return true;
		} else {
			if (getSelectedPeg() == loc) {
				selectPeg(PEG_ID_NONE);
				return true;
			}
			else 
			{
				switch (getMoveType(pegIDToPoint(getSelectedPeg()), pegIDToPoint(loc))) {
				case SLIDE:
					if (isJumping) {
						isJumping = false;
						selectPeg(PEG_ID_NONE);
						super.setStatus(Status.BLACK_TURN);
						reverseTurn();
						return false;
					}
					else
					{
						if (makeMove(getSelectedPeg(), loc)){
							selectPeg(PEG_ID_NONE);
							if(turn==Peg.WHITE)
								super.setStatus(Status.BLACK_TURN);
							else super.setStatus(Status.WHITE_TURN);
							reverseTurn();
							return true;
						}
						super.setStatus(Status.INVALID);
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
						super.setStatus(Status.WHITE_JUMP);
						return true;
					}
					return false;
				case NONE:
					isJumping = false;
					return true;
				case INVALID:
					return false;
				}
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
		case INVALID:
		case NONE:
		case BLANK:
			return false;
		case BLACK:
			// black dots can only move forward and sideways
			if (checkSlide(fPt,sPt)) {
				return !isPegAtLocation(sPt);
			}
			return false;

		case WHITE:
			// white can move either way
			// 1: move
			if (checkSlide(fPt,sPt)) {
				return !this.isPegAtLocation(sPt);
			}
			else if(checkJump(fPt,sPt)){
				// 2: jump
				Point mid = getMiddlePeg(fPt, sPt);
				return getPeg(mid) == Peg.BLACK && !isPegAtLocation(sPt);
			}
			return false;
		}
		return false;
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

	public boolean checkMove(int firstLoc, int secLoc) {
		if (!isPegLocationValid(firstLoc) || !isPegLocationValid(secLoc)) return false;

		Point fPt = pegIDToPoint(firstLoc);
		Point sPt = pegIDToPoint(secLoc);
		return checkMove(fPt, sPt);

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

	public boolean placeWhite(int i) {
		if ((i >= 1 && i <= 6) || (i >= 9 && i <= 11))
		{
			this.setPeg(Peg.WHITE, i);
			return true;
		}
		else
			return false;
	}
	public boolean isFutureJumpPossible(int loc){
		if (!isPegLocationValid(loc)) return false;

		Point p=pegIDToPoint(loc);
		Point fp=new Point();
		//test left
		fp.y=p.y-2;
		fp.x=p.x;
		if(checkMove(p,fp))
			return true;
		//test topleft
		fp.y=p.y-2;
		fp.x=p.x-2;
		if(checkMove(p,fp))
			return true;
		//test top
		fp.y=p.y;
		fp.x=p.x-2;
		if(checkMove(p,fp))
			return true;
		//test topright
		fp.y=p.y+2;
		fp.x=p.x-2;
		if(checkMove(p,fp))
			return true;
		//test right
		fp.y=p.y+2;
		fp.x=p.x;
		if(checkMove(p,fp))
			return true;
		//test bottomright
		fp.y=p.y+2;
		fp.x=p.x+2;
		if(checkMove(p,fp))
			return true;
		//test bottom
		fp.y=p.y;
		fp.x=p.x+2;
		if(checkMove(p,fp))
			return true;
		//test bottomleft
		fp.y=p.y-2;
		fp.x=p.x+2;
		if(checkMove(p,fp))
			return true;
		return false;
	}
}
