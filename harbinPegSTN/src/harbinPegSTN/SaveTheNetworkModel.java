package harbinPegSTN;

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

	public boolean checkMove(Point fPt, Point sPt) {

		switch (pegs[fPt.x][fPt.y]) {
		case INVALID:
		case NONE:
		case BLANK:
			return false;
		case BLACK:
			// black dots can only move forward and sideways
			if ((fPt.x == sPt.x && Math.abs(fPt.y - sPt.y) == 1)
					|| (fPt.x == sPt.x + 1 && fPt.y == sPt.y)
					|| (fPt.x == sPt.x + 1 && Math.abs(fPt.y - sPt.y) == 1)) {
				return !isPegAtLocation(sPt);
			}
			return false;

		case WHITE:
			// white can move either way
			// 1: move
			if (Math.abs(fPt.x - sPt.x) <= 1 && Math.abs(fPt.y - sPt.y) <= 1) {
				return !this.isPegAtLocation(sPt);
			}
			// 2: jump
			Point mid = getMiddlePeg(fPt, sPt);
			if (mid == null)
				return false;

			return pegs[mid.x][mid.y] == Peg.BLACK && !isPegAtLocation(sPt);

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
			if (Math.abs(fPt.x - sPt.x) <= 1 && Math.abs(fPt.y - sPt.y) <= 1) {
				pegs[fPt.x][fPt.y] = Peg.BLANK;
				pegs[sPt.x][sPt.y] = Peg.WHITE;
				return true;
			}

			Point mid = getMiddlePeg(fPt, sPt);
			if(mid==null)
				return false;
			pegs[fPt.x][fPt.y] = Peg.BLANK;
			pegs[mid.x][mid.y] = Peg.BLANK;
			pegs[sPt.x][sPt.y] = Peg.WHITE;
			return true;
		default: return false;
		}

	}

	public boolean makeMove(int firstLoc, int secLoc) {
		if (!checkMove(firstLoc, secLoc))
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
