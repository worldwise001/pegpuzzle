package harbinPegSTN.model;

import java.awt.Point;

public class SaveTheNetworkModel extends Model {
	private Peg turn;

	private int[][] white = new int[2][2];
	private Move lastWhiteMove = Move.NONE;
	private int whitePenalty = 0;

	//who is winning
	private Peg winning;
	public SaveTheNetworkModel() {
		reset();

	}
	public void setPenaltyWhiteLoc(int p, int n) {
		preWhiteMove();
		if (white[0][1] == n) white[0][0] = p;
		else if (white[1][1] == n) white[1][0] = p;
		checkPenalty();
	}

	public int[] getPenaltyWhiteLoc() { 
		if (whitePenalty > 0) return white[whitePenalty-1];
		return null;
	}

	private void resetWhiteTrackers() {
		white[0][0] = 0;
		white[0][1] = 0;
		white[1][0] = 0;
		white[1][1] = 0;
		whitePenalty = 0;
	}

	private boolean whiteExists() {
		for (int i = 1; i < 34; i++) {
			if (getPeg(i) == Peg.WHITE) {
				return true;
			}
		}
		return false;
	}

	private void preWhiteMove() {
		int count = 0;
		resetWhiteTrackers();
		for (int i = 1; i < 34 && count < 2; i++) {
			if (getPeg(i) == Peg.WHITE) {
				white[count][0] = i;
				white[count][1] = i;
				count++;
			}
		}
	}

	private void postWhiteMove(int from, int to) {
		if (white[0][0] == from) white[0][1] = to;
		if (white[1][0] == from) white[1][1] = to;
	}
	
	private void printWhites() {
		System.out.println(white[0][0] + " -> " + white[0][1]);
		System.out.println(white[1][0] + " -> " + white[1][1]);
	}
	
	private void tempFlipWhites(int i) {
		if (getPeg(white[i][0]) == Peg.WHITE) setPeg(Peg.NONE, white[i][0]);
		else setPeg(Peg.WHITE, white[i][0]);
	}

	private void checkPenalty() {
		if (lastWhiteMove == Move.JUMP) return;
		printWhites();
		for (int i = 0; i < 2; i++) {
			if (white[i][0] != white[i][1]) tempFlipWhites(i);
			if (isFutureJumpPossible(white[i][0])) {
				if (white[i][0] != white[i][1]) tempFlipWhites(i);
				System.out.println("Penalty at "+white[i][0]+"!");
				whitePenalty = i+1;
				setStatus(Status.PENALTY_REQUIRED);
				return;
			}
			if (white[i][0] != white[i][1]) tempFlipWhites(i);
		}
		whitePenalty = 0;
	}

	public void doPenalty() {
		if (whitePenalty > 0) setPeg(Peg.NONE, white[whitePenalty-1][1]);
		if(!whiteExists()) setStatus(Status.WINNER_BLACK);
		else {
			setStatus(Status.BLACK_MOVE);
			turn=Peg.BLACK;
		}
		resetWhiteTrackers();
	}

	public boolean togglePeg(int loc) {
		if (getStatus() == Status.WINNER_BLACK || getStatus() == Status.WINNER_WHITE) return true;
		if (!isPegLocationValid(loc)) return false;
		if(getStatus()==Status.PENALTY_REQUIRED) return false;
		if (processWhiteClick(loc)) return true;
		if (getSelectedPeg() == PEG_ID_NONE && isPegAtLocation(loc))
		{
			if(whoseTurn()!= getPeg(loc)) return false;
			selectPeg(loc);
			return true;
		}
		return processMove(loc);
	}

	protected boolean processMove(int loc) {
		switch (getMoveType(pegIDToPoint(getSelectedPeg()), pegIDToPoint(loc))) {
		case SLIDE:
			if (whoseTurn() == Peg.WHITE && lastWhiteMove == Move.JUMP) {
				reverseTurn();
				return true;
			}
			else
			{
				if (makeMove(getSelectedPeg(), loc)){
					if (whoseTurn() == Peg.WHITE) {
						lastWhiteMove = Move.SLIDE;
						postWhiteMove(getSelectedPeg(), loc);
					}
					reverseTurn();
					return true;
				}
				selectPeg(PEG_ID_NONE);
				setStatus(Status.INVALID);
				return false;
			}
		case JUMP:
			if (makeMove(getSelectedPeg(), loc)) {
				lastWhiteMove = Move.JUMP;
				selectPeg(loc);
				setStatus(Status.WHITE_JUMP);
				return true;
			} else if (lastWhiteMove == Move.JUMP) {
				reverseTurn();
				return true;
			}
			selectPeg(PEG_ID_NONE);
			setStatus(Status.INVALID);
			return false;
		case NONE:
			lastWhiteMove = Move.NONE;
			selectPeg(PEG_ID_NONE);
			return true;
		case INVALID:
			lastWhiteMove = Move.NONE;
			selectPeg(PEG_ID_NONE);
			setStatus(Status.INVALID);
			return false;
		}
		return false;
	}
	public boolean processWhiteClick(int i) {
		if (getStatus() == Status.WHITE_PLACE_1ST || getStatus() == Status.WHITE_PLACE_2ND) {
			if ((i >= 1 && i <= 6) || (i >= 9 && i <= 11))
			{
				if (getPeg(i) == Peg.WHITE) return false;
				setPeg(Peg.WHITE, i);
				if (getStatus() == Status.WHITE_PLACE_1ST) setStatus(Status.WHITE_PLACE_2ND);
				else setStatus(Status.BLACK_MOVE);
				return true;
			}
		}
		return false;
	}

	public Peg whoseTurn(){
		return turn;
	}

	private boolean isBlackLose(){
		int count=0;
		for(int i=1;i<=33;i++){
			if(getPeg(i)==Peg.BLACK)
				count++;
		}
		return count<6;
	}

	public void reverseTurn() {
		if(turn==Peg.WHITE)
		{
			System.out.println("End white turn, last move="+lastWhiteMove);
			selectPeg(PEG_ID_NONE);
			checkPenalty();
			lastWhiteMove = Move.NONE;
			if(this.isBlackLose()){
				setStatus(Status.WINNER_WHITE);
				selectPeg(PEG_ID_NONE);
				return;
			}
			if (getStatus() != Status.PENALTY_REQUIRED) {
				setStatus(Status.BLACK_MOVE);
				turn=Peg.BLACK;
			}
		}
		else
		{
			turn=Peg.WHITE;
			lastWhiteMove = Move.NONE;
			setStatus(Status.WHITE_MOVE);
			preWhiteMove();
		}
		selectPeg(PEG_ID_NONE);
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
			boolean slide = (checkSlide(fPt,sPt) && !isPegAtLocation(sPt));// 1: slide
			boolean jump = (checkJump(fPt,sPt) && (getPeg(mid) == Peg.BLACK) && !isPegAtLocation(sPt)); // 2: jump
			return slide || jump;
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
		super.setStatus(Status.WHITE_PLACE_1ST);
	}
	@Override
	protected boolean isPegLocationValid(Point pegLocation) {
		if(!super.isPegLocationValid(pegLocation))
			return false;
		if(this.getSquaredDistance(pegLocation, this.getNearestCorner(pegLocation))<=2)
			return false;
		return true;
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
