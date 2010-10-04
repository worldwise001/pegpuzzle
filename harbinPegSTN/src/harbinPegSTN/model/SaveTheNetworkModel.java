package harbinPegSTN.model;

import java.awt.Point;

public class SaveTheNetworkModel extends Model {

	public SaveTheNetworkModel() {
		super();
		// Board is NONE by default
		setPegAt(Peg.BLACK, 7);
		setPegAt(Peg.BLACK, 8);
		setPegAt(Peg.BLACK, 12);
		setPegAt(Peg.BLACK, 13);
		for (int i = 14; i <= 33; i++)
			super.setPegAt(Peg.BLACK, i);

	}
	public boolean checkJump(Point fPt, Point sPt){
		return super.checkJump(fPt, sPt, true);
	}
	public boolean checkHop(Point fPt, Point sPt){
		if( !super.checkHop(fPt, sPt, true))
			return false;
		//black can only move forward and sideways
		if(pegs[fPt.x][fPt.y]==Model.Peg.BLACK){
			if(sPt.x>fPt.x)
				return false;
		}
		//else is white can hop freely
		return true;
	}
	public boolean checkMove(Point fPt, Point sPt) {
		if(fPt==null||sPt==null)
			return false;
		switch (pegs[fPt.x][fPt.y]) {
		case INVALID:
		case NONE:
		case BLANK:
			return false;
		case BLACK:
			// black dots can only move forward and sideways
			if (checkHop(fPt,sPt)) {
				return !isPegAtLocation(sPt);
			}
			return false;

		case WHITE:
			// white can move either way
			// 1: move
			if (checkHop(fPt,sPt)) {
				return !this.isPegAtLocation(sPt);
			}
			else if(checkJump(fPt,sPt)){
			// 2: jump
			Point mid = getMiddlePeg(fPt, sPt);
			return pegs[mid.x][mid.y] == Peg.BLACK && !isPegAtLocation(sPt);
			}
			return false;
		}
		return false;
	}

	public boolean makeMove(Point fPt, Point sPt) {
		if (!checkMove(fPt, sPt))
			return false;
		switch (pegs[fPt.x][fPt.y]) {
		case BLACK:
			pegs[fPt.x][fPt.y] = Peg.BLANK;
			pegs[sPt.x][sPt.y] = Peg.BLACK;
			return true;

		case WHITE:
			// white can move either way
			// 1: move
			if (checkHop(fPt,sPt)) {
				pegs[fPt.x][fPt.y] = Peg.BLANK;
				pegs[sPt.x][sPt.y] = Peg.WHITE;
				return true;
			}
			// 2: jump
			Point mid = getMiddlePeg(fPt, sPt);
			pegs[fPt.x][fPt.y] = Peg.BLANK;
			pegs[mid.x][mid.y] = Peg.BLANK;
			pegs[sPt.x][sPt.y] = Peg.WHITE;
			return true;
		default: return false;
		}

	}

	public boolean makeMove(int firstLoc, int secLoc) {
		if(firstLoc<1||firstLoc>33)
			return false;
		if(secLoc<1||secLoc>33)
			return false;

		Point fPt = pegIDToPoint(firstLoc);
		Point sPt = pegIDToPoint(secLoc);
		return makeMove(fPt, sPt);

	}

	public boolean checkMove(int firstLoc, int secLoc) {
		if (firstLoc < 1 || firstLoc > 33)
			return false;
		if (secLoc < 1 || secLoc > 33)
			return false;

		Point fPt = pegIDToPoint(firstLoc);
		Point sPt = pegIDToPoint(secLoc);
		return checkMove(fPt, sPt);

	}
}