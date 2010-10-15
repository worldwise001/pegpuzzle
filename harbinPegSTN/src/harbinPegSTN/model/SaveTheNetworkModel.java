package harbinPegSTN.model;

import java.awt.Point;

public class SaveTheNetworkModel extends Model {
	private Peg turn;
	
	public SaveTheNetworkModel() {
		reset();
		//intially start with white's turn
		
	}
	
	public boolean togglePeg(int loc) {
		if (loc < 1 || loc > 33)
			return false;
		if (super.getPreviousClicked() == -1) {
			super.setPreviousClicked(loc);
			return true;
		}
		int preClicked = super.getPreviousClicked();
		if (this.checkJump(preClicked, loc)) {
			if (makeMove(preClicked, loc)) {
				if (this.isFutureJumpPossible(loc))
					super.setPreviousClicked(loc);
				else {
					setPreviousClicked(-1);
					this.reverseTurn();
				}
				return true;
			}
		} else if (this.checkHop(preClicked, loc)) {
			if (makeMove(preClicked, loc)) {
				this.reverseTurn();
				setPreviousClicked(-1);
				return true;
			}
		}
		if(this.isPegAtLocation(loc))
			setPreviousClicked(loc);
		else setPreviousClicked(-1);
		return true;
	}
	public Peg whoseTurn(){
		return turn;
	}
	public void reverseTurn(){
		if(turn==Peg.WHITE)
			turn=Peg.BLACK;
		else turn=Peg.WHITE;
	}
	public boolean checkJump(int id1, int id2){
		if(id1<1||id1>33||id2<1||id2>33)
			return false;
		return checkJump(pegIDToPoint(id1), pegIDToPoint(id2));
	}
	public boolean checkJump(Point fPt, Point sPt){
		if(fPt==null || sPt==null)
			return false;
		return super.checkJump(fPt, sPt, true);
	}
	public boolean checkHop(int id1,int id2){
		return checkSlide(pegIDToPoint(id1),pegIDToPoint(id2));
	}
	public boolean checkSlide(Point fPt, Point sPt){
		if( !super.checkSlide(fPt, sPt, true))
			return false;
		//black can only move forward and sideways
		if(pegs[fPt.x][fPt.y]==Peg.BLACK){
			if(sPt.x>fPt.x)
				return false;
		}
		//else is white can hop freely
		return true;
	}
	public boolean checkMove(Point fPt, Point sPt) {
		if(fPt==null||sPt==null)
			return false;
		if(fPt.x<0||fPt.x>7||fPt.y<0||fPt.y>7)
			return false;
		if(sPt.x<0||sPt.x>7||sPt.y<0||sPt.y>7)
			return false;
		switch (pegs[fPt.x][fPt.y]) {
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
			Point mid = getMiddlePeg(fPt, sPt,true);
			return pegs[mid.x][mid.y] == Peg.BLACK && !isPegAtLocation(sPt);
			}
			return false;
		}
		return false;
	}

	public boolean makeMove(Point fPt, Point sPt) {
		if (!checkMove(fPt, sPt))
			return false;
		if(pegs[fPt.x][fPt.y]!=turn)
			return false;
		switch (pegs[fPt.x][fPt.y]) {
		case BLACK:
			pegs[fPt.x][fPt.y] = Peg.NONE;
			pegs[sPt.x][sPt.y] = Peg.BLACK;
			return true;

		case WHITE:
			// white can move either way
			// 1: move
			if (checkSlide(fPt,sPt)) {
				pegs[fPt.x][fPt.y] = Peg.NONE;
				pegs[sPt.x][sPt.y] = Peg.WHITE;
				return true;
			}
			// 2: jump
			Point mid = getMiddlePeg(fPt, sPt);
			pegs[fPt.x][fPt.y] = Peg.NONE;
			pegs[mid.x][mid.y] = Peg.NONE;
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
	
	public void reset() {
		super.reset();
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
		if(loc<1||loc>33)
			return false;
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
		//test bootom
		fp.y=p.y;
		fp.x=p.x+2;
		if(checkMove(p,fp))
			return true;
		//test bootomleft
		fp.y=p.y-2;
		fp.x=p.x+2;
		if(checkMove(p,fp))
			return true;
		return false;
	}
}
